package com.etrade.puggo.service.goods.message;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author niuzhenyu
 * @description : 商品交易聊天消息回调参数
 * @date 2023/6/25 12:10
 **/
@Data
public class TransactionMsgCallbackParam {

    @ApiModelProperty("商品id")
    @NotNull(message = "goodsId is null")
    private Long goodsId;

    @ApiModelProperty("买家最后一次出价")
    private BigDecimal price;

    @ApiModelProperty("买家用户id")
    private Long customerId;

    @ApiModelProperty("卖家用户id")
    private Long sellerId;

}
