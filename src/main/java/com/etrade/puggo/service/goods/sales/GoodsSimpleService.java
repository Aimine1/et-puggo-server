package com.etrade.puggo.service.goods.sales;

import com.etrade.puggo.dao.goods.GoodsDao;
import com.etrade.puggo.dao.goods.GoodsPictureDao;
import com.etrade.puggo.service.BaseService;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author niuzhenyu
 * @description : 货品简介
 * @date 2023/6/28 18:47
 **/
@Service
public class GoodsSimpleService extends BaseService {

    @Resource
    private GoodsDao goodsDao;
    @Resource
    private GoodsPictureDao goodsPictureDao;


    public List<GoodsSimpleVO> getGoodsList(List<Long> goodsIdList) {
        List<GoodsSimpleVO> list = goodsDao.findGoodsSimpleList(goodsIdList);

        List<Long> existsGoodsIdList = list.stream().map(GoodsSimpleVO::getGoodsId).collect(Collectors.toList());

        List<GoodsMainPicUrlDTO> mainPicList = goodsPictureDao.findGoodsMainPicList(existsGoodsIdList);

        Map<Long, String> mainPicMap = mainPicList.stream()
            .collect(Collectors.toMap(GoodsMainPicUrlDTO::getGoodsId, GoodsMainPicUrlDTO::getUrl, (o1, o2) -> o1));

        for (GoodsSimpleVO vo : list) {
            if (mainPicMap.containsKey(vo.getGoodsId())) {
                vo.setMainImgUrl(mainPicMap.get(vo.getGoodsId()));
            }
        }

        return list;
    }


    public Map<Long, GoodsSimpleVO> getGoodsMap(List<Long> goodsIdList) {
        List<GoodsSimpleVO> list = getGoodsList(goodsIdList);

        return list.stream().collect(Collectors.toMap(GoodsSimpleVO::getGoodsId, Function.identity()));
    }


    public GoodsSimpleVO getSingleGoods(Long goodsId) {
        List<GoodsSimpleVO> goodsList = getGoodsList(Collections.singletonList(goodsId));

        if (goodsList.isEmpty()) {
            return null;
        } else {
            return goodsList.get(0);
        }
    }

}
