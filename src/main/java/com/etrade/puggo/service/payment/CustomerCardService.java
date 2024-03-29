package com.etrade.puggo.service.payment;

import com.etrade.puggo.common.enums.LangErrorEnum;
import com.etrade.puggo.common.exception.ServiceException;
import com.etrade.puggo.dao.payment.CustomerCardDao;
import com.etrade.puggo.db.tables.records.PaymentCardRecord;
import com.etrade.puggo.service.BaseService;
import com.etrade.puggo.service.payment.pojo.CreditCardDO;
import com.etrade.puggo.service.payment.pojo.CreditCardVO;
import com.etrade.puggo.service.payment.pojo.UpdateCreditCardDO;
import com.etrade.puggo.utils.DateTimeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

/**
 * @author zhenyu
 * @version 1.0
 * @description: 信用卡管理
 * @date 2024/1/18 15:09
 */
@Service
public class CustomerCardService extends BaseService {

    @Resource
    private CustomerCardDao customerCardDao;

    public Integer save(CreditCardDO param) {

        checkCardNumber(param.getCardNumber());

        checkExpireYearAndMonth(param.getExpireYear(), param.getExpireMonth());

        PaymentCardRecord paymentCardRecord = customerCardDao.selectOne(param.getCardNumber(), userId());

        if (paymentCardRecord != null) {
            return paymentCardRecord.getId();
        }

        return customerCardDao.save(param, userId());
    }


    public void update(UpdateCreditCardDO param) {

        checkCardNumber(param.getCardNumber());

        checkExpireYearAndMonth(param.getExpireYear(), param.getExpireMonth());

        customerCardDao.update(param, userId());
    }


    public List<CreditCardVO> list() {
        List<CreditCardVO> list = customerCardDao.list(userId());
        list.forEach(o -> o.setCardNumber(substrCardNumber(o.getCardNumber())));
        return list;
    }


    public void remove(Integer id) {
        customerCardDao.delete(id);
    }


    public Integer getCardId(Integer id) {
        PaymentCardRecord paymentCardRecord = customerCardDao.selectOne(id);
        return paymentCardRecord == null ? null : paymentCardRecord.getId();
    }


    private static void checkExpireYearAndMonth(String expireYear, String expireMonth) {
        if (isInvalidExpireYearAndMonth(expireYear, expireMonth)) {
            throw new ServiceException(LangErrorEnum.INVALID_EXPIRE_DATE.lang());
        }
    }


    private static void checkCardNumber(String cardNumber) {
        if (isInvalidCreditCardNumber(cardNumber)) {
            throw new ServiceException(LangErrorEnum.INVALID_CARD_NUMBER.lang());
        }
    }


    /**
     * 检查卡号是否符合Luhn算法，并不能完全确定信用卡的真伪
     *
     * @param cardNumber 卡号
     * @return 如果不合法返回true
     */
    private static boolean isInvalidCreditCardNumber(String cardNumber) {
        int sum = 0;
        boolean alternate = false;
        for (int i = cardNumber.length() - 1; i >= 0; i--) {
            int n = Integer.parseInt(cardNumber.substring(i, i + 1));
            if (alternate) {
                n *= 2;
                if (n > 9) {
                    n = (n % 10) + 1;
                }
            }
            sum += n;
            alternate = !alternate;
        }
        return (sum % 10 != 0);
    }


    /**
     * 检查失效日期是否合法
     *
     * @param expireYear  失效年份
     * @param expireMonth 失效月份
     * @return 如果不合法返回true
     */
    private static boolean isInvalidExpireYearAndMonth(String expireYear, String expireMonth) {
        LocalDateTime now = DateTimeUtils.now();

        // 假设year是从2000年开始的年份，所以需要加上2000
        int yearInt = Integer.parseInt(expireYear) + 2000;
        int monthInt = Integer.parseInt(expireMonth);

        YearMonth currentYearMonth = YearMonth.of(yearInt, monthInt);
        LocalDateTime expireTime = currentYearMonth.atEndOfMonth().atTime(23, 59, 59);

        return expireTime.isBefore(now);
    }


    private static String substrCardNumber(String cardNumber) {
        if (StringUtils.isNotBlank(cardNumber) && cardNumber.length() > 4) {
            return cardNumber.substring(cardNumber.length() - 4);
        }
        return "";
    }

}
