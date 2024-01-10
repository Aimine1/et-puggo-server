package com.etrade.puggo.service.goods.trade;

import com.etrade.puggo.service.goods.sales.LaunchUserDO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

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

}
