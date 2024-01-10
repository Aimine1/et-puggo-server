package com.etrade.puggo.dao.ai;

import static com.etrade.puggo.db.Tables.AI_BRAND_LIST;
import static com.etrade.puggo.db.Tables.AI_OVERALL_APPRAISAL;
import static com.etrade.puggo.db.Tables.AI_SERIES_LIST;
import static com.etrade.puggo.db.Tables.AI_SINGLE_APPRAISAL;

import com.etrade.puggo.common.page.PageContentContainer;
import com.etrade.puggo.constants.AIState;
import com.etrade.puggo.dao.BaseDao;
import com.etrade.puggo.db.tables.records.AiOverallAppraisalRecord;
import com.etrade.puggo.service.ai.pojo.AIIdentifyRecord;
import com.etrade.puggo.service.ai.pojo.AiIdentificationRecordParam;
import com.etrade.puggo.service.ai.pojo.IdentifyOverallAppraisal;
import com.etrade.puggo.service.ai.pojo.IdentifyReportVO;
import com.etrade.puggo.utils.SQLUtils;
import com.etrade.puggo.utils.StrUtils;
import java.util.List;
import org.jooq.Record5;
import org.jooq.SelectConditionStep;
import org.springframework.stereotype.Repository;

/**
 * @author niuzhenyu
 * @description : AI整包鉴定结果
 * @date 2023/9/10 19:23
 **/
@Repository
public class AiOverallAppraisalDao extends BaseDao {

    public void save(AiOverallAppraisalRecord record) {
        db.insertInto(AI_OVERALL_APPRAISAL)
            .columns(
                AI_OVERALL_APPRAISAL.USER_ID,
                AI_OVERALL_APPRAISAL.OPERATION_ID,
                AI_OVERALL_APPRAISAL.KIND_ID,
                AI_OVERALL_APPRAISAL.BRAND_ID,
                AI_OVERALL_APPRAISAL.SERIES_ID,
                AI_OVERALL_APPRAISAL.OAID,
                AI_OVERALL_APPRAISAL.GENUINE,
                AI_OVERALL_APPRAISAL.GRADE,
                AI_OVERALL_APPRAISAL.DESCRIPTION,
                AI_OVERALL_APPRAISAL.REPORT_URL,
                AI_OVERALL_APPRAISAL.STATE
            )
            .values(
                record.getUserId(),
                record.getOperationId(),
                record.getKindId(),
                record.getBrandId(),
                record.getSeriesId(),
                record.getOaid(),
                record.getGenuine(),
                record.getGrade(),
                record.getDescription(),
                record.getReportUrl(),
                record.getState()
            )
            .execute();
    }


    public List<IdentifyOverallAppraisal> getHistoryAppraisalList(Long userId) {
        return db.select(
                AI_OVERALL_APPRAISAL.OAID,
                AI_OVERALL_APPRAISAL.GENUINE,
                AI_OVERALL_APPRAISAL.GRADE,
                AI_OVERALL_APPRAISAL.DESCRIPTION,
                AI_OVERALL_APPRAISAL.REPORT_URL
            ).from(AI_OVERALL_APPRAISAL)
            .where(AI_OVERALL_APPRAISAL.USER_ID.eq(userId))
            .orderBy(AI_OVERALL_APPRAISAL.CREATED.desc())
            .fetchInto(IdentifyOverallAppraisal.class);
    }

    public IdentifyOverallAppraisal getAnyAppraisal() {
        return db.select(
                AI_OVERALL_APPRAISAL.OAID,
                AI_OVERALL_APPRAISAL.GENUINE,
                AI_OVERALL_APPRAISAL.GRADE,
                AI_OVERALL_APPRAISAL.DESCRIPTION,
                AI_OVERALL_APPRAISAL.REPORT_URL
            ).from(AI_OVERALL_APPRAISAL)
            .limit(1)
            .fetchAnyInto(IdentifyOverallAppraisal.class);
    }


    public PageContentContainer<AIIdentifyRecord> getAiIdentifyRecordPage(AiIdentificationRecordParam param) {
        SelectConditionStep<Record5<String, String, String, String, String>> sql = db
            .select(
                AI_OVERALL_APPRAISAL.OAID.as("aiIdentifyNo"),
                AI_OVERALL_APPRAISAL.STATE,
                AI_BRAND_LIST.NAME.as("brandName"),
                AI_SERIES_LIST.NAME.as("seriesName"),
                AI_SINGLE_APPRAISAL.IMAGE_URL
            )
            .from(AI_OVERALL_APPRAISAL)
            .innerJoin(AI_SINGLE_APPRAISAL)
            .on(AI_OVERALL_APPRAISAL.USER_ID.eq(AI_SINGLE_APPRAISAL.USER_ID)
                .and(AI_OVERALL_APPRAISAL.OPERATION_ID.eq(AI_SINGLE_APPRAISAL.OPERATION_ID)))
            .innerJoin(AI_BRAND_LIST)
            .on(AI_OVERALL_APPRAISAL.BRAND_ID.eq(AI_BRAND_LIST.ID))
            .innerJoin(AI_SERIES_LIST)
            .on(AI_OVERALL_APPRAISAL.SERIES_ID.eq(AI_SERIES_LIST.ID))
            .where(AI_OVERALL_APPRAISAL.USER_ID.eq(userId()));

        if (!StrUtils.isBlank(param.getState())) {
            sql.and(AI_OVERALL_APPRAISAL.STATE.eq(param.getState()));
        }

        sql.groupBy(AI_OVERALL_APPRAISAL.ID)
            .orderBy(AI_OVERALL_APPRAISAL.ID.desc());

        return getPageResult(sql, param, AIIdentifyRecord.class);
    }


    public IdentifyReportVO getReport(String aiIdentifyNo) {
        return db
            .select(
                AI_OVERALL_APPRAISAL.OAID.as("aiIdentifyNo"),
                AI_BRAND_LIST.NAME.as("brandName"),
                AI_SINGLE_APPRAISAL.IMAGE_URL
            )
            .from(AI_OVERALL_APPRAISAL)
            .innerJoin(AI_SINGLE_APPRAISAL)
            .on(AI_OVERALL_APPRAISAL.USER_ID.eq(AI_SINGLE_APPRAISAL.USER_ID)
                .and(AI_OVERALL_APPRAISAL.OPERATION_ID.eq(AI_SINGLE_APPRAISAL.OPERATION_ID)))
            .innerJoin(AI_BRAND_LIST)
            .on(AI_OVERALL_APPRAISAL.BRAND_ID.eq(AI_BRAND_LIST.ID))
            .where(AI_OVERALL_APPRAISAL.USER_ID.eq(userId()).and(AI_OVERALL_APPRAISAL.OAID.eq(aiIdentifyNo))
                .and(AI_OVERALL_APPRAISAL.STATE.eq(AIState.COMPLETE)))
            .groupBy(AI_OVERALL_APPRAISAL.ID)
            .fetchAnyInto(IdentifyReportVO.class);
    }


    public Long checkAiIdentifyNo(String aiIdentifyNo) {
        return db.select(AI_OVERALL_APPRAISAL.ID).from(AI_OVERALL_APPRAISAL)
            .where(AI_OVERALL_APPRAISAL.USER_ID.eq(userId())
                .and(AI_OVERALL_APPRAISAL.OAID.like(SQLUtils.suffixLike(aiIdentifyNo)))
                .and(AI_OVERALL_APPRAISAL.STATE.eq(AIState.COMPLETE))
            )
            .fetchAnyInto(Long.class);
    }
}
