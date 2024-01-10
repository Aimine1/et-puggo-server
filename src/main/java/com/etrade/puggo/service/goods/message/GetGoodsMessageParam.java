package com.etrade.puggo.service.goods.message;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author niuzhenyu
 * @description : 获取商品会话日志入参
 * @date 2023/7/11 18:25
 **/
@Data
@ApiModel("获取商品会话日志入参")
public class GetGoodsMessageParam {

    @NotNull(message = "买家用户id必须")
    @ApiModelProperty(value = "买家用户id", required = true)
    private Long buyerId;

    @NotNull(message = "卖家用户id必须")
    @ApiModelProperty(value = "卖家用户id", required = true)
    private Long sellerId;

    @ApiModelProperty("商品id，如果为null则返回最后一条会话日志")
    private Long goodsId;

}
