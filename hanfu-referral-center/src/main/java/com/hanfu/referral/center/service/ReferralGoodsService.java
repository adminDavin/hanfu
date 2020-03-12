package com.hanfu.referral.center.service;

import com.hanfu.inner.model.product.center.HfGoodsDisplay;
import com.hanfu.inner.model.product.center.HfGoodsPictrue;

import java.util.List;

public interface ReferralGoodsService {
    public List<HfGoodsDisplay> getAllGoods(Integer page, Integer size);

    public List<HfGoodsPictrue> findAllPicture();

//	public void getPicture(Integer fileId, HttpServletResponse response) throws Exception;

    public List<HfGoodsDisplay> getGoodsInfo(Integer goodsId);
}
