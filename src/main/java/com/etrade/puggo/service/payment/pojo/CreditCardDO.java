package com.etrade.puggo.service.payment.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author zhenyu
 * @version 1.0
 * @description: 信用卡信息
 * @date 2024/1/18 15:04
 */
@Data
public class CreditCardDO {

    @Length(max = 100)
    @ApiModelProperty(value = "标题，用于区分不同的信用卡")
    private String title;

    @NotNull
    @NotBlank
    @Length(max = 30)
    @ApiModelProperty(value = "卡号", example = "4242424242424242", required = true)
    private String cardNumber;

    @NotNull
    @NotBlank
    @Length(max = 2)
    @ApiModelProperty(value = "失效的年份，固定2位数字", example = "24", required = true)
    private String expireYear;

    @NotNull
    @NotBlank
    @Length(max = 2)
    @ApiModelProperty(value = "失效的月份，固定2位数字，格式MM", example = "01", required = true)
    private String expireMonth;

    @NotNull
    @NotBlank
    @Length(max = 4)
    @ApiModelProperty(value = "安全码，一般为3-4位数字", example = "123/1234", required = true)
    private String cvc;

    @NotNull
    @NotBlank
    @Length(max = 20)
    @ApiModelProperty(value = "信用卡品牌，比如：Visa、MasterCard、American Express、Discover、JCB (Japan Credit Bureau)、Diners Club International等", example = "Visa", required = true)
    private String brand;

    @ApiModelProperty(value = "是否是默认卡", example = "true")
    private Boolean isDefault;

    @NotNull
    @NotBlank
    @ApiModelProperty(value = "卡类型：借记卡传debit，信用卡传credit", example = "credit", required = true)
    private String type;

}
