package com.etrade.puggo.service.message;

import com.etrade.puggo.service.goods.sales.LaunchUserDO;
import com.etrade.puggo.third.im.pojo.SendNewsParam;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * @author niuzhenyu
 * @description : 用户消息
 * @date 2023/6/28 11:43
 **/
@Data
public class UserNewsVO extends SendNewsParam {

    @ApiModelProperty("消息发送者用户信息")
    private LaunchUserDO fromUserInfo;

    @ApiModelProperty("是否是未读消息")
    private Byte isUnread;

    @ApiModelProperty("推送时间")
    private LocalDateTime pushTime;

    @ApiModelProperty("消息id")
    private Long id;

}
