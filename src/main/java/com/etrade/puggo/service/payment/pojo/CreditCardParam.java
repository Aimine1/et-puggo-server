package com.etrade.puggo.service.payment.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author zhenyu
 * @version 1.0
 * @description: 信用卡信息
 * @date 2024/1/18 15:04
 */
@Data
public class CreditCardParam {

    @NotNull
    @NotBlank
    @ApiModelProperty(value = "卡号", example = "4242424242424242")
    protected String cardNumber;

    @NotNull
    @NotBlank
    @ApiModelProperty(value = "到期年份，固定四位数字", example = "2024")
    protected String expireYear;

    @NotNull
    @NotBlank
    @ApiModelProperty(value = "到期年份，固定两位数字", example = "01")
    protected String expireMonth;

    @NotNull
    @NotBlank
    @ApiModelProperty(value = "安全码，一般为三位数字", example = "123")
    protected String cvc;

}
