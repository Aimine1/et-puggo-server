package com.etrade.puggo.dao.groupon;

import static com.etrade.puggo.db.Tables.GROUPON_CLASS;

import com.etrade.puggo.dao.BaseDao;
import com.etrade.puggo.service.groupon.GrouponClassDTO;
import com.etrade.puggo.service.groupon.clazz.AddClassParam;
import com.etrade.puggo.service.groupon.clazz.EditClassParam;
import com.etrade.puggo.service.groupon.clazz.SelectClassVO;
import com.etrade.puggo.utils.OptionalUtils;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * @author niuzhenyu
 * @description : 团购券分类
 * @date 2023/6/6 17:37
 **/
@Repository
public class GrouponClassDao extends BaseDao {

    /**
     * 顶级class
     */
    public static final byte TOP_LEVEL = 1;

    public List<GrouponClassDTO> findGrouponClassList() {
        return db
            .select(
                GROUPON_CLASS.ID,
                GROUPON_CLASS.CLASS_NAME,
                GROUPON_CLASS.CLASS_PATH,
                GROUPON_CLASS.CLASS_LEVEL,
                GROUPON_CLASS.ICON_ID)
            .from(GROUPON_CLASS)
            .fetchInto(GrouponClassDTO.class);
    }


    public GrouponClassDTO findClassById(Integer classId) {
        return db
            .select(GROUPON_CLASS.ID, GROUPON_CLASS.CLASS_NAME, GROUPON_CLASS.CLASS_PATH, GROUPON_CLASS.CLASS_LEVEL)
            .from(GROUPON_CLASS)
            .where(GROUPON_CLASS.ID.eq(classId))
            .fetchAnyInto(GrouponClassDTO.class);
    }


    public Integer addClass(AddClassParam param) {
        return db.insertInto(GROUPON_CLASS, GROUPON_CLASS.CLASS_NAME, GROUPON_CLASS.CLASS_LEVEL, GROUPON_CLASS.ICON_ID)
            .values(param.getClassName(), param.getClassLevel(), OptionalUtils.valueOrDefault(param.getIconId()))
            .returning(GROUPON_CLASS.ID).fetchOne().getId();
    }

    public void updateClassPath(Integer classId, String classPath) {
        db.update(GROUPON_CLASS)
            .set(GROUPON_CLASS.CLASS_PATH, classPath)
            .where(GROUPON_CLASS.ID.eq(classId))
            .execute();
    }

    public int deleteClass(Integer classId) {
        return db.deleteFrom(GROUPON_CLASS).where(GROUPON_CLASS.ID.eq(classId)).execute();
    }

    public int editClass(EditClassParam param) {
        return db.update(GROUPON_CLASS)
            .set(GROUPON_CLASS.CLASS_NAME, param.getClassName())
            .set(GROUPON_CLASS.CLASS_LEVEL, param.getClassLevel())
            .set(GROUPON_CLASS.CLASS_PATH, param.getClassPath())
            .set(GROUPON_CLASS.ICON_ID, OptionalUtils.valueOrDefault(param.getIconId()))
            .where(GROUPON_CLASS.ID.eq(param.getId()))
            .execute();
    }

    public List<SelectClassVO> findClassSelect() {
        return db
            .select(
                GROUPON_CLASS.ID.as("value"),
                GROUPON_CLASS.CLASS_NAME.as("label"),
                GROUPON_CLASS.CLASS_PATH,
                GROUPON_CLASS.CLASS_LEVEL)
            .from(GROUPON_CLASS)
            .fetchInto(SelectClassVO.class);
    }


    public Integer findClassIdByClassPath(String classPath) {
        return db.select(GROUPON_CLASS.ID)
            .from(GROUPON_CLASS)
            .where(GROUPON_CLASS.CLASS_PATH.eq(classPath))
            .fetchAnyInto(Integer.class);
    }

}
