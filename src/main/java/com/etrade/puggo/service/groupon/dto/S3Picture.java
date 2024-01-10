package com.etrade.puggo.service.groupon.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author niuzhenyu
 * @description : S3图片
 * @date 2023/6/13 22:23
 **/
@Data
@ApiModel("上传的图片")
public class S3Picture {

    @JsonIgnore
    @ApiModelProperty("上传时不填")
    private Long id;

    @JsonIgnore
    @ApiModelProperty("上传时不填")
    private Long targetId;

    @ApiModelProperty("上传时不填")
    private Byte isMain;

    @NotNull(message = "图片必须")
    @NotBlank(message = "图片必须")
    private String url;

    @NotNull(message = "图片必须")
    @NotBlank(message = "图片必须")
    private String key;

    @NotNull(message = "图片必须")
    @NotBlank(message = "图片必须")
    private String versionId;

}