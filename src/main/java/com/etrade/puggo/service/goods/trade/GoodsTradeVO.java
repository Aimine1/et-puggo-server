package com.etrade.puggo.service.goods.trade;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * @author niuzhenyu
 * @description : 商品成交
 * @date 2023/6/30 10:19
 **/
@Data
public class GoodsTradeVO {

    private Long goodsId;

    private String goodsTitle;

    private Boolean isFree;

    private String moneyKind;

    private Long tradeId;

    private String tradeNo;

    private BigDecimal tradingPrice;

    private LocalDateTime tradingTime;

    private String state;

    private String customer;

    private String className;

}
