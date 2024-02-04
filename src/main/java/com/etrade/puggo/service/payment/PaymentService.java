package com.etrade.puggo.service.payment;

import com.etrade.puggo.common.enums.*;
import com.etrade.puggo.common.exception.ServiceException;
import com.etrade.puggo.dao.user.UserDao;
import com.etrade.puggo.db.tables.records.GoodsMessageLogsRecord;
import com.etrade.puggo.service.BaseService;
import com.etrade.puggo.service.account.UserAccountService;
import com.etrade.puggo.service.account.pojo.UserInfoVO;
import com.etrade.puggo.service.goods.message.GoodsMessageService;
import com.etrade.puggo.service.payment.pojo.PaymentParam;
import com.etrade.puggo.service.setting.SettingService;
import com.etrade.puggo.third.aws.PaymentLambdaFunctions;
import com.etrade.puggo.third.aws.pojo.CreateInvoiceReq;
import com.etrade.puggo.third.aws.pojo.SellerAccountDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;

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
    private UserDao userDao;

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
        // 检查买家与卖家协商一致的记录，否则是非法的支付
        GoodsMessageLogsRecord msgRecord = goodsMessageService.getPaymentPendingMsgRecord(
                param.getCustomerId(), param.getSellerId(), param.getGoodsId());

        if (msgRecord == null) {
            log.error("支付失败，没有找到协商记录");
            throw new ServiceException(LangErrorEnum.INVALID_PAY.lang());
        }

        String paymentCustomerId = userAccountService.getPaymentCustomerId(param.getCustomerId());

        if (StringUtils.isBlank(paymentCustomerId)) {
            log.error("支付失败，paymentCustomerId is null，customerId={}", param.getSellerId());
            throw new ServiceException(LangErrorEnum.PAYMENT_FAILED.lang());
        }

        String paymentSellerId = userAccountService.getPaymentSellerId(param.getSellerId());

        if (StringUtils.isBlank(paymentSellerId)) {
            log.error("支付失败，paymentSellerId is null，sellerId={}", param.getSellerId());
            throw new ServiceException(LangErrorEnum.PAYMENT_FAILED.lang());
        }

        // 商品总金额
        BigDecimal totalAmount = msgRecord.getBuyerPrice();

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

        return execute(amount, tax, shippingFees, paymentCustomerId, paymentSellerId, param.getPaymentType(),
                param.getPaymentMethodId(), param.getToken());
    }


    private String payForAI(PaymentParam param) {

        String paymentCustomerId = userAccountService.getPaymentCustomerId(param.getCustomerId());

        if (StringUtils.isBlank(paymentCustomerId)) {
            log.error("支付失败，paymentCustomerId is null，customerId={}", param.getSellerId());
            throw new ServiceException(LangErrorEnum.PAYMENT_FAILED.lang());
        }

        BigDecimal amount = BigDecimal.ZERO;

        return execute(amount, BigDecimal.ZERO, BigDecimal.ZERO, paymentCustomerId, null, param.getPaymentType(),
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

        try {
            String payload = paymentLambdaFunctions.createInvoice(req);
            log.info("支付结果为: {}", payload);
            return payload;
        } catch (Exception e) {
            log.error("支付失败，userId={}，原因={}", userId(), e.getMessage());
        }

        return null;
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


    public String createSellerAccount() {
        // 获取商家邮件
        UserInfoVO userInfo = userDao.findUserInfo(userId());

        String email = userInfo.getEmail();

        // 创建商家支付账号
        SellerAccountDTO sellerAccount = paymentLambdaFunctions.createSellerAccount(email);

        // 保存商家支付账号
        if (sellerAccount != null && sellerAccount.getAccountId() != null) {
            userDao.updatePaymentSellerId(userId(), sellerAccount.getAccountId());
        }

        return sellerAccount != null ? sellerAccount.getAccountLinkURL() : null;
    }

}
