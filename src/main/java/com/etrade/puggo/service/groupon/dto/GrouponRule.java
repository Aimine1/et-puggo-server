package com.etrade.puggo.service.groupon.dto;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author niuzhenyu
 * @description : 团购券规则
 * @date 2023/6/13 22:24
 **/
@Data
public class GrouponRule {

    @NotNull
    @ApiModelProperty("最小回复时间")
    private Byte minReplyHours;

    @NotNull
    @ApiModelProperty("是否允许取消")
    private Boolean isAllowCancel;

    @ApiModelProperty("限制订购数量")
    private Integer limitNumber;

    @NotBlank
    @ApiModelProperty("服务有效期区间: BETWEEN; >=GE; <=LE. BETWEEN意思为有效期在起止时间之内（包括起止时间），GE意思为从serviceTimeStart开始，LE意思为截止为serviceTimeEnd")
    private String serviceTimeInterval;

    @NotBlank
    @ApiModelProperty("有效期开始时间")
    private String serviceTimeStart;

    @ApiModelProperty("有效期截止时间")
    private String serviceTimeEnd;

    @NotBlank
    @ApiModelProperty("有效期单位：WEEK周 DATE日期")
    private String serviceTimeUnit;

    @ApiModelProperty("有效期期天数")
    private Integer effectiveDays;

}
