package com.etrade.puggo.service.setting;

import com.etrade.puggo.dao.setting.UserProfileDao;
import com.etrade.puggo.service.BaseService;
import com.etrade.puggo.service.setting.pojo.KeyValParam;
import com.etrade.puggo.service.setting.pojo.SettingsVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhenyu
 * @version 1.0
 * @description: 用户偏好设置service
 * @date 2024/1/20 22:07
 */
@Service
public class UserProfileService extends BaseService {


    @Resource
    private UserProfileDao userProfileDao;


    public void set(KeyValParam param) {
        userProfileDao.saveOrUpdate(userId(), param.getKey(), param.getVal(), param.getComment());
    }


    public String k(String k) {
        return userProfileDao.get(userId(), k);
    }


    public List<SettingsVO> list() {
        return userProfileDao.list(userId());
    }

}
