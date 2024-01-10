package com.etrade.puggo.third.ai;

import com.alibaba.fastjson.JSONObject;
import com.etrade.puggo.dao.ai.AiBrandListDao;
import com.etrade.puggo.dao.ai.AiKindListDao;
import com.etrade.puggo.dao.ai.AiSeriesListDao;
import java.util.List;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author niuzhenyu
 * @description : 定时更新
 * @date 2023/9/10 22:57
 **/
@Component
@Slf4j
public class UpdateDataSchedule {

    @Resource
    private AIService aiService;
    @Resource
    private AiKindListDao aiKindListDao;
    @Resource
    private AiBrandListDao aiBrandListDao;
    @Resource
    private AiSeriesListDao aiSeriesListDao;
    @Resource
    private SaveOrUpdateBrandService saveOrUpdateBrandService;

    private static List<Integer> kindList = List.of(83, 84, 85, 88, 89, 90, 91, 95);


    //@Scheduled(cron = "0 1 3 * * 1")
    public void update() {

        log.info("开始更新图灵鉴定基础数据...");
        long start = System.currentTimeMillis();

        AIServiceResult<List<JSONObject>> queryKindResult = aiService.queryKindList();

        if (queryKindResult.getErrorCode() == AIService.SUCCESS) {

            List<JSONObject> kindJsonObjectList = queryKindResult.getResult();
            aiKindListDao.batchSave(kindJsonObjectList);

            // 遍历品类
            for (JSONObject kindJsonObject : kindJsonObjectList) {

                int kindId = (int) kindJsonObject.get("id");

                if (!kindList.contains(kindId)) {
                    continue;
                }

                if (kindId != 0) {
                    AIServiceResult<List<JSONObject>> queryBrandResult = aiService.queryBrandList(kindId);

                    if (queryBrandResult.getErrorCode() == AIService.SUCCESS) {
                        List<JSONObject> brandJsonObjectList = queryBrandResult.getResult();
                        aiBrandListDao.batchSave(brandJsonObjectList, kindId);

                        // 遍历写入品牌
                        for (JSONObject brandJsonObject : brandJsonObjectList) {
                            saveOrUpdateBrandService.saveBrand(kindId, brandJsonObject);
                        }
                    }
                }
            }

            // 更新系列is_show
            updateSeriesShow();

            // 更新品牌is_show
            updateBrandShow();

            log.info("更新图灵鉴定基础数据完成, 总耗时(s):" + (System.currentTimeMillis() - start) / 1000);
        }
    }


    private void updateBrandShow() {
        aiBrandListDao.updateIsShow();
    }

    private void updateSeriesShow() {
        aiSeriesListDao.updateIsShow();
    }
}
