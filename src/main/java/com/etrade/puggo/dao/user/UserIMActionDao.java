package com.etrade.puggo.dao.user;

import static com.etrade.puggo.db.Tables.USER_IM_ACTION;

import com.etrade.puggo.dao.BaseDao;
import com.etrade.puggo.db.tables.records.UserImActionRecord;
import org.springframework.stereotype.Repository;

/**
 * @author niuzhenyu
 * @description : 网易云信账号
 * @date 2023/6/21 10:43
 **/
@Repository
public class UserIMActionDao extends BaseDao {


    private static final Long SYSTEM_USER = -1L;

    public Long save(UserImActionRecord record) {
        return db.insertInto(
                USER_IM_ACTION,
                USER_IM_ACTION.USER_ID,
                USER_IM_ACTION.ACCID,
                USER_IM_ACTION.NAME,
                USER_IM_ACTION.TOKEN)
            .values(
                record.getUserId(),
                record.getAccid(),
                record.getName(),
                record.getToken()
            )
            .returning(USER_IM_ACTION.ID).fetchOne().getId();
    }


    public int updateToken(Long userId, String token) {
        return db.update(USER_IM_ACTION).set(USER_IM_ACTION.TOKEN, token).where(USER_IM_ACTION.USER_ID.eq(userId))
            .execute();
    }


    public UserImActionRecord findIMAction(Long userId) {
        return db.select(USER_IM_ACTION.ACCID, USER_IM_ACTION.USER_ID, USER_IM_ACTION.TOKEN, USER_IM_ACTION.NAME)
            .from(USER_IM_ACTION)
            .where(USER_IM_ACTION.USER_ID.eq(userId))
            .fetchAnyInto(UserImActionRecord.class);
    }


    public UserImActionRecord findSystemIMAction() {
        return findIMAction(SYSTEM_USER);
    }

}
