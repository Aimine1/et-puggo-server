package com.etrade.puggo.third.aws;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author niuzhenyu
 * @description : 文件存储信息
 * @date 2023/6/5 18:30
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("文件存储信息")
public class S3PutObjectResult {

    @ApiModelProperty(value = "文件版本号", required = true)
    protected String versionId;

    @ApiModelProperty(value = "文件唯一key", required = true)
    protected String key;

    @ApiModelProperty(value = "文件存储地址", required = true)
    protected String url;

}
