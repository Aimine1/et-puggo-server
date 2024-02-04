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

    @NotNull(message = "Invalid trade")
    @ApiModelProperty("商品交易订单id")
    private Long tradeId;

    @NotNull(message = "Invalid delivery address")
    @ApiModelProperty("收货地址id")
    private Integer deliveryAddressId;

    @ApiModelProperty("账单地址id，当勾选\"Same as delivery address\"选项时，此值不生效")
    private Integer billingAddressId;

    @ApiModelProperty("账单地址同收货地址")
    private boolean isSameAsDeliveryAddress = false;

    @NotNull(message = "Invalid shipping method")
    @ApiModelProperty("邮寄方式，可选项：1 Public Meetup，2 Standard Shipping，4 Puggo Same-day Delivery，同发布时的选项")
    private Integer shippingMethod;

    @NotNull(message = "Invalid target")
    @NotBlank(message = "Invalid target")
    @ApiModelProperty("支付的目的，哪个模块的付款，目前有两类：product和ai")
    private String target;

}
