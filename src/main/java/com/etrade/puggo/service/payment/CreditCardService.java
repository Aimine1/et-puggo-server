package com.etrade.puggo.service.payment;

import com.etrade.puggo.service.payment.pojo.CreditCardParam;
import com.etrade.puggo.service.payment.pojo.UpdateCreditCardParam;
import org.springframework.stereotype.Service;

/**
 * @author zhenyu
 * @version 1.0
 * @description: 信用卡管理
 * @date 2024/1/18 15:09
 */
@Service
public class CreditCardService {

    public void save(CreditCardParam param) {

    }


    public void update(UpdateCreditCardParam param) {

    }


    public void list() {

    }


    /**
     * 检查卡号是否符合Luhn算法，并不能完全确定信用卡的真伪
     *
     * @param cardNumber 卡号
     * @return 真为true，假为false
     */
    private static boolean isValidCreditCardNumber(String cardNumber) {
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
        return (sum % 10 == 0);
    }

}
