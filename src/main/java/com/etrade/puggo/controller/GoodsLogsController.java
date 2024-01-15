package com.etrade.puggo.controller;

import com.etrade.puggo.common.Result;
import com.etrade.puggo.common.page.PageContentContainer;
import com.etrade.puggo.common.page.PageParam;
import com.etrade.puggo.common.weblog.WebLog;
import com.etrade.puggo.service.log.GoodsLogsService;
import com.etrade.puggo.service.log.UserLogsVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author niuzhenyu
 * @description : 商品日志接口
 * @date 2023/6/28 19:01
 **/
@Api(value = "商品日志接口", tags = "商品日志接口")
@RequestMapping("/goods/goods.logs/")
@RestController
public class GoodsLogsController {

    @Resource
    private GoodsLogsService goodsLogsService;


    @WebLog
    @PostMapping("/user/logs")
    @ApiOperation(value = "用户商品操作日志")
    public Result<PageContentContainer<UserLogsVO>> getUserGoodsLogs(@RequestBody @Validated PageParam param) {

        return Result.ok(goodsLogsService.getUserGoodsLogs(param));
    }

    @WebLog
    @PostMapping("/unreadCount/get")
    @ApiOperation(value = "获取用户商品操作日志未读数量")
    public Result<Integer> getUnreadCount() {
        return Result.ok(goodsLogsService.getUnreadCount());
    }


    @WebLog
    @PostMapping("/updateToRead")
    @ApiOperation(value = "修改所有日志状态为已读状态")
    public Result<?> updateToRead() {
        goodsLogsService.updateToRead();
        return Result.ok();
    }

}
