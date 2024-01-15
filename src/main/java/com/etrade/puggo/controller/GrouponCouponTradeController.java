package com.etrade.puggo.controller;

import com.etrade.puggo.common.Result;
import com.etrade.puggo.common.page.PageContentContainer;
import com.etrade.puggo.common.page.PageParam;
import com.etrade.puggo.common.weblog.WebLog;
import com.etrade.puggo.common.enums.MobileRegularExpEnum;
import com.etrade.puggo.service.groupon.NationalCodeVO;
import com.etrade.puggo.service.groupon.trade.TradeGrouponCouponDTO;
import com.etrade.puggo.service.groupon.trade.TradeGrouponCouponDetailDTO;
import com.etrade.puggo.service.groupon.trade.TradeGrouponCouponListVO;
import com.etrade.puggo.service.groupon.trade.TradeGrouponCouponParam;
import com.etrade.puggo.service.groupon.trade.TradeGrouponCouponSearchParam;
import com.etrade.puggo.service.groupon.trade.TradeGrouponCouponService;
import com.etrade.puggo.service.groupon.trade.TradeGrouponCouponWebVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author niuzhenyu
 * @description : 团购券订单类接口
 * @date 2023/6/6 15:34
 **/
@Api(value = "团购券订单接口", tags = "团购券订单接口")
@RequestMapping("/goods/groupon.trade/")
@RestController
public class GrouponCouponTradeController {


    @Resource
    private TradeGrouponCouponService tradeGrouponCouponService;


    @WebLog
    @PostMapping("/create")
    @ApiOperation(value = "订购团购券接口", response = TradeGrouponCouponDTO.class)
    public Result<TradeGrouponCouponDTO> tradeGrouponCoupon(@RequestBody @Validated TradeGrouponCouponParam param) {

        return Result.ok(tradeGrouponCouponService.createTrade(param));
    }


    @WebLog
    @PutMapping("/cancel")
    @ApiOperation(value = "取消订购团购券接口")
    public Result<?> cancelGrouponCouponTrade(
        @RequestParam("tradeId") @ApiParam(value = "tradeId", required = true) Long tradeId) {

        tradeGrouponCouponService.cancelTrade(tradeId);
        return Result.ok();
    }


    @WebLog
    @PostMapping("/user/list")
    @ApiOperation(value = "团购券订单列表接口", response = TradeGrouponCouponListVO.class)
    public Result<PageContentContainer<TradeGrouponCouponListVO>> getUserGrouponCouponTradeList(
        @Validated @RequestBody PageParam param) {

        return Result.ok(tradeGrouponCouponService.getUserTradeList(param));
    }


    @WebLog
    @GetMapping("/detail")
    @ApiOperation(value = "团购券订单详情接口", response = TradeGrouponCouponDetailDTO.class)
    public Result<TradeGrouponCouponDetailDTO> getGrouponCouponTradeDetail(
        @RequestParam("tradeId") @ApiParam(value = "tradeId", required = true) Long tradeId) {

        return Result.ok(tradeGrouponCouponService.getTradeDetail(tradeId));
    }


    @WebLog
    @GetMapping("/national/code/list")
    @ApiOperation(value = "国家电话区号列表", response = NationalCodeVO.class)
    public Result<List<NationalCodeVO>> listNationalCode() {

        List<NationalCodeVO> list = new ArrayList<>();
        for (MobileRegularExpEnum value : MobileRegularExpEnum.values()) {
            list.add(new NationalCodeVO(value.getNationalCode(), value.getNational()));
        }

        return Result.ok(list);
    }


    @WebLog
    @PostMapping("/web/list")
    @ApiOperation(value = "团购券订单列表接口for web", response = TradeGrouponCouponWebVO.class)
    public Result<PageContentContainer<TradeGrouponCouponWebVO>> getGrouponCouponWebTradeList(
        @Validated @RequestBody TradeGrouponCouponSearchParam param) {

        return Result.ok(tradeGrouponCouponService.getWebTradeList(param));
    }


    @WebLog
    @GetMapping("/web/detail")
    @ApiOperation(value = "团购券订单详情接口for web", response = TradeGrouponCouponWebVO.class)
    public Result<TradeGrouponCouponWebVO> getGrouponCouponWebTradeDetail(
        @RequestParam("tradeId") @ApiParam(value = "tradeId", required = true) Long tradeId) {

        return Result.ok(tradeGrouponCouponService.getWebTradeDetail(tradeId));
    }

}
