package com.etrade.puggo.controller;

import com.etrade.puggo.common.Result;
import com.etrade.puggo.common.page.PageContentContainer;
import com.etrade.puggo.common.weblog.WebLog;
import com.etrade.puggo.service.goods.comment.CommentGoodsParam;
import com.etrade.puggo.service.goods.comment.CommentVO;
import com.etrade.puggo.service.goods.comment.GoodsCommentService;
import com.etrade.puggo.service.goods.comment.ReplyCommentParam;
import com.etrade.puggo.service.goods.comment.UserCommentListParam;
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
 * @description : 商品评论
 * @date 2023/6/25 20:09
 **/
@Api(value = "商品评论接口", tags = "商品评论接口")
@RequestMapping("/goods.comment/")
@RestController
public class GoodsCommentController {


    @Resource
    private GoodsCommentService goodsCommentService;


    @WebLog
    @PostMapping("/comment/goods")
    @ApiOperation(value = "对商品做出评价")
    public Result<?> commentGoods(@Validated @RequestBody CommentGoodsParam param) {

        goodsCommentService.comment(param);
        return Result.ok();
    }


    @WebLog
    @PostMapping("/comment/reply")
    @ApiOperation(value = "对评论做出回复")
    public Result<?> replyComment(@Validated @RequestBody ReplyCommentParam param) {

        goodsCommentService.replyComment(param);
        return Result.ok();
    }


    @WebLog
    @PostMapping("/user/comment/list")
    @ApiOperation(value = "我的-用户评论列表", response = CommentVO.class)
    public Result<PageContentContainer<CommentVO>> getUserCommentList(
        @Validated @RequestBody UserCommentListParam param) {

        return Result.ok(goodsCommentService.getUserCommentList(param));
    }

}
