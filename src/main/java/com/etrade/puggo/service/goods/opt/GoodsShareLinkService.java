package com.etrade.puggo.service.goods.opt;

import com.etrade.puggo.common.exception.ServiceException;
import com.etrade.puggo.dao.goods.GoodsDao;
import com.etrade.puggo.db.tables.records.GoodsRecord;
import com.etrade.puggo.service.BaseService;
import com.etrade.puggo.utils.DESUtils;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

/**
 * @author niuzhenyu
 * @description : 商品分享链接
 * @date 2023/11/21 22:54
 **/
@Service
@RefreshScope
public class GoodsShareLinkService extends BaseService {


    @Resource
    private GoodsDao goodsDao;


    @Value("${goods.shareLink.script:生活有望穿秋水的等待也会有意想不到的惊喜，复制惊喜等你 }")
    private String script;

    @Value("${goods.shareLink.leftSeparator:[}")
    private String leftSeparator;

    @Value("${goods.shareLink.rightSeparator:].}")
    private String rightSeparator;


    public GoodsShareLinkVO shareLink(Long goodsId) {
        GoodsRecord goods = goodsDao.findOne(goodsId);

        if (goods == null) {
            throw new ServiceException("商品不存在");
        }

        String encrypt;
        try {
            encrypt = DESUtils.encrypt(String.valueOf(goodsId));
        } catch (Exception e) {
            throw new ServiceException("生成商品分享链接失败，请您稍后重试");
        }
        String shareLink = String.format("%s%s%s%s%s", script, leftSeparator, encrypt, rightSeparator,
            goods.getTitle());

        return new GoodsShareLinkVO(goodsId, shareLink);
    }


    public DecryptGoodsShareLinkVO decryptLink(String shareLink) {

        if (!shareLink.contains(leftSeparator) || !shareLink.contains(rightSeparator)) {
            throw new ServiceException("商品不存在");
        }

        String encrypt = subString(shareLink, leftSeparator, rightSeparator);

        String decrypt;
        try {
            decrypt = DESUtils.decrypt(encrypt);
        } catch (Exception e) {
            throw new ServiceException("商品不存在");
        }

        Long goodsId = Long.valueOf(decrypt);

        GoodsRecord goods = goodsDao.findOne(goodsId);
        if (goods == null) {
            throw new ServiceException("商品不存在");
        }

        return new DecryptGoodsShareLinkVO(goodsId);
    }


    private static String subString(String str, String strStart, String strEnd) {

        /* 找出指定的2个字符在 该字符串里面的 位置 */
        int strStartIndex = str.indexOf(strStart);
        int strEndIndex = str.indexOf(strEnd);

        /* index 为负数 即表示该字符串中 没有该字符 */
        if (strStartIndex < 0 || strEndIndex < 0) {
            return "";
        }

        /* 开始截取 */
        return str.substring(strStartIndex, strEndIndex).substring(strStart.length());

    }

}
