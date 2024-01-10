package com.etrade.puggo.dao.user;

import static com.etrade.puggo.db.Tables.USER;
import static com.etrade.puggo.db.Tables.USER_IM_ACTION;

import com.etrade.puggo.common.page.PageContentContainer;
import com.etrade.puggo.dao.BaseDao;
import com.etrade.puggo.service.account.SellerInfoVO;
import com.etrade.puggo.service.account.UserInfoVO;
import com.etrade.puggo.service.account.WebUserSearchParam;
import com.etrade.puggo.service.goods.sales.LaunchUserDO;
import com.etrade.puggo.utils.SQLUtils;
import com.etrade.puggo.utils.StrUtils;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.jooq.SelectJoinStep;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Repository;

/**
 * @author niuzhenyu
 * @description : 用户表
 * @date 2023/5/22 15:08
 **/
@Repository
@RefreshScope
public class UserDao extends BaseDao {

    private static final byte IS_VERIFIED = 1;

    @Value("${user.avatar.default:}")
    private String avatar;

    public Long insertNewUser(String nickname, String email, String role) {
        return db
            .insertInto(USER, USER.NICKNAME, USER.NICKNAME_LAST_TIME, USER.EMAIL, USER.USER_ROLE, USER.JOIN_DATE,
                USER.AVATAR)
            .values(nickname, LocalDateTime.now(), email, role, LocalDate.now(), this.avatar)
            .returning(USER.ID).fetchOne().getId();
    }


    public Long findUserIdByNickname(String nickname) {
        return db.select(USER.ID).from(USER).where(USER.NICKNAME.eq(nickname)).fetchAnyInto(Long.class);
    }

    public Long findUserIdByEmail(String email) {
        return db.select(USER.ID).from(USER).where(USER.EMAIL.eq(email)).fetchAnyInto(Long.class);
    }

    public UserInfoVO findUserInfo(Long userId) {
        return db.select(
                USER.USER_ROLE,
                USER.NICKNAME,
                USER.AVATAR,
                USER.EMAIL,
                USER.JOIN_DATE,
                USER.CREDIT_RATING,
                USER.PHONE,
                DSL.iif(USER.IS_VERIFIED.eq(IS_VERIFIED), true, false).as("isVerified"),
                USER.COUNTRY.concat(" ").concat(USER.PROVINCE).concat(" ").concat(USER.CITY).as("region")
            )
            .from(USER).where(USER.ID.eq(userId)).fetchAnyInto(UserInfoVO.class);
    }


    public SellerInfoVO findSellerInfo(Long userId) {
        return db.select(
                USER.USER_ROLE,
                USER.NICKNAME,
                USER.AVATAR,
                USER.EMAIL,
                USER.JOIN_DATE,
                USER.CREDIT_RATING,
                DSL.iif(USER.IS_VERIFIED.eq(IS_VERIFIED), true, false).as("isVerified"),
                USER.COUNTRY.concat(" ").concat(USER.PROVINCE).concat(" ").concat(USER.CITY).as("region")
            )
            .from(USER).where(USER.ID.eq(userId)).fetchAnyInto(SellerInfoVO.class);
    }


    public int updateUserAvatar(Long userId, String url) {
        return db.update(USER)
            .set(USER.AVATAR, url)
            .where(USER.ID.eq(userId))
            .execute();
    }


    public List<LaunchUserDO> findUserList(List<Long> userIdList) {
        return db.select(USER.ID.as("userId"), USER.AVATAR, USER.NICKNAME, USER_IM_ACTION.ACCID)
            .from(USER)
            .leftJoin(USER_IM_ACTION)
            .on(USER_IM_ACTION.USER_ID.eq(USER.ID))
            .where(USER.ID.in(ascendingOrder(userIdList)))
            .fetchInto(LaunchUserDO.class);
    }


    public PageContentContainer<UserInfoVO> findUserPageList(WebUserSearchParam param) {
        SelectJoinStep<?> sql = db.select(
                USER.ID.as("userId"),
                USER.USER_ROLE,
                USER.NICKNAME,
                USER.AVATAR,
                USER.EMAIL,
                USER.JOIN_DATE,
                USER.CREDIT_RATING,
                USER.PHONE,
                DSL.iif(USER.IS_VERIFIED.eq(IS_VERIFIED), true, false).as("isVerified"),
                USER.COUNTRY.concat(" ").concat(USER.PROVINCE).concat(" ").concat(USER.CITY).as("region")
            )
            .from(USER);

        if (!StrUtils.isBlank(param.getNickname())) {
            sql.where(USER.NICKNAME.like(SQLUtils.suffixLike(param.getNickname())));
        }

        return getPageResult(sql, param, UserInfoVO.class);
    }


    public Integer updateUserVerified(Long userId, Byte isVerified) {
        return db.update(USER).set(USER.IS_VERIFIED, isVerified).where(USER.ID.eq(userId)).execute();
    }


    public void delete(Long userId) {
        db.delete(USER).where(USER.ID.eq(userId)).execute();
    }
}
