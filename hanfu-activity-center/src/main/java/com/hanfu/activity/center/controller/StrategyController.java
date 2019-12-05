package com.hanfu.activity.center.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.hanfu.activity.center.dao.ActivityComponyMapper;
import com.hanfu.activity.center.dao.ActivityEvaluateTemplateMapper;
import com.hanfu.activity.center.dao.ActivityUserEvaluateMapper;
import com.hanfu.activity.center.dao.FileDescMapper;
import com.hanfu.activity.center.dao.StrategyRuleMapper;
import com.hanfu.activity.center.dao.StrategyRuleRelateMapper;
import com.hanfu.activity.center.manual.dao.StrategyRuleDao;
import com.hanfu.activity.center.model.ActivityCompony;
import com.hanfu.activity.center.model.ActivityEvaluateTemplate;
import com.hanfu.activity.center.model.ActivityEvaluateTemplateExample;
import com.hanfu.activity.center.model.ActivityUserEvaluate;
import com.hanfu.activity.center.model.ActivityUserEvaluateExample;
import com.hanfu.activity.center.model.FileDesc;
import com.hanfu.activity.center.model.FileDescExample;
import com.hanfu.activity.center.model.StrategyRule;
import com.hanfu.activity.center.model.StrategyRuleExample;
import com.hanfu.activity.center.model.StrategyRuleRelate;
import com.hanfu.activity.center.request.ActivityCompanyRequest;
import com.hanfu.activity.center.request.ActivityEvaluateTemplateRequest;
import com.hanfu.activity.center.request.ActivityUserEvaluateRequest;
import com.hanfu.activity.center.request.StrategyRuleRelateRequest;
import com.hanfu.activity.center.request.StrategyRuleRequest;
import com.hanfu.common.service.FileMangeService;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseUtils;

import ch.qos.logback.classic.Logger;

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

	@Autowired
	private FileDescMapper fileDescMapper;

	@Autowired
	private StrategyRuleDao strategyRuleDao;

	@Autowired
	private ActivityEvaluateTemplateMapper activityEvaluateTemplateMapper;

	@Autowired
	private ActivityUserEvaluateMapper activityUserEvaluateMapper;
	
	@Autowired
	private ActivityComponyMapper activityComponyMapper;

	@ApiOperation(value = "查询策略规则", notes = "公司每次举行活动的策略规则")
	@RequestMapping(value = "/listStrategyRule", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> listStrategyRule() throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
//		StrategyRuleExample example = new StrategyRuleExample();
//		example.setDistinct(true);
		List<String> list = strategyRuleDao.findRuleType();
		for (int i = 0; i < list.size(); i++) {
			String str = list.get(i);
			if("elector".equals(list.get(i))) {
				list.set(i, "投票人");
			}
			if("elected".equals(list.get(i))) {
				list.set(i, "被投票人");
			}
			if("vote_ticket_count".equals(list.get(i))) {
				list.set(i, "星星投票");
			}
			if("record_score".equals(list.get(i))) {
				list.set(i, "线上线下评分");
			}
			if("internal_election".equals(list.get(i))) {
				list.set(i, "内部选举");
			}
			if("public_praise".equals(list.get(i))) {
				list.set(i, "公共选举");
			}
		}
		return builder.body(ResponseUtils.getResponseBody(list));
	}

	@ApiOperation(value = "删除策略规则", notes = "公司每次举行策略规则的删除")
	@RequestMapping(value = "/deleteStrategyRule", method = RequestMethod.POST)
//	@ApiImplicitParams({
//			@ApiImplicitParam(paramType = "query", name = "strategyRuleId", value = "策略规则id", required = true, type = "Integer") })
	public ResponseEntity<JSONObject> deleteStrategyRule(@RequestParam Integer strategyRuleId) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		return builder.body(ResponseUtils.getResponseBody(strategyRuleMapper.deleteByPrimaryKey(strategyRuleId)));
	}

	@ApiOperation(value = "增加轮播图", notes = "增加轮播图")
	@RequestMapping(value = "/addlunbotu", method = RequestMethod.POST)
	public void addlunbotu(MultipartFile file, Integer userId) throws JSONException, IOException {
		FileMangeService fileMangeService = new FileMangeService();
		String arr[];
		arr = fileMangeService.uploadFile(file.getBytes(), String.valueOf(userId));
		FileDesc fileDesc = new FileDesc();
		fileDesc.setFileName("lunbotu");
		fileDesc.setGroupName(arr[0]);
		fileDesc.setRemoteFilename(arr[1]);
//		fileDesc.setUserId(userId);
		fileDesc.setCreateTime(LocalDateTime.now());
		fileDesc.setModifyTime(LocalDateTime.now());
		fileDesc.setIsDeleted((short) 0);
		fileDescMapper.insert(fileDesc);
	}

	@ApiOperation(value = "获取轮播图", notes = "获取轮播图")
	@RequestMapping(value = "/findlunbotu", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> findlunbotu() throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		FileDescExample example = new FileDescExample();
		example.createCriteria().andFileNameEqualTo("lunbotu");
		return builder.body(ResponseUtils.getResponseBody(fileDescMapper.selectByExample(example)));
	}

	@ApiOperation(value = "删除图片", notes = "删除图片")
	@RequestMapping(value = "/deletelunbotu", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> deletelunbotu(@RequestParam Integer fileId) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		FileMangeService fileMangeService = new FileMangeService();
		FileDesc fileDesc = fileDescMapper.selectByPrimaryKey(fileId);
		fileMangeService.deleteFile(fileDesc.getGroupName(), fileDesc.getRemoteFilename());
		fileDescMapper.deleteByPrimaryKey(fileId);
		return builder.body(ResponseUtils.getResponseBody(null));
	}

	@RequestMapping(path = "/addUserEvaluationTemplate", method = RequestMethod.POST)
	@ApiOperation(value = "增加用户评价模板", notes = "增加用户评价模板")
	public ResponseEntity<JSONObject> addUserEvaluationTemplate(ActivityEvaluateTemplateRequest request)
			throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		ActivityEvaluateTemplate template = new ActivityEvaluateTemplate();
		template.setEvaluateContent(request.getEvaluateContent());
		template.setEvaluateType(request.getEvaluateType());
		ActivityEvaluateTemplateExample example = new ActivityEvaluateTemplateExample();
		example.createCriteria().andParentTemplateIdEqualTo(request.getParentTemplateId());
		List<ActivityEvaluateTemplate> list = activityEvaluateTemplateMapper.selectByExample(example);
		Double count = 0.0;
		if(Double.valueOf(request.getEvaluateWeight()) < 0.00 || Double.valueOf(request.getEvaluateWeight()) > 1.00) {
			return builder.body(ResponseUtils.getResponseBody("非法，比重分配超过1"));
		}
		for (int i = 0; i < list.size(); i++) {
			ActivityEvaluateTemplate template1 = list.get(i);
			count = count + Double.valueOf(template1.getEvaluateWeight());
			if(count + Double.valueOf(request.getEvaluateWeight()) > 1) {
				return builder.body(ResponseUtils.getResponseBody("非法，比重分配超过1"));
			}
		}
		template.setEvaluateWeight(request.getEvaluateWeight());
		template.setParentTemplateId(request.getParentTemplateId());
		template.setCreateTime(LocalDateTime.now());
		template.setModifyTime(LocalDateTime.now());
		template.setIsDeleted((short) 0);
		activityEvaluateTemplateMapper.insert(template);
		return builder.body(ResponseUtils.getResponseBody(template.getId()));
	}
	
	@RequestMapping(path = "/findEvaluationTemplateWeight", method = RequestMethod.GET)
	@ApiOperation(value = "查询现有模板权重", notes = "查询现有模板权重")
	public ResponseEntity<JSONObject> findEvaluationTemplateWeight(@RequestParam Integer activityId)
			throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		Double weight = 0.0;
		ActivityEvaluateTemplateExample example = new ActivityEvaluateTemplateExample();
		example.createCriteria().andParentTemplateIdEqualTo(activityId);
		List<ActivityEvaluateTemplate> list = activityEvaluateTemplateMapper.selectByExample(example);
		for (int i = 0; i < list.size(); i++) {
			ActivityEvaluateTemplate activityEvaluateTemplate = list.get(0);
			weight = weight + Double.valueOf(activityEvaluateTemplate.getEvaluateWeight());
		}
		return builder.body(ResponseUtils.getResponseBody(weight));
	}

	@RequestMapping(path = "/delterUserEvaluationTemplate", method = RequestMethod.GET)
	@ApiOperation(value = "删除用户评价模板", notes = "删除用户评价模板")
	public ResponseEntity<JSONObject> delterUserEvaluationTemplate(ActivityEvaluateTemplateRequest request)
			throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		ActivityEvaluateTemplate activityEvaluateTemplate = activityEvaluateTemplateMapper
				.selectByPrimaryKey(request.getId());
		if (activityEvaluateTemplate == null) {
			return builder.body(ResponseUtils.getResponseBody("此模板不存在"));
		}
		activityEvaluateTemplateMapper.deleteByPrimaryKey(request.getId());
		return builder.body(ResponseUtils.getResponseBody(activityEvaluateTemplate.getId()));
	}

	@RequestMapping(path = "/updateUserEvaluationTemplate", method = RequestMethod.POST)
	@ApiOperation(value = "修改用户评价模板", notes = "修改用户评价模板")
	public ResponseEntity<JSONObject> updateUserEvaluationTemplate(ActivityEvaluateTemplateRequest request)
			throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		ActivityEvaluateTemplateExample example = new ActivityEvaluateTemplateExample();
		example.createCriteria().andParentTemplateIdEqualTo(request.getParentTemplateId());
		List<ActivityEvaluateTemplate> list = activityEvaluateTemplateMapper.selectByExample(example);
		Integer count = 0;
		if(Double.valueOf(request.getEvaluateWeight()) < 0.00 || Double.valueOf(request.getEvaluateWeight()) > 1.00) {
			return builder.body(ResponseUtils.getResponseBody("非法，比重分配超过1"));
		}
		for (int i = 0; i < list.size(); i++) {
			ActivityEvaluateTemplate template = list.get(i);
			count = count + Integer.valueOf(template.getEvaluateWeight());
			if(count + Double.valueOf(request.getEvaluateWeight()) > 1) {
				return builder.body(ResponseUtils.getResponseBody("非法，比重分配超过1"));
			}
		}
		ActivityEvaluateTemplate activityEvaluateTemplate = activityEvaluateTemplateMapper
				.selectByPrimaryKey(request.getId());
		if (activityEvaluateTemplate == null) {
			return builder.body(ResponseUtils.getResponseBody("此模板不存在"));
		}
		activityEvaluateTemplate.setEvaluateContent(request.getEvaluateContent());
		activityEvaluateTemplate.setEvaluateType(request.getEvaluateType());
		activityEvaluateTemplate.setEvaluateWeight(request.getEvaluateWeight());
		activityEvaluateTemplate.setModifyTime(LocalDateTime.now());
		activityEvaluateTemplateMapper.updateByPrimaryKey(activityEvaluateTemplate);
		return builder.body(ResponseUtils.getResponseBody(activityEvaluateTemplate.getId()));
	}

	@RequestMapping(path = "/findUserEvaluationTemplate", method = RequestMethod.GET)
	@ApiOperation(value = "查询用户评价模板", notes = "查询用户评价模板")
	public ResponseEntity<JSONObject> findUserEvaluationTemplate(@RequestParam Integer activityId) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		ActivityEvaluateTemplateExample example = new ActivityEvaluateTemplateExample();
		example.createCriteria().andParentTemplateIdEqualTo(activityId);
		List<ActivityEvaluateTemplate> list = activityEvaluateTemplateMapper.selectByExample(example);
		return builder.body(ResponseUtils.getResponseBody(list));
	}

	@RequestMapping(path = "/userAddEvaluation", method = RequestMethod.POST)
	@ApiOperation(value = "用户填写评价", notes = "用户填写评价")
	public ResponseEntity<JSONObject> userAddEvaluation(ActivityUserEvaluateRequest request) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		ActivityUserEvaluateExample example = new ActivityUserEvaluateExample();
		example.createCriteria().andUserIdEqualTo(request.getUserId()).andEvaluateTemplateIdEqualTo(request.getEvaluateTemplateId());
		List<ActivityUserEvaluate> list = activityUserEvaluateMapper.selectByExample(example);
		if(!list.isEmpty()) {
			userUpdateEvaluation(list.get(0).getId(),request.getEvaluateContent());
		}
		ActivityUserEvaluate activityUserEvaluate = new ActivityUserEvaluate();
		activityUserEvaluate.setUserId(request.getUserId());
		activityUserEvaluate.setEvaluateTemplateId(request.getEvaluateTemplateId());
		activityUserEvaluate.setEvaluateContent(request.getEvaluateContent());
//			activityUserEvaluate.setEvaluateResult(request.getEvaluateResult());
		activityUserEvaluate.setCreateTime(LocalDateTime.now());
		activityUserEvaluate.setModifyTime(LocalDateTime.now());
		activityUserEvaluate.setIsDeleted((short) 0);
		activityUserEvaluateMapper.insert(activityUserEvaluate);
		return builder.body(ResponseUtils.getResponseBody(activityUserEvaluate.getId()));
	}

	@RequestMapping(path = "/userUpdateEvaluation", method = RequestMethod.POST)
	@ApiOperation(value = "用户更新个人评价", notes = "用户更新个人评价")
	public ResponseEntity<JSONObject> userUpdateEvaluation(@RequestParam Integer userEvaluateId,@RequestParam String evaluateContent) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		ActivityUserEvaluate activityUserEvaluate = activityUserEvaluateMapper.selectByPrimaryKey(userEvaluateId);
		if (activityUserEvaluate == null) {
			return builder.body(ResponseUtils.getResponseBody("异常异常"));
		}
		activityUserEvaluate.setEvaluateContent(evaluateContent);
		activityUserEvaluateMapper.updateByPrimaryKey(activityUserEvaluate);
		return builder.body(ResponseUtils.getResponseBody(activityUserEvaluate.getId()));
	}
	
	@RequestMapping(path = "/addCompany", method = RequestMethod.POST)
	@ApiOperation(value = "添加公司", notes = "添加公司")
	public ResponseEntity<JSONObject> addCompany(ActivityCompanyRequest request) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		ActivityCompony compony = new ActivityCompony();
		compony.setCompanyName(request.getCompanyName());
		compony.setCompanyInfo(request.getCompanyInfo());
		compony.setCreateTime(LocalDateTime.now());
		compony.setModifyTime(LocalDateTime.now());
		compony.setRemarks(request.getRemarks());
		compony.setIsDeleted((short) 0);
		activityComponyMapper.insert(compony);
		return builder.body(ResponseUtils.getResponseBody(compony.getId()));
	}
	
	@RequestMapping(path = "/findCompany", method = RequestMethod.POST)
	@ApiOperation(value = "查询公司", notes = "查询公司")
	public ResponseEntity<JSONObject> addCompany() throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		return builder.body(ResponseUtils.getResponseBody(activityComponyMapper.selectByExample(null)));
	}
}
