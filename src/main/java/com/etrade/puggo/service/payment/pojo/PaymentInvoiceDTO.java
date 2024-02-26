package com.etrade.puggo.service.payment.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author zhenyu
 * @version 1.0
 * @description: 支付发票
 * @date 2024/2/6 17:33
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentInvoiceDTO {

    private Long id;

    private String payNo;

    private Long userId;

    private String paymentType;

    private String paymentMethodId;

    private Integer billingAddressId;

    private Integer shippingMethod;

    private Integer paymentCardId;

    private String title;

    private BigDecimal amount;

    private BigDecimal tax;

    private BigDecimal otherFees;

    private String invoiceId;

    private String paymentSellerId;

    private String paymentIntentId;

    private String clientSecret;

    private Integer aiKindId;

    private Integer aiPlusAvailableTimes;

}
