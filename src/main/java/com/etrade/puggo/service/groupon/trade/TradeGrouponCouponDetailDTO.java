package com.etrade.puggo.service.groupon.trade;

import com.etrade.puggo.service.groupon.dto.CouponPlan;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import java.util.List;
import lombok.Data;

/**
 * @author niuzhenyu
 * @description : 团购券订单列表
 * @date 2023/6/6 17:19
 **/
@Data
@ApiModel("团购券订单详情")
public class TradeGrouponCouponDetailDTO extends TradeGrouponCouponDTO {

    @ApiModelProperty("团购券id")
    private Long grouponId;

    @ApiModelProperty("使用日期")
    private LocalDate useageDate;

    @ApiModelProperty("订购数量")
    private Integer number;

    @ApiModelProperty("联系人姓氏")
    private String contactLastName;

    @ApiModelProperty("联系人名字")
    private String contactName;

    @ApiModelProperty("联系人电话")
    private String contact_phone;

    @ApiModelProperty("电话区号")
    private String national_code;

    @ApiModelProperty("联系人邮箱")
    private String contact_email;

    @ApiModelProperty("订单状态")
    private String state;

    @ApiModelProperty("方案")
    private List<CouponPlan> planList;

}
