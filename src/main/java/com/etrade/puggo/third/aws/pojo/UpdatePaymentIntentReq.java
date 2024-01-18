package com.etrade.puggo.third.aws.pojo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author zhenyu
 * @version 1.0
 * @description: 更新支付参数
 * @date 2024/1/16 11:15
 */
@Data
public class UpdatePaymentIntentReq {

    @NotNull
    @NotBlank
    private String customerId;

    @NotNull
    @NotBlank
    private String paymentIntentId;

}
