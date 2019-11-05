package com.hanfu.inner.sdk.goods.center;

import java.util.List;
 
import com.hanfu.inner.model.product.center.HfGoodsDisplay;
import com.hanfu.inner.model.product.center.HfGoodsPictrue;

public interface GoodsService {

	public List<HfGoodsDisplay> findAllGoods();
	
	public List<HfGoodsPictrue> findAllPicture();

	
}
