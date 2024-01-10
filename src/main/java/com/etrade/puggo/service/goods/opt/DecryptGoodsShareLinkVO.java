package com.etrade.puggo.service.goods.opt;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author niuzhenyu
 * @description : 解密商品分享链接VO
 * @date 2023/11/21 22:43
 **/
@Data
@ApiModel("解密商品分享链接VO")
@AllArgsConstructor
@NoArgsConstructor
public class DecryptGoodsShareLinkVO {

    @ApiModelProperty("商品id")
    private Long goodsId;

}
