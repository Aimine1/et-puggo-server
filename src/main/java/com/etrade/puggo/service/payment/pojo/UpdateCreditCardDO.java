package com.etrade.puggo.service.payment.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * @author zhenyu
 * @version 1.0
 * @description: 修改信用卡信息
 * @date 2024/1/18 15:10
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UpdateCreditCardDO extends CreditCardDO {

    @NotNull
    @ApiModelProperty("卡号id")
    private Integer cardId;

}
