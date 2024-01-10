package com.etrade.puggo.service.account;

import com.etrade.puggo.filter.DeviceInfoDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author niuzhenyu
 * @description : 登录验证邮箱
 * @date 2023/5/19 20:06
 **/
@Data
@ApiModel("登录账号密码")
public class LoginAccountParam {

    @NotNull(message = "account is empty")
    @NotEmpty(message = "account is empty")
    @ApiModelProperty(name = "用户昵称或者邮箱", required = true)
    private String account;

    @NotNull(message = "password is empty")
    @NotEmpty(message = "password is empty")
    @ApiModelProperty(name = "密码", required = true)
    private String password;

    @Valid
    @ApiModelProperty(name = "登录的设备信息", required = true)
    private DeviceInfoDO deviceInfo;

}
