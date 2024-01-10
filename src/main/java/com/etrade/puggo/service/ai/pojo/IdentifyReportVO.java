package com.etrade.puggo.service.ai.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author niuzhenyu
 * @description : 鉴定报告结果
 * @date 2023/10/29 16:23
 **/
@Data
@ApiModel("鉴定报告")
public class IdentifyReportVO {

    @ApiModelProperty("品牌名称")
    private String brandName;

    @ApiModelProperty("鉴定编号")
    private String aiIdentifyNo;

    @ApiModelProperty("图片")
    private String imageUrl;

}
