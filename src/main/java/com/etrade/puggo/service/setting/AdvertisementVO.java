package com.etrade.puggo.service.setting;

import com.etrade.puggo.service.groupon.dto.S3Picture;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author niuzhenyu
 * @description : 广告
 * @date 2023/11/14 21:03
 **/
@Data
@ApiModel("广告")
public class AdvertisementVO extends S3Picture {

    @ApiModelProperty("跳转链接")
    private String jumpUrl;

    @ApiModelProperty("跳转类型")
    private String jumpType;

}
