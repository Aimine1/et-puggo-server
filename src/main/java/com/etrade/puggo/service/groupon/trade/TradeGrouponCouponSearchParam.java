package com.etrade.puggo.service.groupon.trade;

import com.etrade.puggo.common.page.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author niuzhenyu
 * @description : 订购优惠券参数
 * @date 2023/6/6 17:12
 **/
@Data
@ApiModel("订购优惠券参数for web")
public class TradeGrouponCouponSearchParam extends PageParam {

    @ApiModelProperty("团购券title")
    private String title;

    @ApiModelProperty("分类id")
    private Integer classId;

    @ApiModelProperty("订单id")
    private Long tradeId;

}
