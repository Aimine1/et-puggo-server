package com.etrade.puggo.third.im.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author niuzhenyu
 * @description : 发送系统消息
 * @date 2023/6/27 17:42
 **/
@Data
@ApiModel("发送系统消息")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SendNewsParam {

    @NotNull
    @NotBlank
    @ApiModelProperty(value = "消息内容", required = true)
    protected String attach;

    @NotNull
    @NotBlank
    @ApiModelProperty(value = "推送文案, 在下拉消息中展示 \"昵称:你的推送文案\"", required = true)
    protected String pushcontent;

    @NotNull
    @ApiModelProperty(value = "消息接收者的用户id", required = true)
    protected Long toUserId;

    @ApiModelProperty("消息发送者的用户id")
    protected Long fromUserId;

    @NotNull
    @ApiModelProperty(value = "商品id", required = true)
    protected Long goodsId;

    @NotNull
    @ApiModelProperty(value = "商品图片", required = true)
    protected String goodsMainPic;

}
