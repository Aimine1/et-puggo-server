package com.etrade.puggo.service.goods.message;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author niuzhenyu
 * @description : 买家发送商品回调入参
 * @date 2023/7/11 18:28
 **/
@Data
@ApiModel("买家回调入参")
public class BuyerSendGoodsCallbackParam {

    @NotNull(message = "卖家用户id必须")
    @ApiModelProperty(value = "卖家用户id", required = true)
    private Long sellerId;

    @NotNull(message = "商品id必须")
    @ApiModelProperty(value = "商品", required = true)
    private Long goodsId;

}
