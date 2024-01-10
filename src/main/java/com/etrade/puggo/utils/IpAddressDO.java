package com.etrade.puggo.utils;

import lombok.Data;

/**
 * @ClassName
 * @Description
 * @Author caoruiqi
 * @Date 2021/4/13 17:03
 */
@Data
public class IpAddressDO {

    // area: ""
    // area_id: ""
    // city: "北京"
    // city_id: "110100"
    // country: "中国"
    // country_id: "CN"
    // county: ""
    // county_id: null
    // ip: "123.116.132.62"
    // isp: "联通"
    // isp_id: "100026"
    // queryIp: "123.116.132.62"
    // region: "北京"
    // region_id: "110000"

    private String area;
    private String area_id;
    private String city;
    private String city_id;
    private String country;
    private String country_id;
    private String county;
    private String county_id;
    private String ip;
    private String isp;
    private String isp_id;
    private String region;
    private String region_id;
    private String queryIp;
}
