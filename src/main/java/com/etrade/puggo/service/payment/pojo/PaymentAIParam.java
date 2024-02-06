package com.etrade.puggo.service.payment.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author zhenyu
 * @version 1.0
 * @description: 支付
 * @date 2024/1/18 15:25
 */
@Data
@ApiModel("AI可用次数支付参数")
public class PaymentAIParam {

    @NotNull(message = "Invalid paymentType")
    @NotBlank(message = "Invalid paymentType")
    @ApiModelProperty("付款类型，枚举值有：card、apple_pay、google_pay、wechat、alipay")
    private String paymentType;

    @NotNull(message = "Invalid paymentMethodId")
    @NotBlank(message = "Invalid paymentMethodId")
    @ApiModelProperty("客户端发起支付之前生成")
    private String paymentMethodId;

    @NotNull(message = "Invalid token")
    @NotBlank(message = "Invalid token")
    @ApiModelProperty("客户端发起支付之前生成")
    private String token;

    @NotNull(message = "Invalid billingAddressId")
    @ApiModelProperty("账单地址id，当勾选\"Same as delivery address\"选项时，此值不生效")
    private Integer billingAddressId;

    @ApiModelProperty("如果支付方式是card，信用卡id")
    private Integer paymentCardId;

    @NotNull(message = "Invalid kindId")
    @ApiModelProperty("AI鉴定品类")
    private Integer kindId;

    @NotNull(message = "Invalid availableTimes")
    @ApiModelProperty("购买可用额度")
    @Min(value = 1, message = "购买次数不能小于一次")
    private Integer availableTimes;

    @NotNull(message = "Invalid amount")
    @ApiModelProperty("金额")
    private BigDecimal amount;

}
