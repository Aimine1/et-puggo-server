package com.etrade.puggo.controller;

import com.etrade.puggo.common.Result;
import com.etrade.puggo.common.weblog.WebLog;
import com.etrade.puggo.service.account.pojo.LoginAccountParam;
import com.etrade.puggo.service.account.pojo.LoginResponse;
import com.etrade.puggo.service.account.pojo.RegisterAccountParam;
import com.etrade.puggo.service.account.pojo.ResetPasswordParam;
import com.etrade.puggo.service.account.pojo.RetrievePasswordParam;
import com.etrade.puggo.service.account.UserAccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author niuzhenyu
 * @description : 登录注册接口
 * @date 2023/5/19 19:48
 **/
@Api(tags = "登录注册接口", value = "登录注册接口")
@Validated
@RestController
@RequestMapping("/user/user.account/")
public class LoginController {

    @Resource
    private UserAccountService userAccountService;

    @WebLog
    @PostMapping("/login")
    @ApiOperation("登录接口，登录成功后会返回有效token")
    public Result<LoginResponse> login(@RequestBody @Validated LoginAccountParam param, HttpServletRequest request) {
        LoginResponse loginResponse = userAccountService.login(param, request);

        return Result.ok(loginResponse);
    }

    @WebLog
    @GetMapping("/send/verification/code")
    @ApiOperation("往邮箱发送验证码")
    public Result<String> sendVerificationCode(
        @Email @RequestParam("email") @ApiParam("电子邮箱") String email,
        @Min(1) @Max(2) @RequestParam("type") @ApiParam("验证类型: 1注册验证 2找回密码验证") Byte type) {
        userAccountService.sendVerificationCode(email, type);

        return Result.ok("操作成功");
    }

    @WebLog
    @GetMapping("/verify/code")
    @ApiOperation("验证用户收到的验证码")
    public Result<String> verifyCode(
        @Email @RequestParam("email") @ApiParam("电子邮箱") String email,
        @NotNull @NotBlank @RequestParam("code") @ApiParam("验证码") String code) {
        userAccountService.verifyCode(email, code);

        return Result.ok("验证码核验成功");
    }

    @WebLog
    @PostMapping("/register")
    @ApiOperation("注册接口")
    public Result<String> register(@RequestBody @Validated RegisterAccountParam param) {
        userAccountService.register(param);

        return Result.ok(param.getNickname() + "注册成功");
    }

    @WebLog
    @GetMapping("/verify/nickname/exists")
    @ApiOperation("验证用户昵称是否存在")
    public Result<String> verifyNicknameExists(
        @NotNull @NotBlank @RequestParam("nickname") @ApiParam("用户昵称") String nickname) {
        userAccountService.verifyNicknameExists(nickname);

        return Result.ok("用户昵称没有被使用");
    }

    @WebLog
    @GetMapping("/logout")
    @ApiOperation("用户登出")
    public Result<String> logout(HttpServletRequest request) {
        userAccountService.logout(request);

        return Result.ok("用户退出成功");
    }

    @WebLog
    @PostMapping("/reset/password")
    @ApiOperation("重置密码")
    public Result<String> resetPassword(@RequestBody @Validated ResetPasswordParam param) {
        userAccountService.resetPassword(param);

        return Result.ok("重置密码成功");
    }

    @WebLog
    @PostMapping("/retrieve/password")
    @ApiOperation("找回密码")
    public Result<String> retrievePassword(@RequestBody @Validated RetrievePasswordParam param) {
        userAccountService.retrievePassword(param);

        return Result.ok("密码找回成功");
    }


    @WebLog
    @GetMapping("/delete")
    @ApiOperation("用户注销")
    public Result<String> delete(HttpServletRequest request) {
        userAccountService.delete(request);

        return Result.ok("用户删除成功");
    }

}
