package com.etrade.puggo.service.goods.opt;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author niuzhenyu
 * @description : 修改商品状态
 * @date 2023/10/7 10:13
 **/
@Data
@ApiModel("修改商品状态")
public class UpdateGoodsStateParam {

    @NotNull(message = "goodsId is null")
    @ApiModelProperty(value = "商品id", required = true)
    private Long goodsId;

    @NotNull(message = "state is null")
    @NotBlank(message = "state is null")
    @ApiModelProperty(value = "商品状态，DELETED-已发布到删除，OCCUPY-已发布到预留，SOLD-已发布到已售 PUBLISHED-从其他状态修改为发布状态", required = true)
    private String state;

}
