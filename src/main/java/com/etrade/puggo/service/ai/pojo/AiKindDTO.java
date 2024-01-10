package com.etrade.puggo.service.ai.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author niuzhenyu
 * @description : 品类
 * @date 2023/9/10 19:35
 **/
@Data
@ApiModel("AI鉴定-品类")
public class AiKindDTO {

    @ApiModelProperty("品类id")
    private Integer id;

    @ApiModelProperty("品类名称")
    private String name;

    @ApiModelProperty("示例图")
    private String exampleImg;

}
