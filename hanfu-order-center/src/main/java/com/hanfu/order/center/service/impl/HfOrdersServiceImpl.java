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
import com.hanfu.order.center.manual.model.OrdersInfo;
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

	@SuppressWarnings({"unchecked", "rawtypes"})
	@Override
	public List<OrdersInfo> creatOrder(OrdersInfo ordersInfo) {
		HfOrders hfOrders = new HfOrders();
		HfOrdersDetail hfOrdersDetail = new HfOrdersDetail();
		hfOrders.setUserId(ordersInfo.getUserId());
		hfOrders.setAmount(ordersInfo.getAmount());
		hfOrders.setHfMemo(ordersInfo.getHfMemo());
		hfOrders.setHfRemark(ordersInfo.getHfRemark());
		hfOrders.setPayMethodName(ordersInfo.getPayMethodName());
		hfOrders.setPayMethodType(ordersInfo.getPayMethodType());
		hfOrders.setPayStatus(ordersInfo.getPayStatus());
		hfOrders.setCreateTime(LocalDateTime.now());
		hfOrders.setOrderType(ordersInfo.getOrderType());
		hfOrders.setModifyTime(LocalDateTime.now());
		hfOrders.setIsDeleted((short) 0);
		hfOrders.setLastModifier("1");
		hfOrdersMapper.insert(hfOrders);
		for (Integer googsId : ordersInfo.getGoogsId()) {
			for (Integer purchasePrice : ordersInfo.getPurchasePrice()) {
				for (Integer purchaseQuantity : ordersInfo.getPurchaseQuantity()) {
					hfOrdersDetail.setOrdersId(hfOrders.getId());
					hfOrdersDetail.setGoogsId(googsId);
					hfOrdersDetail.setHfDesc(ordersInfo.getHfDesc());
					hfOrdersDetail.setHfTax(ordersInfo.getHfTax());
					hfOrdersDetail.setDistribution(ordersInfo.getDistribution());
					hfOrdersDetail.setRespId(ordersInfo.getRespId());
					hfOrdersDetail.setPurchasePrice(purchasePrice);
					hfOrdersDetail.setPurchaseQuantity(purchaseQuantity);
					hfOrdersDetail.setOrderDetailStatus(hfOrderStatusMapper.selectByPrimaryKey(10).getHfName());
					hfOrdersDetail.setCreateTime(LocalDateTime.now());
					hfOrdersDetail.setModifyTime(LocalDateTime.now());
					hfOrdersDetail.setIsDeleted((short) 0);
					hfOrdersDetail.setOrdersId(hfOrders.getId());
					hfOrdersDetail.setLastModifier("1");
					hfOrdersDetailMapper.insert(hfOrdersDetail);
				}
			}
		}
		return null;
	}

	@Override
	public List updateOrder(HfOrdersDetailRequest request, HfOrdersRequest hfOrder,
			HfOrderLogisticsRequest hfOrderLogistics) throws Exception {
		HfOrdersDetail hfOrdersDetail = hfOrdersDetailMapper.selectByPrimaryKey(request.getId());
		if (hfOrdersDetail == null) {
			throw new OrderIsExistException(String.valueOf(request.getId()));
		}
		if (!StringUtils.isEmpty(request.getRespId())) {
			hfOrdersDetail.setRespId(request.getRespId());
		}
		if (!StringUtils.isEmpty(request.getPurchaseQuantity())) {
			for (Integer s : request.getPurchaseQuantity()) {
				hfOrdersDetail.setPurchaseQuantity(s);
			}
		}
		if (!StringUtils.isEmpty(request.getPurchasePrice())) {
			for (Integer s : request.getPurchasePrice()) {
				hfOrdersDetail.setPurchasePrice(s);
			}
		}
		if (!StringUtils.isEmpty(request.getOrderDetailStatus())) {
			hfOrdersDetail.setOrderDetailStatus(request.getOrderDetailStatus());
		}
		if (!StringUtils.isEmpty(request.getHfTax())) {
			hfOrdersDetail.setHfTax(request.getHfTax());
		}
		if (!StringUtils.isEmpty(request.getHfDesc())) {
			hfOrdersDetail.setHfDesc(request.getHfDesc());
		}
		if (!StringUtils.isEmpty(request.getGoogsId())) {
			for (Integer s : request.getGoogsId()) {
				hfOrdersDetail.setGoogsId(s);
			}
		}
		if (!StringUtils.isEmpty(request.getDistribution())) {
			hfOrdersDetail.setDistribution(request.getDistribution());
		}
		hfOrdersDetail.setModifyTime(LocalDateTime.now());
		hfOrdersDetail.setIsDeleted((short) 0);
		hfOrdersDetailMapper.updateByPrimaryKeySelective(hfOrdersDetail);
		HfOrders hfOrders = hfOrdersMapper.selectByPrimaryKey(request.getId());
		if (hfOrders == null) {
			throw new OrderIsExistException(String.valueOf(hfOrder.getId()));
		}
		if (!StringUtils.isEmpty(hfOrder.getUserId())) {
			hfOrders.setUserId(hfOrder.getUserId());
		}
		if (!StringUtils.isEmpty(hfOrder.getPayStatus())) {
			hfOrders.setPayStatus(hfOrder.getPayStatus());
		}
		if (!StringUtils.isEmpty(hfOrder.getPayMethodType())) {
			hfOrders.setPayMethodType(hfOrder.getPayMethodType());
		}
		if (!StringUtils.isEmpty(hfOrder.getPayMethodName())) {
			hfOrders.setPayMethodName(hfOrder.getPayMethodName());
		}
		if (!StringUtils.isEmpty(hfOrder.getOrderType())) {
			hfOrders.setOrderType(hfOrder.getOrderType());
		}
		if (!StringUtils.isEmpty(hfOrder.getHfRemark())) {
			hfOrders.setHfRemark(hfOrder.getHfRemark());
		}
		if (!StringUtils.isEmpty(hfOrder.getHfMemo())) {
			hfOrders.setHfMemo(hfOrder.getHfMemo());
		}
		if (!StringUtils.isEmpty(hfOrder.getAmount())) {
			hfOrders.setAmount(hfOrder.getAmount());
		}
		hfOrders.setModifyTime(LocalDateTime.now());
		hfOrders.setIsDeleted((short) 0);
		hfOrdersMapper.updateByPrimaryKeySelective(hfOrders);
		HfOrderLogistics hfOrderLogistic = hfOrderLogisticsMapper.selectByPrimaryKey(request.getId());
		if (hfOrderLogistic == null) {
			throw new OrderIsExistException(String.valueOf(hfOrderLogistics.getId()));
		}
		if (!StringUtils.isEmpty(hfOrder.getUserId())) {
			hfOrderLogistic.setUserId(hfOrder.getUserId());
		}
		if (!StringUtils.isEmpty(hfOrderLogistics.getUserAddressId())) {
			hfOrderLogistic.setUserAddressId(hfOrderLogistics.getUserAddressId());
		}
		if (!StringUtils.isEmpty(hfOrderLogistics.getRespId())) {
			hfOrderLogistic.setRespId(hfOrderLogistics.getRespId());
		}
		if (!StringUtils.isEmpty(hfOrderLogistics.getOrdersId())) {
			hfOrderLogistic.setOrdersId(hfOrderLogistics.getOrdersId());
		}
		if (!StringUtils.isEmpty(hfOrderLogistics.getOrderDetailId())) {
			hfOrderLogistic.setOrderDetailId(hfOrderLogistics.getOrderDetailId());
		}
		if (!StringUtils.isEmpty(hfOrderLogistics.getLogisticsOrdersId())) {
			hfOrderLogistic.setLogisticsOrdersId(hfOrderLogistics.getLogisticsOrdersId());
		}
		if (!StringUtils.isEmpty(hfOrderLogistics.getLogisticsOrderName())) {
			hfOrderLogistic.setLogisticsOrderName(hfOrderLogistics.getLogisticsOrderName());
		}
		if (!StringUtils.isEmpty(hfOrderLogistics.getLogisticsCompany())) {
			hfOrderLogistic.setLogisticsCompany(hfOrderLogistics.getLogisticsCompany());
		}
		if (!StringUtils.isEmpty(hfOrderLogistics.getHfDesc())) {
			hfOrderLogistic.setHfDesc(request.getHfDesc());
		}
		if (!StringUtils.isEmpty(hfOrderLogistics.getGoogsId())) {
			for (Integer s : hfOrderLogistics.getGoogsId()) {
				hfOrderLogistic.setGoogsId(s);
			}
		}
		hfOrderLogistic.setModifyTime(LocalDateTime.now());
		hfOrderLogistic.setIsDeleted((short) 0);
		hfOrderLogisticsMapper.updateByPrimaryKeySelective(hfOrderLogistic);
		List list = new ArrayList<>();
		list.add(hfOrdersDetail);
		list.add(hfOrderLogistic);
		list.add(hfOrders);
		return list;
	}
}
