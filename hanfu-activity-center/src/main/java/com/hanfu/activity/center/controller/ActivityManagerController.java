package com.hanfu.activity.center.controller;

import java.time.LocalDateTime;
import java.util.List;

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
import com.hanfu.activity.center.dao.ActivitiRuleInstanceMapper;
import com.hanfu.activity.center.dao.ActivitiStrategyMapper;
import com.hanfu.activity.center.dao.ActivityMapper;
import com.hanfu.activity.center.dao.ActivityStrategyInstanceMapper;
import com.hanfu.activity.center.dao.StrategyRuleMapper;
import com.hanfu.activity.center.model.ActivitiStrategy;
import com.hanfu.activity.center.model.Activity;
import com.hanfu.activity.center.model.ActivityStrategyInstance;
import com.hanfu.activity.center.model.ActivityStrategyInstanceExample;
import com.hanfu.activity.center.model.StrategyRule;
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
    
    @ApiOperation(value = "6 添加活动参与者", notes = "添加活动参与者")
    @RequestMapping(value = "/addActivityUser", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> addActivityUser(AddActivityUserRequest request) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        ActivityStrategyInstanceExample example = new ActivityStrategyInstanceExample();
        example.createCriteria().andActivityIdEqualTo(request.getActivityId()).andRuleIdEqualTo(Integer.valueOf(request.getRuleId()));
        List<ActivityStrategyInstance> records = activityStrategyInstanceMapper.selectByExample(example);
        ActivityStrategyInstance record = null;
        // TODO 
//        if (records != null && !records.isEmpty()) {
//            record = records.get(0);
//            record.setModifyTime(LocalDateTime.now());
//            activityStrategyInstanceMapper.updateByPrimaryKey(record);
//        } else {
//            record = new ActivityStrategyInstance();
//            Activity activity = activityMapper.selectByPrimaryKey(request.getActivityId());
//            StrategyRule strategyRule = strategyRuleMapper.selectByPrimaryKey(Integer.valueOf(request.getRuleId()));
//            record.setActivityId(activity.getId());
//            record.setRuleId(strategyRule.getId());
//            record.setRuleValueType(strategyRule.getRuelValueType());
//            activityStrategyInstanceMapper.insert(record);
//            
//        }
        return builder.body(ResponseUtils.getResponseBody(record));
    }
    
    @ApiOperation(value = "5 设置活动规则参数", notes = "设置活动规则")
    @RequestMapping(value = "/setActivityRuleInstance", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> setActivityRuleInstance(UpdateActivityRuleRequest request) throws JSONException {
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
            record.setRuleDesc(request.getRuleSDesc());
            record.setRuleValue(request.getRuleValue());
            record.setRuleValueType(strategyRule.getRuelValueType());
            activityStrategyInstanceMapper.insert(record);
            
        }
        return builder.body(ResponseUtils.getResponseBody(record));
    }
    
    @ApiOperation(value = "7 投票", notes = "用户投票")
    @RequestMapping(value = "/voteTicket", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> voteTicket(VoteTicketRequest request) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        ActivityStrategyInstanceExample example = new ActivityStrategyInstanceExample();
        example.createCriteria().andActivityIdEqualTo(request.getActivityId());
        List<ActivityStrategyInstance> records = activityStrategyInstanceMapper.selectByExample(example);
        ActivityStrategyInstance record = null;
//        TODO 投票 并将投票结果存入  activiti_rule_instance的user_score
        return builder.body(ResponseUtils.getResponseBody(record));
    }
    
    @ApiOperation(value = "8 统计投票结果", notes = "统计投票结果")
    @RequestMapping(value = "/statisticsTicket", method = RequestMethod.GET)
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "query", name = "userId活动发起者", value = "用户id", required = true, type = "Integer") })
    public ResponseEntity<JSONObject> statisticsTicket(@RequestParam Integer userId) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        ActivityStrategyInstanceExample example = new ActivityStrategyInstanceExample();
//        example.createCriteria().andActivityIdEqualTo(request.getActivityId()).andRuleIdEqualTo(Integer.valueOf(request.getRuleId()));
//        List<ActivityStrategyInstance> records = activityStrategyInstanceMapper.selectByExample(example);
        ActivityStrategyInstance record = null;
//        TODO 统计投票 并将投票结果存入activity的activity_result 返回投票结果
        return builder.body(ResponseUtils.getResponseBody(record));
    }
    
    
    @ApiOperation(value = "查看活动列表", notes = "查看活动列表")
    @RequestMapping(value = "/activities", method = RequestMethod.GET)
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "query", name = "userId", value = "用户id", required = true, type = "Integer") })
    public ResponseEntity<JSONObject> activities(@RequestParam Integer userId) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        ActivityStrategyInstanceExample example = new ActivityStrategyInstanceExample();
//        example.createCriteria().andActivityIdEqualTo(request.getActivityId()).andRuleIdEqualTo(Integer.valueOf(request.getRuleId()));
//        List<ActivityStrategyInstance> records = activityStrategyInstanceMapper.selectByExample(example);
        ActivityStrategyInstance record = null;
//        TODO 活动发起者查看自己的活动, 或者查看自己参与过的活动列表
        return builder.body(ResponseUtils.getResponseBody(record));
    }
    
}
