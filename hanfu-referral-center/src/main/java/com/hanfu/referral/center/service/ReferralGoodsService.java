package com.hanfu.referral.center.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
 
import com.hanfu.inner.model.product.center.HfGoodsDisplay;
import com.hanfu.inner.model.product.center.HfGoodsPictrue;
import com.hanfu.inner.model.product.center.Product;

public interface ReferralGoodsService {
	public List<HfGoodsDisplay> getAllGoods(Integer page,Integer size);
	
	public List<HfGoodsPictrue> findAllPicture();
 
//	public void getPicture(Integer fileId, HttpServletResponse response) throws Exception;

	public List<HfGoodsDisplay> getGoodsInfo(Integer goodsId);
}
