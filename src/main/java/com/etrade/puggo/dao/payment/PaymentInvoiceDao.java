package com.etrade.puggo.dao.payment;

import com.etrade.puggo.dao.BaseDao;
import com.etrade.puggo.service.payment.pojo.PaymentInvoiceDTO;
import com.etrade.puggo.utils.OptionalUtils;
import org.springframework.stereotype.Repository;

import static com.etrade.puggo.db.Tables.PAYMENT_INVOICE;

/**
 * @author zhenyu
 * @version 1.0
 * @description: 支付发票
 * @date 2024/2/6 17:31
 */
@Repository
public class PaymentInvoiceDao extends BaseDao {

    public long save(PaymentInvoiceDTO invoiceDTO) {
        return db.insertInto(
                        PAYMENT_INVOICE,
                        PAYMENT_INVOICE.PAY_NO,
                        PAYMENT_INVOICE.USER_ID,
                        PAYMENT_INVOICE.PAYMENT_METHOD_ID,
                        PAYMENT_INVOICE.PAYMENT_TYPE,
                        PAYMENT_INVOICE.BILLING_ADDRESS_ID,
                        PAYMENT_INVOICE.PAYMENT_CARD_ID,
                        PAYMENT_INVOICE.PAYMENT_SELLER_ID,
                        PAYMENT_INVOICE.TITLE,
                        PAYMENT_INVOICE.OTHER_FEES,
                        PAYMENT_INVOICE.INVOICE_ID
                )
                .values(
                        invoiceDTO.getPayNo(),
                        invoiceDTO.getUserId(),
                        invoiceDTO.getPaymentMethodId(),
                        invoiceDTO.getPaymentType(),
                        invoiceDTO.getBillingAddressId(),
                        invoiceDTO.getPaymentCardId(),
                        invoiceDTO.getPaymentSellerId(),
                        invoiceDTO.getTitle(),
                        OptionalUtils.valueOrDefault(invoiceDTO.getOtherFees()),
                        OptionalUtils.valueOrDefault(invoiceDTO.getInvoiceId())
                )
                .returning(PAYMENT_INVOICE.ID).fetchOne().getId();
    }


    public void updateGoodsTrade(PaymentInvoiceDTO invoiceDTO) {
        db.update(PAYMENT_INVOICE)
                .set(PAYMENT_INVOICE.OTHER_FEES, invoiceDTO.getOtherFees())
                .set(PAYMENT_INVOICE.AMOUNT, invoiceDTO.getAmount())
                .set(PAYMENT_INVOICE.TAX, invoiceDTO.getTax())
                .set(PAYMENT_INVOICE.PAYMENT_METHOD_ID, invoiceDTO.getPaymentMethodId())
                .set(PAYMENT_INVOICE.PAYMENT_TYPE, invoiceDTO.getPaymentType())
                .set(PAYMENT_INVOICE.BILLING_ADDRESS_ID, invoiceDTO.getBillingAddressId())
                .set(PAYMENT_INVOICE.INVOICE_ID, invoiceDTO.getInvoiceId())
                .set(PAYMENT_INVOICE.PAYMENT_CARD_ID, invoiceDTO.getPaymentCardId())
                .where(PAYMENT_INVOICE.ID.eq(invoiceDTO.getId()))
                .execute();
    }

}
