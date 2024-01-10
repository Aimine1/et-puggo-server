package com.etrade.puggo.service.account;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author niuzhenyu
 * @description : 重置密码
 * @date 2023/5/22 19:08
 **/
@Data
@ApiModel("重置密码参数")
public class ResetPasswordParam {

    @NotNull(message = "account is empty")
    @NotBlank(message = "account is empty")
    @ApiModelProperty(value = "登录账号", required = true)
    private String account;

    @NotNull(message = "oldPassword is empty")
    @NotBlank(message = "oldPassword is empty")
    @ApiModelProperty(value = "旧密码", required = true)
    private String oldPassword;

    @NotNull(message = "newPassword is empty")
    @NotBlank(message = "newPassword is empty")
    @ApiModelProperty(value = "新密码", required = true)
    private String newPassword;

    @NotNull(message = "confirmPassword is empty")
    @NotBlank(message = "confirmPassword is empty")
    @ApiModelProperty(value = "确认新密码", required = true)
    private String confirmPassword;

}
