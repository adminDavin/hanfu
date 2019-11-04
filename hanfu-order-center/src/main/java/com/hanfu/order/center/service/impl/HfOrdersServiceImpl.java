package com.hanfu.order.center.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.hanfu.inner.sdk.product.center.ProductService;
import com.hanfu.order.center.dao.HfOrderLogisticsMapper;
import com.hanfu.order.center.dao.HfOrderStatusMapper;
import com.hanfu.order.center.dao.HfOrdersDetailMapper;
import com.hanfu.order.center.dao.HfOrdersMapper;
import com.hanfu.order.center.manual.dao.OrderDao;
import com.hanfu.order.center.model.HfOrderLogistics;
import com.hanfu.order.center.model.HfOrders;
import com.hanfu.order.center.model.HfOrdersDetail;
import com.hanfu.order.center.request.HfOrderLogisticsRequest;
import com.hanfu.order.center.request.HfOrdersDetailRequest;
import com.hanfu.order.center.request.HfOrdersRequest;
import com.hanfu.order.center.response.handler.OrderIsExistException;
import com.hanfu.order.center.service.HfOrdersService;

@Service("hfOrdersDetailService")
public class HfOrdersServiceImpl implements HfOrdersService {
	@Autowired
	HfOrdersDetailMapper hfOrdersDetailMapper;
	@Autowired
	HfOrderLogisticsMapper hfOrderLogisticsMapper;
	@Autowired
	HfOrdersMapper hfOrdersMapper;
	@Reference(registry = "dubboProductServer", url = "dubbo://127.0.0.1:1900/com.hanfu.inner.sdk.product.center.ProductService")
	ProductService productService;
	@Autowired
	OrderDao orderDao;
	@Autowired
	HfOrderStatusMapper hfOrderStatusMapper;
	@Override
	public List creatOrder(HfOrdersDetailRequest request, HfOrdersRequest hfOrder,
			HfOrderLogisticsRequest hfOrderLogistics)  {
		String logisticsOrdersId = UUID.randomUUID().toString();
		HfOrders hfOrders = new HfOrders();
		hfOrders.setUserId(hfOrder.getUserId());
		Integer price = request.getPurchasePrice();
		Integer quantity = request.getPurchaseQuantity();
		Integer amount = price*quantity;
		hfOrders.setAmount(amount);
		hfOrders.setHfMemo(hfOrder.getHfMemo());
		hfOrders.setHfRemark(hfOrder.getHfRemark());
		hfOrders.setId(hfOrder.getId());
		hfOrders.setPayMethodName(hfOrder.getPayMethodName());
		hfOrders.setPayMethodType(hfOrder.getPayMethodType());
		hfOrders.setPayStatus(hfOrder.getPayStatus());
		hfOrders.setCreateTime(LocalDateTime.now());
		hfOrders.setOrderType(hfOrder.getOrderType());
		hfOrders.setModifyTime(LocalDateTime.now());
		hfOrders.setIsDeleted((short) 0);
		hfOrders.setLastModifier("1");
		hfOrdersMapper.insert(hfOrders);
		HfOrdersDetail hfOrdersDetail = new HfOrdersDetail();
		hfOrdersDetail.setGoogsId(request.getGoogsId()); 
		hfOrdersDetail.setHfDesc(request.getHfDesc());
		hfOrdersDetail.setHfTax(request.getHfTax());
		hfOrdersDetail.setDistribution(request.getDistribution());
		hfOrdersDetail.setRespId(request.getRespId());
		hfOrdersDetail.setPurchasePrice(request.getPurchasePrice());
		hfOrdersDetail.setPurchaseQuantity(request.getPurchaseQuantity());
		hfOrdersDetail.setOrderDetailStatus(hfOrderStatusMapper.selectByPrimaryKey(10).getHfName());
		hfOrdersDetail.setCreateTime(LocalDateTime.now());
		hfOrdersDetail.setModifyTime(LocalDateTime.now());
		hfOrdersDetail.setIsDeleted((short) 0);
		hfOrdersDetail.setOrdersId(hfOrder.getId());
		hfOrdersDetail.setLastModifier("1");
		hfOrdersDetail.setId(hfOrder.getId());
		hfOrdersDetailMapper.insert(hfOrdersDetail);
		HfOrderLogistics hfOrderLogistic = new HfOrderLogistics();
		hfOrderLogistic.setGoogsId(request.getGoogsId());
		hfOrderLogistic.setCreateTime(LocalDateTime.now());
		hfOrderLogistic.setLogisticsCompany(hfOrderLogistics.getLogisticsCompany());
		hfOrderLogistic.setLogisticsOrderName(hfOrderLogistics.getLogisticsOrderName());
		hfOrderLogistic.setLogisticsOrdersId(logisticsOrdersId);
		hfOrderLogistic.setOrderDetailId(request.getId());
		hfOrderLogistic.setUserAddressId(hfOrderLogistics.getUserAddressId());
		hfOrderLogistic.setUserId(hfOrderLogistics.getUserId());
		hfOrderLogistic.setOrdersId(hfOrder.getId());
		hfOrderLogistic.setRespId(request.getRespId());
		hfOrderLogistic.setGoogsId(request.getGoogsId());
		hfOrderLogistic.setModifyTime(LocalDateTime.now());
		hfOrderLogistic.setIsDeleted((short) 0);
		hfOrderLogistic.setLastModifier("1");
		hfOrderLogistic.setHfDesc(hfOrderLogistics.getHfDesc());
		hfOrderLogisticsMapper.insert(hfOrderLogistic);
		List list = new ArrayList<>();
		list.add(hfOrdersDetail);
		list.add(hfOrderLogistic);
		list.add(hfOrders);
		return list;
	}
	@SuppressWarnings("unused")
	@Override
	public List updateOrder(HfOrdersDetailRequest request, HfOrdersRequest hfOrder,
			HfOrderLogisticsRequest hfOrderLogistics) throws Exception {
		HfOrdersDetail hfOrdersDetail = new HfOrdersDetail();
		if(hfOrdersDetail == null) {
			throw new OrderIsExistException(String.valueOf(request.getId()));
		}
		if(!StringUtils.isEmpty(request.getRespId())) {
			hfOrdersDetail.setRespId(request.getRespId());
		}
		if(!StringUtils.isEmpty(request.getPurchaseQuantity())) {
			hfOrdersDetail.setPurchaseQuantity(request.getPurchaseQuantity());
		}
		if(!StringUtils.isEmpty(request.getPurchasePrice())) {
			hfOrdersDetail.setPurchasePrice(request.getPurchasePrice());
		}	
		if(!StringUtils.isEmpty(request.getOrderDetailStatus())) {
			hfOrdersDetail.setOrderDetailStatus(request.getOrderDetailStatus());
		}
		if(!StringUtils.isEmpty(request.getHfTax())) {
			hfOrdersDetail.setHfTax(request.getHfTax());
		}
		if(!StringUtils.isEmpty(request.getHfDesc())) {
			hfOrdersDetail.setHfDesc(request.getHfDesc());
		}
		if(!StringUtils.isEmpty(request.getGoogsId())) {
			hfOrdersDetail.setGoogsId(request.getGoogsId());
		}
		if(!StringUtils.isEmpty(request.getDistribution())) {
			hfOrdersDetail.setDistribution(request.getDistribution());
		}
		hfOrdersDetail.setModifyTime(LocalDateTime.now());
		hfOrdersDetail.setIsDeleted((short) 0);
		hfOrdersDetailMapper.updateByPrimaryKeySelective(hfOrdersDetail);
		HfOrders hfOrders = new HfOrders();
		if(hfOrders == null) {
			throw new OrderIsExistException(String.valueOf(hfOrder.getId()));
		}
		if(!StringUtils.isEmpty(hfOrder.getUserId())) {
			hfOrders.setUserId(hfOrder.getUserId());
		}
		if(!StringUtils.isEmpty(hfOrder.getPayStatus())) {
			hfOrders.setPayStatus(hfOrder.getPayStatus());
		}
		if(!StringUtils.isEmpty(hfOrder.getPayMethodType())) {
			hfOrders.setPayMethodType(hfOrder.getPayMethodType());
		}
		if(!StringUtils.isEmpty(hfOrder.getPayMethodName())) {
			hfOrders.setPayMethodName(hfOrder.getPayMethodName());
		}
		if(!StringUtils.isEmpty(hfOrder.getOrderType())) {
			hfOrders.setOrderType(hfOrder.getOrderType());
		}
		if(!StringUtils.isEmpty(hfOrder.getHfRemark())) {
			hfOrders.setHfRemark(hfOrder.getHfRemark());
		}
		if(!StringUtils.isEmpty(hfOrder.getHfMemo())) {
			hfOrders.setHfMemo(hfOrder.getHfMemo());
		}
		if(!StringUtils.isEmpty(hfOrder.getAmount())) {
			hfOrders.setAmount(hfOrder.getAmount());
		}
		hfOrders.setModifyTime(LocalDateTime.now());
		hfOrders.setIsDeleted((short) 0);
		hfOrdersMapper.updateByPrimaryKey(hfOrders);
		HfOrderLogistics hfOrderLogistic = new HfOrderLogistics();
		if(hfOrderLogistic == null) {
			throw new OrderIsExistException(String.valueOf(hfOrderLogistics.getId()));
		}
		if(!StringUtils.isEmpty(hfOrder.getUserId())) {
			hfOrderLogistic.setUserId(hfOrder.getUserId());
		}
		if(!StringUtils.isEmpty(hfOrderLogistics.getUserAddressId())) {
			hfOrderLogistic.setUserAddressId(hfOrderLogistics.getUserAddressId());
		}
		if(!StringUtils.isEmpty(hfOrderLogistics.getRespId())) {
			hfOrderLogistic.setRespId(hfOrderLogistics.getRespId());
		}
		if(!StringUtils.isEmpty(hfOrderLogistics.getOrdersId())) {
			hfOrderLogistic.setOrdersId(hfOrderLogistics.getOrdersId());
		}
		if(!StringUtils.isEmpty(hfOrderLogistics.getOrderDetailId())) {
			hfOrderLogistic.setOrderDetailId(hfOrderLogistics.getOrderDetailId());
		}
		if(!StringUtils.isEmpty(hfOrderLogistics.getLogisticsOrdersId())) {
			hfOrderLogistic.setLogisticsOrdersId(hfOrderLogistics.getLogisticsOrdersId());
		}
		if(!StringUtils.isEmpty(hfOrderLogistics.getLogisticsOrderName())) {
			hfOrderLogistic.setLogisticsOrderName(hfOrderLogistics.getLogisticsOrderName());
		}
		if(!StringUtils.isEmpty(hfOrderLogistics.getLogisticsCompany())) {
			hfOrderLogistic.setLogisticsCompany(hfOrderLogistics.getLogisticsCompany());
		}
		if(!StringUtils.isEmpty(hfOrderLogistics.getHfDesc())) {
			hfOrderLogistic.setHfDesc(hfOrderLogistics.getHfDesc());
		}
		if(!StringUtils.isEmpty(hfOrderLogistics.getGoogsId())) {
			hfOrderLogistic.setGoogsId(hfOrderLogistics.getGoogsId());
		}
		hfOrderLogistic.setModifyTime(LocalDateTime.now());
		hfOrderLogistic.setIsDeleted((short) 0);
		hfOrderLogisticsMapper.updateByPrimaryKey(hfOrderLogistic);
		List list = new ArrayList<>();
		list.add(hfOrdersDetail);
		list.add(hfOrderLogistic);
		list.add(hfOrders);
		return list;
	}
}
