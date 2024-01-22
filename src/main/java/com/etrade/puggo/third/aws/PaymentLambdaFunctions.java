package com.etrade.puggo.third.aws;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.shaded.com.google.common.collect.ImmutableMap;
import com.etrade.puggo.third.aws.pojo.*;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zhenyu
 * @version 1.0
 * @description: 支付Lambda方法列表
 * @date 2024/1/16 11:31
 */
@Component
@Validated
public class PaymentLambdaFunctions {


    /**
     * 创建用户
     *
     * @param email    邮箱
     * @param username 昵称
     * @return Stripe的用户id，参考：cus_PNt6qcCNTZFOO4
     */
    public String createCustomer(@NotNull String email, @NotNull String username) {
        CreateCustomerReq param = new CreateCustomerReq();
        param.setEmail(email);
        param.setDescription(username);

        // Customer created: cus_PNt6qcCNTZFOO4
        String payload = AwsLambdaUtils.invokeFunction("create_customer", param);

        String patternString = "(cus_[\\w]+)";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(payload);

        return matcher.find() ? matcher.group(1) : "";
    }


    /**
     * 检索用户信息
     *
     * @param customerId 用户id
     * @return 用户信息 Json格式
     */
    public String retrieveCustomer(@NotNull String customerId) {
        /*
         * {"address":{"city":"","country":"","line1":"","line2":"","postal_code":"","state":""},"balance":0,"created":1705392275,"currency":"","default_source":null,"deleted":false,"delinquent":false,"description":"Aimin","discount":null,"email":"niuzhenyuself@163.com","id":"cus_PNtQgQvpacHLMP","invoice_prefix":"75C6667A","invoice_settings":{"custom_fields":null,"default_payment_method":null,"footer":""},"livemode":false,"metadata":{},"name":"","next_invoice_sequence":1,"phone":"","preferred_locales":[],"shipping":null,"sources":{"has_more":false,"total_count":0,"url":"/v1/customers/cus_PNtQgQvpacHLMP/sources","data":[]},"subscriptions":{"has_more":false,"total_count":0,"url":"/v1/customers/cus_PNtQgQvpacHLMP/subscriptions","data":[]},"tax_exempt":"none","tax_ids":{"has_more":false,"total_count":0,"url":"/v1/customers/cus_PNtQgQvpacHLMP/tax_ids","data":[]}}
         */
        ImmutableMap<String, String> param = ImmutableMap.of("customerId", customerId);
        return AwsLambdaUtils.invokeFunction("retrieve_customer", param);
    }


    /**
     * 删除用户，不要轻易调
     *
     * @param customerId 用户id
     * @return 返回删除的用户的具体信息
     */
    public String deleteCustomer(@NotNull String customerId) {
        ImmutableMap<String, String> param = ImmutableMap.of("customerId", customerId);
        return AwsLambdaUtils.invokeFunction("delete_customer", param);
    }


    public String confirmPaymentIntent(@NotNull String paymentIntentId) {
        ImmutableMap<String, String> param = ImmutableMap.of("paymentIntentId", paymentIntentId);
        return AwsLambdaUtils.invokeFunction("confirm_payment_intent", param);
    }


    public String createPaymentIntent(@Valid CreatePaymentIntentReq param) {
        return AwsLambdaUtils.invokeFunction("create_payment_intent", param);
    }


    /**
     * 支付方式列表
     *
     * @param customerId 用户id
     * @return 未知
     */
    public Object listPaymentMethod(@NotNull String customerId) {
        ImmutableMap<String, String> param = ImmutableMap.of("customerId", customerId);

        // {"has_more":false,"total_count":0,"url":"","data":null}
        String payload = AwsLambdaUtils.invokeFunction("list_payment_methods", param);

        JSONObject jsonObject = JSONObject.parseObject(payload);

        if ((int) jsonObject.get("total_count") > 0) {
            return jsonObject.get("data");
        }

        return null;
    }


    public String updateCustomer(@Valid UpdateCustomerReq req) {
        return AwsLambdaUtils.invokeFunction("update_customer", req);
    }


    public String updatePaymentIntent(@Valid UpdatePaymentIntentReq req) {
        return AwsLambdaUtils.invokeFunction("update_payment_intent", req);
    }


    public String updatePaymentMethod(@Valid UpdatePaymentMethodReq req) {
        return AwsLambdaUtils.invokeFunction("update_payment_method", req);
    }


    public String retrievePaymentIntent(@NotNull String paymentIntentId) {
        ImmutableMap<String, String> param = ImmutableMap.of("paymentIntentId", paymentIntentId);
        return AwsLambdaUtils.invokeFunction("retrieve_payment_intent", param);
    }


    public String retrievePaymentMethod(@NotNull String paymentMethodId) {
        ImmutableMap<String, String> param = ImmutableMap.of("paymentMethodId", paymentMethodId);
        return AwsLambdaUtils.invokeFunction("retrieve_payment_method", param);
    }


    public String deletePaymentIntent(@NotNull String paymentIntentId) {
        ImmutableMap<String, String> param = ImmutableMap.of("paymentIntentId", paymentIntentId);
        return AwsLambdaUtils.invokeFunction("delete_payment_intent", param);
    }


    public String deletePaymentMethod(@NotNull String paymentMethodId) {
        ImmutableMap<String, String> param = ImmutableMap.of("paymentMethodId", paymentMethodId);
        return AwsLambdaUtils.invokeFunction("delete_payment_method", param);
    }


}
