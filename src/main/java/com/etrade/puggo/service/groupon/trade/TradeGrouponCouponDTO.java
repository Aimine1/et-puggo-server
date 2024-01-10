package com.etrade.puggo.service.groupon.trade;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author niuzhenyu
 * @description : 团购券订单详情
 * @date 2023/6/6 17:19
 **/
@Data
@ApiModel("团购券订单详情")
public class TradeGrouponCouponDTO {

    @ApiModelProperty("订单id")
    private Long tradeId;

    @ApiModelProperty("订单编号")
    private String tradeNo;

}
