package com.etrade.puggo.service.ai.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author niuzhenyu
 * @description : TODO品牌
 * @date 2023/9/10 19:35
 **/
@Data
@ApiModel("AI鉴定-品牌")
public class AiBrandDTO {

    @ApiModelProperty("品牌id")
    private Integer id;

    @ApiModelProperty("品牌名称")
    private String name;

    @ApiModelProperty("是否关联鉴定点，值为1则关联，说明该品牌下没有系列，直接查询鉴定点；值为0则没有关联，需要先查询子系列，再查询鉴定点")
    private Byte isRelated;

    @ApiModelProperty("示例图")
    private String exampleImg;

    @ApiModelProperty("对应的品类id")
    private Integer kindId;

}
