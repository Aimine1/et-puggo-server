package com.etrade.puggo.dao.comment;

import com.etrade.puggo.common.constants.CommentConstants;
import com.etrade.puggo.dao.BaseDao;
import com.etrade.puggo.db.tables.records.StatisticsUserCommentScoreRecord;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static com.etrade.puggo.db.Tables.STATISTICS_USER_COMMENT_SCORE;

/**
 * @author zhenyu
 * @version 1.0
 * @description: 用户评分统计
 * @date 2024/1/23 16:47
 */
@Repository
public class StatisticsUserCommentScoreDao extends BaseDao {


    public void saveOrUpdate(long userId, BigDecimal score, int identity) {
        StatisticsUserCommentScoreRecord record = getOneRecord(userId);
        if (record == null) {
            save(userId, score, identity);
        } else {
            update(userId, score, identity, record);
        }
    }


    public StatisticsUserCommentScoreRecord getOneRecord(long userId) {
        return db
                .select(STATISTICS_USER_COMMENT_SCORE.ID,
                        STATISTICS_USER_COMMENT_SCORE.AVERAGE_SCORE,
                        STATISTICS_USER_COMMENT_SCORE.TOTAL_COMMENT,
                        STATISTICS_USER_COMMENT_SCORE.TOTAL_SCORE,
                        STATISTICS_USER_COMMENT_SCORE.FROM_CUSTOMER_SCORE,
                        STATISTICS_USER_COMMENT_SCORE.FROM_SELLER_SCORE,
                        STATISTICS_USER_COMMENT_SCORE.TOTAL_FROM_CUSTOMER_COMMENT,
                        STATISTICS_USER_COMMENT_SCORE.TOTAL_FROM_SELLER_COMMENT
                )
                .from(STATISTICS_USER_COMMENT_SCORE)
                .where(STATISTICS_USER_COMMENT_SCORE.USER_ID.eq(userId))
                .fetchAnyInto(StatisticsUserCommentScoreRecord.class);
    }


    private void update(long userId, BigDecimal score, int identity, StatisticsUserCommentScoreRecord record) {
        record.setTotalComment(record.getTotalComment() + 1);
        record.setTotalScore(record.getTotalScore().add(score));
        record.setAverageScore(record.getTotalScore().divide(new BigDecimal(record.getTotalComment()), 2, RoundingMode.HALF_UP));
        if (identity == CommentConstants.IDENTITY_BUYER) {
            record.setFromCustomerScore(record.getFromCustomerScore().add(score));
            record.setTotalFromCustomerComment(record.getTotalFromCustomerComment() + 1);
        }
        if (identity == CommentConstants.IDENTITY_SELLER) {
            record.setFromSellerScore(record.getFromSellerScore().add(score));
            record.setTotalFromSellerComment(record.getTotalFromSellerComment() + 1);
        }
        db.update(STATISTICS_USER_COMMENT_SCORE).set(record)
                .where(STATISTICS_USER_COMMENT_SCORE.USER_ID.eq(userId)).execute();
    }


    public void save(long userId, BigDecimal score, int identity) {
        StatisticsUserCommentScoreRecord record = db.newRecord(STATISTICS_USER_COMMENT_SCORE);
        record.setUserId(userId);
        record.setAverageScore(score);
        record.setTotalComment(1);
        record.setTotalScore(score);
        if (identity == CommentConstants.IDENTITY_BUYER) {
            record.setFromCustomerScore(score);
            record.setTotalFromCustomerComment(1);
        }
        if (identity == CommentConstants.IDENTITY_SELLER) {
            record.setFromSellerScore(score);
            record.setTotalFromSellerComment(1);
        }
        record.insert();
    }

}
