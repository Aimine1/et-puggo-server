package com.etrade.puggo.service.goods.comment;

import com.etrade.puggo.third.aws.S3PutObjectResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.List;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 * @author niuzhenyu
 * @description : 对商品评价参数
 * @date 2023/6/25 17:08
 **/
@Data
@ApiModel("评价商品参数")
public class CommentGoodsParam {

    @NotNull(message = "goodsId is Null")
    @ApiModelProperty(value = "商品id", required = true)
    private Long goodsId;

    @NotNull(message = "rate is Null")
    @Min(value = 1L, message = "评分最低1.0, 最高5.0")
    @Max(value = 5L, message = "评分最低1.0, 最高5.0")
    @ApiModelProperty(value = "评分，最低1.0, 最高5.0，半颗星表示0.5", required = true)
    private BigDecimal rate;

    @NotNull(message = "评论内容不能为空")
    @NotBlank(message = "评论内容不能为空")
    @ApiModelProperty(value = "评论内容", required = true)
    private String comment;

    @NotNull(message = "type is Null")
    @ApiModelProperty(value = "评论类型：1评论买家 2评论卖家 3评论私讯", required = true)
    private Byte type;

    @NotNull(message = "toUserId is Null")
    @ApiModelProperty(value = "评论目标用户id", required = true)
    private Long toUserId;

    @Size(max = 5, message = "最多允许上传5张")
    @ApiModelProperty(value = "上传的图片, 非必传字段", required = false)
    private List<S3PutObjectResult> pictureList;

}
