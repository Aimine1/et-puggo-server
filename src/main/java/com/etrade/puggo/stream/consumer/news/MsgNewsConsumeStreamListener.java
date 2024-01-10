package com.etrade.puggo.stream.consumer.news;

import com.alibaba.fastjson.JSONObject;
import com.etrade.puggo.constants.MsgNewsState;
import com.etrade.puggo.dao.im.MsgNewsDao;
import com.etrade.puggo.stream.consumer.StreamKeys.StreamMsgNewsKey;
import com.etrade.puggo.third.im.YunxinIMApi;
import com.etrade.puggo.third.im.pojo.MsgNewsDO;
import com.etrade.puggo.utils.RedisUtils;
import javax.annotation.Resource;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.stereotype.Component;

/**
 * 通过监听器异步消费
 *
 * @author huan.fu 2021/11/10 - 下午5:51
 */
@Slf4j
@Getter
@Setter
@Component
@NoArgsConstructor
public class MsgNewsConsumeStreamListener implements StreamListener<String, ObjectRecord<String, MsgNewsDO>> {

    /**
     * 消费类型
     */
    private String consumerType;
    /**
     * 消费组
     */
    private String group;
    /**
     * 消费组中的某个消费者
     */
    private String consumerName;

    @Resource
    private RedisUtils redisUtils;
    @Resource
    private YunxinIMApi yunxinIMApi;
    @Resource
    private MsgNewsDao msgNewsDao;


    @Override
    public void onMessage(ObjectRecord<String, MsgNewsDO> message) {

        String stream = message.getStream();

        // 目前只接收打印消息，如果之后有多个stream需要改
        if (!StreamMsgNewsKey.KEY.equals(stream)) {
            return;
        }

        RecordId recordId = message.getId();
        MsgNewsDO news = message.getValue();

        if (StringUtils.isBlank(group)) {
            log.info("[{}]: 接收到一个消息 stream:[{}],id:[{}]", consumerType, stream, recordId);
        } else {
            log.info("[{}] stream:[{}] group:[{}] consumerName:[{}] 接收到一个消息 id:[{}]", consumerType,
                stream, group, consumerName, recordId);
        }

        log.info("消息内容为: {}", news);

        JSONObject jsonObject = yunxinIMApi.sendCustomNews(news);

        if ((int) jsonObject.get("code") == 200) {
            msgNewsDao.pushSuccess(news.getId(), MsgNewsState.SUCCESS);
            log.info("系统消息推送成功");
        } else {
            String desc = (String) jsonObject.get("desc");
            msgNewsDao.pushFail(news.getId(), MsgNewsState.FAILURE, desc);
            log.info("系统消息推送失败 {}", jsonObject);
            return;
        }

        // 当是消费组消费时，如果不是自动ack，则需要在这个地方手动ack
        Long ackId = redisUtils.ack(stream, group, recordId);
        if (ackId == 1L) {
            log.info("已消费的消息 {} ack成功", recordId);
        } else {
            log.error("已消费消息ack失败，recordId：{}", recordId);
            return;
        }

        Long delId = redisUtils.delStream(stream, recordId);
        if (delId == 1L) {
            log.info("已确认的消息 {} delete成功", recordId);
        } else {
            log.error("已确认的消息delete失败，recordId：{}", recordId);
        }

    }
}
