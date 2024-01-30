package com.etrade.puggo;

import com.etrade.puggo.common.enums.PaymentTypeEnum;
import com.etrade.puggo.third.aws.PaymentLambdaFunctions;
import com.etrade.puggo.third.aws.pojo.CreatePaymentIntentReq;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @author zhenyu
 * @version 1.0
 * @description: AwsLambdaUnitTest 单元测试
 * @date 2024/1/16 13:59
 */
@SpringBootTest
public class AwsLambdaUnitTest {

    @Resource
    PaymentLambdaFunctions paymentLambdaFunctions;

    @Test
    void createCustomer() {
        System.out.println(paymentLambdaFunctions.createCustomer("niuzhenyuself@163.com", "Aimin"));
        // cus_PNtQgQvpacHLMP
    }

    @Test
    void deleteCustomer() {
        paymentLambdaFunctions.deleteCustomer("cus_PNtQgQvpacHLMP");
    }


    @Test
    void retrieveCustomer() {
        paymentLambdaFunctions.retrieveCustomer("cus_PNtQgQvpacHLMP");
    }


    @Test
    void listPaymentMethod() {
        paymentLambdaFunctions.listPaymentMethod("cus_PNtQgQvpacHLMP");
    }


    @Test
    void createPaymentIntent() {
        CreatePaymentIntentReq req = new CreatePaymentIntentReq();
        req.setCurrency("CAD");
        req.setPaymentMethodId("pm_1Oc58tGjtDrE34Dqaqpm2LEU");
        req.setToken("tok_1Oc58tGjtDrE34Dqs7BceJv6,");
        req.setCustomerId("cus_PQ4RwjPZ130DJG");
        req.setPaymentType(PaymentTypeEnum.card.name());
        req.setAmount(new BigDecimal("1"));
        paymentLambdaFunctions.createPaymentIntent(req);
    }

    @Test
    void retrievePaymentMethod() {
        paymentLambdaFunctions.retrievePaymentMethod("pm_1Oc58tGjtDrE34Dqaqpm2LEU");
    }

    @Test
    void createSellerAccount() {
        paymentLambdaFunctions.createSellerAccount("everythingtradeltd.verify@gmail.com");
    }


    @Test
    void createSellerAccountLink() {
        paymentLambdaFunctions.createSellerAccountLink("acct_1A2B3C4D5E6F7G8H");
    }
}
