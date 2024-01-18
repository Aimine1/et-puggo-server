package com.etrade.puggo.dao.goods;

import static com.etrade.puggo.db.Tables.GOODS_QUALITY;

import com.etrade.puggo.dao.BaseDao;
import com.etrade.puggo.common.filter.AuthContext;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * @author niuzhenyu
 * @description : 商品成色
 * @date 2023/5/24 18:19
 **/
@Repository
public class GoodsQualityDao extends BaseDao {

    public List<String> findQualityList() {
        return db
            .select(GOODS_QUALITY.QUALITY)
            .from(GOODS_QUALITY)
            .where(GOODS_QUALITY.LANG.eq(AuthContext.getLang()))
            .fetchInto(String.class);
    }

}
