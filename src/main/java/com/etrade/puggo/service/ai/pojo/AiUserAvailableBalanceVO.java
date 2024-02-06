package com.etrade.puggo.service.ai.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhenyu
 * @version 1.0
 * @description: 用户AI鉴定额度
 * @date 2024/2/6 15:25
 */
@Data
public class AiUserAvailableBalanceVO {

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("品类id")
    private Integer kindId;

    @ApiModelProperty("品类名称")
    private String kindName;

    @ApiModelProperty("已用次数")
    private Integer usedTimes;

    @ApiModelProperty("剩余可用次数")
    private Integer availableTimes;

}
