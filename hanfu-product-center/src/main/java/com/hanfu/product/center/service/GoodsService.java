package com.hanfu.product.center.service;

import java.util.List;

import com.hanfu.product.center.model.HfGoods;

public interface GoodsService {
	public Integer[] findAllByStoreId(Integer storeId);
	
	List<HfGoods> findAllByInstanceId(Integer[] instanceId);
}
