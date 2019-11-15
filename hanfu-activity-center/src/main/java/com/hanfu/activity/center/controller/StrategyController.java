package com.hanfu.activity.center.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.hanfu.activity.center.dao.StrategyRuleMapper;
import com.hanfu.activity.center.dao.StrategyRuleRelateMapper;
import com.hanfu.activity.center.model.StrategyRule;
import com.hanfu.activity.center.model.StrategyRuleRelate;
import com.hanfu.activity.center.request.StrategyRuleRelateRequest;
import com.hanfu.activity.center.request.StrategyRuleRequest;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseUtils;
import com.hanfu.utils.response.handler.ResponseEntity.BodyBuilder;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/strategy")
@Api
public class StrategyController {

	@Autowired
	private StrategyRuleRelateMapper strategyRuleRelateMapper;
	
	@Autowired
	private StrategyRuleMapper strategyRuleMapper;
	
	@ApiOperation(value = "查询策略规则关系", notes = "公司每次举行活动的策略规则关系")
	@RequestMapping(value = "/listStrategyRuleRelate", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> listStrategyRuleRelate() throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		return builder.body(ResponseUtils.getResponseBody(strategyRuleRelateMapper.selectByExample(null)));
	}
	
	@ApiOperation(value = "增加策略规则关系", notes = "公司每次举行活动的策略规则关系")
	@RequestMapping(value = "/addStrategyRuleRelate", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> addStrategyRuleRelate(StrategyRuleRelateRequest request) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		StrategyRuleRelate strategyRuleRelate = new StrategyRuleRelate();
		strategyRuleRelate.setStrategyId(request.getStrategyId());
		strategyRuleRelate.setStrategyRuleId(request.getStrategyRuleId());
		strategyRuleRelate.setIsUsed(request.isUsed());
		strategyRuleRelate.setCreateTime(LocalDateTime.now());
		strategyRuleRelate.setModifyTime(LocalDateTime.now());
		strategyRuleRelate.setIsDeleted((short) 0);
		return builder.body(ResponseUtils.getResponseBody(strategyRuleRelateMapper.insert(strategyRuleRelate)));
	}

	@ApiOperation(value = "删除策略规则关系", notes = "公司每次举行策略规则关系的删除")
	@RequestMapping(value = "/deleteStrategyRuleRelate", method = RequestMethod.POST)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "strategyRuleRelateId", value = "策略规则关系id", required = true, type = "Integer") })
	public ResponseEntity<JSONObject> deleteStrategyRuleRelate(@RequestParam Integer strategyRuleRelateId) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		return builder.body(ResponseUtils.getResponseBody(strategyRuleRelateMapper.deleteByPrimaryKey(strategyRuleRelateId)));
	}

	@ApiOperation(value = "修改策略规则关系", notes = "公司每次举行策略规则关系的修改")
	@RequestMapping(value = "/updateStrategyRuleRelate", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> updateStrategyRuleRelate(StrategyRuleRelateRequest request) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		StrategyRuleRelate strategyRuleRelate = strategyRuleRelateMapper.selectByPrimaryKey(request.getId());
		if (strategyRuleRelate == null) {
			throw new Exception("此活动策略关系不存在");
		}
		if (!StringUtils.isEmpty(request.getStrategyId())) {
			strategyRuleRelate.setStrategyId(request.getStrategyId());
		}
		if (!StringUtils.isEmpty(request.getStrategyRuleId())) {
			strategyRuleRelate.setStrategyRuleId(request.getStrategyRuleId());
		}
		if (!StringUtils.isEmpty(request.isUsed())) {
			strategyRuleRelate.setIsUsed(request.isUsed());
		}
		strategyRuleRelate.setModifyTime(LocalDateTime.now());
		return builder.body(ResponseUtils.getResponseBody(strategyRuleRelateMapper.updateByPrimaryKey(strategyRuleRelate)));
	}
	
	
	
	@ApiOperation(value = "查询策略规则", notes = "公司每次举行活动的策略规则")
	@RequestMapping(value = "/listStrategyRule", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> listStrategyRule() throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		return builder.body(ResponseUtils.getResponseBody(strategyRuleMapper.selectByExample(null)));
	}
	
	@ApiOperation(value = "增加策略规则", notes = "公司每次举行活动的策略规则")
	@RequestMapping(value = "/addStrategyRule", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> addStrategyRule(StrategyRuleRequest request) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		StrategyRule strategyRule = new StrategyRule();
		strategyRule.setRuleName(request.getRuleName());
		strategyRule.setRuleDesc(request.getRuleDesc());
//		strategyRule.setRuleStatus(request.getRuleStatus());
		strategyRule.setRuleType(request.getRuleType());
//		strategyRule.setRuelValueType(request.getRuelValueType());
		strategyRule.setCreateTime(LocalDateTime.now());
		strategyRule.setModifyTime(LocalDateTime.now());
		strategyRule.setIsDeleted((short) 0);
		return builder.body(ResponseUtils.getResponseBody(strategyRuleMapper.insert(strategyRule)));
	}

	@ApiOperation(value = "删除策略规则", notes = "公司每次举行策略规则的删除")
	@RequestMapping(value = "/deleteStrategyRule", method = RequestMethod.POST)
//	@ApiImplicitParams({
//			@ApiImplicitParam(paramType = "query", name = "strategyRuleId", value = "策略规则id", required = true, type = "Integer") })
	public ResponseEntity<JSONObject> deleteStrategyRule(@RequestParam Integer strategyRuleId) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		return builder.body(ResponseUtils.getResponseBody(strategyRuleMapper.deleteByPrimaryKey(strategyRuleId)));
	}

	@ApiOperation(value = "修改策略规则", notes = "公司每次举行策略规则的修改")
	@RequestMapping(value = "/updateStrategyRule", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> updateStrategyRule(StrategyRuleRequest request) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		StrategyRule strategyRule = strategyRuleMapper.selectByPrimaryKey(request.getId());
		if (strategyRule == null) {
			throw new Exception("此策略规则不存在");
		}
		if (!StringUtils.isEmpty(request.getRuleName())) {
			strategyRule.setRuleName(request.getRuleName());
		}
		if (!StringUtils.isEmpty(request.getRuleDesc())) {
			strategyRule.setRuleDesc(request.getRuleDesc());
		}
		if (!StringUtils.isEmpty(request.getRuleStatus())) {
			strategyRule.setRuleStatus(request.getRuleStatus());
		}
		if (!StringUtils.isEmpty(request.getRuleType())) {
			strategyRule.setRuleType(request.getRuleType());
		}
		if (!StringUtils.isEmpty(request.getRuleValueType())) {
			strategyRule.setRuelValueType(request.getRuleValueType());
		}
		strategyRule.setModifyTime(LocalDateTime.now());
		return builder.body(ResponseUtils.getResponseBody(strategyRuleMapper.updateByPrimaryKey(strategyRule)));
	}
}
