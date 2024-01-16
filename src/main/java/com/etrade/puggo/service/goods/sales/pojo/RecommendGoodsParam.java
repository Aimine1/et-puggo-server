package com.etrade.puggo.service.goods.sales.pojo;

import com.etrade.puggo.common.page.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author niuzhenyu
 * @description : 依据商品推荐列表
 * @date 2023/6/25 15:23
 **/
@Data
@ApiModel("依据某商品推荐商品")
public class RecommendGoodsParam extends PageParam {

    @NotNull(message = "goodsId is null")
    @ApiModelProperty("依据的商品")
    private Long goodsId;

}
