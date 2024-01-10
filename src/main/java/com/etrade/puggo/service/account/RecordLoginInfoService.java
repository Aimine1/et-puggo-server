package com.etrade.puggo.service.account;

import com.alibaba.fastjson.JSONObject;
import com.etrade.puggo.dao.user.UserAccountDao;
import com.etrade.puggo.service.BaseService;
import com.etrade.puggo.utils.DateTimeUtils;
import com.etrade.puggo.utils.IpUtils;
import com.etrade.puggo.utils.StrUtils;
import java.time.LocalDateTime;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author niuzhenyu
 * @description : 记录登录信息
 * @date 2023/6/8 20:30
 **/
@Service
public class RecordLoginInfoService extends BaseService {

    @Resource
    private UserAccountDao userAccountDao;

    @Async
    public void lastLogin(HttpServletRequest request, String deviceJson, long accountId) {
        // 登录ip
        String ip = IpUtils.getIpAddress(request);
        // 登录地址
        String address = StrUtils.isBlank(ip) ? "" : JSONObject.toJSONString(IpUtils.getIpInfo(ip));
        // 登录时间
        LocalDateTime loginTime = DateTimeUtils.now();

        // 登录设备
        userAccountDao.updateLoginInfo(accountId, ip, address, loginTime, deviceJson);
    }

}
