package com.etrade.puggo.dao.user;


import static com.etrade.puggo.db.Tables.USER_ACCOUNT;

import com.etrade.puggo.dao.BaseDao;
import com.etrade.puggo.db.tables.records.UserAccountRecord;
import com.etrade.puggo.service.account.UserAccountParam;
import com.etrade.puggo.utils.OptionalUtils;
import java.time.LocalDateTime;
import org.springframework.stereotype.Repository;

/**
 * @author niuzhenyu
 * @description : 用户账号注册、登录
 * @date 2023/5/22 10:16
 **/
@Repository
public class UserAccountDao extends BaseDao {

    public UserAccountRecord findUserAccount(String account) {
        return db.select(USER_ACCOUNT.USER_ID, USER_ACCOUNT.ACCOUNT, USER_ACCOUNT.PASSWORD, USER_ACCOUNT.DELETED,
                USER_ACCOUNT.SALT, USER_ACCOUNT.ID)
            .from(USER_ACCOUNT)
            .where(USER_ACCOUNT.ACCOUNT.eq(account))
            .fetchAnyInto(UserAccountRecord.class);
    }

    public UserAccountRecord findUserAccount(Long userId) {
        return db.select(USER_ACCOUNT.USER_ID, USER_ACCOUNT.ACCOUNT, USER_ACCOUNT.PASSWORD, USER_ACCOUNT.DELETED,
                USER_ACCOUNT.SALT, USER_ACCOUNT.ID)
            .from(USER_ACCOUNT)
            .where(USER_ACCOUNT.USER_ID.eq(userId))
            .fetchAnyInto(UserAccountRecord.class);
    }

    public void updateLoginInfo(long id, String ip, String address, LocalDateTime time, String device) {
        db.update(USER_ACCOUNT)
            .set(USER_ACCOUNT.LAST_LOGIN_IP, OptionalUtils.valueOrDefault(ip))
            .set(USER_ACCOUNT.LAST_LOGIN_ADDRESS, OptionalUtils.valueOrDefault(address))
            .set(USER_ACCOUNT.LAST_LOGIN_TIME, time)
            .set(USER_ACCOUNT.LAST_LOGIN_DEVICE, device)
            .where(USER_ACCOUNT.ID.eq(id))
            .execute();
    }

    public Long insertNewAccount(UserAccountParam param) {
        return db.insertInto(USER_ACCOUNT)
            .columns(
                USER_ACCOUNT.USER_ID,
                USER_ACCOUNT.ACCOUNT,
                USER_ACCOUNT.PASSWORD,
                USER_ACCOUNT.SALT)
            .values(
                OptionalUtils.valueOrDefault(param.getUserId()),
                OptionalUtils.valueOrDefault(param.getAccount()),
                OptionalUtils.valueOrDefault(param.getPassword()),
                OptionalUtils.valueOrDefault(param.getSalt())
            )
            .returning(USER_ACCOUNT.ID).fetchOne().getId();
    }


    public void updatePassword(long id, String password, String salt) {
        db.update(USER_ACCOUNT)
            .set(USER_ACCOUNT.PASSWORD, OptionalUtils.valueOrDefault(password))
            .set(USER_ACCOUNT.SALT, OptionalUtils.valueOrDefault(salt))
            .where(USER_ACCOUNT.ID.eq(id))
            .execute();
    }


    public void delete(Long userId) {
        db.delete(USER_ACCOUNT).where(USER_ACCOUNT.USER_ID.eq(userId)).execute();
    }
}
