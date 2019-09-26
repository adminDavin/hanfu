package com.hanfu.product.center.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanfu.product.center.dao.GoodsSpecMapper;
import com.hanfu.product.center.dao.HfGoodsMapper;
import com.hanfu.product.center.model.HfGoods;
import com.hanfu.product.center.service.GoodsService;
@Service
public class GoodsServiceImpl implements GoodsService{
	
	@Autowired
	private HfGoodsMapper hfGoodsMapper;
	
	@Override
	public Integer[] findAllByStoreId(Integer Id) {
		Integer[] arr = hfGoodsMapper.selectAllByStoreId(Id);
		return arr;
	}
	
	@Autowired
	private GoodsSpecMapper goodsSpecMapper;
	
	@Override
	public List<HfGoods> findAllByInstanceId(Integer[] instanceId) {
		return goodsSpecMapper.selectAllByInstanceId(instanceId);
	}
	
}
