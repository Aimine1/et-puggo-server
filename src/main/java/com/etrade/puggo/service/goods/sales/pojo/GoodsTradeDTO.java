package com.etrade.puggo.service.goods.sales.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author niuzhenyu
 * @description : 商品订单
 * @date 2023/6/25 13:26
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoodsTradeDTO {

    private String state;

    private Long goodsId;

    private Long customerId;

    private Long sellerId;

    private String customer;

    private BigDecimal tradingPrice;

    private LocalDateTime tradingTime;

    private Integer shippingMethod;

    private Integer deliveryAddressId;

    private String title;

}
