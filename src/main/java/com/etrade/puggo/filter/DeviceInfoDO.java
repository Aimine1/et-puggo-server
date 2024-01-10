package com.etrade.puggo.filter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author niuzhenyu
 * @description : 设备信息
 * @date 2023/5/26 23:15
 **/
@Data
@ApiModel("设备信息")
public class DeviceInfoDO {

    /**
     * 操作系统版本
     */
    @NotBlank(message = "osVersion is empty")
    @ApiModelProperty(value = "操作系统版本", required = true)
    private String osVersion;

    /**
     * 操作系统名称
     */
    @NotBlank(message = "osName is empty")
    @ApiModelProperty(value = "操作系统版本", required = true)
    private String osName;

    /**
     * 设备型号
     */
    @NotBlank(message = "deviceModel is empty")
    @ApiModelProperty(value = "操作系统版本", required = true)
    private String deviceModel;

    /**
     * 设备生产厂商
     */
    @NotBlank(message = "deviceVendor is empty")
    @ApiModelProperty(value = "操作系统版本", required = true)
    private String deviceVendor;

    /**
     * 设备唯一标识
     */
    @NotBlank(message = "deviceId is empty")
    @ApiModelProperty(value = "操作系统版本", required = true)
    private String deviceId;
}
