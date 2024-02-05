package com.etrade.puggo.service.payment;

import com.etrade.puggo.common.enums.*;
import com.etrade.puggo.common.exception.PaymentException;
import com.etrade.puggo.service.account.UserAccountService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author zhenyu
 * @version 1.0
 * @description: 支付参数检查工具类
 * @date 2024/2/5 14:19
 */
@Slf4j
@Component
public class PaymentCheckUtils {

    @Resource
    private CustomerAddressService customerAddressService;

    @Resource
    private CustomerCardService customerCardService;

    @Resource
    private UserAccountService userAccountService;


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
            throw new PaymentException(LangErrorEnum.INVALID_PAYMENT_SELLER.lang());
        }

        return paymentCustomerId;
    }


    public String getPaymentSellerId(Long sellerId) {
        String paymentSellerId = userAccountService.getPaymentSellerId(sellerId);

        if (StringUtils.isBlank(paymentSellerId)) {
            log.error("支付失败，paymentSellerId is null，sellerId={}", sellerId);
            throw new PaymentException(LangErrorEnum.PAYMENT_FAILED.lang());
        }

        return paymentSellerId;
    }


}
