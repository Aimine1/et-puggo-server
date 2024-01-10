package com.etrade.puggo.dao.ai;

import static com.etrade.puggo.db.Tables.AI_AVAILABLE_BALANCE;

import com.etrade.puggo.dao.BaseDao;
import org.springframework.stereotype.Repository;

/**
 * @author niuzhenyu
 * @description : AI鉴定可用次数
 * @date 2023/9/16 17:24
 **/
@Repository
public class AiAvailableBalanceDao extends BaseDao {

    public Integer getAvailableBalance(Integer kindId) {
        return db.select(AI_AVAILABLE_BALANCE.AVAILABLE_TIMES).from(AI_AVAILABLE_BALANCE)
            .where(AI_AVAILABLE_BALANCE.KIND_ID.eq(kindId)).fetchAnyInto(Integer.class);
    }


    public void update(Integer kindId, Integer availableBalance) {
        db.insertInto(AI_AVAILABLE_BALANCE)
            .columns(
                AI_AVAILABLE_BALANCE.KIND_ID,
                AI_AVAILABLE_BALANCE.USED_TIMES,
                AI_AVAILABLE_BALANCE.AVAILABLE_TIMES
            )
            .values(kindId, 0, availableBalance)
            .onDuplicateKeyUpdate()
            .set(AI_AVAILABLE_BALANCE.AVAILABLE_TIMES, availableBalance)
            .execute();
    }


    public Integer deductAvailableBalance(Integer kindId) {
        return db.update(AI_AVAILABLE_BALANCE)
            .set(AI_AVAILABLE_BALANCE.AVAILABLE_TIMES, AI_AVAILABLE_BALANCE.AVAILABLE_TIMES.sub(1))
            .where(AI_AVAILABLE_BALANCE.KIND_ID.eq(kindId).and(AI_AVAILABLE_BALANCE.AVAILABLE_TIMES.ge(1)))
            .execute();
    }
}
