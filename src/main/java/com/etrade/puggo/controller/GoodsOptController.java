package com.etrade.puggo.controller;

import com.etrade.puggo.common.Result;
import com.etrade.puggo.common.weblog.WebLog;
import com.etrade.puggo.service.goods.opt.*;
import com.etrade.puggo.service.goods.sales.GoodsSalesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;

/**
 * @author niuzhenyu
 * @description : 商品操作接口
 * @date 2023/6/15 18:37
 **/
@Api(value = "商品操作接口", tags = "商品操作接口")
@RequestMapping("/goods/goods.opt/")
@RestController
public class GoodsOptController {

    @Resource
    private GoodsSalesService goodsSalesService;

    @Resource
    private UpdateGoodsStateService updateGoodsStateService;

    @Resource
    private GoodsShareLinkService goodsShareLinkService;


    @WebLog
    @PutMapping("/user/like")
    @ApiOperation(value = "用户收藏商品")
    public Result<?> likeGoods(
            @ApiParam(value = "goodsId", required = true) @RequestParam("goodsId") Long goodsId) {

        goodsSalesService.likeGoods(goodsId);
        return Result.ok();
    }


    @WebLog
    @PutMapping("/user/unlike")
    @ApiOperation(value = "用户取消收藏商品")
    public Result<?> unlikeGoods(
            @ApiParam(value = "goodsId", required = true) @RequestParam("goodsId") Long goodsId) {

        goodsSalesService.unlikeGoods(goodsId);
        return Result.ok();
    }


    @WebLog
    @PostMapping("/state/update")
    @ApiOperation(value = "修改商品状态")
    public Result<?> updateState(@RequestBody @Validated UpdateGoodsStateParam param) {

        updateGoodsStateService.updateState(param.getGoodsId(), param.getState());
        return Result.ok();
    }


    @WebLog
    @ApiOperation("生成商品分享链接")
    @GetMapping("/link/share")
    public Result<GoodsShareLinkVO> shareLink(@RequestParam("goodsId") Long goodsId) {
        return Result.ok(goodsShareLinkService.shareLink(goodsId));
    }


    @WebLog
    @ApiOperation("解析商品分享链接")
    @PostMapping("/link/decrypt")
    public Result<DecryptGoodsShareLinkVO> decryptLink(@Validated @RequestBody DecryptGoodsShareLinkParam param) {
        return Result.ok(goodsShareLinkService.decryptLink(param.getShareLink()));
    }


    /**************************************************************************
     ***********************************  web端  *******************************
     *************************************************************************/

    @WebLog
    @ApiIgnore
    @PutMapping("/web/takeOffSale")
    public Result<?> takeOffSale(@RequestParam Long goodsId) {

        updateGoodsStateService.takeOffSaleWeb(goodsId);
        return Result.ok();
    }

}
