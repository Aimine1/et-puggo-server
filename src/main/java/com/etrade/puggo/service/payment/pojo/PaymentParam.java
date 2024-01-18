package com.etrade.puggo.service.payment.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
@ApiModel("发起支付参数")
public class PaymentParam {

    @NotNull
    @ApiModelProperty("金额")
    private BigDecimal amount;

    @NotNull
    @NotBlank
    @ApiModelProperty("付款类型，枚举值有：card、apple_pay、google_pay、wechat、alipay")
    private String paymentType;

    @NotNull
    @NotBlank
    @ApiModelProperty("客户端发起支付之前生成")
    private String paymentMethodId;

    @NotNull
    @NotBlank
    @ApiModelProperty("客户端发起支付之前生成")
    private String token;

    @NotNull
    @ApiModelProperty("交易商品id")
    private Long goodsId;

    @NotNull
    @ApiModelProperty("收货地址id")
    private Integer deliveryAddressId;

    @NotNull
    @ApiModelProperty("账单地址id，当勾选\"Same as delivery address\"选项时，此值不生效")
    private Integer billingAddressId;

    @NotNull
    @ApiModelProperty("账单地址同收货地址")
    private boolean isSameAsDeliveryAddress = false;

    @NotNull
    @ApiModelProperty("邮寄方式，可选项：1 Public Meetup，2 Standard Shipping，4 Puggo Same-day Delivery，同发布时的选项")
    private Integer shippingMethod;


}
