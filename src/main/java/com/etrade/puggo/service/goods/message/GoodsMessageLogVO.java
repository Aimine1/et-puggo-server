package com.etrade.puggo.service.goods.message;

import com.etrade.puggo.service.goods.sales.GoodsSimpleVO;
import com.etrade.puggo.service.goods.sales.LaunchUserDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import lombok.Data;

/**
 * @author niuzhenyu
 * @description : 商品会话日志VO
 * @date 2023/7/11 18:20
 **/
@Data
@ApiModel("商品会话日志VO")
public class GoodsMessageLogVO {

    @ApiModelProperty("商品信息")
    private GoodsSimpleVO goodsInfo;

    @ApiModelProperty("卖家信息")
    private LaunchUserDO sellerInfo;

    @ApiModelProperty("买家信息")
    private LaunchUserDO buyerInfo;

    @ApiModelProperty("会话状态：OFFER_PRICE=买家出价 ACCEPT_PRICE=卖家接收出价")
    private String state;

    @ApiModelProperty("是否买家对商品发表评论？")
    private Byte isGoodsComment;

    @ApiModelProperty("是否对卖家发表评论？")
    private Byte isSellerComment;

    @ApiModelProperty("是否对买家发表评论？")
    private Byte isBuyerComment;

    @ApiModelProperty("买家出价")
    private BigDecimal buyerPrice;

}
