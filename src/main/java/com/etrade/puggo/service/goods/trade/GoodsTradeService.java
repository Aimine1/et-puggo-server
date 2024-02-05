package com.etrade.puggo.service.goods.trade;

import com.etrade.puggo.common.constants.GoodsState;
import com.etrade.puggo.common.constants.GoodsTradeState;
import com.etrade.puggo.common.enums.LangErrorEnum;
import com.etrade.puggo.common.enums.PaymentTargetEnum;
import com.etrade.puggo.common.enums.PaymentTypeEnum;
import com.etrade.puggo.common.exception.CommonErrorV2;
import com.etrade.puggo.common.exception.PaymentException;
import com.etrade.puggo.common.exception.ServiceException;
import com.etrade.puggo.common.page.PageContentContainer;
import com.etrade.puggo.dao.goods.GoodsDao;
import com.etrade.puggo.dao.goods.GoodsPictureDao;
import com.etrade.puggo.dao.goods.GoodsTradeDao;
import com.etrade.puggo.db.tables.records.GoodsRecord;
import com.etrade.puggo.service.BaseService;
import com.etrade.puggo.service.account.UserAccountService;
import com.etrade.puggo.service.goods.opt.UpdateGoodsStateService;
import com.etrade.puggo.service.goods.sales.pojo.GoodsMainPicUrlDTO;
import com.etrade.puggo.service.goods.sales.pojo.GoodsTradeDTO;
import com.etrade.puggo.service.goods.sales.pojo.LaunchUserDO;
import com.etrade.puggo.service.goods.sales.pojo.TradeNoVO;
import com.etrade.puggo.service.goods.trade.pojo.*;
import com.etrade.puggo.service.payment.PaymentCheckUtils;
import com.etrade.puggo.utils.DateTimeUtils;
import com.etrade.puggo.utils.IncrTradeNoUtils;
import com.etrade.puggo.utils.OptionalUtils;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author niuzhenyu
 * @description : 商品成交
 * @date 2023/6/30 10:57
 **/
@Service
@Slf4j
public class GoodsTradeService extends BaseService {

    @Resource
    private GoodsTradeDao goodsTradeDao;

    @Resource
    private GoodsPictureDao goodsPictureDao;

    @Resource
    private GoodsDao goodsDao;

    @Resource
    private UpdateGoodsStateService updateGoodsStateService;

    @Resource
    private IncrTradeNoUtils incrTradeNoUtils;

    @Resource
    private UserAccountService userAccountService;

    @Resource
    private PaymentCheckUtils paymentCheckUtils;


    /**
     * 生成订单
     *
     * @param param 交易信息
     * @return 订单编号信息
     */
    @Transactional(rollbackFor = Throwable.class)
    public TradeNoVO saveTrade(SaveTradeParam param) {

        Long customerId = param.getCustomerId();
        Long sellerId = param.getSellerId();
        BigDecimal price = param.getPrice();
        Long goodsId = param.getGoodsId();

        // published状态的商品才允许开单
        GoodsRecord goods = goodsDao.findOne(goodsId);

        if (goods == null) {
            throw new ServiceException(LangErrorEnum.INVALID_GOODS.lang());
        }

        if (!goods.getState().equals(GoodsState.PUBLISHED)) {
            throw new ServiceException(CommonErrorV2.GOODS_IS_RESERVED);
        }

        // 检查支付信息
        checkParameter(param);

        MyTradeVO oneTrade = getOne(param);
        if (oneTrade != null) {
            return new TradeNoVO(oneTrade.getTradeId(), oneTrade.getTradeNo());
        }

        List<LaunchUserDO> userList = userAccountService.getUserList(Lists.newArrayList(customerId, sellerId));

        Optional<LaunchUserDO> anySeller = userList.stream().filter(u -> u.getUserId().equals(sellerId)).findAny();
        LaunchUserDO seller = anySeller.orElse(null);

        if (seller == null) {
            throw new ServiceException("Invalid seller");
        }

        Optional<LaunchUserDO> anyCustomer = userList.stream().filter(u -> u.getUserId().equals(customerId)).findAny();
        LaunchUserDO customer = anyCustomer.orElse(null);

        if (customer == null) {
            throw new ServiceException("Invalid customer");
        }

        String title = param.getTarget().equals(PaymentTargetEnum.product.name()) ? "商品交易" : "AI鉴定支付";

        GoodsTradeDTO trade = GoodsTradeDTO.builder()
                .state(GoodsTradeState.PAY_PENDING)
                .goodsId(goodsId)
                .customerId(customer.getUserId())
                .customer(customer.getNickname())
                .sellerId(sellerId)
                .tradingTime(DateTimeUtils.now())
                .tradingPrice(price)
                .shippingMethod(OptionalUtils.valueOrDefault(param.getShippingMethod(), 0))
                .paymentMethodId(OptionalUtils.valueOrDefault(param.getPaymentMethodId(), ""))
                .paymentType(OptionalUtils.valueOrDefault(param.getPaymentType(), ""))
                .deliveryAddressId(OptionalUtils.valueOrDefault(param.getDeliveryAddressId(), 0))
                .billingAddressId(OptionalUtils.valueOrDefault(param.getBillingAddressId(), 0))
                .isSameAsDeliveryAddress(BooleanUtils.isTrue(param.getIsSameAsDeliveryAddress()) ? (byte) 1 : (byte) 0)
                .paymentCardId(OptionalUtils.valueOrDefault(param.getPaymentCardId(), 0))
                .title(title)
                .build();

        String tradeNo = incrTradeNoUtils.get("P");
        Long tradeId = goodsTradeDao.save(trade, tradeNo);

        // 更新货品状态为已预售，防止重复抢占
        updateGoodsStateService.updateStateInner(goodsId, GoodsState.OCCUPY);

        return new TradeNoVO(tradeId, tradeNo);
    }


    public PageContentContainer<GoodsTradeVO> getGoodsTradeList(GoodsTradeParam param) {
        return goodsTradeDao.listTradePage(param);
    }


    public PageContentContainer<MyTradeVO> getMyTradeList(UserGoodsTradeParam param) {

        PageContentContainer<MyTradeVO> page = goodsTradeDao.listMyTradePage(param);

        if (page.isEmpty()) {
            return page;
        }

        List<MyTradeVO> list = page.getList();

        // 发布人信息
        List<Long> userIdList = list.stream().map(MyTradeVO::getLaunchUserId).collect(Collectors.toList());

        List<LaunchUserDO> userList = userAccountService.getUserList(userIdList);

        Map<Long, LaunchUserDO> userMap = userList.stream()
                .collect(Collectors.toMap(LaunchUserDO::getUserId, Function.identity()));

        for (MyTradeVO vo : list) {
            if (userMap.containsKey(vo.getLaunchUserId())) {
                vo.setLaunchUser(userMap.get(vo.getLaunchUserId()));
            }
        }

        // 商品主图
        List<Long> goodsIdList = list.stream().map(MyTradeVO::getGoodsId).collect(Collectors.toList());

        List<GoodsMainPicUrlDTO> mainPicList = goodsPictureDao.findGoodsMainPicList(goodsIdList);

        Map<Long, String> mainPicMap = mainPicList.stream()
                .collect(Collectors.toMap(GoodsMainPicUrlDTO::getGoodsId, GoodsMainPicUrlDTO::getUrl, (o1, o2) -> o1));

        for (MyTradeVO vo : list) {
            if (mainPicMap.containsKey(vo.getGoodsId())) {
                vo.setMainImgUrl(mainPicMap.get(vo.getGoodsId()));
            }
        }

        return page;
    }


    public MyTradeVO getOne(GetTradeParam param) {
        return goodsTradeDao.getOne(param.getCustomerId(), param.getSellerId(), param.getGoodsId());
    }


    public MyTradeVO getOne(Long tradeId) {
        if (tradeId == null) {
            return null;
        }
        return goodsTradeDao.getOne(tradeId);
    }


    public void updateTradePaymentInfo(UpdateTradeParam param) {
        goodsTradeDao.updateGoodsTrade(param);
    }


    private void checkParameter(SaveTradeParam param) {

        if (param.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new PaymentException(LangErrorEnum.INVALID_AMOUNT.lang());
        }

        // 检查类型
        paymentCheckUtils.checkTarget(param.getTarget());

        // 检查支付方式
        if (!StringUtils.isBlank(param.getPaymentType())) {
            paymentCheckUtils.checkPaymentType(param.getPaymentType());
        }

        // 检查交易方式
        if (param.getShippingMethod() != null) {
            paymentCheckUtils.checkShippingMethod(param.getShippingMethod());
        }

        // 检查收货地址
        if (param.getDeliveryAddressId() != null) {
            paymentCheckUtils.checkDeliveryAddress(param.getDeliveryAddressId());
        }

        // 检查账单地址
        if (BooleanUtils.isNotTrue(param.getIsSameAsDeliveryAddress())) {
            if (param.getBillingAddressId() != null) {
                paymentCheckUtils.checkBillingAddress(param.getBillingAddressId());
            }
        }

        // 检查银行卡信息
        if (Objects.equals(param.getPaymentType(), PaymentTypeEnum.card.name())) {
            paymentCheckUtils.checkCardInfo(param.getPaymentCardId());
        }
    }
}
