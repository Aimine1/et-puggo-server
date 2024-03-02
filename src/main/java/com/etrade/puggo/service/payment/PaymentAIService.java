package com.etrade.puggo.service.payment;

import com.alibaba.fastjson.JSONObject;
import com.etrade.puggo.common.enums.LangErrorEnum;
import com.etrade.puggo.common.enums.PaymentTypeEnum;
import com.etrade.puggo.common.exception.CommonErrorV2;
import com.etrade.puggo.common.exception.PaymentException;
import com.etrade.puggo.dao.payment.PaymentInvoiceDao;
import com.etrade.puggo.service.BaseService;
import com.etrade.puggo.service.ai.AiUserAvailableBalanceService;
import com.etrade.puggo.service.payment.pojo.PayResult;
import com.etrade.puggo.service.payment.pojo.PayVO;
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
    public PayVO payForAI(PaymentAIParam param) {

        // 参数验证
        checkParameter(param);

        // 买家支付账号
        String paymentCustomerId = paymentUtils.getPaymentCustomerId(userId());

        // 发起支付，防止抢占资源
        PayResult payResult = doPay(param, paymentCustomerId);

        // 保存支付信息
        long payId = savePaymentInvoice(param, payResult);

        // 如果是信用卡支付直接更新额度
        if (param.getPaymentType().equals(PaymentTypeEnum.card.name())) {
            aiUserAvailableBalanceService.plusAvailableBalance(userId(), param.getKindId(), param.getAvailableTimes());
        }

        return PayVO.builder().payId(payId).clientSecret(payResult.getClientSecret()).build();
    }


    private PayResult doPay(PaymentAIParam param, String paymentCustomerId) {

        Integer systemAvailableTimes = aiUserAvailableBalanceService.getSystemAvailableBalance(param.getKindId());

        if (param.getAvailableTimes().compareTo(systemAvailableTimes) >= 0) {
            throw new PaymentException(CommonErrorV2.AI_INSUFFICIENT_AVAILABLE_TIME2);
        }

        JSONObject jsonObject = paymentUtils.execute(
                param.getAmount().setScale(0, RoundingMode.UP),
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                paymentCustomerId,
                null,
                param.getPaymentType(),
                OptionalUtils.valueOrDefault(param.getPaymentMethodId()),
                OptionalUtils.valueOrDefault(param.getToken()),
                param.getPaymentType().equals(PaymentTypeEnum.card.name()) && param.getPaymentCardId() != null
        );

        PayResult payResult = PayResult.builder().otherFees(param.getAmount()).build();

        if (jsonObject.containsKey("invoiceId")) {
            payResult.setInvoiceId((String) jsonObject.get("invoiceId"));
        }
        if (jsonObject.containsKey("clientSecret")) {
            payResult.setClientSecret((String) jsonObject.get("clientSecret"));
        }
        if (jsonObject.containsKey("paymentIntent")) {
            payResult.setPaymentIntent((String) jsonObject.get("paymentIntent"));
        }

        return payResult;
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

    private long savePaymentInvoice(PaymentAIParam param, PayResult payResult) {
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
                .invoiceId(payResult.getInvoiceId())
                .paymentIntentId(payResult.getPaymentIntent())
                .clientSecret(payResult.getClientSecret())
                .aiKindId(param.getKindId())
                .aiPlusAvailableTimes(param.getAvailableTimes())
                .build();

        return paymentInvoiceDao.save(paymentInvoiceDTO);
    }
}
