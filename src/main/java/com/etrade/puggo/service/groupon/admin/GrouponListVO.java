package com.etrade.puggo.service.groupon.admin;

import com.etrade.puggo.service.groupon.GrouponCouponDTO;
import com.etrade.puggo.service.groupon.dto.CouponPlan;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

/**
 * @author niuzhenyu
 * @description : 团购券列表
 * @date 2023/6/10 23:33
 **/
@Data
public class GrouponListVO extends GrouponCouponDTO {

    private String className;

    private String brand;

    private String launchUserNickname;

    private LocalDateTime launchLastTime;

    @ApiModelProperty("服务有效期区间: BETWEEN; >=GE; <=LE. BETWEEN意思为有效期在起止时间之内（包括起止时间），GE意思为从serviceTimeStart开始，LE意思为截止为serviceTimeEnd")
    private String serviceTimeInterval;

    @ApiModelProperty("有效期开始时间")
    private String serviceTimeStart;

    @ApiModelProperty("有效期截止时间")
    private String serviceTimeEnd;

    @ApiModelProperty("有效期单位：WEEK周 DATE日期")
    private String serviceTimeUnit;

    @ApiModelProperty("有效期期天数")
    private String effectiveDays;

    @ApiModelProperty("最小回复时间")
    private Byte minReplyHours;

    @ApiModelProperty("是否允许取消")
    private Boolean isAllowCancel;

    @ApiModelProperty("适用门店名称")
    private String applicableShop;

    @ApiModelProperty("门店地址，可以使用谷歌地图可以查看到")
    private String shopAddress;

    @ApiModelProperty("门店到达方式")
    private String shopArrivalMethod;


    private Integer browseNumber;
    private Integer likeNumber;
    private Integer tradeNumber;


    @ApiModelProperty("团购券基本信息")
    private List<String> baseInfoList;

    @ApiModelProperty("团购券使用声明")
    private List<String> useStatementList;

    @ApiModelProperty("团购券方案")
    private List<CouponPlan> planList;

}
