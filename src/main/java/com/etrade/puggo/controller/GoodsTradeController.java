package com.etrade.puggo.controller;

import com.etrade.puggo.common.Result;
import com.etrade.puggo.common.page.PageContentContainer;
import com.etrade.puggo.common.weblog.WebLog;
import com.etrade.puggo.service.goods.trade.GoodsTradeService;
import com.etrade.puggo.service.goods.trade.MyTradeVO;
import com.etrade.puggo.service.goods.trade.UserGoodsTradeParam;
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
    @ApiOperation(value = "我的-我买到的商品列表", response = MyTradeVO.class)
    @PostMapping("/myTrade/list")
    public Result<PageContentContainer<MyTradeVO>> getMyTradeList(@Validated @RequestBody UserGoodsTradeParam param) {

        return Result.ok(goodsTradeService.getMyTradeList(param));
    }

}
