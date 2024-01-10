package com.etrade.puggo.utils;

import com.etrade.puggo.constants.RedisKeys;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

/**
 * @Description 获取订单编号，基于redis incr
 * @Author niuzhenyu
 * @Date 2021/12/29 18:37
 **/
@Component
public class IncrTradeNoUtils {

    public static final Long DIGIT_4_MAX = 9999L;
    public static final Long DIGIT_5_MAX = 99999L;
    public static final Long DIGIT_6_MAX = 999999L;


    @Resource
    private RedisUtils redisUtils;


    /**
     * 拿货订单生成，规则： 1、XX-211228-0001（中间-忽略），如果订单数量超过10万，规则自动变更为：XX-211228-10000 2、XX为导入来源标识，比如CQ(超群)、DN(代拿)等
     * 3、如果一批推送/导入超过1000条小标签，自动拆单
     *
     * @param prefix 前缀，2-3个大写英文字母，如JY
     * @return 单号
     */
    public String get(String prefix) {
        String dateFormatted = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"));
        String key = String.format(RedisKeys.TRADE_NO_PREFIX, prefix, dateFormatted);
        Long index = redisUtils.increment(key);
        if (index == null) {
            index = 1L;
            redisUtils.set(key, index, 1, TimeUnit.DAYS);
        }
        String no;
        if (index > DIGIT_6_MAX) {
            no = String.format("%07d", index);
        } else if (index > DIGIT_5_MAX) {
            no = String.format("%06d", index);
        } else if (index > DIGIT_4_MAX) {
            no = String.format("%05d", index);
        } else {
            no = String.format("%04d", index);
        }
        return String.format("%s%s%s", prefix, dateFormatted, no);
    }

}
