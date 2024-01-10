package com.etrade.puggo.service.goods.publish;

import com.etrade.puggo.third.aws.S3PutObjectResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * @author niuzhenyu
 * @description : 发布新产品
 * @date 2023/5/24 16:06
 **/
@Data
@ApiModel("商品发布参数")
public class PublishGoodsParam {

    @ApiModelProperty(value = "分类级别Path", required = true)
    protected String classPath;

    @NotBlank(message = "quality is empty")
    @NotNull(message = "quality is empty")
    @ApiModelProperty(value = "商品成色", required = true)
    protected String quality;

    @ApiModelProperty(value = "原价/划线价")
    protected BigDecimal originalPrice;

    @ApiModelProperty(value = "实际价格, isFree=false 时必须")
    protected BigDecimal realPrice;

    @NotNull(message = "isFree is null")
    @ApiModelProperty(value = "是否免费", required = true)
    protected Boolean isFree;

    @NotNull(message = "description is empty")
    @NotBlank(message = "description is empty")
    @Length(max = 1000, min = 1)
    @ApiModelProperty(value = "商品描述，最多1000字+标点符号", required = true)
    protected String description;

    @ApiModelProperty(value = "商品title，如果为空则截取description", required = false)
    private String title;

    @NotNull(message = "isDraft is null")
    @ApiModelProperty(value = "是否存为草稿", required = true)
    protected Boolean isDraft;

    @NotNull(message = "deliveryType is null")
    @ApiModelProperty(value = "交易方式：1邮寄 2面交 3两者均可", required = true)
    @Min(value = 1, message = "未知的交易方式")
    @Max(value = 3, message = "未知的交易方式")
    protected Byte deliveryType;

    @ApiModelProperty(value = "品牌, 选填")
    protected String brand;

    @ApiModelProperty(value = "AI鉴定编号，选填")
    protected String aiIdentifyNo;

    @Valid
    @Size(max = 9, min = 1)
    @ApiModelProperty("商品图片列表，最多支持上传5张图片，最少上传1张图片")
    protected List<S3PutObjectResult> goodsPicturesList;

}
