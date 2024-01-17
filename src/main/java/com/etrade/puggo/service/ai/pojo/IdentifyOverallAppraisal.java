package com.etrade.puggo.service.ai.pojo;

import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import lombok.Data;

/**
 * @author niuzhenyu
 * @description : 整包鉴定结果
 * @date 2023/9/12 22:48
 **/
@Data
public class IdentifyOverallAppraisal {

    @ApiModelProperty("整鉴记录id")
    private String oaid;

    @ApiModelProperty("整体鉴定真假 1-鉴定为真 2-鉴定为假 3-无法鉴定")
    private Byte genuine;

    @ApiModelProperty("整体鉴定分数")
    private BigDecimal grade;

    @ApiModelProperty("整体鉴定报告描述")
    private String description;

    @ApiModelProperty("鉴定状态")
    private String state;

    @ApiModelProperty("鉴定报告")
    private String reportUrl;

}
