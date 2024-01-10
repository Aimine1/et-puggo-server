package com.etrade.puggo.common.weblog;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description http请求统一日志
 * @Author liuyuqing
 * @Date 2021/6/25 14:51
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WebLog {

    /**
     * 如果入参里含有非文本（比如导入功能），需要设为false
     */
    boolean isNeedRequestArg() default true;

    /**
     * 如果出参里含有非文本（比如导出、下载功能），需要设为false
     */
    boolean isNeedResponseArg() default true;
}
