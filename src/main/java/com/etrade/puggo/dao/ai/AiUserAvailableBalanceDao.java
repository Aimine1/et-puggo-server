package com.etrade.puggo.dao.ai;

import com.etrade.puggo.dao.BaseDao;
import com.etrade.puggo.service.ai.pojo.AiUserAvailableBalanceVO;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.etrade.puggo.db.Tables.AI_KIND_LIST;
import static com.etrade.puggo.db.Tables.AI_USER_AVAILABLE_BALANCE;

/**
 * @author zhenyu
 * @version 1.0
 * @description: Ai鉴定 用户可用次数
 * @date 2024/2/6 15:08
 */
@Repository
public class AiUserAvailableBalanceDao extends BaseDao {


    public Integer selectAvailableBalance(Long userId, Integer kindId) {
        return db.select(AI_USER_AVAILABLE_BALANCE.AVAILABLE_TIMES)
                .from(AI_USER_AVAILABLE_BALANCE)
                .where(AI_USER_AVAILABLE_BALANCE.USER_ID.eq(userId).and(AI_USER_AVAILABLE_BALANCE.KIND_ID.eq(kindId)))
                .fetchAnyInto(Integer.class);
    }


    public void plus(Long userId, Integer kindId, Integer availableBalance) {
        db.insertInto(AI_USER_AVAILABLE_BALANCE)
                .columns(
                        AI_USER_AVAILABLE_BALANCE.USER_ID,
                        AI_USER_AVAILABLE_BALANCE.KIND_ID,
                        AI_USER_AVAILABLE_BALANCE.USED_TIMES,
                        AI_USER_AVAILABLE_BALANCE.AVAILABLE_TIMES
                )
                .values(userId, kindId, 0, availableBalance)
                .onDuplicateKeyUpdate()
                .set(AI_USER_AVAILABLE_BALANCE.AVAILABLE_TIMES, AI_USER_AVAILABLE_BALANCE.AVAILABLE_TIMES.plus(availableBalance))
                .execute();
    }


    public Integer deduct(Long userId, Integer kindId) {
        return db.update(AI_USER_AVAILABLE_BALANCE)
                .set(AI_USER_AVAILABLE_BALANCE.AVAILABLE_TIMES, AI_USER_AVAILABLE_BALANCE.AVAILABLE_TIMES.sub(1))
                .set(AI_USER_AVAILABLE_BALANCE.USED_TIMES, AI_USER_AVAILABLE_BALANCE.USED_TIMES.plus(1))
                .where(AI_USER_AVAILABLE_BALANCE.USER_ID.eq(userId)
                        .and(AI_USER_AVAILABLE_BALANCE.KIND_ID.eq(kindId))
                        .and(AI_USER_AVAILABLE_BALANCE.AVAILABLE_TIMES.ge(1)))
                .execute();
    }


    public List<AiUserAvailableBalanceVO> list(Long userId) {
        return db.select(
                        AI_USER_AVAILABLE_BALANCE.USER_ID,
                        AI_USER_AVAILABLE_BALANCE.KIND_ID,
                        AI_USER_AVAILABLE_BALANCE.USED_TIMES,
                        AI_USER_AVAILABLE_BALANCE.AVAILABLE_TIMES,
                        AI_KIND_LIST.NAME.as("kind_name")
                )
                .from(AI_USER_AVAILABLE_BALANCE)
                .innerJoin(AI_KIND_LIST)
                .on(AI_KIND_LIST.ID.eq(AI_USER_AVAILABLE_BALANCE.KIND_ID))
                .where(AI_USER_AVAILABLE_BALANCE.USER_ID.eq(userId))
                .fetchInto(AiUserAvailableBalanceVO.class);
    }

}
