package com.etrade.puggo.controller;

import com.etrade.puggo.common.Result;
import com.etrade.puggo.common.weblog.WebLog;
import com.etrade.puggo.service.setting.UserProfileService;
import com.etrade.puggo.service.setting.pojo.KeyValParam;
import com.etrade.puggo.service.setting.pojo.SettingsVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhenyu
 * @version 1.0
 * @description: 用户偏好设置接口
 * @date 2024/1/20 22:22
 */
@Api(value = "用户偏好设置接口", tags = "用户偏好设置接口")
@RequestMapping("/user/profile")
@RestController
public class UserProfileController {


    @Resource
    private UserProfileService userProfileService;


    @WebLog
    @ApiOperation("当前用户偏好设置清单")
    @GetMapping("/list")
    public Result<List<SettingsVO>> listProfile() {
        return Result.ok(userProfileService.list());
    }


    @WebLog
    @ApiOperation("用户偏好设置")
    @PostMapping("/set")
    public Result<?> setProfile(@RequestBody @Validated KeyValParam param) {
        userProfileService.set(param);
        return Result.ok();
    }

}
