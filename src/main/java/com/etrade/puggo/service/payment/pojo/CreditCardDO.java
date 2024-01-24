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

    @NotNull(message = "cardNumber is empty")
    @NotBlank(message = "cardNumber is empty")
    @Length(min = 10, max = 20, message = "invalid cardNumber")
    @ApiModelProperty(value = "卡号", example = "4242424242424242", required = true)
    private String cardNumber;

    @NotNull(message = "expireYear is empty")
    @NotBlank(message = "expireYear is empty")
    @Length(min = 2, max = 2, message = "invalid expireYear")
    @ApiModelProperty(value = "失效的年份，固定2位数字", example = "24", required = true)
    private String expireYear;

    @NotNull(message = "expireMonth is empty")
    @NotBlank(message = "expireMonth is empty")
    @Length(min = 2, max = 2, message = "invalid expireMonth")
    @ApiModelProperty(value = "失效的月份，固定2位数字，格式MM", example = "01", required = true)
    private String expireMonth;

    @NotNull(message = "cvc is empty")
    @NotBlank(message = "cvc is empty")
    @Length(min = 3, max = 4, message = "invalid cvc")
    @ApiModelProperty(value = "安全码，一般为3-4位数字", example = "123/1234", required = true)
    private String cvc;

    @ApiModelProperty(value = "是否是默认卡", example = "true")
    private Boolean isDefault;

}
