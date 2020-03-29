package com.hanfu.product.center.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.hanfu.common.service.FileMangeService;
import com.hanfu.product.center.dao.FileDescMapper;
import com.hanfu.product.center.dao.HfGoodsMapper;
import com.hanfu.product.center.dao.HfGoodsPictrueMapper;
import com.hanfu.product.center.dao.HfGoodsSpecMapper;
import com.hanfu.product.center.dao.HfOrderMapper;
import com.hanfu.product.center.dao.HfPriceMapper;
import com.hanfu.product.center.dao.HfRespMapper;
import com.hanfu.product.center.dao.HfStoneMapper;
import com.hanfu.product.center.dao.HfUserBrowseRecordMapper;
import com.hanfu.product.center.dao.ProductMapper;
import com.hanfu.product.center.dao.ProductSpecMapper;
import com.hanfu.product.center.dao.WarehouseMapper;
import com.hanfu.product.center.manual.model.CheckResp;
import com.hanfu.product.center.manual.model.HfGoodsDisplay;
import com.hanfu.product.center.manual.model.HfGoodsDisplayInfo;
import com.hanfu.product.center.manual.model.HfGoodsSpecDisplay;
import com.hanfu.product.center.manual.model.HfProductDisplay;
import com.hanfu.product.center.manual.model.HomePageInfo;
import com.hanfu.product.center.manual.model.PriceRanking;
import com.hanfu.product.center.manual.model.ProductForValue;
import com.hanfu.product.center.request.GoodsPictrueRequest;
import com.hanfu.product.center.request.GoodsPriceInfo;
import com.hanfu.product.center.request.GoodsSpecRequest;
import com.hanfu.product.center.request.HfGoodsInfo;
import com.hanfu.product.center.request.RespInfo;
import com.hanfu.product.center.service.GoodsRespService;
import com.hanfu.product.center.service.GoodsService;
import com.hanfu.product.center.service.SpecsService;
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
		LocalDateTime mouthStart = LocalDateTime.of(LocalDateTime.now().with(TemporalAdjusters.firstDayOfMonth()).toLocalDate(), LocalTime.MIN);
		LocalDateTime mouthEnd = LocalDateTime.of(LocalDateTime.now().with(TemporalAdjusters.lastDayOfMonth()).toLocalDate(), LocalTime.MAX);
		LocalDateTime lastMouthStart = LocalDateTime.of(LocalDateTime.now().with(TemporalAdjusters.firstDayOfMonth()).toLocalDate(), LocalTime.MIN).plusMonths(-1);
		LocalDateTime lastMouthEnd = LocalDateTime.of(LocalDateTime.now().with(TemporalAdjusters.lastDayOfMonth()).toLocalDate(), LocalTime.MAX).plusMonths(-1);
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
        browseRecordExample.createCriteria().andBossIdEqualTo(bossId).andBrowseDateBetween(lastMouthStart, lastMouthEnd);
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
	public static void main(String[] args) {
		LocalDateTime date = LocalDateTime.now();
		LocalDateTime firstday = LocalDateTime.now().with(TemporalAdjusters.firstDayOfMonth());
		LocalDateTime lastDay = LocalDateTime.now().with(TemporalAdjusters.lastDayOfMonth());
		
		System.out.println(LocalDateTime.now().plusMonths(-1));
	}
}
