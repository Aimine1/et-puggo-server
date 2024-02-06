package com.etrade.puggo.service.goods.trade.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author zhenyu
 * @version 1.0
 * @description: 获取订单接口
 * @date 2024/2/4 14:07
 */
@Data
public class GetTradeParam {

    @ApiModelProperty("买家用户id")
    protected Long customerId;

    @ApiModelProperty("卖家用户id")
    protected Long sellerId;

    @ApiModelProperty("商品id")
    protected Long goodsId;

}
