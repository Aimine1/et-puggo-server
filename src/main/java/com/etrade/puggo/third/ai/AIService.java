package com.etrade.puggo.third.ai;

import com.alibaba.fastjson.JSONObject;
import com.etrade.puggo.utils.HttpClientUtils;
import com.etrade.puggo.utils.HttpResult;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

/**
 * @author niuzhenyu
 * @description : 图灵鉴定API
 * @date 2023/9/10 21:23
 **/
@Service
@RefreshScope
public class AIService {

    public static final int SUCCESS = 0;

    @Value("${ai.domain-uri:https://openplatform.turingsenseai.com}")
    private String domainUrl;

    @Value("${ai.open-platform-api-key:9471eeb8e135cca596b1ac4afc1c3439}")
    private String apiKey;

    @Value("${ai.open-platform-api-secret:9471eeb8e135cca596b1ac4afc1c3439}")
    private String apiSecret;

    private Map<String, String> commonHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Open-Platform-Api-Key", apiKey);
        headers.put("Open-Platform-Api-Secret", apiSecret);
        return headers;
    }


    /**
     * 查询品类列表
     */
    public AIServiceResult<List<JSONObject>> queryKindList() {
        String url = domainUrl + "/aiservice/kindList";
        // header
        Map<String, String> headers = commonHeaders();
        // params
        JSONObject params = new JSONObject();
        params.put("type", 3);
        HttpResult result = HttpClientUtils.doGet(url, params, headers);
        if (result != null && result.getStatus() == HttpStatus.SC_OK) {
            return JSONObject.parseObject(result.getStringEntity(), AIServiceResult.class);
        }
        return null;
    }


    /**
     * 查询品牌列表
     */
    public AIServiceResult<List<JSONObject>> queryBrandList(Integer kindId) {
        String url = domainUrl + "/aiservice/brandList";
        // header
        Map<String, String> headers = commonHeaders();
        // params
        JSONObject params = new JSONObject();
        params.put("kind_id", kindId);
        params.put("type", 3);
        String json = params.toJSONString();
        HttpResult result = HttpClientUtils.doPostJson(url, json, headers);
        if (result != null && result.getStatus() == HttpStatus.SC_OK) {
            return JSONObject.parseObject(result.getStringEntity(), AIServiceResult.class);
        }
        return null;
    }


    /**
     * 查询系列列表
     */
    public AIServiceResult<List<JSONObject>> querySeriesList(Integer brandId) {
        String url = domainUrl + "/aiservice/seriesList";
        // header
        Map<String, String> headers = commonHeaders();
        // params
        JSONObject params = new JSONObject();
        params.put("brand_id", brandId);
        params.put("type", 3);
        String json = params.toJSONString();
        HttpResult result = HttpClientUtils.doPostJson(url, json, headers);
        if (result != null && result.getStatus() == HttpStatus.SC_OK) {
            return JSONObject.parseObject(result.getStringEntity(), AIServiceResult.class);
        }
        return null;
    }


    /**
     * 查询鉴定点列表
     */
    public AIServiceResult<List<JSONObject>> queryPointList(String hierarchy, Integer queryId) {
        String url = domainUrl + "/aiservice/pointList";
        // header
        Map<String, String> headers = commonHeaders();
        // params
        JSONObject params = new JSONObject();
        params.put("hierarchy", hierarchy);
        params.put("query_id", queryId);
        params.put("type", 1);
        String json = params.toJSONString();
        HttpResult result = HttpClientUtils.doPostJson(url, json, headers);
        if (result != null && result.getStatus() == HttpStatus.SC_OK) {
            return JSONObject.parseObject(result.getStringEntity(), AIServiceResult.class);
        }
        return null;
    }


    /**
     * 单鉴
     */
    public JSONObject identifySinglePoint(Integer pointId, String imageUrl) {
        String url = domainUrl + "/aiservice/SingleAppraisal";
        // header
        Map<String, String> headers = commonHeaders();
        // params
        JSONObject params = new JSONObject();
        params.put("point_id", pointId);
        params.put("image_url", imageUrl);
        String json = params.toJSONString();
        // 执行
        HttpResult result = HttpClientUtils.doPostJson(url, json, headers);
        if (result != null && result.getStatus() == HttpStatus.SC_OK) {
            return JSONObject.parseObject(result.getStringEntity());
        }
        return null;
    }


    /**
     * 整包鉴定
     */
    public JSONObject identifyOverallPoint(Integer queryId, List<String> saidList) {
        String url = domainUrl + "/aiservice/OverallAppraisal";
        // header
        Map<String, String> headers = commonHeaders();
        // params
        JSONObject params = new JSONObject();
        params.put("category_id", queryId);
        params.put("salist", saidList);
        String json = params.toJSONString();
        // 执行
        HttpResult result = HttpClientUtils.doPostJson(url, json, headers);
        if (result != null && result.getStatus() == HttpStatus.SC_OK) {
            return JSONObject.parseObject(result.getStringEntity());
        }
        return null;
    }

}
