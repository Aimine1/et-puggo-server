package com.etrade.puggo.service.payment;

import com.etrade.puggo.common.enums.AddressTypeEnum;
import com.etrade.puggo.common.enums.LangErrorEnum;
import com.etrade.puggo.common.enums.PaymentTypeEnum;
import com.etrade.puggo.common.enums.ShippingMethodEnum;
import com.etrade.puggo.common.exception.ServiceException;
import com.etrade.puggo.db.tables.records.GoodsMessageLogsRecord;
import com.etrade.puggo.service.BaseService;
import com.etrade.puggo.service.goods.message.GoodsMessageService;
import com.etrade.puggo.service.payment.pojo.PaymentParam;
import com.etrade.puggo.third.aws.PaymentLambdaFunctions;
import com.etrade.puggo.third.aws.pojo.CreatePaymentIntentReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

    @Resource
    private CustomerAddressService customerAddressService;

    @Resource
    private PaymentLambdaFunctions paymentLambdaFunctions;

    @Resource
    private GoodsMessageService goodsMessageService;


    public void pay(PaymentParam param) {

        BigDecimal amount = param.getAmount();
        String paymentType = param.getPaymentType();
        Integer shippingMethod = param.getShippingMethod();
        boolean isSameAsDeliveryAddress = param.isSameAsDeliveryAddress();
        Long goodsId = param.getGoodsId();
        String paymentMethodId = param.getPaymentMethodId();

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            log.error("支付失败，金额异常 amount={}", amount);
            throw new ServiceException(LangErrorEnum.INVALID_AMOUNT.lang());
        }

        if (!PaymentTypeEnum.isValid(paymentType)) {
            log.error("支付失败，支付类型异常 paymentType={}", paymentType);
            throw new ServiceException(LangErrorEnum.INVALID_SHIPPING_METHOD.lang());
        }

        if (!ShippingMethodEnum.isValid(shippingMethod)) {
            log.error("支付失败，货品交易方式异常 shippingMethod={}", shippingMethod);
            throw new ServiceException(LangErrorEnum.INVALID_PAYMENT_TYPE.lang());
        }

        // 检查收货地址
        if (!customerAddressService.check(AddressTypeEnum.delivery, param.getDeliveryAddressId())) {
            log.error("支付失败，未知的收货地址 addressId={}", param.getDeliveryAddressId());
            throw new ServiceException(LangErrorEnum.INVALID_DELIVERY_ADDRESS.lang());
        }

        // 检查账单地址
        if (!isSameAsDeliveryAddress) {
            if (!customerAddressService.check(AddressTypeEnum.billing, param.getBillingAddressId())) {
                log.error("支付失败，未知的账单地址 addressId={}", param.getBillingAddressId());
                throw new ServiceException(LangErrorEnum.INVALID_BILLING_ADDRESS.lang());
            }
        }

        // 检查买家与卖家协商一致的记录，否则是非法的支付
        GoodsMessageLogsRecord msgRecord = goodsMessageService.getPaymentPendingMsgRecord(param.getCustomerId(),
                param.getSellerId(), goodsId);
        if (msgRecord == null) {
            log.error("支付失败，没有找到协商记录");
            throw new ServiceException(LangErrorEnum.INVALID_PAY.lang());
        }


        // 发起支付
        CreatePaymentIntentReq req = new CreatePaymentIntentReq();
        req.setAmount(amount);
        req.setCustomerId(null);
        req.setPaymentType(paymentType);
        req.setPaymentMethodId(paymentMethodId);
        req.setCurrency(null);
        // paymentLambdaFunctions.createPaymentIntent(req);

    }


}
