package com.etrade.puggo.service.goods.trade.pojo;

import com.etrade.puggo.service.goods.trade.pojo.GetTradeParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author zhenyu
 * @version 1.0
 * @description: 创建待支付交易订单
 * @date 2024/2/4 13:58
 */
@Data
public class SaveTradeParam extends GetTradeParam {

    @NotNull
    @ApiModelProperty("最后成交价格")
    private BigDecimal price;

}
