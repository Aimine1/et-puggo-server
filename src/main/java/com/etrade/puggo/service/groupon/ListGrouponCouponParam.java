package com.etrade.puggo.service.groupon;

import com.etrade.puggo.common.page.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author niuzhenyu
 * @description : 团购券列表参数
 * @date 2023/6/6 15:39
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("团购券列表参数")
public class ListGrouponCouponParam extends PageParam {


    @ApiModelProperty(value = "团购券title")
    private String title;

    @ApiModelProperty(value = "分类path，可选项")
    private String classPath;

    @ApiModelProperty(value = "筛选的标签，比如:热门精选、最近浏览，可选项。后端根据searchLabel按照不同的算法筛选合适的券")
    private String searchLabel;

    @ApiModelProperty(value = "内部自己用，前端忽略")
    private List<Long> grouponList;


    public ListGrouponCouponParam(PageParam page) {
        this.pageIndex = page.getPageIndex();
        this.pageSize = page.getPageSize();
    }


}
