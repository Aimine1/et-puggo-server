package com.etrade.puggo.service.payment.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author zhenyu
 * @version 1.0
 * @description: 收货地址/用户信息
 * @date 2024/1/18 14:48
 */
@Data
public class ShippingAddressParam {

    @NotNull
    @NotBlank
    @ApiModelProperty("标题")
    protected String title;

    @NotNull
    @NotBlank
    @ApiModelProperty("名字")
    protected String firstName;

    @NotNull
    @NotBlank
    @ApiModelProperty("姓氏")
    protected String lastName;

    @NotNull
    @NotBlank
    @ApiModelProperty("电话号码")
    protected String phoneNumber;

    @NotNull
    @NotBlank
    @ApiModelProperty("详细地址1")
    protected String addressLine1;

    @NotNull
    @NotBlank
    @ApiModelProperty("详细地址2")
    protected String addressLine2;

    @NotNull
    @NotBlank
    @ApiModelProperty("邮编")
    protected String postCode;

    @NotNull
    @NotBlank
    @ApiModelProperty("所在城市")
    protected String city;

    @NotNull
    @NotBlank
    @ApiModelProperty("所在州/省")
    protected String state;

    @NotNull
    @NotBlank
    @ApiModelProperty("所在国家")
    protected String country;

}
