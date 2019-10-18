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
import com.hanfu.order.center.request.HfOrderLogisticsRequest;
import com.hanfu.order.center.request.HfOrdersDetailRequest;
import com.hanfu.order.center.request.HfOrdersRequest;
import com.hanfu.order.center.service.HfOrdersService;
import com.hanfu.order.center.service.ProductService;
@Service("hfOrdersDetailService")
public class HfOrdersServiceImpl implements HfOrdersService {
	@Autowired
	HfOrdersDetailMapper hfOrdersDetailMapper;
	@Autowired
	HfOrderLogisticsMapper hfOrderLogisticsMapper;
	@Autowired
	HfOrdersMapper hfOrdersMapper;
	@Autowired
	ProductService productService;
	@Override
	public List creatOrder(HfOrdersDetailRequest request, HfOrdersRequest hfOrder,
			HfOrderLogisticsRequest hfOrderLogistics)  {
		Integer ordersId = UUID.randomUUID().version();
		String logisticsOrdersId = UUID.randomUUID().toString();
		HfOrdersDetail hfOrdersDetail = new HfOrdersDetail();
		hfOrdersDetail.setGoogsId(request.getGoogsId());
		hfOrdersDetail.setHfDesc(request.getHfDesc());
		hfOrdersDetail.setHfTax(request.getHfTax());
		hfOrdersDetail.setDistribution(request.getDistribution());
		hfOrdersDetail.setRespId(request.getRespId());
		hfOrdersDetail.setPurchasePrice(request.getPurchasePrice());
		hfOrdersDetail.setPurchaseQuantity(request.getPurchaseQuantity());
		hfOrdersDetail.setOrderDetailStatus(request.getOrderDetailStatus());
		hfOrdersDetail.setCreateTime(LocalDateTime.now());
		hfOrdersDetailMapper.insert(hfOrdersDetail);
		HfOrders hfOrders = new HfOrders();
		hfOrders.setUserId(hfOrder.getUserId());
		Integer price = request.getPurchasePrice();
		Integer quantity = request.getPurchaseQuantity();
		Integer amount = price*quantity;
		hfOrders.setAmount(amount);
		hfOrders.setHfMemo(hfOrder.getHfMemo());
		hfOrders.setHfRemark(hfOrder.getHfRemark());
		hfOrders.setId(ordersId);
		hfOrders.setPayMethodName(hfOrder.getPayMethodName());
		hfOrders.setPayMethodType(hfOrder.getPayMethodType());
		hfOrders.setPayStatus(hfOrder.getPayStatus());
		hfOrders.setCreateTime(LocalDateTime.now());
		hfOrders.setOrderType(hfOrder.getOrderType());
		hfOrdersMapper.insert(hfOrders);
		HfOrderLogistics hfOrderLogistic = new HfOrderLogistics();
		hfOrderLogistic.setGoogsId(request.getGoogsId());
		hfOrderLogistic.setCreateTime(LocalDateTime.now());
		hfOrderLogistic.setLogisticsCompany(hfOrderLogistics.getLogisticsCompany());
		hfOrderLogistic.setLogisticsOrderName(hfOrderLogistics.getLogisticsOrderName());
		hfOrderLogistic.setLogisticsOrdersId(logisticsOrdersId);
		hfOrderLogistic.setOrderDetailId(request.getId());
		hfOrderLogistic.setUserAddressId(hfOrderLogistics.getUserAddressId());
		hfOrderLogistic.setUserId(hfOrder.getUserId());
		hfOrderLogistic.setOrdersId(ordersId);
		hfOrderLogistic.setRespId(request.getRespId());
		hfOrderLogistic.setGoogsId(request.getGoogsId());
		hfOrderLogisticsMapper.insert(hfOrderLogistic);
		List list = new ArrayList<>();
		list.add(hfOrdersDetail);
		list.add(hfOrderLogistic);
		list.add(hfOrders);
		return list;
	}
	@Override
	public List updateOrder(HfOrdersDetailRequest request, HfOrdersRequest hfOrder,
			HfOrderLogisticsRequest hfOrderLogistics) {
		HfOrdersDetail hfOrdersDetail = new HfOrdersDetail();
		hfOrdersDetailMapper.updateByPrimaryKey(hfOrdersDetail);
		HfOrders hfOrders = new HfOrders();
		hfOrdersMapper.updateByPrimaryKey(hfOrders);
		HfOrderLogistics hfOrderLogistic = new HfOrderLogistics();
		hfOrderLogisticsMapper.updateByPrimaryKey(hfOrderLogistic);
		List list = new ArrayList<>();
		list.add(hfOrdersDetail);
		list.add(hfOrderLogistic);
		list.add(hfOrders);
		return list;
	}

}
