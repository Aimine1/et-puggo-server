package com.etrade.puggo.third.im;

import com.alibaba.fastjson.JSONObject;
import com.etrade.puggo.dao.user.UserIMActionDao;
import com.etrade.puggo.db.tables.records.UserImActionRecord;
import com.etrade.puggo.third.im.pojo.IMActionDO;
import com.etrade.puggo.third.im.pojo.IMTokenDO;
import com.etrade.puggo.third.im.pojo.MessageDO;
import com.etrade.puggo.third.im.pojo.MsgNewsDO;
import com.etrade.puggo.utils.HttpClientUtils;
import com.etrade.puggo.utils.HttpResult;
import com.etrade.puggo.utils.OptionalUtils;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

/**
 * @author niuzhenyu
 * @description : 网易云信Api集成
 * @date 2023/6/21 10:17
 **/
@Service
@RefreshScope
public class YunxinIMApi {


    /**
     * Random Accid
     */
    private static final String PREFIX = "etrade_";
    private static final int MAX_LENGTH = 32;

    /**
     * Http Code
     */
    private static final int SUCCESS = 200;


    @Resource
    private YunxinIMConfig yunxinIMConfig;
    @Resource
    private UserIMActionDao IMActionDao;


    public String randomAccid(String name) {
        name = name.replace(" ", "");
        String prefix = PREFIX + name.toLowerCase();
        if (prefix.length() >= MAX_LENGTH) {
            return prefix.substring(0, MAX_LENGTH);
        }
        int randomLength = MAX_LENGTH - prefix.length() - 1;
        String random = RandomStringUtils.randomAlphanumeric(randomLength);
        return prefix + "@" + random.toLowerCase();
    }


    private Map<String, String> getCommonHeaders() {
        String nonce = RandomStringUtils.randomAlphanumeric(16);
        long timestamp = System.currentTimeMillis() / 1000;
        Map<String, String> headers = new HashMap<>();
        headers.put("appKey", yunxinIMConfig.getAppKey());
        headers.put("Nonce", nonce);
        headers.put("CurTime", String.valueOf(timestamp));
        headers.put("CheckSum",
            CheckSumBuilder.getCheckSum(yunxinIMConfig.getAppSecret(), nonce, String.valueOf(timestamp)));
        return headers;
    }


    /**
     * 注册网易云信账号
     *
     * @param accid  网易云信账号id 全局唯一
     * @param name   网易云信账号姓名
     * @param userId e-trade用户id
     * @return
     */
    public UserImActionRecord createAction(String accid, String name, Long userId) {

        Map<String, String> headers = getCommonHeaders();

        Map<String, Object> params = new HashMap<>();
        params.put("accid", accid);
        params.put("name", name);

        String url = yunxinIMConfig.getDomain() + "/user/create.action";
        HttpResult httpResult = HttpClientUtils.doPost(url, params, headers);

        UserImActionRecord actionRecord = null;

        if (null != httpResult && httpResult.getStatus() == SUCCESS) {
            // {"code":200,"info":{"name":"everythingtrade","accid":"mki817y060b63quryj0zsy","token":"77b4fd72cdb4904ace43dfbd6cf4fa91"}}
            String entity = httpResult.getStringEntity();
            JSONObject jsonObject = JSONObject.parseObject(entity);
            if ((int) jsonObject.get("code") == SUCCESS) {
                IMActionDO info = jsonObject.getObject("info", IMActionDO.class);
                String token = info.getToken();

                // 入库
                actionRecord = new UserImActionRecord();
                actionRecord.setUserId(userId);
                actionRecord.setName(name);
                actionRecord.setToken(token);
                actionRecord.setAccid(accid);
                Long id = IMActionDao.save(actionRecord);
            }
        }

        return actionRecord;
    }


    /**
     * 刷新token，一般用于token异常时系统主动刷新
     *
     * @param accid  网易云信账号id 全局唯一
     * @param userId e-trade用户id
     * @return token
     */
    public String refreshToken(String accid, Long userId) {

        Map<String, String> headers = getCommonHeaders();

        Map<String, Object> params = new HashMap<>();
        params.put("accid", accid);

        String url = yunxinIMConfig.getDomain() + "/user/refreshToken.action";
        HttpResult httpResult = HttpClientUtils.doPost(url, params, headers);

        String token = "";

        if (null != httpResult && httpResult.getStatus() == SUCCESS) {
            // {"code":200,"info":{"accid":"mki817y060b63quryj0zsy","token":"76e8c08bf7a89c71dfbfc8f4c94d2bc8"}}
            String entity = httpResult.getStringEntity();
            JSONObject jsonObject = JSONObject.parseObject(entity);
            if ((int) jsonObject.get("code") == SUCCESS) {
                IMTokenDO info = jsonObject.getObject("info", IMTokenDO.class);
                token = info.getToken();
            }
        }

        // 更新token
        IMActionDao.updateToken(userId, token);

        return token;
    }


    public JSONObject sendCustomNews(MsgNewsDO news) {

        Map<String, String> headers = getCommonHeaders();

        Map<String, Object> params = new HashMap<>();
        params.put("from", news.getFromAccount());
        params.put("to", news.getToAccount());
        params.put("msgtype", 0);
        params.put("attach", news.getAttach());
        params.put("pushcontent", news.getPushcontent());
        params.put("payload", OptionalUtils.valueOrDefault(news.getPayload(), "{}"));

        String url = yunxinIMConfig.getDomain() + "/msg/sendAttachMsg.action";
        HttpResult httpResult = HttpClientUtils.doPost(url, params, headers);

        if (null != httpResult && httpResult.getStatus() == SUCCESS) {
            // {
            //    "code":414
            //    "desc":"check msgType"  // msgType参数不是"0"或"1"
            // }
            // {
            //    "code":200
            // }
            String entity = httpResult.getStringEntity();
            return JSONObject.parseObject(entity);
        }

        return null;
    }


    public JSONObject sendMessage(MessageDO message) {
        Map<String, String> headers = getCommonHeaders();

        Map<String, Object> params = new HashMap<>();
        params.put("from", message.getFrom());
        params.put("to", message.getTo());
        params.put("ope", message.getOpe());
        params.put("type", message.getType());
        params.put("body", message.getBody());

        String url = yunxinIMConfig.getDomain() + "/msg/sendMsg.action";
        HttpResult httpResult = HttpClientUtils.doPost(url, params, headers);

        if (null != httpResult && httpResult.getStatus() == SUCCESS) {
            //            {
            //                "code":200,
            //                "data":{
            //                "msgid":1200510468189,
            //                    "timetag": 1545635366312,//消息发送的时间戳
            //                    "antispam":false
            //                }
            //            }
            String entity = httpResult.getStringEntity();
            return JSONObject.parseObject(entity);
        }

        return null;
    }

}
