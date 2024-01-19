package com.etrade.puggo.dao.payment;

import com.etrade.puggo.dao.BaseDao;
import com.etrade.puggo.db.tables.records.PaymentCardRecord;
import com.etrade.puggo.service.payment.pojo.CreditCardDO;
import com.etrade.puggo.service.payment.pojo.CreditCardVO;
import com.etrade.puggo.service.payment.pojo.UpdateCreditCardDO;
import com.etrade.puggo.utils.OptionalUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.etrade.puggo.db.Tables.PAYMENT_CARD;

/**
 * @author zhenyu
 * @version 1.0
 * @description: 信用卡
 * @date 2024/1/19 14:45
 */
@Repository
public class CustomerCardDao extends BaseDao {

    private static final byte IS_DEFAULT = 1;


    public void save(CreditCardDO param, Long userId) {
        PaymentCardRecord record = newRecord(param, userId);
        record.insert();
    }


    public int update(UpdateCreditCardDO param, Long userId) {
        Integer cardId = param.getCardId();
        PaymentCardRecord record = newRecord(param, userId);
        return db.update(PAYMENT_CARD).set(record).where(PAYMENT_CARD.ID.eq(cardId)).execute();
    }


    public int delete(Integer id) {
        return db.deleteFrom(PAYMENT_CARD).where(PAYMENT_CARD.ID.eq(id)).execute();
    }


    public List<CreditCardVO> list(Long userId) {
        return db.select(
                        PAYMENT_CARD.ID.as("cardId"),
                        PAYMENT_CARD.CARD_NUMBER,
                        PAYMENT_CARD.CARD_NUMBER,
                        PAYMENT_CARD.TITLE,
                        PAYMENT_CARD.TYPE,
                        DSL.iif(PAYMENT_CARD.IS_DEFAULT.eq(IS_DEFAULT), true, false)
                )
                .from(PAYMENT_CARD)
                .where(PAYMENT_CARD.USER_ID.eq(userId))
                .fetchInto(CreditCardVO.class);
    }


    private PaymentCardRecord newRecord(CreditCardDO param, Long userId) {
        PaymentCardRecord record = db.newRecord(PAYMENT_CARD);
        record.setCardNumber(param.getCardNumber());
        record.setExpireYear(param.getExpireYear());
        record.setExpireMonth(param.getExpireMonth());
        record.setCvs(param.getCvc());
        record.setTitle(OptionalUtils.valueOrDefault(param.getTitle()));
        record.setIsDefault(BooleanUtils.isTrue(param.getIsDefault()) ? (byte) 1 : (byte) 0);
        record.setBrand(param.getBrand());
        record.setType(param.getType());
        record.setUserId(userId);
        return record;
    }

}
