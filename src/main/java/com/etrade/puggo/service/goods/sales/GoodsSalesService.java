package com.etrade.puggo.service.goods.sales;

import com.etrade.puggo.common.exception.ServiceException;
import com.etrade.puggo.common.page.PageContentContainer;
import com.etrade.puggo.common.page.PageParam;
import com.etrade.puggo.constants.GoodsImgType;
import com.etrade.puggo.constants.GoodsState;
import com.etrade.puggo.common.enums.LangErrorEnum;
import com.etrade.puggo.dao.goods.GoodsDao;
import com.etrade.puggo.dao.goods.GoodsDataDao;
import com.etrade.puggo.dao.goods.GoodsPictureDao;
import com.etrade.puggo.filter.AuthContext;
import com.etrade.puggo.service.BaseService;
import com.etrade.puggo.service.account.UserAccountService;
import com.etrade.puggo.service.fans.UserFansService;
import com.etrade.puggo.service.goods.user.UserGoodsService;
import com.etrade.puggo.service.groupon.dto.S3Picture;
import com.etrade.puggo.service.groupon.user.UserBrowseHistoryVO;
import com.etrade.puggo.service.log.GoodsLogsService;
import com.etrade.puggo.service.log.LogsOperate;
import com.etrade.puggo.stream.producer.StreamProducer;
import com.etrade.puggo.third.im.pojo.SendNewsParam;
import com.etrade.puggo.utils.StrUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.jooq.SortOrder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author niuzhenyu
 * @description : 商品销售处理
 * @date 2023/6/15 18:46
 **/
@Service
public class GoodsSalesService extends BaseService {

    @Resource
    private GoodsDao goodsDao;

    @Resource
    private GoodsPictureDao goodsPictureDao;

    @Resource
    private GoodsDataDao goodsDataDao;

    @Resource
    private UserGoodsService userGoodsService;

    @Resource
    private GoodsLogsService goodsLogsService;

    @Resource
    private UserFansService userFansService;

    @Resource
    private UserAccountService userAccountService;

    @Resource
    private StreamProducer streamProducer;


    /**
     * 获取商品列表
     *
     * @author niuzhenyu
     * @lastEditor niuzhenyu
     * @createTime 2023/6/15 21:33
     * @editTime 2023/6/15 21:33
     **/
    public PageContentContainer<GoodsSimpleVO> getSampleGoodsList(GoodsSearchParam param) {
        ExpendGoodsSearchParam expendParam = new ExpendGoodsSearchParam(param);
        expendParam.setTitle(param.getTitle());
        expendParam.setClassPath(param.getClassPath());
        expendParam.setState(List.of(GoodsState.PUBLISHED, GoodsState.OCCUPY));
        expendParam.setSortKey(param.getSortKey());

        if (!StrUtils.isBlank(param.getSearchLabel())) {
            String searchLabel = param.getSearchLabel();
            if (searchLabel.equals("热门") || searchLabel.equals("Trending")) {
                // no thing
            } else if (searchLabel.equals("最近") || searchLabel.equals("Recent")) {
                expendParam.setGoodsIdList(Collections.singletonList(-1L));
                if (!AuthContext.isTourist()) {
                    List<UserBrowseHistoryVO> browseHistory = userGoodsService.getBrowseHistory(param);
                    if (!browseHistory.isEmpty()) {
                        List<Long> goodsIdList = browseHistory.stream().map(UserBrowseHistoryVO::getGoodsId).sorted()
                            .collect(Collectors.toList());
                        expendParam.setGoodsIdList(goodsIdList);
                    }
                }
            } else if (searchLabel.equals("关注") || searchLabel.equals("Following")) {
                expendParam.setLaunchUserId(Collections.singletonList(-1L));
                if (!AuthContext.isTourist()) {
                    List<LaunchUserDO> launchUserDOS = userFansService.listFollow(userId());
                    List<Long> userList = launchUserDOS.stream().map(LaunchUserDO::getUserId)
                        .collect(Collectors.toList());
                    if (!userList.isEmpty()) {
                        expendParam.setLaunchUserId(userList);
                    }
                }
            } else if (searchLabel.equals("附近") || searchLabel.equals("Nearby")) {
                // TODO.. 需要获取app当前定位信息
            }
        }

        if (!StrUtils.isBlank(param.getSearchKey())) {
            String[] searchKeys = param.getSearchKey().split(",");
            for (String searchKey : searchKeys) {
                if (searchKey.equals("已鉴定") || searchKey.equals("Authenticated")) {
                    expendParam.setIsAiIdentify(true);
                }
                if (searchKey.equals("非预留") || searchKey.equals("Not reserved")) {
                    expendParam.setExcludeState(GoodsState.OCCUPY);
                }
            }
        }

        if (!StrUtils.isBlank(param.getSortKey())) {
            String sortKey = param.getSortKey();
            if (sortKey.equals("价格") || sortKey.contains("price")) {
                expendParam.setSortKey("real_price");
                expendParam.setSortOrder(SortOrder.ASC);
            } else if (sortKey.equals("发布日期") || sortKey.contains("data listed")) {
                expendParam.setSortKey("launch_last_time");
                expendParam.setSortOrder(SortOrder.DESC);
            }
        }

        if (StrUtils.isBlank(param.getSortKey())) {
            expendParam.setSortKey("id");
            expendParam.setSortOrder(SortOrder.DESC);
        }

        return getGoodsList(expendParam, false);
    }


    public PageContentContainer<GoodsSimpleVO> getGoodsList(ExpendGoodsSearchParam param, boolean isSelf) {

        PageContentContainer<GoodsSimpleVO> pageList = goodsDao.findGoodsPageList(param);

        if (pageList.getTotal() == 0) {
            return pageList;
        }

        List<GoodsSimpleVO> list = pageList.getList();

        // 获取主图
        List<Long> goodsIdList = list.stream().map(GoodsSimpleVO::getGoodsId).collect(Collectors.toList());

        List<GoodsMainPicUrlDTO> mainPicList = goodsPictureDao.findGoodsMainPicList(goodsIdList);

        Map<Long, String> mainPicMap = mainPicList.stream()
            .collect(Collectors.toMap(GoodsMainPicUrlDTO::getGoodsId, GoodsMainPicUrlDTO::getUrl, (o1, o2) -> o1));

        // 是否收藏
        List<Long> likeGoods = new ArrayList<>();
        if (!AuthContext.isTourist()) {
            likeGoods = userGoodsService.isLike(goodsIdList);
        }

        for (GoodsSimpleVO vo : list) {
            if (mainPicMap.containsKey(vo.getGoodsId())) {
                vo.setMainImgUrl(mainPicMap.get(vo.getGoodsId()));
            }

            vo.setIsLike(likeGoods.contains(vo.getGoodsId()));
        }

        // 获取发布人信息
        if (!isSelf) {
            List<Long> userIdList = list.stream().map(GoodsSimpleVO::getLaunchUserId).collect(Collectors.toList());

            List<LaunchUserDO> userList = userAccountService.getUserList(userIdList);

            Map<Long, LaunchUserDO> userMap = userList.stream()
                .collect(Collectors.toMap(LaunchUserDO::getUserId, Function.identity()));

            for (GoodsSimpleVO vo : list) {
                if (userMap.containsKey(vo.getLaunchUserId())) {
                    vo.setLaunchUser(userMap.get(vo.getLaunchUserId()));
                }
            }
        }

        return pageList;
    }


    /**
     * 获取商品详情
     *
     * @author niuzhenyu
     * @lastEditor niuzhenyu
     * @createTime 2023/6/15 21:34
     * @editTime 2023/6/15 21:34
     **/
    public GoodsDetailVO getGoodsDetail(Long goodsId) {

        GoodsDetailVO detail = goodsDao.findGoodsDetail(goodsId);

        if (detail == null) {
            throw new ServiceException("商品不存在");
        }

        List<S3Picture> goodsPictures = goodsPictureDao.findTargetPictures(goodsId, GoodsImgType.GOODS);

        detail.setPictureList(goodsPictures);

        List<LaunchUserDO> userList = userAccountService.getUserList(
            Collections.singletonList(detail.getLaunchUserId()));

        detail.setLaunchUser(userList.get(0));

        if (!AuthContext.isTourist()) {
            // 浏览记录+1
            userGoodsService.browseGoods(goodsId);

            // 判断用户是否收藏该商品
            List<Long> likeGoods = userGoodsService.isLike(Collections.singletonList(detail.getGoodsId()));
            detail.setIsLike(likeGoods.contains(detail.getGoodsId()));
        } else {
            detail.setIsLike(false);
        }

        return detail;
    }


    /**
     * 用户收藏商品
     *
     * @author niuzhenyu
     * @lastEditor niuzhenyu
     * @createTime 2023/6/7 16:41
     * @editTime 2023/6/7 16:41
     **/
    @Transactional(rollbackFor = Throwable.class)
    public void likeGoods(Long goodsId) {
        boolean b = userGoodsService.likeGoods(goodsId) != null;

        if (!b) {
            return;
        }

        // like num 增加
        goodsDataDao.incLikeNumber(goodsId);

        // 给商品主人发送消息
        GoodsSimpleVO goodsInfo = goodsDao.findGoodsSimple(goodsId);

        if (goodsInfo == null) {
            return;
        }

        String goodsMainPic = goodsPictureDao.findGoodsMainPic(goodsId);

        SendNewsParam newsParam = SendNewsParam.builder()
            .goodsId(goodsId)
            .goodsMainPic(goodsMainPic)
            .attach(String.format(LangErrorEnum.MSG_LIKE.lang(), userName()))
            .pushcontent(LangErrorEnum.MSG_NEW.lang())
            .toUserId(goodsInfo.getLaunchUserId())
            .fromUserId(userId())
            .build();

        streamProducer.sendNews(newsParam);

        // 商品操作日志
        String content = String.format("钟意 %s 的商品", goodsInfo.getLaunchUserNickname());
        goodsLogsService.logs(goodsId, LogsOperate.LIKE, content);

    }


    /**
     * 用户取消收藏商品
     *
     * @author niuzhenyu
     * @lastEditor niuzhenyu
     * @createTime 2023/6/7 22:29
     * @editTime 2023/6/7 22:29
     **/
    @Transactional(rollbackFor = Throwable.class)
    public void unlikeGoods(Long goodsId) {
        Integer rows = userGoodsService.unlikeGoods(goodsId);
        boolean b = rows != null && rows != 0;

        if (!b) {
            return;
        }

        goodsDataDao.decLikeNumber(goodsId);

        List<GoodsSimpleVO> goodsList = goodsDao.findGoodsSimpleList(Collections.singletonList(goodsId));
        GoodsSimpleVO goodsInfo = goodsList.get(0);

        // 商品操作日志
        String content = String.format("取消收藏 %s 的商品", goodsInfo.getLaunchUserNickname());
        goodsLogsService.logs(goodsId, LogsOperate.UNLIKE, content);

    }


    /**
     * 用户自己发布的商品
     *
     * @author niuzhenyu
     * @lastEditor niuzhenyu
     * @createTime 2023/6/16 11:10
     * @editTime 2023/6/16 11:10
     **/
    public PageContentContainer<GoodsSimpleVO> getUserGoodsList(UserGoodsListParam param) {
        ExpendGoodsSearchParam expends = new ExpendGoodsSearchParam(param);

        expends.setLaunchUserId(Collections.singletonList(userId()));
        expends.setTitle(param.getTitle());
        return getGoodsList(expends, true);
    }


    /**
     * 草稿箱
     *
     * @author niuzhenyu
     * @lastEditor niuzhenyu
     * @createTime 2023/10/7 10:43
     * @editTime 2023/10/7 10:43
     **/
    public PageContentContainer<GoodsSimpleVO> getDraftGoodsList(PageParam pageParam) {
        ExpendGoodsSearchParam param = new ExpendGoodsSearchParam(pageParam);

        param.setState(Collections.singletonList(GoodsState.DRAFT));
        param.setLaunchUserId(Collections.singletonList(userId()));
        return getGoodsList(param, true);
    }


    /**
     * 用户收藏的商品
     *
     * @author niuzhenyu
     * @lastEditor niuzhenyu
     * @createTime 2023/6/16 11:10
     * @editTime 2023/6/16 11:10
     **/
    public PageContentContainer<GoodsSimpleVO> getUserLikeGoodsList(PageParam pageParam) {
        ExpendGoodsSearchParam param = new ExpendGoodsSearchParam(pageParam);

        param.setGoodsIdList(Collections.singletonList(-1L));

        List<Long> goodsIdList = userGoodsService.getUserLikeGoodsList();

        if (!goodsIdList.isEmpty()) {
            param.setGoodsIdList(goodsIdList);
        }

        param.setState(Arrays.asList(GoodsState.PUBLISHED, GoodsState.OCCUPY));

        return getGoodsList(param, false);
    }


    /**
     * 商品推荐列表
     *
     * @author niuzhenyu
     * @lastEditor niuzhenyu
     * @createTime 2023/6/25 15:22
     * @editTime 2023/6/25 15:22
     **/
    public PageContentContainer<GoodsSimpleVO> getRecommendGoodsList(RecommendGoodsParam originalParam) {
        ExpendGoodsSearchParam param = new ExpendGoodsSearchParam(originalParam);

        long goodsId = originalParam.getGoodsId();

        GoodsDetailVO goodsDetail = goodsDao.findGoodsDetail(goodsId);

        if (goodsDetail == null) {
            throw new ServiceException("依据的商品不存在");
        }

        // 根据查看的商品分类来推荐新的商品
        param.setClassPath(goodsDetail.getClassPath());
        param.setExcludeGoodsId(goodsId);
        param.setState(Arrays.asList(GoodsState.PUBLISHED, GoodsState.OCCUPY));

        return getGoodsList(param, false);
    }


    /**
     * web端商品列表
     *
     * @author niuzhenyu
     * @lastEditor niuzhenyu
     * @createTime 2023/6/29 20:17
     * @editTime 2023/6/29 20:17
     **/
    public PageContentContainer<GoodsDetailVO> getWebGoodsList(GoodsSearchParam param) {

        PageContentContainer<GoodsDetailVO> pageList = goodsDao.findWebGoodsPageList(param);

        if (pageList.getTotal() == 0) {
            return pageList;
        }

        List<GoodsDetailVO> list = pageList.getList();

        // 获取主图
        List<Long> goodsIdList = list.stream().map(GoodsDetailVO::getGoodsId).collect(Collectors.toList());

        List<GoodsMainPicUrlDTO> mainPicList = goodsPictureDao.findGoodsMainPicList(goodsIdList);

        Map<Long, String> mainPicMap = mainPicList.stream()
            .collect(Collectors.toMap(GoodsMainPicUrlDTO::getGoodsId, GoodsMainPicUrlDTO::getUrl, (o1, o2) -> o1));

        for (GoodsSimpleVO vo : list) {
            if (mainPicMap.containsKey(vo.getGoodsId())) {
                vo.setMainImgUrl(mainPicMap.get(vo.getGoodsId()));
            }
        }

        return pageList;
    }

}
