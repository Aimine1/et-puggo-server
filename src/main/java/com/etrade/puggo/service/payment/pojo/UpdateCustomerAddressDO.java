package com.etrade.puggo.service.payment.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * @author zhenyu
 * @version 1.0
 * @description: 修改收货地址/用户信息
 * @date 2024/1/18 14:48
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UpdateCustomerAddressDO extends CustomerAddressDO {

    @NotNull
    @ApiModelProperty("收货地址id")
    private Integer addressId;

}
