package com.etrade.puggo.stream.producer;

import com.etrade.puggo.common.exception.CommonError;
import com.etrade.puggo.common.exception.ServiceException;
import com.etrade.puggo.constants.MsgNewsState;
import com.etrade.puggo.dao.im.MsgNewsDao;
import com.etrade.puggo.dao.user.UserIMActionDao;
import com.etrade.puggo.db.tables.records.MsgNewsRecord;
import com.etrade.puggo.db.tables.records.UserImActionRecord;
import com.etrade.puggo.stream.consumer.StreamKeys.StreamMsgNewsKey;
import com.etrade.puggo.third.im.YunxinIMApi;
import com.etrade.puggo.third.im.pojo.MsgNewsDO;
import com.etrade.puggo.third.im.pojo.SendNewsParam;
import com.etrade.puggo.utils.RedisUtils;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.connection.stream.StreamRecords;
import org.springframework.stereotype.Component;

/**
 * 消息生产者
 *
 * @author huan.fu 2021/11/1 - 下午5:09
 */
@Slf4j
@Component
public class StreamProducer {

    @Resource
    private RedisUtils redisUtils;
    @Resource
    private UserIMActionDao userIMActionDao;
    @Resource
    private MsgNewsDao msgNewsDao;
    @Resource
    private YunxinIMApi yunxinIMApi;


    private static final String SYS_NAME = "system";
    private static final Long SYS_USER_ID = -1L;


    public boolean sendNews(SendNewsParam param) {
        try {
            addRedisStream(param);
            return true;
        } catch (ServiceException e) {
            log.error("往stream中加入消息失败", e);
            return false;
        }
    }


    public void addRedisStream(SendNewsParam param) {
        Long toUserId = param.getToUserId();
        Long fromUserId = param.getFromUserId();
        byte state = MsgNewsState.TO_PUSH;
        String fromAccount;

        UserImActionRecord toUser = userIMActionDao.findIMAction(toUserId);

        if (toUser == null) {
            throw new ServiceException(CommonError.USER_IM_ACTION_IS_NOT_LOGIN);
        }

        if (fromUserId == null || fromUserId.equals(SYS_USER_ID)) {
            UserImActionRecord systemUser = userIMActionDao.findSystemIMAction();
            fromUserId = SYS_USER_ID;
            if (systemUser == null) {
                fromAccount = yunxinIMApi.randomAccid(SYS_NAME);
                state = createSysUserAction(fromAccount) ? MsgNewsState.TO_PUSH : MsgNewsState.PENDING;
            } else {
                fromAccount = systemUser.getAccid();
            }
        } else {
            UserImActionRecord fromUser = userIMActionDao.findIMAction(fromUserId);

            if (fromUser == null) {
                throw new ServiceException(CommonError.USER_IM_ACTION_IS_NOT_LOGIN);
            }

            fromAccount = fromUser.getAccid();
        }

        // 消息入库
        MsgNewsRecord msgNewsRecord = new MsgNewsRecord();
        msgNewsRecord.setAttach(param.getAttach());
        msgNewsRecord.setPushcontent(param.getPushcontent());
        msgNewsRecord.setToAccount(toUser.getAccid());
        msgNewsRecord.setToUserId(toUserId);
        msgNewsRecord.setFromAccount(fromAccount);
        msgNewsRecord.setFromUserId(fromUserId);
        msgNewsRecord.setGoodsId(param.getGoodsId());
        msgNewsRecord.setGoodsMainPic(param.getGoodsMainPic());
        msgNewsRecord.setState(state);

        Long id = msgNewsDao.save(msgNewsRecord);

        // 消息写入redis stream队列
        if (id != null) {
            MsgNewsDO msgNewsDO = MsgNewsDO.builder()
                .attach(param.getAttach())
                .pushcontent(param.getPushcontent())
                .toAccount(toUser.getAccid())
                .fromAccount(fromAccount)
                .state(state)
                .payload("{}")
                .id(id)
                .build();

            ObjectRecord<String, MsgNewsDO> record = StreamRecords.newRecord()
                .in(StreamMsgNewsKey.KEY)
                .ofObject(msgNewsDO)
                .withId(RecordId.autoGenerate());

            addRecord(record);
        }

    }


    private <R> void addRecord(ObjectRecord<String, R> record) {
        log.info("添加 Redis Stream 消息记录:[{}]", record);

        RecordId recordId = redisUtils.addStream(record);

        log.info("添加 Redis Stream 消息记录成功，recordId：{}", recordId);
    }


    private boolean createSysUserAction(String systemAccid) {
        UserImActionRecord action = yunxinIMApi.createAction(systemAccid, SYS_NAME, SYS_USER_ID);
        if (action != null) {
            log.info("system IM action 注册成功 {}", action.getAccid());
            return true;
        }
        return false;
    }
}
