package com.etrade.puggo.controller;

import com.etrade.puggo.common.Result;
import com.etrade.puggo.common.page.PageContentContainer;
import com.etrade.puggo.common.weblog.WebLog;
import com.etrade.puggo.service.fans.UserFansParam;
import com.etrade.puggo.service.fans.UserFansService;
import com.etrade.puggo.service.goods.sales.LaunchUserDO;
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

/**
 * @author niuzhenyu
 * @description : 用户粉丝
 * @date 2023/10/8 10:32
 **/
@Api(tags = "用户粉丝相关接口", value = "用户粉丝相关接口")
@RequestMapping("/user.fans")
@RestController
@Validated
public class UserFansController {

    @Resource
    private UserFansService userFansService;


    @WebLog
    @PutMapping("/follow")
    @ApiOperation("关注某人")
    public Result<?> follow(@RequestParam Long userId) {
        userFansService.follow(userId);
        return Result.ok();
    }

    @WebLog
    @PutMapping("/unfollow")
    @ApiOperation("对某人取消关注")
    public Result<?> unfollow(@RequestParam Long userId) {
        userFansService.unfollow(userId);
        return Result.ok();
    }

    @WebLog
    @PostMapping("/myFollow/list")
    @ApiOperation("我的关注列表")
    public Result<PageContentContainer<LaunchUserDO>> listFollow(@RequestBody @Validated UserFansParam param) {
        return Result.ok(userFansService.listFollow(param));
    }


    @WebLog
    @PostMapping("/myFans/list")
    @ApiOperation("我的粉丝列表")
    public Result<PageContentContainer<LaunchUserDO>> listFans(@RequestBody @Validated UserFansParam param) {
        return Result.ok(userFansService.listFans(param));
    }


    @WebLog
    @GetMapping("/isFans")
    @ApiOperation("当前用户是否是该用户的粉丝")
    public Result<Boolean> isFans(@RequestParam Long userId) {
        return Result.ok(userFansService.isFans(userId));
    }
}
