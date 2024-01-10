package com.etrade.puggo.service.groupon.clazz;

import com.etrade.puggo.common.exception.ServiceException;
import com.etrade.puggo.dao.groupon.GrouponClassDao;
import com.etrade.puggo.dao.groupon.GrouponCouponPicDao;
import com.etrade.puggo.service.BaseService;
import com.etrade.puggo.service.groupon.GrouponClassDTO;
import com.etrade.puggo.service.groupon.dto.S3Picture;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author niuzhenyu
 * @description : 团购券分类
 * @date 2023/6/9 13:25
 **/
@Service
public class GrouponClassService extends BaseService {

    @Resource
    private GrouponClassDao grouponClassDao;
    @Resource
    private GrouponCouponPicDao grouponCouponPicDao;


    /**
     * 团购券分类列表
     *
     * @author niuzhenyu
     * @lastEditor niuzhenyu
     * @createTime 2023/6/6 18:07
     * @editTime 2023/6/6 18:07
     **/
    public List<GrouponClassDTO> listGrouponClass() {

        List<GrouponClassDTO> classList = grouponClassDao.findGrouponClassList();

        List<Long> iconIdList = classList.stream()
            .map(GrouponClassDTO::getIconId).filter(iconId -> iconId != 0L).collect(Collectors.toList());

        List<S3Picture> S3IconList = grouponCouponPicDao.findS3IconList(iconIdList);

        Map<Long, S3Picture> picMap = S3IconList.stream()
            .collect(Collectors.toMap(S3Picture::getId, Function.identity()));

        for (GrouponClassDTO c : classList) {
            if (picMap.containsKey(c.getIconId())) {
                c.setIcon(picMap.get(c.getIconId()));
            }
        }

        List<GrouponClassDTO> topCLass = classList.stream()
            .filter(c -> c.getClassLevel().equals(GrouponClassDao.TOP_LEVEL)).collect(Collectors.toList());

        for (GrouponClassDTO c : topCLass) {
            String path = c.getClassPath();
            byte level = c.getClassLevel();
            List<GrouponClassDTO> children =
                classList.stream().filter(o -> !o.getClassLevel().equals(level) && o.getClassPath().startsWith(path))
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

        int parentClass = param.getParentClass();
        byte classLevel = param.getClassLevel();
        S3Picture icon = param.getIcon();

        GrouponClassDTO parent = null;
        if (classLevel == 2) {
            parent = grouponClassDao.findClassById(parentClass);

            if (parent == null) {
                throw new ServiceException("父级分类不存在");
            }
        }

        if (icon != null) {
            param.setIconId(grouponCouponPicDao.saveIcon(icon));
        }

        int classId = grouponClassDao.addClass(param);
        String classPath =
            parent == null ? String.format("%s,", classId) : String.format("%s%s,", parent.getClassPath(), classId);

        grouponClassDao.updateClassPath(classId, classPath);

    }

    public int editClass(EditClassParam param) {

        isAdminRole();

        int classId = param.getId();
        int parentClass = param.getParentClass();
        byte classLevel = param.getClassLevel();
        S3Picture icon = param.getIcon();

        GrouponClassDTO classDTO = grouponClassDao.findClassById(classId);

        if (classDTO == null) {
            throw new ServiceException("分类不存在");
        }

        GrouponClassDTO parent = null;
        if (classLevel == 2) {
            parent = grouponClassDao.findClassById(parentClass);

            if (parent == null) {
                throw new ServiceException("父级分类不存在");
            }
        }

        if (icon != null) {
            param.setIconId(grouponCouponPicDao.saveIcon(icon));
        }

        String classPath =
            parent == null ? String.format("%s,", classId) : String.format("%s%s,", parent.getClassPath(), classId);

        param.setClassPath(classPath);

        return grouponClassDao.editClass(param);

    }

    public int deleteClass(Integer classId) {

        isAdminRole();

        return grouponClassDao.deleteClass(classId);
    }


    public List<SelectClassVO> selectClass() {

        List<SelectClassVO> classSelect = grouponClassDao.findClassSelect();

        List<SelectClassVO> classResponse = classSelect.stream()
            .filter(c -> c.getClassLevel().equals(GrouponClassDao.TOP_LEVEL)).collect(Collectors.toList());

        for (SelectClassVO c : classResponse) {
            String path = c.getClassPath();
            byte level = c.getClassLevel();
            List<SelectClassVO> children = classSelect.stream()
                .filter(o -> !o.getClassLevel().equals(level) && o.getClassPath().startsWith(path))
                .collect(Collectors.toList());
            if (children.isEmpty()) {
                continue;
            }
            c.setChildren(children);
        }

        return classResponse;
    }

}
