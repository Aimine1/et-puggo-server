package com.etrade.puggo.service.ai.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author niuzhenyu
 * @description : 整包鉴定参数
 * @date 2023/9/12 22:23
 **/
@Data
@ApiModel("整包鉴定参数")
public class IdentifyOverallParam {

    @NotNull(message = "kindId is null")
    @ApiModelProperty("品类id")
    private Integer kindId;

    @NotNull(message = "brandId is null")
    @ApiModelProperty("品牌id")
    private Integer brandId;

    @NotNull(message = "seriesId is null")
    @ApiModelProperty("系列id，如果品牌下面没有系列默认填0")
    private Integer seriesId;

    @NotNull(message = "operationId is null")
    @ApiModelProperty("本次鉴定操作id，如果是第一次传空，第一次之后单点鉴定该字段必传。operationId相同表示是同一物品同一次的单点鉴定操作。")
    private String operationId;

}
