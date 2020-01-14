package com.hanfu.order.center.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSONObject;
import com.hanfu.order.center.dao.HfOrderLogisticsMapper;
import com.hanfu.order.center.dao.HfOrderStatusMapper;
import com.hanfu.order.center.dao.HfOrdersDetailMapper;
import com.hanfu.order.center.dao.HfOrdersMapper;
import com.hanfu.order.center.manual.dao.OrderDao;
import com.hanfu.order.center.manual.model.OrderFindValue;
import com.hanfu.order.center.manual.model.OrdersInfo;
import com.hanfu.order.center.model.HfOrderLogistics;
import com.hanfu.order.center.model.HfOrderStatus;
import com.hanfu.order.center.model.HfOrderStatusExample;
import com.hanfu.order.center.model.HfOrders;
import com.hanfu.order.center.model.HfOrdersDetail;
import com.hanfu.order.center.model.HfOrdersDetailExample;
import com.hanfu.order.center.request.HfOrderLogisticsRequest;
import com.hanfu.order.center.request.HfOrdersDetailRequest;
import com.hanfu.order.center.request.HfOrdersRequest;
import com.hanfu.order.center.request.MyPrint;
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
	@Autowired
	HfOrderStatusMapper hfOrderStatusMapper;

	@ApiOperation(value = "查询订单", notes = "查询订单")
	@RequestMapping(value = "/query", method = RequestMethod.GET)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "id", value = "id", required = false, type = "Integer")})
	public ResponseEntity<JSONObject> query()
			throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		return builder.body(ResponseUtils.getResponseBody(orderDao.selectOrderList()));
	}

	@ApiOperation(value = "根据用户Id查询订单", notes = "根据用户Id查询订单")
	@RequestMapping(value = "/queryByUserid", method = RequestMethod.GET)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "userId", value = "用户Id", required = true, type = "Integer")})
	public ResponseEntity<JSONObject> queryByUserId(Integer userId)
			throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		return builder.body(ResponseUtils.getResponseBody(orderDao.selectOrderByUserId(userId)));
	}

	@ApiOperation(value = "创建订单", notes = "创建订单")
	@RequestMapping(value = "/creat", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> creatOrder(OrdersInfo ordersInfo)
			throws JSONException {
		Integer amount = 0;
		Integer amount1 = 0;
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		HfOrders hfOrders = new HfOrders();
		HfOrdersDetail hfOrdersDetail = new HfOrdersDetail();
		HfOrderLogistics hfOrderLogistics = new HfOrderLogistics();
		hfOrders.setUserId(ordersInfo.getUserId());
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
					amount1 = purchasePrice*purchaseQuantity;
					amount = amount1+amount;
					hfOrders.setAmount(amount);
					hfOrdersMapper.updateByPrimaryKeySelective(hfOrders);
					hfOrderLogistics.setGoogsId(googsId);
					hfOrderLogistics.setLogisticsCompany(ordersInfo.getLogisticsCompany());
					hfOrderLogistics.setLogisticsOrderName(ordersInfo.getLogisticsOrderName());
					hfOrderLogistics.setLastModifier("");
					hfOrderLogistics.setModifyTime(LocalDateTime.now());
					hfOrderLogistics.setCreateTime(LocalDateTime.now());
					hfOrderLogistics.setIsDeleted((short) 0 );
					hfOrderLogistics.setOrderDetailId(hfOrdersDetail.getId());
					hfOrderLogistics.setOrdersId(hfOrders.getId());
					hfOrderLogistics.setUserId(ordersInfo.getUserId());
					hfOrderLogistics.setUserAddressId(ordersInfo.getUserAddressId());
					hfOrderLogisticsMapper.insert(hfOrderLogistics);
				}
			}
		}
		return builder.body(ResponseUtils.getResponseBody(orderDao.selectOrders(hfOrders.getId())));
	}

	@ApiOperation(value = "获取订单状态", notes = "获取订单状态")
	@RequestMapping(value = "/status", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> status()
			throws JSONException, Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		return builder.body(ResponseUtils.getResponseBody(orderDao.selectOrderStatus()));
	}

	@ApiOperation(value = "修改订单状态", notes = "修改订单状态")
	@RequestMapping(value = "/updatestatus", method = RequestMethod.POST)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "orderId", value = "订单id", required = true, type = "Integer"),
		@ApiImplicitParam(paramType = "query", name = "id", value = "状态id", required = true, type = "Integer")
	})
	public ResponseEntity<JSONObject> deleteOrder(@RequestParam Integer orderId, @RequestParam Integer id)
			throws JSONException, Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		HfOrdersDetail hfOrdersDetail = hfOrdersDetailMapper.selectByPrimaryKey(orderId);
		HfOrderStatus hfOrderStatus = hfOrderStatusMapper.selectByPrimaryKey(id);
		hfOrdersDetail.setOrderDetailStatus(hfOrderStatus.getHfName());
		return builder.body(ResponseUtils.getResponseBody(hfOrdersDetailMapper.updateByPrimaryKey(hfOrdersDetail)));
	}

	@SuppressWarnings("rawtypes")
	@ApiOperation(value = "修改订单", notes = "修改订单")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "id", value = "id", required = true, type = "Integer")})
	public ResponseEntity<JSONObject> updateOrder(HfOrdersDetailRequest request, HfOrdersRequest hfOrder, HfOrderLogisticsRequest hfOrderLogistics)
			throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		List list = hfOrdersService.updateOrder(request, hfOrder, hfOrderLogistics);
		return builder.body(ResponseUtils.getResponseBody(list));
	}

	@ApiOperation(value = "获取订单详情", notes = "获取订单详情")
	@RequestMapping(value = "/queryDetail", method = RequestMethod.GET)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "id", value = "订单id", required = true, type = "Integer")})
	public ResponseEntity<JSONObject> queryOrderList(@RequestParam Integer id)
			throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		return builder.body(ResponseUtils.getResponseBody(orderDao.selectOrderDetail(id)));
	}

	@ApiOperation(value = "打印订单", notes = "打印订单")
	@RequestMapping(value = "/print", method = RequestMethod.GET)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "id", value = "订单id", required = true, type = "Integer")})
	public ResponseEntity<JSONObject> printOrder(@RequestParam Integer id)
			throws Exception {
		MyPrint myPrint = new MyPrint();
		myPrint.setTotalPageCount(2);
		myPrint.doPrint(myPrint);
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		return builder.body(ResponseUtils.getResponseBody("打印成功"));
	}

	@ApiOperation(value = "根据条件查询订单", notes = "根据条件查询订单")
	@RequestMapping(value = "/queryOrder", method = RequestMethod.GET)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "orderId", value = "订单id", required = false, type = "Integer"),
		@ApiImplicitParam(paramType = "query", name = "hfName", value = "商品名称", required = false, type = "String"),
		@ApiImplicitParam(paramType = "query", name = "payMethodType", value = "支付方式", required = false, type = "String"),
		@ApiImplicitParam(paramType = "query", name = "orderDetailStatus", value = "订单状态", required = false, type = "String"),
		@ApiImplicitParam(paramType = "query", name = "orderDetailId", value = "订单详情Id", required = false, type = "Integer"),
		@ApiImplicitParam(paramType = "query", name = "userId", value = "用户Id", required = false, type = "Integer"),
	})
	public ResponseEntity<JSONObject> queryOrder(OrderFindValue orderFindValue)
			throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		return builder.body(ResponseUtils.getResponseBody(orderDao.selectOrder(orderFindValue)));
	}
	@ApiOperation(value = "申请退款", notes = "申请退款")
	@RequestMapping(value = "/forDrawback", method = RequestMethod.GET)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "orderId", value = "订单id", required = false, type = "Integer"),
	})
	public ResponseEntity<JSONObject> forDrawback(Integer orderId)
			throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		HfOrdersDetailExample hfOrdersDetailExample = new HfOrdersDetailExample();
		hfOrdersDetailExample.createCriteria().andOrdersIdNotEqualTo(orderId);
		if(StringUtils.isEmpty(hfOrdersDetailMapper.selectByPrimaryKey(orderId))) {
			return builder.body(ResponseUtils.getResponseBody("订单不存在"));
		}
		HfOrdersDetail hfOrderDetail = new HfOrdersDetail();
		hfOrderDetail.setOrderDetailStatus("退款中");
		hfOrdersDetailMapper.updateByPrimaryKeySelective(hfOrderDetail);
		return builder.body(ResponseUtils.getResponseBody("修改订单状态"));
	} 
	@ApiOperation(value = "填写物流信息", notes = "填写物流信息")
	@RequestMapping(value = "/insertLogistics", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> insertLogistics(HfOrderLogisticsRequest request)
			throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		HfOrderLogistics hfOrderLogistics = new HfOrderLogistics();
		for (Integer goodsId : request.getGoogsId()) {
			hfOrderLogistics.setGoogsId(goodsId);
			hfOrderLogistics.setLogisticsCompany(request.getLogisticsCompany());
			hfOrderLogistics.setLogisticsOrderName(request.getLogisticsOrderName());
			hfOrderLogistics.setLastModifier("");
			hfOrderLogistics.setModifyTime(LocalDateTime.now());
			hfOrderLogistics.setCreateTime(LocalDateTime.now());
			hfOrderLogistics.setIsDeleted((short) 0 );
			hfOrderLogistics.setOrderDetailId(request.getOrderDetailId());
			hfOrderLogistics.setOrdersId(request.getOrdersId());
			hfOrderLogistics.setUserId(request.getUserId());
			hfOrderLogistics.setUserAddressId(request.getUserAddressId());
		}
		return builder.body(ResponseUtils.getResponseBody(hfOrderLogisticsMapper.insert(hfOrderLogistics)));
	}
}
