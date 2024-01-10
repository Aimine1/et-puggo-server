package com.etrade.puggo.service.goods.publish;

import com.etrade.puggo.service.groupon.dto.S3Picture;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Data;

/**
 * @author niuzhenyu
 * @description : 商品分类等级
 * @date 2023/5/25 10:31
 **/
@Data
@ApiModel("商品分类等级")
public class GoodsClassVO {

    @ApiModelProperty("分类id")
    private Integer id;

    @ApiModelProperty("分类id")
    private Integer classId;

    @ApiModelProperty("分类path")
    private String classPath;

    @ApiModelProperty("分类级别")
    private Byte classLevel;

    @ApiModelProperty("分类名称")
    private String className;

    @ApiModelProperty("icon")
    private S3Picture icon;

    @ApiModelProperty("icon")
    private List<GoodsClassVO> children;

    @JsonIgnore
    private Long iconId;
}
