package com.etrade.puggo.service.goods.trade.pojo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author zhenyu
 * @version 1.0
 * @description: 更新交易参数
 * @date 2024/2/5 16:33
 */
@Data
public class UpdateTradeParam extends SaveTradeParam {

    private BigDecimal subtotal;

    private BigDecimal tax;

    private BigDecimal otherFees;

    private String invoiceId;

    private Long tradeId;

}
