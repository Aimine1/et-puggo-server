package com.etrade.puggo.service.payment;

import com.etrade.puggo.common.enums.PaymentTypeEnum;
import com.etrade.puggo.common.enums.ShippingMethodEnum;
import com.etrade.puggo.service.BaseService;
import com.etrade.puggo.service.goods.sales.GoodsSimpleService;
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

    public void pay(PaymentParam param) {

        BigDecimal amount = param.getAmount();
        String paymentType = param.getPaymentType();
        Integer shippingMethod = param.getShippingMethod();

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            log.error("支付失败，金额异常 amount={}", amount);
            return;
        }

        if (!PaymentTypeEnum.isValid(paymentType)) {
            log.error("支付失败，支付类型异常 paymentType={}", paymentType);
            return;
        }

        if (!ShippingMethodEnum.isValid(shippingMethod)) {
            log.error("支付失败，货品交易方式异常 shippingMethod={}", shippingMethod);
            return;
        }

        // 检查收货地址

        // 检查账单地址

        // 检查商品信息


        // 生成pending状态订单
        goodsTradeService.saveTrade(userId(), param.getGoodsId(), amount);

    }


}
