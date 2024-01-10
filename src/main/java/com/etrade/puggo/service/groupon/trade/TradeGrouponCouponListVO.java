package com.etrade.puggo.service.groupon.trade;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * @author niuzhenyu
 * @description : 团购券订单列表
 * @date 2023/6/6 17:19
 **/
@Data
@ApiModel("团购券订单列表")
public class TradeGrouponCouponListVO extends TradeGrouponCouponDTO {

    @ApiModelProperty("团购券id")
    private Long grouponId;

    @ApiModelProperty("团购券title")
    private String grouponTitle;

    @ApiModelProperty("团购券主图")
    private String grouponMainPic;

    @ApiModelProperty("订购时间")
    private LocalDateTime tradeTime;

}
