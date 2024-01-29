package com.etrade.puggo.service.goods.clazz;

import com.etrade.puggo.common.exception.ServiceException;
import com.etrade.puggo.common.constants.GoodsImgType;
import com.etrade.puggo.dao.goods.GoodsClassDao;
import com.etrade.puggo.dao.goods.GoodsPictureDao;
import com.etrade.puggo.service.BaseService;
import com.etrade.puggo.service.goods.publish.GoodsClassVO;
import com.etrade.puggo.service.groupon.clazz.AddClassParam;
import com.etrade.puggo.service.groupon.clazz.EditClassParam;
import com.etrade.puggo.service.groupon.dto.S3Picture;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author niuzhenyu
 * @description : 商品分类
 * @date 2023/6/29 14:40
 **/
@Service
public class GoodsClassService extends BaseService {


    @Resource
    private GoodsClassDao classDao;
    @Resource
    private GoodsPictureDao picturesDao;


    private static final Byte TOP_LEVEL = 1;


    /**
     * 获取商品分类级别
     *
     * @author niuzhenyu
     * @lastEditor niuzhenyu
     * @createTime 2023/5/25 10:34
     * @editTime 2023/5/25 10:34
     **/
    public List<GoodsClassVO> getClassList() {
        // 默认一级分类
        List<GoodsClassVO> classList = classDao.findClassList();

        // icon
        List<Long> iconIdList = classList.stream()
            .map(GoodsClassVO::getIconId).filter(iconId -> iconId != 0L).collect(Collectors.toList());

        List<S3Picture> S3IconList = picturesDao.findS3IconList(iconIdList);

        Map<Long, S3Picture> picMap = S3IconList.stream()
            .collect(Collectors.toMap(S3Picture::getId, Function.identity()));

        for (GoodsClassVO c : classList) {
            if (picMap.containsKey(c.getIconId())) {
                c.setIcon(picMap.get(c.getIconId()));
            }
        }

        // children
        List<GoodsClassVO> topCLass = classList.stream()
            .filter(c -> c.getClassLevel().equals(TOP_LEVEL)).collect(Collectors.toList());

        for (GoodsClassVO c : topCLass) {
            String path = c.getClassPath();
            List<GoodsClassVO> children = classList.stream()
                .filter(o -> o.getClassLevel().compareTo(TOP_LEVEL) > 0 && o.getClassPath().startsWith(path))
                .collect(Collectors.toList());
            if (children.isEmpty()) {
                continue;
            }
            c.setChildren(children);
        }

        return topCLass;
    }


    public void addClass(AddClassParam param) {

        isAdminRole();

        S3Picture icon = param.getIcon();
        Byte classLevel = param.getClassLevel();
        Integer parentClass = param.getParentClass();

        GoodsClassVO parent = null;
        if (!Objects.equals(classLevel, TOP_LEVEL)) {
            parent = classDao.findClass(parentClass);

            if (parent == null) {
                throw new ServiceException("父级分类不存在");
            }
        }

        if (icon != null) {
            // icon
            param.setIconId(picturesDao.saveSingle(icon, GoodsImgType.ICON));
        }

        int classId = classDao.addClass(param);

        String classPath =
            parent == null ? String.format("%s,", classId) : String.format("%s%s,", parent.getClassPath(), classId);
        classDao.updateClassPath(classId, classPath);
    }


    public int editClass(EditClassParam param) {

        isAdminRole();

        int classId = param.getId();
        S3Picture icon = param.getIcon();
        Integer parentClass = param.getParentClass();
        Byte classLevel = param.getClassLevel();

        GoodsClassVO classVO = classDao.findClass(classId);

        if (classVO == null) {
            throw new ServiceException("您要修改的分类不存在");
        }

        GoodsClassVO parent = null;
        if (!Objects.equals(classLevel, TOP_LEVEL)) {
            parent = classDao.findClass(parentClass);

            if (parent == null) {
                throw new ServiceException("父级分类不存在");
            }
        }

        if (icon != null) {
            param.setIconId(picturesDao.saveSingle(icon, GoodsImgType.ICON));
        }

        String classPath =
            parent == null ? String.format("%s,", classId) : String.format("%s%s,", parent.getClassPath(), classId);

        param.setClassPath(classPath);

        return classDao.editClass(param);

    }


    public int deleteClass(Integer classId) {

        isAdminRole();

        GoodsClassVO clazz = classDao.findClass(classId);

        List<GoodsClassVO> childrenClass = classDao.findChildrenClass(clazz.getClassPath());

        if (!childrenClass.isEmpty()) {
            throw new ServiceException("该分类下存在子分类，不能删除");
        }

        return classDao.deleteClass(classId);
    }


    public List<SelectClassVO2> selectClass() {

        List<SelectClassVO2> classSelect = classDao.findClassSelect();

        List<SelectClassVO2> classResponse = classSelect.stream()
            .filter(c -> c.getClassLevel().equals(TOP_LEVEL)).collect(Collectors.toList());

        for (SelectClassVO2 c : classResponse) {
            String topPath = c.getValue();
            byte topLevel = c.getClassLevel();
            List<SelectClassVO2> children = classSelect.stream()
                .filter(o -> o.getClassLevel().compareTo(topLevel) < 0 && o.getValue().startsWith(topPath))
                .collect(Collectors.toList());
            if (children.isEmpty()) {
                continue;
            }
            c.setChildren(children);
        }

        return classResponse;
    }

}
