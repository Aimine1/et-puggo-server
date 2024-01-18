package com.etrade.puggo.third.aws.pojo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author zhenyu
 * @version 1.0
 * @description: 创建支付入参
 * @date 2024/1/16 11:00
 */
@Data
public class CreatePaymentIntentReq {

    @NotNull
    private BigDecimal amount;

    @NotBlank
    @NotBlank
    private String currency;

    @NotNull
    @NotBlank
    private String customerId;

    @NotNull
    @NotBlank
    private String paymentType;

    @NotNull
    private String paymentMethodId;

    @NotNull
    @NotBlank
    private String token;

}
