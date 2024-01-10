package com.etrade.puggo.service.groupon.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author niuzhenyu
 * @description : 服务有效期
 * @date 2023/6/13 22:21
 **/
@Data
@Builder
@ApiModel("服务有效期")
@AllArgsConstructor
@NoArgsConstructor
public class ServiceTime {

    @ApiModelProperty("服务有效期区间: BETWEEN; >=GE; <=LE. BETWEEN意思为有效期在起止时间之内（包括起止时间），GE意思为从serviceTimeStart开始，LE意思为截止为serviceTimeEnd")
    private String serviceTimeInterval;

    @ApiModelProperty("有效期开始时间")
    private String serviceTimeStart;

    @ApiModelProperty("有效期截止时间")
    private String serviceTimeEnd;

    @ApiModelProperty("有效期单位：WEEK周 DATE日期")
    private String serviceTimeUnit;

    @ApiModelProperty("有效期期天数")
    private String effectiveDays;

}
