package com.etrade.puggo.service.goods.sales.pojo;

import com.etrade.puggo.service.goods.publish.PublishGoodsParam.DeliveryTypeDTO;
import com.etrade.puggo.service.groupon.dto.S3Picture;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Data;

/**
 * @author niuzhenyu
 * @description : 商品详细信息
 * @date 2023/6/15 17:45
 **/
@Data
@ApiModel("商品详细信息")
public class GoodsDetailVO extends GoodsSimpleVO {

    @ApiModelProperty("图片列表")
    private List<S3Picture> pictureList;

    @ApiModelProperty("收藏数量")
    private Integer likeNumber;

    @ApiModelProperty("浏览数量")
    private Integer browseNumber;

    @ApiModelProperty("评论数量")
    private Integer commentNumber;

    @ApiModelProperty("分类")
    private String classPath;

    @ApiModelProperty("分类名称")
    private String className;

    @ApiModelProperty("分类ID")
    private Integer classId;

    @JsonIgnore
    private Byte deliveryType;

    @ApiModelProperty(value = "交易方式")
    private DeliveryTypeDTO deliveryTypeObj;

    @ApiModelProperty(value = "品牌")
    private String brand;

    @ApiModelProperty(value = "AI鉴定编号，如果为空证明未鉴定或者鉴定失败")
    private String aiIdentifyNo;
}
