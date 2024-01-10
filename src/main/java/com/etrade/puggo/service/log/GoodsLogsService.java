package com.etrade.puggo.service.log;

import com.etrade.puggo.common.page.PageContentContainer;
import com.etrade.puggo.common.page.PageParam;
import com.etrade.puggo.dao.goods.GoodsLogsDao;
import com.etrade.puggo.service.BaseService;
import com.etrade.puggo.service.goods.sales.GoodsSimpleService;
import com.etrade.puggo.service.goods.sales.GoodsSimpleVO;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author niuzhenyu
 * @description : 商品操作日志
 * @date 2023/6/28 17:37
 **/
@Slf4j
@Service
public class GoodsLogsService extends BaseService {

    @Resource
    private GoodsLogsDao goodsLogsDao;
    @Resource
    private GoodsSimpleService goodsSimpleService;


    /**
     * 保存日志
     *
     * @author niuzhenyu
     * @lastEditor niuzhenyu
     * @createTime 2023/6/28 17:49
     * @editTime 2023/6/28 17:49
     **/
    public void logs(Long goodsId, String operate, String content) {
        LogsBuilder logs = LogsBuilder.builder()
            .goodsId(goodsId).userId(userId()).operate(operate).content(content).build();

        goodsLogsDao.save(logs);
    }


    /**
     * 用户日志
     *
     * @author niuzhenyu
     * @lastEditor niuzhenyu
     * @createTime 2023/6/28 18:59
     * @editTime 2023/6/28 18:59
     **/
    public PageContentContainer<UserLogsVO> getUserGoodsLogs(PageParam param) {

        PageContentContainer<UserLogsVO> pageList = goodsLogsDao.findUserGoodsLogs(param);

        if (pageList.getTotal() == 0) {
            return pageList;
        }

        List<UserLogsVO> list = pageList.getList();

        List<Long> goodsIdList = list.stream().map(UserLogsVO::getGoodsId).collect(Collectors.toList());

        Map<Long, GoodsSimpleVO> goodsMap = goodsSimpleService.getGoodsMap(goodsIdList);

        for (UserLogsVO vo : list) {
            if (goodsMap.containsKey(vo.getGoodsId())) {
                vo.setGoodsInfo(goodsMap.get(vo.getGoodsId()));
            }
        }

        return pageList;
    }


    /**
     * 查询用户商品日志未读数量
     *
     * @return 未读数量
     */
    public Integer getUnreadCount() {
        return goodsLogsDao.findUnreadCount(userId());
    }


    /**
     * 修改日志状态为已读
     */
    public void updateToRead() {
        goodsLogsDao.updateToRead(userId());
    }

}
