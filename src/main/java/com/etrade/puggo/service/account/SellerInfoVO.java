package com.etrade.puggo.service.account;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import lombok.Data;

/**
 * @author niuzhenyu
 * @description : 卖家信息
 * @date 2023/6/8 22:09
 **/
@Data
@ApiModel("卖家信息")
public class SellerInfoVO {

    @ApiModelProperty("昵称")
    private String nickname;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("信誉等级")
    private String creditRating;

    @ApiModelProperty("加入日期")
    private LocalDate joinDate;

    @ApiModelProperty("地区")
    private String region;

    @ApiModelProperty("是否认证")
    private Boolean isVerified;

}
