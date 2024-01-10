package com.etrade.puggo.common.exception;

/**
 * @Description 错误码与错误信息获取接口
 * @Author niuzhenyu
 * @Date 2023-05-19
 **/
public interface ErrorMsg {

    /**
     * 获取错误码
     *
     * @author niuzhenyu
     * @lastEditor niuzhenyu
     * @createTime 2023/5/19 20:01
     * @editTime 2023/5/19 20:01
     **/
    int getCode();

    /**
     * 获取错误信息
     *
     * @author niuzhenyu
     * @lastEditor niuzhenyu
     * @createTime 2023/5/19 20:01
     * @editTime 2023/5/19 20:01
     **/
    String getMsg();
}
