package com.hanfu.order.center.service.impl;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanfu.order.center.manual.dao.ManualDao;
import com.hanfu.order.center.service.ProductService;

@Service("productService")
@org.apache.dubbo.config.annotation.Service(registry = "dubboProductServer")
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ManualDao manualDao;

	@Override
	public void getProductByStone(Integer stoneId) {
		manualDao.selectProductByStone(stoneId);
		System.out.println("hello word");
	}



}
