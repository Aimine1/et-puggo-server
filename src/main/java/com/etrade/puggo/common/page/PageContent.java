/*
 * Copyright 2019-2020 北京掌上先机网络科技有限公司
 */

package com.etrade.puggo.common.page;

import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhengbaole
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageContent<T> implements PageContentContainer<T> {

    protected Integer total = 0;
    protected List<T> list = Collections.emptyList();
}
