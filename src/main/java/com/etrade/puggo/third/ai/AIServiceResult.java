package com.etrade.puggo.third.ai;

import lombok.Data;

/**
 * @author niuzhenyu
 * @description : 图灵鉴定API Result
 * @date 2023/9/10 21:24
 **/
@Data
public class AIServiceResult<T> {

    private Integer errorCode;

    private String errorMsg;

    private T result;

}
