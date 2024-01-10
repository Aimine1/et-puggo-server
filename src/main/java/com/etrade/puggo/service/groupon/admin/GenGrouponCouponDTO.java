package com.etrade.puggo.service.groupon.admin;

import com.etrade.puggo.service.groupon.dto.ApplicableShop;
import com.etrade.puggo.service.groupon.dto.CouponPlan;
import com.etrade.puggo.service.groupon.dto.GrouponRule;
import com.etrade.puggo.service.groupon.dto.S3Picture;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author niuzhenyu
 * @description : 生成/编辑 团购券
 * @date 2023/6/10 21:24
 **/
@Data
public class GenGrouponCouponDTO {

    private Long grouponId;

    @NotNull
    private String state;

    @Valid
    @NotNull
    private GrouponDetail detail;

    @Valid
    @NotNull
    private GrouponRule rule;

    @Valid
    @NotNull
    private GrouponStatement statement;

    @Valid
    @NotNull
    private ApplicableShop shop;

    @Valid
    private List<CouponPlan> plans;


    @Data
    public static class GrouponDetail {

        @ApiModelProperty(value = "状态")
        private String state;

        @NotBlank
        @ApiModelProperty(value = "团购券title")
        private String title;

        @NotNull
        @ApiModelProperty(value = "原价/划线价")
        private BigDecimal originalPrice;

        @NotNull
        @ApiModelProperty(value = "现价")
        private BigDecimal realPrice;

        @NotBlank
        @ApiModelProperty(value = "现价的区间, EQ=; >=GE; <=LE; EQ意思是不同套餐价钱一样，GE现价起步，LE低于现价")
        private String realPriceInterval;

        @NotBlank
        @ApiModelProperty(value = "货币种类")
        private String moneyKind;

        @NotEmpty
        @ApiModelProperty("团购券图片")
        private List<S3Picture> pictureList;

        @NotNull
        @ApiModelProperty(value = "分类")
        private Integer classId;

        @JsonIgnore
        private String classPath;

        @ApiModelProperty("合作的品牌")
        private String brand;
    }


    @Data
    public static class GrouponStatement {

        @ApiModelProperty("商品说明")
        private List<String> descList;

        @ApiModelProperty("使用声明")
        private List<String> statementList;

    }

}
