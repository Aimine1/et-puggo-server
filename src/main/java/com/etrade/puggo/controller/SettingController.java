package com.etrade.puggo.controller;

import com.etrade.puggo.common.Result;
import com.etrade.puggo.common.weblog.WebLog;
import com.etrade.puggo.service.groupon.MoneyKindVO;
import com.etrade.puggo.service.groupon.dto.S3Picture;
import com.etrade.puggo.service.setting.AdvertisementVO;
import com.etrade.puggo.service.setting.KeyValParam;
import com.etrade.puggo.service.setting.SettingService;
import com.etrade.puggo.service.setting.SettingsParam;
import com.etrade.puggo.service.setting.SettingsVO;
import com.etrade.puggo.service.setting.SystemImAction;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author niuzhenyu
 * @description : 设置接口
 * @date 2023/6/30 15:32
 **/
@Api(value = "设置接口", tags = "设置接口")
@RequestMapping("/goods/settings/")
@RestController
public class SettingController {

    @Resource
    private SettingService settingService;


    @WebLog
    @ApiOperation("设置列表")
    @GetMapping("/list")
    public Result<List<SettingsVO>> listSettings() {

        return Result.ok(settingService.listSettings());
    }


    @WebLog
    @ApiOperation("修改单个设置")
    @PostMapping("/single/set")
    public Result<?> setV(@RequestBody @Validated KeyValParam param) {

        settingService.v(param.getKey(), param.getVal());
        return Result.ok();
    }


    @WebLog
    @GetMapping("/moneyKind/select")
    @ApiOperation(value = "货币种类", response = MoneyKindVO.class)
    public Result<List<MoneyKindVO>> listMoneyKind() {

        return Result.ok(settingService.listMoneyKind());
    }


    @WebLog
    @ApiIgnore
    @ApiOperation("修改设置")
    @PostMapping("/set")
    public Result<?> set(@RequestBody @Validated SettingsParam param) {

        settingService.set(param);
        return Result.ok();
    }


    @WebLog
    @ApiOperation("NoToken-获取系统IM账号")
    @GetMapping("/getSystemIm")
    public Result<SystemImAction> getSystemIm() {

        return Result.ok(settingService.getSystemIm());
    }

    @WebLog
    @ApiOperation("NoToken-获取系统默认货币")
    @GetMapping("/getMoneyKind")
    public Result<String> getMoneyKind() {

        return Result.ok(settingService.getMoneyKind());
    }

    // +++++++++++++++++++++++ 广告位 +++++++++++++++++++++++++++++++++++++//

    @WebLog
    @ApiOperation("NoToken-广告位")
    @GetMapping("/ad/list")
    public Result<List<AdvertisementVO>> listAdvertisement() {

        return Result.ok(settingService.listAdvertisement());
    }


    @WebLog
    @ApiIgnore
    @ApiOperation("添加广告位")
    @PostMapping("/ad/add")
    public Result<Long> addAdvertisement(@RequestBody @Validated S3Picture S3) {

        return Result.ok(settingService.addAdvertisement(S3));
    }


    @WebLog
    @ApiIgnore
    @ApiOperation("删除广告位")
    @PostMapping("/ad/delete")
    public Result<Integer> deleteAdvertisement(@RequestBody @Validated S3Picture S3) {

        return Result.ok(settingService.deleteAdvertisement(S3));
    }

}
