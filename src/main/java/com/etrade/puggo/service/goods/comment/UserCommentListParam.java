package com.etrade.puggo.service.goods.comment;

import com.etrade.puggo.common.page.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import lombok.Data;

/**
 * @author niuzhenyu
 * @description : 用户评论列表
 * @date 2023/6/25 21:48
 **/
@Data
@ApiModel("用户评论列表")
public class UserCommentListParam extends PageParam {

    @ApiModelProperty("评论者身份: 1买家, 2卖家, 不存默认全部")
    @Min(value = 1L, message = "未知评论者身份，1买家身份 2卖家身份")
    @Max(value = 2L, message = "未知评论者身份，1买家身份 2卖家身份")
    private Byte identity;

}
