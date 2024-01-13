package com.etrade.puggo.service.groupon;

import com.etrade.puggo.common.exception.ServiceException;
import com.etrade.puggo.common.page.PageContentContainer;
import com.etrade.puggo.common.page.PageParam;
import com.etrade.puggo.constants.GrouponState;
import com.etrade.puggo.common.enums.MoneyKindEnum;
import com.etrade.puggo.dao.groupon.GrouponClassDao;
import com.etrade.puggo.dao.groupon.GrouponCouponDao;
import com.etrade.puggo.dao.groupon.GrouponCouponDataDao;
import com.etrade.puggo.dao.groupon.GrouponCouponDetailDao;
import com.etrade.puggo.dao.groupon.GrouponCouponPicDao;
import com.etrade.puggo.dao.groupon.GrouponCouponPlanDao;
import com.etrade.puggo.dao.groupon.GrouponCouponRuleDao;
import com.etrade.puggo.db.tables.records.GrouponCouponRuleRecord;
import com.etrade.puggo.service.BaseService;
import com.etrade.puggo.service.groupon.GrouponCouponDetailDTO.Picture;
import com.etrade.puggo.service.groupon.GrouponCouponDetailDTO.Rule;
import com.etrade.puggo.service.groupon.admin.GenGrouponCouponDTO;
import com.etrade.puggo.service.groupon.admin.GenGrouponCouponDTO.GrouponDetail;
import com.etrade.puggo.service.groupon.admin.GenGrouponCouponDTO.GrouponStatement;
import com.etrade.puggo.service.groupon.admin.GrouponListParam;
import com.etrade.puggo.service.groupon.admin.GrouponListVO;
import com.etrade.puggo.service.groupon.dto.ApplicableShop;
import com.etrade.puggo.service.groupon.dto.CouponPlan;
import com.etrade.puggo.service.groupon.dto.GrouponRule;
import com.etrade.puggo.service.groupon.dto.S3Picture;
import com.etrade.puggo.service.groupon.dto.ServiceTime;
import com.etrade.puggo.service.groupon.dto.Text;
import com.etrade.puggo.service.groupon.user.UserGrouponService;
import com.etrade.puggo.utils.OptionalUtils;
import com.etrade.puggo.utils.StrUtils;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author niuzhenyu
 * @description : 团购券service
 * @date 2023/6/6 17:35
 **/
@Slf4j
@Service
public class GrouponCouponService extends BaseService {

    @Resource
    private GrouponCouponDao grouponCouponDao;
    @Resource
    private GrouponCouponDetailDao grouponDetailDao;
    @Resource
    private GrouponCouponRuleDao grouponRuleDao;
    @Resource
    private GrouponCouponPlanDao grouponPlanDao;
    @Resource
    private GrouponCouponPicDao grouponCouponPicDao;
    @Resource
    private GrouponCouponDataDao grouponCouponDataDao;
    @Resource
    private GrouponClassDao grouponClassDao;
    @Resource
    private UserGrouponService userGrouponService;


    /**
     * 团购券列表
     *
     * @author niuzhenyu
     * @lastEditor niuzhenyu
     * @createTime 2023/6/6 22:07
     * @editTime 2023/6/6 22:07
     **/
    public PageContentContainer<GrouponCouponDTO> listGrouponCoupon(ListGrouponCouponParam param) {

        if (!StrUtils.isEmpty(param.getSearchLabel())) {
            if (param.getSearchLabel().equals("最近浏览")) {

                param.setGrouponList(Collections.singletonList(-1L));

               /* List<UserBrowseHistoryVO> list = userGoodsService.getBrowseHistory(param);
                if (!list.isEmpty()) {
                    List<Long> grouponIdList = list.stream()
                        .sorted((Comparator.comparing(UserBrowseHistoryVO::getLastBrowseTime)))
                        .map(UserBrowseHistoryVO::getGoodsId).collect(Collectors.toList());
                    if (!grouponIdList.isEmpty()) {
                        param.setGrouponList(grouponIdList);
                    }
                }*/
            }
        }

        return grouponCouponDao.listGrouponCouponPage(param);
    }


    /**
     * 团购券详情
     *
     * @author niuzhenyu
     * @lastEditor niuzhenyu
     * @createTime 2023/6/6 23:07
     * @editTime 2023/6/6 23:07
     **/
    public GrouponCouponDetailDTO getGrouponCouponDetail(Long grouponId) {

        // 团购券
        GrouponCouponDetailDTO dto = grouponCouponDao.findGrouponCoupon(grouponId);

        if (dto == null) {
            throw new ServiceException("团购券不存在");
        }

        // 团购券规则
        dto.setRule(getGrouponRule(grouponId));

        List<Text> detailList = grouponDetailDao.findGrouponCouponDetailList(grouponId);

        // 基本信息
        List<Text> baseInfo = detailList.stream()
            .filter(d -> d.getType().equals(GrouponCouponDetailDao.TYPE_BASE_INFO))
            .sorted(Comparator.comparing(Text::getCreated))
            .collect(Collectors.toList());

        dto.setBaseInfoList(baseInfo);

        // 使用声明
        List<Text> useStatement = detailList.stream()
            .filter(d -> d.getType().equals(GrouponCouponDetailDao.TYPE_USE_STATEMENT))
            .sorted(Comparator.comparing(Text::getCreated))
            .collect(Collectors.toList());

        dto.setUseStatementList(useStatement);

        // 团购方案
        List<CouponPlan> planList = grouponPlanDao.findGrouponCouponPlanList(grouponId);
        dto.setCouponPlanList(planList);

        // 图片
        List<Picture> picList = grouponCouponPicDao.findPicList(grouponId);
        dto.setPictureList(picList);

        // 是否收藏了
        dto.setIsLike(userGrouponService.isLike(grouponId) != null);

        // browse number +1
        userGrouponService.browseGroupon(grouponId, userId());

        return dto;
    }


    public GrouponCouponDetailDTO.Rule getGrouponRule(long grouponId) {

        GrouponCouponDetailDTO.Rule dto = new Rule();

        GrouponCouponRuleRecord rule = grouponRuleDao.findGrouponCouponRule(grouponId);

        dto.setMinReplyHours(rule.getMinReplyHours());
        dto.setIsAllowCancel(rule.getIsAllowCancel().equals((byte) 1));
        dto.setLimitNumber(rule.getLimitNumber());

        // 服务时间
        dto.setServiceTime(
            ServiceTime.builder()
                .serviceTimeStart(rule.getServiceTimeStart())
                .serviceTimeEnd(rule.getServiceTimeEnd())
                .serviceTimeInterval(rule.getServiceTimeInterval())
                .serviceTimeUnit(rule.getServiceTimeUnit())
                .build()
        );

        // 门店相关
        dto.setApplicableShop(
            ApplicableShop.builder()
                .applicableBrand(rule.getApplicableBrand())
                .applicableShop(rule.getApplicableShop())
                .shopAddress(rule.getShopAddress())
                .shopArrivalMethod(rule.getShopArrivalMethod())
                .build()
        );

        return dto;
    }


    /**
     * 用户收藏团购券
     *
     * @author niuzhenyu
     * @lastEditor niuzhenyu
     * @createTime 2023/6/7 16:41
     * @editTime 2023/6/7 16:41
     **/
    public void likeGrouponCoupon(Long grouponId) {
        userGrouponService.likeGroupon(grouponId);
    }


    /**
     * 用户取消收藏团购券
     *
     * @author niuzhenyu
     * @lastEditor niuzhenyu
     * @createTime 2023/6/7 22:29
     * @editTime 2023/6/7 22:29
     **/
    public void unlikeGrouponCoupon(Long grouponId) {
        userGrouponService.unlikeGroupon(grouponId);
    }


    /**
     * 创建团购券
     *
     * @author niuzhenyu
     * @lastEditor niuzhenyu
     * @createTime 2023/6/10 22:14
     * @editTime 2023/6/10 22:14
     **/
    @Transactional(rollbackFor = Throwable.class)
    public void createGrouponCoupon(GenGrouponCouponDTO param) {

        Integer classId = param.getDetail().getClassId();

        GrouponClassDTO classDTO = grouponClassDao.findClassById(classId);
        if (classDTO == null) {
            throw new ServiceException("无效的分类");
        }

        param.getDetail().setClassPath(classDTO.getClassPath());

        String brand = OptionalUtils.valueOrDefault(param.getShop().getApplicableBrand());
        param.getDetail().setBrand(brand);

        String symbol = MoneyKindEnum.switchSymbol(param.getDetail().getMoneyKind());
        param.getDetail().setMoneyKind(symbol);

        // 创建基本数据
        Long grouponId = grouponCouponDao.saveGroupon(param.getDetail(), param.getState());

        // 创建图片
        List<S3Picture> pictureList = param.getDetail().getPictureList();
        grouponCouponPicDao.saveGrouponPic(pictureList, grouponId);

        // 创建规则
        grouponRuleDao.saveGrouponRule(param.getRule(), param.getShop(), grouponId);

        // 创建详情
        grouponDetailDao.saveGrouponDetail(param.getStatement().getDescList(), GrouponCouponDetailDao.TYPE_BASE_INFO,
            grouponId);
        grouponDetailDao.saveGrouponDetail(param.getStatement().getStatementList(),
            GrouponCouponDetailDao.TYPE_USE_STATEMENT, grouponId);

        // 创建方案
        grouponPlanDao.saveGrouponPlan(param.getPlans(), grouponId);

        // 创建data
        grouponCouponDataDao.newData(grouponId);
    }

    public PageContentContainer<GrouponListVO> listGrouponCouponWeb(GrouponListParam param) {

        PageContentContainer<GrouponListVO> pageList = grouponCouponDao.listGrouponCouponPageWeb(param);

        if (pageList.isEmpty()) {
            return pageList;
        }

        List<Long> grouponIdList = pageList.getList().stream().map(GrouponListVO::getGrouponId)
            .collect(Collectors.toList());

        List<Text> textList = grouponDetailDao.findGrouponCouponDetailList(grouponIdList);

        Map<Long, List<String>> baseInfoMap = textList.stream()
            .filter(d -> d.getType().equals(GrouponCouponDetailDao.TYPE_BASE_INFO))
            .collect(Collectors.groupingBy(Text::getGrouponId, Collectors.mapping(Text::getText, Collectors.toList())));

        Map<Long, List<String>> statementMap = textList.stream()
            .filter(d -> d.getType().equals(GrouponCouponDetailDao.TYPE_USE_STATEMENT))
            .collect(Collectors.groupingBy(Text::getGrouponId, Collectors.mapping(Text::getText, Collectors.toList())));

        List<CouponPlan> planList = grouponPlanDao.findGrouponCouponPlanListByGrouponId(grouponIdList);

        Map<Long, List<CouponPlan>> planMap = planList.stream()
            .collect(Collectors.groupingBy(CouponPlan::getGrouponId));

        for (GrouponListVO vo : pageList.getList()) {
            if (baseInfoMap.containsKey(vo.getGrouponId())) {
                vo.setBaseInfoList(baseInfoMap.get(vo.getGrouponId()));
            }

            if (statementMap.containsKey(vo.getGrouponId())) {
                vo.setUseStatementList(statementMap.get(vo.getGrouponId()));
            }

            if (planMap.containsKey(vo.getGrouponId())) {
                vo.setPlanList(planMap.get(vo.getGrouponId()));
            }
        }

        return pageList;

    }

    public void updateState(Long grouponId, String state) {
        GrouponCouponDetailDTO grouponCoupon = grouponCouponDao.findGrouponCoupon(grouponId);
        if (grouponCoupon == null) {
            throw new ServiceException("团购券不存在");
        }

        if (!GrouponState.isValid(state)) {
            throw new ServiceException("状态不合法");
        }

        grouponCouponDao.updateState(grouponId, state);
    }


    public GenGrouponCouponDTO getGrouponCouponDetailWeb(Long grouponId) {

        GenGrouponCouponDTO dto = new GenGrouponCouponDTO();

        // 团购券
        GrouponDetail detail = grouponCouponDao.findGrouponCouponDetail(grouponId);

        if (detail == null) {
            throw new ServiceException("团购券不存在");
        }

        dto.setGrouponId(grouponId);
        dto.setState(detail.getState());
        dto.setDetail(detail);

        // 分类
        Integer classId = grouponClassDao.findClassIdByClassPath(detail.getClassPath());
        detail.setClassId(classId);

        // moneyKind
        String moneyKind = MoneyKindEnum.switchCode(detail.getMoneyKind());
        detail.setMoneyKind(moneyKind);

        List<S3Picture> pictureList = grouponCouponPicDao.findS3PicList(grouponId);
        dto.getDetail().setPictureList(pictureList);

        GrouponCouponRuleRecord ruleRecord = grouponRuleDao.findGrouponCouponRule(grouponId);

        GrouponRule rule = new GrouponRule();
        rule.setServiceTimeUnit(ruleRecord.getServiceTimeUnit());
        rule.setServiceTimeInterval(ruleRecord.getServiceTimeInterval());
        rule.setServiceTimeStart(ruleRecord.getServiceTimeStart());
        rule.setServiceTimeEnd(ruleRecord.getServiceTimeEnd());
        rule.setLimitNumber(ruleRecord.getLimitNumber());
        rule.setEffectiveDays(ruleRecord.getEffectiveDays());
        rule.setMinReplyHours(ruleRecord.getMinReplyHours());
        rule.setIsAllowCancel(ruleRecord.getIsAllowCancel() == (byte) 1);

        dto.setRule(rule);

        ApplicableShop shop = new ApplicableShop();
        shop.setApplicableBrand(ruleRecord.getApplicableBrand());
        shop.setApplicableShop(ruleRecord.getApplicableShop());
        shop.setShopAddress(ruleRecord.getShopAddress());
        shop.setShopArrivalMethod(ruleRecord.getShopArrivalMethod());

        dto.setShop(shop);

        GrouponStatement statement = new GrouponStatement();
        dto.setStatement(statement);

        List<Text> detailList = grouponDetailDao.findGrouponCouponDetailList(grouponId);

        // 基本信息
        List<String> baseInfo = detailList.stream()
            .filter(d -> d.getType().equals(GrouponCouponDetailDao.TYPE_BASE_INFO))
            .sorted(Comparator.comparing(Text::getCreated))
            .map(Text::getText)
            .collect(Collectors.toList());

        statement.setDescList(baseInfo);

        // 使用声明
        List<String> useStatement = detailList.stream()
            .filter(d -> d.getType().equals(GrouponCouponDetailDao.TYPE_USE_STATEMENT))
            .sorted(Comparator.comparing(Text::getCreated))
            .map(Text::getText)
            .collect(Collectors.toList());

        statement.setStatementList(useStatement);

        // 团购方案
        List<CouponPlan> planList = grouponPlanDao.findGrouponCouponPlanList(grouponId);
        dto.setPlans(planList);

        return dto;

    }


    @Transactional(rollbackFor = Throwable.class)
    public void editGrouponCoupon(GenGrouponCouponDTO param) {

        Long grouponId = param.getGrouponId();
        if (null == grouponCouponDao.checkExistsGrouponCoupon(grouponId)) {
            throw new ServiceException("优惠券不存在");
        }

        Integer classId = param.getDetail().getClassId();

        GrouponClassDTO classDTO = grouponClassDao.findClassById(classId);
        if (classDTO == null) {
            throw new ServiceException("无效的分类");
        }

        param.getDetail().setClassPath(classDTO.getClassPath());

        String brand = OptionalUtils.valueOrDefault(param.getShop().getApplicableBrand());
        param.getDetail().setBrand(brand);

        String symbol = MoneyKindEnum.switchSymbol(param.getDetail().getMoneyKind());
        param.getDetail().setMoneyKind(symbol);

        // 修改基本数据
        int num = grouponCouponDao.updateGroupon(param.getDetail(), grouponId);
        log.info("团购基本数据更新结果：{}", num);

        // 修改图片
        int picRes = grouponCouponPicDao.clearGrouponPic(grouponId);
        if (picRes > 0) {
            List<S3Picture> pictureList = param.getDetail().getPictureList();
            grouponCouponPicDao.saveGrouponPic(pictureList, grouponId);
        }

        // 修改规则
        grouponRuleDao.updateGrouponRule(param.getRule(), param.getShop(), grouponId);

        // 修改详情
        int detailRes = grouponDetailDao.clearGrouponCouponDetail(grouponId);
        if (detailRes > 0) {
            grouponDetailDao.saveGrouponDetail(param.getStatement().getDescList(),
                GrouponCouponDetailDao.TYPE_BASE_INFO, grouponId);
            grouponDetailDao.saveGrouponDetail(param.getStatement().getStatementList(),
                GrouponCouponDetailDao.TYPE_USE_STATEMENT, grouponId);
        }

        // 修改方案
        int planRes = grouponPlanDao.clearGrouponCouponPlans(grouponId);
        if (planRes > 0) {
            grouponPlanDao.saveGrouponPlan(param.getPlans(), grouponId);
        }
    }


    /**
     * 用户收藏的团购券列表
     *
     * @author niuzhenyu
     * @lastEditor niuzhenyu
     * @createTime 2023/6/16 11:59
     * @editTime 2023/6/16 11:59
     **/
    public PageContentContainer<GrouponCouponDTO> getUserLikeGrouponList(PageParam page) {

        ListGrouponCouponParam param = new ListGrouponCouponParam(page);

        param.setGrouponList(Collections.singletonList(-1L));

        List<Long> grouponIdList = userGrouponService.getUserLikeGrouponList();

        if (!grouponIdList.isEmpty()) {
            param.setGrouponList(grouponIdList);
        }

        return listGrouponCoupon(param);

    }


}
