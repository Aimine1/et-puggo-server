package com.etrade.puggo.service.groupon.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * @author niuzhenyu
 * @description : 基本信息
 * @date 2023/6/13 22:20
 **/
@Data
@ApiModel("基本信息")
public class Text {

    @ApiModelProperty("基本信息")
    private String text;

    @ApiModelProperty("创建时间")
    private LocalDateTime created;

    @JsonIgnore
    private String type;

    @JsonIgnore
    private Long grouponId;
}

