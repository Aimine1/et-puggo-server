package com.etrade.puggo.service.groupon.trade;

import com.etrade.puggo.service.groupon.dto.CouponPlan;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

/**
 * @author niuzhenyu
 * @description : 团购券订单列表
 * @date 2023/6/6 17:19
 **/
@Data
@ApiModel("团购券订单列表for web")
public class TradeGrouponCouponWebVO extends TradeGrouponCouponDTO {

    @ApiModelProperty("团购券id")
    private Long grouponId;

    @ApiModelProperty("团购券title")
    private String grouponTitle;

    @ApiModelProperty("订购时间")
    private LocalDateTime tradeTime;

    @ApiModelProperty("使用日期")
    private String useageDate;

    @ApiModelProperty("订购数量")
    private Integer number;

    @ApiModelProperty("订购用户id")
    private Long customerId;

    @ApiModelProperty("用户姓氏")
    private String contactLastName;

    @ApiModelProperty("用户名字")
    private String contactName;

    @ApiModelProperty("电话")
    private String contactPhone;

    @ApiModelProperty("email")
    private String contactEmail;

    @ApiModelProperty("团购券套餐")
    private List<CouponPlan> planList;

    @JsonIgnore
    private String planIds;

    @ApiModelProperty("状态")
    private String state;

}
