package com.etrade.puggo.third.aws.pojo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author zhenyu
 * @version 1.0
 * @description: 发票
 * @date 2024/1/29 11:11
 */
@Data
public class CreateInvoiceReq {

    private String customerId;

    private String sellerAccountId;

    private String paymentMethodId;

    private String paymentType;

    private BigDecimal amount;

    private BigDecimal fees;

    private BigDecimal tax;

}
