package com.hanfu.product.center.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import com.hanfu.product.center.manual.dao.*;
import com.hanfu.product.center.model.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.hanfu.product.center.dao.HfActivityMapper;
import com.hanfu.product.center.dao.HfActivityProductMapper;
import com.hanfu.product.center.dao.HfAnnouncementMapper;
import com.hanfu.product.center.dao.HfBalanceDetailMapper;
import com.hanfu.product.center.dao.HfCategoryMapper;
import com.hanfu.product.center.dao.HfGoodsMapper;
import com.hanfu.product.center.dao.HfIntegralMapper;
import com.hanfu.product.center.dao.HfOrderDetailMapper;
import com.hanfu.product.center.dao.HfOrderMapper;
import com.hanfu.product.center.dao.HfProductCollectMapper;
import com.hanfu.product.center.dao.HfStoneConcernMapper;
import com.hanfu.product.center.dao.HfStoneMapper;
import com.hanfu.product.center.dao.HfStonePictureMapper;
import com.hanfu.product.center.dao.HfUserBrowseRecordMapper;
import com.hanfu.product.center.dao.HfUserMemberMapper;
import com.hanfu.product.center.dao.HfUsersMapper;
import com.hanfu.product.center.dao.HfVisitsRecordMapper;
import com.hanfu.product.center.dao.PayOrderMapper;
import com.hanfu.product.center.dao.ProductInstanceMapper;
import com.hanfu.product.center.dao.ProductMapper;
import com.hanfu.product.center.dao.UserPersonalBrowseMapper;
import com.hanfu.product.center.manual.model.HfGoodsDisplayInfo;
import com.hanfu.product.center.manual.model.HfProductDisplay;
import com.hanfu.product.center.manual.model.HomePageInfo;
import com.hanfu.product.center.manual.model.StoneConcernInfo;
import com.hanfu.product.center.manual.model.Time;
import com.hanfu.product.center.manual.model.UserBrowseInfo;
import com.hanfu.product.center.manual.model.HomePageInfo.MouthEnum;
import com.hanfu.product.center.manual.model.HomePageOrderType;
import com.hanfu.product.center.manual.model.OrderRecord;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseEntity.BodyBuilder;

import com.hanfu.utils.response.handler.ResponseUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
@RequestMapping("/home")
@Api
public class HomePageController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private HfStoneMapper hfStoneMapper;

	@Autowired
	private HfOrderMapper hfOrderMapper;

	@Autowired
	private HfUserBrowseRecordMapper hfUserBrowseRecordMapper;

	@Autowired
	private HfOrderDetailMapper hfOrderDetailMapper;

	@Autowired
	private HomePageDao homePageDao;

	@Autowired
	private HfGoodsMapper hfGoodsMapper;

	@Autowired
	private ProductMapper productMapper;

	@Autowired
	private HfUsersMapper hfUsersMapper;

	@Autowired
	private HfIntegralMapper hfIntegralMapper;

	@Autowired
	private HfBalanceDetailMapper hfBalanceDetailMapper;

	@Autowired
	private ManualDao manualDao;

	@Autowired
	private UserPersonalBrowseMapper userPersonalBrowseMapper;

	@Autowired
	private HfGoodsDisplayDao hfGoodsDisplayDao;

	@Autowired
	private HfCategoryMapper hfCategoryMapper;

	@Autowired
	private HfProductCollectMapper hfProductCollectMapper;

	@Autowired
	private HfStoneConcernMapper hfStoneConcernMapper;
	
	@Autowired
	private HfStonePictureMapper hfStonePictureMapper;
	
	@Autowired
	private ProductInstanceMapper productInstanceMapper;
	
	@Autowired
	private HfActivityProductMapper hfActivityProductMapper;
	
	@Autowired
	private HfActivityMapper hfActivityMapper;
	
	@Autowired
	private HfAnnouncementMapper hfAnnouncementMapper;
	
	@Autowired
	private HfUserMemberMapper hfUserMemberMapper;
	
	@Autowired
	private HfVisitsRecordMapper hfVisitsRecordMapper;
	
	@Autowired
	private PayOrderMapper payOrderMapper;

//	@ApiOperation(value = "获取首页收入金额数据", notes = "获取首页收入金额数据")
//	@RequestMapping(value = "/findAmountData", method = RequestMethod.GET)
//	@ApiImplicitParams({
//			@ApiImplicitParam(paramType = "query", name = "bossId", value = "bossId", required = true, type = "Integer") })
//	public ResponseEntity<JSONObject> findAmountData(HttpServletRequest request) throws Exception {
//		Integer orderCountDay = 0;
//		Integer orderCountMouth = 0;
//		Integer orderCountAll = 0;
//		double amountDay = 0;
//		double amountMouth = 0;
//		double amountAll = 0;
//		Integer vistisDay = 0;
//		Integer vistisMouth = 0;
//		Integer vistisAll = 0;
//		Integer memberDay = 0;
//		Integer memberMouth = 0;
//		Integer memberAll = 0;
//		List<String> status = new ArrayList<String>();
//		status.add("complete");
//		status.add("evaluate");
//		Integer bossId = 1;
//		Integer stoneId = 1;
//		ServletContext sc;
//		if("boss".equals((sc = request.getServletContext()).getAttribute("getServletContextType"))) {
//			bossId = (Integer) sc.getAttribute("getServletContext");
//		}
//		if("stone".equals(sc.getAttribute("getServletContextType"))) {
//			stoneId = (Integer) sc.getAttribute("getServletContext");
//		}
//		
//		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
//		
//		
//
//		HashSet h = new HashSet(paymentCountDay);
//		paymentCountDay.clear();
//		paymentCountDay.addAll(h);
//		h = new HashSet(paymentCountYestday);
//		
//		
//		HomePageInfo info = new HomePageInfo();
//		info.setAmountDay(amountDay);
//		info.setOrderCountsDay(orderCountDay);
//		info.setOrderCountsYestday(orderCountYestday);
//		info.setPaymentConutsDay(paymentCountDay.size());
//		info.setPaymentConutsYestday(paymentCountYestday.size());
//		info.setBrowseCountsDay(browseCountsDay.size());
//		info.setBrowseCountsYestday(browseCountsYestday.size());
//		info.setAmountMouth(amountMouth);
//		info.setOrderConutsMouth(orderCountMouth);
//		info.setOrderConutsLastMouth(orderCountLastMouth);
//		info.setPaymentConutsMouth(paymentCountMouth.size());
//		info.setPaymentConutsLastMouth(paymentCountLastMouth.size());
//		info.setBrowseCountsMouth(browseCountsMouth.size());
//		info.setBrowseCountsLastMouth(browseCountsLastMouth.size());
//		return builder.body(ResponseUtils.getResponseBody(info));
//	}
	
	@ApiOperation(value = "获取首页会员数", notes = "获取首页会员数")
	@RequestMapping(value = "/findMemberData", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> findMemberData(HttpServletRequest request) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		HfUserMemberExample example = new HfUserMemberExample();
		Time time = new Time();
		Integer memberDay = 0, memberMouth = 0, memberAll = 0, bossId = 1, stoneId = 1;
		ServletContext sc;
		if("boss".equals((sc = request.getServletContext()).getAttribute("getServletContextType"))) {
			bossId = (Integer) sc.getAttribute("getServletContext");
			example.createCriteria().andBossIdEqualTo(bossId).andIsDeletedEqualTo((byte) 0)
			.andCreateTimeBetween(time.getDayStart(), time.getDayEnd());
			memberDay = hfUserMemberMapper.selectByExample(example).size();
			example.clear();
			example.createCriteria().andBossIdEqualTo(bossId).andIsDeletedEqualTo((byte) 0)
			.andCreateTimeBetween(time.getMouthStart(), time.getMouthEnd());
			memberMouth = hfUserMemberMapper.selectByExample(example).size();
			example.clear();
			example.createCriteria().andBossIdEqualTo(bossId).andIsDeletedEqualTo((byte) 0);
			memberAll = hfUserMemberMapper.selectByExample(example).size();
		}
		if("stone".equals(sc.getAttribute("getServletContextType"))) {
			stoneId = (Integer) sc.getAttribute("getServletContext");
		}
		
		HomePageInfo info = new HomePageInfo();
		info.setMembersDay(memberDay);
		info.setMembersMouth(memberMouth);
		info.setMembersAll(memberAll);
		return builder.body(ResponseUtils.getResponseBody(info));
	}
	
	@ApiOperation(value = "获取首页访问量", notes = "获取首页访问量")
	@RequestMapping(value = "/findVistisData", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> findVistisData(HttpServletRequest request) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		Time time = new Time();
		Integer visitsDay = 0, visitsMouth = 0, visitsAll = 0, bossId = 1, stoneId = 1;
		ServletContext sc;
		if("boss".equals((sc = request.getServletContext()).getAttribute("getServletContextType"))) {
			bossId = (Integer) sc.getAttribute("getServletContext");
			HfVisitsRecordExample example = new HfVisitsRecordExample();
			example.createCriteria().andBossIdEqualTo(bossId).andIsDeletedEqualTo((byte) 0)
			.andVisitsTimeBetween(time.getDayStart(), time.getDayEnd());
			visitsDay = hfVisitsRecordMapper.selectByExample(example).size();
			example.clear();
			example.createCriteria().andBossIdEqualTo(bossId).andIsDeletedEqualTo((byte) 0)
			.andVisitsTimeBetween(time.getMouthStart(), time.getMouthEnd());
			visitsMouth = hfVisitsRecordMapper.selectByExample(example).size();
			example.clear();
			example.createCriteria().andBossIdEqualTo(bossId).andIsDeletedEqualTo((byte) 0);
			visitsAll = hfVisitsRecordMapper.selectByExample(example).size();
		}
		if("stone".equals(sc.getAttribute("getServletContextType"))) {
			stoneId = (Integer) sc.getAttribute("getServletContext");
			HfUserBrowseRecordExample example = new HfUserBrowseRecordExample();
			example.createCriteria().andStoneIdEqualTo(stoneId).andIdDeletedEqualTo((byte) 0)
			.andBrowseDateBetween(time.getDayStart(), time.getDayEnd());
			visitsDay = hfUserBrowseRecordMapper.selectByExample(example).size();
			example.clear();
			example.createCriteria().andStoneIdEqualTo(stoneId).andIdDeletedEqualTo((byte) 0)
			.andBrowseDateBetween(time.getMouthStart(), time.getMouthEnd());
			visitsMouth = hfUserBrowseRecordMapper.selectByExample(example).size();
			example.clear();
			example.createCriteria().andStoneIdEqualTo(stoneId).andIdDeletedEqualTo((byte) 0);
			visitsAll = hfUserBrowseRecordMapper.selectByExample(example).size();
		}
		
		HomePageInfo info = new HomePageInfo();
		info.setBrowseCountsDay(visitsDay);
		info.setBrowseCountsMouth(visitsMouth);
		info.setBrowseCountsAll(visitsAll);
		return builder.body(ResponseUtils.getResponseBody(info));
	}
	
	@ApiOperation(value = "获取首页订单数量", notes = "获取首页订单数量")
	@RequestMapping(value = "/findOrderData", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> findOrderData(HttpServletRequest request) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		Time time = new Time();
		Integer orderDay = 0, orderMouth = 0, orderAll = 0, bossId = 1, stoneId = 1;
		ServletContext sc;
		if("boss".equals((sc = request.getServletContext()).getAttribute("getServletContextType"))) {
			bossId = (Integer) sc.getAttribute("getServletContext");
			PayOrderExample example = new PayOrderExample();
			example.createCriteria().andBossIdEqualTo(bossId).andIsDeletedEqualTo((byte) 0)
			.andCreateTimeBetween(time.getDayStart(), time.getDayEnd());
			orderDay = payOrderMapper.selectByExample(example).size();
			example.clear();
			example.createCriteria().andBossIdEqualTo(bossId).andIsDeletedEqualTo((byte) 0)
			.andCreateTimeBetween(time.getMouthStart(), time.getMouthEnd());
			orderMouth = payOrderMapper.selectByExample(example).size();
			example.clear();
			example.createCriteria().andBossIdEqualTo(bossId).andIsDeletedEqualTo((byte) 0);
			orderAll = payOrderMapper.selectByExample(example).size();
		}
		if("stone".equals(sc.getAttribute("getServletContextType"))) {
			stoneId = (Integer) sc.getAttribute("getServletContext");
			HfOrderExample example = new HfOrderExample();
			example.createCriteria().andStoneIdEqualTo(stoneId).andIdDeletedEqualTo((byte) 0)
			.andCreateTimeBetween(time.getDayStart(), time.getDayEnd());
			orderDay = hfOrderMapper.selectByExample(example).size();
			example.clear();
			example.createCriteria().andStoneIdEqualTo(stoneId).andIdDeletedEqualTo((byte) 0)
			.andCreateTimeBetween(time.getMouthStart(), time.getMouthEnd());
			orderMouth = hfOrderMapper.selectByExample(example).size();
			example.clear();
			example.createCriteria().andStoneIdEqualTo(stoneId).andIdDeletedEqualTo((byte) 0);
			orderAll = hfOrderMapper.selectByExample(example).size();
		}
		
		HomePageInfo info = new HomePageInfo();
		info.setOrderCountsDay(orderDay);
		info.setOrderConutsMouth(orderMouth);
		info.setOrderCountsAll(orderAll);
		return builder.body(ResponseUtils.getResponseBody(info));
	}
	
	@ApiOperation(value = "获取首页金额数据", notes = "获取首页金额数据")
	@RequestMapping(value = "/findAmountData", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> findAmountData(HttpServletRequest request) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		Time time = new Time();
		Integer amountDay = 0, amountMouth = 0, amountAll = 0, bossId = 1, stoneId = 1;
		ServletContext sc;
		List<String> status = new ArrayList<String>();
		status.add("complete");
		status.add("evaluate");
		if("boss".equals((sc = request.getServletContext()).getAttribute("getServletContextType"))) {
			bossId = (Integer) sc.getAttribute("getServletContext");
			Integer[] a = {0};
			PayOrderExample example = new PayOrderExample();
			example.createCriteria().andBossIdEqualTo(bossId).andIsDeletedEqualTo((byte) 0);
			List<PayOrder> list = payOrderMapper.selectByExample(example);
			List<Integer> payId = list.size() == 0 ? Lists.newArrayList(a):list.stream().map(PayOrder::getId).collect(Collectors.toList());
			HfOrderExample orderExample = new HfOrderExample();
			orderExample.createCriteria().andOrderStatusIn(status).andPayOrderIdIn(payId).andIdDeletedEqualTo((byte) 0)
			.andCreateTimeBetween(time.getDayStart(), time.getDayEnd());
			List<HfOrder> orders = hfOrderMapper.selectByExample(orderExample);
			amountDay = orders.size() == 0 ? 0:orders.stream().collect(Collectors.summingInt(HfOrder::getAmount));
			orderExample.clear();
			orderExample.createCriteria().andOrderStatusIn(status).andPayOrderIdIn(payId).andIdDeletedEqualTo((byte) 0)
			.andCreateTimeBetween(time.getMouthStart(), time.getMouthEnd());
			orders = hfOrderMapper.selectByExample(orderExample);
			amountMouth = orders.size() == 0 ? 0:orders.stream().collect(Collectors.summingInt(HfOrder::getAmount));
			orderExample.clear();
			orderExample.createCriteria().andOrderStatusIn(status).andPayOrderIdIn(payId).andIdDeletedEqualTo((byte) 0);
			orders = hfOrderMapper.selectByExample(orderExample);
			amountAll = orders.size() == 0 ? 0:orders.stream().collect(Collectors.summingInt(HfOrder::getAmount));
			
		}
		if("stone".equals(sc.getAttribute("getServletContextType"))) {
			stoneId = (Integer) sc.getAttribute("getServletContext");
			HfOrderExample example = new HfOrderExample();
			example.createCriteria().andStoneIdEqualTo(stoneId).andIdDeletedEqualTo((byte) 0)
			.andCreateTimeBetween(time.getDayStart(), time.getDayEnd());
			List<HfOrder> orders = hfOrderMapper.selectByExample(example);
			amountDay = orders.size() == 0 ? 0:orders.stream().collect(Collectors.summingInt(HfOrder::getAmount));
			example.clear();
			example.createCriteria().andStoneIdEqualTo(stoneId).andIdDeletedEqualTo((byte) 0)
			.andCreateTimeBetween(time.getMouthStart(), time.getMouthEnd());
			orders = hfOrderMapper.selectByExample(example);
			amountMouth = orders.size() == 0 ? 0:orders.stream().collect(Collectors.summingInt(HfOrder::getAmount));
			example.clear();
			example.createCriteria().andStoneIdEqualTo(stoneId).andIdDeletedEqualTo((byte) 0);
			orders = hfOrderMapper.selectByExample(example);
			amountAll = orders.size() == 0 ? 0:orders.stream().collect(Collectors.summingInt(HfOrder::getAmount));
		}
		
		HomePageInfo info = new HomePageInfo();
		info.setAmountDay(amountDay);
		info.setAmountMouth(amountMouth);
		info.setAmountAll(amountAll);
		return builder.body(ResponseUtils.getResponseBody(info));
	}
	
	@ApiOperation(value = "获取首页订单类型数据", notes = "获取首页订单类型数据")
	@RequestMapping(value = "/findOrderTypeData", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> findOrderTypeData(HttpServletRequest request) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		Map<String, Integer> map = new HashMap<String, Integer>();
		Integer bossId = 1, stoneId = 1;
		ServletContext sc;
		if("boss".equals((sc = request.getServletContext()).getAttribute("getServletContextType"))) {
			bossId = (Integer) sc.getAttribute("getServletContext");
			Integer[] a = {0};
			PayOrderExample example = new PayOrderExample();
			example.createCriteria().andBossIdEqualTo(bossId).andIsDeletedEqualTo((byte) 0);
			List<PayOrder> list = payOrderMapper.selectByExample(example);
			List<Integer> payId = list.size() == 0 ? Lists.newArrayList(a):list.stream().map(PayOrder::getId).collect(Collectors.toList());
			HfOrderExample orderExample = new HfOrderExample();
			orderExample.createCriteria().andOrderStatusEqualTo("payment").andPayOrderIdIn(payId).andIdDeletedEqualTo((byte) 0);
			map.put("payment", hfOrderMapper.selectByExample(orderExample).size());
			orderExample.clear();
			orderExample.createCriteria().andOrderStatusEqualTo("process").andPayOrderIdIn(payId).andIdDeletedEqualTo((byte) 0);
			map.put("process", hfOrderMapper.selectByExample(orderExample).size());
			orderExample.clear();
			orderExample.createCriteria().andOrderStatusEqualTo("transport").andPayOrderIdIn(payId).andIdDeletedEqualTo((byte) 0);
			map.put("transport", hfOrderMapper.selectByExample(orderExample).size());
			orderExample.clear();
			orderExample.createCriteria().andOrderStatusEqualTo("complete").andPayOrderIdIn(payId).andIdDeletedEqualTo((byte) 0);
			map.put("complete", hfOrderMapper.selectByExample(orderExample).size());
		}
		if("stone".equals(sc.getAttribute("getServletContextType"))) {
			stoneId = (Integer) sc.getAttribute("getServletContext");
			HfOrderExample orderExample = new HfOrderExample();
			orderExample.createCriteria().andOrderStatusEqualTo("payment").andStoneIdEqualTo(stoneId).andIdDeletedEqualTo((byte) 0);
			map.put("payment", hfOrderMapper.selectByExample(orderExample).size());
			orderExample.clear();
			orderExample.createCriteria().andOrderStatusEqualTo("process").andStoneIdEqualTo(stoneId).andIdDeletedEqualTo((byte) 0);
			map.put("process", hfOrderMapper.selectByExample(orderExample).size());
			orderExample.clear();
			orderExample.createCriteria().andOrderStatusEqualTo("transport").andStoneIdEqualTo(stoneId).andIdDeletedEqualTo((byte) 0);
			map.put("transport", hfOrderMapper.selectByExample(orderExample).size());
			orderExample.clear();
			orderExample.createCriteria().andOrderStatusEqualTo("complete").andStoneIdEqualTo(stoneId).andIdDeletedEqualTo((byte) 0);
			map.put("complete", hfOrderMapper.selectByExample(orderExample).size());
		}
		
		return builder.body(ResponseUtils.getResponseBody(map));
	}
	
	@ApiOperation(value = "获取首页各类目占比", notes = "获取首页各类目占比")
	@RequestMapping(value = "/findCategoryData", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> findCategoryData(HttpServletRequest request) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		Map<String, Integer> map = new HashMap<String, Integer>();
		Integer bossId = 1, stoneId = 1;
		ServletContext sc;
		if("boss".equals((sc = request.getServletContext()).getAttribute("getServletContextType"))) {
			bossId = (Integer) sc.getAttribute("getServletContext");
			ProductExample example = new ProductExample();
			example.createCriteria().andBossIdEqualTo(bossId).andIsDeletedEqualTo((short) 0);
			List<Product> list = productMapper.selectByExample(example);
			Integer allSize = list.size();
			Map<Integer, List<Product>> mapProduct = list.stream().collect(Collectors.groupingBy(Product::getCategoryId));
			Iterator<Entry<Integer, List<Product>>> iterator = mapProduct.entrySet().iterator();
			while(iterator.hasNext()) {
				HfCategory category = hfCategoryMapper.selectByPrimaryKey(iterator.next().getKey());
				map.put(category.getHfName(), iterator.next().getValue().size()/allSize);
			}
		}
		if("stone".equals(sc.getAttribute("getServletContextType"))) {
			stoneId = (Integer) sc.getAttribute("getServletContext");
			ProductInstanceExample example = new ProductInstanceExample();
			example.createCriteria().andStoneIdEqualTo(stoneId).andIsDeletedEqualTo((short) 0);
			List<ProductInstance> list = productInstanceMapper.selectByExample(example);
			Integer allSize = list.size();
			Map<Integer, List<ProductInstance>> mapProductInstance = list.stream().collect(Collectors.groupingBy(ProductInstance::getCategoryId));
			Iterator<Entry<Integer, List<ProductInstance>>> iterator = mapProductInstance.entrySet().iterator();
			while(iterator.hasNext()) {
				HfCategory category = hfCategoryMapper.selectByPrimaryKey(iterator.next().getKey());
				map.put(category.getHfName(), iterator.next().getValue().size()/allSize);
			}
		}
		
		return builder.body(ResponseUtils.getResponseBody(map));
	}
	
	@ApiOperation(value = "获取首页销量排行数据", notes = "获取首页销量排行数据")
	@RequestMapping(value = "/findSalesVolumeData", method = RequestMethod.GET)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "bossId", value = "bossId", required = true, type = "Integer") })
	public ResponseEntity<JSONObject> findSalesVolumeData(HttpServletRequest request) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		Integer bossId = 1, stoneId = 1;
		ServletContext sc;
		List<HomePageInfo> infos = new ArrayList<HomePageInfo>();
		List<Integer> payId = null;
		HfOrderExample orderExample = new HfOrderExample();
		Integer[] a = {0};
		if("boss".equals((sc = request.getServletContext()).getAttribute("getServletContextType"))) {
			bossId = (Integer) sc.getAttribute("getServletContext");
			PayOrderExample example = new PayOrderExample();
			example.createCriteria().andBossIdEqualTo(bossId).andIsDeletedEqualTo((byte) 0);
			List<PayOrder> list = payOrderMapper.selectByExample(example);
			payId = list.size() == 0 ? Lists.newArrayList(a):list.stream().map(PayOrder::getId).collect(Collectors.toList());
			orderExample.createCriteria().andPayOrderIdIn(payId).andIdDeletedEqualTo((byte) 0 );
		}
		if("stone".equals(sc.getAttribute("getServletContextType"))) {
			stoneId = (Integer) sc.getAttribute("getServletContext");
			orderExample.createCriteria().andStoneIdEqualTo(stoneId).andIdDeletedEqualTo((byte) 0 );
		}
		List<HfOrder> orders = hfOrderMapper.selectByExample(orderExample);
		List<Integer> orderId = orders.size() == 0 ? Lists.newArrayList(a):orders.stream().map(HfOrder::getId).collect(Collectors.toList());
		HfOrderDetailExample detailExample = new HfOrderDetailExample();
		detailExample.createCriteria().andOrderIdIn(orderId).andIdDeletedEqualTo((byte) 0);
		List<HfOrderDetail> details = hfOrderDetailMapper.selectByExample(detailExample);
		if(!CollectionUtils.isEmpty(details)) {
			Map<Integer, List<HfOrderDetail>> mapOrderDetail = details.stream().collect(Collectors.groupingBy(HfOrderDetail::getGoodsId));
			Iterator<Entry<Integer, List<HfOrderDetail>>> iterator = mapOrderDetail.entrySet().iterator();
			while(iterator.hasNext()) {
				HomePageInfo info = new HomePageInfo();
				Entry<Integer, List<HfOrderDetail>> entry = iterator.next();
				HfGoods goods = hfGoodsMapper.selectByPrimaryKey(entry.getKey());
				Product product = productMapper.selectByPrimaryKey(goods.getProductId());
				info.setProductId(product.getId());
				info.setProductName(product.getHfName());
				info.setGoodId(goods.getId());
				Integer count = 0;
				List<HfOrderDetail> orderDetails = entry.getValue();
				for (int i = 0; i < orderDetails.size(); i++) {
					HfOrderDetail detail = orderDetails.get(i);
					count += detail.getQuantity();
				}
				info.setSalesCount(count);
				infos.add(info);
			}
			Map<Integer, List<HomePageInfo>> mapHomePageInfo = infos.stream().collect(Collectors.groupingBy(HomePageInfo::getProductId));
			Iterator<Entry<Integer, List<HomePageInfo>>> iterator2 = mapHomePageInfo.entrySet().iterator();
			infos.clear();
			while (iterator2.hasNext()) {
				Entry<Integer, List<HomePageInfo>> entry = iterator2.next();
				HomePageInfo info = new HomePageInfo();
				Integer all = 0;
				info.setProductId(entry.getKey());
				List<HomePageInfo> homePageInfos = entry.getValue();
				for (int i = 0; i < homePageInfos.size(); i++) {
					HomePageInfo pageInfo = homePageInfos.get(i);
					info.setProductName(pageInfo.getProductName());
					all += pageInfo.getSalesCount();
				}
				info.setSalesCountAll(all);
				infos.add(info);
			}
			infos.stream().sorted(Comparator.comparing(HomePageInfo::getSalesCountAll).reversed());
		}
//		infos.sort(new Comparator<HomePageInfo>() {// Comparator 比较器. 需要实现比较方法
//			@Override
//			public int compare(HomePageInfo o1, HomePageInfo o2) {
//				return o2.getSalesCountAll() - o1.getSalesCountAll();// 从小到大 , 如果是o2.age-o1.age 则表示从大到小
//			}
//		});
		return builder.body(ResponseUtils.getResponseBody(infos));
	}

//	@ApiOperation(value = "获取首页销量排行数据", notes = "获取首页销量排行数据")
//	@RequestMapping(value = "/findSalesVolumeData", method = RequestMethod.GET)
//	@ApiImplicitParams({
//			@ApiImplicitParam(paramType = "query", name = "bossId", value = "bossId", required = true, type = "Integer") })
//	public ResponseEntity<JSONObject> findSalesVolumeData(Integer bossId) throws Exception {
//		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
//		
//		infos.sort(new Comparator<HomePageInfo>() {// Comparator 比较器. 需要实现比较方法
//			@Override
//			public int compare(HomePageInfo o1, HomePageInfo o2) {
//				return o2.getSalesCountAll() - o1.getSalesCountAll();// 从小到大 , 如果是o2.age-o1.age 则表示从大到小
//			}
//		});
//		return builder.body(ResponseUtils.getResponseBody(infos));
//	}

//	@ApiOperation(value = "获取首页订单类型数据", notes = "获取首页订单类型数据")
//	@RequestMapping(value = "/findOrderTypeData", method = RequestMethod.GET)
//	@ApiImplicitParams({
//			@ApiImplicitParam(paramType = "query", name = "bossId", value = "bossId", required = true, type = "Integer") })
//	public ResponseEntity<JSONObject> findOrderTypeData(Integer bossId) throws Exception {
//		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
//		HomePageOrderType info = new HomePageOrderType();
//		List<HomePageOrderType> result = new ArrayList<HomePageOrderType>();
////		HfStoneExample example = new HfStoneExample();
////		example.createCriteria().andBossIdEqualTo(bossId);
////		List<HfStone> list = hfStoneMapper.selectByExample(example);
////		List<Integer> stoneId = list.stream().map(HfStone::getId).collect(Collectors.toList());
//		HfOrderExample example2 = new HfOrderExample();
//		example2.createCriteria().andStoneIdEqualTo(bossId);
//		List<HfOrder> orders = hfOrderMapper.selectByExample(example2);
//		List<Integer> orderId = orders.stream().map(HfOrder::getId).collect(Collectors.toList());
//		List<HomePageInfo> homePageInfos = homePageDao.findOrderTypeCount(orderId);
//		String[] str = new String[homePageInfos.size()];
//		Integer[] str2 = new Integer[homePageInfos.size()];
//		for (int i = 0; i < homePageInfos.size(); i++) {
//			HomePageOrderType homePageOrderType = new HomePageOrderType();
//			if ("nomalOrder".equals(homePageInfos.get(i).getOrderType())) {
//				homePageOrderType.setName("普通订单");
//				str[i] = "普通订单";
//			}
//			if ("rechargeOrder".equals(homePageInfos.get(i).getOrderType())) {
//				homePageOrderType.setName("充值订单");
//				str[i] = "充值订单";
//			}
//			if ("shoppingOrder".equals(homePageInfos.get(i).getOrderType())) {
//				homePageOrderType.setName("到店支付订单");
//				str[i] = "到店支付订单";
//			}
//			homePageOrderType.setValue(homePageInfos.get(i).getOrderTypeCounts());
////			str2[i] = homePageInfos.get(i).getOrderTypeCounts();
//			result.add(homePageOrderType);
//		}
//		info.setJs(JSONArray.parseArray(JSON.toJSONString(result)));
//		info.setData(str);
////		info.setOrderTypeCountsStr(str2);
//		return builder.body(ResponseUtils.getResponseBody(info));
//	}

	@ApiOperation(value = "获取首页年访问量数据", notes = "获取首页年访问量数据")
	@RequestMapping(value = "/findBrowseCountData", method = RequestMethod.GET)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "bossId", value = "bossId", required = true, type = "Integer") })
	public ResponseEntity<JSONObject> findBrowseCountData(Integer bossId, Integer count) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		Integer[] a = new Integer[count];
		Integer[] a2 = new Integer[count];
		HomePageInfo info = new HomePageInfo();
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		Integer index = Integer.valueOf(sdf.format(date)) - (count - 1);
		for (int j = 0; j < count; j++) {
			a[j] = index;
			LocalDateTime mouthStartOfYear = LocalDateTime.of(index++, 1, 1, 0, 0);
//			LocalDateTime lastMouthOfYear = LocalDateTime.of(index++, 12, 1, 0, 0);
			LocalDateTime monthEndOfYear = LocalDateTime
					.of(mouthStartOfYear.with(TemporalAdjusters.lastDayOfYear()).toLocalDate(), LocalTime.MAX);
			HfUserBrowseRecordExample example = new HfUserBrowseRecordExample();
			example.createCriteria().andBossIdEqualTo(bossId).andBrowseDateBetween(mouthStartOfYear, monthEndOfYear);
			List<HfUserBrowseRecord> list = hfUserBrowseRecordMapper.selectByExample(example);
			a2[j] = list.size();
		}
		info.setYear(a);
		info.setBrowseCountForYeay(a2);
		return builder.body(ResponseUtils.getResponseBody(info));
	}

	@ApiOperation(value = "获取销售情况", notes = "获取销售情况")
	@RequestMapping(value = "/findSaleMouthData", method = RequestMethod.GET)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "bossId", value = "bossId", required = true, type = "Integer") })
	public ResponseEntity<JSONObject> findSaleMouthData(Integer bossId) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		List<String> status = new ArrayList<String>();
		status.add("complete");
		status.add("evaluate");
		LocalDateTime ldt = LocalDateTime.now();
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		Integer quantity = 0;
		HomePageInfo info = new HomePageInfo();
		String[] month = new String[ldt.getMonthValue()];
		Integer[] count = new Integer[ldt.getMonthValue()];
		LocalDateTime mouthStart;
		LocalDateTime mouthEnd;
		HfStoneExample example = new HfStoneExample();
		example.createCriteria().andBossIdEqualTo(bossId);
		List<HfStone> list = hfStoneMapper.selectByExample(example);
		List<Integer> stoneId = list.stream().map(HfStone::getId).collect(Collectors.toList());
//		HfOrderExample example2 = new HfOrderExample();
//		example2.createCriteria().andStoneIdIn(stoneId);
//		List<HfOrder> orders = hfOrderMapper.selectByExample(example2);
//		List<Integer> orderId = orders.stream().map(HfOrder::getId).collect(Collectors.toList());
		HfOrderDetailExample example3 = new HfOrderDetailExample();
		example3.createCriteria().andHfStatusIn(status).andStoneIdIn(stoneId);
		List<HfOrderDetail> hfOrderDetails = hfOrderDetailMapper.selectByExample(example3);
		for (int i = 0; i < ldt.getMonthValue(); i++) {
			quantity = 0;
			month[i] = MouthEnum.getPaymentTypeEnum(i + 1);
			mouthStart = LocalDateTime.of(Integer.valueOf(sdf.format(date)), i + 1, 1, 0, 0);
			mouthEnd = LocalDateTime.of(mouthStart.with(TemporalAdjusters.lastDayOfMonth()).toLocalDate(),
					LocalTime.MAX);
			for (int j = 0; j < hfOrderDetails.size(); j++) {
				HfOrderDetail detail = hfOrderDetails.get(j);
				if (detail.getCreateTime().isAfter(mouthStart) && detail.getCreateTime().isBefore(mouthEnd)) {
					quantity += detail.getQuantity();
				}
			}
			count[i] = quantity;
		}
		info.setMouth(month);
		info.setSalesCountMonth(count);
		return builder.body(ResponseUtils.getResponseBody(info));
	}

	@ApiOperation(value = "获取余额充值记录通过用户id", notes = "获取充值记录通过用户id")
	@RequestMapping(value = "/findRechargeRecord", method = RequestMethod.GET)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "userId", value = "用户id", required = true, type = "Integer") })
	public ResponseEntity<JSONObject> findRechargeRecord(Integer userId) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		List<OrderRecord> result = new ArrayList<OrderRecord>();

		HfBalanceDetailExample example = new HfBalanceDetailExample();
		example.createCriteria().andUserIdEqualTo(userId);
		example.setOrderByClause("create_time DESC");
		List<HfBalanceDetail> list = hfBalanceDetailMapper.selectByExample(example);
		for (int i = 0; i < list.size(); i++) {
			HfBalanceDetail order = list.get(i);
			OrderRecord orderRecord = new OrderRecord();
			orderRecord.setAmount(String.valueOf(order.getAmount()));
			orderRecord.setType(order.getPaymentName());
			HfUsers hfUser = hfUsersMapper.selectByPrimaryKey(order.getUserId());
			orderRecord.setPaymentName(hfUser.getRealName());
			orderRecord.setDateTime(order.getCreateTime().plusHours(8));
			result.add(orderRecord);
		}
		return builder.body(ResponseUtils.getResponseBody(result));
	}

	@ApiOperation(value = "获取积分充值记录通过用户id", notes = "获取积分充值记录通过用户id")
	@RequestMapping(value = "/findIntegralRechargeRecord", method = RequestMethod.GET)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "userId", value = "用户id", required = true, type = "Integer") })
	public ResponseEntity<JSONObject> findIntegralRechargeRecord(Integer userId) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		List<OrderRecord> result = new ArrayList<OrderRecord>();
		HfIntegralExample example = new HfIntegralExample();
		example.createCriteria().andUserIdEqualTo(userId);
		example.setOrderByClause("create_time DESC");
		List<HfIntegral> list = hfIntegralMapper.selectByExample(example);
		for (int i = 0; i < list.size(); i++) {
			HfIntegral hfIntegral = list.get(i);
			OrderRecord orderRecord = new OrderRecord();
			orderRecord.setAmount(String.valueOf(hfIntegral.getAmount()));
			orderRecord.setType(hfIntegral.getPaymentName());
			HfUsers hfUser = hfUsersMapper.selectByPrimaryKey(hfIntegral.getUserId());
			orderRecord.setPaymentName(hfUser.getRealName());
			orderRecord.setDateTime(hfIntegral.getCreateTime().plusHours(8));
			result.add(orderRecord);
		}
		return builder.body(ResponseUtils.getResponseBody(result));
	}

	@ApiOperation(value = "查询某一个用户的浏览历史", notes = "查询某一个用户的浏览历史")
	@RequestMapping(value = "/findBrowseRecordByUserId", method = RequestMethod.GET)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "userId", value = "用户id", required = true, type = "Integer") })
	public ResponseEntity<JSONObject> findBrowseRecordByUserId(Integer userId) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		List<UserBrowseInfo> result = new ArrayList<UserBrowseInfo>();
		List<String> list = homePageDao.groupBytime(userId);
		for (int i = 0; i < list.size(); i++) {
			UserBrowseInfo info = new UserBrowseInfo();

			List<HfProductDisplay> displays = new ArrayList<HfProductDisplay>();

			String str = list.get(i);
			LocalDateTime dayStart = LocalDate.parse(str, df).atStartOfDay();
			LocalDateTime dayEnd = dayStart.plusHours(24);
			UserPersonalBrowseExample example = new UserPersonalBrowseExample();
			example.createCriteria().andUserIdEqualTo(userId).andBrowseTimeBetween(dayStart, dayEnd);
			example.setOrderByClause("browse_time DESC");
			List<UserPersonalBrowse> browses = userPersonalBrowseMapper.selectByExample(example);
			for (int j = 0; j < browses.size(); j++) {
				UserPersonalBrowse browse = browses.get(j);
				HfProductDisplay display = new HfProductDisplay();
				
				ProductInstance instance = productInstanceMapper.selectByPrimaryKey(browse.getInstanceId());
				display.setInstanceId(browse.getInstanceId());
				display.setStoneId(instance.getStoneId());
				HfStone hfStone = hfStoneMapper.selectByPrimaryKey(instance.getStoneId());
				display.setStoneName(hfStone.getHfName());
				
				Product product = productMapper.selectByPrimaryKey(browse.getProductId());
				if(product != null) {
				List<HfGoodsDisplayInfo> hfGoodsDisplay = hfGoodsDisplayDao.selectHfGoodsDisplay(browse.getProductId());
				Map<Integer, List<HfGoodsDisplayInfo>> hfGoodsDisplayMap = hfGoodsDisplay.stream()
						.collect(Collectors.toMap(HfGoodsDisplayInfo::getProductId, item -> Lists.newArrayList(item),
								(List<HfGoodsDisplayInfo> oldList, List<HfGoodsDisplayInfo> newList) -> {
									oldList.addAll(newList);
									return oldList;
								}));
				List<HfGoodsDisplayInfo> hfGoods = hfGoodsDisplayMap.get(browse.getProductId());
				Optional<HfGoodsDisplayInfo> hfGood = hfGoods.stream()
						.min(Comparator.comparing(HfGoodsDisplayInfo::getSellPrice));
				if (hfGood.get().getLinePrice() != null) {
					display.setLinePrice(hfGood.isPresent() ? hfGood.get().getLinePrice() : -1);
				}
				display.setId(browse.getProductId());
				display.setPriceArea(hfGood.isPresent() ? String.valueOf(hfGood.get().getSellPrice()) : "异常");
				display.setDefaultGoodsId(hfGood.get().getId());
				display.setProductName(product.getHfName());
				display.setProductDesc(product.getProductDesc());
				display.setFileId(product.getFileId());
				display.setCategoryId(product.getCategoryId());
				com.hanfu.product.center.model.HfCategory category = hfCategoryMapper
						.selectByPrimaryKey(product.getCategoryId());
				display.setCategoryName(category.getHfName());
				display.setBossId(product.getBossId());
				displays.add(display);
				}
			}
			info.setList(displays);
			info.setDate(str);
			info.setUserId(userId);
			result.add(info);
		}
		
//		HfActivityProductExample activityProductExample = new HfActivityProductExample();
//		List<String> type = new ArrayList<String>();
//		type.add("groupActivity");
//		type.add("seckillActivity");
//		for (int j = 0; j < result.size(); j++) {
//			List<HfProductDisplay> products  = result.get(j).getList().stream().filter(p -> p.getInstanceId() != null || !StringUtils.isEmpty(p.getPriceArea())).collect(Collectors.toList());
//			for (int j2 = 0; j2 < products.size(); j2++) {
//				Date date = new Date();
//				HfProductDisplay product = products.get(j2);
//				activityProductExample.clear();
//				activityProductExample.createCriteria().andInstanceIdEqualTo(product.getInstanceId())
//						.andProductActivityTypeIn(type);
//				List<HfActivityProduct> lists = hfActivityProductMapper.selectByExample(activityProductExample);
//				if(!lists.isEmpty()) {
//					HfActivity activity = hfActivityMapper.selectByPrimaryKey(lists.get(0).getActivityId());
//					if (!lists.isEmpty() && activity.getEndTime().after(date)) {
//						product.setProductActivityType(lists.get(0).getProductActivityType());
//						product.setActivityId(lists.get(0).getActivityId());
//						product.setStartTime(activity.getStartTime());
//						product.setEndTime(activity.getEndTime());
//						if (lists.get(0).getFavoravlePrice() != null && lists.get(0).getFavoravlePrice() != 0) {
//							String s = String.valueOf(Integer.valueOf(product.getPriceArea())-lists.get(0).getFavoravlePrice());
//							if (null != s && s.indexOf(".") > 0) {
//								s = s.replaceAll("0+?$", "");// 去掉多余的0
//								s = s.replaceAll("[.]$", "");// 如最后一位是.则去掉
//							}
//							product.setPriceArea(s);
//						} else {
//							if (lists.get(0).getDiscountRatio() != null) {
//								if (lists.get(0).getDiscountRatio() != 0) {
//									String s = String.valueOf(Double.valueOf(product.getPriceArea())
//											* (lists.get(0).getDiscountRatio() / 100));
//									if (null != s && s.indexOf(".") > 0) {
//										s = s.replaceAll("0+?$", "");// 去掉多余的0
//										s = s.replaceAll("[.]$", "");// 如最后一位是.则去掉
//									}
//									product.setPriceArea(s);
//								}
//							}
//						}
//					}
//				}
//			}
//		}
		
		return builder.body(ResponseUtils.getResponseBody(result));
	}

	@ApiOperation(value = "查询某一个用户的商品收藏", notes = "查询某一个用户的商品收藏")
	@RequestMapping(value = "/findProductCollectByUserId", method = RequestMethod.GET)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "userId", value = "用户id", required = true, type = "Integer") })
	public ResponseEntity<JSONObject> findProductCollectByUserId(Integer userId) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		List<UserBrowseInfo> result = new ArrayList<UserBrowseInfo>();
		List<String> list = homePageDao.groupBytimeCollect(userId);
		for (int i = 0; i < list.size(); i++) {
			UserBrowseInfo info = new UserBrowseInfo();

			List<HfProductDisplay> displays = new ArrayList<HfProductDisplay>();

			String str = list.get(i);
			LocalDateTime dayStart = LocalDate.parse(str, df).atStartOfDay();
			LocalDateTime dayEnd = dayStart.plusHours(24);
			HfProductCollectExample example = new HfProductCollectExample();
			example.createCriteria().andUserIdEqualTo(userId).andCollectTimeBetween(dayStart, dayEnd);
			example.setOrderByClause("collect_time DESC");
			List<HfProductCollect> browses = hfProductCollectMapper.selectByExample(example);
			for (int j = 0; j < browses.size(); j++) {
				HfProductCollect browse = browses.get(j);
				HfProductDisplay display = new HfProductDisplay();
				Product product = productMapper.selectByPrimaryKey(browse.getProductId());
				
				ProductInstance instance = productInstanceMapper.selectByPrimaryKey(browse.getInstanceId());
				display.setInstanceId(browse.getInstanceId());
				display.setStoneId(instance.getStoneId());
				HfStone hfStone = hfStoneMapper.selectByPrimaryKey(instance.getStoneId());
				display.setStoneName(hfStone.getHfName());
				List<HfGoodsDisplayInfo> hfGoodsDisplay = hfGoodsDisplayDao.selectHfGoodsDisplay(browse.getProductId());
				Map<Integer, List<HfGoodsDisplayInfo>> hfGoodsDisplayMap = hfGoodsDisplay.stream()
						.collect(Collectors.toMap(HfGoodsDisplayInfo::getProductId, item -> Lists.newArrayList(item),
								(List<HfGoodsDisplayInfo> oldList, List<HfGoodsDisplayInfo> newList) -> {
									oldList.addAll(newList);
									return oldList;
								}));
				List<HfGoodsDisplayInfo> hfGoods = hfGoodsDisplayMap.get(browse.getProductId());
				Optional<HfGoodsDisplayInfo> hfGood = hfGoods.stream()
						.min(Comparator.comparing(HfGoodsDisplayInfo::getSellPrice));
				if (hfGood.get().getLinePrice() != null) {
					display.setLinePrice(hfGood.isPresent() ? hfGood.get().getLinePrice() : -1);
				}
				display.setId(browse.getProductId());
				display.setPriceArea(hfGood.isPresent() ? String.valueOf(hfGood.get().getSellPrice()) : "异常");
				display.setDefaultGoodsId(hfGood.get().getId());
				display.setProductName(product.getHfName());
				display.setProductDesc(product.getProductDesc());
				display.setFileId(product.getFileId());
				display.setCategoryId(product.getCategoryId());
				com.hanfu.product.center.model.HfCategory category = hfCategoryMapper
						.selectByPrimaryKey(product.getCategoryId());
				display.setCategoryName(category.getHfName());
				display.setBossId(product.getBossId());
				displays.add(display);
			}
			info.setList(displays);
			info.setDate(str);
			info.setUserId(userId);
			result.add(info);
		}
		
//		HfActivityProductExample activityProductExample = new HfActivityProductExample();
//		List<String> type = new ArrayList<String>();
//		type.add("groupActivity");
//		type.add("seckillActivity");
//		for (int j = 0; j < result.size(); j++) {
//			List<HfProductDisplay> products  = result.get(j).getList().stream().filter(p -> p.getInstanceId() != null || !StringUtils.isEmpty(p.getPriceArea())).collect(Collectors.toList());
//			for (int j2 = 0; j2 < products.size(); j2++) {
//				Date date = new Date();
//				HfProductDisplay product = products.get(j2);
//				activityProductExample.clear();
//				activityProductExample.createCriteria().andInstanceIdEqualTo(product.getInstanceId())
//						.andProductActivityTypeIn(type);
//				List<HfActivityProduct> lists = hfActivityProductMapper.selectByExample(activityProductExample);
//				if(!lists.isEmpty()) {
//					HfActivity activity = hfActivityMapper.selectByPrimaryKey(lists.get(0).getActivityId());
//					if (!lists.isEmpty() && activity.getEndTime().after(date)) {
//						product.setProductActivityType(lists.get(0).getProductActivityType());
//						product.setActivityId(lists.get(0).getActivityId());
//						product.setStartTime(activity.getStartTime());
//						product.setEndTime(activity.getEndTime());
//						if (lists.get(0).getFavoravlePrice() != null && lists.get(0).getFavoravlePrice() != 0) {
//							String s = String.valueOf(Integer.valueOf(product.getPriceArea())-lists.get(0).getFavoravlePrice());
//							if (null != s && s.indexOf(".") > 0) {
//								s = s.replaceAll("0+?$", "");// 去掉多余的0
//								s = s.replaceAll("[.]$", "");// 如最后一位是.则去掉
//							}
//							product.setPriceArea(s);
//						} else {
//							if (lists.get(0).getDiscountRatio() != null) {
//								if (lists.get(0).getDiscountRatio() != 0) {
//									String s = String.valueOf(Double.valueOf(product.getPriceArea())
//											* (lists.get(0).getDiscountRatio() / 100));
//									if (null != s && s.indexOf(".") > 0) {
//										s = s.replaceAll("0+?$", "");// 去掉多余的0
//										s = s.replaceAll("[.]$", "");// 如最后一位是.则去掉
//									}
//									product.setPriceArea(s);
//								}
//							}
//						}
//					}
//				}
//				
//			}
//		}
		return builder.body(ResponseUtils.getResponseBody(result));
	}

	@ApiOperation(value = "查询某一个用户的店铺关注", notes = "查询某一个用户的店铺关注")
	@RequestMapping(value = "/findStoneConcernByUserId", method = RequestMethod.GET)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "userId", value = "用户id", required = true, type = "Integer") })
	public ResponseEntity<JSONObject> findStoneConcernByUserId(Integer userId) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		List<UserBrowseInfo> result = new ArrayList<UserBrowseInfo>();
		List<String> list = homePageDao.groupBytimeConcern(userId);
		HfStonePictureExample pictureExample = new HfStonePictureExample();
		List<HfStonePicture> pictures = new ArrayList<HfStonePicture>();
		List<Integer> picturesId = new ArrayList<Integer>();
		for (int i = 0; i < list.size(); i++) {
			UserBrowseInfo info = new UserBrowseInfo();

			List<StoneConcernInfo> concernInfos = new ArrayList<StoneConcernInfo>();
			String str = list.get(i);
			LocalDateTime dayStart = LocalDate.parse(str, df).atStartOfDay();
			LocalDateTime dayEnd = dayStart.plusHours(24);
			HfStoneConcernExample example = new HfStoneConcernExample();
			example.createCriteria().andUserIdEqualTo(userId).andConcernTimeBetween(dayStart, dayEnd);
			example.setOrderByClause("concern_time DESC");
			List<HfStoneConcern> browses = hfStoneConcernMapper.selectByExample(example);
			for (int j = 0; j < browses.size(); j++) {
				pictureExample.clear();
				HfStoneConcern concern = browses.get(j);
				StoneConcernInfo concernInfo = new StoneConcernInfo();
				HfStone stone = hfStoneMapper.selectByPrimaryKey(concern.getStoneId());
				concernInfo.setName(stone.getHfName());
				concernInfo.setStoneId(concern.getStoneId());
				concernInfo.setCreateTime(stone.getCreateTime());
				concernInfo.setStoneDesc(stone.getHfDesc());
				concernInfo.setConcernCount(stone.getConcernCount());
				pictureExample.createCriteria().andStoneIdEqualTo(stone.getId());
				pictures = hfStonePictureMapper.selectByExample(pictureExample);
				picturesId = pictures.stream().map(HfStonePicture::getFileId).collect(Collectors.toList());
				concernInfo.setFileId(picturesId);
				concernInfos.add(concernInfo);
			}
			info.setStoneInfo(concernInfos);
			info.setDate(str);
			info.setUserId(userId);
			result.add(info);
		}
		return builder.body(ResponseUtils.getResponseBody(result));
	}
	
	@ApiOperation(value = "添加公告", notes = "添加公告")
	@RequestMapping(value = "/addAnnouncement", method = RequestMethod.POST)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "userId", value = "用户id", required = true, type = "Integer") })
	public ResponseEntity<JSONObject> addAnnouncement(HttpServletRequest request,String content) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		HfAnnouncement announcement = new HfAnnouncement();
		announcement.setContent(content);
		if (request.getServletContext().getAttribute("getServletContext")!=null&&request.getServletContext().getAttribute("getServletContextType")!=null){
			if (request.getServletContext().getAttribute("getServletContextType").equals("boss")) {
				announcement.setBossId((Integer) request.getServletContext().getAttribute("getServletContext"));
			}
		}
		announcement.setCreateTime(LocalDateTime.now());
		announcement.setModifyTime(LocalDateTime.now());
		announcement.setIsDeleted((byte) 0);
		hfAnnouncementMapper.insert(announcement);
		return builder.body(ResponseUtils.getResponseBody(announcement.getId()));
	}
	
	@ApiOperation(value = "查询公告", notes = "查询公告")
	@RequestMapping(value = "/getAnnouncement", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> getAnnouncement(HttpServletRequest request) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		HfAnnouncementExample example = new HfAnnouncementExample();
		if (request.getServletContext().getAttribute("getServletContext")!=null&&request.getServletContext().getAttribute("getServletContextType")!=null){
			if (request.getServletContext().getAttribute("getServletContextType").equals("boss")) {
				example.createCriteria().andBossIdEqualTo((Integer) request.getServletContext().getAttribute("getServletContext"));
			}
		}
		List<HfAnnouncement> list = hfAnnouncementMapper.selectByExample(example);
		return builder.body(ResponseUtils.getResponseBody(list));
	}
	
}
