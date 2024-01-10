package com.etrade.puggo.third.im.pojo;

import lombok.Data;

/**
 * @author niuzhenyu
 * @description : TODO
 * @date 2023/7/6 14:13
 **/
@Data
public class MessageDO {

    private String from;

    private String to;

    private Integer ope;

    private Integer type;

    private String body;

}
