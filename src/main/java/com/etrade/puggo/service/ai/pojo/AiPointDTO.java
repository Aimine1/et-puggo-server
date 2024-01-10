package com.etrade.puggo.service.ai.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author niuzhenyu
 * @description : AI鉴定点查询
 * @date 2023/9/10 20:02
 **/
@Data
@ApiModel("AI鉴定-鉴定点")
public class AiPointDTO {

    @ApiModelProperty("鉴定点id")
    private Integer id;

    @ApiModelProperty("鉴定点名称")
    private String pointName;

    @ApiModelProperty("品类id")
    private Integer kindId;

    @ApiModelProperty("品牌id")
    private Integer brandId;

    @ApiModelProperty("系列id")
    private Integer seriesId;

    @ApiModelProperty("鉴定点描述")
    private String description;

    @ApiModelProperty("示例图")
    private String exampleImg;

    @ApiModelProperty("1-鉴定点必传 2-鉴定点选传")
    private Byte must;

    @ApiModelProperty("是否重要:0-不重要 1-重要")
    private Byte important;

    @ApiModelProperty("简笔画小图")
    private String stickFigureUrl;

    @ApiModelProperty("简笔画大图")
    private String bigStickFigureUrl;

    @JsonIgnore
    @ApiModelProperty("关联鉴定点的品类/品牌/系列id")
    private Integer categoryId;
}
