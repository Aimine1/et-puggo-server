package com.etrade.puggo.service.goods.trade;

import com.etrade.puggo.common.page.PageParam;
import lombok.Data;

/**
 * @author niuzhenyu
 * @description : 商品成交参数
 * @date 2023/6/30 10:40
 **/
@Data
public class GoodsTradeParam extends PageParam {

    private String title;

    private String classPath;

}
