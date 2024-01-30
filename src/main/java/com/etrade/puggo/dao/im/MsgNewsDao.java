package com.etrade.puggo.dao.im;

import static com.etrade.puggo.db.tables.MsgNews.MSG_NEWS;

import com.etrade.puggo.common.page.PageContentContainer;
import com.etrade.puggo.common.page.PageParam;
import com.etrade.puggo.common.constants.MsgNewsState;
import com.etrade.puggo.dao.BaseDao;
import com.etrade.puggo.db.tables.records.MsgNewsRecord;
import com.etrade.puggo.service.message.UserNewsVO;
import com.etrade.puggo.utils.DateTimeUtils;
import com.etrade.puggo.utils.OptionalUtils;
import java.time.LocalDateTime;
import org.jooq.SelectSeekStep1;
import org.springframework.stereotype.Repository;

/**
 * @author niuzhenyu
 * @description : 通知消息
 * @date 2023/6/27 11:30
 **/
@Repository
public class MsgNewsDao extends BaseDao {


    /**
     * 1=消息未读，0=消息已读
     */
    private static final Byte IS_UNREAD = 1;
    private static final Byte IS_READED = 0;


    public PageContentContainer<UserNewsVO> findUserNews(PageParam page) {
        SelectSeekStep1 sql = db
            .select(
                MSG_NEWS.ID,
                MSG_NEWS.ATTACH,
                MSG_NEWS.PUSHCONTENT,
                MSG_NEWS.PAYLOAD,
                MSG_NEWS.FROM_USER_ID,
                MSG_NEWS.PUSH_TIME,
                MSG_NEWS.IS_UNREAD,
                MSG_NEWS.GOODS_ID,
                MSG_NEWS.GOODS_MAIN_PIC
            )
            .from(MSG_NEWS)
            .where(MSG_NEWS.STATE.eq(MsgNewsState.SUCCESS).and(MSG_NEWS.TO_USER_ID.eq(userId())))
            .orderBy(MSG_NEWS.ID.desc());

        return getPageResult(sql, page, UserNewsVO.class);

    }


    public void updateState(Long id, byte state, String failReason, LocalDateTime pushTime) {
        db.update(MSG_NEWS)
            .set(MSG_NEWS.STATE, state)
            .set(MSG_NEWS.FAIL_REASON, failReason)
            .set(MSG_NEWS.PUSH_TIME, pushTime)
            .where(MSG_NEWS.ID.eq(id)).execute();
    }


    public void pushSuccess(Long id, byte state) {
        updateState(id, state, "", DateTimeUtils.now());
    }


    public void pushFail(Long id, byte state, String failReason) {
        updateState(id, state, failReason, null);
    }


    public Long save(MsgNewsRecord r) {
        return db.insertInto(MSG_NEWS)
            .columns(
                MSG_NEWS.TO_USER_ID,
                MSG_NEWS.TO_ACCOUNT,
                MSG_NEWS.FROM_USER_ID,
                MSG_NEWS.FROM_ACCOUNT,
                MSG_NEWS.STATE,
                MSG_NEWS.ATTACH,
                MSG_NEWS.PUSHCONTENT,
                MSG_NEWS.PAYLOAD,
                MSG_NEWS.GOODS_ID,
                MSG_NEWS.GOODS_MAIN_PIC,
                MSG_NEWS.IS_UNREAD
            )
            .values(
                r.getToUserId(),
                r.getToAccount(),
                r.getFromUserId(),
                r.getFromAccount(),
                r.getState(),
                r.getAttach(),
                r.getPushcontent(),
                OptionalUtils.valueOrDefault(r.getPayload()),
                r.getGoodsId(),
                r.getGoodsMainPic(),
                IS_UNREAD
            )
            .returning(MSG_NEWS.ID).fetchOne().getId();
    }


    public void readNews() {
        db.update(MSG_NEWS).set(MSG_NEWS.IS_UNREAD, IS_READED).where(MSG_NEWS.TO_USER_ID.eq(userId())).execute();
    }


    public Integer findUnreadCount() {
        return db.selectCount().from(MSG_NEWS).where(MSG_NEWS.TO_USER_ID.eq(userId()))
            .and(MSG_NEWS.IS_UNREAD.eq(IS_UNREAD)).fetchAnyInto(Integer.class);
    }
}
