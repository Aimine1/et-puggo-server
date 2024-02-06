package com.etrade.puggo.dao.ai;

import com.alibaba.fastjson.JSONObject;
import com.etrade.puggo.common.filter.AuthContext;
import com.etrade.puggo.dao.BaseDao;
import com.etrade.puggo.db.tables.records.AiKindListRecord;
import com.etrade.puggo.service.ai.pojo.AiKindDTO;
import org.jooq.InsertValuesStep4;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.etrade.puggo.db.Tables.AI_KIND_LIST;

/**
 * @author niuzhenyu
 * @description : AI鉴定品类
 * @date 2023/9/10 19:33
 **/
@Repository
public class AiKindListDao extends BaseDao {

    private static final byte NO_RELATED = 0;
    private static final byte IS_RELATED = 1;
    private static final byte IS_SHOW = 1;

    public void batchSave(List<JSONObject> list) {
        InsertValuesStep4<AiKindListRecord, Integer, String, Byte, String> sql = db.insertInto(AI_KIND_LIST)
                .columns(
                        AI_KIND_LIST.ID,
                        AI_KIND_LIST.NAME,
                        AI_KIND_LIST.IS_RELATED,
                        AI_KIND_LIST.EXAMPLE_IMG
                );
        for (JSONObject obj : list) {
            boolean b = (boolean) obj.get("is_related");
            byte isRelated = b ? IS_RELATED : NO_RELATED;
            sql.values((int) obj.get("id"), (String) obj.get("name"), isRelated, (String) obj.get("example_img"));
        }

        sql.onDuplicateKeyIgnore().execute();
    }

    public List<AiKindDTO> getList() {
        return db.select(AI_KIND_LIST.ID, AI_KIND_LIST.NAME, AI_KIND_LIST.IS_RELATED, AI_KIND_LIST.EXAMPLE_IMG)
                .from(AI_KIND_LIST)
                .where(AI_KIND_LIST.IS_SHOW.eq(IS_SHOW).and(AI_KIND_LIST.LANG.eq(AuthContext.getLang())))
                .fetchInto(AiKindDTO.class);
    }


    public Integer getOne(Integer kindId) {
        return db.select(AI_KIND_LIST.ID)
                .from(AI_KIND_LIST)
                .where(AI_KIND_LIST.IS_SHOW.eq(IS_SHOW).and(AI_KIND_LIST.ID.eq(kindId)))
                .fetchAnyInto(Integer.class);
    }

}
