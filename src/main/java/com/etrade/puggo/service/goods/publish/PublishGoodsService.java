package com.etrade.puggo.service.goods.publish;

import com.etrade.puggo.common.exception.CommonError;
import com.etrade.puggo.common.exception.ServiceException;
import com.etrade.puggo.constants.GoodsImgType;
import com.etrade.puggo.constants.GoodsState;
import com.etrade.puggo.constants.LangErrorEnum;
import com.etrade.puggo.constants.MoneyKindEnum;
import com.etrade.puggo.dao.goods.GoodsClassDao;
import com.etrade.puggo.dao.goods.GoodsDao;
import com.etrade.puggo.dao.goods.GoodsDataDao;
import com.etrade.puggo.dao.goods.GoodsPictureDao;
import com.etrade.puggo.dao.goods.GoodsQualityDao;
import com.etrade.puggo.db.tables.records.GoodsRecord;
import com.etrade.puggo.service.BaseService;
import com.etrade.puggo.service.ai.AiIdentityService;
import com.etrade.puggo.service.log.GoodsLogsService;
import com.etrade.puggo.service.log.LogsOperate;
import com.etrade.puggo.service.setting.SettingService;
import com.etrade.puggo.stream.producer.StreamProducer;
import com.etrade.puggo.third.aws.S3PutObjectResult;
import com.etrade.puggo.third.im.pojo.SendNewsParam;
import com.etrade.puggo.utils.DateTimeUtils;
import com.etrade.puggo.utils.OptionalUtils;
import java.math.BigDecimal;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author niuzhenyu
 * @description : 商品发布
 * @date 2023/5/24 16:06
 **/
@Slf4j
@Service
public class PublishGoodsService extends BaseService {

    @Resource
    private GoodsDao goodsDao;
    @Resource
    private GoodsClassDao classDao;
    @Resource
    private GoodsQualityDao qualityDao;
    @Resource
    private GoodsPictureDao picturesDao;
    @Resource
    private GoodsDataDao goodsDataDao;
    @Resource
    private SettingService settingService;
    @Resource
    private GoodsLogsService goodsLogsService;
    @Resource
    private AiIdentityService aiIdentityService;
    @Resource
    private StreamProducer streamProducer;


    /**
     * 发布商品
     *
     * @author niuzhenyu
     * @lastEditor niuzhenyu
     * @createTime 2023/5/24 22:03
     * @editTime 2023/5/24 22:03
     **/
    @Transactional(rollbackFor = Throwable.class)
    public Long publish(PublishGoodsParam param, HttpServletRequest request) {

        String description = OptionalUtils.valueOrDefault(param.getDescription());
        String quality = OptionalUtils.valueOrDefault(param.getQuality());
        Boolean isFree = OptionalUtils.valueOrDefault(param.getIsFree(), false);
        BigDecimal realPrice = OptionalUtils.valueOrDefault(param.getRealPrice());
        BigDecimal originalPrice = OptionalUtils.valueOrDefault(param.getOriginalPrice());
        Boolean isDraft = OptionalUtils.valueOrDefault(param.getIsDraft());
        String brand = OptionalUtils.valueOrDefault(param.getBrand());
        String aiIdentifyNo = OptionalUtils.valueOrDefault(param.getAiIdentifyNo());
        Byte deliveryType = OptionalUtils.valueOrDefault(param.getDeliveryType());
        List<S3PutObjectResult> pictureList = param.getGoodsPicturesList();
        String title = OptionalUtils.valueOrDefault(param.getTitle());
        String classPath = param.getClassPath();

        GoodsClassVO goodsClassVO = classDao.findClass(classPath);
        if (goodsClassVO == null) {
            throw new ServiceException(CommonError.BAD_REQUEST.getCode(), LangErrorEnum.GOODS_CLASS.lang());
        }

        List<String> qualityList = qualityDao.findQualityList();
        if (!qualityList.contains(quality)) {
            throw new ServiceException(CommonError.BAD_REQUEST.getCode(), LangErrorEnum.GOODS_QUALITY.lang());
        }

        if (realPrice.compareTo(BigDecimal.ZERO) == 0 && !isFree) {
            throw new ServiceException(CommonError.BAD_REQUEST.getCode(), LangErrorEnum.GOODS_PRICE.lang());
        }

        if (description.length() == 0) {
            throw new ServiceException(CommonError.BAD_REQUEST.getCode(), LangErrorEnum.EMPTY_DESC.lang());
        }

        if (!aiIdentifyNo.isBlank() && !aiIdentityService.checkAiIdentifyNo(aiIdentifyNo)) {
            throw new ServiceException(CommonError.BAD_REQUEST.getCode(),
                LangErrorEnum.UNKNOWN_AUTHENTICATION_CODE.lang());
        }

        // 获取用户地址
        // IpAddressDO userAddress = getUserIpAddress(request);

        if (title.isBlank()) {
            title = description.substring(0, Math.min(20, description.length()));
        }

        String code = settingService.k("moneyKind");
        String symbol = MoneyKindEnum.switchSymbol(code);

        GoodsRecord goodsRecord = new GoodsRecord();
        goodsRecord.setTitle(title);
        goodsRecord.setDescription(description);
        goodsRecord.setRealPrice(realPrice);
        goodsRecord.setOriginalPrice(originalPrice);
        goodsRecord.setMoneyKind(symbol);
        goodsRecord.setLaunchUserId(userId());
        goodsRecord.setLaunchUserNickname(userName());
        goodsRecord.setLaunchLastTime(DateTimeUtils.now());
        goodsRecord.setQuality(quality);
        goodsRecord.setClassPath(classPath);
        goodsRecord.setState(isDraft ? GoodsState.DRAFT : GoodsState.PUBLISHED);
        goodsRecord.setBrand(brand);
        goodsRecord.setAiIdentifyNo(aiIdentifyNo);
        goodsRecord.setDeliveryType(deliveryType);
        // 国家区域
        goodsRecord.setCountry("");
        goodsRecord.setProvince("");
        goodsRecord.setCity("");
        goodsRecord.setDistrict("");

        // 添加商品
        Long goodsId = goodsDao.saveGoods(goodsRecord);

        // 添加商品图片
        picturesDao.savePic(goodsId, GoodsImgType.GOODS, pictureList);

        // 添加商品数据
        goodsDataDao.newData(goodsId);

        // 商品日志
        goodsLogsService.logs(goodsId, LogsOperate.PUBLISH, "发布了商品: " + title);

        String goodsMainPic = pictureList.get(0).getUrl();
        SendNewsParam newsParam = SendNewsParam.builder()
            .goodsId(goodsId)
            .goodsMainPic(goodsMainPic)
            .attach(String.format(LangErrorEnum.MSG_PUBLISH.lang(), userName()))
            .pushcontent(LangErrorEnum.MSG_NEW.lang())
            .toUserId(userId())
            .fromUserId(-1L)
            .build();
        streamProducer.sendNews(newsParam);

        return goodsId;
    }


    /**
     * 根据分类获取商品成色列表
     *
     * @author niuzhenyu
     * @lastEditor niuzhenyu
     * @createTime 2023/5/25 10:39
     * @editTime 2023/5/25 10:39
     **/
    public List<String> getQualityList() {
        return qualityDao.findQualityList();
    }


    /**
     * 编辑商品
     *
     * @author niuzhenyu
     * @lastEditor niuzhenyu
     * @createTime 2023/10/19 22:13
     * @editTime 2023/10/19 22:13
     **/
    @Transactional(rollbackFor = Throwable.class)
    public Long update(UpdatePublishGoodsParam param, HttpServletRequest request) {

        Long goodsId = param.getGoodsId();
        String description = OptionalUtils.valueOrDefault(param.getDescription());
        String quality = OptionalUtils.valueOrDefault(param.getQuality());
        Boolean isFree = OptionalUtils.valueOrDefault(param.getIsFree(), false);
        BigDecimal realPrice = OptionalUtils.valueOrDefault(param.getRealPrice());
        BigDecimal originalPrice = OptionalUtils.valueOrDefault(param.getOriginalPrice());
        Boolean isDraft = OptionalUtils.valueOrDefault(param.getIsDraft());
        String brand = OptionalUtils.valueOrDefault(param.getBrand());
        String aiIdentifyNo = OptionalUtils.valueOrDefault(param.getAiIdentifyNo());
        Byte deliveryType = OptionalUtils.valueOrDefault(param.getDeliveryType());
        List<S3PutObjectResult> pictureList = param.getGoodsPicturesList();
        String title = OptionalUtils.valueOrDefault(param.getTitle());
        String classPath = param.getClassPath();

        GoodsClassVO goodsClassVO = classDao.findClass(classPath);
        if (goodsClassVO == null) {
            throw new ServiceException(CommonError.BAD_REQUEST.getCode(), LangErrorEnum.GOODS_CLASS.lang());
        }

        List<String> qualityList = qualityDao.findQualityList();
        if (!qualityList.contains(quality)) {
            throw new ServiceException(CommonError.BAD_REQUEST.getCode(), LangErrorEnum.GOODS_QUALITY.lang());
        }

        if (realPrice.compareTo(BigDecimal.ZERO) == 0 && !isFree) {
            throw new ServiceException(CommonError.BAD_REQUEST.getCode(), LangErrorEnum.GOODS_PRICE.lang());
        }

        if (description.length() == 0) {
            throw new ServiceException(CommonError.BAD_REQUEST.getCode(), LangErrorEnum.EMPTY_DESC.lang());
        }

        if (!aiIdentifyNo.isBlank() && !aiIdentityService.checkAiIdentifyNo(aiIdentifyNo)) {
            throw new ServiceException(CommonError.BAD_REQUEST.getCode(),
                LangErrorEnum.UNKNOWN_AUTHENTICATION_CODE.lang());
        }

        // 获取用户地址
        // IpAddressDO userAddress = getUserIpAddress(request);

        if (title.isBlank()) {
            title = description.substring(0, Math.min(20, description.length()));
        }

        String code = settingService.k("moneyKind");
        String symbol = MoneyKindEnum.switchSymbol(code);

        GoodsRecord goodsRecord = new GoodsRecord();
        goodsRecord.setTitle(title);
        goodsRecord.setDescription(description);
        goodsRecord.setRealPrice(realPrice);
        goodsRecord.setOriginalPrice(originalPrice);
        goodsRecord.setMoneyKind(symbol);
        goodsRecord.setLaunchUserId(userId());
        goodsRecord.setLaunchUserNickname(userName());
        goodsRecord.setLaunchLastTime(DateTimeUtils.now());
        goodsRecord.setQuality(quality);
        goodsRecord.setClassPath(classPath);
        goodsRecord.setState(isDraft ? GoodsState.DRAFT : GoodsState.PUBLISHED);
        goodsRecord.setIsFree(isFree ? (byte) 1 : (byte) 0);
        goodsRecord.setBrand(brand);
        goodsRecord.setAiIdentifyNo(aiIdentifyNo);
        goodsRecord.setDeliveryType(deliveryType);
        goodsRecord.setId(goodsId);
        // 国家区域
        goodsRecord.setCountry("");
        goodsRecord.setProvince("");
        goodsRecord.setCity("");
        goodsRecord.setDistrict("");

        // 添加商品
        goodsDao.updateGoods(goodsRecord);

        // 添加商品图片
        picturesDao.deleteGoodsPics(goodsId);
        picturesDao.savePic(goodsId, GoodsImgType.GOODS, pictureList);

        // 添加商品数据
        goodsDataDao.newData(goodsId);

        // 商品日志
        goodsLogsService.logs(goodsId, LogsOperate.PUBLISH, "编辑了商品: " + title);

        String goodsMainPic = pictureList.get(0).getUrl();
        SendNewsParam newsParam = SendNewsParam.builder()
            .goodsId(goodsId)
            .goodsMainPic(goodsMainPic)
            .attach(String.format(LangErrorEnum.MSG_PUBLISH.lang(), userName()))
            .pushcontent(LangErrorEnum.MSG_NEW.lang())
            .toUserId(userId())
            .fromUserId(-1L)
            .build();
        streamProducer.sendNews(newsParam);

        return goodsId;
    }


}
