package com.hanfu.inner.sdk.goods.center;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.hanfu.inner.model.product.center.Aa;
import com.hanfu.inner.model.product.center.HfGoodsDisplay;
import com.hanfu.inner.model.product.center.HfGoodsPictrue;

public interface GoodsService {

	public List<HfGoodsDisplay> findAllGoods();
	
	public List<HfGoodsPictrue> findAllPicture();

	public void getPicture(Aa aa) throws Exception;
	
}
