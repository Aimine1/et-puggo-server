package com.etrade.puggo.service.message;

import com.alibaba.fastjson.JSONObject;
import com.etrade.puggo.common.page.PageContentContainer;
import com.etrade.puggo.common.page.PageParam;
import com.etrade.puggo.dao.im.MsgNewsDao;
import com.etrade.puggo.dao.user.UserDao;
import com.etrade.puggo.dao.user.UserIMActionDao;
import com.etrade.puggo.db.tables.records.UserImActionRecord;
import com.etrade.puggo.service.BaseService;
import com.etrade.puggo.service.goods.sales.pojo.LaunchUserDO;
import com.etrade.puggo.third.im.YunxinIMApi;
import com.etrade.puggo.third.im.pojo.MessageDO;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author niuzhenyu
 * @description : 系统消息
 * @date 2023/6/28 11:41
 **/
@Service
public class MsgNewsService extends BaseService {

    @Resource
    private MsgNewsDao msgNewsDao;

    @Resource
    private UserDao userDao;

    @Resource
    private UserIMActionDao userIMActionDao;

    @Resource
    private YunxinIMApi yunxinIMApi;


    /**
     * 获取用户消息
     *
     * @author niuzhenyu
     * @lastEditor niuzhenyu
     * @createTime 2023/6/28 12:50
     * @editTime 2023/6/28 12:50
     **/
    public PageContentContainer<UserNewsVO> getUserNews(PageParam param) {

        PageContentContainer<UserNewsVO> pageList = msgNewsDao.findUserNews(param);

        if (pageList.getTotal() == 0) {
            return pageList;
        }

        List<UserNewsVO> list = pageList.getList();

        List<Long> fromUserIdList = list.stream().map(UserNewsVO::getFromUserId).collect(Collectors.toList());

        List<LaunchUserDO> fromUserList = userDao.findUserList(fromUserIdList);

        Map<Long, LaunchUserDO> fromUserMap = fromUserList.stream()
            .collect(Collectors.toMap(LaunchUserDO::getUserId, Function.identity()));

        for (UserNewsVO vo : list) {

            Long fromUserId = vo.getFromUserId();

            if (fromUserId == -1L) {
                vo.setFromUserInfo(buildSysUserDO());
            } else {
                if (fromUserMap.containsKey(fromUserId)) {
                    vo.setFromUserInfo(fromUserMap.get(fromUserId));
                }
            }
        }

        return pageList;
    }


    /**
     * 用户读取消息
     *
     * @author niuzhenyu
     * @lastEditor niuzhenyu
     * @createTime 2023/6/28 12:51
     * @editTime 2023/6/28 12:51
     **/
    public void readNews() {
        msgNewsDao.readNews();
    }


    /**
     * 用户未读数量
     *
     * @return
     */
    public int getUnreadCount() {
        Integer unreadCount = msgNewsDao.findUnreadCount();
        return unreadCount != null ? unreadCount : 0;
    }


    private LaunchUserDO buildSysUserDO() {
        LaunchUserDO user = new LaunchUserDO();
        user.setUserId(-1L);
        user.setNickname("系统消息");
        user.setAvatar("https://everythingtradeltd-awsbucket-test.s3.ap-southeast-1.amazonaws.com/avatar/Asset+17.png");
        return user;
    }


    public JSONObject sendMessage(String msg) {

        UserImActionRecord fromAction = userIMActionDao.findIMAction(2L);
        UserImActionRecord toAction = userIMActionDao.findIMAction(-1L);

        MessageDO message = new MessageDO();

        message.setFrom(fromAction.getAccid());
        message.setTo(toAction.getAccid());
        message.setOpe(0);
        message.setType(0);
        JSONObject body = new JSONObject();
        body.put("msg", msg);
        message.setBody(body.toJSONString());

        return yunxinIMApi.sendMessage(message);
    }
}
