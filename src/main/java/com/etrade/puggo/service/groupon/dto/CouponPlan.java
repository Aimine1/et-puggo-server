package com.etrade.puggo.service.groupon.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author niuzhenyu
 * @description : 团购券方案
 * @date 2023/6/13 22:20
 **/
@Data
@ApiModel("团购券方案")
public class CouponPlan {

    @ApiModelProperty("团购券id")
    private Long grouponId;

    @ApiModelProperty("方案id")
    private Integer planId;

    @NotNull
    @ApiModelProperty("使用声明")
    private String text;

    @NotNull
    @ApiModelProperty(value = "现价")
    private BigDecimal price;

    @NotNull
    @ApiModelProperty(value = "价格的区间, EQ=; >=GE; <=LE; EQ等于现价，GE现价起步，LE低于现价")
    private String priceInterval;

}
