package com.etrade.puggo.service.ai.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author niuzhenyu
 * @description : 子系类
 * @date 2023/9/10 19:35
 **/
@Data
@ApiModel("AI鉴定-品牌子系类")
public class AiSeriesDTO {

    @ApiModelProperty("系列id")
    private Integer id;

    @ApiModelProperty("系列名称")
    private String name;

    @ApiModelProperty("示例图")
    private String exampleImg;

    @ApiModelProperty("对应的品牌id")
    private Integer brandId;

}
