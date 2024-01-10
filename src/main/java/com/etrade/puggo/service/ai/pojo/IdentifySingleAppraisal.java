package com.etrade.puggo.service.ai.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import lombok.Data;

/**
 * @author niuzhenyu
 * @description : 单点鉴定结果
 * @date 2023/9/12 20:50
 **/
@Data
@ApiModel("单鉴点结果")
public class IdentifySingleAppraisal {

    @ApiModelProperty("单鉴定点真假，值为1表示为真，值为0表示假")
    private Byte genuine;

    @ApiModelProperty("单鉴定点分数，默认为0")
    private BigDecimal grade;

    @ApiModelProperty("鉴定点id")
    private Integer pointId;

    @ApiModelProperty("鉴定点名称")
    private String pointName;

    @ApiModelProperty("鉴定结果描述")
    private String description;

    @ApiModelProperty("鉴定记录id")
    private String said;

    @ApiModelProperty("本次鉴定操作id，如果是第一次传空，第一次之后单点鉴定该字段必传。operationId相同表示是同一物品同一次的单点鉴定操作。")
    private String operationId;

}
