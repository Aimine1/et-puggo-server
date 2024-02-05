package com.etrade.puggo.service.goods.opt;

import com.etrade.puggo.common.constants.GoodsState;
import com.etrade.puggo.common.exception.ServiceException;
import com.etrade.puggo.dao.goods.GoodsDao;
import com.etrade.puggo.dao.goods.GoodsPictureDao;
import com.etrade.puggo.db.tables.records.GoodsRecord;
import com.etrade.puggo.service.BaseService;
import com.etrade.puggo.service.goods.sales.pojo.GoodsSimpleVO;
import com.etrade.puggo.stream.producer.StreamProducer;
import com.etrade.puggo.third.im.pojo.SendNewsParam;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author niuzhenyu
 * @description : 修改商品状态
 * @date 2023/10/7 10:21
 **/
@Service
public class UpdateGoodsStateService extends BaseService {

    @Resource
    private GoodsDao goodsDao;

    @Resource
    private GoodsPictureDao goodsPictureDao;

    @Resource
    private StreamProducer streamProducer;


    private static final Map<String, List<String>> stateFlowMap = new HashMap<>();

    static {
        // 草稿、已发布可以流转未删除
        stateFlowMap.put(GoodsState.DELETED, Arrays.asList(GoodsState.DRAFT, GoodsState.PUBLISHED));
        // 已发布可以流转为预定
        stateFlowMap.put(GoodsState.OCCUPY, List.of(GoodsState.PUBLISHED));
        // 兼容之前的
        stateFlowMap.put("OCCUPY", List.of(GoodsState.PUBLISHED));
        // 已发布可以流转为已售卖
        stateFlowMap.put(GoodsState.SOLD, Arrays.asList(GoodsState.PUBLISHED, GoodsState.OCCUPY));
        // 草稿、已发布、预定可以流转为下架
        stateFlowMap.put(GoodsState.OFF_SALE, Arrays.asList(GoodsState.DRAFT, GoodsState.OCCUPY, GoodsState.PUBLISHED));
        // 草稿、预留状态可以转为已发布状态
        stateFlowMap.put(GoodsState.PUBLISHED, Arrays.asList(GoodsState.DRAFT, GoodsState.OCCUPY));
    }


    public int updateState(Long goodsId, String state) {

        GoodsRecord goods = goodsDao.findOne(goodsId);
        if (goods == null) {
            throw new ServiceException("Unknown goods.");
        }

        if (!stateFlowMap.containsKey(state)) {
            throw new ServiceException("Unknown state.");
        }

        String curState = goods.getState();
        List<String> allowFlowStateList = stateFlowMap.get(state);

        if (!allowFlowStateList.contains(curState)) {
            throw new ServiceException("The current status cannot be changed");
        }

        return goodsDao.updateGoodsSale(goodsId, state);
    }


    public void updateStateInner(Long goodsId, String state) {
        goodsDao.updateGoodsSale(goodsId, state);
    }


    public void takeOffSaleWeb(Long goodsId) {
        isAdminRole();
        int i = updateState(goodsId, GoodsState.OFF_SALE);
        if (i > 0) {
            GoodsSimpleVO goodsInfo = goodsDao.findGoodsSimple(goodsId);

            if (goodsInfo == null) {
                return;
            }

            String goodsMainPic = goodsPictureDao.findGoodsMainPic(goodsId);

            SendNewsParam newsParam = SendNewsParam.builder()
                    .goodsId(goodsId)
                    .goodsMainPic(goodsMainPic)
                    .attach(String.format("%s 存在违规行为 您的商品已下架", "系统"))
                    .pushcontent("您有一条消息待查看")
                    .toUserId(goodsInfo.getLaunchUserId())
                    .build();

            streamProducer.sendNews(newsParam);
        }
    }
}
