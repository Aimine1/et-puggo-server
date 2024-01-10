package com.etrade.puggo.service.fans;

import com.etrade.puggo.common.page.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author niuzhenyu
 * @description : 用户粉丝
 * @date 2023/11/14 21:14
 **/
@Data
@ApiModel("用户粉丝")
public class UserFansParam extends PageParam {

    @ApiModelProperty("用户昵称，模糊搜索")
    private String nickname;

}
