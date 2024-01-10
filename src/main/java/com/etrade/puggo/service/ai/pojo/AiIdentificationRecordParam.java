package com.etrade.puggo.service.ai.pojo;

import com.etrade.puggo.common.page.PageParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author niuzhenyu
 * @description : ai鉴定记录
 * @date 2023/10/20 19:29
 **/
@Data
public class AiIdentificationRecordParam extends PageParam {

    @ApiModelProperty("ai鉴定记录状态，WAITING=等待鉴定，COMPLETE=已鉴定")
    private String state;

}
