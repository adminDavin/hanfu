package com.hanfu.activity.center.controller;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.hanfu.activity.center.dao.ActivitiRuleInstanceMapper;
import com.hanfu.activity.center.dao.ActivitiStrategyMapper;
import com.hanfu.activity.center.dao.ActivityMapper;
import com.hanfu.activity.center.dao.ActivityStrategyInstanceMapper;
import com.hanfu.activity.center.dao.ActivityVoteRecordsMapper;
import com.hanfu.activity.center.dao.FileDescMapper;
import com.hanfu.activity.center.dao.StrategyRuleMapper;
import com.hanfu.activity.center.dao.UserInfoMapper;
import com.hanfu.activity.center.manual.dao.HfUserDao;
import com.hanfu.activity.center.manual.dao.VotePictureDao;
import com.hanfu.activity.center.manual.model.HfUser;
import com.hanfu.activity.center.manual.model.Pictures;
import com.hanfu.activity.center.model.ActivitiRuleInstance;
import com.hanfu.activity.center.model.ActivitiRuleInstanceExample;
import com.hanfu.activity.center.model.ActivitiStrategy;
import com.hanfu.activity.center.model.Activity;
import com.hanfu.activity.center.model.ActivityExample;
import com.hanfu.activity.center.model.ActivityStrategyInstance;
import com.hanfu.activity.center.model.ActivityStrategyInstanceExample;
import com.hanfu.activity.center.model.ActivityVoteRecords;
import com.hanfu.activity.center.model.ActivityVoteRecordsExample;
import com.hanfu.activity.center.model.FileDesc;
import com.hanfu.activity.center.model.StrategyRule;
import com.hanfu.activity.center.model.StrategyRuleExample;
import com.hanfu.activity.center.model.Total;
import com.hanfu.activity.center.model.UserInfo;
import com.hanfu.activity.center.request.ActivityRequest;
import com.hanfu.activity.center.request.ActivityStrategyRequest;
import com.hanfu.activity.center.request.ActivityVoteRecordRequest;
import com.hanfu.activity.center.request.AddActivityUserRequest;
import com.hanfu.activity.center.request.StrategyRuleRequest;
import com.hanfu.activity.center.request.UpdateActivityRuleRequest;
import com.hanfu.activity.center.request.VoteTicketRequest;
import com.hanfu.common.service.FileMangeService;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseEntity.BodyBuilder;
import com.hanfu.utils.response.handler.ResponseUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/wareHouse")
@Api
public class ActivityManagerController {
	private static final String LOCK = "lock";

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ActivityMapper activityMapper;

	@Autowired
	private ActivitiStrategyMapper activitiStrategyMapper;

	@Autowired
	private ActivityStrategyInstanceMapper activityStrategyInstanceMapper;

	@Autowired
	private ActivitiRuleInstanceMapper activitiRuleInstanceMapper;

	@Autowired
	private StrategyRuleMapper strategyRuleMapper;

	@Autowired
	private ActivityVoteRecordsMapper activityVoteRecordsMapper;

	@Autowired
	private HfUserDao hfUserMapper;

	@Autowired
	private VotePictureDao votePictureDao;

	@Autowired
	private FileDescMapper fileDescMapper;

	@Autowired
	private UserInfoMapper userInfoMapper;

	@ApiOperation(value = "1、制定活动策略", notes = "制定活动策略")
	@RequestMapping(value = "/addActivityStrategy", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> addActivityStrategy(ActivityStrategyRequest request) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		ActivitiStrategy activitiStrategy = new ActivitiStrategy();
		activitiStrategy.setStrategyName(request.getStrategyName());
		activitiStrategy.setStrategyDesc(request.getStrategyDesc());
		activitiStrategy.setStrategyType(request.getStrategyType());
		activitiStrategy.setStrategyStatus("使用中");
		activitiStrategy.setCreateTime(LocalDateTime.now());
		activitiStrategy.setModifyTime(LocalDateTime.now());
		activitiStrategy.setIsDeleted((short) 0);
		return builder.body(ResponseUtils.getResponseBody(activitiStrategyMapper.insert(activitiStrategy)));
	}

	@ApiOperation(value = "2 增加策略规则", notes = "公司每次举行活动的策略规则")
	@RequestMapping(value = "/addStrategyRule", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> addStrategyRule(StrategyRuleRequest request) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		StrategyRule strategyRule = new StrategyRule();
		strategyRule.setRuleName(request.getRuleName());
		strategyRule.setRuleDesc(request.getRuleDesc());
		strategyRule.setStrategyId(request.getStrategyId());
		strategyRule.setRuleStatus("生效中");
		String ruleType = request.getRuleType();
		strategyRule.setRuleType(ruleType);
		if ("elector".equals(ruleType) || "elected".equals(ruleType) || "internal selection".equals(ruleType)) {
			strategyRule.setRuelValueType("user_list");
		}
		if ("vote_ticket_count".equals(ruleType)) {
			strategyRule.setRuelValueType("ticket_count");
		}
		if("record_score".equals(ruleType)) {
			strategyRule.setRuelValueType("score");
		}
		strategyRule.setCreateTime(LocalDateTime.now());
		strategyRule.setModifyTime(LocalDateTime.now());
		strategyRule.setIsDeleted((short) 0);
		return builder.body(ResponseUtils.getResponseBody(strategyRuleMapper.insert(strategyRule)));
	}

	@ApiOperation(value = "3 发起活动", notes = "公司每次举行活动的添加")
	@RequestMapping(value = "/addActivity", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> addActivity(ActivityRequest request) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		Activity activity = new Activity();
		activity.setActivityName(request.getActivityName());
		activity.setActivityDesc(request.getActivityDesc());
		activity.setActivityStatus(request.getActivityStatus());
		activity.setActiviyType(request.getActiviyType());
		activity.setStrategyId(request.getStrategyId());
		activity.setUserId(request.getUserId());
		activity.setIsTimingStart((short) 0);
		activity.setCreateTime(LocalDateTime.now());
		activity.setModifyTime(LocalDateTime.now());
		activity.setIsDeleted((short) 0);
		StrategyRuleExample example = new StrategyRuleExample();
		example.createCriteria().andStrategyIdEqualTo(request.getStrategyId());
		List<StrategyRule> list = strategyRuleMapper.selectByExample(example);
		for (int i = 0; i < list.size(); i++) {
			UpdateActivityRuleRequest request2 = new UpdateActivityRuleRequest();
			request2.setActivityId(activity.getId());
			request2.setRuleId(String.valueOf(list.get(i).getId()));
			request2.setRuleName(list.get(i).getRuleName());
			request2.setRuleSDesc(list.get(i).getRuleDesc());
			request2.setRuleValue(list.get(i).getRuleType());
			setActivitiRules(request2);
		}
		return builder.body(ResponseUtils.getResponseBody(activityMapper.insert(activity)));
	}

	@ApiOperation(value = "4 设置活动规则", notes = "设置活动规则")
	@RequestMapping(value = "/setActivityRules", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> setActivitiRules(UpdateActivityRuleRequest request) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		ActivityStrategyInstanceExample example = new ActivityStrategyInstanceExample();
		example.createCriteria().andActivityIdEqualTo(request.getActivityId())
				.andRuleIdEqualTo(Integer.valueOf(request.getRuleId()));
		List<ActivityStrategyInstance> records = activityStrategyInstanceMapper.selectByExample(example);
		ActivityStrategyInstance record;
		if (records != null && !records.isEmpty()) {
			record = records.get(0);
			record.setRuleDesc(request.getRuleSDesc());
			record.setRuleValue(request.getRuleValue());
			record.setModifyTime(LocalDateTime.now());
			activityStrategyInstanceMapper.updateByPrimaryKey(record);
		} else {
			record = new ActivityStrategyInstance();
			Activity activity = activityMapper.selectByPrimaryKey(request.getActivityId());
			StrategyRule strategyRule = strategyRuleMapper.selectByPrimaryKey(Integer.valueOf(request.getRuleId()));
			record.setActivityId(activity.getId());
			record.setRuleId(strategyRule.getId());
			record.setRuleName(request.getRuleName());
			record.setRuleDesc(request.getRuleSDesc());
			record.setRuleValue(request.getRuleValue());
			record.setRuleValueType(strategyRule.getRuelValueType());
			record.setRuleStatus("0");
			record.setCreateTime(LocalDateTime.now());
			record.setModifyTime(LocalDateTime.now());
			record.setIsDeleted((short) 0);
			activityStrategyInstanceMapper.insert(record);
		}
		return builder.body(ResponseUtils.getResponseBody(record));
	}

	@ApiOperation(value = "5 添加活动参与者", notes = "添加活动参与者")
	@RequestMapping(value = "/addActivityUser", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> addActivityUser(AddActivityUserRequest request) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		String arr = "";
		String str = "";
		String id = ":";
		for (int i = 0; i < request.getUserIds().length; i++) {
//			arr = create();
			ActivitiRuleInstanceExample example3 = new ActivitiRuleInstanceExample();
			example3.createCriteria().andActivityIdEqualTo(request.getActivityId())
					.andUserIdEqualTo(request.getUserIds()[i]);
			List<ActivitiRuleInstance> list3 = activitiRuleInstanceMapper.selectByExample(example3);
//			ActivitiRuleInstance recod;
			if (list3.isEmpty()) {
				ActivitiRuleInstance ruleValueDesc = new ActivitiRuleInstance();
				ruleValueDesc.setActivityId(request.getActivityId());

				if ("elector".equals(strategyRuleMapper.selectByPrimaryKey(request.getRuleId()).getRuleType())) {
					ActivityStrategyInstanceExample example = new ActivityStrategyInstanceExample();
					example.createCriteria().andRuleValueTypeEqualTo("ticket_count");
					List<ActivityStrategyInstance> activityStrategyInstance = activityStrategyInstanceMapper
							.selectByExample(example);
					ruleValueDesc.setUserTicketCount(Integer.valueOf(activityStrategyInstance.get(0).getRuleValue()));
				}
				ActivityStrategyInstanceExample strategyInstanceExample = new ActivityStrategyInstanceExample();
				strategyInstanceExample.createCriteria().andRuleIdEqualTo(request.getRuleId());
				ruleValueDesc.setRuleInstanceId(
						activityStrategyInstanceMapper.selectByExample(strategyInstanceExample).get(0).getId());
				ruleValueDesc.setRuleId(request.getRuleId());
				StrategyRule rule = strategyRuleMapper.selectByPrimaryKey(request.getRuleId());
				if ("elector".equals(rule.getRuleType())) {
					ruleValueDesc.setIsElected(false);
				} else if ("elected".equals(rule.getRuleType())) {
					ruleValueDesc.setIsElected(true);
				}
//				ruleValueDesc.setIsElected(isElected);
				ruleValueDesc.setUserId(request.getUserIds()[i]);
//				ruleValueDesc.setRuleInstanceValue(arr);
				ruleValueDesc.setCreateTime(LocalDateTime.now());
				ruleValueDesc.setModifyTime(LocalDateTime.now());
				ruleValueDesc.setIsDeleted((short) 0);
				activitiRuleInstanceMapper.insert(ruleValueDesc);
				if ("user_list".equals(strategyRuleMapper.selectByPrimaryKey(request.getRuleId()).getRuelValueType())) {
					ActivityStrategyInstanceExample example = new ActivityStrategyInstanceExample();
					example.createCriteria().andRuleIdEqualTo(request.getRuleId());
					List<ActivityStrategyInstance> activityStrategyInstance1 = activityStrategyInstanceMapper
							.selectByExample(example);
					if (Integer.valueOf(activityStrategyInstance1.get(0).getRuleValue()) <= 0) {
						return builder.body(ResponseUtils.getResponseBody("超过限定人数"));
					}
					activityStrategyInstance1.get(0).setRuleValue(
							String.valueOf(Integer.valueOf(activityStrategyInstance1.get(0).getRuleValue()) - 1));
					activityStrategyInstanceMapper.updateByPrimaryKey(activityStrategyInstance1.get(0));
				}
			} else {
				id = id + list3.get(0).getUserId() + ",";
			}
//			arr = arr + ",";
//			str = arr + str;
		}
//		str = str + id;
		return builder.body(ResponseUtils.getResponseBody(id));
	}

	@ApiOperation(value = "生成活动码", notes = "生成活动码")
	@RequestMapping(value = "/creatrCode", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> creatrCode(Integer[] usersId, @RequestParam Integer activityId)
			throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		List<Total> list = new ArrayList<Total>(usersId.length);
		for (int i = 0; i < usersId.length; i++) {
			ActivitiRuleInstanceExample example = new ActivitiRuleInstanceExample();
			example.createCriteria().andUserIdEqualTo(usersId[i]).andActivityIdEqualTo(activityId);
			List<ActivitiRuleInstance> instance = activitiRuleInstanceMapper.selectByExample(example);
			if (instance.isEmpty()) {
				return builder.body(ResponseUtils.getResponseBody("此活动人不存在"));
			}
			ActivitiRuleInstance ruleInstance = instance.get(0);
			ruleInstance.setRuleInstanceValue(create());
			activitiRuleInstanceMapper.updateByPrimaryKey(ruleInstance);
		}
		return builder.body(ResponseUtils.getResponseBody(null));
	}

	public static String create() {
		String code = "0123456789qwertyuiopasdfghjklzxcvbnmQWERTYUIZXCVBNM";
		String str = "";
		for (int i = 1; i <= 4; i++) {
			String num = String.valueOf(code.charAt((int) Math.floor(Math.random() * code.length())));
			str += num;
			code = code.replaceAll(num, "");
		}
		return str;
	}

	@ApiOperation(value = "6 投票", notes = "用户投票")
	@RequestMapping(value = "/voteTicket", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> voteTicket(VoteTicketRequest request) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
//		ActivitiStrategy activitiStrategy = activitiStrategyMapper.selectByPrimaryKey(request.getRuleInstanceId());
		Activity activity = activityMapper.selectByPrimaryKey(request.getActivityId());
		if (activity.getIsTimingStart() == 0) {
			return builder.body(ResponseUtils.getResponseBody("活动未开始"));
		}
		ActivitiRuleInstanceExample ruleInstanceExample = new ActivitiRuleInstanceExample();
		ruleInstanceExample.createCriteria().andActivityIdEqualTo(request.getActivityId())
				.andUserIdEqualTo(request.getUserId());
		if (activitiRuleInstanceMapper.selectByExample(ruleInstanceExample).isEmpty()) {
			return builder.body(ResponseUtils.getResponseBody("此投票人不存在"));
		}
		Integer index = activitiRuleInstanceMapper.selectByExample(ruleInstanceExample).get(0).getUserTicketCount();
		if (index <= 0) {
			return builder.body(ResponseUtils.getResponseBody("没票了"));
		}
		ActivityVoteRecordsExample activityVoteRecordsExample = new ActivityVoteRecordsExample();
		activityVoteRecordsExample.createCriteria().andUserIdEqualTo(request.getUserId())
				.andElectedUserIdEqualTo(request.getElectedUserId());
		if (!activityVoteRecordsMapper.selectByExample(activityVoteRecordsExample).isEmpty()) {
			return builder.body(ResponseUtils.getResponseBody("不能重复投"));
		}
		activityVoteRecordsExample.clear();
		activityVoteRecordsExample.createCriteria().andUserIdEqualTo(request.getUserId())
				.andRemarksEqualTo(request.getRemark());
		if (!activityVoteRecordsMapper.selectByExample(activityVoteRecordsExample).isEmpty()) {
			return builder.body(ResponseUtils.getResponseBody("此分数已经使用"));
		}
		ActivitiRuleInstanceExample example = new ActivitiRuleInstanceExample();
		example.createCriteria().andUserIdEqualTo(request.getElectedUserId())
				.andActivityIdEqualTo(request.getActivityId());
		ActivitiRuleInstanceExample example2 = new ActivitiRuleInstanceExample();
		example2.createCriteria().andUserIdEqualTo(request.getUserId()).andActivityIdEqualTo(request.getActivityId());
		List<ActivitiRuleInstance> ruleValueDesc = activitiRuleInstanceMapper.selectByExample(example);
		List<ActivitiRuleInstance> ruleValueDesc2 = activitiRuleInstanceMapper.selectByExample(example2);
		if (ruleValueDesc.isEmpty()) {
			throw new Exception("此被投票人不存在");
		}
		if (ruleValueDesc2.isEmpty()) {
			throw new Exception("此投票人不存在");
		}
		ActivitiRuleInstance userElect = ruleValueDesc.get(0);
		ActivitiRuleInstance userVote = ruleValueDesc2.get(0);
		if (userVote.getUserTicketCount() <= 0) {
			return builder.body(ResponseUtils.getResponseBody("没票了"));
		}
		if (userElect.getUserTicketCount() == null) {
			addVoteRecords(request.getActivityId(), request.getUserId(), request.getElectedUserId(), index,
					request.getRemark());
			userElect.setRemarks(request.getRemark());
			userElect.setUserTicketCount(1);
			userElect.setUserScore(Integer.valueOf(request.getRemark()));
			activitiRuleInstanceMapper.updateByPrimaryKey(userElect);
			userVote.setUserTicketCount(userVote.getUserTicketCount() - 1);
			activitiRuleInstanceMapper.updateByPrimaryKey(userVote);
		} else {
			addVoteRecords(request.getActivityId(), request.getUserId(), request.getElectedUserId(), index,
					request.getRemark());
			userElect.setRemarks(request.getRemark());
			userElect.setUserTicketCount(userElect.getUserTicketCount() + 1);
			userElect.setUserScore(userElect.getUserScore() + Integer.valueOf(request.getRemark()));
			activitiRuleInstanceMapper.updateByPrimaryKey(userElect);
			userVote.setUserTicketCount(userVote.getUserTicketCount() - 1);
			activitiRuleInstanceMapper.updateByPrimaryKey(userVote);
		}
		return builder.body(ResponseUtils.getResponseBody(null));
	}

	@ApiOperation(value = "内部推荐", notes = "内部推荐")
	@RequestMapping(value = "/internalVote", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> creatrCode(VoteTicketRequest request) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		Activity activity = activityMapper.selectByPrimaryKey(request.getActivityId());
		if (activity.getIsTimingStart() == 0) {
			return builder.body(ResponseUtils.getResponseBody("活动未开始"));
		}
		ActivitiRuleInstanceExample ruleInstanceExample = new ActivitiRuleInstanceExample();
		ruleInstanceExample.createCriteria().andActivityIdEqualTo(request.getActivityId())
				.andUserIdEqualTo(request.getUserId());
		if (activitiRuleInstanceMapper.selectByExample(ruleInstanceExample).isEmpty()) {
			return builder.body(ResponseUtils.getResponseBody("此投票人不存在"));
		}
		ActivitiRuleInstanceExample example = new ActivitiRuleInstanceExample();
		example.createCriteria().andUserIdEqualTo(request.getElectedUserId())
				.andActivityIdEqualTo(request.getActivityId());
		ActivitiRuleInstanceExample example2 = new ActivitiRuleInstanceExample();
		example2.createCriteria().andUserIdEqualTo(request.getUserId()).andActivityIdEqualTo(request.getActivityId());
		List<ActivitiRuleInstance> ruleValueDesc = activitiRuleInstanceMapper.selectByExample(example);
		List<ActivitiRuleInstance> ruleValueDesc2 = activitiRuleInstanceMapper.selectByExample(example2);
		ActivitiRuleInstance userElect = ruleValueDesc.get(0);
		ActivitiRuleInstance userVote = ruleValueDesc2.get(0);
		addVoteRecords(request.getActivityId(), request.getUserId(), request.getElectedUserId(), -1, "-1");
		userElect.setIsDeleted((short) 1);
		userVote.setIsDeleted((short) 1);
		activitiRuleInstanceMapper.updateByPrimaryKey(userElect);
		activitiRuleInstanceMapper.updateByPrimaryKey(userVote);
		return builder.body(ResponseUtils.getResponseBody(null));
	}

//	@ApiOperation(value = "7 统计投票结果", notes = "统计投票结果")
//	@RequestMapping(value = "/statisticsTicket", method = RequestMethod.GET)
//	public ResponseEntity<JSONObject> statisticsTicket(@RequestParam Integer userId,
//			@RequestParam(name = "size", required = false) Integer size) throws JSONException {
//		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
//		Integer page = 1;
//		ActivityExample example = new ActivityExample();
//		example.createCriteria().andUserIdEqualTo(userId).andIsTimingStartEqualTo((short) 0);
//		List<Activity> activity = activityMapper.selectByExample(example);
//		if (activity.isEmpty()) {
//			return builder.body(ResponseUtils.getResponseBody("还未有活动结束"));
//		}
//		for (int i = 0; i < activity.size(); i++) {
//			ActivityStrategyInstanceExample activityStrategyInstanceExample = new ActivityStrategyInstanceExample();
//			activityStrategyInstanceExample.createCriteria().andActivityIdEqualTo(activity.get(i).getId())
//					.andRuleValueTypeEqualTo("ticket_count");
//			ActivitiRuleInstanceExample activitiRuleInstanceExample = new ActivitiRuleInstanceExample();
//			activitiRuleInstanceExample.createCriteria().andActivityIdEqualTo(activity.get(i).getId())
//					.andIsElectedEqualTo(false);
//			List<ActivitiRuleInstance> list = activitiRuleInstanceMapper.selectByExample(activitiRuleInstanceExample);
//			for (int j = 0; j < list.size(); j++) {
//				ActivityVoteRecordsExample activityVoteRecordsExample = new ActivityVoteRecordsExample();
//				activityVoteRecordsExample.createCriteria().andUserIdEqualTo(list.get(j).getUserId());
//				List<ActivityVoteRecords> activityVoteRecords = activityVoteRecordsMapper
//						.selectByExample(activityVoteRecordsExample);
//				if (!activityVoteRecords.isEmpty()) {
//					if (activityVoteRecords.size() < Integer.valueOf(activityStrategyInstanceMapper
//							.selectByExample(activityStrategyInstanceExample).get(0).getRuleValue())) {
//						for (int k = 0; k < activityVoteRecords.size(); k++) {
//							ActivitiRuleInstanceExample activitiRuleInstanceExample2 = new ActivitiRuleInstanceExample();
//							activitiRuleInstanceExample2.createCriteria().andActivityIdEqualTo(activity.get(i).getId())
//									.andUserIdEqualTo(activityVoteRecords.get(k).getElectedUserId());
//							List<ActivitiRuleInstance> activitiRuleInstance = activitiRuleInstanceMapper
//									.selectByExample(activitiRuleInstanceExample2);
//							ActivitiRuleInstance instance = activitiRuleInstance.get(0);
//							instance.setUserTicketCount(instance.getUserTicketCount() - 1);
//							instance.setUserScore(
//									instance.getUserScore() - Integer.valueOf(activityVoteRecords.get(k).getRemarks()));
//							activitiRuleInstanceMapper.updateByPrimaryKey(instance);
//						}
//						for (int k = 0; k < Integer.valueOf(activityStrategyInstanceMapper
//								.selectByExample(activityStrategyInstanceExample).get(0).getRuleValue())
//								- activityVoteRecords.size(); k++) {
//							ActivityVoteRecords records = new ActivityVoteRecords();
//							records.setUserId(activityVoteRecords.get(0).getUserId());
//							activityVoteRecordsMapper.insert(records);
//						}
//					}
//				}
//			}
//		}
//
//		List<ActivitiRuleInstance> list = null;
//		for (int i = 0; i < activity.size(); i++) {
//			List<Total> result = new ArrayList<Total>();
//			ActivitiRuleInstanceExample example2 = new ActivitiRuleInstanceExample();
//			example2.createCriteria().andActivityIdEqualTo(activity.get(i).getId()).andIsElectedEqualTo(true);
//			example2.setOrderByClause("user_ticket_count DESC,user_score DESC");
//			if (!StringUtils.isEmpty(size)) {
//				PageHelper.startPage(page, size);
//			}
//			list = activitiRuleInstanceMapper.selectByExample(example2);
//			if (!list.isEmpty()) {
//				Integer index = 1;
//				for (int j = 0; j < list.size(); j++) {
//					Total total = new Total();
//					if (list.get(j).getUserScore() == null) {
//						total.setSocre(0);
//					} else {
//						total.setSocre(list.get(j).getUserScore());
//					}
//					if (list.get(j).getUserTicketCount() == null) {
//						total.setVoteCount(0);
//					} else {
//						total.setVoteCount(list.get(j).getUserTicketCount());
//					}
//					total.setPosition(index);
//					total.setUserId(list.get(j).getUserId());
//					total.setActivityId(list.get(j).getActivityId());
//					result.add(total);
//					index++;
//				}
//				activity.get(i).setActivityResult(result.toString());
//				activityMapper.updateByPrimaryKey(activity.get(i));
//			}
//		}
////		if (result.isEmpty()) {
////			return builder.body(ResponseUtils.getResponseBody("还未有活动开始"));
////		}
////        TODO 统计投票 并将投票结果存入activity的activity_result 返回投票结果
//		return builder.body(ResponseUtils.getResponseBody(activity));
//	}

	@ApiOperation(value = "查看活动列表", notes = "查看活动列表")
	@RequestMapping(value = "/activities", method = RequestMethod.GET)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "userId", value = "用户id", required = true, type = "Integer") })
	public ResponseEntity<JSONObject> activities(@RequestParam Integer userId) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		String str = "";
		ActivityExample example = new ActivityExample();
		example.createCriteria().andUserIdEqualTo(userId);
		List<Activity> activity = activityMapper.selectByExample(example);
		if (!activity.isEmpty()) {
			for (int i = 0; i < activity.size(); i++) {
				str = str + activity.get(i).getActivityResult();
			}
			return builder.body(ResponseUtils.getResponseBody(str));
		}
		ActivitiRuleInstanceExample example2 = new ActivitiRuleInstanceExample();
		example2.createCriteria().andUserIdEqualTo(userId);
		List<ActivitiRuleInstance> activitiRuleInstance = activitiRuleInstanceMapper.selectByExample(example2);
		for (int i = 0; i < activitiRuleInstance.size(); i++) {
			Activity activity2 = activityMapper.selectByPrimaryKey(activitiRuleInstance.get(i).getActivityId());
			str = str + activity2.getActivityResult();
		}
//        TODO 活动发起者查看自己的活动, 或者查看自己参与过的活动列表
		if (str == "") {
			return builder.body(ResponseUtils.getResponseBody("未参加活动"));
		}
		return builder.body(ResponseUtils.getResponseBody(str));
	}

	@ApiOperation(value = "开启关闭活动按钮", notes = "开启关闭活动按钮")
	@RequestMapping(value = "/startActivity", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> startActivity(@RequestParam Integer activityId) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		Activity activity = activityMapper.selectByPrimaryKey(activityId);
		System.out.println(activity.getIsTimingStart());
		if (activity.getIsTimingStart() == (short) 0) {
			activity.setIsTimingStart((short) 1);
		} else {
			activity.setIsTimingStart((short) 0);
		}
		activityMapper.updateByPrimaryKey(activity);
		return builder.body(ResponseUtils.getResponseBody("修改成功"));
	}

	@ApiOperation(value = "查询某个活动的结果", notes = "查询某个活动的结果")
	@RequestMapping(value = "/findActivityResult", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> findActivityResult(@RequestParam Integer activityId) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		return builder.body(ResponseUtils.getResponseBody(activityMapper.selectByPrimaryKey(activityId)));
	}

	@ApiOperation(value = "根据活动查规则", notes = "查询某个活动的结果")
	@RequestMapping(value = "/findActivityRule", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> findActivityRule(@RequestParam Integer activityId) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		Activity activity = activityMapper.selectByPrimaryKey(activityId);
		StrategyRuleExample example = new StrategyRuleExample();
		example.createCriteria().andStrategyIdEqualTo(activity.getStrategyId());
		List<StrategyRule> list = strategyRuleMapper.selectByExample(example);
		return builder.body(ResponseUtils.getResponseBody(list));
	}

	@ApiOperation(value = "根据策略查规则", notes = "根据策略查规则")
	@RequestMapping(value = "/findStrategyRule", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> findStrategyRule(@RequestParam Integer strategyId) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		StrategyRuleExample example = new StrategyRuleExample();
		example.createCriteria().andStrategyIdEqualTo(strategyId);
		List<StrategyRule> list = strategyRuleMapper.selectByExample(example);
		return builder.body(ResponseUtils.getResponseBody(list));
	}

	@ApiOperation(value = "根据活动查策略", notes = "根据活动查策略")
	@RequestMapping(value = "/findActivityStrategy", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> findActivityStrategy(@RequestParam Integer activityId) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		Activity activity = activityMapper.selectByPrimaryKey(activityId);
		ActivitiStrategy activitiStrategy = activitiStrategyMapper.selectByPrimaryKey(activity.getStrategyId());
		return builder.body(ResponseUtils.getResponseBody(activitiStrategy));
	}

	@ApiOperation(value = "投票记录", notes = "投票记录")
	@RequestMapping(value = "/addVoteRecords", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> addVoteRecords(Integer activityId, Integer userId, Integer electedUserId,
			Integer voteTimes, String remarks) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		ActivityVoteRecords activityVoteRecords = new ActivityVoteRecords();
		activityVoteRecords.setActivityId(activityId);
		activityVoteRecords.setUserId(userId);
		activityVoteRecords.setElectedUserId(electedUserId);
		activityVoteRecords.setVoteTimes(voteTimes);
		activityVoteRecords.setRemarks(remarks);
		activityVoteRecords.setCreateTime(LocalDateTime.now());
		activityVoteRecords.setModifyTime(LocalDateTime.now());
		activityVoteRecords.setIsDeleted((short) 0);
		activityVoteRecordsMapper.insert(activityVoteRecords);
		return builder.body(ResponseUtils.getResponseBody(activityVoteRecords));
	}

	@ApiOperation(value = "查询参加该活动人员", notes = "查询参加该活动人员")
	@RequestMapping(value = "/listActivityUser", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> listActivityUser(@RequestParam Integer activityId) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		ActivitiRuleInstanceExample example = new ActivitiRuleInstanceExample();
		example.createCriteria().andActivityIdEqualTo(activityId);
		List<ActivitiRuleInstance> list = activitiRuleInstanceMapper.selectByExample(example);
		if (list.isEmpty()) {
			return builder.body(ResponseUtils.getResponseBody(null));
		}
		List<HfUser> users = new ArrayList<HfUser>(list.size());
		List<HfUser> users2 = hfUserMapper.findAllUser();
		for (int i = 0; i < users2.size(); i++) {
			for (int j = 0; j < list.size(); j++) {
				if (list.get(j).getUserId() == users2.get(i).getId()) {
					if (list.get(j).getRuleInstanceValue() != null) {
						users2.get(i).setCode(list.get(j).getRuleInstanceValue());
					}
					if (list.get(j).getUserTicketCount() != null) {
						users2.get(i).setCount((list.get(j).getUserTicketCount()));
					}
					if (list.get(j).getIsElected() != null) {
						users2.get(i).setIsElected(list.get(j).getIsElected());
					}
					users.add(users2.get(i));
				}
			}
		}
		return builder.body(ResponseUtils.getResponseBody(users));
	}

	@ApiOperation(value = "删除参选人", notes = "删除参选人")
	@RequestMapping(value = "/deleteActivityUser", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> deleteActivityUser(@RequestParam Integer activityId, Integer userId[])
			throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		for (int i = 0; i < userId.length; i++) {
			ActivitiRuleInstanceExample example = new ActivitiRuleInstanceExample();
			example.createCriteria().andActivityIdEqualTo(activityId).andUserIdEqualTo(userId[i]);
			List<ActivitiRuleInstance> activitiRuleInstance = activitiRuleInstanceMapper.selectByExample(example);
			ActivitiRuleInstance instance = activitiRuleInstance.get(0);
			ActivityStrategyInstance activityStrategyInstance = activityStrategyInstanceMapper
					.selectByPrimaryKey(instance.getRuleInstanceId());
			activityStrategyInstance
					.setRuleValue(String.valueOf(Integer.valueOf(activityStrategyInstance.getRuleValue()) + 1));
			activityStrategyInstanceMapper.updateByPrimaryKey(activityStrategyInstance);
			activitiRuleInstanceMapper.deleteByExample(example);
		}
		return builder.body(ResponseUtils.getResponseBody(null));
	}

	@ApiOperation(value = "添加奖品图片", notes = "添加奖品图片")
	@PostMapping(value = "/addPicture")
	public ResponseEntity<JSONObject> addAwardPicture(Pictures request) throws JSONException, IOException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		try {
			FileMangeService fileMangeService = new FileMangeService();
			String arr[];
			MultipartFile fileInfo = request.getFileInfo();
			arr = fileMangeService.uploadFile(fileInfo.getBytes(), String.valueOf(request.getUserId()));
			FileDesc fileDesc = new FileDesc();
			fileDesc.setFileName(fileInfo.getName());
			fileDesc.setGroupName(arr[0]);
			fileDesc.setRemoteFilename(arr[1]);
			fileDesc.setUserId(request.getUserId());
			fileDesc.setCreateTime(LocalDateTime.now());
			fileDesc.setModifyTime(LocalDateTime.now());
			fileDesc.setIsDeleted((short) 0);
			fileDescMapper.insert(fileDesc);
			ActivitiRuleInstance instance = new ActivitiRuleInstance();
			instance.setFileId(fileDesc.getId());
			activitiRuleInstanceMapper.insert(instance);
		} catch (IOException e) {
			logger.error("add picture failed", e);
		}
		return builder.body(ResponseUtils.getResponseBody(null));
	}

	@ApiOperation(value = "获取图片", notes = "获取图片")
	@RequestMapping(value = "/getFile", method = RequestMethod.GET)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "fileId", value = "文件id", required = true, type = "Integer") })
	public void getFile(@RequestParam(name = "fileId") Integer fileId, HttpServletResponse response) throws Exception {
		response.addHeader("Access-Control-Allow-Origin", "*");
		FileDesc fileDesc = fileDescMapper.selectByPrimaryKey(fileId);
		if (fileDesc == null) {
			throw new Exception("file not exists");
		}
		FileMangeService fileManageService = new FileMangeService();
		synchronized (LOCK) {
			byte[] file = fileManageService.downloadFile(fileDesc.getGroupName(), fileDesc.getRemoteFilename());
			ByteArrayInputStream stream = new ByteArrayInputStream(file);
			BufferedImage readImg = ImageIO.read(stream);
			stream.reset();
			OutputStream outputStream = response.getOutputStream();
			ImageIO.write(readImg, "png", outputStream);
			outputStream.close();
		}
	}

	@RequestMapping(path = "/uploadResume", method = RequestMethod.POST)
	@ApiOperation(value = "上传简历", notes = "上传简历")
	public ResponseEntity<JSONObject> uploadResume(MultipartFile file, @RequestParam Integer userId,
			@RequestParam String baseInfo) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		FileMangeService fileMangeService = new FileMangeService();
		String arr[];
		arr = fileMangeService.uploadFile(file.getBytes(), String.valueOf(userId));
		FileDesc fileDesc = new FileDesc();
		fileDesc.setFileName(file.getName());
		fileDesc.setGroupName(arr[0]);
		fileDesc.setRemoteFilename(arr[1]);
		fileDesc.setUserId(userId);
		fileDesc.setCreateTime(LocalDateTime.now());
		fileDesc.setModifyTime(LocalDateTime.now());
		fileDesc.setIsDeleted((short) 0);
		fileDescMapper.insert(fileDesc);
		UserInfo info = new UserInfo();
		info.setBaseInfo(baseInfo);
		info.setCreateTime(LocalDateTime.now());
		info.setFileId(fileDesc.getId());
		info.setIsDeleted((short) 0);
		info.setModifyTime(LocalDateTime.now());
		info.setUserId(userId);
		return builder.body(ResponseUtils.getResponseBody(userInfoMapper.insert(info)));
	}

	@RequestMapping(path = "/downloadResume", method = RequestMethod.GET)
	@ApiOperation(value = "下载简历", notes = "下载简历")
	public ResponseEntity<JSONObject> downloadResume(@RequestParam(name = "fileId") Integer fileId,
			HttpServletResponse response, Boolean isOnLine) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		FileDesc fileDesc = fileDescMapper.selectByPrimaryKey(fileId);
		FileMangeService fileManageService = new FileMangeService();
		byte[] file_buff = fileManageService.downloadFile(fileDesc.getGroupName(), fileDesc.getRemoteFilename());
		File file = new File("src/main/resources/" + fileId + ".doc");
		FileOutputStream outStream = new FileOutputStream(file);
		outStream.write(file_buff);
		outStream.flush();
		outStream.close();
		File file1 = new File("src/main/resources/" + fileId + ".doc");
		if (!file1.exists()) {
			response.sendError(404, "File not found!");
			return builder.body(ResponseUtils.getResponseBody(0));
		}
		BufferedInputStream br = new BufferedInputStream(new FileInputStream(file1));
		byte[] buf = new byte[1024];
		int len = 0;

		response.reset(); // 非常重要
		if (isOnLine) { // 在线打开方式
			URL u = new URL("file:///" + "src/main/resources/" + fileId + ".doc");
			response.setContentType(u.openConnection().getContentType());
			response.setHeader("Content-Disposition", "inline; filename=" + file1.getName());
			// 文件名应该编码成UTF-8
		} else { // 纯下载方式
			response.setContentType("application/x-msdownload");
			response.setHeader("Content-Disposition", "attachment; filename=" + file1.getName());
		}
		OutputStream out = response.getOutputStream();
		while ((len = br.read(buf)) > 0)
			out.write(buf, 0, len);
		br.close();
		out.close();
		return builder.body(ResponseUtils.getResponseBody(file));
	}

}
