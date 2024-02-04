package com.etrade.puggo.service.goods.trade;

import com.etrade.puggo.common.constants.GoodsTradeState;
import com.etrade.puggo.common.exception.ServiceException;
import com.etrade.puggo.common.page.PageContentContainer;
import com.etrade.puggo.dao.goods.GoodsPictureDao;
import com.etrade.puggo.dao.goods.GoodsTradeDao;
import com.etrade.puggo.service.BaseService;
import com.etrade.puggo.service.account.UserAccountService;
import com.etrade.puggo.service.goods.sales.pojo.GoodsMainPicUrlDTO;
import com.etrade.puggo.service.goods.sales.pojo.GoodsTradeDTO;
import com.etrade.puggo.service.goods.sales.pojo.LaunchUserDO;
import com.etrade.puggo.service.goods.sales.pojo.TradeNoVO;
import com.etrade.puggo.service.goods.trade.pojo.*;
import com.etrade.puggo.utils.DateTimeUtils;
import com.etrade.puggo.utils.IncrTradeNoUtils;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
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
    private IncrTradeNoUtils incrTradeNoUtils;

    @Resource
    private UserAccountService userAccountService;


    /**
     * 生成订单
     *
     * @param param 交易信息
     * @return 订单编号信息
     */
    public TradeNoVO saveTrade(SaveTradeParam param) {

        Long customerId = param.getCustomerId();
        Long sellerId = param.getSellerId();
        BigDecimal price = param.getPrice();
        Long goodsId = param.getGoodsId();

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

        GoodsTradeDTO trade = GoodsTradeDTO.builder()
                .state(GoodsTradeState.TO_USE)
                .goodsId(goodsId)
                .customerId(customer.getUserId())
                .customer(customer.getNickname())
                .sellerId(sellerId)
                .tradingTime(DateTimeUtils.now())
                .tradingPrice(price)
                .build();

        String tradeNo = incrTradeNoUtils.get("P");

        Long tradeId = goodsTradeDao.save(trade, tradeNo);

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

}
