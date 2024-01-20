package com.etrade.puggo.service.payment.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author zhenyu
 * @version 1.0
 * @description: 收货地址/用户信息
 * @date 2024/1/18 14:48
 */
@Data
public class CustomerAddressDO {

    @NotNull(message = "address type is empty")
    @NotBlank(message = "address type is empty")
    @ApiModelProperty("收货地址传delivery 账单地址传billing")
    private String type;

    @NotNull(message = "title is empty")
    @NotBlank(message = "title is empty")
    @Length(max = 100)
    @ApiModelProperty("标题，用于区分不同账单")
    protected String title;

    @NotNull(message = "firstName is empty")
    @NotBlank(message = "firstName is empty")
    @Length(max = 50)
    @ApiModelProperty("名字")
    protected String firstName;

    @NotNull(message = "lastName is empty")
    @NotBlank(message = "lastName is empty")
    @Length(max = 50)
    @ApiModelProperty("姓氏")
    protected String lastName;

    @NotNull(message = "phoneNumber is empty")
    @NotBlank(message = "phoneNumber is empty")
    @Length(max = 50)
    @ApiModelProperty("电话号码")
    protected String phoneNumber;

    @NotNull(message = "addressLine is empty")
    @NotBlank(message = "addressLine is empty")
    @Length(max = 255)
    @ApiModelProperty("详细地址1")
    protected String addressLine1;

    @Length(max = 255)
    @ApiModelProperty("详细地址2")
    protected String addressLine2;

    @NotNull(message = "postCode is empty")
    @NotBlank(message = "postCode is empty")
    @Length(max = 20)
    @ApiModelProperty("邮编")
    protected String postCode;

    @NotNull(message = "city is empty")
    @NotBlank(message = "city is empty")
    @Length(max = 50)
    @ApiModelProperty("所在城市")
    protected String city;

    @NotNull(message = "state is empty")
    @NotBlank(message = "state is empty")
    @Length(max = 50)
    @ApiModelProperty("所在州/省")
    protected String state;

    @NotNull(message = "country is empty")
    @NotBlank(message = "country is empty")
    @Length(max = 50)
    @ApiModelProperty("所在国家")
    protected String country;

    @ApiModelProperty(value = "是否是默认地址", example = "true")
    protected Boolean isDefault;

}
