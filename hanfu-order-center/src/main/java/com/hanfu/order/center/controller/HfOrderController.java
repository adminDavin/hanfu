package com.hanfu.order.center.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.curator.shaded.com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSONObject;
import com.hanfu.order.center.dao.HfOrderDetailMapper;
import com.hanfu.order.center.dao.HfOrderMapper;
import com.hanfu.order.center.manual.model.HfOrderDisplay;
import com.hanfu.order.center.model.HfOrder;
import com.hanfu.order.center.model.HfOrderDetail;
import com.hanfu.order.center.model.HfOrderDetailExample;
import com.hanfu.order.center.model.HfOrderExample;
import com.hanfu.order.center.request.CreateHfOrderRequest;
import com.hanfu.order.center.request.CreateHfOrderRequest.OrderStatus;
import com.hanfu.order.center.request.CreateHfOrderRequest.PaymentStatus;
import com.hanfu.order.center.request.CreateHfOrderRequest.PaymentType;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseUtils;
import com.hanfu.utils.response.handler.ResponseEntity.BodyBuilder;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
@RequestMapping("/hf-order")
@Api
public class HfOrderController {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private HfOrderMapper hfOrderMapper;

    @Autowired
    private HfOrderDetailMapper hfOrderDetailMapper;

    @ApiOperation(value = "创建订单", notes = "创建订单")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> creatOrder(CreateHfOrderRequest request) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        System.out.println(request);
        LocalDateTime time = LocalDateTime.now();
        HfOrder hfOrder = new HfOrder();
        hfOrder.setAmount(request.getAmount());
        hfOrder.setCreateTime(time);
        hfOrder.setHfRemark(request.getHfRemark());
        hfOrder.setLastModifier(String.valueOf(request.getUserId()));
        hfOrder.setModifyTime(time);
        hfOrder.setOrderStatus(OrderStatus.PAYMENT.getOrderStatus());
        hfOrder.setOrderCode(UUID.randomUUID().toString().replaceAll("-", ""));
        hfOrder.setOrderType(request.getOrderType());
        hfOrder.setPaymentName(request.getPaymentName());
        hfOrder.setPaymentType(PaymentType.getPaymentTypeEnum(request.getPaymentName()).getPaymentType());
        hfOrder.setPayStatus(PaymentStatus.UNPAID.getPaymentStatus());
        hfOrder.setUserId(request.getUserId());
        hfOrderMapper.insertSelective(hfOrder);

        HfOrderDetail detail = new HfOrderDetail();
        detail.setActualPrice(request.getActualPrice());
        detail.setCreateTime(time);
        detail.setGoodsId(request.getGoodsId());
        detail.setFreight(request.getFreight());
        detail.setHfDesc(request.getHfDesc());
        detail.setHfStatus(OrderStatus.PAYMENT.getOrderStatus());
        detail.setLastModifier(String.valueOf(request.getUserId()));
        detail.setModifyTime(time);
        detail.setOrderId(hfOrder.getId());
        detail.setQuantity(request.getQuantity());
        detail.setSellPrice(request.getSellPrice());
        detail.setTakingType(request.getTakingType());
        hfOrderDetailMapper.insertSelective(detail);
        
        
        return builder.body(ResponseUtils.getResponseBody(hfOrder));
    }

    @ApiOperation(value = "订单查询", notes = "订单查询")
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "orderStatus", value = "订单状态", required = true,
                    type = "String"),
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户Id", required = true,
                    type = "Integer") })
    public ResponseEntity<JSONObject> queryOrder(String orderStatus, Integer userId) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        OrderStatus orderStatusEnum = OrderStatus.getOrderStatusEnum(orderStatus);
        HfOrderExample example = new HfOrderExample();
        if (orderStatusEnum.equals(OrderStatus.ALL)) {
            example.createCriteria().andUserIdEqualTo(userId);
        } else {
            example.createCriteria().andUserIdEqualTo(userId).andOrderStatusEqualTo(orderStatusEnum.getOrderStatus());
        }

        List<HfOrder> hfOrders = hfOrderMapper.selectByExample(example);
        List<Integer> hfOrderIds = hfOrders.stream().map(HfOrder::getId).collect(Collectors.toList());

        HfOrderDetailExample detailExample = new HfOrderDetailExample();
        detailExample.createCriteria().andOrderIdIn(hfOrderIds);

        return builder.body(ResponseUtils.getResponseBody(hfOrders));
    }

}
