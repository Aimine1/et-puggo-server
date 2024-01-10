package com.etrade.puggo.service.groupon;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import lombok.Data;

/**
 * @author niuzhenyu
 * @description : 团购券列表参数
 * @date 2023/6/6 15:39
 **/
@Data
@ApiModel("团购券列表")
public class GrouponCouponDTO {

    @ApiModelProperty(value = "团购券id，用于查看详情")
    protected Long grouponId;

    @ApiModelProperty(value = "团购券title")
    protected String title;

    @ApiModelProperty(value = "原价/划线价")
    protected BigDecimal originalPrice;

    @ApiModelProperty(value = "现价")
    protected BigDecimal realPrice;

    @ApiModelProperty(value = "现价的区间, EQ=; >=GE; <=LE; EQ意思是不同套餐价钱一样，GE现价起步，LE低于现价")
    protected String realPriceInterval;

    @ApiModelProperty(value = "货币种类")
    protected String moneyKind;

    @ApiModelProperty(value = "团购券状态")
    protected String state;

    @ApiModelProperty("团购券主图")
    protected String mainPic;

    @ApiModelProperty(value = "浏览次数")
    protected Integer browseNumber;

    @ApiModelProperty(value = "收藏次数")
    protected Integer likeNumber;
}
