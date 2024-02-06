package com.etrade.puggo.service.payment;

import com.etrade.puggo.common.enums.*;
import com.etrade.puggo.common.exception.PaymentException;
import com.etrade.puggo.service.account.UserAccountService;
import com.etrade.puggo.third.aws.PaymentLambdaFunctions;
import com.etrade.puggo.third.aws.pojo.CreateInvoiceReq;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @author zhenyu
 * @version 1.0
 * @description: 支付参数检查工具类
 * @date 2024/2/5 14:19
 */
@Slf4j
@Component
public class PaymentUtils {

    private static final BigDecimal Dollar2CentUnit = new BigDecimal(100);

    @Resource
    private CustomerAddressService customerAddressService;

    @Resource
    private CustomerCardService customerCardService;

    @Resource
    private UserAccountService userAccountService;

    @Resource
    private PaymentLambdaFunctions paymentLambdaFunctions;


    public void checkPaymentType(String paymentType) {
        if (!PaymentTypeEnum.isValid(paymentType)) {
            log.error("支付失败，支付类型异常 paymentType={}", paymentType);
            throw new PaymentException(LangErrorEnum.INVALID_SHIPPING_METHOD.lang());
        }
    }


    public void checkShippingMethod(Integer shippingMethod) {
        if (!ShippingMethodEnum.isValid(shippingMethod)) {
            log.error("支付失败，货品交易方式异常 shippingMethod={}", shippingMethod);
            throw new PaymentException(LangErrorEnum.INVALID_PAYMENT_TYPE.lang());
        }
    }


    public void checkDeliveryAddress(Integer addressId) {
        if (!customerAddressService.check(AddressTypeEnum.delivery, addressId)) {
            log.error("支付失败，未知的收货地址 addressId={}", addressId);
            throw new PaymentException(LangErrorEnum.INVALID_DELIVERY_ADDRESS.lang());
        }
    }


    public void checkBillingAddress(Integer addressId) {
        if (!customerAddressService.check(AddressTypeEnum.billing, addressId)) {
            log.error("支付失败，未知的账单地址 addressId={}", addressId);
            throw new PaymentException(LangErrorEnum.INVALID_BILLING_ADDRESS.lang());
        }
    }


    public void checkTarget(String target) {
        if (!PaymentTargetEnum.isValid(target)) {
            log.error("支付失败，货品交易方式异常 target={}", target);
            throw new PaymentException(LangErrorEnum.INVALID_TARGET.lang());
        }
    }


    public void checkCardInfo(Integer cardId) {
        if (null == customerCardService.getCardId(cardId)) {
            throw new PaymentException(LangErrorEnum.INVALID_CARD.lang());
        }
    }


    public String getPaymentCustomerId(Long customerId) {
        String paymentCustomerId = userAccountService.getPaymentCustomerId(customerId);

        if (StringUtils.isBlank(paymentCustomerId)) {
            log.error("支付失败，paymentCustomerId is null，customerId={}", customerId);
            throw new PaymentException(LangErrorEnum.INVALID_PAYMENT_CUSTOMER.lang());
        }

        return paymentCustomerId;
    }


    public String getPaymentSellerId(Long sellerId) {
        String paymentSellerId = userAccountService.getPaymentSellerId(sellerId);

        if (StringUtils.isBlank(paymentSellerId)) {
            log.error("支付失败，paymentSellerId is null，sellerId={}", sellerId);
            throw new PaymentException(LangErrorEnum.INVALID_PAYMENT_SELLER.lang());
        }

        return paymentSellerId;
    }


    public String execute(BigDecimal amount, BigDecimal tax, BigDecimal shippingFees,
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

}
