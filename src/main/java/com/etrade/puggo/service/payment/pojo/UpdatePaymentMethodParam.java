package com.etrade.puggo.service.payment.pojo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author zhenyu
 * @version 1.0
 * @description: 更新支付方式参数
 * @date 2024/1/16 11:16
 */
@Data
public class UpdatePaymentMethodParam {

    @NotNull
    @NotBlank
    private String customerId;

    @NotNull
    @NotBlank
    private String paymentMethodId;

}
