package com.etrade.puggo.service.payment;

import com.alibaba.fastjson.JSONObject;
import com.etrade.puggo.common.constants.GoodsState;
import com.etrade.puggo.common.constants.GoodsTradeState;
import com.etrade.puggo.common.enums.LangErrorEnum;
import com.etrade.puggo.common.enums.PaymentTypeEnum;
import com.etrade.puggo.common.enums.SettingsEnum;
import com.etrade.puggo.common.exception.CommonErrorV2;
import com.etrade.puggo.common.exception.PaymentException;
import com.etrade.puggo.common.exception.ServiceException;
import com.etrade.puggo.dao.payment.PaymentInvoiceDao;
import com.etrade.puggo.dao.user.UserDao;
import com.etrade.puggo.db.tables.records.PaymentInvoiceRecord;
import com.etrade.puggo.service.BaseService;
import com.etrade.puggo.service.account.pojo.UserInfoVO;
import com.etrade.puggo.service.ai.AiUserAvailableBalanceService;
import com.etrade.puggo.service.goods.opt.UpdateGoodsStateService;
import com.etrade.puggo.service.goods.trade.GoodsTradeService;
import com.etrade.puggo.service.goods.trade.pojo.MyTradeVO;
import com.etrade.puggo.service.goods.trade.pojo.UpdateTradeParam;
import com.etrade.puggo.service.payment.pojo.PayResult;
import com.etrade.puggo.service.payment.pojo.PayVO;
import com.etrade.puggo.service.payment.pojo.PaymentInvoiceDTO;
import com.etrade.puggo.service.payment.pojo.PaymentParam;
import com.etrade.puggo.service.setting.SettingService;
import com.etrade.puggo.third.aws.PaymentLambdaFunctions;
import com.etrade.puggo.third.aws.pojo.SellerAccountDTO;
import com.etrade.puggo.third.aws.pojo.UpdatePaymentIntentReq;
import com.etrade.puggo.utils.OptionalUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author zhenyu
 * @version 1.0
 * @description: 支付管理
 * @date 2024/1/18 15:24
 */
@Service
@Slf4j
public class PaymentService extends BaseService {


    private static final BigDecimal default_shipping = new BigDecimal(18);

    private static final BigDecimal default_tax_percentage = new BigDecimal("0.08");

    @Resource
    private PaymentLambdaFunctions paymentLambdaFunctions;

    @Resource
    private SettingService settingService;

    @Resource
    private GoodsTradeService goodsTradeService;

    @Resource
    private PaymentUtils paymentUtils;

    @Resource
    private UpdateGoodsStateService updateGoodsSale;

    @Resource
    private UserDao userDao;

    @Resource
    private PaymentInvoiceDao paymentInvoiceDao;

    @Resource
    private AiUserAvailableBalanceService aiUserAvailableBalanceService;


    @Transactional(rollbackFor = Throwable.class)
    public PayVO payForProduct(PaymentParam param) {

        // 参数验证
        checkParameter(param);

        // 验证支付订单
        MyTradeVO payTrade = goodsTradeService.getOne(param.getTradeId());
        if (payTrade == null) {
            throw new ServiceException(LangErrorEnum.INVALID_TRADE.lang());
        }

        // 发起支付
        PayResult payResult = doPay(param, payTrade);

        // 后续步骤：
        // 1. 更新支付订单信息
        goodsTradeService.updateTradePaymentInfo(buildUpdateTradeParam(param, payResult));

        // 2. 更新支付信息
        updatePaymentInvoice(param, payResult, payTrade);

        // 支付成功后，卡号方式认为是直接成功
        if (param.getPaymentType().equals(PaymentTypeEnum.card.name())) {
            afterPaySuccess(payTrade.getGoodsId(), param.getTradeId());
        }

        return PayVO.builder().payId(payTrade.getPaymentInvoiceId()).clientSecret(payResult.getClientSecret()).build();
    }


    private void afterPaySuccess(Long goodsId, Long tradeId) {
        // 1. 更新商品状态为已售
        updateGoodsSale.updateStateInner(goodsId, GoodsState.SOLD);

        // 2. 商品状态更新为已完成
        goodsTradeService.updateStatus(tradeId, GoodsTradeState.COMPLETE);
    }


    private void updatePaymentInvoice(PaymentParam param, PayResult payResult, MyTradeVO payTrade) {
        PaymentInvoiceDTO paymentInvoiceDTO = PaymentInvoiceDTO.builder()
                .paymentMethodId(OptionalUtils.valueOrDefault(param.getPaymentMethodId(), ""))
                .paymentType(OptionalUtils.valueOrDefault(param.getPaymentType(), ""))
                .billingAddressId(OptionalUtils.valueOrDefault(param.getBillingAddressId(), 0))
                .paymentCardId(OptionalUtils.valueOrDefault(param.getPaymentCardId(), 0))
                .otherFees(payResult.getOtherFees())
                .tax(payResult.getTax())
                .amount(payResult.getSubtotal())
                .invoiceId(payResult.getInvoiceId())
                .id(payTrade.getPaymentInvoiceId())
                .paymentIntentId(payResult.getPaymentIntent())
                .clientSecret(payResult.getClientSecret())
                .build();

        paymentInvoiceDao.updateGoodsTrade(paymentInvoiceDTO);
    }


    private static UpdateTradeParam buildUpdateTradeParam(PaymentParam param, PayResult payResult) {
        UpdateTradeParam updateTradeParam = new UpdateTradeParam();
        updateTradeParam.setDeliveryAddressId(param.getDeliveryAddressId());
        updateTradeParam.setShippingMethod(param.getShippingMethod());
        updateTradeParam.setSubtotal(payResult.getSubtotal());
        updateTradeParam.setTax(payResult.getTax());
        updateTradeParam.setOtherFees(payResult.getOtherFees());
        updateTradeParam.setTradeId(param.getTradeId());
        return updateTradeParam;
    }


    private PayResult doPay(PaymentParam param, MyTradeVO oneTrade) {

        // 商品成交总金额
        BigDecimal totalAmount = oneTrade.getTradingPrice();

        // 买家支付账号
        String paymentCustomerId = paymentUtils.getPaymentCustomerId(oneTrade.getCustomerId());

        // 卖家支付账号
        String paymentSellerId = paymentUtils.getPaymentSellerId(oneTrade.getSellerId());

        // 邮费，这个给系统
        String shippingSetting = settingService.k(SettingsEnum.sameDayDeliveryCharge.v());
        BigDecimal shippingFees = isNumber(shippingSetting) ? new BigDecimal(shippingSetting) : default_shipping;

        // 商品税，这个给系统
        String taxPercentageSetting = settingService.k(SettingsEnum.productTaxPercentage.v());
        BigDecimal taxPercentage = isValidPercentage(new BigDecimal(taxPercentageSetting)) ?
                new BigDecimal(taxPercentageSetting) : default_tax_percentage;
        BigDecimal tax = totalAmount.multiply(taxPercentage);

        // 商品金额，这个给商家
        BigDecimal amount = totalAmount.subtract(tax);

        // 发起支付
        JSONObject jsonObject = paymentUtils.execute(
                amount.setScale(0, RoundingMode.UP),
                tax.setScale(0, RoundingMode.UP),
                shippingFees,
                paymentCustomerId,
                paymentSellerId,
                param.getPaymentType(),
                OptionalUtils.valueOrDefault(param.getPaymentMethodId()),
                OptionalUtils.valueOrDefault(param.getToken()),
                param.getPaymentType().equals(PaymentTypeEnum.card.name()) && param.getPaymentCardId() != null
        );

        PayResult payResult = PayResult.builder().subtotal(amount).tax(tax).otherFees(shippingFees).build();

        if (jsonObject.containsKey("invoiceId")) {
            payResult.setInvoiceId((String) jsonObject.get("invoiceId"));
        }

        if (jsonObject.containsKey("clientSecret")) {
            payResult.setClientSecret((String) jsonObject.get("clientSecret"));
        }

        if (jsonObject.containsKey("paymentIntent")) {
            payResult.setPaymentIntent((String) jsonObject.get("paymentIntent"));
        }

        return payResult;
    }


    private void checkParameter(PaymentParam param) {

        // 检查支付方式
        paymentUtils.checkPaymentType(param.getPaymentType());

        // 检查交易方式
        paymentUtils.checkShippingMethod(param.getShippingMethod());

        // 检查收货地址
        paymentUtils.checkDeliveryAddress(param.getDeliveryAddressId());

        // 检查账单地址
        if (BooleanUtils.isNotTrue(param.getIsSameAsDeliveryAddress())) {
            paymentUtils.checkBillingAddress(param.getBillingAddressId());
        }

        // 检查银行卡信息
        if (param.getPaymentCardId() != null) {
            paymentUtils.checkCardInfo(param.getPaymentCardId());
        }
    }


    public static boolean isNumber(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e1) {
            try {
                Float.parseFloat(str);
                return true;
            } catch (NumberFormatException e2) {
                return false;
            }
        }
    }


    private static boolean isValidPercentage(BigDecimal percentage) {
        return percentage.compareTo(BigDecimal.ZERO) > 0 && percentage.compareTo(BigDecimal.ONE) < 1;
    }


    /**
     * 创建/更新商家支付账号
     *
     * @return 商家Stripe注册链接
     */
    public String createSellerAccount() {
        // 获取商家邮件
        UserInfoVO userInfo = userDao.findUserInfo(userId());

        String email = userInfo.getEmail();
        String paymentSellerId = userInfo.getPaymentSellerId();

        String sellerAccountLinkURL;

        if (StringUtils.isBlank(paymentSellerId)) {
            // 创建商家支付账号
            SellerAccountDTO sellerAccount = paymentLambdaFunctions.createSellerAccount(email);

            // 保存商家支付账号
            if (sellerAccount != null && sellerAccount.getAccountId() != null) {
                userDao.updatePaymentSellerId(userId(), sellerAccount.getAccountId());
            }

            sellerAccountLinkURL = sellerAccount != null ? sellerAccount.getAccountLinkURL() : null;
        } else {
            // 重新获取Stripe账号注册链接
            sellerAccountLinkURL = paymentLambdaFunctions.createSellerAccountLink(paymentSellerId);
        }

        return sellerAccountLinkURL;
    }


    public void updatePaymentIntent(Long payId, String token) {

        PaymentInvoiceRecord payRecord = paymentInvoiceDao.getOne(payId);

        if (payRecord == null) {
            throw new PaymentException(CommonErrorV2.PAYMENT_ERROR);
        }

        UpdatePaymentIntentReq updatePaymentIntentReq = new UpdatePaymentIntentReq();
        updatePaymentIntentReq.setPaymentIntentId(payRecord.getPaymentIntentId());
        updatePaymentIntentReq.setCustomerId(userDao.getPaymentCustomerId(payRecord.getUserId()));
        updatePaymentIntentReq.setToken(token);

        // call ’update_payment_intent‘
        paymentLambdaFunctions.updatePaymentIntent(updatePaymentIntentReq);
    }


    @Transactional(rollbackFor = Exception.class)
    public void confirmPaymentIntent(Long payId) {

        PaymentInvoiceRecord payRecord = paymentInvoiceDao.getOne(payId);

        if (payRecord == null) {
            throw new PaymentException(CommonErrorV2.PAYMENT_ERROR);
        }

        // call ’confirm_payment_intent‘
        paymentLambdaFunctions.confirmPaymentIntent(payRecord.getPaymentIntentId());

        // 支付成功后的处理
        String title = payRecord.getTitle();
        if (title.equals("购买AI鉴定额度")) {
            aiUserAvailableBalanceService.plusAvailableBalance(payRecord.getUserId(), payRecord.getAiKindId(), payRecord.getAiPlusAvailableTimes());
        }

        if (title.equals("商品交易")) {
            MyTradeVO tradeVO = goodsTradeService.getOneByPaymentInvoiceId(payId);

            afterPaySuccess(tradeVO.getGoodsId(), tradeVO.getTradeId());
        }

    }

}
