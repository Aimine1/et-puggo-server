package com.etrade.puggo.service.ai.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author niuzhenyu
 * @description : AI鉴定记录
 * @date 2023/10/15 19:30
 **/
@Data
@ApiModel("AI鉴定记录")
public class AIIdentifyRecord {

    @ApiModelProperty("品牌名称")
    private String brandName;

    @ApiModelProperty("系列名称")
    private String seriesName;

    @ApiModelProperty("鉴定编号")
    private String aiIdentifyNo;

    @ApiModelProperty("状态")
    private String state;

    @ApiModelProperty("图片")
    private String imageUrl;
}
