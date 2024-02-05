package com.etrade.puggo.service.goods.trade.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author zhenyu
 * @version 1.0
 * @description: 创建待支付交易订单
 * @date 2024/2/4 13:58
 */
@Data
public class SaveTradeParam extends GetTradeParam {

    @NotNull
    @ApiModelProperty(value = "最后成交价格", required = true)
    private BigDecimal price;

    @NotNull(message = "invalid target")
    @NotBlank(message = "invalid target")
    @ApiModelProperty(value = "支付的目的，哪个模块的付款，目前有两类：product和ai", required = true)
    private String target;

    @ApiModelProperty("付款类型，枚举值有：card、apple_pay、google_pay、wechat、alipay")
    private String paymentType;

    @ApiModelProperty("客户端发起支付之前生成")
    private String paymentMethodId;

    @ApiModelProperty("收货地址id")
    private Integer deliveryAddressId;

    @ApiModelProperty("账单地址id，当勾选\"Same as delivery address\"选项时，此值不生效")
    private Integer billingAddressId;

    @ApiModelProperty("账单地址同收货地址")
    private Boolean isSameAsDeliveryAddress;

    @ApiModelProperty("邮寄方式，可选项：1 Public Meetup，2 Standard Shipping，4 Puggo Same-day Delivery，同发布时的选项")
    private Integer shippingMethod;

    @ApiModelProperty("如果支付方式是card，信用卡id")
    private Integer paymentCardId;

}
