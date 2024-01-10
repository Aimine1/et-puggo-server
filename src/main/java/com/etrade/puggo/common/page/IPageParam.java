/*
 * Copyright 2019-2020 北京掌上先机网络科技有限公司
 */

package com.etrade.puggo.common.page;

/**
 * @author zhengbaole
 */
public interface IPageParam {

    /**
     * 当前页码
     */
    Integer getPage();

    /**
     * 页容量
     */
    Integer getSize();
}
