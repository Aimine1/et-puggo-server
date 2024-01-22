package com.etrade.puggo.service.goods.sales.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author niuzhenyu
 * @description : 发布用户信息
 * @date 2023/6/15 17:39
 **/
@Data
@ApiModel("用户信息")
public class LaunchUserDO {

    @ApiModelProperty("系统内用户唯一ID")
    private Long userId;

    @ApiModelProperty("用户昵称")
    private String nickname;

    @ApiModelProperty("用户头像")
    private String avatar;

    @ApiModelProperty("网易IM账号id")
    private String accid;

    @ApiModelProperty("是否认证")
    private Boolean isVerified;

    @ApiModelProperty("信誉评分，最高5分，如果评分为0展示N/A")
    private String creditRating;

}
