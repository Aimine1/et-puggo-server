package com.etrade.puggo.service.goods.sales.pojo;

import com.etrade.puggo.common.page.PageParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author niuzhenyu
 * @description : 用户的商品列表
 * @date 2023/10/19 21:03
 **/
@Data
public class UserGoodsListParam extends PageParam {

    @ApiModelProperty("商品title，模糊搜索")
    private String title;

}
