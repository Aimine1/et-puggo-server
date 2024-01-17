package com.etrade.puggo.service.payment.pojo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author zhenyu
 * @version 1.0
 * @description: 更新用户信息参数
 * @date 2024/1/16 11:14
 */
@Data
public class UpdateCustomerParam {

    @NotNull
    @NotBlank
    private String email;

    @NotNull
    @NotBlank
    private String description;

    @NotNull
    @NotBlank
    private String customerId;

}
