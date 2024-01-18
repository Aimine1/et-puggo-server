package com.etrade.puggo.third.aws.pojo;

import lombok.Data;

/**
 * @author niuzhenyu
 * @description : 创建用户
 * @date 2024/1/15 23:34
 **/
@Data
public class CreateCustomerReq {

    private String Email;

    private String Description;

}
