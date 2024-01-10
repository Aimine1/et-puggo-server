package com.etrade.puggo.service.ai.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author niuzhenyu
 * @description : 单点鉴定参数
 * @date 2023/9/12 20:38
 **/
@Data
@ApiModel("单点鉴定参数")
public class IdentifySingleParam {

    @NotNull(message = "pointId is null")
    @ApiModelProperty("鉴定点id")
    private Integer pointId;

    @NotNull(message = "imageUrl is null")
    @NotBlank(message = "imageUrl is empty")
    @ApiModelProperty("上传的鉴定图片")
    private String imageUrl;

    @ApiModelProperty("本次鉴定操作id，如果是第一次传空，第一次之后单点鉴定该字段必传。operationId相同表示是同一物品同一次的单点鉴定操作。")
    private String operationId;

}
