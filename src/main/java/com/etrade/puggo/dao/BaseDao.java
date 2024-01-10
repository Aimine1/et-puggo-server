package com.etrade.puggo.dao;


import com.alibaba.nacos.common.utils.CollectionUtils;
import com.etrade.puggo.common.page.PageContent;
import com.etrade.puggo.common.page.PageContentContainer;
import com.etrade.puggo.common.page.PageParam;
import com.etrade.puggo.service.BaseService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import javax.annotation.Resource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.DeleteConditionStep;
import org.jooq.SelectLimitStep;
import org.jooq.UpdateConditionStep;
import org.springframework.stereotype.Repository;

/**
 * @Description Base dao
 * @Author zhengbaole
 * @Date 2020/11/11
 **/
@Slf4j
@Repository
public class BaseDao extends BaseService {

    @Resource(name = "dslContext")
    protected DSLContext db;

    protected <T> PageContentContainer<T> getPageResult(SelectLimitStep<?> query, PageParam param,
        Class<T> clz) {
        return getPageResult(query, param.getPageIndex(), param.getPageSize(), clz);
    }

    protected <T> PageContentContainer<T> getPageResult(
        SelectLimitStep<?> query, PageParam param, Class<T> clz, Consumer<T> processor) {
        PageContentContainer<T> pageResult = getPageResult(query, param.getPageIndex(),
            param.getPageSize(), clz);
        List<T> list = pageResult.getList();
        list.forEach(processor);
        return pageResult;
    }

    protected <T> PageContentContainer<T> getPageResult(SelectLimitStep<?> query, PageParam param,
        Class<T> clz,
        PageContentContainer<T> container) {
        return getPageResult(query, param.getPageIndex(), param.getPageSize(), clz, container);
    }

    /**
     * 推荐使用 {@link #getPageResult(SelectLimitStep, PageParam, Class)}
     *
     * @deprecated 推荐入参类型继承 {@link PageParam} 类
     */
    @Deprecated
    protected <T> PageContentContainer<T> getPageResult(SelectLimitStep<?> select,
        Integer currentPage,
        Integer pageRows, Class<T> clazz) {
        return getPageResult(select, currentPage, pageRows, clazz, null);
    }

    /**
     * 推荐使用 {@link #getPageResult(SelectLimitStep, PageParam, Class)}
     *
     * @deprecated 推荐入参类型继承 {@link PageParam} 类
     */
    @SneakyThrows
    @Deprecated
    private <T> PageContentContainer<T> getPageResult(SelectLimitStep<?> select,
        Integer currentPage,
        Integer pageRows, Class<T> clazz, PageContentContainer<T> container) {
        if (pageRows == null || pageRows <= 0) {
            pageRows = PageParam.DEFAULT_PAGE_ROWS;
        }
        PageContentContainer<T> result;
        if (null != container) {
            result = container;
        } else {
            result = new PageContent<>();
        }
        if (select == null) {
            result.setTotal(0);
            result.setList(Collections.emptyList());
            return result;
        }

        if (currentPage == null || currentPage < PageParam.DEFAULT_CURRENT_PAGE) {
            currentPage = PageParam.DEFAULT_CURRENT_PAGE;
        }

        Integer finalCurrentPage = currentPage;
        Integer finalPageRows = pageRows;

        final String selectSQL = select.getSQL();
        Object[] bindValues = select.getBindValues().toArray();
        CompletableFuture<Integer> countFuture = supplyAsync(
            () -> fetchCount(selectSQL, bindValues));

        CompletableFuture<List<T>> pageFuture = supplyAsync(
            () -> select.limit((finalCurrentPage - 1) * finalPageRows, (int) finalPageRows)
                .fetchInto(clazz));

        return countFuture.thenCombine(pageFuture, (countFutureResult, pageFutureResult) -> {
            result.setList(pageFutureResult);
            result.setTotal(countFutureResult);
            return result;
        }).get();
    }

    /**
     * @author zhengbaole
     * @lastEditor zhengbaole
     * @createTime 2022/3/28 2:02 PM
     * @editTime 2022/3/28 2:02 PM
     */
    protected int fetchCount(String sql, Object[] bindValues) {
        return db.selectCount().from("(" + sql + ") as `q`", bindValues)
            .fetchAnyInto(Integer.class);
    }

    public interface GetPage<T, E> {

        PageContentContainer<T> page(SelectLimitStep<?> query, E param, Class<T> clz);
    }

    /**
     * List 升序排列 查询条件 where in 条件中排序用
     *
     * @author weike
     * @lastEditor weike
     * @createTime 2021/1/28 14:46
     * @editTime
     **/
    /*protected static <T extends Comparable<? super T>> List<T> ascendingOrder(List<T> list) {
        java.util.Objects.requireNonNull(list, "Condition object list is null. ");
        Collections.sort(list);
        return list;
    }*/

    /**
     * List 升序排列 查询条件 where in 条件中排序用
     *
     * @author zhengbaole
     * @lastEditor zhengbaole
     * @createTime 2021/2/2 7:23 PM
     * @editTime
     **/
    protected static <T extends Comparable<? super T>> List<T> ascendingOrder(Collection<T> c) {
        Objects.requireNonNull(c, "Condition object list is null. ");
        ArrayList<T> list = new ArrayList<>(c);
        Collections.sort(list);
        return list;
    }


    /**
     * 批量执行更新
     */
    public void batchUpdateExecute(List<UpdateConditionStep<?>> updateConditionSteps) {
        if (CollectionUtils.isEmpty(updateConditionSteps)) {
            return;
        }
        db.batch(updateConditionSteps).execute();
    }


    /**
     * 批量执行删除
     */
    public void batchDeleteExecute(List<DeleteConditionStep<?>> deleteConditionSteps) {
        if (CollectionUtils.isEmpty(deleteConditionSteps)) {
            return;
        }
        db.batch(deleteConditionSteps).execute();
    }


}