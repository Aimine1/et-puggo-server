package com.etrade.puggo.service.goods.trade;

import com.etrade.puggo.common.exception.ServiceException;
import com.etrade.puggo.common.page.PageContentContainer;
import com.etrade.puggo.constants.GoodsTradeState;
import com.etrade.puggo.dao.goods.GoodsPictureDao;
import com.etrade.puggo.dao.goods.GoodsTradeDao;
import com.etrade.puggo.service.BaseService;
import com.etrade.puggo.service.account.UserAccountService;
import com.etrade.puggo.service.goods.sales.pojo.GoodsMainPicUrlDTO;
import com.etrade.puggo.service.goods.sales.pojo.GoodsTradeDTO;
import com.etrade.puggo.service.goods.sales.pojo.LaunchUserDO;
import com.etrade.puggo.service.goods.sales.pojo.TradeNoVO;
import com.etrade.puggo.utils.DateTimeUtils;
import com.etrade.puggo.utils.IncrTradeNoUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author niuzhenyu
 * @description : 商品成交
 * @date 2023/6/30 10:57
 **/
@Service
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
     * @param customerId 买家ID
     * @param goodsId    商品id
     * @param price      交易价格
     * @return
     */
    public TradeNoVO saveTrade(Long customerId, Long goodsId, BigDecimal price) {
        List<LaunchUserDO> userList = userAccountService.getUserList(Collections.singletonList(customerId));

        if (userList.isEmpty()) {
            throw new ServiceException("买家不存在");
        }

        LaunchUserDO customer = userList.get(0);

        GoodsTradeDTO trade = GoodsTradeDTO.builder()
                .state(GoodsTradeState.TO_USE)
                .goodsId(goodsId)
                .customerId(customer.getUserId())
                .customer(customer.getNickname())
                .tradingTime(DateTimeUtils.now())
                .tradingPrice(price)
                .build();

        String tradeNo = incrTradeNoUtils.get("P");

        Long tradeId = goodsTradeDao.save(trade, tradeNo);

        return new TradeNoVO(tradeId, tradeNo);
    }


    public PageContentContainer<GoodsTradeVO> getGoodsTradeList(GoodsTradeParam param) {
        return goodsTradeDao.findTradePage(param);
    }


    public PageContentContainer<MyTradeVO> getMyTradeList(UserGoodsTradeParam param) {

        PageContentContainer<MyTradeVO> page = goodsTradeDao.findMyTradePage(param);

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

}
