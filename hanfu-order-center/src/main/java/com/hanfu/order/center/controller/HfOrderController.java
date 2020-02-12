package com.hanfu.order.center.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


import com.hanfu.order.center.model.HfOrderDetailExample;
import com.hanfu.order.center.model.HfOrderExample;
import io.swagger.annotations.*;
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
import com.hanfu.order.center.manual.dao.HfOrderDao;
import com.hanfu.order.center.manual.model.HfGoodsDisplay;
import com.hanfu.order.center.manual.model.HfOrderDisplay;
import com.hanfu.order.center.manual.model.HfOrderStatistics;
import com.hanfu.order.center.model.HfOrder;
import com.hanfu.order.center.model.HfOrderDetail;
import com.hanfu.order.center.request.CreateHfOrderRequest;
import com.hanfu.order.center.request.CreateHfOrderRequest.OrderStatus;
import com.hanfu.order.center.request.CreateHfOrderRequest.OrderTypeEnum;
import com.hanfu.order.center.request.CreateHfOrderRequest.PaymentStatus;
import com.hanfu.order.center.request.CreateHfOrderRequest.PaymentType;
import com.hanfu.order.center.request.CreateHfOrderRequest.TakingTypeEnum;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseUtils;
import com.hanfu.utils.response.handler.ResponseEntity.BodyBuilder;

@CrossOrigin
@RestController
@RequestMapping("/hf-order")
@Api
public class HfOrderController {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private HfOrderMapper hfOrderMapper;

    @Autowired
    private HfOrderDao hfOrderDao;
    
    @Autowired
    private HfOrderDetailMapper hfOrderDetailMapper;

    @ApiOperation(value = "创建订单", notes = "创建订单")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> creatOrder(CreateHfOrderRequest request) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        LocalDateTime time = LocalDateTime.now();
        HfOrder hfOrder = new HfOrder();
        hfOrder.setCreateTime(time);
        hfOrder.setModifyTime(time);
        
        hfOrder.setAmount(request.getAmount());
        hfOrder.setHfRemark(request.getHfRemark());        
        hfOrder.setUserId(request.getUserId());
        hfOrder.setOrderType(request.getOrderType());
        hfOrder.setPaymentName(request.getPaymentName());
        hfOrder.setStoneId(request.getStoneId());
        hfOrder.setDistributorId(request.getDistributorId());
        hfOrder.setOrderCode(UUID.randomUUID().toString().replaceAll("-", ""));
        hfOrder.setLastModifier(String.valueOf(hfOrder.getUserId()));
        Integer paymentType = PaymentType.getPaymentTypeEnum(hfOrder.getPaymentName()).getPaymentType();
        hfOrder.setPaymentType(paymentType);
        hfOrder.setOrderStatus(OrderStatus.PAYMENT.getOrderStatus());
        hfOrder.setPayStatus(PaymentStatus.UNPAID.getPaymentStatus());
        
        hfOrderMapper.insertSelective(hfOrder); 
        if (OrderTypeEnum.NOMAL_ORDER.getOrderType().equals(hfOrder.getOrderType())) {  
            handleNomalOrder(request, hfOrder);
        }
        return builder.body(ResponseUtils.getResponseBody(hfOrder));
    }

    private void handleNomalOrder(CreateHfOrderRequest request, HfOrder hfOrder) {
        LocalDateTime time = LocalDateTime.now();

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
        detail.setTakingType(TakingTypeEnum.getTakingTypeEnum(request.getTakingType()).getTakingType());
        hfOrderDetailMapper.insertSelective(detail);
        if (java.util.Optional.ofNullable(request.getUserAddressId()).isPresent()) {
            if (TakingTypeEnum.getTakingTypeEnum(request.getTakingType()).equals(TakingTypeEnum.DELIVERY)) {
                hfOrderDao.insertOrderAddress(request.getUserAddressId(), hfOrder.getId());   
            }
        }

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
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", userId);
        params.put("orderStatus", orderStatusEnum.getOrderStatus());
        List<HfOrderDisplay> hfOrders = hfOrderDao.selectHfOrder(params);
        if (!hfOrders.isEmpty()) {
            Set<Integer> goodsIds = hfOrders.stream().map(HfOrderDisplay::getGoodsId).collect(Collectors.toSet());
            List<HfGoodsDisplay> goodses = hfOrderDao.selectGoodsInfo(goodsIds);
            
            Map<Integer, HfGoodsDisplay> hfGoodsDisplayMap = goodses.stream().collect(Collectors.toMap(HfGoodsDisplay::getId, apple1 -> apple1));
            
            hfOrders.forEach(hfOrder -> {
                HfGoodsDisplay goods = hfGoodsDisplayMap.get(hfOrder.getGoodsId());
                if (java.util.Optional.ofNullable(goods).isPresent()) {
                    hfOrder.setGoodsName(goods.getHfName());
                    hfOrder.setStoneName(goods.getStoneName());
                    hfOrder.setFileId(goods.getFileId());
                }
            });
        }
        
        
        return builder.body(ResponseUtils.getResponseBody(hfOrders));
    }

    @ApiOperation(value = "订单统计", notes = "订单查询")
    @RequestMapping(value = "/statistics", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "orderStatus", value = "订单状态", required = true,
                    type = "String")})
    public ResponseEntity<JSONObject> statistics(Integer userId) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        List<HfOrderStatistics> hfOrderStatus = hfOrderDao.selectHfOrderStatistics(userId);
        return builder.body(ResponseUtils.getResponseBody(hfOrderStatus));
    }

    @ApiOperation(value = "修改订单状态", notes = "修改订单状态")
    @RequestMapping(value = "/modifyStatus", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "Id", value = "订单id", required = true,
                    type = "Integer")})
    public ResponseEntity<JSONObject> updateStatus(Integer Id,String orderCode,String originOrderStatus,String targetOrderStatus) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HfOrder hfOrder = new HfOrder();
        hfOrder.setId(Id);
        hfOrder.setOrderStatus(targetOrderStatus);
        HfOrderExample hfOrderExample = new HfOrderExample();
        hfOrderExample.createCriteria().andIdEqualTo(Id).andOrderCodeEqualTo(orderCode).andOrderStatusEqualTo(originOrderStatus);
        hfOrderMapper.updateByExampleSelective(hfOrder,hfOrderExample);
        HfOrderDetail hfOrderDetail = new HfOrderDetail();
        hfOrderDetail.setId(Id);
        hfOrderDetail.setHfStatus(targetOrderStatus);
        HfOrderDetailExample hfOrderDetailExample = new HfOrderDetailExample();
        hfOrderDetailExample.createCriteria().andOrderIdEqualTo(Id);
        hfOrderDetailMapper.updateByExample(hfOrderDetail,hfOrderDetailExample);
        return builder.body(ResponseUtils.getResponseBody("0"));
    }

}
