package com.etrade.puggo.service.goods.message;

import com.etrade.puggo.common.exception.ServiceException;
import com.etrade.puggo.constants.GoodsMessageState;
import com.etrade.puggo.constants.GoodsState;
import com.etrade.puggo.dao.goods.GoodsDao;
import com.etrade.puggo.dao.goods.GoodsMessageLogDao;
import com.etrade.puggo.db.tables.records.GoodsMessageLogsRecord;
import com.etrade.puggo.service.BaseService;
import com.etrade.puggo.service.account.UserAccountService;
import com.etrade.puggo.service.goods.sales.GoodsSimpleService;
import com.etrade.puggo.service.goods.sales.pojo.AcceptPriceParam;
import com.etrade.puggo.service.goods.sales.pojo.GoodsDetailVO;
import com.etrade.puggo.service.goods.sales.pojo.GoodsSimpleVO;
import com.etrade.puggo.service.goods.sales.pojo.LaunchUserDO;
import com.etrade.puggo.service.setting.SettingService;
import com.etrade.puggo.utils.OptionalUtils;
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
 * @description : 商品会话日志
 * @date 2023/7/11 18:39
 **/
@Service
public class GoodsMessageService extends BaseService {

    @Resource
    private GoodsDao goodsDao;

    @Resource
    private GoodsMessageLogDao messageDao;

    @Resource
    private GoodsSimpleService goodsSimpleService;

    @Resource
    private UserAccountService userAccountService;

    @Resource
    private SettingService settingService;


    public void buyerSendGoodsCallback(BuyerSendGoodsCallbackParam param) {

        Long sellerId = param.getSellerId();
        Long goodsId = param.getGoodsId();
        long buyerId = userId();

        if (sellerId.equals(buyerId)) {
            return;
        }

        GoodsMessageLogsRecord record = messageDao.selectOne(buyerId, sellerId, goodsId);

        if (record == null) {
            messageDao.saveOne(sellerId, goodsId, GoodsMessageState.SEND_GOODS, null);
        } else {
            messageDao.updateModified(record.getId());
        }
    }

    public void buyerOfferPriceCallback(BuyerOfferPriceCallbackParam param) {

        Long sellerId = param.getSellerId();
        Long goodsId = param.getGoodsId();
        BigDecimal buyerPrice = param.getBuyerPrice();
        long buyerId = userId();

        if (sellerId.equals(buyerId)) {
            return;
        }

        GoodsMessageLogsRecord record = messageDao.selectOne(buyerId, sellerId, goodsId);

        if (record == null) {
            messageDao.saveOne(sellerId, goodsId, GoodsMessageState.OFFER_PRICE, buyerPrice);
        } else {
            if (GoodsMessageState.ACCEPT_PRICE.equals(record.getState())) {
                throw new ServiceException("卖家已经同意出价，不允许修改");
            }
            messageDao.updateBuyerPrice(param);
        }
    }


    public void buyerCancelPriceCallback(BuyerSendGoodsCallbackParam param) {

        Long sellerId = param.getSellerId();
        Long goodsId = param.getGoodsId();
        long buyerId = userId();

        GoodsMessageLogsRecord record = messageDao.selectOne(buyerId, sellerId, goodsId);

        if (record != null) {
            messageDao.updateState(buyerId, sellerId, goodsId, GoodsMessageState.CANCEL_PRICE);
        }
    }


    public void sellerRejectPriceCallback(AcceptPriceParam param) {

        Long buyerId = param.getCustomerId();
        Long goodsId = param.getGoodsId();
        Long sellerId = userId();

        GoodsMessageLogsRecord record = messageDao.selectOne(buyerId, sellerId, goodsId);

        if (record != null) {
            messageDao.updateState(buyerId, sellerId, goodsId, GoodsMessageState.REJECT_PRICE);
        }
    }


    public GoodsMessageLogVO getGoodsMessageLog(GetGoodsMessageParam param) {
        GoodsMessageLogsRecord record = messageDao
                .selectOne(param.getBuyerId(), param.getSellerId(), param.getGoodsId());

        if (record == null) {
            return null;
        }

        GoodsSimpleVO goodsInfo = goodsSimpleService.getSingleGoods(record.getGoodsId());

        List<LaunchUserDO> userList = userAccountService.getUserList(
                Arrays.asList(record.getBuyerId(), record.getSellerId()));

        Map<Long, LaunchUserDO> userMap = userList.stream()
                .collect(Collectors.toMap(LaunchUserDO::getUserId, Function.identity()));

        GoodsMessageLogVO vo = new GoodsMessageLogVO();

        vo.setGoodsInfo(goodsInfo);
        vo.setBuyerInfo(userMap.get(record.getBuyerId()));
        vo.setSellerInfo(userMap.get(record.getSellerId()));
        vo.setState(record.getState());
        vo.setIsGoodsComment(record.getIsGoodsConmment());
        vo.setIsBuyerComment(record.getIsBuyerConmment());
        vo.setIsSellerComment(record.getIsSellerConmment());
        vo.setBuyerPrice(record.getBuyerPrice());

        return vo;
    }


    public String getGoodsMessageState(GetGoodsMessageParam param) {
        GoodsMessageLogsRecord record = messageDao
                .selectOne(param.getBuyerId(), param.getSellerId(), param.getGoodsId());

        if (record == null) {
            return null;
        }

        return record.getState();
    }


    /**
     * 买家接受出价
     *
     * @author niuzhenyu
     * @lastEditor niuzhenyu
     * @createTime 2023/6/25 12:09
     * @editTime 2023/6/25 12:09
     **/
    @Transactional(rollbackFor = Throwable.class)
    public void acceptPriceCallback(AcceptPriceParam param) {
        Long goodsId = param.getGoodsId();
        Long customerId = param.getCustomerId();
        BigDecimal price = OptionalUtils.valueOrDefault(param.getPrice());
        long sellerId = userId();

        GoodsDetailVO goodsDetail = goodsDao.findGoodsDetail(goodsId);

        if (goodsDetail == null) {
            throw new ServiceException("商品不存在");
        }

        if (price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ServiceException("价格不合理");
        }

        // 修改商品会话状态
        messageDao.updateState(customerId, sellerId, goodsId, GoodsMessageState.ACCEPT_PRICE);

        // 修改商品状态
        String v = settingService.k("autoOccupy");
        if (Objects.equals(v, "1")) {
            goodsDao.updateGoodsSale(goodsId, GoodsState.OCCUPY);
        }
    }

}
