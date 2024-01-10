package com.etrade.puggo.dao.ai;

import static com.etrade.puggo.db.Tables.AI_BRAND_LIST;
import static com.etrade.puggo.db.Tables.AI_POINT_LIST;

import com.alibaba.fastjson.JSONObject;
import com.etrade.puggo.dao.BaseDao;
import com.etrade.puggo.db.tables.records.AiBrandListRecord;
import com.etrade.puggo.service.ai.pojo.AiBrandDTO;
import com.etrade.puggo.utils.SQLUtils;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.jooq.InsertValuesStep5;
import org.jooq.Record5;
import org.jooq.SelectConditionStep;
import org.springframework.stereotype.Repository;

/**
 * @author niuzhenyu
 * @description : AI鉴定品牌
 * @date 2023/9/10 19:45
 **/
@Repository
public class AiBrandListDao extends BaseDao {

    private static final byte NO_RELATED = 0;
    private static final byte IS_RELATED = 1;
    private static final byte IS_SHOW = 1;

//    public void batchSave(List<AiBrandDTO> list) {
//        InsertValuesStep5<AiBrandListRecord, Integer, String, Byte, String, Integer> sql = db.insertInto(
//                AI_BRAND_LIST)
//            .columns(
//                AI_BRAND_LIST.ID,
//                AI_BRAND_LIST.NAME,
//                AI_BRAND_LIST.IS_RELATED,
//                AI_BRAND_LIST.EXAMPLE_IMG,
//                AI_BRAND_LIST.KIND_ID
//            );
//        for (AiBrandDTO vo : list) {
//            sql.values(vo.getId(), vo.getName(), vo.getIsRelated(), vo.getExampleImg(), vo.getKindId());
//        }
//
//        sql.execute();
//    }


    public void batchSave(List<JSONObject> list, int kindId) {
        InsertValuesStep5<AiBrandListRecord, Integer, String, Byte, String, Integer> sql = db.insertInto(
                AI_BRAND_LIST)
            .columns(
                AI_BRAND_LIST.ID,
                AI_BRAND_LIST.NAME,
                AI_BRAND_LIST.IS_RELATED,
                AI_BRAND_LIST.EXAMPLE_IMG,
                AI_BRAND_LIST.KIND_ID
            );
        for (JSONObject obj : list) {
            boolean b = (boolean) obj.get("is_related");
            byte isRelated = b ? IS_RELATED : NO_RELATED;
            sql.values((int) obj.get("id"), (String) obj.get("name"), isRelated, (String) obj.get("example_img"),
                kindId);
        }

        sql.onDuplicateKeyIgnore().execute();
    }

    public List<AiBrandDTO> getList(Integer kindId, String brandName) {
        SelectConditionStep<Record5<Integer, String, Byte, String, Integer>> sql =
            db.select(AI_BRAND_LIST.ID,
                    AI_BRAND_LIST.NAME, AI_BRAND_LIST.IS_RELATED, AI_BRAND_LIST.EXAMPLE_IMG, AI_BRAND_LIST.KIND_ID)
                .from(AI_BRAND_LIST)
                .where(AI_BRAND_LIST.KIND_ID.eq(kindId).and(AI_BRAND_LIST.IS_SHOW.eq(IS_SHOW)));

        if (!StringUtils.isBlank(brandName)) {
            sql.and(AI_BRAND_LIST.NAME.like(SQLUtils.surroundingLike(brandName)));
        }

        return sql.fetchInto(AiBrandDTO.class);
    }


    public int updateIsShow() {
        db.update(AI_BRAND_LIST).set(AI_BRAND_LIST.IS_SHOW, (byte) 0).where(AI_BRAND_LIST.ID.gt(0)).execute();

        return db.update(AI_BRAND_LIST)
            .set(AI_BRAND_LIST.IS_SHOW, IS_SHOW)
            .where(
                AI_BRAND_LIST.ID.in(
                    db.select(AI_POINT_LIST.BRAND_ID).from(AI_POINT_LIST).groupBy(AI_POINT_LIST.BRAND_ID))
            ).execute();
    }

}
