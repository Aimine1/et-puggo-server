package com.etrade.puggo.service.groupon.trade;

import com.etrade.puggo.common.exception.CommonError;
import com.etrade.puggo.common.exception.ServiceException;
import com.etrade.puggo.common.page.PageContentContainer;
import com.etrade.puggo.common.page.PageParam;
import com.etrade.puggo.constants.GrouponTradeState;
import com.etrade.puggo.common.enums.MobileRegularExpEnum;
import com.etrade.puggo.dao.groupon.GrouponCouponDao;
import com.etrade.puggo.dao.groupon.GrouponCouponPlanDao;
import com.etrade.puggo.dao.groupon.GrouponCouponRuleDao;
import com.etrade.puggo.dao.groupon.GrouponCouponTradeDao;
import com.etrade.puggo.db.tables.records.GrouponCouponRuleRecord;
import com.etrade.puggo.service.BaseService;
import com.etrade.puggo.service.groupon.GrouponCouponDetailDTO;
import com.etrade.puggo.service.groupon.GrouponCouponDetailDTO.Rule;
import com.etrade.puggo.service.groupon.GrouponCouponService;
import com.etrade.puggo.service.groupon.dto.CouponPlan;
import com.etrade.puggo.utils.DateTimeUtils;
import com.etrade.puggo.utils.IncrTradeNoUtils;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author niuzhenyu
 * @description : 团购券订单service
 * @date 2023/6/7 9:35
 **/
@Service
public class TradeGrouponCouponService extends BaseService {

    @Resource
    private GrouponCouponDao grouponCouponDao;
    @Resource
    private GrouponCouponPlanDao grouponCouponPlanDao;
    @Resource
    private GrouponCouponService grouponCouponService;
    @Resource
    private GrouponCouponTradeDao grouponCouponTradeDao;
    @Resource
    private GrouponCouponRuleDao grouponCouponRuleDao;
    @Resource
    private IncrTradeNoUtils incrTradeNoUtils;


    /**
     * 生成团购券订单
     *
     * @author niuzhenyu
     * @lastEditor niuzhenyu
     * @createTime 2023/6/7 10:14
     * @editTime 2023/6/7 10:14
     **/
    @Transactional(rollbackFor = Throwable.class)
    public TradeGrouponCouponDTO createTrade(TradeGrouponCouponParam param) {

        Long grouponId = param.getGrouponId();
        List<Integer> planList = param.getPlanList();
        LocalDate useageDate = param.getUseageDate();
        Integer nationalCode = param.getNationalCode();
        String contactPhone = param.getContactPhone();

        GrouponCouponDetailDTO grouponCoupon = grouponCouponDao.findGrouponCoupon(grouponId);

        if (grouponCoupon == null) {
            throw new ServiceException(CommonError.GROUPON_IS_UNKNOWN);
        }

        List<CouponPlan> grouponPlanList = grouponCouponPlanDao.findGrouponCouponPlanList(grouponId);
        List<Integer> grouponPlanIdList = grouponPlanList.stream()
            .map(CouponPlan::getPlanId).collect(Collectors.toList());
        List<Integer> unknownPlan = planList.stream()
            .filter(o -> !grouponPlanIdList.contains(o)).collect(Collectors.toList());

        if (!unknownPlan.isEmpty()) {
            throw new ServiceException(CommonError.GROUPON_PLAN_IS_UNKNOWN);
        }

        // 校验日期
        if (LocalDate.now().compareTo(useageDate) > 0) {
            throw new ServiceException(CommonError.GROUPON_USEAGE_DATE_ILLEGAL);
        }

        Rule rule = grouponCouponService.getGrouponRule(grouponId);
        if (rule.getServiceTime().getServiceTimeInterval().equals("DATE")) {
            LocalDate endDate = DateTimeUtils.stringDateToLocalDate(rule.getServiceTime().getServiceTimeEnd());
            if (endDate.compareTo(useageDate) > 0) {
                throw new ServiceException(CommonError.GROUPON_USEAGE_DATE_ILLEGAL);
            }
        }

        // 校验区号和手机号
        Pattern pattern = Pattern.compile(MobileRegularExpEnum.getRegularExp(nationalCode));
        Matcher matcher = pattern.matcher(nationalCode + contactPhone);
        if (!matcher.matches()) {
            throw new ServiceException(CommonError.TELEPHONE_IS_ILLEGAL);
        }

        // 生成订单
        String tradeNo = incrTradeNoUtils.get("JY");
        Long tradeId = grouponCouponTradeDao.createTrade(param, tradeNo);
        TradeGrouponCouponDTO trade = new TradeGrouponCouponDTO();
        trade.setTradeId(tradeId);
        trade.setTradeNo(tradeNo);

        return trade;
    }


    /**
     * 取消订购
     *
     * @author niuzhenyu
     * @lastEditor niuzhenyu
     * @createTime 2023/6/7 16:10
     * @editTime 2023/6/7 16:10
     **/
    public void cancelTrade(Long tradeId) {
        TradeGrouponCouponDetailDTO trade = grouponCouponTradeDao.getTradeDetail(tradeId, userId());
        if (trade == null) {
            throw new ServiceException("无效订单");
        }

        long grouponId = trade.getGrouponId();

        GrouponCouponRuleRecord rule = grouponCouponRuleDao.findGrouponCouponRule(grouponId);

        byte isAllow = (byte) 1;
        if (rule.getIsAllowCancel() != isAllow) {
            throw new ServiceException("该团购券不允许取消");
        }

        grouponCouponTradeDao.updateTradeState(tradeId, GrouponTradeState.CANCELED);
    }


    /**
     * 查询用户团购订单列表
     *
     * @author niuzhenyu
     * @lastEditor niuzhenyu
     * @createTime 2023/6/7 16:10
     * @editTime 2023/6/7 16:10
     **/
    public PageContentContainer<TradeGrouponCouponListVO> getUserTradeList(PageParam param) {
        return grouponCouponTradeDao.getUserTradeList(param);
    }


    /**
     * 查询团购订单详情
     *
     * @author niuzhenyu
     * @lastEditor niuzhenyu
     * @createTime 2023/6/7 16:12
     * @editTime 2023/6/7 16:12
     **/
    public TradeGrouponCouponDetailDTO getTradeDetail(Long tradeId) {
        TradeGrouponCouponDetailDTO detail = grouponCouponTradeDao.getTradeDetail(tradeId, userId());

        String planIds = grouponCouponTradeDao.findPlanIdsByTradeId(tradeId);

        List<Long> planIdList = Arrays.stream(planIds.split(",")).map(Long::parseLong).collect(Collectors.toList());

        List<CouponPlan> planList = grouponCouponPlanDao.findGrouponCouponPlanList(detail.getGrouponId(), planIdList);

        detail.setPlanList(planList);

        return detail;
    }


    public PageContentContainer<TradeGrouponCouponWebVO> getWebTradeList(TradeGrouponCouponSearchParam param) {

        return grouponCouponTradeDao.getTradeList(param);
    }


    public TradeGrouponCouponWebVO getWebTradeDetail(Long tradeId) {

        TradeGrouponCouponWebVO trade = grouponCouponTradeDao.getTradeDetail(tradeId);

        if (trade == null) {
            return null;
        }

        List<Long> planIdList = Arrays.stream(trade.getPlanIds().split(",")).map(Long::parseLong)
            .collect(Collectors.toList());

        List<CouponPlan> planList = grouponCouponPlanDao.findGrouponCouponPlanList(trade.getGrouponId(), planIdList);

        trade.setPlanList(planList);

        return trade;
    }

}
