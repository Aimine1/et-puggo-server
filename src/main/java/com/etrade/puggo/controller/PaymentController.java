package com.etrade.puggo.controller;

import com.etrade.puggo.common.Result;
import com.etrade.puggo.common.weblog.WebLog;
import com.etrade.puggo.service.payment.*;
import com.etrade.puggo.service.payment.pojo.*;
import com.etrade.puggo.utils.JsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author zhenyu
 * @version 1.0
 * @description: 支付接口
 * @date 2024/1/19 11:30
 */
@Slf4j
@Validated
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

    @Resource
    private PaymentAIService paymentAIService;

    @Resource
    private PaymentMethodService paymentMethodService;


    @WebLog
    @PostMapping("/customerCard/save")
    @ApiOperation("保存信用卡/借记卡信息")
    public Result<?> saveCustomerCard(@Validated @RequestBody CreditCardDO creditCardDO) {
        return Result.ok(customerCardService.save(creditCardDO));
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
        return Result.ok(customerAddressService.save(customerAddressDO));
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
    @ApiOperation("用户支付，返回支付id")
    public Result<PayVO> pay(@Validated @RequestBody PaymentParam param) {
        return Result.ok(paymentService.payForProduct(param));
    }


    @WebLog
    @PostMapping("/customer/payForAI")
    @ApiOperation("用户支付-AI鉴定额度支付，返回支付id")
    public Result<PayVO> payForAI(@Validated @RequestBody PaymentAIParam param) {
        return Result.ok(paymentAIService.payForAI(param));
    }


    @WebLog
    @PutMapping("/customer/pay/callback")
    @ApiOperation("调起第三方支付后的回调接口，最终才能完成支付")
    public Result<?> payCallback(@RequestParam("payId") Long payId, @RequestParam(value = "token", required = false) String token) {
        paymentService.confirmPaymentIntent(payId, token);
        return Result.ok();
    }


    @WebLog
    @GetMapping("/seller/account/callback")
    @ApiOperation("商家账号回调接口")
    public Result<?> callbackSellerAccount(HttpServletRequest request) {
        log.info("callback request parameter = {}", JsonUtils.toJson(request.getParameterMap()));
        return Result.ok();
    }


    @WebLog
    @PutMapping("/seller/account/create")
    @ApiOperation("创建商家账号")
    public Result<String> createSellerAccount() {
        return Result.ok(paymentService.createSellerAccount());
    }


    @WebLog
    @GetMapping("/customer/paymentMethod/list")
    @ApiOperation("获取买家支付方式列表")
    public Result<List<String>> listPaymentMethod() {
        return Result.ok(paymentMethodService.listPaymentMethod());
    }


    @WebLog
    @PutMapping("/customer/paymentMethod/bind")
    @ApiOperation("将买家与支付方式进行绑定")
    public Result<?> updatePaymentMethod(@NotBlank @RequestParam("paymentMethodId") String paymentMethodId) {
        paymentMethodService.updatePaymentMethod(paymentMethodId);
        return Result.ok();
    }

}
