package com.etrade.puggo.service.payment;

import com.etrade.puggo.common.enums.*;
import com.etrade.puggo.common.exception.ServiceException;
import com.etrade.puggo.dao.user.UserDao;
import com.etrade.puggo.db.tables.records.GoodsMessageLogsRecord;
import com.etrade.puggo.service.BaseService;
import com.etrade.puggo.service.account.UserAccountService;
import com.etrade.puggo.service.account.pojo.UserInfoVO;
import com.etrade.puggo.service.goods.message.GoodsMessageService;
import com.etrade.puggo.service.payment.pojo.PaymentParam;
import com.etrade.puggo.service.setting.SettingService;
import com.etrade.puggo.third.aws.PaymentLambdaFunctions;
import com.etrade.puggo.third.aws.pojo.CreatePaymentIntentReq;
import com.etrade.puggo.third.aws.pojo.SellerAccountDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @author zhenyu
 * @version 1.0
 * @description: 支付管理
 * @date 2024/1/18 15:24
 */
@Service
@Slf4j
public class PaymentService extends BaseService {


    private static final BigDecimal default_shipping = new BigDecimal(18);
    private static final BigDecimal default_tax_percentage = new BigDecimal("0.08");
    private static final String default_currency = "CAD";

    @Resource
    private CustomerAddressService customerAddressService;

    @Resource
    private PaymentLambdaFunctions paymentLambdaFunctions;

    @Resource
    private GoodsMessageService goodsMessageService;

    @Resource
    private SettingService settingService;

    @Resource
    private UserAccountService userAccountService;

    @Resource
    private UserDao userDao;


    @Transactional(rollbackFor = Throwable.class)
    public void pay(PaymentParam param) {

        // 参数验证
        checkParameter(param);

        // 检查买家与卖家协商一致的记录，否则是非法的支付
        GoodsMessageLogsRecord msgRecord = goodsMessageService.getPaymentPendingMsgRecord(
                param.getCustomerId(), param.getSellerId(), param.getGoodsId());
        if (msgRecord == null) {
            log.error("支付失败，没有找到协商记录");
            throw new ServiceException(LangErrorEnum.INVALID_PAY.lang());
        }

        // 计算费用
        BigDecimal totalAmount;
        if (PaymentTargetEnum.product.name().equals(param.getTarget())) {
            totalAmount = calculateTotal(msgRecord.getBuyerPrice());
        } else {
            // TODO.
            totalAmount = BigDecimal.ZERO;
        }

        // 发起支付
        execute(totalAmount, param.getPaymentType(), param.getPaymentMethodId());
    }


    private void execute(BigDecimal amount, String paymentType, String paymentMethodId) {
        String paymentCustomerId = userAccountService.getPaymentCustomerId(userId());

        if (StringUtils.isBlank(paymentCustomerId)) {
            log.error("支付失败，paymentCustomerId is null， userId={}", userId());
            throw new ServiceException(LangErrorEnum.PAYMENT_FAILED.lang());
        }

        CreatePaymentIntentReq req = new CreatePaymentIntentReq();
        req.setAmount(amount);
        req.setCustomerId(paymentCustomerId);
        req.setPaymentType(paymentType);
        req.setPaymentMethodId(paymentMethodId);
        req.setCurrency(settingService.k(SettingsEnum.moneyKind.v(), default_currency));

        try {
            String payload = paymentLambdaFunctions.createPaymentIntent(req);
            log.info("支付结果为: {}", payload);
        } catch (Exception e) {
            log.error("支付失败，userId={}，原因={}", userId(), e.getMessage());
        }
    }


    private void checkParameter(PaymentParam param) {

        if (!PaymentTypeEnum.isValid(param.getPaymentType())) {
            log.error("支付失败，支付类型异常 paymentType={}", param.getPaymentType());
            throw new ServiceException(LangErrorEnum.INVALID_SHIPPING_METHOD.lang());
        }

        if (!ShippingMethodEnum.isValid(param.getShippingMethod())) {
            log.error("支付失败，货品交易方式异常 shippingMethod={}", param.getShippingMethod());
            throw new ServiceException(LangErrorEnum.INVALID_PAYMENT_TYPE.lang());
        }

        // 检查收货地址
        if (!customerAddressService.check(AddressTypeEnum.delivery, param.getDeliveryAddressId())) {
            log.error("支付失败，未知的收货地址 addressId={}", param.getDeliveryAddressId());
            throw new ServiceException(LangErrorEnum.INVALID_DELIVERY_ADDRESS.lang());
        }

        // 检查账单地址
        if (!param.isSameAsDeliveryAddress()) {
            if (!customerAddressService.check(AddressTypeEnum.billing, param.getBillingAddressId())) {
                log.error("支付失败，未知的账单地址 addressId={}", param.getBillingAddressId());
                throw new ServiceException(LangErrorEnum.INVALID_BILLING_ADDRESS.lang());
            }
        }

        // 检查支付目的
        if (!PaymentTargetEnum.isValid(param.getTarget())) {
            log.error("支付失败，货品交易方式异常 target={}", param.getTarget());
            throw new ServiceException(LangErrorEnum.INVALID_TARGET.lang());
        }

    }


    /**
     * 计算订单总价 orderTotal = productPrice + shippingCharge
     *
     * @param productPrice 商品成交价格
     * @return 订单总价
     */
    public BigDecimal calculateTotal(BigDecimal productPrice) {
        if (productPrice == null || productPrice.compareTo(BigDecimal.ZERO) <= 0) {
            log.error("支付失败，商品成交价异常 productPrice={}", productPrice);
            throw new ServiceException(LangErrorEnum.INVALID_PRODUCT_PRICE.lang());
        }
        String shippingCharge = settingService.k(SettingsEnum.sameDayDeliveryCharge.v());
        BigDecimal shippingChargeBdc = isNumber(shippingCharge) ? new BigDecimal(shippingCharge) : default_shipping;
        return productPrice.add(shippingChargeBdc);
    }


    public static boolean isNumber(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e1) {
            try {
                Float.parseFloat(str);
                return true;
            } catch (NumberFormatException e2) {
                return false;
            }
        }
    }


    public String createSellerAccount() {
        // 获取商家邮件
        UserInfoVO userInfo = userDao.findUserInfo(userId());

        String email = userInfo.getEmail();

        // 创建商家支付账号
        SellerAccountDTO sellerAccount = paymentLambdaFunctions.createSellerAccount(email);

        // 保存商家支付账号
        if (sellerAccount != null && sellerAccount.getAccountId() != null) {
            userDao.updatePaymentSellerId(userId(), sellerAccount.getAccountId());
        }

        return sellerAccount != null ? sellerAccount.getAccountLinkURL() : null;
    }

}
