package com.etrade.puggo.controller;

import com.etrade.puggo.common.Result;
import com.etrade.puggo.common.page.PageContentContainer;
import com.etrade.puggo.common.page.PageParam;
import com.etrade.puggo.common.weblog.WebLog;
import com.etrade.puggo.service.goods.sales.GoodsDetailVO;
import com.etrade.puggo.service.goods.sales.GoodsSalesService;
import com.etrade.puggo.service.goods.sales.GoodsSearchParam;
import com.etrade.puggo.service.goods.sales.GoodsSimpleVO;
import com.etrade.puggo.service.goods.sales.RecommendGoodsParam;
import com.etrade.puggo.service.goods.sales.UserGoodsListParam;
import com.etrade.puggo.service.goods.trade.GoodsTradeParam;
import com.etrade.puggo.service.goods.trade.GoodsTradeService;
import com.etrade.puggo.service.goods.trade.GoodsTradeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author niuzhenyu
 * @description : 商品销售类接口
 * @date 2023/6/15 17:15
 **/
@Api(value = "商品接口", tags = "商品接口")
@RequestMapping("/goods.sales/")
@RestController
public class GoodsController {


    @Resource
    private GoodsSalesService goodsSalesService;
    @Resource
    private GoodsTradeService goodsTradeService;


    @WebLog
    @ApiOperation(value = "NoToken-首页-商品列表简洁展示", response = GoodsSimpleVO.class)
    @PostMapping("/list")
    public Result<PageContentContainer<GoodsSimpleVO>> getGoodsList(@Validated @RequestBody GoodsSearchParam param) {

        return Result.ok(goodsSalesService.getSampleGoodsList(param));
    }


    @WebLog
    @ApiOperation(value = "NoToken-首页-商品详情展示", response = GoodsDetailVO.class)
    @GetMapping("/detail")
    public Result<GoodsDetailVO> getGoodsDetail(@RequestParam Long goodsId) {

        return Result.ok(goodsSalesService.getGoodsDetail(goodsId));
    }


    @WebLog
    @ApiOperation(value = "我的-商品列表", response = GoodsSimpleVO.class)
    @PostMapping("/user/publish/list")
    public Result<PageContentContainer<GoodsSimpleVO>> getUserLaunchGoodsList(@Validated @RequestBody UserGoodsListParam param) {

        return Result.ok(goodsSalesService.getUserGoodsList(param));
    }


    @WebLog
    @ApiOperation(value = "草稿箱", response = GoodsSimpleVO.class)
    @PostMapping("/user/draft/list")
    public Result<PageContentContainer<GoodsSimpleVO>> getUserDraftGoodsList(@Validated @RequestBody PageParam param) {

        return Result.ok(goodsSalesService.getDraftGoodsList(param));
    }


    @WebLog
    @ApiOperation(value = "我的-收藏的商品列表", response = GoodsSimpleVO.class)
    @PostMapping("/user/like/list")
    public Result<PageContentContainer<GoodsSimpleVO>> getUserLikeGoodsList(@Validated @RequestBody PageParam param) {

        return Result.ok(goodsSalesService.getUserLikeGoodsList(param));
    }


    @WebLog
    @ApiOperation(value = "商品详情页-商品推荐列表", response = GoodsSimpleVO.class)
    @PostMapping("/recommend/list")
    public Result<PageContentContainer<GoodsSimpleVO>> getRecommendGoodsList(
        @Validated @RequestBody RecommendGoodsParam param) {

        return Result.ok(goodsSalesService.getRecommendGoodsList(param));
    }


    /**************************************************************************
     ***********************************  web端  *******************************
     *************************************************************************/

    @WebLog
    @ApiIgnore
    @ApiOperation(value = "web端-商品列表", response = GoodsSimpleVO.class)
    @PostMapping("/web/list")
    public Result<PageContentContainer<GoodsDetailVO>> getWebGoodsList(@Validated @RequestBody GoodsSearchParam param) {

        return Result.ok(goodsSalesService.getWebGoodsList(param));
    }


    @WebLog
    @ApiIgnore
    @ApiOperation(value = "web端-商品成交列表", response = GoodsSimpleVO.class)
    @PostMapping("/web/trade/list")
    public Result<PageContentContainer<GoodsTradeVO>> getWebGoodsTradeList(
        @Validated @RequestBody GoodsTradeParam param) {

        return Result.ok(goodsTradeService.getGoodsTradeList(param));
    }

}
