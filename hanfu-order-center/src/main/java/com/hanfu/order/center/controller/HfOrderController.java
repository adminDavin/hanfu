package com.hanfu.order.center.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


import com.hanfu.order.center.dao.*;
import com.hanfu.order.center.manual.model.payment;
import com.hanfu.order.center.model.*;
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
import com.hanfu.order.center.manual.dao.HfOrderDao;
import com.hanfu.order.center.manual.model.HfGoodsDisplay;
import com.hanfu.order.center.manual.model.HfOrderDisplay;
import com.hanfu.order.center.manual.model.HfOrderStatistics;
import com.hanfu.order.center.request.CreateHfOrderRequest;
import com.hanfu.order.center.request.CreateHfOrderRequest.OrderStatus;
import com.hanfu.order.center.request.CreateHfOrderRequest.OrderTypeEnum;
import com.hanfu.order.center.request.CreateHfOrderRequest.PaymentStatus;
import com.hanfu.order.center.request.CreateHfOrderRequest.PaymentType;
import com.hanfu.order.center.request.CreateHfOrderRequest.TakingTypeEnum;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseUtils;
import com.hanfu.utils.response.handler.ResponseEntity.BodyBuilder;
import org.springframework.web.client.RestTemplate;

@CrossOrigin
@RestController
@RequestMapping("/hf-order")
@Api
public class HfOrderController {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final String REST_URL_PREFIX = "https://www.tjsichuang.cn:1443/api/cart/";
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HfOrderMapper hfOrderMapper;

    @Autowired
    private HfOrderDao hfOrderDao;
    
    @Autowired
    private HfOrderDetailMapper hfOrderDetailMapper;

    @Autowired
    private HfActivityGroupMapper hfActivityGroupMapper;

    @Autowired
    private HfActivityCountMapper hfActivityCountMapper;

    @Autowired
    private HfActivityProductMapper hfActivityProductMapper;

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
            @ApiImplicitParam(paramType = "query", name = "orderStatus", value = "订单状态", required = false,
                    type = "String"),
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户Id", required = false,
                    type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "orderCode", value = "订单号", required = false,
                    type = "Integer")})
    public ResponseEntity<JSONObject> queryOrder(String orderStatus, Integer userId, String orderType,String orderCode) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        OrderStatus orderStatusEnum = OrderStatus.getOrderStatusEnum(orderStatus);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", userId);
        params.put("orderStatus", orderStatusEnum.getOrderStatus());
        params.put("orderType", orderType);
        params.put("orderCode",orderCode);
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
                //活动判断
                HfActivityCountExample hfActivityCountExample = new HfActivityCountExample();
                hfActivityCountExample.createCriteria().andIsDeletedEqualTo((byte) 0).andOrderIdEqualTo(hfOrder.getId());
                List<HfActivityCount> hfActivityCount= hfActivityCountMapper.selectByExample(hfActivityCountExample);
                if (hfActivityCount.size()!=0){
                    //查询拼团人数
                    HfActivityCountExample hfActivityCountExample1 = new HfActivityCountExample();
                    hfActivityCountExample1.createCriteria().andGroupIdEqualTo(hfActivityCount.get(0).getGroupId()).andIsDeletedEqualTo((byte) 0);
                    List<HfActivityCount> hfActivityCount1= hfActivityCountMapper.selectByExample(hfActivityCountExample1);
                    //查询总拼团人数
                    HfActivityGroup hfActivityGroup= hfActivityGroupMapper.selectByPrimaryKey(hfActivityCount1.get(0).getGroupId());
                    HfActivityProductExample hfActivityProductExample = new HfActivityProductExample();
                    hfActivityProductExample.createCriteria().andActivityIdEqualTo(hfActivityGroup.getActivityId()).andProductIdEqualTo(hfActivityGroup.getProductId());
                    List<HfActivityProduct> hfActivityProduct = hfActivityProductMapper.selectByExample(hfActivityProductExample);
                    hfOrder.setActivityState(hfActivityCount.get(0).getState());
                    hfOrder.setNowSum(hfActivityProduct.get(0).getGroupNum());
                    hfOrder.setGroupSum(hfActivityCount1.size());
                    hfOrder.setActivity("group");

                    Date date1 = new Date();
                    Date date2 = new Date();
                    SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    try {
                        date1 = f.parse(f.format(new Date())); //这是获取当前时间
                        date2 = f.parse(f.format(hfActivityGroup.getClusteringTime()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    hfOrder.setActivityTime(86400000 - (date1.getTime() - date2.getTime()));
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
        if (targetOrderStatus.equals("transport")){
            HfActivityCountExample hfActivityCountExample = new HfActivityCountExample();
            hfActivityCountExample.createCriteria().andOrderIdEqualTo(Id).andIsDeletedEqualTo((byte) 0);
            List<HfActivityCount> hfActivityCountList= hfActivityCountMapper.selectByExample(hfActivityCountExample);
            if (hfActivityCountList.size()!=0){
                if (hfActivityCountList.get(0).getState()!=3){
                    return builder.body(ResponseUtils.getResponseBody("In spelling"));
                }
            }
        }
        if (targetOrderStatus.equals("cancel")&&originOrderStatus.equals("process")||originOrderStatus.equals("complete")||originOrderStatus.equals("transport")){
            HfOrderExample hfOrderExample1 = new HfOrderExample();
            hfOrderExample1.createCriteria().andIdEqualTo(Id).andOrderCodeEqualTo(orderCode).andOrderStatusEqualTo(originOrderStatus);

            payment payment = new payment();
            payment.setOutTradeNo(orderCode);
            payment.setUserId(hfOrderMapper.selectByExample(hfOrderExample1).get(0).getUserId());
//            Map map = (Map) payment;
            restTemplate.getForEntity(REST_URL_PREFIX+"/hf-payment/refund/?outTradeNo={outTradeNo}&userId={userId}",payment.class,orderCode,hfOrderMapper.selectByExample(hfOrderExample1).get(0).getUserId());
        }
        HfOrder hfOrder = new HfOrder();
        hfOrder.setId(Id);
        hfOrder.setOrderStatus(targetOrderStatus);
        HfOrderExample hfOrderExample = new HfOrderExample();
        hfOrderExample.createCriteria().andIdEqualTo(Id).andOrderCodeEqualTo(orderCode).andOrderStatusEqualTo(originOrderStatus);
        hfOrderMapper.updateByExampleSelective(hfOrder,hfOrderExample);
        HfOrderDetail hfOrderDetail = new HfOrderDetail();
        hfOrderDetail.setHfStatus(targetOrderStatus);
        HfOrderDetailExample hfOrderDetailExample = new HfOrderDetailExample();
        hfOrderDetailExample.createCriteria().andOrderIdEqualTo(Id);
        hfOrderDetailMapper.updateByExampleSelective(hfOrderDetail,hfOrderDetailExample);
        return builder.body(ResponseUtils.getResponseBody("0"));
    }

}
