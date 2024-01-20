package com.etrade.puggo.controller;

import com.etrade.puggo.common.Result;
import com.etrade.puggo.common.weblog.WebLog;
import com.etrade.puggo.service.payment.CustomerAddressService;
import com.etrade.puggo.service.payment.CustomerCardService;
import com.etrade.puggo.service.payment.PaymentService;
import com.etrade.puggo.service.payment.pojo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhenyu
 * @version 1.0
 * @description: 支付接口
 * @date 2024/1/19 11:30
 */
@Api(value = "支付接口", tags = "支付接口")
@RestController
@RequestMapping("/payment")
public class PaymentController {


    @Resource
    private CustomerCardService customerCardService;

    @Resource
    private CustomerAddressService customerAddressService;

    @Resource
    private PaymentService paymentService;


    @WebLog
    @PostMapping("/customerCard/save")
    @ApiOperation("保存信用卡/借记卡信息")
    public Result<?> saveCustomerCard(@Validated @RequestBody CreditCardDO creditCardDO) {
        customerCardService.save(creditCardDO);
        return Result.ok();
    }


    @WebLog
    @PostMapping("/customerCard/update")
    @ApiOperation("更新信用卡/借记卡信息")
    public Result<?> updateCustomerCard(@Validated @RequestBody UpdateCreditCardDO updateCreditCardDO) {
        customerCardService.update(updateCreditCardDO);
        return Result.ok();
    }


    @WebLog
    @GetMapping("/customerCard/list")
    @ApiOperation("获取信用卡/借记卡列表")
    public Result<List<CreditCardVO>> listCustomerCard() {
        return Result.ok(customerCardService.list());
    }


    @WebLog
    @DeleteMapping("/customerCard/remove")
    @ApiOperation("根据ID删除一条信用卡/借记卡信息")
    public Result<?> removeCustomerCard(@RequestParam("cardId") Integer cardId) {
        customerCardService.remove(cardId);
        return Result.ok();
    }


    @WebLog
    @PostMapping("/customerAddress/save")
    @ApiOperation("保存买家收货地址/账单地址")
    public Result<?> saveCustomerAddress(@Validated @RequestBody CustomerAddressDO customerAddressDO) {
        customerAddressService.save(customerAddressDO);
        return Result.ok();
    }


    @WebLog
    @PostMapping("/customerAddress/update")
    @ApiOperation("更新买家收货地址/账单地址")
    public Result<?> updateCustomerAddress(@Validated @RequestBody UpdateCustomerAddressDO updateCustomerAddressDO) {
        customerAddressService.update(updateCustomerAddressDO);
        return Result.ok();
    }


    @WebLog
    @GetMapping("/customerAddress/list")
    @ApiOperation("获取买家收货地址/账单地址列表，使用type区分")
    public Result<List<UpdateCustomerAddressDO>> listCustomerAddress(@RequestParam("type") String type) {
        return Result.ok(customerAddressService.list(type));
    }


    @WebLog
    @DeleteMapping("/customerAddress/remove")
    @ApiOperation("根据ID删除一条买家收货地址/账单地址")
    public Result<?> removeCustomerAddress(@RequestParam("addressId") Integer addressId) {
        customerAddressService.remove(addressId);
        return Result.ok();
    }


    @WebLog
    @PostMapping("/customer/pay")
    @ApiOperation("用户支付")
    public Result<?> pay(@Validated @RequestBody PaymentParam param) {
        paymentService.pay(param);
        return Result.ok();
    }

}
