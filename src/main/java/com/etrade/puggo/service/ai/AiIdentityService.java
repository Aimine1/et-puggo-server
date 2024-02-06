package com.etrade.puggo.service.ai;

import com.alibaba.fastjson.JSONObject;
import com.etrade.puggo.common.constants.AIState;
import com.etrade.puggo.common.enums.LangErrorEnum;
import com.etrade.puggo.common.exception.CommonErrorV2;
import com.etrade.puggo.common.exception.ServiceException;
import com.etrade.puggo.common.filter.AuthContext;
import com.etrade.puggo.common.page.PageContentContainer;
import com.etrade.puggo.dao.ai.AiAvailableBalanceDao;
import com.etrade.puggo.dao.ai.AiOverallAppraisalDao;
import com.etrade.puggo.dao.ai.AiPointListDao;
import com.etrade.puggo.dao.ai.AiSingleAppraisalDao;
import com.etrade.puggo.dao.goods.GoodsDao;
import com.etrade.puggo.db.tables.records.AiOverallAppraisalRecord;
import com.etrade.puggo.db.tables.records.AiSingleAppraisalRecord;
import com.etrade.puggo.service.BaseService;
import com.etrade.puggo.service.ai.pojo.*;
import com.etrade.puggo.third.ai.AIService;
import com.etrade.puggo.utils.StrUtils;
import com.etrade.puggo.utils.VerifyCodeUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


/**
 * @author niuzhenyu
 * @description : AI鉴定
 * @date 2023/9/12 20:35
 **/
@Service
@RefreshScope
public class AiIdentityService extends BaseService {

    @Resource
    private AiPointListDao aiPointListDao;
    @Resource
    private AiSingleAppraisalDao aiSingleAppraisalDao;
    @Resource
    private AiOverallAppraisalDao aiOverallAppraisalDao;
    @Resource
    private AiAvailableBalanceDao aiAvailableBalanceDao;
    @Resource
    private AIService aiService;
    @Resource
    private AiUserAvailableBalanceService aiUserAvailableBalanceService;
    @Resource
    private GoodsDao goodsDao;

    @Value("${ai.debug-mode:false}")
    private boolean isDebugMode;

    @Value("${ai.white-users:}")
    private String whiteUsers;


    /**
     * 检查用户是否开启调试模式
     *
     * @param userId 用户id
     * @return true为开启调试模式 false为严格模式
     */
    private boolean checkUserIsDebug(Long userId) {
        if (isDebugMode) {
            String[] whites = whiteUsers.split(",");
            Optional<String> any = Arrays.stream(whites).filter(o -> userId.equals(Long.valueOf(o))).findAny();
            return any.isPresent();
        }
        return false;
    }


    @Transactional
    public IdentifySingleAppraisal identifySinglePoint(IdentifySingleParam param) {
        Integer pointId = param.getPointId();
        String imageUrl = param.getImageUrl();
        String operationId =
                StrUtils.isBlank(param.getOperationId()) ? UUID.randomUUID().toString() : param.getOperationId();

        AiPointDTO pointRecord = aiPointListDao.getOne(pointId);
        if (pointRecord == null) {
            throw new ServiceException(LangErrorEnum.UNKNOWN_POINT.lang(AuthContext.getLang()));
        }

        // 调用接口鉴定
        JSONObject result;

        if (checkUserIsDebug(userId())) {
            result = buildJSONObject();
        } else {
            result = aiService.identifySinglePoint(pointId, imageUrl);
            if ((int) result.get("error_code") != AIService.SUCCESS) {
                throw new ServiceException((int) result.get("error_code"), (String) result.get("error_msg"));
            }
        }

        boolean detection = (boolean) result.get("detection");
        if (!detection) {
            throw new ServiceException(LangErrorEnum.RE_UPLOAD.lang(AuthContext.getLang()));
        }

        // 保存到db
        AiSingleAppraisalRecord record = new AiSingleAppraisalRecord();
        record.setUserId(userId());
        record.setOperationId(operationId);
        record.setKindId(pointRecord.getKindId());
        record.setBrandId(pointRecord.getBrandId());
        record.setSeriesId(pointRecord.getSeriesId());
        record.setPointId(pointRecord.getId());
        record.setPointName(pointRecord.getPointName());
        record.setDetection((boolean) result.get("detection") ? (byte) 1 : (byte) 0);
        record.setImageUrl(imageUrl);
        record.setSaid((String) result.get("said"));
        // genuine默认true
        //record.setGenuine((byte) 1);
        record.setGenuine((boolean) result.get("genuine") ? (byte) 1 : (byte) 0);
        if (result.get("grade") == null) {
            record.setGrade(new BigDecimal(0));
        } else {
            record.setGrade(new BigDecimal(String.valueOf(result.get("grade"))));
        }
        if (result.get("genuine_standard") == null) {
            record.setGenuineStandard(new BigDecimal(0));
        } else {
            record.setGenuineStandard(new BigDecimal(String.valueOf(result.get("genuine_standard"))));
        }
        record.setOriginalBox((String) result.get("original_box"));
        record.setDetectionBox((String) result.get("detection_box"));
        record.setCropImageUrl((String) result.get("crop_image_url"));
        record.setDescription((String) result.get("description"));
        // 是否需要展示偏假点的红圈圈 只有鉴定为假才有值
        boolean showFake = result.get("show_fake") != null && (boolean) result.get("show_fake");
        record.setShowFake(showFake ? (byte) 1 : (byte) 0);
        String fakePoints = result.get("fake_points") != null ? (String) result.get("fake_points") : "";
        record.setFakePoints(fakePoints);

        Integer id = aiSingleAppraisalDao.check(userId(), operationId, pointRecord.getId());
        if (id == null) {
            aiSingleAppraisalDao.save(record);
        } else {
            record.setId(id);
            aiSingleAppraisalDao.update(record);
        }

        // 构建IdentifySingleAppraisal
        IdentifySingleAppraisal appraisal = new IdentifySingleAppraisal();
        appraisal.setPointId(pointId);
        appraisal.setPointName(record.getPointName());
        appraisal.setGenuine(record.getGenuine());
        appraisal.setGrade(record.getGrade());
        appraisal.setDescription(record.getDescription());
        appraisal.setOperationId(operationId);
        appraisal.setSaid(record.getSaid());

        return appraisal;
    }


    private JSONObject buildJSONObject() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("detection", true);
        jsonObject.put("said", UUID.randomUUID().toString());
        jsonObject.put("genuine", true);
        jsonObject.put("grade", random());
        jsonObject.put("genuine_standard", new BigDecimal("0.5"));
        jsonObject.put("description", "");
        jsonObject.put("original_box", "");
        jsonObject.put("detection_box", "");
        jsonObject.put("crop_image_url", "");
        return jsonObject;
    }


    @Transactional
    public IdentifyOverallAppraisal identifyOverallPoints(IdentifyOverallParam param) {

        Integer kindId = param.getKindId();
        Integer brandId = param.getBrandId();
        Integer seriesId = param.getSeriesId();
        String operationId = param.getOperationId();

        List<Integer> mustPointList = aiPointListDao.getMustPointList(kindId, brandId, seriesId);

        if (mustPointList.isEmpty()) {
            throw new ServiceException(LangErrorEnum.UNKNOWN_POINT.lang(AuthContext.getLang()));
        }

        List<IdentifySingleAppraisal> singleList = aiSingleAppraisalDao.getGenuineSaidList(userId(), operationId,
                mustPointList);

        if (singleList.size() < mustPointList.size()) {
            throw new ServiceException(LangErrorEnum.MUST_POINT.lang(AuthContext.getLang()));
        }

        // 可用余额
        Integer availableBalance = aiUserAvailableBalanceService.getAvailableBalance(userId(), kindId);

        if (availableBalance <= 0) {
            throw new ServiceException(CommonErrorV2.AI_INSUFFICIENT_AVAILABLE_TIMES);
        }

        List<String> saidList = singleList.stream().map(IdentifySingleAppraisal::getSaid).collect(Collectors.toList());

        AiPointDTO onePoint = aiPointListDao.getOne(mustPointList.get(0));

        // 调接口鉴定
        JSONObject result = aiService.identifyOverallPoint(onePoint.getCategoryId(), saidList);

        if ((int) result.get("error_code") != AIService.SUCCESS) {
            throw new ServiceException((int) result.get("error_code"), (String) result.get("error_msg"));
        }

        // 扣除AI鉴定可用次数
        aiUserAvailableBalanceService.deductAvailableBalance(userId(), kindId);

        // 保存到db
        AiOverallAppraisalRecord record = buildAiOverallAppraisalRecord(kindId, brandId, seriesId, operationId, result);

        aiOverallAppraisalDao.saveOrUpdate(record);

        // 构建IdentifyOverallAppraisal
        IdentifyOverallAppraisal appraisal = new IdentifyOverallAppraisal();
        appraisal.setOaid(record.getOaid());
        appraisal.setGenuine(record.getGenuine());
        appraisal.setGrade(record.getGrade());
        appraisal.setDescription(record.getDescription());
        appraisal.setState(record.getState());
        appraisal.setReportUrl(record.getReportUrl());

        return appraisal;
    }


    private AiOverallAppraisalRecord buildAiOverallAppraisalLocalRecord(Integer kindId, Integer brandId,
                                                                        Integer seriesId, String operationId) {

        AiOverallAppraisalRecord record = new AiOverallAppraisalRecord();
        record.setUserId(userId());
        record.setKindId(kindId);
        record.setBrandId(brandId);
        record.setSeriesId(seriesId);
        record.setOperationId(operationId);
        record.setOaid(VerifyCodeUtils.aiIdentifyNo());
        record.setGenuine((byte) 1);
        record.setGrade(random());
        record.setDescription(LangErrorEnum.RESULT_IS_TRUE.lang(AuthContext.getLang()));
        record.setReportUrl("");
        record.setState(AIState.COMPLETE);
        return record;
    }


    private AiOverallAppraisalRecord buildAiOverallAppraisalRecord(Integer kindId, Integer brandId, Integer seriesId,
                                                                   String operationId, JSONObject result) {

        AiOverallAppraisalRecord record = new AiOverallAppraisalRecord();
        record.setUserId(userId());
        record.setKindId(kindId);
        record.setBrandId(brandId);
        record.setSeriesId(seriesId);
        record.setOperationId(operationId);
        record.setOaid((String) result.get("oaid"));
        boolean genuine = (boolean) result.get("genuine");
        record.setGenuine(genuine ? (byte) 1 : (byte) 0);
        if (result.get("grade") == null) {
            record.setGrade(BigDecimal.ZERO);
        } else {
            record.setGrade(new BigDecimal(String.valueOf(result.get("grade"))));
        }
        if (genuine) {
            record.setState(AIState.COMPLETE);
            record.setDescription(LangErrorEnum.RESULT_IS_TRUE.lang(AuthContext.getLang()));
            record.setReportUrl((String) result.get("report_url"));
        } else {
            record.setState(AIState.WAITING);
            record.setDescription((String) result.get("description"));
        }
        return record;
    }


    public void updateAvailableBalance(UpdateAvailableParam param) {
        Integer kindId = param.getKindId();
        Integer availableBalance = param.getAvailableBalance();

        aiAvailableBalanceDao.plus(kindId, availableBalance);
    }


    private static BigDecimal random() {
        BigDecimal base = new BigDecimal("0.50");
        while (true) {
            BigDecimal b = BigDecimal.valueOf(Math.random()).setScale(4, RoundingMode.HALF_UP);
            if (b.compareTo(base) > 0) {
                return b;
            }
        }
    }


    public PageContentContainer<AIIdentifyRecord> listAiIdentifyRecord(AiIdentificationRecordParam param) {
        return aiOverallAppraisalDao.getAiIdentifyRecordPage(param);
    }


    public IdentifyReportVO getReport(String aiIdentifyNo) {
        return aiOverallAppraisalDao.getReport(aiIdentifyNo);
    }


    public Boolean checkAiIdentifyNo(String aiIdentifyNo) {
        // ai鉴定记录中存在并且商品没有绑定
        return aiOverallAppraisalDao.checkAiIdentifyNo(aiIdentifyNo) != null
                && goodsDao.findAnyGoodsByAiIdentifyNo(aiIdentifyNo) == null;
    }

}
