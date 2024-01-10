package com.etrade.puggo.service.groupon.trade;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import java.util.List;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author niuzhenyu
 * @description : 订购优惠券参数
 * @date 2023/6/6 17:12
 **/
@Data
@ApiModel("订购优惠券参数")
public class TradeGrouponCouponParam {

    @NotNull(message = "grouponId is null")
    @ApiModelProperty("团购券id")
    private Long grouponId;

    @NotEmpty
    @ApiModelProperty("团购券方案，此处传plan_id")
    private List<Integer> planList;

    @ApiModelProperty("计划使用日期，格式:yyyy-MM-dd")
    @NotNull(message = "useageDate is null")
    private LocalDate useageDate;

    @ApiModelProperty("订购数量")
    @Min(value = 1, message = "number Cannot be less than 1")
    @NotNull(message = "number is null")
    private Integer number;

    @ApiModelProperty("联系人 姓氏")
    @NotNull(message = "contactLastName is null")
    @NotBlank(message = "contactLastName is empty")
    private String contactLastName;

    @ApiModelProperty("联系人 名字")
    @NotNull(message = "contactName is null")
    @NotBlank(message = "contactName is empty")
    private String contactName;

    @ApiModelProperty("联系人 电话")
    @NotNull(message = "contactPhone is null")
    @NotBlank(message = "contactPhone is empty")
    private String contactPhone;

    @ApiModelProperty("联系人 电话国际区号")
    @NotNull(message = "nationalCode is null")
    @Min(value = 1, message = "nationalCode is illegal")
    private Integer nationalCode;

    @ApiModelProperty("联系人 邮箱")
    @Email
    @NotNull(message = "contactEmail is null")
    @NotBlank(message = "contactEmail is empty")
    private String contactEmail;


}
