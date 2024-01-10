package com.etrade.puggo.utils;

import com.etrade.puggo.common.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

/**
 * @ClassName
 * @Description
 * @Author caoruiqi
 * @Date 2021/4/13 17:00
 */
@Service
@Slf4j
public class IpUtils {


    public static IpAddressDO getIpInfo(String ip) {
        MultiValueMap<String, Object> realParam = new LinkedMultiValueMap<>();
        realParam.add("ip", ip);
        realParam.add("accessKey", "alibaba-inc");

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        //设置ContentType类型
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<MultiValueMap<String, Object>>(
            realParam, headers);
        //发送请求，设置请求返回数据格式为String
        ResponseEntity<Result> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity("https://ip.taobao.com/outGetIpInfo", request, Result.class);
        } catch (Exception e) {
            log.error("[IPUtils]IP转化地址失败 IP:{} API:{}", ip, "https://ip.taobao.com/outGetIpInfo");
            e.printStackTrace();
            return new IpAddressDO();
        }

        if (responseEntity != null && responseEntity.getStatusCode().equals(HttpStatus.OK)
            && responseEntity.getBody() != null) {
            Object data = responseEntity.getBody().getData();
            return new ObjectMapper().convertValue(data, IpAddressDO.class);
        } else {
            return new IpAddressDO();
        }
    }

    /**
     * 获取ip地址
     *
     * @param request 请求
     * @return ip
     */
    public static String getIpAddress(HttpServletRequest request) {
        String unknown = "unknown";
        String defaultHost = "127.0.0.1";
        //X-Forwarded-For：Squid 服务代理
        String ip = request.getHeader("x-forwarded-for");
        if (StringUtils.isEmpty(ip) || unknown.equalsIgnoreCase(ip)
            || defaultHost.equals(ip)) {
            //Proxy-Client-IP：apache 服务代理
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip) || unknown.equalsIgnoreCase(ip)
            || defaultHost.equals(ip)) {
            //WL-Proxy-Client-IP：WebLogic 服务代理
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip) || unknown.equalsIgnoreCase(ip)
            || defaultHost.equals(ip)) {
            //HTTP_CLIENT_IP：有些代理服务器
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtils.isEmpty(ip) || unknown.equalsIgnoreCase(ip)
            || defaultHost.equals(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StringUtils.isEmpty(ip) || unknown.equalsIgnoreCase(ip)
            || defaultHost.equals(ip)) {
            // X-Real-IP：Nginx 反向代理时置入的 header
            ip = request.getHeader("X-Real-IP");
            log.info("根据 Nginx 置入的 Header 获取到，请求者公网IP: " + ip);

            if (ip == null) {
                String remoteAddr = request.getRemoteAddr();
                log.info("请求未经 Nginx 转发，直接请求者IP为: " + remoteAddr);
            }
        }
        if (StringUtils.isEmpty(ip) || unknown.equalsIgnoreCase(ip) ||
            defaultHost.equals(ip)) {
            ip = request.getRemoteHost();
        }
        return ip;
    }

    /**
     * 是否内网IP
     *
     * @author zhengbaole
     * @lastEditor zhengbaole
     * @createTime 2022/2/28 10:10 AM
     * @editTime 2022/2/28 10:10 AM
     */
    public static boolean isInternalIp(String ip) throws UnknownHostException {
        log.info("判断局域网IP {}", ip);
        if (ip.equals("0:0:0:0:0:0:0:1") || ip.equals("127.0.0.1")) {
            log.info("判断局域网IP {} true", ip);
            return true;
        }
        InetAddress ia = InetAddress.getByName(ip);
        byte[] bytes = ia.getAddress();
        boolean result = isInternalIp(bytes);
        log.info("判断局域网IP {} {}", ip, result);
        return result;
    }

    /**
     * 是否内网IP
     *
     * @author zhengbaole
     * @lastEditor zhengbaole
     * @createTime 2022/2/28 10:10 AM
     * @editTime 2022/2/28 10:10 AM
     */
    private static boolean isInternalIp(byte[] addr) {
        final byte b0 = addr[0];
        final byte b1 = addr[1];
        // 10.x.x.x/8
        final byte SECTION_1 = 0x0A;
        // 172.16.x.x/12
        final byte SECTION_2 = (byte) 0xAC;
        final byte SECTION_3 = (byte) 0x10;
        final byte SECTION_4 = (byte) 0x1F;
        // 192.168.x.x/16
        final byte SECTION_5 = (byte) 0xC0;
        final byte SECTION_6 = (byte) 0xA8;
        switch (b0) {
            case SECTION_1:
                return true;
            case SECTION_2:
                if (b1 >= SECTION_3 && b1 <= SECTION_4) {
                    return true;
                }
            case SECTION_5:
                switch (b1) {
                    case SECTION_6:
                        return true;
                }
            default:
                return false;
        }
    }

}
