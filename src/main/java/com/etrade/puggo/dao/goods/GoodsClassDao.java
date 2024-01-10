package com.etrade.puggo.dao.goods;

import static com.etrade.puggo.db.Tables.GOODS_CLASS;

import com.etrade.puggo.dao.BaseDao;
import com.etrade.puggo.filter.AuthContext;
import com.etrade.puggo.service.goods.clazz.SelectClassVO2;
import com.etrade.puggo.service.goods.publish.GoodsClassVO;
import com.etrade.puggo.service.groupon.clazz.AddClassParam;
import com.etrade.puggo.service.groupon.clazz.EditClassParam;
import com.etrade.puggo.utils.OptionalUtils;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * @author niuzhenyu
 * @description : 商品分类级别
 * @date 2023/5/24 18:19
 **/
@Repository
public class GoodsClassDao extends BaseDao {

    public GoodsClassVO findClass(int classId) {
        return db
            .select(GOODS_CLASS.ID.as("class_id"), GOODS_CLASS.CLASS_PATH, GOODS_CLASS.CLASS_NAME)
            .from(GOODS_CLASS)
            .where(GOODS_CLASS.ID.eq(classId))
            .fetchAnyInto(GoodsClassVO.class);
    }

    public GoodsClassVO findClass(String classPath) {
        return db
            .select(GOODS_CLASS.ID.as("class_id"), GOODS_CLASS.CLASS_PATH, GOODS_CLASS.CLASS_NAME)
            .from(GOODS_CLASS)
            .where(GOODS_CLASS.CLASS_PATH.eq(classPath))
            .fetchAnyInto(GoodsClassVO.class);
    }

    public List<GoodsClassVO> findClassList() {
        return db
            .select(GOODS_CLASS.ID, GOODS_CLASS.ID.as("class_id"), GOODS_CLASS.CLASS_PATH, GOODS_CLASS.CLASS_NAME,
                GOODS_CLASS.CLASS_LEVEL, GOODS_CLASS.ICON_ID)
            .from(GOODS_CLASS)
            .where(GOODS_CLASS.LANG.eq(AuthContext.getLang()))
            .fetchInto(GoodsClassVO.class);
    }

    public Integer addClass(AddClassParam param) {
        return db.insertInto(GOODS_CLASS, GOODS_CLASS.CLASS_NAME, GOODS_CLASS.CLASS_LEVEL, GOODS_CLASS.ICON_ID)
            .values(param.getClassName(), param.getClassLevel(), OptionalUtils.valueOrDefault(param.getIconId()))
            .returning(GOODS_CLASS.ID).fetchOne().getId();
    }

    public void updateClassPath(Integer classId, String classPath) {
        db.update(GOODS_CLASS)
            .set(GOODS_CLASS.CLASS_PATH, classPath)
            .where(GOODS_CLASS.ID.eq(classId))
            .execute();
    }

    public int editClass(EditClassParam param) {
        return db.update(GOODS_CLASS)
            .set(GOODS_CLASS.CLASS_NAME, param.getClassName())
            .set(GOODS_CLASS.CLASS_LEVEL, param.getClassLevel())
            .set(GOODS_CLASS.CLASS_PATH, param.getClassPath())
            .set(GOODS_CLASS.ICON_ID, OptionalUtils.valueOrDefault(param.getIconId()))
            .where(GOODS_CLASS.ID.eq(param.getId()))
            .execute();
    }


    public int deleteClass(Integer classId) {
        return db.deleteFrom(GOODS_CLASS).where(GOODS_CLASS.ID.eq(classId)).execute();
    }


    public List<SelectClassVO2> findClassSelect() {
        return db
            .select(
                GOODS_CLASS.CLASS_NAME.as("label"),
                GOODS_CLASS.CLASS_PATH.as("value"),
                GOODS_CLASS.CLASS_LEVEL)
            .from(GOODS_CLASS)
            .fetchInto(SelectClassVO2.class);
    }


    public List<GoodsClassVO> findChildrenClass(String classPath) {
        return db
            .select(
                GOODS_CLASS.ID, GOODS_CLASS.ID.as("class_id"), GOODS_CLASS.CLASS_PATH, GOODS_CLASS.CLASS_NAME,
                GOODS_CLASS.CLASS_LEVEL, GOODS_CLASS.ICON_ID
            )
            .from(GOODS_CLASS)
            .where(GOODS_CLASS.CLASS_PATH.startsWith(classPath).and(GOODS_CLASS.CLASS_PATH.ne(classPath)))
            .fetchInto(GoodsClassVO.class);
    }
}
