package com.etrade.puggo.controller;

import com.etrade.puggo.common.Result;
import com.etrade.puggo.common.weblog.WebLog;
import com.etrade.puggo.service.goods.publish.PublishGoodsParam;
import com.etrade.puggo.service.goods.publish.PublishGoodsService;
import com.etrade.puggo.service.goods.publish.UpdatePublishGoodsParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author niuzhenyu
 * @description : 发布新商品
 * @date 2023/5/24 16:03
 **/
@Api(tags = "商品发布类接口", value = "商品发布类接口")
@RequestMapping("/goods/goods.publish/")
@RestController
public class GoodsPublishController {

    @Resource
    private PublishGoodsService publishGoodsService;

    @WebLog
    @ApiOperation("商品发布")
    @PostMapping("/publish")
    public Result<Long> publishGoods(@Validated @RequestBody PublishGoodsParam param, HttpServletRequest request) {
        return Result.ok(publishGoodsService.publish(param, request));
    }

    @WebLog
    @ApiOperation("商品编辑")
    @PostMapping("/update")
    public Result<Long> updateGoods(@Validated @RequestBody UpdatePublishGoodsParam param,
        HttpServletRequest request) {
        return Result.ok(publishGoodsService.update(param, request));
    }

    @WebLog
    @ApiOperation("商品成色列表")
    @GetMapping("/quality/list")
    public Result<List<String>> getQualityList() {
        return Result.ok(publishGoodsService.getQualityList());
    }

}
