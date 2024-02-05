package com.etrade.puggo.service.payment;

import com.etrade.puggo.common.enums.*;
import com.etrade.puggo.common.exception.CommonErrorV2;
import com.etrade.puggo.common.exception.ServiceException;
import com.etrade.puggo.dao.goods.GoodsDao;
import com.etrade.puggo.dao.user.UserDao;
import com.etrade.puggo.db.tables.records.GoodsMessageLogsRecord;
import com.etrade.puggo.db.tables.records.GoodsRecord;
import com.etrade.puggo.service.BaseService;
import com.etrade.puggo.service.account.UserAccountService;
import com.etrade.puggo.service.account.pojo.UserInfoVO;
import com.etrade.puggo.service.goods.message.GoodsMessageService;
import com.etrade.puggo.service.goods.trade.GoodsTradeService;
import com.etrade.puggo.service.goods.trade.pojo.MyTradeVO;
import com.etrade.puggo.service.payment.pojo.PaymentParam;
import com.etrade.puggo.service.setting.SettingService;
import com.etrade.puggo.third.aws.PaymentLambdaFunctions;
import com.etrade.puggo.third.aws.pojo.CreateInvoiceReq;
import com.etrade.puggo.third.aws.pojo.PaymentMethodDTO;
import com.etrade.puggo.third.aws.pojo.SellerAccountDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @Resource
    private CustomerAddressService customerAddressService;

    @Resource
    private PaymentLambdaFunctions paymentLambdaFunctions;

    @Resource
    private GoodsMessageService goodsMessageService;

    @Resource
    private SettingService settingService;

    @Resource
    private UserAccountService userAccountService;

    @Resource
    private GoodsTradeService goodsTradeService;

    @Resource
    private UserDao userDao;

    @Resource
    private GoodsDao goodsDao;


    private static final BigDecimal Dollar2CentUnit = new BigDecimal(100);


    @Transactional(rollbackFor = Throwable.class)
    public String pay(PaymentParam param) {

        // 参数验证
        checkParameter(param);

        // 发起支付
        if (PaymentTargetEnum.product.name().equals(param.getTarget())) {
            return payForProduct(param);
        } else {
            return payForAI(param);
        }

        // 后续步骤 TODO
    }


    private String payForProduct(PaymentParam param) {

        MyTradeVO oneTrade = goodsTradeService.getOne(param.getTradeId());

        if (oneTrade == null) {
            throw new ServiceException(LangErrorEnum.INVALID_TRADE.lang());
        }

        // 商品总金额
        BigDecimal totalAmount;

        // 检查买家与卖家协商一致的记录，否则是非法的支付
        GoodsMessageLogsRecord msgRecord = goodsMessageService.getPaymentPendingMsgRecord(
                oneTrade.getCustomerId(), oneTrade.getSellerId(), oneTrade.getGoodsId());

        if (msgRecord == null) {
            // 如果没有聊天协商记录，说明是直接付款
            GoodsRecord goodsRecord = goodsDao.findOne(oneTrade.getGoodsId());
            totalAmount = goodsRecord.getRealPrice();
        } else {
            totalAmount = msgRecord.getBuyerPrice();
        }

        String paymentCustomerId = userAccountService.getPaymentCustomerId(oneTrade.getCustomerId());

        if (StringUtils.isBlank(paymentCustomerId)) {
            log.error("支付失败，paymentCustomerId is null，customerId={}", oneTrade.getSellerId());
            throw new ServiceException(LangErrorEnum.PAYMENT_FAILED.lang());
        }

        String paymentSellerId = userAccountService.getPaymentSellerId(oneTrade.getSellerId());

        if (StringUtils.isBlank(paymentSellerId)) {
            log.error("支付失败，paymentSellerId is null，sellerId={}", oneTrade.getSellerId());
            throw new ServiceException(LangErrorEnum.PAYMENT_FAILED.lang());
        }

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

        return execute(
                amount.setScale(0, RoundingMode.UP),
                tax.setScale(0, RoundingMode.UP),
                shippingFees,
                paymentCustomerId,
                paymentSellerId,
                param.getPaymentType(),
                param.getPaymentMethodId(),
                param.getToken()
        );
    }


    private String payForAI(PaymentParam param) {

//        String paymentCustomerId = userAccountService.getPaymentCustomerId(param.getCustomerId());
//
//        if (StringUtils.isBlank(paymentCustomerId)) {
//            log.error("支付失败，paymentCustomerId is null，customerId={}", param.getCustomerId());
//            throw new ServiceException(LangErrorEnum.PAYMENT_FAILED.lang());
//        }

        BigDecimal amount = BigDecimal.ZERO;

        return execute(amount, BigDecimal.ZERO, BigDecimal.ZERO, null, null, param.getPaymentType(),
                param.getPaymentMethodId(), param.getToken());
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

        if (!PaymentTypeEnum.isValid(param.getPaymentType())) {
            log.error("支付失败，支付类型异常 paymentType={}", param.getPaymentType());
            throw new ServiceException(LangErrorEnum.INVALID_SHIPPING_METHOD.lang());
        }

        if (!ShippingMethodEnum.isValid(param.getShippingMethod())) {
            log.error("支付失败，货品交易方式异常 shippingMethod={}", param.getShippingMethod());
            throw new ServiceException(LangErrorEnum.INVALID_PAYMENT_TYPE.lang());
        }

        // 检查收货地址
        if (!customerAddressService.check(AddressTypeEnum.delivery, param.getDeliveryAddressId())) {
            log.error("支付失败，未知的收货地址 addressId={}", param.getDeliveryAddressId());
            throw new ServiceException(LangErrorEnum.INVALID_DELIVERY_ADDRESS.lang());
        }

        // 检查账单地址
        if (!param.isSameAsDeliveryAddress()) {
            if (!customerAddressService.check(AddressTypeEnum.billing, param.getBillingAddressId())) {
                log.error("支付失败，未知的账单地址 addressId={}", param.getBillingAddressId());
                throw new ServiceException(LangErrorEnum.INVALID_BILLING_ADDRESS.lang());
            }
        }

        // 检查支付目的
        if (!PaymentTargetEnum.isValid(param.getTarget())) {
            log.error("支付失败，货品交易方式异常 target={}", param.getTarget());
            throw new ServiceException(LangErrorEnum.INVALID_TARGET.lang());
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


    public static boolean isValidPercentage(BigDecimal percentage) {
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
