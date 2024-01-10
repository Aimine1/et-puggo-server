package com.etrade.puggo.service.groupon;

import com.etrade.puggo.service.groupon.dto.ApplicableShop;
import com.etrade.puggo.service.groupon.dto.CouponPlan;
import com.etrade.puggo.service.groupon.dto.ServiceTime;
import com.etrade.puggo.service.groupon.dto.Text;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Data;

/**
 * @author niuzhenyu
 * @description : 团购券详情
 * @date 2023/6/6 16:41
 **/
@Data
@ApiModel("团购券详情")
public class GrouponCouponDetailDTO extends GrouponCouponDTO {

    @ApiModelProperty("团购券规则")
    private Rule rule;

    @ApiModelProperty("团购券基本信息")
    private List<Text> baseInfoList;

    @ApiModelProperty("团购券使用声明")
    private List<Text> useStatementList;

    @ApiModelProperty("团购券方案")
    private List<CouponPlan> couponPlanList;

    @ApiModelProperty("团购券图片")
    private List<Picture> pictureList;

    @ApiModelProperty("用户是否收藏了该团购券")
    private Boolean isLike;

    @Data
    @ApiModel("团购券图片")
    public static class Picture {

        private String url;

        private Byte isMain;
    }


    @Data
    @ApiModel("团购券规则")
    public static class Rule {

        @ApiModelProperty("服务有效期")
        private ServiceTime serviceTime;

        @ApiModelProperty("最小回复时间")
        private Byte minReplyHours;

        @ApiModelProperty("是否允许取消")
        private Boolean isAllowCancel;

        @ApiModelProperty("限制订购数量")
        private Integer limitNumber;

        @ApiModelProperty("适用门店信息")
        private ApplicableShop applicableShop;
    }


}
