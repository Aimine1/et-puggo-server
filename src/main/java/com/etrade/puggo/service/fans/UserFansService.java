package com.etrade.puggo.service.fans;

import com.etrade.puggo.common.page.PageContentContainer;
import com.etrade.puggo.dao.user.UserFansDao;
import com.etrade.puggo.service.BaseService;
import com.etrade.puggo.service.goods.sales.LaunchUserDO;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author niuzhenyu
 * @description : 用户粉丝服务
 * @date 2023/10/8 10:38
 **/
@Service
public class UserFansService extends BaseService {

    @Resource
    private UserFansDao userFansDao;

    public void follow(Long userId) {
        Long fansUserId = userId();
        if (fansUserId.equals(userId)) {
            return;
        }
        userFansDao.follow(userId, fansUserId);
    }

    public void unfollow(Long userId) {
        Long fansUserId = userId();
        if (fansUserId.equals(userId)) {
            return;
        }
        userFansDao.unfollow(userId, fansUserId);
    }

    public PageContentContainer<LaunchUserDO> listFollow(UserFansParam param) {
        return userFansDao.findFollowList(param, userId());
    }

    public List<LaunchUserDO> listFollow(Long userId) {
        return userFansDao.findFollowList(userId);
    }

    public PageContentContainer<LaunchUserDO> listFans(UserFansParam param) {
        return userFansDao.findFansList(param, userId());
    }

    public boolean isFans(Long userId) {
        return userFansDao.findOne(userId, userId()) != null;
    }

}
