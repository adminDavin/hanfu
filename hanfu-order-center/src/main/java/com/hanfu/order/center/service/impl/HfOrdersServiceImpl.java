package com.hanfu.order.center.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanfu.order.center.dao.HfOrderLogisticsMapper;
import com.hanfu.order.center.dao.HfOrdersDetailMapper;
import com.hanfu.order.center.dao.HfOrdersMapper;
import com.hanfu.order.center.model.HfOrderLogistics;
import com.hanfu.order.center.model.HfOrders;
import com.hanfu.order.center.model.HfOrdersDetail;
import com.hanfu.order.center.request.HfOrdersRequest;
import com.hanfu.order.center.service.HfOrdersService;
@Service("hfOrdersDetailService")
public class HfOrdersServiceImpl implements HfOrdersService {
	@Autowired
	HfOrdersDetailMapper hfOrdersDetailMapper;
	@Autowired
	HfOrderLogisticsMapper hfOrderLogisticsMapper;
	@Autowired
	HfOrdersMapper hfOrdersMapper;
	
	@Override
	public List creatOrder(HfOrdersRequest request) {
		Integer orderId =UUID.randomUUID().clockSequence() ;
		HfOrdersDetail hfOrdersDetail = new HfOrdersDetail();
		hfOrdersDetail.setOrdersId(orderId);
		hfOrdersDetail.setCreateTime(LocalDateTime.now());
		hfOrdersDetail.setDistribution(request.getDistribution());
		hfOrdersDetail.setGoogsId(request.getGoogsId());
		hfOrdersDetail.setHfDesc(request.getHfDesc());
		hfOrdersDetail.setHfTax(request.getHfTax());
		hfOrdersDetail.setOrderDetailStatus(request.getOrderDetailStatus());
		hfOrdersDetail.setPurchasePrice(request.getPurchasePrice());
		hfOrdersDetail.setPurchaseQuantity(request.getPurchaseQuantity());
		hfOrdersDetail.setRespId(request.getRespId());	
		hfOrdersDetailMapper.insert(hfOrdersDetail);
		HfOrderLogistics hfOrderLogistics = new HfOrderLogistics();
		hfOrderLogistics.setUserId(request.getUserId());
		hfOrderLogistics.setGoogsId(request.getGoogsId());
		hfOrderLogistics.setUserAddressId(request.getUserAddressId());
		hfOrderLogistics.setRespId(request.getRespId());
		hfOrderLogistics.setOrderDetailId(request.getId());
		hfOrderLogistics.setLogisticsCompany(request.getLogisticsCompany());
		hfOrderLogistics.setLogisticsOrderName(request.getLogisticsOrderName());
		hfOrderLogistics.setLogisticsOrdersId(request.getLogisticsOrdersId());
		hfOrderLogistics.setCreateTime(LocalDateTime.now());
		hfOrderLogistics.setHfDesc(request.getHfDesc());
		hfOrderLogisticsMapper.insert(hfOrderLogistics);
		HfOrders hfOrders = new HfOrders();
		hfOrders.setAmount(request.getAmount());
		hfOrders.setHfMemo(request.getHfMemo());
		hfOrders.setHfRemark(request.getHfRemark());
		hfOrders.setOrderType(request.getOrderType());
		hfOrders.setPayMethodName(request.getPayMethodName());
		hfOrders.setPayMethodType(request.getPayMethodType());
		hfOrders.setPayStatus(request.getPayStatus());
		hfOrders.setUserId(request.getUserId());
		hfOrders.setCreateTime(LocalDateTime.now());
		hfOrders.setId(orderId);
		hfOrdersMapper.insert(hfOrders);
		List list = new ArrayList<>();
		list.add(hfOrdersDetail);
		list.add(hfOrderLogistics);
		list.add(hfOrders);
		return list;
	}




}
