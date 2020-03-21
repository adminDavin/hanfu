package com.hanfu.product.center.controller;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



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
import com.hanfu.product.center.dao.HfActivityMapper;
import com.hanfu.product.center.dao.HfActivityProductMapper;
import com.hanfu.product.center.manual.dao.ManualDao;
import com.hanfu.product.center.manual.model.ProductActivityInfo;
import com.hanfu.product.center.model.HfActivity;
import com.hanfu.product.center.model.HfActivityProduct;
import com.hanfu.product.center.model.HfActivityProductExample;
import com.hanfu.product.center.request.ProductActivityInfoRequest;
import com.hanfu.product.center.request.ProductActivityRequest;
import com.hanfu.user.center.model.HfUser;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseEntity.BodyBuilder;
import com.hanfu.utils.response.handler.ResponseUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
@RequestMapping("/hfProductActivity")
@Api
public class HfProductActivityController {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ManualDao manualDao;
	
	@Autowired 
	private HfActivityMapper hfActivityMapper;
	
	@Autowired
	private HfActivityProductMapper hfActivityProductMapper;
	
	@ApiOperation(value = "添加活动", notes = "添加活动（秒杀，团购，精选，分销）")
	@RequestMapping(value = "/addProdcutActivity", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> addProdcutActivity(ProductActivityRequest request) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		HfActivity hfActivity = new HfActivity();
		hfActivity.setActivityName(request.getActivityName());
		hfActivity.setActivityType(request.getActivityType());
		if (!StringUtils.isEmpty(request.getStartTime())) {
			Instant instant = request.getStartTime().toInstant();
			ZoneId zoneId = ZoneId.systemDefault();
			LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
			hfActivity.setStartTime(localDateTime);
		}
		if (!StringUtils.isEmpty(request.getEndTime())) {
			Instant instant = request.getEndTime().toInstant();
			ZoneId zoneId = ZoneId.systemDefault();
			LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
			hfActivity.setEndTime(localDateTime);
		}
		HfUser hfUser = manualDao.select(request.getUserId());
		if(hfUser != null) {
			if(hfUser.getNickName() != null) {
				hfActivity.setLastModifier(hfUser.getNickName());
			}
			
		}
		hfActivity.setCreateTime(LocalDateTime.now());
		hfActivity.setModifyTime(LocalDateTime.now());
		hfActivity.setIsDeleted((byte) 0);
		hfActivityMapper.insert(hfActivity);
		return builder.body(ResponseUtils.getResponseBody(hfActivity.getId()));
	}
	
	@ApiOperation(value = "查询活动", notes = "查询活动")
	@RequestMapping(value = "/findProdcutActivity", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> addProdcutActivity(String activityType) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		List<ProductActivityInfo> result = manualDao.selectProductActivityList(activityType);
		return builder.body(ResponseUtils.getResponseBody(result));
	}
	
	@ApiOperation(value = "给活动绑定商品", notes = "给活动绑定商品")
	@RequestMapping(value = "/intoActivityProduct", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> intoActivityProduct(@RequestParam(required = true,name = "活动id") Integer id,
			@RequestParam(required = true,name = "商品id") Integer productId) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		HfActivityProduct hfActivityProduct = new HfActivityProduct();
		hfActivityProduct.setActivityId(id);
		hfActivityProduct.setProductId(productId);
		hfActivityProduct.setCreateTime(LocalDateTime.now());
		hfActivityProduct.setModifyTime(LocalDateTime.now());
		hfActivityProduct.setIsDeleted((byte) 0);
		hfActivityProductMapper.insert(hfActivityProduct);
		return builder.body(ResponseUtils.getResponseBody("添加成功"));
	}
	
	@ApiOperation(value = "查询活动商品列表信息", notes = "查询活动商品列表信息")
	@RequestMapping(value = "/getActivityProductList", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> getActivityProductList(Integer id) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		HfActivityProductExample example = new HfActivityProductExample();
		example.createCriteria().andActivityIdEqualTo(id);
		List<HfActivityProduct> list = hfActivityProductMapper.selectByExample(example);
		return builder.body(ResponseUtils.getResponseBody(list));
	}
	
	@ApiOperation(value = "完善活动商品信息", notes = "完善活动商品信息")
	@RequestMapping(value = "/updateActivityProduct", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> updateActivityProduct(ProductActivityInfoRequest request) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		HfActivityProduct hfActivityProduct = new HfActivityProduct();
		if(!StringUtils.isEmpty(request.getDiscountRatio())) {
			hfActivityProduct.setDiscountRatio(request.getDiscountRatio());
		}
		if(!StringUtils.isEmpty(request.getDistributionRatio())) {
			hfActivityProduct.setDistributionRatio(request.getDistributionRatio());
		}
		if(!StringUtils.isEmpty(request.getFavoravlePrice())) {
			hfActivityProduct.setFavoravlePrice(request.getFavoravlePrice());
		}
		if(!StringUtils.isEmpty(request.getGroupNum())) {
			hfActivityProduct.setGroupNum(request.getGroupNum());
		}
		if(!StringUtils.isEmpty(request.getInventoryCelling())) {
			hfActivityProduct.setInventoryCelling(request.getInventoryCelling());
		}
		hfActivityProduct.setModifyTime(LocalDateTime.now());
		return builder.body(ResponseUtils.getResponseBody("修改成功"));
	}
	
	@ApiOperation(value = "获取商品活动类型", notes = "获取商品活动类型")
	@RequestMapping(value = "/getProdcutActivityType", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> getProdcutActivityType() throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> params1 = new HashMap<String, Object>();
		Map<String, Object> params2 = new HashMap<String, Object>();
		Map<String, Object> params3 = new HashMap<String, Object>();
		params.put("activityType", "seckillActivity");
		params.put("activityDesc", "秒杀");
		params1.put("activityType", "groupActivity");
		params1.put("activityDesc", "团购");

		params2.put("activityType", "seniorityActivity");
		params2.put("activityDesc", "精选");

		params3.put("activityType", "distributionActivity");
		params3.put("activityDesc", "分销");

		List<Object> list = new ArrayList<>();
		list.add(0, params);
		list.add(1, params1);
		list.add(2, params2);
		list.add(3, params3);
		return builder.body(ResponseUtils.getResponseBody(list));
	}

}