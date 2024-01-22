package com.etrade.puggo.controller;

import com.etrade.puggo.common.Result;
import com.etrade.puggo.common.page.PageContentContainer;
import com.etrade.puggo.common.weblog.WebLog;
import com.etrade.puggo.service.goods.sales.pojo.GoodsSimpleVO;
import com.etrade.puggo.service.goods.trade.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;

/**
 * @author niuzhenyu
 * @description : 商品交易接口
 * @date 2023/10/8 11:40
 **/
@Api(value = "商品交易接口", tags = "商品交易接口")
@RequestMapping("/goods/goods.trade/")
@RestController
public class GoodsTradeController {

    @Resource
    private GoodsTradeService goodsTradeService;

    @WebLog
    @ApiOperation(value = "我的商品交易订单列表", response = MyTradeVO.class)
    @PostMapping("/myTrade/list")
    public Result<PageContentContainer<MyTradeVO>> getMyTradeList(@Validated @RequestBody UserGoodsTradeParam param) {

        return Result.ok(goodsTradeService.getMyTradeList(param));
    }


    @WebLog
    @ApiOperation(value = "根据id查询单个商品交易订单，用来查询计算待支付倒计时", response = MyTradeVO.class)
    @PostMapping("/myTrade/get")
    public Result<MyTradeVO> getOneTrade(@RequestParam("tradeId") Long tradeId) {

        return Result.ok(goodsTradeService.getOne(tradeId));
    }


    @WebLog
    @ApiIgnore
    @ApiOperation(value = "web端-商品成交列表", response = GoodsSimpleVO.class)
    @PostMapping("/web/trade/list")
    public Result<PageContentContainer<GoodsTradeVO>> getWebGoodsTradeList(@Validated @RequestBody GoodsTradeParam param) {

        return Result.ok(goodsTradeService.getGoodsTradeList(param));
    }

}
