package com.etrade.puggo.dao.goods;

import static com.etrade.puggo.db.Tables.GOODS_LOGS;

import com.etrade.puggo.common.page.PageContentContainer;
import com.etrade.puggo.common.page.PageParam;
import com.etrade.puggo.dao.BaseDao;
import com.etrade.puggo.service.log.LogsBuilder;
import com.etrade.puggo.service.log.UserLogsVO;
import java.time.LocalDateTime;
import org.jooq.Record6;
import org.jooq.SelectSeekStep1;
import org.springframework.stereotype.Repository;

/**
 * @author niuzhenyu
 * @description : 商品操作日志Dao
 * @date 2023/6/28 17:37
 **/
@Repository
public class GoodsLogsDao extends BaseDao {


    public void save(LogsBuilder logs) {
        db.insertInto(GOODS_LOGS, GOODS_LOGS.GOODS_ID, GOODS_LOGS.USER_ID, GOODS_LOGS.OPERATE, GOODS_LOGS.CONTENT)
            .values(logs.getGoodsId(), logs.getUserId(), logs.getOperate(), logs.getContent())
            .execute();
    }


    public PageContentContainer<UserLogsVO> findUserGoodsLogs(PageParam param) {
        SelectSeekStep1<Record6<Long, Long, String, String, LocalDateTime, Byte>, Long> sql = db
            .select(
                GOODS_LOGS.ID,
                GOODS_LOGS.GOODS_ID,
                GOODS_LOGS.OPERATE,
                GOODS_LOGS.CONTENT,
                GOODS_LOGS.CREATED.as("logTime"),
                GOODS_LOGS.IS_UNREAD
            )
            .from(GOODS_LOGS)
            .where(GOODS_LOGS.USER_ID.eq(userId()))
            .orderBy(GOODS_LOGS.ID.desc());

        return getPageResult(sql, param, UserLogsVO.class);
    }

}
