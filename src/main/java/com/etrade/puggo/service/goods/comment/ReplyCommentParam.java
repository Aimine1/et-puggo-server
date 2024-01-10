package com.etrade.puggo.service.goods.comment;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author niuzhenyu
 * @description : 回复评论
 * @date 2023/6/25 20:23
 **/
@Data
@ApiModel("回复评价参数")
public class ReplyCommentParam {

    @NotNull(message = "lastCommentId is null")
    @ApiModelProperty("上一条评论id")
    private Long lastCommentId;

    @NotNull(message = "评论内容不能为空")
    @NotBlank(message = "评论内容不能为空")
    @ApiModelProperty("评论内容")
    private String comment;

}
