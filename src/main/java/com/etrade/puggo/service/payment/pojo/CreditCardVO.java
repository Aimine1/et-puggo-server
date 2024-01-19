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
public class CreditCardVO {

    @ApiModelProperty("卡号id")
    private Integer cardId;

    @ApiModelProperty(value = "标题，用于区分不同的信用卡")
    private String title;

    @ApiModelProperty(value = "卡号后四位", example = "5418", required = true)
    private String cardNumber;

    @ApiModelProperty(value = "信用卡品牌，比如：Visa、MasterCard、American Express、Discover、JCB (Japan Credit Bureau)、Diners Club International等", example = "Visa", required = true)
    private String brand;

    @ApiModelProperty(value = "是否是默认卡", example = "true")
    private Boolean isDefault;

    @ApiModelProperty(value = "卡类型：借记卡为debit，信用卡为credit", example = "credit", required = true)
    private String type;

}
