package com.etrade.puggo.dao.setting;

import com.etrade.puggo.dao.BaseDao;
import com.etrade.puggo.db.tables.records.UserProfileRecord;
import com.etrade.puggo.service.setting.pojo.SettingsVO;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.etrade.puggo.db.Tables.USER_PROFILE;


/**
 * @author zhenyu
 * @version 1.0
 * @description: 个人偏好设置
 * @date 2024/1/20 21:47
 */
@Repository
public class UserProfileDao extends BaseDao {

    public long saveOrUpdate(Long userId, String key, String val, String comment) {
        UserProfileRecord record = newRecord(userId, key, val, comment);
        return db.insertInto(USER_PROFILE).set(record).returning(USER_PROFILE.ID).fetchOne().getId();
    }


    public List<SettingsVO> list(Long userId) {
        return db.select(USER_PROFILE.KEY, USER_PROFILE.VALUE, USER_PROFILE.COMMENT)
                .from(USER_PROFILE).where(USER_PROFILE.USER_ID.eq(userId)).fetchInto(SettingsVO.class);
    }


    public String get(Long userId, String key) {
        return db.select(USER_PROFILE.VALUE)
                .from(USER_PROFILE).where(USER_PROFILE.USER_ID.eq(userId).and(USER_PROFILE.KEY.eq(key)))
                .fetchAnyInto(String.class);
    }


    private UserProfileRecord newRecord(Long userId, String key, String val, String comment) {
        UserProfileRecord record = new UserProfileRecord();
        record.setUserId(userId);
        record.setKey(key);
        record.setValue(val);
        record.setComment(comment);
        return record;
    }

}
