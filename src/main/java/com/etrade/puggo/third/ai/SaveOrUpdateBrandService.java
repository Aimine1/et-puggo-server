package com.etrade.puggo.third.ai;

import com.alibaba.fastjson.JSONObject;
import com.etrade.puggo.dao.ai.AiPointListDao;
import com.etrade.puggo.dao.ai.AiSeriesListDao;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author niuzhenyu
 * @description : TODO
 * @date 2023/12/6 1:21
 **/
@Service
public class SaveOrUpdateBrandService {

    @Resource
    private AIService aiService;
    @Resource
    private AiSeriesListDao aiSeriesListDao;
    @Resource
    private AiPointListDao aiPointListDao;

    @Transactional
    public void saveBrand(int kindId, JSONObject brandJsonObject) {
        int brandId = (int) brandJsonObject.get("id");
        boolean isRelated1 = (boolean) brandJsonObject.get("is_related");

        if (brandId > 0) {
            if (isRelated1) {
                // 如果品牌可以直接关联鉴定点，则直接查询鉴定点，跳过查询系列
                batchSavePoint(AiPointListDao.BRAND_TYPE, brandId, kindId, brandId, 0);
                return;
            }

            AIServiceResult<List<JSONObject>> querySeriesRes = aiService.querySeriesList(brandId);
            if (querySeriesRes.getErrorCode() == AIService.SUCCESS) {

                List<JSONObject> seriesJsonObjectList = querySeriesRes.getResult();
                aiSeriesListDao.batchSave(seriesJsonObjectList, brandId);

                // 遍历系列
                for (JSONObject seriesJsonObject : seriesJsonObjectList) {
                    int seriesId = (int) seriesJsonObject.get("id");
                    boolean isRelated2 = (boolean) seriesJsonObject.get("is_related");

                    if (seriesId > 0 && isRelated2) {
                        batchSavePoint(AiPointListDao.SERIES_TYPE, seriesId, kindId, brandId, seriesId);
                    }
                }
            }
        }
    }

    private void batchSavePoint(String hierarchy, int queryId, int kindId, int brandId, int seriesId) {
        AIServiceResult<List<JSONObject>> queryPointResult = aiService.queryPointList(hierarchy, queryId);
        if (queryPointResult.getErrorCode() == AIService.SUCCESS) {
            List<JSONObject> pointJsonObjectList = queryPointResult.getResult();
            aiPointListDao.batchSave(pointJsonObjectList, queryId, kindId, brandId, seriesId);
        }
    }

}
