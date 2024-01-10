package com.etrade.puggo.service.groupon.admin;

import com.etrade.puggo.common.page.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author niuzhenyu
 * @description : 团购券列表参数
 * @date 2023/6/6 15:39
 **/
@Data
@ApiModel("团购券列表参数")
public class GrouponListParam extends PageParam {

    @ApiModelProperty(value = "分类id，可选项")
    private Integer classId;

    @ApiModelProperty(value = "title")
    private String title;

}
