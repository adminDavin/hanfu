package com.hanfu.order.center.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.hanfu.order.center.cancel.dao.HfGoodsMapper;
import com.hanfu.order.center.cancel.dao.ProductMapper;
import com.hanfu.order.center.cancel.model.Product;
import com.hanfu.order.center.dao.*;
import com.hanfu.order.center.manual.model.*;
import com.hanfu.order.center.model.*;
import com.hanfu.payment.center.request.HfStoneRequest;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import com.hanfu.order.center.manual.dao.HfOrderDao;
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
import org.springframework.web.context.request.WebRequest;

import javax.annotation.Resource;

@CrossOrigin
@RestController
@RequestMapping("/hf-order")
@Api
public class HfOrderController {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final String REST_URL_PREFIX = "https://www.tjsichuang.cn:1443/api/cart/";
    private static final String REST_URL_CHECK = "https://www.tjsichuang.cn:1443/api/product/";
    @Autowired
    private RestTemplate restTemplate;
    @Value("${myspcloud.item.url}")
    private String itemUrl;
    @Value("${myspcloud.item1.url1}")
    private String itemUrl1;
    @Value("${myspcloud.item2.url2}")
    private String itemUrl2;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

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

    @Autowired
    private HfGoodMapper hfGoodMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private HfPriceMappers hfPriceMappers;
    @Autowired
    private HfRespMapper hfRespMapper;
    @Autowired
    private HfStoneMapper hfStoneMapper;
    @Autowired
    private DiscountCouponOrderMapper discountCouponOrderMapper;
    @Autowired
    private HfRequestIdMapper hfRequestIdMapper;

    @ApiOperation(value = "创建订单", notes = "创建订单")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> creatOrder(CreateHfOrderRequest request) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HfRequestIdExample idExample = new HfRequestIdExample();
        idExample.createCriteria().andRequestIdEqualTo(request.getRequestId());
        if(!hfRequestIdMapper.selectByExample(idExample).isEmpty()) {
        	return builder.body(ResponseUtils.getResponseBody(-1));
        }else {
        	HfRequestId id = new HfRequestId();
        	id.setRequestId(request.getRequestId());
        	id.setCreateTime(LocalDateTime.now());
        	id.setModifTime(LocalDateTime.now());
        	id.setIsDeleted((byte) 0);
        	hfRequestIdMapper.insert(id);
        }
        
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
    public ResponseEntity<JSONObject> queryOrder(String orderStatus, Integer userId, String orderType,String orderCode,String productName,
                                                 String paymentName,String today,String yesterday,String sevenDays,String month,
                                                 @RequestParam(value = "stateTime") Date stateTime,@RequestParam(value = "endTime") Date endTime) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        OrderStatus orderStatusEnum = OrderStatus.getOrderStatusEnum(orderStatus);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("stateTime",stateTime);
        params.put("endTime",endTime);
        params.put("userId", userId);
        params.put("orderStatus", orderStatusEnum.getOrderStatus());
        params.put("orderType", orderType);
        params.put("orderCode",orderCode);
//        params.put("productName",productName);
        params.put("paymentName",paymentName);
        params.put("today",today);
        params.put("yesterday",yesterday);
        params.put("sevenDays",sevenDays);
        params.put("months",month);
        List<HfOrderDisplay> hfOrders = hfOrderDao.selectHfOrder(params);
        if (!hfOrders.isEmpty()) {
//            Set<Integer> goodsIds = hfOrders.stream().map(HfOrderDisplay::getGoodsId).collect(Collectors.toSet());
//            List<HfGoodsDisplay> goodses = hfOrderDao.selectGoodsInfo(goodsIds);
            
//            Map<Integer, HfGoodsDisplay> hfGoodsDisplayMap = goodses.stream().collect(Collectors.toMap(HfGoodsDisplay::getId, apple1 -> apple1));
            
            hfOrders.forEach(hfOrder -> {
                HfOrderDetailExample hfOrderDetailExample = new HfOrderDetailExample();
                hfOrderDetailExample.createCriteria().andOrderIdEqualTo(hfOrder.getId());
                List<HfOrderDetail> hfOrderDetailList = hfOrderDetailMapper.selectByExample(hfOrderDetailExample);
//                hfOrder.setOrderDetailList(hfOrderDetailMapper.selectByExample(hfOrderDetailExample));
                Map<Integer, List<HfOrderDetail>> resultList = hfOrderDetailList.stream().collect(Collectors.groupingBy(HfOrderDetail::getStoneId));
                List<DetailRequest> detailRequest = new ArrayList<>();
                Set<Map.Entry<Integer, List<HfOrderDetail>>> set = resultList.entrySet();
                for(Map.Entry<Integer, List<HfOrderDetail>> entry:set) {
                    DetailRequest cartList = new DetailRequest();
                    cartList.setStoneId(entry.getKey());
                    HfStone hfStone= hfStoneMapper.selectByPrimaryKey(entry.getKey());
                    cartList.setStoneName(hfStone.getHfName());
                    cartList.setHfOrderDetailList(entry.getValue());
                    cartList.setDetailStatus(entry.getValue().get(0).getHfStatus());
                    cartList.setTakingType(entry.getValue().get(0).getTakingType());
                    cartList.setWuliu(String.valueOf(0));
                    detailRequest.add(cartList);
                }
                hfOrder.setDetailRequestList(detailRequest);
                hfOrderDetailList.forEach(hfOrderDetail -> {
                    if (hfOrderDetail.getTakingType()!=null){
                        hfOrder.setTakingType(hfOrderDetail.getTakingType());
                    }
                });

//                HfGoodsDisplay goods = hfGoodsDisplayMap.get(hfOrder.getGoodsId());
//                if (java.util.Optional.ofNullable(goods).isPresent()) {
//                    hfOrder.setGoodsName(goods.getHfName());
//                    hfOrder.setStoneName(goods.getStoneName());
//                    hfOrder.setFileId(goods.getFileId());
//                }
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
    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));// CustomDateEditor为自定义日期编辑器
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
    public ResponseEntity<JSONObject> updateStatus(Integer Id,String orderCode,String originOrderStatus,String targetOrderStatus,Integer stoneId) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        if (targetOrderStatus.equals("controversial")){
            redisTemplate.opsForValue().set(orderCode+"controversial", originOrderStatus);
            HfOrderDetail hfOrderDetail = new HfOrderDetail();
            hfOrderDetail.setHfStatus(targetOrderStatus);
            HfOrderDetailExample hfOrderDetailExample = new HfOrderDetailExample();
            hfOrderDetailExample.createCriteria().andOrderIdEqualTo(Id);
            hfOrderDetailMapper.updateByExampleSelective(hfOrderDetail,hfOrderDetailExample);
            HfOrder hfOrder = new HfOrder();
            hfOrder.setId(Id);
            hfOrder.setOrderStatus(targetOrderStatus);
            HfOrderExample hfOrderExample = new HfOrderExample();
            hfOrderExample.createCriteria().andIdEqualTo(Id).andOrderCodeEqualTo(orderCode).andOrderStatusEqualTo(originOrderStatus);
            hfOrderMapper.updateByExampleSelective(hfOrder,hfOrderExample);
        }
        //----
        if (targetOrderStatus.equals("process")){
            HfOrderDetail hfOrderDetail = new HfOrderDetail();
            hfOrderDetail.setHfStatus(targetOrderStatus);
            HfOrderDetailExample hfOrderDetailExample = new HfOrderDetailExample();
            hfOrderDetailExample.createCriteria().andOrderIdEqualTo(Id);
            hfOrderDetailMapper.updateByExampleSelective(hfOrderDetail,hfOrderDetailExample);
            HfOrder hfOrder = new HfOrder();
            hfOrder.setId(Id);
            hfOrder.setOrderStatus(targetOrderStatus);
            HfOrderExample hfOrderExample = new HfOrderExample();
            hfOrderExample.createCriteria().andIdEqualTo(Id).andOrderCodeEqualTo(orderCode).andOrderStatusEqualTo(originOrderStatus);
            hfOrderMapper.updateByExampleSelective(hfOrder,hfOrderExample);
        }
//---
        if (targetOrderStatus.equals("transport")||targetOrderStatus.equals("cancel")){
            HfActivityCountExample hfActivityCountExample = new HfActivityCountExample();
            hfActivityCountExample.createCriteria().andOrderIdEqualTo(Id).andIsDeletedEqualTo((byte) 0);
            List<HfActivityCount> hfActivityCountList= hfActivityCountMapper.selectByExample(hfActivityCountExample);
            if (hfActivityCountList.size()!=0){
                if (hfActivityCountList.get(0).getState()!=3){
                    return builder.body(ResponseUtils.getResponseBody("In spelling"));
                }
            } else if (targetOrderStatus.equals("transport")){
                HfOrderDetail hfOrderDetail = new HfOrderDetail();
                hfOrderDetail.setHfStatus(targetOrderStatus);
                HfOrderDetailExample hfOrderDetailExample = new HfOrderDetailExample();
                hfOrderDetailExample.createCriteria().andOrderIdEqualTo(Id).andStoneIdEqualTo(stoneId);
                hfOrderDetailMapper.updateByExampleSelective(hfOrderDetail,hfOrderDetailExample);
                HfOrderDetailExample hfOrderDetailExample1 = new HfOrderDetailExample();
                hfOrderDetailExample1.createCriteria().andOrderIdEqualTo(Id).andHfStatusNotEqualTo("transport").andHfStatusNotEqualTo("complete").andHfStatusNotEqualTo("evaluate");
                List<HfOrderDetail> hfOrderDetail1= hfOrderDetailMapper.selectByExample(hfOrderDetailExample1);
                if (hfOrderDetail1.size()==0){
                    HfOrder hfOrder = new HfOrder();
                    hfOrder.setId(Id);
                    hfOrder.setOrderStatus(targetOrderStatus);
                    HfOrderExample hfOrderExample = new HfOrderExample();
                    hfOrderExample.createCriteria().andIdEqualTo(Id).andOrderCodeEqualTo(orderCode).andOrderStatusEqualTo(originOrderStatus);
                    hfOrderMapper.updateByExampleSelective(hfOrder,hfOrderExample);
                }
                return builder.body(ResponseUtils.getResponseBody("0"));
            }
        }
        //-----cancel
        if (targetOrderStatus.equals("cancel")){
            if (originOrderStatus.equals("process")||originOrderStatus.equals("complete")||originOrderStatus.equals("transport")||originOrderStatus.equals("controversial")) {
                HfOrderDetail hfOrderDetail = new HfOrderDetail();
                hfOrderDetail.setHfStatus(targetOrderStatus);
                HfOrderDetailExample hfOrderDetailExample = new HfOrderDetailExample();
                hfOrderDetailExample.createCriteria().andOrderIdEqualTo(Id);
                hfOrderDetailMapper.updateByExampleSelective(hfOrderDetail,hfOrderDetailExample);
                HfOrder hfOrder = new HfOrder();
                hfOrder.setId(Id);
                hfOrder.setOrderStatus(targetOrderStatus);
                HfOrderExample hfOrderExample = new HfOrderExample();
                hfOrderExample.createCriteria().andIdEqualTo(Id).andOrderCodeEqualTo(orderCode).andOrderStatusEqualTo(originOrderStatus);
                hfOrderMapper.updateByExampleSelective(hfOrder,hfOrderExample);

                HfOrderExample hfOrderExample1 = new HfOrderExample();
                hfOrderExample1.createCriteria().andIdEqualTo(Id).andOrderCodeEqualTo(orderCode);

                payment payment = new payment();
                payment.setOutTradeNo(orderCode);
                payment.setUserId(hfOrderMapper.selectByExample(hfOrderExample1).get(0).getUserId());
//            Map map = (Map) payment;
                restTemplate.getForEntity(REST_URL_PREFIX + "/hf-payment/refund/?outTradeNo={outTradeNo}&userId={userId}", payment.class, orderCode, hfOrderMapper.selectByExample(hfOrderExample1).get(0).getUserId());
            }else {
                HfOrderDetail hfOrderDetail = new HfOrderDetail();
                hfOrderDetail.setHfStatus(targetOrderStatus);
                HfOrderDetailExample hfOrderDetailExample = new HfOrderDetailExample();
                hfOrderDetailExample.createCriteria().andOrderIdEqualTo(Id);
                hfOrderDetailMapper.updateByExampleSelective(hfOrderDetail,hfOrderDetailExample);
                HfOrder hfOrder = new HfOrder();
                hfOrder.setId(Id);
                hfOrder.setOrderStatus(targetOrderStatus);
                HfOrderExample hfOrderExample = new HfOrderExample();
                hfOrderExample.createCriteria().andIdEqualTo(Id).andOrderCodeEqualTo(orderCode).andOrderStatusEqualTo(originOrderStatus);
                hfOrderMapper.updateByExampleSelective(hfOrder,hfOrderExample);
            }
            }
            //----evaluate
            if (targetOrderStatus.equals("evaluate")){
                HfOrderDetail hfOrderDetail = new HfOrderDetail();
                hfOrderDetail.setHfStatus(targetOrderStatus);
                HfOrderDetailExample hfOrderDetailExample = new HfOrderDetailExample();
                hfOrderDetailExample.createCriteria().andOrderIdEqualTo(Id);
                hfOrderDetailMapper.updateByExampleSelective(hfOrderDetail,hfOrderDetailExample);


                HfOrderDetailExample hfOrderDetailExample1 = new HfOrderDetailExample();
                hfOrderDetailExample1.createCriteria().andOrderIdEqualTo(Id).andHfStatusNotEqualTo("evaluate").andHfStatusNotEqualTo("complete");
                List<HfOrderDetail> hfOrderDetail1= hfOrderDetailMapper.selectByExample(hfOrderDetailExample1);
//                if (hfOrderDetail1.size()==0){
                    HfOrder hfOrder = new HfOrder();
                    hfOrder.setId(Id);
                    hfOrder.setOrderStatus(targetOrderStatus);
                    HfOrderExample hfOrderExample = new HfOrderExample();
                    hfOrderExample.createCriteria().andIdEqualTo(Id).andOrderCodeEqualTo(orderCode).andOrderStatusEqualTo(originOrderStatus);
                    hfOrderMapper.updateByExampleSelective(hfOrder,hfOrderExample);
//                }
            }
            //--complete
            if (targetOrderStatus.equals("complete")){
                HfOrderDetail hfOrderDetail3 = new HfOrderDetail();
                hfOrderDetail3.setHfStatus(targetOrderStatus);
                HfOrderDetailExample hfOrderDetailExample3 = new HfOrderDetailExample();
                hfOrderDetailExample3.createCriteria().andOrderIdEqualTo(Id).andStoneIdEqualTo(stoneId);
                List<HfOrderDetail> hfOrderDetailList= hfOrderDetailMapper.selectByExample(hfOrderDetailExample3);
//                Integer money = hfOrderDetailList.stream().mapToInt(HfOrderDetail::getActualPrice).sum();
                //lius
                MultiValueMap<String, Object> paramMap1 = new LinkedMultiValueMap<>();
                paramMap1.add("orderId",Id);
                restTemplate.postForObject(itemUrl1,paramMap1,JSONObject.class);
                hfOrderDetailList.forEach(hfOrderDetail -> {
                    MultiValueMap<String, Object> paramMap2 = new LinkedMultiValueMap<>();
                    paramMap2.add("stoneId",hfOrderDetail.getStoneId());
                    paramMap2.add("balanceType","rechargeAmount");
                    paramMap2.add("money",hfOrderDetail.getActualPrice());
                    restTemplate.postForObject(itemUrl2,paramMap2,JSONObject.class);
                });

                //
                HfOrderDetail hfOrderDetail = new HfOrderDetail();
                hfOrderDetail.setHfStatus(targetOrderStatus);
                HfOrderDetailExample hfOrderDetailExample = new HfOrderDetailExample();
                hfOrderDetailExample.createCriteria().andOrderIdEqualTo(Id).andStoneIdEqualTo(stoneId);
                hfOrderDetailMapper.updateByExampleSelective(hfOrderDetail,hfOrderDetailExample);

                HfOrderDetailExample hfOrderDetailExample1 = new HfOrderDetailExample();
                hfOrderDetailExample1.createCriteria().andOrderIdEqualTo(Id).andHfStatusNotEqualTo("complete");
                List<HfOrderDetail> hfOrderDetail1= hfOrderDetailMapper.selectByExample(hfOrderDetailExample1);
                if (hfOrderDetail1.size()==0){
                    HfOrder hfOrder = new HfOrder();
                    hfOrder.setId(Id);
                    hfOrder.setOrderStatus(targetOrderStatus);
                    HfOrderExample hfOrderExample = new HfOrderExample();
                    hfOrderExample.createCriteria().andIdEqualTo(Id).andOrderCodeEqualTo(orderCode).andOrderStatusEqualTo(originOrderStatus);
                    hfOrderMapper.updateByExampleSelective(hfOrder,hfOrderExample);
                }
            }
        if (targetOrderStatus.equals("reject")){
            targetOrderStatus= (String) redisTemplate.opsForValue().get(orderCode+"controversial");
            HfOrderDetail hfOrderDetail = new HfOrderDetail();
            hfOrderDetail.setHfStatus(targetOrderStatus);
            HfOrderDetailExample hfOrderDetailExample = new HfOrderDetailExample();
            hfOrderDetailExample.createCriteria().andOrderIdEqualTo(Id);
            hfOrderDetailMapper.updateByExampleSelective(hfOrderDetail,hfOrderDetailExample);
            HfOrder hfOrder = new HfOrder();
            hfOrder.setId(Id);
            hfOrder.setOrderStatus(targetOrderStatus);
            HfOrderExample hfOrderExample = new HfOrderExample();
            hfOrderExample.createCriteria().andIdEqualTo(Id).andOrderCodeEqualTo(orderCode);
            hfOrderMapper.updateByExampleSelective(hfOrder,hfOrderExample);
        }



        return builder.body(ResponseUtils.getResponseBody("0"));
    }

    @ApiOperation(value = "创建订单", notes = "创建订单")
    @RequestMapping(value = "/Ordercreate", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> Ordercreate(CreateOrderRequest request) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HfRequestIdExample idExample = new HfRequestIdExample();
        idExample.createCriteria().andRequestIdEqualTo(request.getRequestId());
        if(!hfRequestIdMapper.selectByExample(idExample).isEmpty()) {
        	return builder.body(ResponseUtils.getResponseBody(-1));
        }else {
        	HfRequestId id = new HfRequestId();
        	id.setRequestId(request.getRequestId());
        	id.setCreateTime(LocalDateTime.now());
        	id.setModifTime(LocalDateTime.now());
        	id.setIsDeleted((byte) 0);
        	hfRequestIdMapper.insert(id);
        }
        Integer moneys = 0;
        JSONArray jsonArray= JSONArray.parseArray(request.getGoodsList());
//        JSONObject jsonObject1= JSON.parseObject(request.getGoodsList());
//        CreatesOrder createsOrder1 = JSONArray.toJavaObject(jsonObject1,CreatesOrder.class);
        //转list
        List<CreatesOrder> list = JSONObject.parseArray(jsonArray.toJSONString(), CreatesOrder.class);
        if (chock(list).size()!=0){
            return builder.body(ResponseUtils.getResponseBody(chock(list)));
        }
        LocalDateTime time = LocalDateTime.now();
        HfOrder hfOrder = new HfOrder();
        hfOrder.setCreateTime(time);
        hfOrder.setModifyTime(time);
        
        hfOrder.setAmount(moneys);
        hfOrder.setHfRemark(request.getHfRemark());
        hfOrder.setUserId(request.getUserId());
        hfOrder.setOrderType(request.getOrderType());
        hfOrder.setPaymentName(request.getPaymentName());
        hfOrder.setStoneId(1);//用作bossId
//        hfOrder.setDistributorId(request.getDistributorId());
        hfOrder.setOrderCode(UUID.randomUUID().toString().replaceAll("-", ""));
        hfOrder.setLastModifier(String.valueOf(hfOrder.getUserId()));
        Integer paymentType = PaymentType.getPaymentTypeEnum(hfOrder.getPaymentName()).getPaymentType();
        hfOrder.setPaymentType(paymentType);
        hfOrder.setOrderStatus(OrderStatus.PAYMENT.getOrderStatus());
        hfOrder.setPayStatus(PaymentStatus.UNPAID.getPaymentStatus());

        hfOrderMapper.insertSelective(hfOrder);
        //
        Integer actualPrice = null;
        if (request.getDisconuntId()!=null && request.getDisconuntId().length!=0) {
            actualPrice=0;
            for (CreatesOrder goodss : list) {
                System.out.println(goodss.getGoodsId());
                HfPriceExample hfPriceExample = new HfPriceExample();
                hfPriceExample.createCriteria().andGoogsIdEqualTo(goodss.getGoodsId());
                List<HfPrice> hfPriceList = hfPriceMappers.selectByExample(hfPriceExample);
                actualPrice = (hfPriceList.get(0).getSellPrice() * goodss.getQuantity())+actualPrice;
            }
            //平台优惠券记录
            for (Integer discountId:request.getDisconuntId()){
                DiscountCouponOrder discountCouponOrder = new DiscountCouponOrder();
                discountCouponOrder.setCreateDate(LocalDateTime.now());
                discountCouponOrder.setModifyDate(LocalDateTime.now());
                discountCouponOrder.setIsDeleted(0);
                discountCouponOrder.setUseState(1);
                discountCouponOrder.setOrderId(hfOrder.getId());
                discountCouponOrder.setDiscountCouponId(discountId);
                discountCouponOrderMapper.insertSelective(discountCouponOrder);
                restTemplate.getForObject(REST_URL_CHECK+"discountCoupon/useDis/?discountCouponId={discountCouponId}&userId={userId}",JSONObject.class,discountId,request.getUserId());
            }
        }
        System.out.println(actualPrice);
        List<Integer> sss = new ArrayList<>();
            for (CreatesOrder goods : list) {
                Map map = money(goods.getGoodsId(), request.getDisconuntId(), request.getActivityId(), goods.getQuantity(), actualPrice, Integer.valueOf(goods.getHfDesc()));
                moneys = (Integer) map.get("money") + moneys;
                sss.add(moneys);
                HfPriceExample hfPriceExample = new HfPriceExample();
                hfPriceExample.createCriteria().andGoogsIdEqualTo(goods.getGoodsId());
                List<HfPrice> hfPrices = hfPriceMappers.selectByExample(hfPriceExample);
                request.setActualPrice(hfPrices.get(0).getSellPrice() * goods.getQuantity());
                request.setGoodsId(goods.getGoodsId());
                request.setHfDesc(goods.getHfDesc());
                request.setQuantity(goods.getQuantity());
                request.setSellPrice(hfPrices.get(0).getSellPrice());
                request.setStoneId(goods.getStoneId());
                //库存处理
                HfGoods hfGoods = hfGoodMapper.selectByPrimaryKey(goods.getGoodsId());
                HfResp hfResp = hfRespMapper.selectByPrimaryKey(hfGoods.getRespId());
                hfResp.setQuantity(hfResp.getQuantity()-goods.getQuantity());
                hfRespMapper.updateByPrimaryKeySelective(hfResp);
                //详情
                if (OrderTypeEnum.NOMAL_ORDER.getOrderType().equals(hfOrder.getOrderType())) {
                    detailNomalOrders(request, hfOrder);
                }
            }
        HfOrder hfOrder1 = new HfOrder();
        hfOrder1.setId(hfOrder.getId());
        if (actualPrice!=null){
            moneys=sss.get(0);
        }
            hfOrder1.setAmount(moneys);
        hfOrderMapper.updateByPrimaryKeySelective(hfOrder1);
//清购物车
        List<ProductStone> productStoneList = new ArrayList<>();
        for (CreatesOrder goodss : list) {
            ProductStone productStone = new ProductStone();
            productStone.setProductId(String.valueOf(goodss.getGoodsId()));
            productStone.setStoneId(String.valueOf(goodss.getStoneId()));
            productStoneList.add(productStone);
        }
        String productStone = JSON.toJSONString(productStoneList);
        System.out.println(productStone);
        System.out.println(request.getUserId());
//        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
//        paramMap.add("productStoneId",productStone);
//        paramMap.add("userId", String.valueOf(request.getUserId()));
        JSONObject entity=restTemplate.getForObject(REST_URL_CHECK+"cart/delGoods/?productStoneId={productStoneId}&userId={userId}",JSONObject.class,productStone,request.getUserId());
        return builder.body(ResponseUtils.getResponseBody(hfOrder));
    }

    private void detailNomalOrders(CreateOrderRequest request, HfOrder hfOrder) {
        LocalDateTime time = LocalDateTime.now();
//流水
        MultiValueMap<String, Object> paramMap1 = new LinkedMultiValueMap<>();
        paramMap1.add("balanceType","order");
        paramMap1.add("price",request.getActualPrice());
        paramMap1.add("state",2);
        paramMap1.add("stoneId",request.getStoneId());
        paramMap1.add("orderId",hfOrder.getId());
        restTemplate.postForObject(itemUrl,paramMap1,JSONObject.class);
        //
        HfOrderDetail detail = new HfOrderDetail();
        detail.setActualPrice(request.getActualPrice());
        detail.setCreateTime(time);
        detail.setGoodsId(request.getGoodsId());
        detail.setFreight(request.getFreight());
//Map map = new HashMap();
        MultiValueMap<String, Integer> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("productId",request.getGoodsId());
        paramMap.add("quantity",request.getQuantity());
        JSONObject entity=restTemplate.getForObject(REST_URL_CHECK+"goods/getDetail/?goodsId={goodsId}&quantity={quantity}",JSONObject.class,request.getGoodsId(),request.getQuantity());
        JSONObject data=entity.getJSONObject("data");
//        map=JSON.parseObject(data.toString(),new TypeReference<Map<String,Object>>(){});

        detail.setHfDesc(String.valueOf(data));
        detail.setHfStatus(OrderStatus.PAYMENT.getOrderStatus());
        detail.setLastModifier(String.valueOf(request.getUserId()));
        detail.setModifyTime(time);
        detail.setOrderId(hfOrder.getId());
        detail.setQuantity(request.getQuantity());
        detail.setSellPrice(request.getSellPrice());
        detail.setStoneId(request.getStoneId());
//        CreateHfOrderRequest request1 = new CreateHfOrderRequest();
        detail.setTakingType(TakingTypeEnum.getTakingTypeEnum(request.getTakingType()).getTakingType());
        hfOrderDetailMapper.insertSelective(detail);
        if (java.util.Optional.ofNullable(request.getUserAddressId()).isPresent()) {
            if (TakingTypeEnum.getTakingTypeEnum(request.getTakingType()).equals(TakingTypeEnum.DELIVERY)) {
                hfOrderDao.insertOrderAddress(request.getUserAddressId(), hfOrder.getId());
            }
        }

    }

    private Map money(Integer goodsId,Integer[] disconuntId,Integer activityId,Integer num,Integer actualPrice,Integer instanceId){
        Map map = new HashMap();
        if (activityId!=null){
            MultiValueMap<String, Integer> paramMap = new LinkedMultiValueMap<>();
            paramMap.add("goodsId",goodsId);
            paramMap.add("GoodsNum",num);
            paramMap.add("activityId",activityId);
            paramMap.add("instanceId",instanceId);
            JSONObject entity=restTemplate.postForObject(REST_URL_CHECK+"hf-goods/checkResp/",paramMap,JSONObject.class);
            JSONObject data=entity.getJSONObject("data");
            map=JSON.parseObject(data.toString(),new TypeReference<Map<String,Object>>(){});
            System.out.println(map.get("money")+"活动");
        } else if (disconuntId!=null && actualPrice!=null && disconuntId.length!=0){
            MultiValueMap<String, Integer> paramMap = new LinkedMultiValueMap<>();
            paramMap.add("goodsId",goodsId);
            paramMap.add("GoodsNum",num);
            paramMap.add("actualPrice",actualPrice);
            paramMap.add("instanceId",instanceId);
            for (Integer integer:disconuntId){
                paramMap.add("discountCouponId",integer);
            }
            JSONObject entity=restTemplate.postForObject(REST_URL_CHECK+"hf-goods/checkResp/",paramMap,JSONObject.class);
            JSONObject data=entity.getJSONObject("data");
            map=JSON.parseObject(data.toString(),new TypeReference<Map<String,Object>>(){});
            System.out.println(map.get("money")+"优惠券");
        } else {
            MultiValueMap<String, Integer> paramMap = new LinkedMultiValueMap<>();
            paramMap.add("goodsId",goodsId);
            paramMap.add("GoodsNum",num);
            paramMap.add("instanceId",instanceId);
            JSONObject entity=restTemplate.postForObject(REST_URL_CHECK+"hf-goods/checkResp/",paramMap,JSONObject.class);
            JSONObject data=entity.getJSONObject("data");
            map=JSON.parseObject(data.toString(),new TypeReference<Map<String,Object>>(){});
            System.out.println(map.get("money")+"普通");
        }
        return map;
    }
private Map<String,String> chock(List<CreatesOrder> list){
        Map<String,String> map = new HashMap<>();
        for (CreatesOrder createsOrder:list){
            HfRespExample hfRespExample = new HfRespExample();
            hfRespExample.createCriteria().andGoogsIdEqualTo(createsOrder.getGoodsId());
            List<HfResp> hfResps= hfRespMapper.selectByExample(hfRespExample);
            if (hfResps.get(0).getQuantity()<createsOrder.getQuantity()){
                HfGoods hfGoods= hfGoodMapper.selectByPrimaryKey(createsOrder.getGoodsId());
                Product product = productMapper.selectByPrimaryKey(hfGoods.getProductId());
                map.put("goodsId", String.valueOf(createsOrder.getGoodsId()));
                map.put("productName",product.getHfName());
                map.put("goodsName",hfGoods.getHfName());
            }
        }
        return map;
}
    public static void main(String[] args) {
		System.out.println(UUID.randomUUID().toString().replaceAll("-", ""));
	}
}
