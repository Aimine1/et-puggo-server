package com.etrade.puggo.third.aws.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * @author zhenyu
 * @version 1.0
 * @description: TODO
 * @date 2024/1/30 11:19
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentFunctionsResponse {

    private String errorMessage;

    private String errorType;
}
