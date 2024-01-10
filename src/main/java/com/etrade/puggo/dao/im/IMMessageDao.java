package com.etrade.puggo.dao.im;

import static com.etrade.puggo.db.Tables.MESSAGE;

import com.etrade.puggo.dao.BaseDao;
import com.etrade.puggo.third.im.pojo.IMMessageDO;
import com.etrade.puggo.utils.OptionalUtils;
import org.springframework.stereotype.Repository;

/**
 * @author niuzhenyu
 * @description : IM 消息记录
 * @date 2023/6/25 11:15
 **/
@Repository
public class IMMessageDao extends BaseDao {


    public void save(IMMessageDO message) {
        db
            .insertInto(
                MESSAGE,
                MESSAGE.CONV_TYPE,
                MESSAGE.TO_ACCOUNT,
                MESSAGE.FROM_ACCOUNT,
                MESSAGE.FROM_CLIENT_TYPE,
                MESSAGE.FROM_DEVICE_ID,
                MESSAGE.FROM_NICK,
                MESSAGE.MSG_TIMESTAMP,
                MESSAGE.MSG_TYPE,
                MESSAGE.BODY,
                MESSAGE.ATTACH,
                MESSAGE.MSGID_CLIENT,
                MESSAGE.MSGID_SERVER,
                MESSAGE.RESEND_FLAG,
                MESSAGE.CUSTOM_SAFE_FLAG,
                MESSAGE.CUSTOM_APNS_TEXT,
                MESSAGE.EXT,
                MESSAGE.IP,
                MESSAGE.PORT
            )
            .values(
                OptionalUtils.valueOrDefault(message.getConvType()),
                OptionalUtils.valueOrDefault(message.getTo()),
                OptionalUtils.valueOrDefault(message.getFromAccount()),
                OptionalUtils.valueOrDefault(message.getFromClientType()),
                OptionalUtils.valueOrDefault(message.getFromDeviceId()),
                OptionalUtils.valueOrDefault(message.getFromNick()),
                OptionalUtils.valueOrDefault(message.getMsgTimestamp()),
                OptionalUtils.valueOrDefault(message.getMsgType()),
                OptionalUtils.valueOrDefault(message.getBody()),
                OptionalUtils.valueOrDefault(message.getAttach()),
                OptionalUtils.valueOrDefault(message.getMsgidClient()),
                OptionalUtils.valueOrDefault(message.getMsgidServer()),
                OptionalUtils.valueOrDefault(message.getResendFlag()),
                OptionalUtils.valueOrDefault(message.getCustomSafeFlag()),
                OptionalUtils.valueOrDefault(message.getCustomApnsText()),
                OptionalUtils.valueOrDefault(message.getExt()),
                OptionalUtils.valueOrDefault(message.getIp()),
                OptionalUtils.valueOrDefault(message.getPort())
            )
            .execute();
    }


}
