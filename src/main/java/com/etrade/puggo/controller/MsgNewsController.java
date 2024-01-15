package com.etrade.puggo.controller;

import com.etrade.puggo.common.Result;
import com.etrade.puggo.common.page.PageContentContainer;
import com.etrade.puggo.common.page.PageParam;
import com.etrade.puggo.common.weblog.WebLog;
import com.etrade.puggo.service.message.MsgNewsService;
import com.etrade.puggo.service.message.UserNewsVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author niuzhenyu
 * @description : 用户消息接口
 * @date 2023/6/28 11:37
 **/
@Api(tags = "用户消息接口", value = "用户消息接口")
@Validated
@RestController
@RequestMapping("/user/message")
public class MsgNewsController {


    @Resource
    private MsgNewsService msgNewsService;

    @WebLog
    @PostMapping("/user/news")
    @ApiOperation(value = "用户动态消息", response = UserNewsVO.class)
    public Result<PageContentContainer<UserNewsVO>> getUserMsgNews(@RequestBody @Validated PageParam page) {

        return Result.ok(msgNewsService.getUserNews(page));
    }


    @WebLog
    @PutMapping("/read/news")
    @ApiOperation("用户读取消息，未读状态变已读")
    public Result<?> readNews() {

        msgNewsService.readNews();
        return Result.ok();
    }


    @WebLog
    @PostMapping("/unreadCount/get")
    @ApiOperation(value = "获取用户商品操作日志未读数量")
    public Result<Integer> getUnreadCount() {
        return Result.ok(msgNewsService.getUnreadCount());
    }

}
