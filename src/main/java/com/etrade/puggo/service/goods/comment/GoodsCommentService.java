package com.etrade.puggo.service.goods.comment;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.etrade.puggo.common.enums.LangErrorEnum;
import com.etrade.puggo.common.exception.ServiceException;
import com.etrade.puggo.common.page.PageContentContainer;
import com.etrade.puggo.constants.CommentConstants;
import com.etrade.puggo.constants.GoodsImgType;
import com.etrade.puggo.dao.comment.GoodsCommentDao;
import com.etrade.puggo.dao.comment.StatisticsUserCommentScoreDao;
import com.etrade.puggo.dao.goods.GoodsDao;
import com.etrade.puggo.dao.goods.GoodsMessageLogDao;
import com.etrade.puggo.dao.goods.GoodsPictureDao;
import com.etrade.puggo.db.tables.records.GoodsCommentRecord;
import com.etrade.puggo.db.tables.records.StatisticsUserCommentScoreRecord;
import com.etrade.puggo.service.BaseService;
import com.etrade.puggo.service.account.UserAccountService;
import com.etrade.puggo.service.goods.comment.CommentVO.ReplyCommentVO;
import com.etrade.puggo.service.goods.sales.GoodsSimpleService;
import com.etrade.puggo.service.goods.sales.pojo.GoodsDetailVO;
import com.etrade.puggo.service.goods.sales.pojo.GoodsSimpleVO;
import com.etrade.puggo.service.goods.sales.pojo.LaunchUserDO;
import com.etrade.puggo.service.groupon.dto.S3Picture;
import com.etrade.puggo.third.aws.S3PutObjectResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author niuzhenyu
 * @description : 商品评论
 * @date 2023/6/25 18:12
 **/
@Service
public class GoodsCommentService extends BaseService {

    @Resource
    private GoodsCommentDao goodsCommentDao;
    @Resource
    private GoodsDao goodsDao;
    @Resource
    private GoodsPictureDao goodsPictureDao;
    @Resource
    private GoodsMessageLogDao goodsMessageLogDao;
    @Resource
    private StatisticsUserCommentScoreDao statisticsUserCommentScoreDao;
    @Resource
    private GoodsSimpleService goodsSimpleService;
    @Resource
    private UserAccountService userAccountService;


    /**
     * 评论
     *
     * @param param 前端入参
     */
    @Transactional(rollbackFor = Throwable.class)
    public void comment(CommentGoodsParam param) {

        BigDecimal score = param.getScore();
        Byte type = param.getType();
        Long goodsId = param.getGoodsId();
        Long toUserId = param.getToUserId();
        Long fromUserId = userId();

        List<Byte> typeList = Arrays.asList(CommentConstants.TYPE_BUYER, CommentConstants.TYPE_SELLER);

        if (!typeList.contains(type)) {
            throw new ServiceException(LangErrorEnum.INVALID_COMMENT_TYPE.lang());
        }

        BigDecimal factor = new BigDecimal("0.5");
        BigDecimal[] results = score.divideAndRemainder(factor);

        if (results[1].compareTo(BigDecimal.ZERO) != 0) {
            throw new ServiceException("评分只能是0.5的倍数");
        }

        GoodsDetailVO goods = goodsDao.findGoodsDetail(goodsId);
        if (goods == null) {
            throw new ServiceException(LangErrorEnum.INVALID_GOODS.lang());
        }

        byte identity;
        if (Objects.equals(CommentConstants.TYPE_BUYER, type)) {
            // 评论买家
            identity = CommentConstants.IDENTITY_SELLER;
        } else {
            // 评论卖家和私讯
            identity = CommentConstants.IDENTITY_BUYER;
        }

        GoodsCommentRecord record = new GoodsCommentRecord();
        record.setGoodsId(param.getGoodsId());
        record.setScore(param.getScore());
        record.setComment(param.getComment());
        record.setType(param.getType());
        record.setFromUserId(userId());
        record.setToUserId(toUserId);
        record.setIdentity(identity);
        record.setIsReply((byte) 0);

        Long commentId = goodsCommentDao.save(record);

        // 上传图片
        List<S3PutObjectResult> pictureList = param.getPictureList();

        if (!CollectionUtils.isEmpty(pictureList)) {
            goodsPictureDao.savePic(commentId, GoodsImgType.COMMENT, pictureList);
        }

        // 修改商品会话
        // updateGoodsMessageState(type, toUserId, fromUserId, goodsId);

        // 计算并重置用户信用分
        calculateAndResetUserCreditRating(toUserId, score, identity);
    }


    /**
     * 计算并重置用户信用分
     *
     * @param toUserId 评论对象
     * @param score    评分
     * @param identity 评论者身份
     */
    private void calculateAndResetUserCreditRating(Long toUserId, BigDecimal score, byte identity) {
        // 重新计算平均评分
        statisticsUserCommentScoreDao.saveOrUpdate(toUserId, score, identity);
        // 查询平均分
        StatisticsUserCommentScoreRecord statRecord = statisticsUserCommentScoreDao.getOneRecord(toUserId);
        BigDecimal averageScore = statRecord.getAverageScore();
        // 给用户设置平均分
        userAccountService.resetUserCreditRating(toUserId, averageScore);
    }


    /**
     * 修改商品会话评论状态，目前已经废弃
     *
     * @param type       评论类型
     * @param toUserId   评论对象
     * @param fromUserId 评论者
     * @param goodsId    商品id
     */
    private void updateGoodsMessageState(Byte type, Long toUserId, Long fromUserId, Long goodsId) {
        if (Objects.equals(CommentConstants.TYPE_BUYER, type)) {
            // 评论买家
            goodsMessageLogDao.updateIsBuyerComment(toUserId, fromUserId, goodsId);
        } else if (Objects.equals(CommentConstants.TYPE_SELLER, type)) {
            // 评论卖家
            goodsMessageLogDao.updateIsSellerComment(fromUserId, toUserId, goodsId);
        } else if (Objects.equals(CommentConstants.TYPE_MESSAGE, type)) {
            // 评论商品
            goodsMessageLogDao.updateIsGoodsComment(fromUserId, toUserId, goodsId);
        }
    }


    /**
     * 回复评论
     *
     * @author niuzhenyu
     * @lastEditor niuzhenyu
     * @createTime 2023/6/25 21:43
     * @editTime 2023/6/25 21:43
     **/
    public void replyComment(ReplyCommentParam param) {

        Long lastCommentId = param.getLastCommentId();
        String comment = param.getComment();
        Long userId = userId();

        GoodsCommentRecord lastComment = goodsCommentDao.findLastComment(lastCommentId);

        if (lastComment == null) {
            throw new ServiceException("last comment is unknown");
        }

        Byte identity;
        if (userId.equals(lastComment.getFromUserId())) {
            // 本人回复
            identity = lastComment.getIdentity();
        } else {
            // 别人回复
            identity = lastComment.getIdentity().equals(CommentConstants.IDENTITY_BUYER) ? CommentConstants.TYPE_SELLER
                    : CommentConstants.IDENTITY_BUYER;
        }

        GoodsCommentRecord record = new GoodsCommentRecord();
        record.setGoodsId(lastComment.getGoodsId());
        record.setScore(BigDecimal.ZERO);
        record.setComment(comment);
        record.setType(lastComment.getType());
        record.setFromUserId(userId);
        record.setToUserId(lastComment.getFromUserId());
        record.setIdentity(identity);
        record.setIsReply((byte) 1);
        record.setLastCommentId(lastCommentId);

        goodsCommentDao.save(record);

    }


    /**
     * 我的-评论列表
     *
     * @author niuzhenyu
     * @lastEditor niuzhenyu
     * @createTime 2023/6/26 10:04
     * @editTime 2023/6/26 10:04
     **/
    public PageContentContainer<CommentVO> getUserCommentList(UserCommentListParam param) {

        PageContentContainer<CommentVO> pageList = goodsCommentDao.findUserCommentList(param);

        if (pageList.getTotal() == 0) {
            return pageList;
        }

        List<CommentVO> list = pageList.getList();

        // 商品信息
        List<Long> goodsIdList = list.stream().map(CommentVO::getGoodsId).collect(Collectors.toList());

        Map<Long, GoodsSimpleVO> goodsMap = goodsSimpleService.getGoodsMap(goodsIdList);

        // 评论者信息
        List<Long> userIdList = list.stream().map(CommentVO::getFromUserId).collect(Collectors.toList());

        List<LaunchUserDO> userList = userAccountService.getUserList(userIdList);

        Map<Long, LaunchUserDO> userMap = userList.stream()
                .collect(Collectors.toMap(LaunchUserDO::getUserId, Function.identity()));

        // 回复评论
        List<Long> commentIdList = list.stream().map(CommentVO::getCommentId).collect(Collectors.toList());

        List<ReplyCommentVO> replyCommentList = goodsCommentDao.findReplyCommentList(commentIdList);

        Map<Long, ReplyCommentVO> replyMap = replyCommentList.stream()
                .collect(Collectors.toMap(ReplyCommentVO::getLastCommentId, Function.identity()));

        // 图片内容
        List<S3Picture> pictures = goodsPictureDao.findTargetPictures(commentIdList, GoodsImgType.COMMENT);

        Map<Long, List<S3Picture>> pictureMap = pictures.stream()
                .collect(Collectors.groupingBy(S3Picture::getTargetId));

        for (CommentVO vo : list) {
            Long fromUserId = vo.getFromUserId();
            Long goodsId = vo.getGoodsId();
            Long commentId = vo.getCommentId();
            if (userMap.containsKey(fromUserId)) {
                vo.setCommenter(userMap.get(fromUserId));
            }
            if (goodsMap.containsKey(goodsId)) {
                vo.setGoods(goodsMap.get(goodsId));
            }
            if (replyMap.containsKey(commentId)) {
                vo.setReplyComment(replyMap.get(commentId));
            }
            if (pictureMap.containsKey(commentId)) {
                vo.setPictureList(pictureMap.get(commentId));
            }
        }

        return pageList;
    }


}
