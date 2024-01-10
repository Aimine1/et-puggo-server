package com.etrade.puggo.service.goods.opt;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author niuzhenyu
 * @description : 商品分享链接
 * @date 2023/11/21 22:41
 **/
@Data
@ApiModel("商品分享链接VO")
@AllArgsConstructor
@NoArgsConstructor
public class GoodsShareLinkVO {

    @ApiModelProperty("商品id")
    private Long goodsId;

    @ApiModelProperty("商品分享链接，包含商品ID解密信息")
    private String shareLink;

}
