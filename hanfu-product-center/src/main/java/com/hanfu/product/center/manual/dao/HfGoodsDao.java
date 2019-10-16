package com.hanfu.product.center.manual.dao;

import java.util.List;

import com.hanfu.product.center.manual.model.Categories;
import com.hanfu.product.center.manual.model.HfGoodsDisplay;
import com.hanfu.product.center.manual.model.UserInfo;
import com.hanfu.product.center.model.HfGoods;

public interface HfGoodsDao {
	
	public List<HfGoodsDisplay> selectAllGoods(HfGoods hfGoods);
	
	public List<HfGoods> selectByStoneId(Integer stoneId);
}
