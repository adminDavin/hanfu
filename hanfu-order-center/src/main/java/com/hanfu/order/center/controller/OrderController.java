package com.hanfu.order.center.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.hanfu.order.center.dao.HfOrderLogisticsMapper;
import com.hanfu.order.center.dao.HfOrdersDetailMapper;
import com.hanfu.order.center.dao.HfOrdersMapper;
import com.hanfu.order.center.manual.dao.OrderDao;
import com.hanfu.order.center.model.HfOrdersDetail;
import com.hanfu.order.center.request.HfOrderLogisticsRequest;
import com.hanfu.order.center.request.HfOrdersDetailRequest;
import com.hanfu.order.center.request.HfOrdersRequest;
import com.hanfu.order.center.service.HfOrdersService;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseUtils;
import com.hanfu.utils.response.handler.ResponseEntity.BodyBuilder;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
@CrossOrigin
@RestController
@RequestMapping("/order")
@Api
public class OrderController {
	
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	HfOrdersDetailMapper hfOrdersDetailMapper;
	@Autowired
	HfOrderLogisticsMapper hfOrderLogisticsMapper;
	@Autowired
	HfOrdersMapper hfOrdersMapper;
	@Autowired
	HfOrdersService hfOrdersService;
	@Autowired
	OrderDao orderDao;
	@ApiOperation(value = "查询订单", notes = "查询订单")
	@RequestMapping(value = "/query", method = RequestMethod.GET)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "id", value = "物流id", required = true, type = "Integer") })
	public ResponseEntity<JSONObject> query(@RequestParam Integer id)
			throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		return builder.body(ResponseUtils.getResponseBody(orderDao.selectOrderList(id)));
	}
	@ApiOperation(value = "创建订单", notes = "创建订单")
	@RequestMapping(value = "/creat", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> creatOrder(HfOrdersDetailRequest request,HfOrdersRequest hfOrder,HfOrderLogisticsRequest hfOrderLogistics)
			throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		List  list = hfOrdersService.creatOrder(request,hfOrder,hfOrderLogistics);
		return builder.body(ResponseUtils.getResponseBody(list));
	}
	@ApiOperation(value = "修改订单状态", notes = "修改订单状态")
	@RequestMapping(value = "/updatestatus", method = RequestMethod.POST)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "id", value = "订单id", required = true, type = "Integer"),
		@ApiImplicitParam(paramType = "query", name = "orderDetailStatus", value = "订单状态：1.待支付，2.待发货 ，3.待收货，4.待评价，5.待接单，6.已接单，7.制作中", required = true, type = "String")
		})
	public ResponseEntity<JSONObject> deleteOrder(@RequestParam Integer id,  @RequestParam String orderDetailStatus)
			throws JSONException, Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		HfOrdersDetail hfOrdersDetail = hfOrdersDetailMapper.selectByPrimaryKey(id);
//		if(!StringUtils.isEmpty(hfOrdersDetail == null)) {
//			throw new OrderIsExistException(String.valueOf(hfOrdersDetail.getId()));	
//		}
		if(!StringUtils.isEmpty(hfOrdersDetail.getOrderDetailStatus())) {
			hfOrdersDetail.setOrderDetailStatus(orderDetailStatus);
		}
		return builder.body(ResponseUtils.getResponseBody(hfOrdersDetailMapper.updateByPrimaryKey(hfOrdersDetail)));
	}
	@ApiOperation(value = "修改订单", notes = "修改订单")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "id", value = "id", required = true, type = "Integer") })
	public ResponseEntity<JSONObject> updateOrder(HfOrdersDetailRequest request, HfOrdersRequest hfOrder, HfOrderLogisticsRequest hfOrderLogistics)
			throws Exception{
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		List list = hfOrdersService.updateOrder(request,hfOrder,hfOrderLogistics);
		return builder.body(ResponseUtils.getResponseBody(list));
	}
}
