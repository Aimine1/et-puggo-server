package com.etrade.puggo.service.account.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author niuzhenyu
 * @description : 用户私人信息
 * @date 2023/6/8 22:09
 **/
@Data
@ApiModel("用户私人信息")
public class UserInfoVO {

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("昵称")
    private String nickname;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("信誉评分，最高5分，如果评分为0展示N/A")
    private BigDecimal creditRating;

    @ApiModelProperty("加入日期")
    private LocalDate joinDate;

    @ApiModelProperty("权限")
    private String userRole;

    @ApiModelProperty("电话")
    private String phone;

    @ApiModelProperty("地区")
    private String region;

    @ApiModelProperty("是否认证")
    private Boolean isVerified;

    @ApiModelProperty("关注数量")
    private Integer followNum;

    @ApiModelProperty("粉丝数量")
    private Integer fansNum;

    @ApiModelProperty("买家身份支付id")
    private String paymentCustomerId;

    @ApiModelProperty("卖家身份支付id")
    private String paymentSellerId;

}
