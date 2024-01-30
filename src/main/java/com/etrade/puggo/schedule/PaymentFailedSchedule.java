package com.etrade.puggo.schedule;

import com.etrade.puggo.common.constants.GoodsTradeState;
import com.etrade.puggo.dao.goods.GoodsTradeDao;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhenyu
 * @version 1.0
 * @description: 支付超时订单变更为支付失败状态
 * @date 2024/1/29 19:37
 */
@Slf4j
@Component
public class PaymentFailedSchedule {


    @Resource
    private GoodsTradeDao goodsTradeDao;


    @Scheduled(cron = "0 */5 * * * ?")
    public void loopCheck() {
        List<Long> tradeIds = goodsTradeDao.getTradesWithPaymentTimeout();

        log.info("[PaymentFailedSchedule] 当前支付超时的订单id= {}", tradeIds);

        List<List<Long>> partition = Lists.partition(tradeIds, 100);

        for (List<Long> partTradeIds : partition) {
            goodsTradeDao.batchUpdateState(partTradeIds, GoodsTradeState.FAILED);
            log.info("[PaymentFailedSchedule] 「支付超时」订单状态变更为「支付失败」， 订单id={}", partTradeIds);
        }
    }

}
