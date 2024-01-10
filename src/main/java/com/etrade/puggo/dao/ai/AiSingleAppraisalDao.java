package com.etrade.puggo.dao.ai;

import static com.etrade.puggo.db.Tables.AI_SINGLE_APPRAISAL;

import com.etrade.puggo.dao.BaseDao;
import com.etrade.puggo.db.tables.records.AiSingleAppraisalRecord;
import com.etrade.puggo.service.ai.pojo.IdentifySingleAppraisal;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * @author niuzhenyu
 * @description : AI单点鉴定结果
 * @date 2023/9/10 19:14
 **/
@Repository
public class AiSingleAppraisalDao extends BaseDao {


    public Integer check(Long userId, String operationId, Integer pointId) {
        return db.select(AI_SINGLE_APPRAISAL.ID).from(AI_SINGLE_APPRAISAL)
            .where(AI_SINGLE_APPRAISAL.USER_ID.eq(userId).and(AI_SINGLE_APPRAISAL.OPERATION_ID.eq(operationId))
                .and(AI_SINGLE_APPRAISAL.POINT_ID.eq(pointId)))
            .fetchAnyInto(Integer.class);
    }

    public void update(AiSingleAppraisalRecord record) {
        db.update(AI_SINGLE_APPRAISAL)
            .set(AI_SINGLE_APPRAISAL.DETECTION, record.getDetection())
            .set(AI_SINGLE_APPRAISAL.SAID, record.getSaid())
            .set(AI_SINGLE_APPRAISAL.GENUINE, record.getGenuine())
            .set(AI_SINGLE_APPRAISAL.GRADE, record.getGrade())
            .set(AI_SINGLE_APPRAISAL.GENUINE_STANDARD, record.getGenuineStandard())
            .set(AI_SINGLE_APPRAISAL.ORIGINAL_BOX, record.getOriginalBox())
            .set(AI_SINGLE_APPRAISAL.DETECTION_BOX, record.getDetectionBox())
            .set(AI_SINGLE_APPRAISAL.SHOW_FAKE, record.getShowFake())
            .set(AI_SINGLE_APPRAISAL.FAKE_POINTS, record.getFakePoints())
            .set(AI_SINGLE_APPRAISAL.IMAGE_URL, record.getImageUrl())
            .set(AI_SINGLE_APPRAISAL.CROP_IMAGE_URL, record.getCropImageUrl())
            .where(AI_SINGLE_APPRAISAL.ID.eq(record.getId()))
            .execute();
    }


    public void save(AiSingleAppraisalRecord record) {
        db.insertInto(AI_SINGLE_APPRAISAL)
            .columns(
                AI_SINGLE_APPRAISAL.USER_ID,
                AI_SINGLE_APPRAISAL.OPERATION_ID,
                AI_SINGLE_APPRAISAL.KIND_ID,
                AI_SINGLE_APPRAISAL.BRAND_ID,
                AI_SINGLE_APPRAISAL.SERIES_ID,
                AI_SINGLE_APPRAISAL.POINT_NAME,
                AI_SINGLE_APPRAISAL.DETECTION,
                AI_SINGLE_APPRAISAL.SAID,
                AI_SINGLE_APPRAISAL.GENUINE,
                AI_SINGLE_APPRAISAL.GRADE,
                AI_SINGLE_APPRAISAL.GENUINE_STANDARD,
                AI_SINGLE_APPRAISAL.ORIGINAL_BOX,
                AI_SINGLE_APPRAISAL.DETECTION_BOX,
                AI_SINGLE_APPRAISAL.SHOW_FAKE,
                AI_SINGLE_APPRAISAL.FAKE_POINTS,
                AI_SINGLE_APPRAISAL.IMAGE_URL,
                AI_SINGLE_APPRAISAL.CROP_IMAGE_URL,
                AI_SINGLE_APPRAISAL.POINT_ID
            )
            .values(
                userId(),
                record.getOperationId(),
                record.getKindId(),
                record.getBrandId(),
                record.getSeriesId(),
                record.getPointName(),
                record.getDetection(),
                record.getSaid(),
                record.getGenuine(),
                record.getGrade(),
                record.getGenuineStandard(),
                record.getOriginalBox(),
                record.getDetectionBox(),
                record.getShowFake(),
                record.getFakePoints(),
                record.getImageUrl(),
                record.getCropImageUrl(),
                record.getPointId()
            )
            .execute();
    }

    public List<IdentifySingleAppraisal> getGenuineSaidList(Long userId, String operationId, List<Integer> pointList) {
        return db
            .select(
                AI_SINGLE_APPRAISAL.POINT_ID,
                AI_SINGLE_APPRAISAL.SAID,
                AI_SINGLE_APPRAISAL.GENUINE
            )
            .from(AI_SINGLE_APPRAISAL)
            .where(
                AI_SINGLE_APPRAISAL.USER_ID.eq(userId)
                    .and(AI_SINGLE_APPRAISAL.OPERATION_ID.eq(operationId))
                    .and(AI_SINGLE_APPRAISAL.POINT_ID.in(ascendingOrder(pointList)))
                    .and(AI_SINGLE_APPRAISAL.GENUINE.eq((byte) 1))
            )
            .fetchInto(IdentifySingleAppraisal.class);
    }

}
