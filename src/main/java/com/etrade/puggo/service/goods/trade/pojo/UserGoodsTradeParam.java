package com.etrade.puggo.service.goods.trade.pojo;

import com.etrade.puggo.common.page.PageParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author niuzhenyu
 * @description : 用户交易
 * @date 2023/10/19 21:07
 **/
@Data
public class UserGoodsTradeParam extends PageParam {

    @ApiModelProperty("商品状态，TO_USE=待完成，COMPLETE=已完成，不存默认全部")
    private String state;

}
