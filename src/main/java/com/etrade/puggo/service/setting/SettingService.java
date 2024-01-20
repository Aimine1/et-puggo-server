package com.etrade.puggo.service.setting;

import com.alibaba.fastjson.JSONObject;
import com.etrade.puggo.common.enums.MoneyKindEnum;
import com.etrade.puggo.common.enums.SettingsEnum;
import com.etrade.puggo.common.exception.ServiceException;
import com.etrade.puggo.constants.GoodsImgType;
import com.etrade.puggo.dao.SettingDao;
import com.etrade.puggo.dao.goods.GoodsPictureDao;
import com.etrade.puggo.service.BaseService;
import com.etrade.puggo.service.groupon.MoneyKindVO;
import com.etrade.puggo.service.groupon.dto.S3Picture;
import com.etrade.puggo.utils.OptionalUtils;
import com.etrade.puggo.utils.StrUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author niuzhenyu
 * @description : 设置
 * @date 2023/6/15 21:39
 **/
@Slf4j
@Service
public class SettingService extends BaseService {


    @Resource
    private SettingDao settingDao;
    @Resource
    private GoodsPictureDao goodsPictureDao;


    public String k(String k) {
        return settingDao.getVal(k);
    }


    public void v(String k, String v) {
        settingDao.setVal(k, v);
    }


    public List<SettingsVO> list() {
        return settingDao.listSettings();
    }


    /**
     * 货币种类
     *
     * @author niuzhenyu
     * @lastEditor niuzhenyu
     * @createTime 2023/6/9 16:53
     * @editTime 2023/6/9 16:53
     **/
    public List<MoneyKindVO> listMoneyKind() {

        List<MoneyKindVO> list = new ArrayList<>();

        for (MoneyKindEnum value : MoneyKindEnum.values()) {
            list.add(new MoneyKindVO(value.getDesc(), value.getCode()));
        }

        return list;
    }


    /**
     * 修改设置
     *
     * @author niuzhenyu
     * @lastEditor niuzhenyu
     * @createTime 2023/6/30 16:13
     * @editTime 2023/6/30 16:13
     **/
    public void set(SettingsParam param) {
        isAdminRole();
        v(SettingsEnum.moneyKind.v(), OptionalUtils.valueOrDefault(param.getMoneyKind()));

        if (!parseSystemImAction(param.getSystemImAction())) {
            throw new ServiceException("请输入有效的IM账号");
        }
        v(SettingsEnum.systemImAction.v(), param.getSystemImAction());
    }


    private boolean parseSystemImAction(String json) {
        try {
            SystemImAction action = JSONObject.parseObject(json, SystemImAction.class);
            if (action != null) {
                if (!StrUtils.isBlank(action.getImAccid()) && !StrUtils.isBlank(action.getImToken())) {
                    return true;
                }
            }
            return false;

        } catch (Exception e) {
            log.info("json解析失败", e);
            return false;
        }
    }


    /**
     * 广告位
     *
     * @author niuzhenyu
     * @lastEditor niuzhenyu
     * @createTime 2023/6/30 17:52
     * @editTime 2023/6/30 17:52
     **/
    public List<AdvertisementVO> listAdvertisement() {
        return goodsPictureDao.findS3AdList();
    }


    /**
     * 添加广告位
     *
     * @author niuzhenyu
     * @lastEditor niuzhenyu
     * @createTime 2023/6/30 17:53
     * @editTime 2023/6/30 17:53
     **/
    public Long addAdvertisement(S3Picture S3) {
        isAdminRole();
        return goodsPictureDao.saveSingle(S3, GoodsImgType.AD);
    }


    /**
     * 删除广告位
     *
     * @author niuzhenyu
     * @lastEditor niuzhenyu
     * @createTime 2023/6/30 17:53
     * @editTime 2023/6/30 17:53
     **/
    public Integer deleteAdvertisement(S3Picture S3) {
        isAdminRole();
        return goodsPictureDao.deleteS3Object(S3.getKey(), GoodsImgType.AD);
    }


    public SystemImAction getSystemIm() {
        String systemImAction = k(SettingsEnum.systemImAction.v());
        return JSONObject.parseObject(systemImAction, SystemImAction.class);
    }


    public String getMoneyKind() {
        String moneyKind = k(SettingsEnum.moneyKind.v());
        return MoneyKindEnum.switchSymbol(moneyKind);
    }
}
