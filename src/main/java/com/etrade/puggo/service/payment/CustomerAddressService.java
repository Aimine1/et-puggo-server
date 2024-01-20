package com.etrade.puggo.service.payment;

import com.etrade.puggo.common.enums.AddressTypeEnum;
import com.etrade.puggo.common.enums.LangErrorEnum;
import com.etrade.puggo.common.exception.ServiceException;
import com.etrade.puggo.dao.payment.CustomerAddressDao;
import com.etrade.puggo.service.BaseService;
import com.etrade.puggo.service.payment.pojo.CustomerAddressDO;
import com.etrade.puggo.service.payment.pojo.UpdateCustomerAddressDO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author zhenyu
 * @version 1.0
 * @description: 买家收货地址/账单地址管理
 * @date 2024/1/18 14:39
 */
@Service
public class CustomerAddressService extends BaseService {

    private static final Pattern INTERNATIONAL_PHONE_PATTERN = Pattern.compile(
            "^(\\+?\\d{1,3}[- ]?)?(\\(?\\d{1,4}\\)?[- ]?)?(\\d{1,4}[- ]?)*$"
    );

    @Resource
    private CustomerAddressDao customerAddressDao;


    public void save(CustomerAddressDO param) {

        checkAddressType(param.getType());

        checkPhoneNumber(param.getPhoneNumber());

        customerAddressDao.save(param, userId());
    }


    public void update(UpdateCustomerAddressDO param) {

        checkAddressType(param.getType());

        checkPhoneNumber(param.getPhoneNumber());

        customerAddressDao.update(param, userId());
    }


    public List<UpdateCustomerAddressDO> list(String type) {
        return customerAddressDao.list(userId(), type);
    }


    public void remove(Integer id) {
        customerAddressDao.delete(id);
    }


    public boolean check(AddressTypeEnum type, Integer addressId) {
        if (addressId == null) {
            return false;
        }
        UpdateCustomerAddressDO one = customerAddressDao.getOne(addressId);
        return one != null && one.getType().equals(type.name());
    }


    private static void checkAddressType(String type) {
        if (!AddressTypeEnum.isValid(type)) {
            throw new ServiceException(LangErrorEnum.INVALID_ADDRESS_TYPE.lang());
        }
    }


    private static void checkPhoneNumber(String phoneNumber) {
        if (!validatePhone(phoneNumber)) {
            throw new ServiceException(LangErrorEnum.INVALID_PHONE_NUMBER.lang());
        }
    }


    private static boolean validatePhone(String phoneNumber) {
        return INTERNATIONAL_PHONE_PATTERN.matcher(phoneNumber).matches();
    }

}
