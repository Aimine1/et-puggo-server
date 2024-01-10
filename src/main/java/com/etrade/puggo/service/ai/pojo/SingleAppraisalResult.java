package com.etrade.puggo.service.ai.pojo;

import lombok.Data;

/**
 * @author niuzhenyu
 * @description : 单鉴定点结果
 * @date 2023/9/16 19:22
 **/
@Data
public class SingleAppraisalResult {

    private Byte genuine;

    private String said;

    private Integer pointId;

}
