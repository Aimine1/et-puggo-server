package com.etrade.puggo.third.im.pojo;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author niuzhenyu
 * @description : 系统消息
 * @date 2023/6/27 11:36
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MsgNewsDO implements Serializable {

    @ApiModelProperty("消息id")
    protected Long id;

    @ApiModelProperty("消息内容")
    protected String attach;

    @ApiModelProperty("推送文案")
    protected String pushcontent;

    @ApiModelProperty("手机设备厂商设置")
    protected String payload;

    @ApiModelProperty("发送消息用户IM账号")
    protected String fromAccount;

    @ApiModelProperty("接收消息用户IM账号")
    protected String toAccount;

    @ApiModelProperty("消息发送状态")
    protected Byte state;

}
