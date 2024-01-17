package com.etrade.puggo.service.goods.comment;

import com.etrade.puggo.service.goods.sales.pojo.GoodsSimpleVO;
import com.etrade.puggo.service.goods.sales.pojo.LaunchUserDO;
import com.etrade.puggo.service.groupon.dto.S3Picture;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

/**
 * @author niuzhenyu
 * @description : 评论
 * @date 2023/6/25 21:51
 **/
@Data
@ApiModel("评论")
public class CommentVO {

    @ApiModelProperty("评论id")
    private Long commentId;

    @ApiModelProperty("商品信息")
    private GoodsSimpleVO goods;

    @ApiModelProperty("评论内容")
    private String comment;

    @ApiModelProperty("评论时间")
    private LocalDateTime commentTime;

    @ApiModelProperty("评分")
    private BigDecimal rate;

    @ApiModelProperty("评论者身份")
    private Byte identity;

    @ApiModelProperty("评论者信息")
    private LaunchUserDO commenter;

    @ApiModelProperty("回复的评论信息")
    private ReplyCommentVO replyComment;

    @ApiModelProperty("评论内容中的图片")
    private List<S3Picture> pictureList;

    @JsonIgnore
    private Long goodsId;

    @JsonIgnore
    private Long fromUserId;

    @Data
    public static class ReplyCommentVO {

        @ApiModelProperty("评论者身份")
        private Byte identity;

        @ApiModelProperty("评论信息")
        private String comment;

        @ApiModelProperty("评论时间")
        private LocalDateTime commentTime;

        @JsonIgnore
        private Long lastCommentId;

    }

}
