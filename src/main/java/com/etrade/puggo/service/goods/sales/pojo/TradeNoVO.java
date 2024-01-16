package com.etrade.puggo.service.goods.sales.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author niuzhenyu
 * @description : 商品订单编号
 * @date 2023/6/25 13:43
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TradeNoVO {

    private Long tradeId;

    private String tradeNo;

}
