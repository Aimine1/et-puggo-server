package com.etrade.puggo.service.payment.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author zhenyu
 * @version 1.0
 * @description: 支付结果
 * @date 2024/2/5 16:20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PayResult {

    private BigDecimal subtotal;

    private BigDecimal tax;

    private BigDecimal otherFees;

    private String invoiceId;

}
