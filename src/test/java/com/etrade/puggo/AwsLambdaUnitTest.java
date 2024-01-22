package com.etrade.puggo;

import com.etrade.puggo.third.aws.PaymentLambdaFunctions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

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


}
