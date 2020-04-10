package com.hanfu.product.center.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hanfu.product.center.manual.dao.*;
import com.hanfu.product.center.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.hanfu.common.service.FileMangeService;
import com.hanfu.product.center.dao.FileDescMapper;
import com.hanfu.product.center.dao.HfBalanceDetailMapper;
import com.hanfu.product.center.dao.HfCategoryMapper;
import com.hanfu.product.center.dao.HfGoodsMapper;
import com.hanfu.product.center.dao.HfGoodsPictrueMapper;
import com.hanfu.product.center.dao.HfGoodsSpecMapper;
import com.hanfu.product.center.dao.HfIntegralMapper;
import com.hanfu.product.center.dao.HfOrderDetailMapper;
import com.hanfu.product.center.dao.HfOrderMapper;
import com.hanfu.product.center.dao.HfPriceMapper;
import com.hanfu.product.center.dao.HfProductCollectMapper;
import com.hanfu.product.center.dao.HfRespMapper;
import com.hanfu.product.center.dao.HfStoneConcernMapper;
import com.hanfu.product.center.dao.HfStoneMapper;
import com.hanfu.product.center.dao.HfUserBrowseRecordMapper;
import com.hanfu.product.center.dao.HfUsersMapper;
import com.hanfu.product.center.dao.ProductMapper;
import com.hanfu.product.center.dao.ProductSpecMapper;
import com.hanfu.product.center.dao.UserPersonalBrowseMapper;
import com.hanfu.product.center.dao.WarehouseMapper;
import com.hanfu.product.center.manual.model.CheckResp;
import com.hanfu.product.center.manual.model.HfGoodsDisplay;
import com.hanfu.product.center.manual.model.HfGoodsDisplayInfo;
import com.hanfu.product.center.manual.model.HfGoodsSpecDisplay;
import com.hanfu.product.center.manual.model.HfProductDisplay;
import com.hanfu.product.center.manual.model.HomePageInfo;
import com.hanfu.product.center.manual.model.PriceRanking;
import com.hanfu.product.center.manual.model.ProductForValue;
import com.hanfu.product.center.manual.model.StoneConcernInfo;
import com.hanfu.product.center.manual.model.UserBrowseInfo;
import com.hanfu.product.center.manual.model.HomePageInfo.MouthEnum;
import com.hanfu.product.center.manual.model.HomePageOrderType;
import com.hanfu.product.center.manual.model.OrderRecord;
import com.hanfu.product.center.request.GoodsPictrueRequest;
import com.hanfu.product.center.request.GoodsPriceInfo;
import com.hanfu.product.center.request.GoodsSpecRequest;
import com.hanfu.product.center.request.HfGoodsInfo;
import com.hanfu.product.center.request.RespInfo;
import com.hanfu.product.center.service.GoodsRespService;
import com.hanfu.product.center.service.GoodsService;
import com.hanfu.product.center.service.SpecsService;
import com.hanfu.user.center.model.HfUser;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseEntity.BodyBuilder;

import com.hanfu.utils.response.handler.ResponseUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import tk.mybatis.mapper.entity.Example;

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

	@ApiOperation(value = "获取首页收入金额数据", notes = "获取首页收入金额数据")
	@RequestMapping(value = "/findAmountData", method = RequestMethod.GET)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "bossId", value = "bossId", required = true, type = "Integer") })
	public ResponseEntity<JSONObject> findAmountData(Integer bossId) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		double amountDay = 0;
		double amountMouth = 0;
		List<Integer> paymentCountDay = new ArrayList<Integer>();
		List<Integer> paymentCountYestday = new ArrayList<Integer>();
		List<Integer> paymentCountMouth = new ArrayList<Integer>();
		List<Integer> paymentCountLastMouth = new ArrayList<Integer>();
		Integer orderCountDay = 0;
		Integer orderCountYestday = 0;
		Integer orderCountMouth = 0;
		Integer orderCountLastMouth = 0;
		LocalDateTime dayStart = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
		LocalDateTime dayEnd = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
		LocalDateTime yestdayStart = LocalDateTime.of(LocalDate.now(), LocalTime.MIN).plusDays(-1);
		LocalDateTime yestdayEnd = LocalDateTime.of(LocalDate.now(), LocalTime.MAX).plusDays(-1);
		LocalDateTime mouthStart = LocalDateTime
				.of(LocalDateTime.now().with(TemporalAdjusters.firstDayOfMonth()).toLocalDate(), LocalTime.MIN);
		LocalDateTime mouthEnd = LocalDateTime
				.of(LocalDateTime.now().with(TemporalAdjusters.lastDayOfMonth()).toLocalDate(), LocalTime.MAX);
		LocalDateTime lastMouthStart = LocalDateTime
				.of(LocalDateTime.now().with(TemporalAdjusters.firstDayOfMonth()).toLocalDate(), LocalTime.MIN)
				.plusMonths(-1);
		LocalDateTime lastMouthEnd = LocalDateTime
				.of(LocalDateTime.now().with(TemporalAdjusters.lastDayOfMonth()).toLocalDate(), LocalTime.MAX)
				.plusMonths(-1);
		HfStoneExample example = new HfStoneExample();
		example.createCriteria().andBossIdEqualTo(bossId);
		List<HfStone> hfStones = hfStoneMapper.selectByExample(example);
		for (int i = 0; i < hfStones.size(); i++) {
			HfStone hfStone = hfStones.get(i);
			HfOrderExample example2 = new HfOrderExample();
			example2.createCriteria().andStoneIdEqualTo(hfStone.getId()).andOrderStatusEqualTo("complete")
					.andCreateTimeBetween(dayStart, dayEnd);
			List<HfOrder> hfOrderDays = hfOrderMapper.selectByExample(example2);

			example2.clear();
			example2.createCriteria().andStoneIdEqualTo(hfStone.getId()).andOrderStatusEqualTo("complete")
					.andCreateTimeBetween(yestdayStart, yestdayEnd);
			List<HfOrder> hfOrderYesterday = hfOrderMapper.selectByExample(example2);

			example2.clear();
			example2.createCriteria().andStoneIdEqualTo(hfStone.getId()).andOrderStatusEqualTo("complete")
					.andCreateTimeBetween(mouthStart, mouthEnd);
			List<HfOrder> hfOrderMouths = hfOrderMapper.selectByExample(example2);

			example2.clear();
			example2.createCriteria().andStoneIdEqualTo(hfStone.getId()).andOrderStatusEqualTo("complete")
					.andCreateTimeBetween(lastMouthStart, lastMouthEnd);
			List<HfOrder> hfOrderLastMouths = hfOrderMapper.selectByExample(example2);

			for (int j = 0; j < hfOrderDays.size(); j++) {
				HfOrder order = hfOrderDays.get(j);
				paymentCountDay.add(order.getUserId());
				amountDay += order.getAmount();
			}

			orderCountDay += hfOrderDays.size();

			for (int j = 0; j < hfOrderYesterday.size(); j++) {
				HfOrder order = hfOrderYesterday.get(j);
				paymentCountYestday.add(order.getUserId());
			}

			orderCountYestday += hfOrderYesterday.size();

			for (int j = 0; j < hfOrderMouths.size(); j++) {
				HfOrder order = hfOrderMouths.get(j);
				paymentCountMouth.add(order.getUserId());
				amountMouth += order.getAmount();
			}

			orderCountMouth += hfOrderMouths.size();

			for (int j = 0; j < hfOrderLastMouths.size(); j++) {
				HfOrder order = hfOrderLastMouths.get(j);
				paymentCountLastMouth.add(order.getUserId());
			}

			orderCountLastMouth += hfOrderLastMouths.size();
		}
		HashSet h = new HashSet(paymentCountDay);
		paymentCountDay.clear();
		paymentCountDay.addAll(h);
		h = new HashSet(paymentCountYestday);
		paymentCountYestday.clear();
		paymentCountYestday.addAll(h);
		h = new HashSet(paymentCountMouth);
		paymentCountMouth.clear();
		paymentCountMouth.addAll(h);
		h = new HashSet(paymentCountLastMouth);
		paymentCountLastMouth.clear();
		paymentCountLastMouth.addAll(h);
		HfUserBrowseRecordExample browseRecordExample = new HfUserBrowseRecordExample();
		browseRecordExample.createCriteria().andBossIdEqualTo(bossId).andBrowseDateBetween(dayStart, dayEnd);
		List<HfUserBrowseRecord> browseCountsDay = hfUserBrowseRecordMapper.selectByExample(browseRecordExample);
		browseRecordExample.clear();
		browseRecordExample.createCriteria().andBossIdEqualTo(bossId).andBrowseDateBetween(yestdayStart, yestdayEnd);
		List<HfUserBrowseRecord> browseCountsYestday = hfUserBrowseRecordMapper.selectByExample(browseRecordExample);
		browseRecordExample.clear();
		browseRecordExample.createCriteria().andBossIdEqualTo(bossId).andBrowseDateBetween(mouthStart, mouthEnd);
		List<HfUserBrowseRecord> browseCountsMouth = hfUserBrowseRecordMapper.selectByExample(browseRecordExample);
		browseRecordExample.clear();
		browseRecordExample.createCriteria().andBossIdEqualTo(bossId).andBrowseDateBetween(lastMouthStart,
				lastMouthEnd);
		List<HfUserBrowseRecord> browseCountsLastMouth = hfUserBrowseRecordMapper.selectByExample(browseRecordExample);
		HomePageInfo info = new HomePageInfo();
		info.setAmountDay(amountDay);
		info.setOrderCountsDay(orderCountDay);
		info.setOrderCountsYestday(orderCountYestday);
		info.setPaymentConutsDay(paymentCountDay.size());
		info.setPaymentConutsYestday(paymentCountYestday.size());
		info.setBrowseCountsDay(browseCountsDay.size());
		info.setBrowseCountsYestday(browseCountsYestday.size());
		info.setAmountMouth(amountMouth);
		info.setOrderConutsMouth(orderCountMouth);
		info.setOrderConutsLastMouth(orderCountLastMouth);
		info.setPaymentConutsMouth(paymentCountMouth.size());
		info.setPaymentConutsLastMouth(paymentCountLastMouth.size());
		info.setBrowseCountsMouth(browseCountsMouth.size());
		info.setBrowseCountsLastMouth(browseCountsLastMouth.size());
		return builder.body(ResponseUtils.getResponseBody(info));
	}

	@ApiOperation(value = "获取首页销量排行数据", notes = "获取首页销量排行数据")
	@RequestMapping(value = "/findSalesVolumeData", method = RequestMethod.GET)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "bossId", value = "bossId", required = true, type = "Integer") })
	public ResponseEntity<JSONObject> findSalesVolumeData(Integer bossId) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		Integer salesCountAll = 0;
		List<HomePageInfo> infos = new ArrayList<HomePageInfo>();
		HfStoneExample example = new HfStoneExample();
		example.createCriteria().andBossIdEqualTo(bossId);
		List<HfStone> list = hfStoneMapper.selectByExample(example);
		List<Integer> stoneId = list.stream().map(HfStone::getId).collect(Collectors.toList());
		HfOrderExample example2 = new HfOrderExample();
		example2.createCriteria().andStoneIdIn(stoneId);
		List<HfOrder> orders = hfOrderMapper.selectByExample(example2);
		List<Integer> orderId = orders.stream().map(HfOrder::getId).collect(Collectors.toList());
		HfOrderDetailExample example3 = new HfOrderDetailExample();
		example3.createCriteria().andOrderIdIn(orderId);
		List<HfOrderDetail> hfOrderDetails = hfOrderDetailMapper.selectByExample(example3);
		List<Integer> orderDetailId = hfOrderDetails.stream().map(HfOrderDetail::getId).collect(Collectors.toList());
		List<HomePageInfo> result = homePageDao.findSalesVolume(orderDetailId);
		List<Integer> productId = new ArrayList<Integer>();
		for (int i = 0; i < result.size(); i++) {
			HomePageInfo info = result.get(i);
			HfGoods goods = hfGoodsMapper.selectByPrimaryKey(info.getGoodId());
			if (goods != null) {
				info.setProductId(goods.getProductId());
				productId.add(goods.getProductId());
			}
		}
		HashSet h = new HashSet(productId);
		productId.clear();
		productId.addAll(h);
		for (int i = 0; i < productId.size(); i++) {
			Product product = productMapper.selectByPrimaryKey(productId.get(i));
			salesCountAll = 0;
			List<HomePageInfo> pageInfos = new ArrayList<HomePageInfo>();
			for (int j = 0; j < result.size(); j++) {
				if (productId.get(i) == result.get(j).getProductId()) {
					salesCountAll += result.get(j).getSalesCount();
					pageInfos.add(result.get(j));
				}
			}
			HomePageInfo info = new HomePageInfo();
			info.setSalesCountAll(salesCountAll);
			info.setProductId(productId.get(i));
			info.setGoodsInfo(pageInfos);
			info.setProductName(product.getHfName());
			infos.add(info);
		}
		infos.sort(new Comparator<HomePageInfo>() {// Comparator 比较器. 需要实现比较方法
			@Override
			public int compare(HomePageInfo o1, HomePageInfo o2) {
				return o2.getSalesCountAll() - o1.getSalesCountAll();// 从小到大 , 如果是o2.age-o1.age 则表示从大到小
			}
		});
		return builder.body(ResponseUtils.getResponseBody(infos));
	}

	@ApiOperation(value = "获取首页订单类型数据", notes = "获取首页订单类型数据")
	@RequestMapping(value = "/findOrderTypeData", method = RequestMethod.GET)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "bossId", value = "bossId", required = true, type = "Integer") })
	public ResponseEntity<JSONObject> findOrderTypeData(Integer bossId) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		HomePageOrderType info = new HomePageOrderType();
		List<HomePageOrderType> result = new ArrayList<HomePageOrderType>();
		HfStoneExample example = new HfStoneExample();
		example.createCriteria().andBossIdEqualTo(bossId);
		List<HfStone> list = hfStoneMapper.selectByExample(example);
		List<Integer> stoneId = list.stream().map(HfStone::getId).collect(Collectors.toList());
		HfOrderExample example2 = new HfOrderExample();
		example2.createCriteria().andStoneIdIn(stoneId);
		List<HfOrder> orders = hfOrderMapper.selectByExample(example2);
		List<Integer> orderId = orders.stream().map(HfOrder::getId).collect(Collectors.toList());
		List<HomePageInfo> homePageInfos = homePageDao.findOrderTypeCount(orderId);
		String[] str = new String[homePageInfos.size()];
		Integer[] str2 = new Integer[homePageInfos.size()];
		for (int i = 0; i < homePageInfos.size(); i++) {
			HomePageOrderType homePageOrderType = new HomePageOrderType();
			if ("nomalOrder".equals(homePageInfos.get(i).getOrderType())) {
				homePageOrderType.setName("普通订单");
				str[i] = "普通订单";
			}
			if ("rechargeOrder".equals(homePageInfos.get(i).getOrderType())) {
				homePageOrderType.setName("充值订单");
				str[i] = "充值订单";
			}
			if ("shoppingOrder".equals(homePageInfos.get(i).getOrderType())) {
				homePageOrderType.setName("到店支付订单");
				str[i] = "到店支付订单";
			}
			homePageOrderType.setValue(homePageInfos.get(i).getOrderTypeCounts());
//			str2[i] = homePageInfos.get(i).getOrderTypeCounts();
			result.add(homePageOrderType);
		}
		info.setJs(JSONArray.parseArray(JSON.toJSONString(result)));
		info.setData(str);
//		info.setOrderTypeCountsStr(str2);
		return builder.body(ResponseUtils.getResponseBody(info));
	}

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
		HfOrderExample example2 = new HfOrderExample();
		example2.createCriteria().andStoneIdIn(stoneId);
		List<HfOrder> orders = hfOrderMapper.selectByExample(example2);
		List<Integer> orderId = orders.stream().map(HfOrder::getId).collect(Collectors.toList());
		HfOrderDetailExample example3 = new HfOrderDetailExample();
		example3.createCriteria().andOrderIdIn(orderId);
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
				Product product = productMapper.selectByPrimaryKey(browse.getProductId());
				List<HfGoodsDisplayInfo> hfGoodsDisplay = hfGoodsDisplayDao.selectHfGoodsDisplay(browse.getProductId());
				if (!hfGoodsDisplay.isEmpty()) {
					if (hfGoodsDisplay.get(0).getStoneId() != null) {
						display.setStoneId(hfGoodsDisplay.get(0).getStoneId());
						HfStone hfStone = hfStoneMapper.selectByPrimaryKey(hfGoodsDisplay.get(0).getStoneId());
						display.setStoneName(hfStone.getHfName());
					}
				}
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
				List<HfGoodsDisplayInfo> hfGoodsDisplay = hfGoodsDisplayDao.selectHfGoodsDisplay(browse.getProductId());
				if (!hfGoodsDisplay.isEmpty()) {
					if (hfGoodsDisplay.get(0).getStoneId() != null) {
						display.setStoneId(hfGoodsDisplay.get(0).getStoneId());
						HfStone hfStone = hfStoneMapper.selectByPrimaryKey(hfGoodsDisplay.get(0).getStoneId());
						display.setStoneName(hfStone.getHfName());
					}
				}
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
				HfStoneConcern concern = browses.get(j);
				StoneConcernInfo concernInfo = new StoneConcernInfo();
				HfStone stone = hfStoneMapper.selectByPrimaryKey(concern.getStoneId());
				concernInfo.setName(stone.getHfName());
				concernInfo.setStoneId(concern.getStoneId());
				concernInfo.setCreateTime(stone.getCreateTime());
				concernInfo.setStoneDesc(stone.getHfDesc());
				concernInfos.add(concernInfo);
			}
			info.setStoneInfo(concernInfos);
			info.setDate(str);
			info.setUserId(userId);
			result.add(info);
		}
		return builder.body(ResponseUtils.getResponseBody(result));
	}
}
