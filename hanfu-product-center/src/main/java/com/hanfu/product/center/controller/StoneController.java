package com.hanfu.product.center.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.hanfu.product.center.dao.HfStoneMapper;
import com.hanfu.product.center.model.HfStone;
import com.hanfu.product.center.model.HfStoneExample; 
import com.hanfu.product.center.request.HfStoneRequest; 
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseEntity.BodyBuilder;
import com.hanfu.utils.response.handler.ResponseUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/stone")
@Api
public class StoneController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private HfStoneMapper hfStoneMapper;

	@ApiOperation(value = "获取店铺列表", notes = "根据商家或缺店铺列表")
	@RequestMapping(value = "/byBossId", method = RequestMethod.GET)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "bossId", value = "商家ID", required = true, type = "Integer") })
	public ResponseEntity<JSONObject> listStone(@RequestParam(name = "bossId") Integer bossId) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		HfStoneExample example = new HfStoneExample();
		example.createCriteria().andBossIdEqualTo(bossId);
		return builder.body(ResponseUtils.getResponseBody(hfStoneMapper.selectByExample(example)));
	}
	
	
	@ApiOperation(value = "添加商铺", notes = "添加一个新的商铺")
	@RequestMapping(value = "/addStone", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> addProductInfo(HfStoneRequest request) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		HfStone item = new HfStone();
		item.setHfName(request.getHfName());
		item.setBossId(request.getStoneManagerId());
		item.setUserId(request.getUserId());
		item.setHfDesc(request.getStoneDesc());
		item.setHfStatus(request.getStoneStatus());
		return builder.body(ResponseUtils.getResponseBody(hfStoneMapper.insert(item)));
	}
	
	@ApiOperation(value = "删除商铺", notes = "删除商铺")
	@RequestMapping(value = "/deleteStone", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> deleteStone(Integer stoneId) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		return builder.body(ResponseUtils.getResponseBody(hfStoneMapper.deleteByPrimaryKey(stoneId)));
	}
	
	@ApiOperation(value = "修改商铺", notes = "修改商铺")
	@RequestMapping(value = "/updateStone", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> updateStone(HfStone hfStone) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		HfStoneExample example = new HfStoneExample();
		return builder.body(ResponseUtils.getResponseBody(hfStoneMapper.updateByExample(hfStone, example)));
	}
	
}
