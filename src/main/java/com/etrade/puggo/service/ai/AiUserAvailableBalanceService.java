package com.etrade.puggo.service.ai;

import com.etrade.puggo.common.enums.LangErrorEnum;
import com.etrade.puggo.common.exception.CommonErrorV2;
import com.etrade.puggo.common.exception.ServiceException;
import com.etrade.puggo.dao.ai.AiAvailableBalanceDao;
import com.etrade.puggo.dao.ai.AiKindListDao;
import com.etrade.puggo.dao.ai.AiUserAvailableBalanceDao;
import com.etrade.puggo.service.BaseService;
import com.etrade.puggo.service.ai.pojo.AiUserAvailableBalanceVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhenyu
 * @version 1.0
 * @description: AI鉴定，用户可用次数
 * @date 2024/2/6 15:23
 */
@Service
public class AiUserAvailableBalanceService extends BaseService {


    @Resource
    private AiAvailableBalanceDao aiAvailableBalanceDao;

    @Resource
    private AiUserAvailableBalanceDao aiUserAvailableBalanceDao;

    @Resource
    private AiKindListDao aiKindListDao;


    public List<AiUserAvailableBalanceVO> listAvailableBalance() {
        return aiUserAvailableBalanceDao.list(userId());
    }


    public void deductAvailableBalance(Long userId, Integer kindId) {
        Integer rows = aiUserAvailableBalanceDao.deduct(userId, kindId);
        if (rows == 0) {
            throw new ServiceException(CommonErrorV2.AI_INSUFFICIENT_AVAILABLE_TIMES);
        }
    }


    public Integer getAvailableBalance(Long userId, Integer kindId) {
        Integer balance = aiUserAvailableBalanceDao.selectAvailableBalance(userId, kindId);
        return balance == null ? 0 : balance;
    }


    public void plusAvailableBalance(Long userId, Integer kindId, Integer availableBalance) {
        Integer kind = aiKindListDao.getOne(kindId);
        if (kind == null) {
            throw new ServiceException(LangErrorEnum.UNKNOWN_KIND.lang());
        }
        Integer rows = aiAvailableBalanceDao.deduct(kindId, availableBalance);
        if (rows == 0) {
            throw new ServiceException(CommonErrorV2.AI_INSUFFICIENT_AVAILABLE_TIME2);
        }
        aiUserAvailableBalanceDao.plus(userId, kindId, availableBalance);
    }


    public Integer getSystemAvailableBalance(Integer kindId) {
        Integer balance = aiAvailableBalanceDao.select(kindId);
        return balance == null ? 0 : balance;
    }

}
