package com.etrade.puggo.service.groupon.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author niuzhenyu
 * @description : 适用门店
 * @date 2023/6/13 22:21
 **/
@Data
@Builder
@ApiModel("适用门店")
@AllArgsConstructor
@NoArgsConstructor
public class ApplicableShop {

    @ApiModelProperty("适用品牌")
    private String applicableBrand;

    @ApiModelProperty("适用门店名称")
    private String applicableShop;

    @ApiModelProperty("门店地址，可以使用谷歌地图可以查看到")
    private String shopAddress;

    @ApiModelProperty("门店到达方式")
    private String shopArrivalMethod;

}
