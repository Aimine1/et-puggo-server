package com.etrade.puggo.service.groupon;

import com.etrade.puggo.service.groupon.dto.S3Picture;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author niuzhenyu
 * @description : 团购券分类
 * @date 2023/6/6 16:30
 **/
@Data
@ApiModel("团购券分类")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GrouponClassDTO {

    @ApiModelProperty("分类ID")
    private Integer id;

    @ApiModelProperty("分类名称")
    private String className;

    @ApiModelProperty("分类path")
    private String classPath;

    @ApiModelProperty("分类等级")
    private Byte classLevel;

    @ApiModelProperty("icon url")
    private S3Picture icon;

    @JsonIgnore
    @ApiModelProperty("icon id")
    private Long iconId;

    @ApiModelProperty("子级分类")
    private List<GrouponClassDTO> children;

}
