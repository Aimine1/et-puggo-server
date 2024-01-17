package com.etrade.puggo.service.goods.sales.pojo;

import com.etrade.puggo.common.page.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author niuzhenyu
 * @description : 商品搜索参数
 * @date 2023/6/15 17:20
 **/
@Data
@ApiModel("商品搜索参数")
public class GoodsSearchParam extends PageParam {

    @ApiModelProperty("商品title")
    private String title;

    @ApiModelProperty(value = "筛选的标签，比如:热门、最近、附近、关注，可选项。后端根据searchLabel按照不同的算法筛选合适的商品")
    private String searchLabel;

    @ApiModelProperty(value = "分类path，可选项")
    private String classPath;

    @ApiModelProperty("【新增】排序选项，可选项：价格/price（默认正序）、发布日期/date listed（默认倒序）")
    private String sortKey;

    @ApiModelProperty("【新增】筛选选项，可选项：已鉴定/Authenticated Only、非预留/Unreserved Only")
    private String searchKey;

}
