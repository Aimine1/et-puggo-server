package com.etrade.puggo.service.goods.opt;

import com.etrade.puggo.common.exception.ServiceException;
import com.etrade.puggo.constants.LangConstant;
import com.etrade.puggo.dao.goods.GoodsDao;
import com.etrade.puggo.db.tables.records.GoodsRecord;
import com.etrade.puggo.filter.AuthContext;
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


    @Value("${goods.shareLink.script.zh_cn: 来自Puggo分享,复制后打开Puggo查看详情哦~}")
    private String zh_cnScript;

    @Value("${goods.shareLink.script.en_us: from Puggo sharing, copy and open Puggo to view details~}")
    private String en_usScript;


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

        String shareLink = String.format("<%s>%s[ID=%s]", goods.getTitle(), getScript(), encrypt);

        return new GoodsShareLinkVO(goodsId, shareLink);
    }


    public DecryptGoodsShareLinkVO decryptLink(String shareLink) {

        String lSeparator = "[ID=";
        String rSeparator = "]";

        if (!shareLink.contains(lSeparator) || !shareLink.contains(rSeparator)) {
            throw new ServiceException("商品不存在");
        }

        String encrypt = subString(shareLink, lSeparator, rSeparator);

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


    private String getScript() {
        if (AuthContext.getLang().equals(LangConstant.ZH_CH)) {
            return zh_cnScript;
        } else {
            return en_usScript;
        }
    }

}
