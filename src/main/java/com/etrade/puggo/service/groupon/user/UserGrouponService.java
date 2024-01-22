package com.etrade.puggo.service.groupon.user;

import com.etrade.puggo.dao.user.UserBrowsingHistoryDao;
import com.etrade.puggo.dao.user.UserLikesDao;
import com.etrade.puggo.service.BaseService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author niuzhenyu
 * @description : 用户团购券
 * @date 2023/6/7 18:09
 **/
@Service
public class UserGrouponService extends BaseService {

    @Resource
    private UserLikesDao userLikesDao;
    @Resource
    private UserBrowsingHistoryDao userBrowsingHistoryDao;


    @Async
    public void browseGroupon(Long grouponId, Long userId) {
        userBrowsingHistoryDao.browseGroupon(grouponId, userId);
    }

    public Long likeGroupon(Long grouponId) {
        return userLikesDao.likeGroupon(grouponId);
    }

    public Integer unlikeGroupon(Long grouponId) {
        return userLikesDao.unlikeGroupon(grouponId);
    }


    public Long isLike(Long grouponId) {
        return userLikesDao.isGrouponLike(grouponId, userId());
    }


    public List<Long> getUserLikeGrouponList() {
        return userLikesDao.findUserLikeGrouponList();
    }
}
