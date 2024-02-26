package com.etrade.puggo.service.payment.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhenyu
 * @version 1.0
 * @description: 支付接口返回
 * @date 2024/2/26 15:33
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PayVO {

    private Long payId;

    private String clientSecret;

}
