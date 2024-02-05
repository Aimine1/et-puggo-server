package com.etrade.puggo.service.payment;

import com.etrade.puggo.common.constants.GoodsState;
import com.etrade.puggo.common.enums.LangErrorEnum;
import com.etrade.puggo.common.enums.PaymentTargetEnum;
import com.etrade.puggo.common.enums.PaymentTypeEnum;
import com.etrade.puggo.common.enums.SettingsEnum;
import com.etrade.puggo.common.exception.ServiceException;
import com.etrade.puggo.dao.user.UserDao;
import com.etrade.puggo.service.BaseService;
import com.etrade.puggo.service.account.pojo.UserInfoVO;
import com.etrade.puggo.service.goods.opt.UpdateGoodsStateService;
import com.etrade.puggo.service.goods.trade.GoodsTradeService;
import com.etrade.puggo.service.goods.trade.pojo.MyTradeVO;
import com.etrade.puggo.service.goods.trade.pojo.UpdateTradeParam;
import com.etrade.puggo.service.payment.pojo.PayResult;
import com.etrade.puggo.service.payment.pojo.PaymentParam;
import com.etrade.puggo.service.setting.SettingService;
import com.etrade.puggo.third.aws.PaymentLambdaFunctions;
import com.etrade.puggo.third.aws.pojo.CreateInvoiceReq;
import com.etrade.puggo.third.aws.pojo.SellerAccountDTO;
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

    private static final String default_currency = "CAD";

    private static final BigDecimal Dollar2CentUnit = new BigDecimal(100);

    @Resource
    private PaymentLambdaFunctions paymentLambdaFunctions;

    @Resource
    private SettingService settingService;

    @Resource
    private GoodsTradeService goodsTradeService;

    @Resource
    private PaymentCheckUtils paymentCheckUtils;

    @Resource
    private UpdateGoodsStateService updateGoodsSale;

    @Resource
    private UserDao userDao;


    @Transactional(rollbackFor = Throwable.class)
    public String pay(PaymentParam param) {

        // 参数验证
        checkParameter(param);


        // 验证支付订单
        MyTradeVO payTrade = goodsTradeService.getOne(param.getTradeId());
        if (payTrade == null) {
            throw new ServiceException(LangErrorEnum.INVALID_TRADE.lang());
        }

        // 发起支付
        PayResult payResult;
        if (PaymentTargetEnum.product.name().equals(param.getTarget())) {
            payResult = payForProduct(param, payTrade);
        } else {
            payResult = payForAI(param);
        }

        // 后续步骤
        // 1. 更新支付订单信息
        UpdateTradeParam updateTradeParam = buildUpdateTradeParam(param, payResult);

        goodsTradeService.updateTradePaymentInfo(updateTradeParam);

        // 2. 更新商品状态为已售
        updateGoodsSale.updateStateInner(payTrade.getGoodsId(), GoodsState.SOLD);

        return payResult.getInvoiceId();
    }


    private static UpdateTradeParam buildUpdateTradeParam(PaymentParam param, PayResult payResult) {
        UpdateTradeParam updateTradeParam = new UpdateTradeParam();
        updateTradeParam.setPaymentType(param.getPaymentType());
        updateTradeParam.setPaymentMethodId(param.getPaymentMethodId());
        updateTradeParam.setDeliveryAddressId(param.getDeliveryAddressId());
        updateTradeParam.setBillingAddressId(param.getBillingAddressId());
        updateTradeParam.setIsSameAsDeliveryAddress(param.getIsSameAsDeliveryAddress());
        updateTradeParam.setShippingMethod(param.getShippingMethod());
        updateTradeParam.setPaymentCardId(OptionalUtils.valueOrDefault(param.getPaymentCardId()));
        updateTradeParam.setSubtotal(payResult.getSubtotal());
        updateTradeParam.setTax(payResult.getTax());
        updateTradeParam.setOtherFees(payResult.getOtherFees());
        updateTradeParam.setInvoiceId(payResult.getInvoiceId());
        updateTradeParam.setTradeId(param.getTradeId());
        return updateTradeParam;
    }


    private PayResult payForProduct(PaymentParam param, MyTradeVO oneTrade) {


        // 商品成交总金额
        BigDecimal totalAmount = oneTrade.getTradingPrice();

        // 买家支付账号
        String paymentCustomerId = paymentCheckUtils.getPaymentCustomerId(oneTrade.getCustomerId());

        // 卖家支付账号
        String paymentSellerId = paymentCheckUtils.getPaymentSellerId(oneTrade.getSellerId());

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
        String invoiceId = execute(
                amount.setScale(0, RoundingMode.UP),
                tax.setScale(0, RoundingMode.UP),
                shippingFees,
                paymentCustomerId,
                paymentSellerId,
                param.getPaymentType(),
                param.getPaymentMethodId(),
                param.getToken()
        );

        return PayResult.builder().subtotal(amount).tax(tax).otherFees(shippingFees).invoiceId(invoiceId).build();
    }


    private PayResult payForAI(PaymentParam param) {

        BigDecimal amount = BigDecimal.ZERO;

        execute(amount, BigDecimal.ZERO, BigDecimal.ZERO, null, null, param.getPaymentType(),
                param.getPaymentMethodId(), param.getToken());

        return null;
    }


    private String execute(BigDecimal amount, BigDecimal tax, BigDecimal shippingFees,
                           String paymentCustomerId, String paymentSellerId, String paymentType, String paymentMethodId,
                           String token) {

        CreateInvoiceReq req = new CreateInvoiceReq();
        // 商品金额，单位:分
        req.setAmount(amount.multiply(Dollar2CentUnit));
        // 税，单位:分
        req.setTax(tax.multiply(Dollar2CentUnit));
        // 邮费，单位:分
        req.setFees(shippingFees.multiply(Dollar2CentUnit));
        req.setCustomerId(paymentCustomerId);
        req.setPaymentType(paymentType);
        req.setPaymentMethodId(paymentMethodId);
        req.setSellerAccountId(paymentSellerId);
        req.setToken(token);

        String invoiceId = paymentLambdaFunctions.createInvoice(req);
        log.info("支付成功 invoiceId: {}", invoiceId);
        return invoiceId;
    }


    private void checkParameter(PaymentParam param) {

        // 检查支付方式
        paymentCheckUtils.checkPaymentType(param.getPaymentType());

        // 检查交易方式
        paymentCheckUtils.checkShippingMethod(param.getShippingMethod());

        // 检查收货地址
        paymentCheckUtils.checkDeliveryAddress(param.getDeliveryAddressId());

        // 检查账单地址
        if (BooleanUtils.isNotTrue(param.getIsSameAsDeliveryAddress())) {
            paymentCheckUtils.checkBillingAddress(param.getBillingAddressId());
        }

        // 检查类型
        paymentCheckUtils.checkTarget(param.getTarget());

        // 检查银行卡信息
        if (param.getPaymentType().equals(PaymentTypeEnum.card.name())) {
            paymentCheckUtils.checkCardInfo(param.getPaymentCardId());
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


}
