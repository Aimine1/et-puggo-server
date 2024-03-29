package com.etrade.puggo.dao.comment;

import com.etrade.puggo.common.page.PageContentContainer;
import com.etrade.puggo.dao.BaseDao;
import com.etrade.puggo.db.tables.records.GoodsCommentRecord;
import com.etrade.puggo.service.goods.comment.CommentVO;
import com.etrade.puggo.service.goods.comment.CommentVO.ReplyCommentVO;
import com.etrade.puggo.service.goods.comment.UserCommentListParam;
import com.etrade.puggo.utils.OptionalUtils;
import org.jooq.Record8;
import org.jooq.SelectConditionStep;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static com.etrade.puggo.db.Tables.GOODS_COMMENT;

/**
 * @author niuzhenyu
 * @description : 商品评论
 * @date 2023/6/25 18:17
 **/
@Repository
public class GoodsCommentDao extends BaseDao {




    public static final byte IS_REPLY = 1;


    public Long save(GoodsCommentRecord record) {
        return db.insertInto(
                        GOODS_COMMENT,
                        GOODS_COMMENT.GOODS_ID,
                        GOODS_COMMENT.SCORE,
                        GOODS_COMMENT.COMMENT,
                        GOODS_COMMENT.TYPE,
                        GOODS_COMMENT.TO_USER_ID,
                        GOODS_COMMENT.FROM_USER_ID,
                        GOODS_COMMENT.IDENTITY,
                        GOODS_COMMENT.LAST_COMMENT_ID,
                        GOODS_COMMENT.IS_REPLY
                )
                .values(
                        record.getGoodsId(),
                        record.getScore(),
                        record.getComment(),
                        record.getType(),
                        record.getToUserId(),
                        record.getFromUserId(),
                        record.getIdentity(),
                        OptionalUtils.valueOrDefault(record.getLastCommentId()),
                        record.getIsReply()
                )
                .returning(GOODS_COMMENT.ID).fetchOne().getId();
    }


    public GoodsCommentRecord findLastComment(Long lastCommentId) {
        return db
                .select(
                        GOODS_COMMENT.ID,
                        GOODS_COMMENT.GOODS_ID,
                        GOODS_COMMENT.SCORE,
                        GOODS_COMMENT.IDENTITY,
                        GOODS_COMMENT.TYPE,
                        GOODS_COMMENT.FROM_USER_ID
                )
                .from(GOODS_COMMENT)
                .where(GOODS_COMMENT.ID.eq(lastCommentId))
                .fetchAnyInto(GoodsCommentRecord.class);
    }


    public PageContentContainer<CommentVO> findUserCommentList(UserCommentListParam param) {
        SelectConditionStep<Record8<Long, Long, BigDecimal, Byte, String, Byte, Long, LocalDateTime>> sql = db
                .select(
                        GOODS_COMMENT.ID.as("comment_id"),
                        GOODS_COMMENT.GOODS_ID,
                        GOODS_COMMENT.SCORE,
                        GOODS_COMMENT.IDENTITY,
                        GOODS_COMMENT.COMMENT,
                        GOODS_COMMENT.TYPE,
                        GOODS_COMMENT.FROM_USER_ID,
                        GOODS_COMMENT.CREATED.as("comment_time")
                )
                .from(GOODS_COMMENT)
                .where(GOODS_COMMENT.TO_USER_ID.eq(userId()).and(GOODS_COMMENT.IS_REPLY.ne((IS_REPLY))));

        if (param.getIdentity() != null) {
            sql.and(GOODS_COMMENT.IDENTITY.eq(param.getIdentity()));
        }

        sql.orderBy(GOODS_COMMENT.ID.desc());

        return getPageResult(sql, param, CommentVO.class);

    }


    public List<ReplyCommentVO> findReplyCommentList(List<Long> ids) {
        return db
                .select(
                        GOODS_COMMENT.IDENTITY,
                        GOODS_COMMENT.COMMENT,
                        GOODS_COMMENT.LAST_COMMENT_ID,
                        GOODS_COMMENT.CREATED.as("comment_time")
                )
                .from(GOODS_COMMENT)
                .where(GOODS_COMMENT.LAST_COMMENT_ID.in(ascendingOrder(ids)).and(GOODS_COMMENT.IS_REPLY.eq((IS_REPLY))))
                .fetchInto(ReplyCommentVO.class);
    }

}
