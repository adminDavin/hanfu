package com.hanfu.product.center.service.impl;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanfu.product.center.manual.dao.ManualDao;
import com.hanfu.product.center.service.ProductService;

@Service("productService")
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ManualDao manualDao;

	@Override
	public void getProductByStone(Integer stoneId) {
		manualDao.selectProductByStone(stoneId);
		
	}



}
