package com.etrade.puggo.dao.payment;

import com.etrade.puggo.dao.BaseDao;
import com.etrade.puggo.db.tables.records.PaymentCustomerAddressRecord;
import com.etrade.puggo.service.payment.pojo.CustomerAddressDO;
import com.etrade.puggo.service.payment.pojo.UpdateCustomerAddressDO;
import com.etrade.puggo.utils.OptionalUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.etrade.puggo.db.Tables.PAYMENT_CUSTOMER_ADDRESS;

/**
 * @author zhenyu
 * @version 1.0
 * @description: 信用卡
 * @date 2024/1/19 14:45
 */
@Repository
public class CustomerAddressDao extends BaseDao {


    private static final byte IS_DEFAULT = 1;


    public void save(CustomerAddressDO param, Long userId) {
        PaymentCustomerAddressRecord record = newRecord(param, userId);
        record.insert();
    }


    public int update(UpdateCustomerAddressDO param, Long userId) {
        PaymentCustomerAddressRecord record = newRecord(param, userId);
        Integer id = param.getAddressId();
        return db.update(PAYMENT_CUSTOMER_ADDRESS).set(record).where(PAYMENT_CUSTOMER_ADDRESS.ID.eq(id)).execute();
    }


    public int delete(Integer id) {
        return db.deleteFrom(PAYMENT_CUSTOMER_ADDRESS).where(PAYMENT_CUSTOMER_ADDRESS.ID.eq(id)).execute();
    }


    public List<UpdateCustomerAddressDO> list(Long userId, String type) {
        return db.select(
                        PAYMENT_CUSTOMER_ADDRESS.ID.as("addressId"),
                        PAYMENT_CUSTOMER_ADDRESS.TYPE,
                        PAYMENT_CUSTOMER_ADDRESS.TITLE,
                        PAYMENT_CUSTOMER_ADDRESS.FIRST_NAME,
                        PAYMENT_CUSTOMER_ADDRESS.LAST_NAME,
                        PAYMENT_CUSTOMER_ADDRESS.PHONE_NUMBER,
                        PAYMENT_CUSTOMER_ADDRESS.POST_CODE,
                        PAYMENT_CUSTOMER_ADDRESS.CITY,
                        PAYMENT_CUSTOMER_ADDRESS.COUNTRY,
                        PAYMENT_CUSTOMER_ADDRESS.STATE,
                        PAYMENT_CUSTOMER_ADDRESS.ADDRESS_LINE1,
                        PAYMENT_CUSTOMER_ADDRESS.ADDRESS_LINE2,
                        DSL.iif(PAYMENT_CUSTOMER_ADDRESS.IS_DEFAULT.eq(IS_DEFAULT), true, false).as("isDefault")
                )
                .from(PAYMENT_CUSTOMER_ADDRESS)
                .where(PAYMENT_CUSTOMER_ADDRESS.USER_ID.eq(userId).and(PAYMENT_CUSTOMER_ADDRESS.TYPE.eq(type)))
                .orderBy(PAYMENT_CUSTOMER_ADDRESS.ID.desc())
                .fetchInto(UpdateCustomerAddressDO.class);
    }


    public UpdateCustomerAddressDO getOne(Integer id) {
        return db.select(
                        PAYMENT_CUSTOMER_ADDRESS.ID.as("addressId"),
                        PAYMENT_CUSTOMER_ADDRESS.TYPE,
                        PAYMENT_CUSTOMER_ADDRESS.TITLE,
                        PAYMENT_CUSTOMER_ADDRESS.FIRST_NAME,
                        PAYMENT_CUSTOMER_ADDRESS.LAST_NAME,
                        PAYMENT_CUSTOMER_ADDRESS.PHONE_NUMBER,
                        PAYMENT_CUSTOMER_ADDRESS.POST_CODE,
                        PAYMENT_CUSTOMER_ADDRESS.CITY,
                        PAYMENT_CUSTOMER_ADDRESS.COUNTRY,
                        PAYMENT_CUSTOMER_ADDRESS.STATE,
                        PAYMENT_CUSTOMER_ADDRESS.ADDRESS_LINE1,
                        PAYMENT_CUSTOMER_ADDRESS.ADDRESS_LINE2,
                        DSL.iif(PAYMENT_CUSTOMER_ADDRESS.IS_DEFAULT.eq(IS_DEFAULT), true, false).as("isDefault")
                )
                .from(PAYMENT_CUSTOMER_ADDRESS)
                .where(PAYMENT_CUSTOMER_ADDRESS.ID.eq(id))
                .fetchAnyInto(UpdateCustomerAddressDO.class);
    }


    private PaymentCustomerAddressRecord newRecord(CustomerAddressDO param, Long userId) {
        PaymentCustomerAddressRecord record = db.newRecord(PAYMENT_CUSTOMER_ADDRESS);
        record.setTitle(OptionalUtils.valueOrDefault(param.getTitle()));
        record.setIsDefault(BooleanUtils.isTrue(param.getIsDefault()) ? (byte) 1 : (byte) 0);
        record.setUserId(userId);
        record.setFirstName(param.getFirstName());
        record.setLastName(param.getLastName());
        record.setAddressLine1(param.getAddressLine1());
        record.setAddressLine2(param.getAddressLine2());
        record.setCity(param.getCity());
        record.setPostCode(param.getPostCode());
        record.setState(param.getState());
        record.setCountry(param.getCountry());
        record.setType(param.getType());
        record.setPhoneNumber(param.getPhoneNumber());
        return record;
    }

}
