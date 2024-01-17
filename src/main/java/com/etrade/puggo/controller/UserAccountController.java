package com.etrade.puggo.controller;

import com.etrade.puggo.common.Result;
import com.etrade.puggo.common.page.PageContentContainer;
import com.etrade.puggo.common.weblog.WebLog;
import com.etrade.puggo.service.account.SellerInfoVO;
import com.etrade.puggo.service.account.UserAccountService;
import com.etrade.puggo.service.account.UserInfoVO;
import com.etrade.puggo.service.account.WebUserSearchParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author niuzhenyu
 * @description : 登录注册接口
 * @date 2023/5/19 19:48
 **/
@Api(tags = "用户账号相关接口", value = "用户账号相关接口")
@Validated
@RestController
@RequestMapping("/user/user.account/")
public class UserAccountController {

    @Resource
    private UserAccountService userAccountService;

    @WebLog
    @GetMapping("/user/info")
    @ApiOperation(value = "用户信息", response = UserInfoVO.class)
    public Result<UserInfoVO> getUserInfo() {

        return Result.ok(userAccountService.getUserInfo());
    }


    @WebLog
    @PutMapping("/avatar/change")
    @ApiOperation(value = "更换头像", response = UserInfoVO.class)
    public Result<Integer> changeAvatar(@RequestParam String url) {

        return Result.ok(userAccountService.updateUserAvatar(url));
    }


    @WebLog
    @GetMapping("/seller/info")
    @ApiOperation(value = "卖家用户信息", response = SellerInfoVO.class)
    public Result<SellerInfoVO> getSellerInfo(@RequestParam("userId") Long userId) {

        return Result.ok(userAccountService.getSellerInfo(userId));
    }


    @WebLog
    @ApiIgnore
    @PostMapping("/web/user/list")
    @ApiOperation(value = "web端用户信息列表", response = UserInfoVO.class)
    public Result<PageContentContainer<UserInfoVO>> getUserPageList(@RequestBody @Validated WebUserSearchParam param) {

        return Result.ok(userAccountService.getUserPageList(param));
    }


    @WebLog
    @ApiIgnore
    @PutMapping("/web/user/verify")
    @ApiOperation(value = "用户验证")
    public Result<Integer> verifyUser(@RequestParam Long userId) {

        return Result.ok(userAccountService.verifyUser(userId));
    }
}
