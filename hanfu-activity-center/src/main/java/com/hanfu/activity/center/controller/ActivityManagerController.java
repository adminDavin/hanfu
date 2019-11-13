package com.hanfu.activity.center.controller;

import java.time.LocalDateTime;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.hanfu.activity.center.dao.ActivitiRuleInstanceMapper;
import com.hanfu.activity.center.dao.ActivitiStrategyMapper;
import com.hanfu.activity.center.dao.ActivityMapper;
import com.hanfu.activity.center.dao.ActivityStrategyInstanceMapper;
import com.hanfu.activity.center.dao.StrategyRuleMapper;
import com.hanfu.activity.center.model.ActivitiRuleInstance;
import com.hanfu.activity.center.model.ActivitiRuleInstanceExample;
import com.hanfu.activity.center.model.ActivitiStrategy;
import com.hanfu.activity.center.model.Activity;
import com.hanfu.activity.center.model.ActivityExample;
import com.hanfu.activity.center.model.ActivityStrategyInstance;
import com.hanfu.activity.center.model.ActivityStrategyInstanceExample;
import com.hanfu.activity.center.model.StrategyRule;
import com.hanfu.activity.center.model.Total;
import com.hanfu.activity.center.request.ActivityRequest;
import com.hanfu.activity.center.request.ActivityStrategyRequest;
import com.hanfu.activity.center.request.AddActivityUserRequest;
import com.hanfu.activity.center.request.StrategyRuleRequest;
import com.hanfu.activity.center.request.UpdateActivityRuleRequest;
import com.hanfu.activity.center.request.VoteTicketRequest;
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
//        strategyRule.setStrategyId(request.getStrategyId());
        strategyRule.setRuleStatus("生效中");
        strategyRule.setRuleType(request.getRuleType());
//        strategyRule.setRuelValueType(request.getRuelValueType());
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
        return builder.body(ResponseUtils.getResponseBody(activityMapper.insert(activity)));
    }

    @ApiOperation(value = "4 设置活动规则", notes = "设置活动规则")
    @RequestMapping(value = "/setActivityRules", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> setActivitiRules(UpdateActivityRuleRequest request) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        ActivityStrategyInstanceExample example = new ActivityStrategyInstanceExample();
        example.createCriteria().andActivityIdEqualTo(request.getActivityId()).andRuleIdEqualTo(Integer.valueOf(request.getRuleId()));
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
            activityStrategyInstanceMapper.insert(record);
        }
        return builder.body(ResponseUtils.getResponseBody(record));
    }
    
    @ApiOperation(value = "5 添加活动参与者", notes = "添加活动参与者")
    @RequestMapping(value = "/addActivityUser", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> addActivityUser(AddActivityUserRequest request,@RequestParam Boolean flag) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        String arr = "";
		String str = "";
		String id = ":";
		for(int i=0;i<request.getUserIds().length;i++) {
			arr = create();
			ActivitiRuleInstanceExample example3 = new ActivitiRuleInstanceExample();
			example3.createCriteria().andActivityIdEqualTo(request.getActivityId()).andUserIdEqualTo(request.getUserIds()[i]);
			List<ActivitiRuleInstance> list3 = activitiRuleInstanceMapper.selectByExample(example3);
//			ActivitiRuleInstance recod;
			if(list3.isEmpty()) {
				ActivitiRuleInstance ruleValueDesc = new ActivitiRuleInstance();
				ruleValueDesc.setActivityId(request.getActivityId());
				ruleValueDesc.setRuleId(request.getRuleId());
				ruleValueDesc.setRuleInstanceId(request.getRuleInstanceId());
				ruleValueDesc.setIsElected(flag);
				ruleValueDesc.setUserId(request.getUserIds()[i]);
				ruleValueDesc.setRuleInstanceValue(arr);
				ruleValueDesc.setCreateTime(LocalDateTime.now());
				ruleValueDesc.setModifyTime(LocalDateTime.now());
				ruleValueDesc.setIsDeleted((short) 0);
				activitiRuleInstanceMapper.insert(ruleValueDesc);
			}else {
				id = id + list3.get(0).getUserId() + ",";
			}
			arr = arr+",";
			str = arr + str;
		}
		str = str + id;
		return builder.body(ResponseUtils.getResponseBody(str));
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
//        ActivityStrategyInstanceExample example = new ActivityStrategyInstanceExample();
//        example.createCriteria().andActivityIdEqualTo(request.getActivityId());
//        List<ActivityStrategyInstance> records = activityStrategyInstanceMapper.selectByExample(example);
//        ActivityStrategyInstance record = null;
////        TODO 投票 并将投票结果存入  activiti_rule_instance的user_score
//        return builder.body(ResponseUtils.getResponseBody(record));
        Activity activity = activityMapper.selectByPrimaryKey(request.getActivityId());
        if(activity.getIsTimingStart() == 0) {
        	return builder.body(ResponseUtils.getResponseBody("活动未开始")); 
        }
        ActivitiRuleInstanceExample example = new ActivitiRuleInstanceExample();
        example.createCriteria().andUserIdEqualTo(request.getElectedUserId()).andActivityIdEqualTo(request.getActivityId()).andRuleInstanceIdEqualTo(request.getRuleInstanceId());
        ActivitiRuleInstanceExample example2 = new ActivitiRuleInstanceExample();
        example2.createCriteria().andUserIdEqualTo(request.getUserId()).andActivityIdEqualTo(request.getActivityId()).andRuleInstanceIdEqualTo(request.getRuleInstanceId());
        List<ActivitiRuleInstance> ruleValueDesc = activitiRuleInstanceMapper.selectByExample(example);
        List<ActivitiRuleInstance> ruleValueDesc2 = activitiRuleInstanceMapper.selectByExample(example2);
        Integer index = 1;
		if(ruleValueDesc.isEmpty()) {
			throw new Exception("此被投票人不存在");
		}
		if(ruleValueDesc2.isEmpty()) {
			throw new Exception("此投票人不存在");
		}
		ActivitiRuleInstance userElect = ruleValueDesc.get(0);
		ActivitiRuleInstance userVote = ruleValueDesc2.get(0);
		if(userVote.getIsElected() == true) {
			return builder.body(ResponseUtils.getResponseBody("无资格"));
		}
			
		if(userVote.getIsDeleted() == 0) {
			if(userElect.getUserTicketCount() == null) {
				userElect.setRemarks(request.getRemark());
				userElect.setUserTicketCount(index);
				userElect.setUserScore(index);
				userVote.setIsDeleted((short) 1);
				activitiRuleInstanceMapper.updateByPrimaryKey(userElect);
				activitiRuleInstanceMapper.updateByPrimaryKey(userVote);
				return builder.body(ResponseUtils.getResponseBody(1));
			}else {
				index = userElect.getUserScore();
				userElect.setRemarks(request.getRemark());
				userElect.setUserTicketCount(index+1);
				userElect.setUserScore(index+1);
				userVote.setIsDeleted((short) 1);
				activitiRuleInstanceMapper.updateByPrimaryKey(userElect);
				activitiRuleInstanceMapper.updateByPrimaryKey(userVote);
				return builder.body(ResponseUtils.getResponseBody(1));
			}
		}else {
			return builder.body(ResponseUtils.getResponseBody(0));
		}
    }
    
    @ApiOperation(value = "7 统计投票结果", notes = "统计投票结果")
    @RequestMapping(value = "/statisticsTicket", method = RequestMethod.GET)
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "query", name = "userId活动发起者", value = "用户id", required = true, type = "Integer") })
    public ResponseEntity<JSONObject> statisticsTicket(@RequestParam Integer userId,@RequestParam(name = "size", required = false)Integer size) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        Integer page = 1;
        List<Total> result = new ArrayList<Total>();
        ActivityExample example = new ActivityExample();
        example.createCriteria().andUserIdEqualTo(userId).andIsTimingStartEqualTo((short) 0);
        List<Activity> activity = activityMapper.selectByExample(example);
        if(activity.isEmpty()) {
        	return builder.body(ResponseUtils.getResponseBody("还未有活动结束"));
        }
        List<ActivitiRuleInstance> list = null;
        for (int i = 0; i < activity.size(); i++) {
        	ActivitiRuleInstanceExample example2 = new ActivitiRuleInstanceExample();
            example2.createCriteria().andActivityIdEqualTo(activity.get(i).getId());
            example2.setOrderByClause("user_ticket_count DESC,user_score DESC");
    			if(!StringUtils.isEmpty(size)) {
    				PageHelper.startPage(page,size);
    			}
            list = activitiRuleInstanceMapper.selectByExample(example2);
            for (int j = 0; j < list.size(); j++) {
				Total total = new Total();
				if(list.get(j).getUserScore() == null) {
					total.setSocre(0);
				}else {
					total.setSocre(list.get(j).getUserScore());
				}
				if(list.get(j).getUserTicketCount() == null) {
					total.setVoteCount(0);
				}else {
					total.setVoteCount(list.get(j).getUserTicketCount());
				}
				
				total.setUserId(list.get(j).getUserId());
				total.setActivityId(list.get(j).getActivityId());
				result.add(total);
			}
        	activity.get(i).setActivityResult(result.toString());
        	activityMapper.updateByPrimaryKey(activity.get(i));
		}
        if(result.isEmpty()) {
        	return builder.body(ResponseUtils.getResponseBody("还未有活动开始"));
        }
//        TODO 统计投票 并将投票结果存入activity的activity_result 返回投票结果
        return builder.body(ResponseUtils.getResponseBody(result.toString()));
    }
    
    
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
        if(!activity.isEmpty()) {
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
        if(str=="") {
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
        if(activity.getIsTimingStart() == (short) 0) {
        	activity.setIsTimingStart((short) 1);
        }else {
        	activity.setIsTimingStart((short) 0);
        }
        activityMapper.updateByPrimaryKey(activity);
        return builder.body(ResponseUtils.getResponseBody("修改成功"));
    }
    
}
