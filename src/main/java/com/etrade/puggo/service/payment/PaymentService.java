package com.etrade.puggo.service.payment;

import com.etrade.puggo.common.enums.AddressTypeEnum;
import com.etrade.puggo.common.enums.LangErrorEnum;
import com.etrade.puggo.common.enums.PaymentTypeEnum;
import com.etrade.puggo.common.enums.ShippingMethodEnum;
import com.etrade.puggo.common.exception.ServiceException;
import com.etrade.puggo.service.BaseService;
import com.etrade.puggo.service.goods.sales.GoodsSimpleService;
import com.etrade.puggo.service.goods.sales.pojo.GoodsSimpleVO;
import com.etrade.puggo.service.goods.trade.GoodsTradeService;
import com.etrade.puggo.service.payment.pojo.PaymentParam;
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
    private GoodsTradeService goodsTradeService;

    @Resource
    private GoodsSimpleService goodsSimpleService;

    @Resource
    private CustomerAddressService customerAddressService;


    public void pay(PaymentParam param) {

        BigDecimal amount = param.getAmount();
        String paymentType = param.getPaymentType();
        Integer shippingMethod = param.getShippingMethod();
        boolean isSameAsDeliveryAddress = param.isSameAsDeliveryAddress();
        Long goodsId = param.getGoodsId();

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            log.error("支付失败，金额异常 amount={}", amount);
            throw new ServiceException(LangErrorEnum.INVALID_AMOUNT.lang());
        }

        if (!PaymentTypeEnum.isValid(paymentType)) {
            log.error("支付失败，支付类型异常 paymentType={}", paymentType);
            throw new ServiceException(LangErrorEnum.UNKNOWN_SHIPPING_METHOD.lang());
        }

        if (!ShippingMethodEnum.isValid(shippingMethod)) {
            log.error("支付失败，货品交易方式异常 shippingMethod={}", shippingMethod);
            throw new ServiceException(LangErrorEnum.UNKNOWN_PAYMENT_TYPE.lang());
        }

        // 检查收货地址
        if (!customerAddressService.check(AddressTypeEnum.delivery, param.getDeliveryAddressId())) {
            throw new ServiceException(LangErrorEnum.INVALID_ADDRESS.lang());
        }

        // 检查账单地址
        if (!isSameAsDeliveryAddress) {
            if (!customerAddressService.check(AddressTypeEnum.billing, param.getDeliveryAddressId())) {
                throw new ServiceException(LangErrorEnum.INVALID_ADDRESS.lang());
            }
        }

        // 检查商品信息
        GoodsSimpleVO singleGoods = goodsSimpleService.getSingleGoods(goodsId);
        if (singleGoods == null) {
            log.error("支付失败，商品信息异常 goodsId={}", goodsId);
            throw new ServiceException(LangErrorEnum.INVALID_GOODS.lang());
        }

        // 生成pending状态订单
        goodsTradeService.saveTrade(userId(), goodsId, amount);

    }


}
