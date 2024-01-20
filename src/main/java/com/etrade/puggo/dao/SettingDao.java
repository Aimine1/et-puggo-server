package com.etrade.puggo.dao;

import static com.etrade.puggo.db.Tables.SETTING;

import com.etrade.puggo.service.setting.SettingsVO;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * @author niuzhenyu
 * @description : 设置
 * @date 2023/6/15 21:40
 **/
@Repository
public class SettingDao extends BaseDao {

    public String getVal(String key) {
        return db.select(SETTING.VALUE).from(SETTING).where(SETTING.KEY.eq(key)).fetchAnyInto(String.class);
    }


    public void setVal(String key, String val) {
        db.insertInto(SETTING, SETTING.KEY, SETTING.VALUE).values(key, val)
            .onDuplicateKeyUpdate()
            .set(SETTING.VALUE, val)
            .execute();
    }


    public List<SettingsVO> listSettings() {
        return db
            .select(
                SETTING.KEY,
                SETTING.VALUE,
                SETTING.COMMENT
            )
            .from(SETTING)
            .fetchInto(SettingsVO.class);
    }
}
