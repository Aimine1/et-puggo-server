package com.etrade.puggo.service.payment;

import com.etrade.puggo.common.exception.CommonErrorV2;
import com.etrade.puggo.common.exception.ServiceException;
import com.etrade.puggo.dao.user.UserDao;
import com.etrade.puggo.service.BaseService;
import com.etrade.puggo.third.aws.PaymentLambdaFunctions;
import com.etrade.puggo.third.aws.pojo.PaymentMethodDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhenyu
 * @version 1.0
 * @description: 买家支付方式服务
 * @date 2024/2/5 11:02
 */
@Service
public class PaymentMethodService extends BaseService {


    @Resource
    private UserDao userDao;

    @Resource
    private PaymentLambdaFunctions paymentLambdaFunctions;


    /**
     * 获取该用户下已创建的paymentMethod
     *
     * @return paymentMethodId列表
     */
    public List<String> listPaymentMethod() {
        String paymentCustomerId = userDao.getPaymentCustomerId(userId());

        if (StringUtils.isBlank(paymentCustomerId)) {
            throw new ServiceException(CommonErrorV2.PAYMENT_CUSTOMER_ERROR);
        }

        List<PaymentMethodDTO> paymentMethodList = paymentLambdaFunctions.listPaymentMethod(paymentCustomerId);

        if (paymentMethodList == null) {
            return new ArrayList<>();
        }

        return paymentMethodList.stream().map(PaymentMethodDTO::getId).collect(Collectors.toList());
    }


    /**
     * 将customer与paymentMethod进行绑定
     *
     * @param paymentMethodId paymentMethodId
     */
    public void updatePaymentMethod(String paymentMethodId) {
        String paymentCustomerId = userDao.getPaymentCustomerId(userId());

        if (StringUtils.isBlank(paymentCustomerId)) {
            throw new ServiceException(CommonErrorV2.PAYMENT_CUSTOMER_ERROR);
        }

        paymentLambdaFunctions.updatePaymentMethod(paymentCustomerId, paymentMethodId);
    }

}
