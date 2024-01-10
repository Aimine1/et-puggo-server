package com.etrade.puggo.service.account;

import io.swagger.annotations.ApiModel;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * @author niuzhenyu
 * @description : 注册账号
 * @date 2023/5/20 10:16
 **/
@Data
@ApiModel("注册账号")
public class RegisterAccountParam {

    @Email
    @NotNull(message = "email is illegal")
    @NotEmpty(message = "email is illegal")
    private String email;

    @Length(min = 1, max = 50)
    @NotNull(message = "nickname is illegal")
    @NotEmpty(message = "nickname is illegal")
    private String nickname;

    @NotNull(message = "password is illegal")
    @NotEmpty(message = "password is illegal")
    private String password;

    @NotNull(message = "password is illegal")
    @NotEmpty(message = "password is illegal")
    private String confirmPassword;

}
