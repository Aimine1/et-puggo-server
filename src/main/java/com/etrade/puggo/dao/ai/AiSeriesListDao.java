package com.etrade.puggo.dao.ai;

import static com.etrade.puggo.db.Tables.AI_POINT_LIST;
import static com.etrade.puggo.db.Tables.AI_SERIES_LIST;

import com.alibaba.fastjson.JSONObject;
import com.etrade.puggo.dao.BaseDao;
import com.etrade.puggo.db.tables.records.AiSeriesListRecord;
import com.etrade.puggo.service.ai.pojo.AiSeriesDTO;
import java.util.List;
import org.jooq.InsertValuesStep5;
import org.springframework.stereotype.Repository;

/**
 * @author niuzhenyu
 * @description : AI鉴定系列
 * @date 2023/9/10 19:45
 **/
@Repository
public class AiSeriesListDao extends BaseDao {

    private static final byte NO_RELATED = 0;
    private static final byte IS_RELATED = 1;
    private static final byte IS_SHOW = 1;

//    public void batchSave(List<AiSeriesDTO> list) {
//        InsertValuesStep5<AiSeriesListRecord, Integer, String, Byte, String, Integer> sql = db.insertInto(
//                AI_SERIES_LIST)
//            .columns(
//                AI_SERIES_LIST.ID,
//                AI_SERIES_LIST.NAME,
//                AI_SERIES_LIST.IS_RELATED,
//                AI_SERIES_LIST.EXAMPLE_IMG,
//                AI_SERIES_LIST.BRAND_ID
//            );
//        for (AiSeriesDTO vo : list) {
//            sql.values(vo.getId(), vo.getName(), vo.getIsRelated(), vo.getExampleImg(), vo.getBrandId());
//        }
//
//        sql.execute();
//    }

    public void batchSave(List<JSONObject> list, int brandId) {
        InsertValuesStep5<AiSeriesListRecord, Integer, String, Byte, String, Integer> sql = db.insertInto(
                AI_SERIES_LIST)
            .columns(
                AI_SERIES_LIST.ID,
                AI_SERIES_LIST.NAME,
                AI_SERIES_LIST.IS_RELATED,
                AI_SERIES_LIST.EXAMPLE_IMG,
                AI_SERIES_LIST.BRAND_ID
            );
        for (JSONObject obj : list) {
            boolean b = (boolean) obj.get("is_related");
            byte isRelated = b ? IS_RELATED : NO_RELATED;
            sql.values((int) obj.get("id"), (String) obj.get("name"), isRelated, (String) obj.get("example_img"),
                brandId);
        }

        sql.onDuplicateKeyIgnore().execute();
    }

    public List<AiSeriesDTO> getList(Integer brandId) {
        return db
            .select(AI_SERIES_LIST.ID, AI_SERIES_LIST.NAME, AI_SERIES_LIST.IS_RELATED, AI_SERIES_LIST.EXAMPLE_IMG,
                AI_SERIES_LIST.BRAND_ID)
            .from(AI_SERIES_LIST)
            .where(AI_SERIES_LIST.BRAND_ID.eq(brandId).and(AI_SERIES_LIST.IS_SHOW.eq(IS_SHOW)))
            .fetchInto(AiSeriesDTO.class);
    }

    public int updateIsShow() {
        db.update(AI_SERIES_LIST).set(AI_SERIES_LIST.IS_SHOW, (byte) 0).where(AI_SERIES_LIST.ID.gt(0)).execute();

        return db.update(AI_SERIES_LIST)
            .set(AI_SERIES_LIST.IS_SHOW, IS_SHOW)
            .where(
                AI_SERIES_LIST.ID.in(
                    db.select(AI_POINT_LIST.SERIES_ID).from(AI_POINT_LIST).groupBy(AI_POINT_LIST.SERIES_ID))
            ).execute();
    }

}
