package com.etrade.puggo.dao.user;

import static com.etrade.puggo.db.Tables.USER;
import static com.etrade.puggo.db.Tables.USER_FANS;

import com.etrade.puggo.common.page.PageContentContainer;
import com.etrade.puggo.dao.BaseDao;
import com.etrade.puggo.service.fans.UserFansParam;
import com.etrade.puggo.service.goods.sales.pojo.LaunchUserDO;
import com.etrade.puggo.utils.SQLUtils;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Record3;
import org.jooq.SelectConditionStep;
import org.springframework.stereotype.Repository;

/**
 * @author niuzhenyu
 * @description : 用户粉丝
 * @date 2023/10/8 10:39
 **/
@Repository
public class UserFansDao extends BaseDao {

    public void follow(Long userId, Long fansUserId) {
        db.insertInto(USER_FANS)
            .columns(USER_FANS.USER_ID, USER_FANS.FANS_USER_ID)
            .values(userId, fansUserId)
            .onDuplicateKeyIgnore()
            .execute();
    }

    public void unfollow(Long userId, Long fansUserId) {
        db.deleteFrom(USER_FANS)
            .where(USER_FANS.USER_ID.eq(userId).and(USER_FANS.FANS_USER_ID.eq(fansUserId)))
            .execute();
    }


    public PageContentContainer<LaunchUserDO> findFansList(UserFansParam param, Long userId) {
        SelectConditionStep<Record3<Long, String, String>> sql =
            db.select(USER.ID.as("user_id"), USER.AVATAR, USER.NICKNAME)
                .from(USER_FANS)
                .innerJoin(USER)
                .on(USER_FANS.FANS_USER_ID.eq(USER.ID))
                .where(USER_FANS.USER_ID.eq(userId));

        if (!StringUtils.isBlank(param.getNickname())) {
            sql.and(USER.NICKNAME.like(SQLUtils.surroundingLike(param.getNickname())));
        }

        return getPageResult(sql, param, LaunchUserDO.class);
    }


    public PageContentContainer<LaunchUserDO> findFollowList(UserFansParam param, Long userId) {
        SelectConditionStep<Record3<Long, String, String>> sql =
            db.select(USER.ID.as("user_id"), USER.AVATAR, USER.NICKNAME)
                .from(USER_FANS)
                .innerJoin(USER)
                .on(USER_FANS.USER_ID.eq(USER.ID))
                .where(USER_FANS.FANS_USER_ID.eq(userId));

        if (!StringUtils.isBlank(param.getNickname())) {
            sql.and(USER.NICKNAME.like(SQLUtils.surroundingLike(param.getNickname())));
        }

        return getPageResult(sql, param, LaunchUserDO.class);
    }


    public List<LaunchUserDO> findFollowList(Long userId) {
        return db.select(USER.ID.as("user_id"), USER.AVATAR, USER.NICKNAME)
            .from(USER_FANS)
            .innerJoin(USER)
            .on(USER_FANS.USER_ID.eq(USER.ID))
            .where(USER_FANS.FANS_USER_ID.eq(userId))
            .limit(20)
            .fetchInto(LaunchUserDO.class);
    }


    public Long findOne(Long userId, Long fansUserId) {
        return db.select(USER_FANS.ID).from(USER_FANS)
            .where(USER_FANS.USER_ID.eq(userId).and(USER_FANS.FANS_USER_ID.eq(fansUserId))).fetchAnyInto(Long.class);
    }


    public Integer findFansCount(Long userId) {
        return db.selectCount()
            .from(USER_FANS)
            .where(USER_FANS.USER_ID.eq(userId))
            .fetchAnyInto(Integer.class);
    }

    public Integer findFollowCount(Long userId) {
        return db.selectCount()
            .from(USER_FANS)
            .where(USER_FANS.FANS_USER_ID.eq(userId))
            .fetchAnyInto(Integer.class);
    }

}
