package com.etrade.puggo.service.goods.sales.pojo;

import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author niuzhenyu
 * @description : 卖家家接受出价
 * @date 2023/6/25 12:10
 **/
@Data
public class AcceptPriceParam {

    @ApiModelProperty("商品id")
    @NotNull(message = "goodsId is null")
    private Long goodsId;

    @ApiModelProperty("买家出价")
    @NotNull(message = "price is null")
    private BigDecimal price;

    @ApiModelProperty("买家id")
    @NotNull(message = "customerId is null")
    private Long customerId;

}
