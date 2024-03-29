package com.etrade.puggo.service.goods.user;

import com.etrade.puggo.common.page.PageContentContainer;
import com.etrade.puggo.common.page.PageParam;
import com.etrade.puggo.dao.goods.GoodsDataDao;
import com.etrade.puggo.dao.user.UserBrowsingHistoryDao;
import com.etrade.puggo.dao.user.UserLikesDao;
import com.etrade.puggo.service.BaseService;
import com.etrade.puggo.service.groupon.user.UserBrowseHistoryVO;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author niuzhenyu
 * @description : 用户团购券
 * @date 2023/6/7 18:09
 **/
@Service
public class UserGoodsService extends BaseService {

    @Resource
    private UserLikesDao userLikesDao;

    @Resource
    private UserBrowsingHistoryDao userBrowsingHistoryDao;

    @Resource
    private GoodsDataDao goodsDataDao;


    @Async
    @Transactional(rollbackFor = Throwable.class)
    public void browseGoods(Long goodsId, Long userId) {
        userBrowsingHistoryDao.browseGoods(goodsId, userId);
        goodsDataDao.incBrowseNumber(goodsId);
    }

    public Long likeGoods(Long goodsId) {
        return userLikesDao.likeGoods(goodsId);
    }

    public Integer unlikeGoods(Long goodsId) {
        return userLikesDao.unlikeGoods(goodsId);
    }

    public List<Long> isLike(List<Long> goodsIds) {
        return userLikesDao.isGoodsLike(goodsIds, userId());
    }


    public List<Long> getUserLikeGoodsList() {
        return userLikesDao.findUserLikeGoodsList();
    }


    public List<UserBrowseHistoryVO> getBrowseHistory(PageParam param) {
        PageContentContainer<UserBrowseHistoryVO> page = userBrowsingHistoryDao.getBrowseHistory(param);
        if (page.getList().isEmpty()) {
            return new ArrayList<>();
        } else {
            return page.getList();
        }
    }

}
