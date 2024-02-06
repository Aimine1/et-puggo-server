package com.etrade.puggo.service.goods.trade.pojo;

import com.etrade.puggo.service.goods.sales.pojo.LaunchUserDO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author niuzhenyu
 * @description : 我买到的商品
 * @date 2023/6/30 10:19
 **/
@Data
@ApiModel("我买到的商品")
public class MyTradeVO {

    @ApiModelProperty("商品id")
    private Long goodsId;

    @ApiModelProperty("商品Title")
    private String goodsTitle;

    @ApiModelProperty("货币种类")
    private String moneyKind;

    @ApiModelProperty("交易订单id")
    private Long tradeId;

    @ApiModelProperty("交易订单编号")
    private String tradeNo;

    @ApiModelProperty("成交价格")
    private BigDecimal tradingPrice;

    @ApiModelProperty("成交时间")
    private LocalDateTime tradingTime;

    @ApiModelProperty("交易状态")
    private String state;

    @JsonIgnore
    @ApiModelProperty("商品发布人id")
    private Long launchUserId;

    @ApiModelProperty("商品发布人信息")
    private LaunchUserDO launchUser;

    @ApiModelProperty("商品主图")
    private String mainImgUrl;

    @ApiModelProperty("买家用户id")
    private Long customerId;

    @ApiModelProperty("卖家用户id")
    private Long sellerId;

    @ApiModelProperty("付款类型，枚举值有：card、apple_pay、google_pay、wechat、alipay")
    private String paymentType;

    @ApiModelProperty("客户端发起支付之前生成")
    private String paymentMethodId;

    @ApiModelProperty("收货地址id")
    private Integer deliveryAddressId;

    @ApiModelProperty("账单地址id，当勾选\"Same as delivery address\"选项时，此值不生效")
    private Integer billingAddressId;

    @ApiModelProperty("账单地址同收货地址")
    private boolean isSameAsDeliveryAddress = false;

    @ApiModelProperty("邮寄方式，可选项：1 Public Meetup，2 Standard Shipping，4 Puggo Same-day Delivery，同发布时的选项")
    private Integer shippingMethod;

    @ApiModelProperty("交易title")
    private String title;

    @ApiModelProperty("如果支付方式是card，信用卡id")
    private Integer paymentCardId;

    @ApiModelProperty("支付发票表id")
    private Long paymentInvoiceId;

}
