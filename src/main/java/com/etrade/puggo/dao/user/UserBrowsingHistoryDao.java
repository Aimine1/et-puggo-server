package com.etrade.puggo.dao.user;

import static com.etrade.puggo.db.Tables.USER_BROWSING_HISTORY;

import com.etrade.puggo.common.page.PageContentContainer;
import com.etrade.puggo.common.page.PageParam;
import com.etrade.puggo.dao.BaseDao;
import com.etrade.puggo.service.groupon.user.UserBrowseHistoryVO;
import com.etrade.puggo.utils.DateTimeUtils;
import java.time.LocalDateTime;
import org.jooq.Record3;
import org.jooq.SelectConditionStep;
import org.springframework.stereotype.Repository;

/**
 * @author niuzhenyu
 * @description : 用户浏览历史列表
 * @date 2023/6/7 18:15
 **/
@Repository
public class UserBrowsingHistoryDao extends BaseDao {

    public PageContentContainer<UserBrowseHistoryVO> getBrowseHistory(PageParam param) {
        SelectConditionStep<Record3<Long, Long, LocalDateTime>> sql =
            db.select(
                    USER_BROWSING_HISTORY.USER_ID,
                    USER_BROWSING_HISTORY.GOODS_ID,
                    USER_BROWSING_HISTORY.LAST_BROWSE_TIME
                )
                .from(USER_BROWSING_HISTORY)
                .where(USER_BROWSING_HISTORY.USER_ID.eq(userId()));

        return getPageResult(sql, param, UserBrowseHistoryVO.class);
    }


    public Long browseGroupon(Long grouponId, Long userId) {
        Long id = db.select(USER_BROWSING_HISTORY.ID)
            .from(USER_BROWSING_HISTORY)
            .where(USER_BROWSING_HISTORY.USER_ID.eq(userId))
            .and(USER_BROWSING_HISTORY.GROUPON_ID.eq(grouponId))
            .fetchAnyInto(Long.class);

        if (id != null) {
            db.update(USER_BROWSING_HISTORY)
                .set(USER_BROWSING_HISTORY.LAST_BROWSE_TIME, DateTimeUtils.now())
                .where(USER_BROWSING_HISTORY.USER_ID.eq(userId))
                .and(USER_BROWSING_HISTORY.GROUPON_ID.eq(grouponId))
                .execute();
            return null;
        } else {
            return db.insertInto(
                    USER_BROWSING_HISTORY,
                    USER_BROWSING_HISTORY.USER_ID,
                    USER_BROWSING_HISTORY.GROUPON_ID,
                    USER_BROWSING_HISTORY.LAST_BROWSE_TIME)
                .values(userId, grouponId, DateTimeUtils.now())
                .returning(USER_BROWSING_HISTORY.ID).fetchOne().getId();
        }
    }


    public Long browseGoods(Long goodsId) {
        long userId = userId();

        Long id = db.select(USER_BROWSING_HISTORY.ID)
            .from(USER_BROWSING_HISTORY)
            .where(USER_BROWSING_HISTORY.USER_ID.eq(userId))
            .and(USER_BROWSING_HISTORY.GOODS_ID.eq(goodsId))
            .fetchAnyInto(Long.class);

        if (id != null) {
            db.update(USER_BROWSING_HISTORY)
                .set(USER_BROWSING_HISTORY.LAST_BROWSE_TIME, DateTimeUtils.now())
                .where(USER_BROWSING_HISTORY.USER_ID.eq(userId))
                .and(USER_BROWSING_HISTORY.GOODS_ID.eq(goodsId))
                .execute();
            return null;
        } else {
            return db.insertInto(
                    USER_BROWSING_HISTORY,
                    USER_BROWSING_HISTORY.USER_ID,
                    USER_BROWSING_HISTORY.GOODS_ID,
                    USER_BROWSING_HISTORY.LAST_BROWSE_TIME)
                .values(userId, goodsId, DateTimeUtils.now())
                .returning(USER_BROWSING_HISTORY.ID).fetchOne().getId();
        }
    }

}
