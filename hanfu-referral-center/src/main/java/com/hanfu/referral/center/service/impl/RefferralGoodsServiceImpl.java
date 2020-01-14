package com.hanfu.referral.center.service.impl;

import com.hanfu.inner.model.product.center.HfGoodsDisplay;
import com.hanfu.inner.model.product.center.HfGoodsPictrue;
import com.hanfu.inner.sdk.goods.center.GoodsService;
import com.hanfu.referral.center.service.ReferralGoodsService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("referralGoodsService")
public class RefferralGoodsServiceImpl implements ReferralGoodsService {

    @Reference(registry = "dubboProductServer", url = "dubbo://127.0.0.1:1900/com.hanfu.inner.sdk.goods.center.GoodsService")
    private GoodsService goodsService;

    @Override
    public List<HfGoodsDisplay> getAllGoods(Integer page, Integer size) {
        return goodsService.findAllGoods(page, size);
    }

    @Override
    public List<HfGoodsPictrue> findAllPicture() {
        return goodsService.findAllPicture();
    }

//	@Override
//	public void getPicture(Integer fileId, HttpServletResponse response) throws Exception {
//		goodsService.getPicture(fileId, response);
//	}

    @Override
    public List<HfGoodsDisplay> getGoodsInfo(Integer goodsId) {
        return goodsService.getGoodsInfoApp(goodsId);
    }

}
