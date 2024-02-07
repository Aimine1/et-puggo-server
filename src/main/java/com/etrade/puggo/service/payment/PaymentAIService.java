package com.etrade.puggo.service.payment;

import com.etrade.puggo.common.enums.LangErrorEnum;
import com.etrade.puggo.common.enums.PaymentTypeEnum;
import com.etrade.puggo.common.exception.CommonErrorV2;
import com.etrade.puggo.common.exception.PaymentException;
import com.etrade.puggo.dao.payment.PaymentInvoiceDao;
import com.etrade.puggo.service.BaseService;
import com.etrade.puggo.service.ai.AiUserAvailableBalanceService;
import com.etrade.puggo.service.payment.pojo.PayResult;
import com.etrade.puggo.service.payment.pojo.PaymentAIParam;
import com.etrade.puggo.service.payment.pojo.PaymentInvoiceDTO;
import com.etrade.puggo.utils.OptionalUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

/**
 * @author zhenyu
 * @version 1.0
 * @description: AI鉴定购买额度
 * @date 2024/2/6 16:39
 */
@Service
public class PaymentAIService extends BaseService {

    @Resource
    private PaymentUtils paymentUtils;

    @Resource
    private AiUserAvailableBalanceService aiUserAvailableBalanceService;

    @Resource
    private PaymentInvoiceDao paymentInvoiceDao;


    @Transactional(rollbackFor = Throwable.class)
    public String payForAI(PaymentAIParam param) {

        // 参数验证
        checkParameter(param);

        // 买家支付账号
        String paymentCustomerId = paymentUtils.getPaymentCustomerId(userId());

        // 发起支付，防止抢占资源
        PayResult payResult;
        synchronized (param.getKindId()) {
            payResult = doPay(param, paymentCustomerId);
        }

        // 保存支付信息
        savePaymentInvoice(param, payResult.getInvoiceId());

        return payResult.getInvoiceId();
    }


    private PayResult doPay(PaymentAIParam param, String paymentCustomerId) {

        Integer systemAvailableTimes = aiUserAvailableBalanceService.getSystemAvailableBalance(param.getKindId());

        if (param.getAvailableTimes().compareTo(systemAvailableTimes) >= 0) {
            throw new PaymentException(CommonErrorV2.AI_INSUFFICIENT_AVAILABLE_TIME2);
        }

        String invoiceId = paymentUtils.execute(
                param.getAmount().setScale(0, RoundingMode.UP),
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                paymentCustomerId,
                null,
                param.getPaymentType(),
                OptionalUtils.valueOrDefault(param.getPaymentMethodId()),
                OptionalUtils.valueOrDefault(param.getToken())
        );

        // 更新额度
        aiUserAvailableBalanceService.deductAvailableBalance(userId(), param.getKindId());

        return PayResult.builder().otherFees(param.getAmount()).invoiceId(invoiceId).build();
    }


    private void checkParameter(PaymentAIParam param) {

        // 检查支付方式
        paymentUtils.checkPaymentType(param.getPaymentType());

        // 检查账单地址
        paymentUtils.checkBillingAddress(param.getBillingAddressId());

        // 检查银行卡信息
        if (param.getPaymentType().equals(PaymentTypeEnum.card.name())) {
            paymentUtils.checkCardInfo(param.getPaymentCardId());
        }

        // 检查金额 TODO
        if (param.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new PaymentException(LangErrorEnum.INVALID_AMOUNT.lang());
        }
    }

    private void savePaymentInvoice(PaymentAIParam param, String invoiceId) {
        PaymentInvoiceDTO paymentInvoiceDTO = PaymentInvoiceDTO.builder()
                .payNo(UUID.randomUUID().toString())
                .userId(userId())
                .paymentMethodId(OptionalUtils.valueOrDefault(param.getPaymentMethodId(), ""))
                .paymentType(OptionalUtils.valueOrDefault(param.getPaymentType(), ""))
                .billingAddressId(OptionalUtils.valueOrDefault(param.getBillingAddressId(), 0))
                .paymentCardId(OptionalUtils.valueOrDefault(param.getPaymentCardId(), 0))
                .paymentSellerId("")
                .title("购买AI鉴定额度")
                .otherFees(param.getAmount())
                .invoiceId(invoiceId)
                .build();

        paymentInvoiceDao.save(paymentInvoiceDTO);
    }
}
