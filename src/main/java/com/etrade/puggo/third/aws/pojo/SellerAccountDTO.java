package com.etrade.puggo.third.aws.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * @author zhenyu
 * @version 1.0
 * @description: 商家支付账号
 * @date 2024/2/2 16:29
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SellerAccountDTO {

    private String accountId;

    private String accountLinkURL;

}
