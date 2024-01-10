package com.etrade.puggo.service.goods.message;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author niuzhenyu
 * @description : 买家出价回调入参
 * @date 2023/7/11 18:28
 **/
@Data
@ApiModel("买家出价回调入参")
public class BuyerOfferPriceCallbackParam {

    @NotNull(message = "卖家用户id必须")
    @ApiModelProperty(value = "卖家用户id", required = true)
    private Long sellerId;

    @NotNull(message = "商品id必须")
    @ApiModelProperty(value = "商品", required = true)
    private Long goodsId;

    @NotNull(message = "买家出价必须")
    @Min(value = 0L, message = "买家出价不能低于0")
    @ApiModelProperty(value = "买家出价", required = true)
    private BigDecimal buyerPrice;

}
