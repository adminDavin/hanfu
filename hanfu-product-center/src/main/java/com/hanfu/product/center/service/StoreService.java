package com.hanfu.product.center.service;

import com.hanfu.product.center.model.HfStone;

public interface StoreService {
	 HfStone findByProductId(Integer productId);
}
