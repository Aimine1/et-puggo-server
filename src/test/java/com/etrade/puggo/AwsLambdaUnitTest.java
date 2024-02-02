package com.etrade.puggo;

import com.etrade.puggo.common.enums.PaymentTypeEnum;
import com.etrade.puggo.third.aws.PaymentLambdaFunctions;
import com.etrade.puggo.third.aws.pojo.CreateInvoiceReq;
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
        // {"accountId":"acct_1OfFlBBSebdESxTR","accountLinkURL":"https://connect.stripe.com/setup/e/acct_1OfFlBBSebdESxTR/vYRusLhiNuMb"}
        System.out.println(paymentLambdaFunctions.createSellerAccount("everythingtradeltd.verify@gmail.com"));
    }


    @Test
    void createSellerAccountLink() {
        paymentLambdaFunctions.createSellerAccountLink("acct_1OfFlBBSebdESxTR");
    }


    @Test
    void createInvoice() {
        CreateInvoiceReq req = new CreateInvoiceReq();
        req.setSellerAccountId("acct_1OfFlBBSebdESxTR");
        req.setPaymentMethodId("pm_1Oc58tGjtDrE34Dqaqpm2LEU");
        req.setCustomerId("cus_PQ4RwjPZ130DJG");
        req.setPaymentType(PaymentTypeEnum.card.name());
        req.setAmount(new BigDecimal(1));
        req.setTax(new BigDecimal("0.8"));
        req.setFees(new BigDecimal(18));
        req.setToken("tok_1Oc58tGjtDrE34Dqs7BceJv6");

        paymentLambdaFunctions.createInvoice(req);
    }
}
