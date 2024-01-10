package com.etrade.puggo.third.im;

import com.alibaba.fastjson.JSONObject;
import com.etrade.puggo.dao.im.IMMessageDao;
import com.etrade.puggo.dao.user.UserIMActionDao;
import com.etrade.puggo.db.tables.records.UserImActionRecord;
import com.etrade.puggo.third.im.pojo.IMMessageDO;
import com.etrade.puggo.third.im.pojo.MessageDO;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;


@Slf4j
@RestController
@RequestMapping(value = "/im/route", produces = "application/json;charset=UTF-8")
public class RouteController {

    @Resource
    private YunxinIMConfig yunxinIMConfig;
    @Resource
    private YunxinIMApi yunxinIMApi;
    @Resource
    private IMMessageDao imMessageDao;
    @Resource
    private UserIMActionDao userIMActionDao;


    @ApiIgnore
    @ResponseBody
    @PostMapping("/mockClient/action")
    public JSONObject mockClient(HttpServletRequest request) {

        JSONObject result = new JSONObject();

        try {

            // 获取请求体

            byte[] body = readBody(request);

            if (body == null) {
                log.warn("request wrong, empty body!");
                result.put("code", 414);
                return result;
            }

            // 获取部分request header，并打印
            String ContentType = request.getContentType();
            String AppKey = request.getHeader("AppKey");
            String CurTime = request.getHeader("CurTime");
            String MD5 = request.getHeader("MD5");
            String CheckSum = request.getHeader("CheckSum");

            log.info("request headers: ContentType = {}, AppKey = {}, CurTime = {}, " +
                "MD5 = {}, CheckSum = {}", ContentType, AppKey, CurTime, MD5, CheckSum);

            // 将请求体转成String格式，并打印
            String requestBody = new String(body, StandardCharsets.UTF_8);

            log.info("request body = {}", requestBody);

            // 获取计算过的md5及checkSum

            String verifyMD5 = CheckSumBuilder.getMD5(requestBody);
            String verifyChecksum = CheckSumBuilder.getCheckSum(yunxinIMConfig.getAppSecret(), verifyMD5, CurTime);

            log.debug("verifyMD5 = {}, verifyChecksum = {}", verifyMD5, verifyChecksum);

            // TODO: 比较md5、checkSum是否一致，以及后续业务处理
            if (!MD5.equals(verifyMD5)) {
                log.warn("MD5不一致 Request MD5 {}, Verify MD5 {}", MD5, verifyMD5);
                result.put("code", 400);
                return result;
            }

            if (!CheckSum.equals(verifyChecksum)) {
                log.warn("MD5不一致 Request CheckSum {}, Verify CheckSum {}", CheckSum, verifyChecksum);
                result.put("code", 400);
                return result;
            }

            IMMessageDO message = JSONObject.parseObject(body, IMMessageDO.class);

            // 只允许会话类抄送消息入库
            if (!message.getEventType().equals(1)) {
                result.put("code", 200);
                return result;
            }

            // 入库
            imMessageDao.save(message);

            result.put("code", 200);

            return result;

        } catch (Exception ex) {

            log.error(ex.getMessage(), ex);

            result.put("code", 414);

            return result;

        }

    }

    private byte[] readBody(HttpServletRequest request) throws IOException {

        if (request.getContentLength() > 0) {

            byte[] body = new byte[request.getContentLength()];

            IOUtils.readFully(request.getInputStream(), body);

            return body;

        } else {
            return null;
        }

    }


    @ApiIgnore
    @GetMapping("/send/message")
    public JSONObject sendMessage(@RequestParam String msg) {

        UserImActionRecord fromAction = userIMActionDao.findIMAction(2L);
        UserImActionRecord toAction = userIMActionDao.findIMAction(-1L);

        MessageDO message = new MessageDO();

        message.setFrom(fromAction.getAccid());
        message.setTo(toAction.getAccid());
        message.setOpe(0);
        message.setType(0);
        JSONObject body = new JSONObject();
        body.put("msg", msg);
        message.setBody(body.toJSONString());

        return yunxinIMApi.sendMessage(message);
    }

}
