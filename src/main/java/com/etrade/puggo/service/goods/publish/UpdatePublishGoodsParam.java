package com.etrade.puggo.service.goods.publish;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author niuzhenyu
 * @description : 重新编辑商品
 * @date 2023/10/19 21:15
 **/
@Data
public class UpdatePublishGoodsParam extends PublishGoodsParam {

    @NotNull(message = "goodsId is null")
    @ApiModelProperty("商品id")
    private Long goodsId;

}
