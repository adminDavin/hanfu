package com.hanfu.product.center.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanfu.product.center.dao.HfStoneMapper;
import com.hanfu.product.center.model.HfStone;
import com.hanfu.product.center.service.StoreService;
@Service
public class StoreServiceImpl implements StoreService{
	
	@Autowired
	private HfStoneMapper hfStoneMapper;
	
	@Override
	public HfStone findByProductId(Integer productId) {
		return hfStoneMapper.selectByProductId(productId);
	}
}
