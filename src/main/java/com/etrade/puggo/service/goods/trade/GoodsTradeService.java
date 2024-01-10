package com.etrade.puggo.service.goods.trade;

import com.etrade.puggo.common.exception.ServiceException;
import com.etrade.puggo.common.page.PageContentContainer;
import com.etrade.puggo.constants.GoodsMessageState;
import com.etrade.puggo.constants.GoodsState;
import com.etrade.puggo.constants.GoodsTradeState;
import com.etrade.puggo.dao.goods.GoodsDao;
import com.etrade.puggo.dao.goods.GoodsMessageLogDao;
import com.etrade.puggo.dao.goods.GoodsPictureDao;
import com.etrade.puggo.dao.goods.GoodsTradeDao;
import com.etrade.puggo.service.BaseService;
import com.etrade.puggo.service.account.UserAccountService;
import com.etrade.puggo.service.goods.sales.AcceptPriceParam;
import com.etrade.puggo.service.goods.sales.GoodsDetailVO;
import com.etrade.puggo.service.goods.sales.GoodsMainPicUrlDTO;
import com.etrade.puggo.service.goods.sales.GoodsTradeDTO;
import com.etrade.puggo.service.goods.sales.LaunchUserDO;
import com.etrade.puggo.service.goods.sales.TradeNoVO;
import com.etrade.puggo.service.setting.SettingService;
import com.etrade.puggo.utils.DateTimeUtils;
import com.etrade.puggo.utils.IncrTradeNoUtils;
import com.etrade.puggo.utils.OptionalUtils;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private GoodsDao goodsDao;
    @Resource
    private GoodsMessageLogDao goodsMessageLogDao;
    @Resource
    private GoodsPictureDao goodsPictureDao;
    @Resource
    private IncrTradeNoUtils incrTradeNoUtils;
    @Resource
    private SettingService settingService;
    @Resource
    private UserAccountService userAccountService;


    /**
     * 买家接受出价
     *
     * @author niuzhenyu
     * @lastEditor niuzhenyu
     * @createTime 2023/6/25 12:09
     * @editTime 2023/6/25 12:09
     **/
    @Transactional(rollbackFor = Throwable.class)
    public TradeNoVO acceptPriceCallback(AcceptPriceParam param) {
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

        List<LaunchUserDO> userList = userAccountService.getUserList(Collections.singletonList(customerId));

        if (userList.isEmpty()) {
            throw new ServiceException("买家不存在");
        }

        // 修改商品会话状态
        goodsMessageLogDao.updateState(customerId, sellerId, goodsId, GoodsMessageState.ACCEPT_PRICE);

        // 修改商品状态
        String v = settingService.k("autoOccupy");
        if (Objects.equals(v, "1")) {
            goodsDao.updateGoodsSale(goodsId, GoodsState.OCCUPY);
        }

        // 生成订单
        LaunchUserDO customer = userList.get(0);

        GoodsTradeDTO trade = GoodsTradeDTO.builder()
            .state(GoodsTradeState.TO_USE)
            .goodsId(goodsId)
            .customerId(customer.getUserId())
            .customer(customer.getNickname())
            .tradingTime(DateTimeUtils.now())
            .tradingPrice(price)
            .build();

        String tradeNo = incrTradeNoUtils.get("GS");

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
