package com.etrade.puggo.dao.ai;

import static com.etrade.puggo.db.Tables.AI_POINT_LIST;

import com.alibaba.fastjson.JSONObject;
import com.etrade.puggo.dao.BaseDao;
import com.etrade.puggo.db.tables.records.AiPointListRecord;
import com.etrade.puggo.service.ai.pojo.AiPointDTO;
import java.util.List;
import org.jooq.InsertValuesStep12;
import org.springframework.stereotype.Repository;

/**
 * @author niuzhenyu
 * @description : AI鉴定品牌
 * @date 2023/9/10 19:45
 **/
@Repository
public class AiPointListDao extends BaseDao {

    public static final String KIND_TYPE = "0";
    public static final String BRAND_TYPE = "1";
    public static final String SERIES_TYPE = "2";


    public void batchSave(List<JSONObject> list, int queryId, int kindId, int brandId, int seriesId) {

        InsertValuesStep12<AiPointListRecord, Integer, Integer, Integer, Integer, Integer, String, String, Byte, Byte, String, String, String> sql = db
            .insertInto(AI_POINT_LIST)
            .columns(
                AI_POINT_LIST.ID,
                AI_POINT_LIST.KIND_ID,
                AI_POINT_LIST.BRAND_ID,
                AI_POINT_LIST.SERIES_ID,
                AI_POINT_LIST.CATEGORY_ID,
                AI_POINT_LIST.DESCRIPTION,
                AI_POINT_LIST.EXAMPLE_IMG,
                AI_POINT_LIST.MUST,
                AI_POINT_LIST.IMPORTANT,
                AI_POINT_LIST.POINT_NAME,
                AI_POINT_LIST.STICK_FIGURE_URL,
                AI_POINT_LIST.BIG_STICK_FIGURE_URL
            );
        for (JSONObject obj : list) {
            byte must = (int) obj.get("must") == 1 ? (byte) 1 : (byte) 2;
            byte important = (boolean) obj.get("important") ? (byte) 1 : (byte) 0;

            sql.values(
                (int) obj.get("point_id"), kindId, brandId, seriesId, queryId, (String) obj.get("description"),
                (String) obj.get("example_img"), must, important, (String) obj.get("point_name"),
                (String) obj.get("stick_figure_url"), (String) obj.get("big_stick_figure_url")
            );
        }

        sql.onDuplicateKeyIgnore().execute();
    }

    public List<AiPointDTO> getList(Integer kindId, Integer brandId, Integer seriesId) {
        return db.select(
                AI_POINT_LIST.ID,
                AI_POINT_LIST.KIND_ID,
                AI_POINT_LIST.BRAND_ID,
                AI_POINT_LIST.SERIES_ID,
                AI_POINT_LIST.DESCRIPTION,
                AI_POINT_LIST.EXAMPLE_IMG,
                AI_POINT_LIST.MUST,
                AI_POINT_LIST.IMPORTANT,
                AI_POINT_LIST.POINT_NAME,
                AI_POINT_LIST.STICK_FIGURE_URL,
                AI_POINT_LIST.BIG_STICK_FIGURE_URL
            )
            .from(AI_POINT_LIST)
            .where(AI_POINT_LIST.KIND_ID.eq(kindId).and(AI_POINT_LIST.BRAND_ID.eq(brandId))
                .and(AI_POINT_LIST.SERIES_ID.eq(seriesId)))
            .orderBy(AI_POINT_LIST.MUST)
            .fetchInto(AiPointDTO.class);
    }

    public AiPointDTO getOne(Integer pointId) {
        return db.select(
                AI_POINT_LIST.ID,
                AI_POINT_LIST.KIND_ID,
                AI_POINT_LIST.BRAND_ID,
                AI_POINT_LIST.SERIES_ID,
                AI_POINT_LIST.DESCRIPTION,
                AI_POINT_LIST.EXAMPLE_IMG,
                AI_POINT_LIST.MUST,
                AI_POINT_LIST.IMPORTANT,
                AI_POINT_LIST.POINT_NAME,
                AI_POINT_LIST.STICK_FIGURE_URL,
                AI_POINT_LIST.BIG_STICK_FIGURE_URL,
                AI_POINT_LIST.CATEGORY_ID
            )
            .from(AI_POINT_LIST)
            .where(AI_POINT_LIST.ID.eq(pointId))
            .fetchAnyInto(AiPointDTO.class);
    }


    public List<Integer> getMustPointList(Integer kindId, Integer brandId, Integer seriesId) {
        return db
            .select(AI_POINT_LIST.ID)
            .from(AI_POINT_LIST)
            .where(AI_POINT_LIST.KIND_ID.eq(kindId)
                .and(AI_POINT_LIST.BRAND_ID.eq(brandId))
                .and(AI_POINT_LIST.SERIES_ID.eq(seriesId))
                .and(AI_POINT_LIST.MUST.eq((byte) 1))
            )
            .fetchInto(Integer.class);
    }

}
