package com.etrade.puggo.service.goods.sales.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * @author niuzhenyu
 * @description : 商品搜索参数
 * @date 2023/6/15 17:20
 **/
@Data
@ApiModel("商品列表简洁展示")
public class GoodsSimpleVO {


    @ApiModelProperty("商品唯一ID")
    protected Long goodsId;

    @ApiModelProperty("商品title, 截取于商品简介前20个字符")
    protected String title;

    @ApiModelProperty("原价格/划线价格")
    protected BigDecimal originalPrice;

    @ApiModelProperty("实际价格")
    protected BigDecimal realPrice;

    @ApiModelProperty("货币种类")
    protected String moneyKind;

    @ApiModelProperty("成色")
    protected String quality;

    @ApiModelProperty("最后发布时间")
    protected LocalDateTime launchLastTime;

    @ApiModelProperty("发布用户id")
    protected Long launchUserId;

    @ApiModelProperty("发布用户昵称")
    protected String launchUserNickname;

    @ApiModelProperty("主图URL")
    protected String mainImgUrl;

    @ApiModelProperty("用户是否收藏了该商品")
    protected Boolean isLike;

    @ApiModelProperty("是否时免费商品")
    private Boolean isFree;

    @ApiModelProperty("发布人信息")
    protected LaunchUserDO launchUser;

    @ApiModelProperty("ai鉴定编号，如果为空表示没有鉴定或者鉴定失败")
    protected String aiIdentifyNo;

    @ApiModelProperty("货品状态")
    protected String state;

    @ApiModelProperty("商品简介")
    protected String description;
}
