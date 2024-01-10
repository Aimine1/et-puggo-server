package com.etrade.puggo.service.goods.opt;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author niuzhenyu
 * @description : 商品分享链接
 * @date 2023/11/21 22:41
 **/
@Data
@ApiModel("解密商品分享链")
public class DecryptGoodsShareLinkParam {

    @NotBlank(message = "shareLink is blank")
    @NotNull(message = "shareLink is null")
    @ApiModelProperty("商品分享链接")
    private String shareLink;

}
