package com.etrade.puggo;


import com.etrade.puggo.dao.ai.AiBrandListDao;
import com.etrade.puggo.dao.ai.AiSeriesListDao;
import com.etrade.puggo.service.ai.AiIdentityService;
import com.etrade.puggo.service.ai.pojo.IdentifySingleParam;
import com.etrade.puggo.third.ai.UpdateDataSchedule;
import javax.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class ApplicationTests {

    @Autowired
    private UpdateDataSchedule updateDataSchedule;

    @Resource
    private AiIdentityService aiIdentityService;

    @Test
    void testSchedule(){
        updateDataSchedule.update();
    }

    @Test
    void testAi() {
        IdentifySingleParam param = new IdentifySingleParam();
        param.setImageUrl("https://everythingtradeltd-awsbucket-test.s3.ap-southeast-1.amazonaws.com/goods/CAP_53B85CA0-D826-44FC-8308-B70D9EBAD405.jpg");
        param.setPointId(2455);
        System.out.println(aiIdentityService.identifySinglePoint(param));
    }


    @Resource
    private AiBrandListDao aiBrandListDao;

    @Resource
    private AiSeriesListDao aiSeriesListDao;

    @Test
    void testDao() {
        aiBrandListDao.updateIsShow();

        aiSeriesListDao.updateIsShow();
    }

}
