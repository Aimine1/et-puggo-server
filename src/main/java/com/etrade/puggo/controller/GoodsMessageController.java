package com.etrade.puggo.controller;

import com.etrade.puggo.common.Result;
import com.etrade.puggo.common.weblog.WebLog;
import com.etrade.puggo.service.goods.message.*;
import com.etrade.puggo.service.goods.sales.pojo.AcceptPriceParam;
import com.etrade.puggo.service.goods.sales.pojo.TradeNoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author niuzhenyu
 * @description : 商品会话日志接口
 * @date 2023/7/11 18:18
 **/
@Api(value = "商品会话日志接口", tags = "商品会话日志接口")
@RequestMapping("/goods/goods.message/")
@RestController
public class GoodsMessageController {


    @Resource
    private GoodsMessageService goodsMessageService;


    @WebLog
    @PostMapping("/buyer/sendGoods/callback")
    @ApiOperation("买家发送商品记录的回调接口")
    public Result<?> sendGoodsCallback(@RequestBody @Validated BuyerSendGoodsCallbackParam param) {

        goodsMessageService.buyerSendGoodsCallback(param);
        return Result.ok();
    }


    @WebLog
    @PostMapping("/buyer/offerPrice/callback")
    @ApiOperation("买家出价的回调接口")
    public Result<?> offerPriceCallback(@RequestBody @Validated BuyerOfferPriceCallbackParam param) {

        goodsMessageService.buyerOfferPriceCallback(param);
        return Result.ok();
    }


    @WebLog
    @PostMapping("/buyer/cancelPrice/callback")
    @ApiOperation(value = "买家取消出价的回调接口")
    public Result<?> cancelPrice(@RequestBody @Validated BuyerSendGoodsCallbackParam param) {

        goodsMessageService.buyerCancelPriceCallback(param);
        return Result.ok();
    }


    @WebLog
    @PostMapping("/seller/acceptPrice/callback")
    @ApiOperation(value = "卖家接受出价后的回调接口", response = TradeNoVO.class)
    public Result<?> acceptPrice(@RequestBody @Validated AcceptPriceParam param) {

        goodsMessageService.acceptPriceCallback(param);
        return Result.ok();
    }


    @WebLog
    @PostMapping("/seller/reject/callback")
    @ApiOperation(value = "卖家拒绝出价的回调接口")
    public Result<?> rejectPrice(@RequestBody @Validated AcceptPriceParam param) {

        goodsMessageService.sellerRejectPriceCallback(param);
        return Result.ok();
    }


    @WebLog
    @PostMapping("/message/get")
    @ApiOperation(value = "获取商品会话日志，如果商品id为null则返回最后一条会话日志", response = GoodsMessageLogVO.class)
    public Result<GoodsMessageLogVO> getGoodsMessageLog(@RequestBody @Validated GetGoodsMessageParam param) {

        return Result.ok(goodsMessageService.getGoodsMessageLog(param));
    }


    @WebLog
    @PostMapping("/messageState/get")
    @ApiOperation(value = "获取该商品会话中的状态", response = String.class)
    public Result<String> getGoodsMessageState(@RequestBody @Validated GetGoodsMessageParam param) {

        return Result.ok(goodsMessageService.getGoodsMessageState(param));
    }

}
