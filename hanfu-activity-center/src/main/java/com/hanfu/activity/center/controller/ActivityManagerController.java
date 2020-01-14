
package com.hanfu.activity.center.controller;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.alibaba.fastjson.JSONObject;
import com.cedarsoftware.util.StringUtilities;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.hanfu.activity.center.dao.ActivitiRuleInstanceMapper;
import com.hanfu.activity.center.dao.ActivitiStrategyMapper;
import com.hanfu.activity.center.dao.ActivityDepartmentMapper;
import com.hanfu.activity.center.dao.ActivityEvaluateTemplateMapper;
import com.hanfu.activity.center.dao.ActivityMapper;
import com.hanfu.activity.center.dao.ActivityStrategyInstanceMapper;
import com.hanfu.activity.center.dao.ActivityUserEvaluateMapper;
import com.hanfu.activity.center.dao.ActivityUserExperienceMapper;
import com.hanfu.activity.center.dao.ActivityUserInfoMapper;
import com.hanfu.activity.center.dao.ActivityVoteRecordsMapper;
import com.hanfu.activity.center.dao.FileDescMapper;
import com.hanfu.activity.center.dao.HfUserMapper;
import com.hanfu.activity.center.dao.StrategyRuleMapper;
import com.hanfu.activity.center.dao.UserInfoMapper;
import com.hanfu.activity.center.manual.dao.HfUserDao;
import com.hanfu.activity.center.manual.dao.VotePictureDao;
import com.hanfu.activity.center.manual.model.HfUser;
import com.hanfu.activity.center.manual.model.Pictures;
import com.hanfu.activity.center.manual.model.TimeTime;
import com.hanfu.activity.center.manual.model.UserFormInfo;
import com.hanfu.activity.center.model.ActivitiRuleInstance;
import com.hanfu.activity.center.model.ActivitiRuleInstanceExample;
import com.hanfu.activity.center.model.ActivitiStrategy;
import com.hanfu.activity.center.model.Activity;
import com.hanfu.activity.center.model.ActivityDepartment;
import com.hanfu.activity.center.model.ActivityDepartmentExample;
import com.hanfu.activity.center.model.ActivityEvaluateTemplate;
import com.hanfu.activity.center.model.ActivityEvaluateTemplateExample;
import com.hanfu.activity.center.model.ActivityExample;
import com.hanfu.activity.center.model.ActivityStrategyInstance;
import com.hanfu.activity.center.model.ActivityStrategyInstanceExample;
import com.hanfu.activity.center.model.ActivityUserEvaluate;
import com.hanfu.activity.center.model.ActivityUserEvaluateExample;
import com.hanfu.activity.center.model.ActivityUserExperience;
import com.hanfu.activity.center.model.ActivityUserExperienceExample;
import com.hanfu.activity.center.model.ActivityUserInfo;
import com.hanfu.activity.center.model.ActivityUserInfoExample;
import com.hanfu.activity.center.model.ActivityVoteRecords;
import com.hanfu.activity.center.model.ActivityVoteRecordsExample;
import com.hanfu.activity.center.model.FileDesc;
import com.hanfu.activity.center.model.FileDescExample;
import com.hanfu.activity.center.model.StrategyRule;
import com.hanfu.activity.center.model.StrategyRuleExample;
import com.hanfu.activity.center.model.Total;
import com.hanfu.activity.center.model.UserInfo;
import com.hanfu.activity.center.request.ActivityDepartmentRequest;
import com.hanfu.activity.center.request.ActivityRequest;
import com.hanfu.activity.center.request.ActivityStrategyRequest;
import com.hanfu.activity.center.request.ActivityUserBaseInfoRequest;
import com.hanfu.activity.center.request.ActivityVoteRecordRequest;
import com.hanfu.activity.center.request.AddActivityUserRequest;
import com.hanfu.activity.center.request.RecordScoreRequest;
import com.hanfu.activity.center.request.StrategyRuleRequest;
import com.hanfu.activity.center.request.UpdateActivityRuleRequest;
import com.hanfu.activity.center.request.VoteTicketRequest;
import com.hanfu.common.service.FileMangeService;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseEntity.BodyBuilder;
import com.hanfu.utils.response.handler.ResponseUtils;
import com.mysql.cj.result.Field;

import io.netty.handler.codec.http.HttpRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
@RequestMapping("/wareHouse")
@Api
public class ActivityManagerController {
    private static final String LOCK = "lock";

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String LOCKLOCK = "LOCKLOCK";
    private static final String LOCKLOCK2 = "LOCKLOCK2";
    private static final String LOCKLOCK3 = "LOCKLOCK3";
    private static final String LOCKLOCK4 = "LOCKLOCK4";

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
    private HfUserDao hfUserDao;

    @Autowired
    private VotePictureDao votePictureDao;

    @Autowired
    private FileDescMapper fileDescMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private ActivityUserInfoMapper activityUserInfoMapper;

    @Autowired
    private ActivityDepartmentMapper activityDepartmentMapper;

    @Autowired
    private ActivityUserExperienceMapper activityUserExperienceMapper;

    @Autowired
    private HfUserMapper hfUserMapper;

    @Autowired
    private ActivityUserEvaluateMapper activityUserEvaluateMapper;

    @Autowired
    private ActivityEvaluateTemplateMapper activityEvaluateTemplateMapper;

    @ApiOperation(value = "1、制定活动策略", notes = "制定活动策略")
    @RequestMapping(value = "/addActivityStrategy", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> addActivityStrategy(ActivityStrategyRequest request) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        ActivitiStrategy activitiStrategy = new ActivitiStrategy();
        activitiStrategy.setStrategyName(request.getStrategyName());
        activitiStrategy.setStrategyDesc(request.getStrategyDesc());
//		activitiStrategy.setStrategyType(request.getStrategyType());
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
        StrategyRuleExample example = new StrategyRuleExample();
        example.createCriteria().andRuleTypeEqualTo(request.getRuleType())
                .andStrategyIdEqualTo(request.getStrategyId());
        List<StrategyRule> list = strategyRuleMapper.selectByExample(example);
        if (list.isEmpty()) {
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
            if ("record_score".equals(ruleType)) {
                strategyRule.setRuelValueType("score");
            }
            if ("internal_election".equals(ruleType)) {
                strategyRule.setRuelValueType("election");
            }
            if ("public_praise".equals(ruleType)) {
                strategyRule.setRuelValueType("praise");
            }
            strategyRule.setCreateTime(LocalDateTime.now());
            strategyRule.setModifyTime(LocalDateTime.now());
            strategyRule.setIsDeleted((short) 0);
            return builder.body(ResponseUtils.getResponseBody(strategyRuleMapper.insert(strategyRule)));
        } else {
            return builder.body(ResponseUtils.getResponseBody("已经存在"));
        }

    }

    @ApiOperation(value = "3 发起活动", notes = "公司每次举行活动的添加")
    @RequestMapping(value = "/addActivity", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> addActivity(ActivityRequest request) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        Activity activity = new Activity();
        activity.setActivityName(request.getActivityName());
        activity.setActivityDesc(request.getActivityDesc());
//		activity.setActivityStatus(request.getActivityStatus());
//		activity.setActiviyType(request.getActiviyType());
        activity.setStrategyId(request.getStrategyId());
        activity.setUserId(request.getUserId());
        activity.setIsTimingStart((short) 0);
        activity.setCreateTime(LocalDateTime.now());
        activity.setModifyTime(LocalDateTime.now());
        activity.setIsDeleted((short) 0);
        activityMapper.insert(activity);
        StrategyRuleExample example = new StrategyRuleExample();
        example.createCriteria().andStrategyIdEqualTo(request.getStrategyId());
        List<StrategyRule> list = strategyRuleMapper.selectByExample(example);
        for (int i = 0; i < list.size(); i++) {
            UpdateActivityRuleRequest request2 = new UpdateActivityRuleRequest();
            request2.setActivityId(activity.getId());
            request2.setRuleName(list.get(i).getRuleName());
            request2.setRuleSDesc(list.get(i).getRuleDesc());
            request2.setRuleValue(list.get(i).getRuleType());
            request2.setRuleId(list.get(i).getId());
            setActivitiRules(request2);
        }
        return builder.body(ResponseUtils.getResponseBody(null));
    }

    @ApiOperation(value = "4 设置活动规则", notes = "设置活动规则")
    @RequestMapping(value = "/setActivityRules", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> setActivitiRules(UpdateActivityRuleRequest request) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        ActivityStrategyInstance records = activityStrategyInstanceMapper
                .selectByPrimaryKey(request.getRuleInstanceId());
        if (records != null) {
            if (!StringUtils.isEmpty(request.getRuleSDesc())) {
                records.setRuleDesc(request.getRuleSDesc());
            }
            if (!StringUtils.isEmpty(request.getRuleValue())) {
                records.setRuleValue(request.getRuleValue());
            }
            records.setModifyTime(LocalDateTime.now());
            activityStrategyInstanceMapper.updateByPrimaryKey(records);
        } else {
            records = new ActivityStrategyInstance();
            Activity activity = activityMapper.selectByPrimaryKey(request.getActivityId());
            StrategyRule strategyRule = strategyRuleMapper.selectByPrimaryKey(Integer.valueOf(request.getRuleId()));
            records.setActivityId(activity.getId());
            records.setRuleId(strategyRule.getId());
            records.setRuleName(request.getRuleName());
            records.setRuleDesc(request.getRuleSDesc());
            records.setRuleValue(request.getRuleValue());
            records.setRuleValueType(strategyRule.getRuelValueType());
            if ("ticket_count".equals(strategyRule.getRuelValueType())) {
                activity.setActiviyType(strategyRule.getRuelValueType());
                activityMapper.updateByPrimaryKey(activity);
            }
            if ("score".equals(strategyRule.getRuelValueType())) {
                activity.setActiviyType(strategyRule.getRuelValueType());
                activityMapper.updateByPrimaryKey(activity);
            }
            if ("election".equals(strategyRule.getRuelValueType())) {
                activity.setActiviyType(strategyRule.getRuelValueType());
                activityMapper.updateByPrimaryKey(activity);
            }
            if ("praise".equals(strategyRule.getRuelValueType())) {
                activity.setActiviyType(strategyRule.getRuelValueType());
                activityMapper.updateByPrimaryKey(activity);
            }
            records.setRuleStatus("0");
            records.setCreateTime(LocalDateTime.now());
            records.setModifyTime(LocalDateTime.now());
            records.setIsDeleted((short) 0);
            activityStrategyInstanceMapper.insert(records);
        }
        return builder.body(ResponseUtils.getResponseBody(records));
    }

    @ApiOperation(value = "5 添加活动参与者", notes = "添加活动参与者")
    @RequestMapping(value = "/addActivityUser", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> addActivityUser(AddActivityUserRequest request) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        String arr = "";
        String str = "";
        String id = ":";
        ActivityStrategyInstance instance = activityStrategyInstanceMapper
                .selectByPrimaryKey(request.getRuleInstanceId());
        if ("ticket_count".equals(instance.getRuleValueType()) || "score".equals(instance.getRuleValueType())) {
            return builder.body(ResponseUtils.getResponseBody("违反规则"));
        }
        if ("score".equals(instance.getRuleValueType()) || "score".equals(instance.getRuleValueType())) {
            return builder.body(ResponseUtils.getResponseBody("违反规则"));
        }
        if ("election".equals(instance.getRuleValueType()) || "score".equals(instance.getRuleValueType())) {
            return builder.body(ResponseUtils.getResponseBody("违反规则"));
        }
        if ("praise".equals(instance.getRuleValueType()) || "score".equals(instance.getRuleValueType())) {
            return builder.body(ResponseUtils.getResponseBody("违反规则"));
        }
        if (request.getUserIds().length > Integer.valueOf(instance.getRuleValue())) {
            return builder.body(ResponseUtils.getResponseBody("超过限定数量"));
        }
        for (int i = 0; i < request.getUserIds().length; i++) {
            ActivitiRuleInstanceExample example3 = new ActivitiRuleInstanceExample();
            example3.createCriteria().andActivityIdEqualTo(request.getActivityId())
                    .andUserIdEqualTo(request.getUserIds()[i]);
            List<ActivitiRuleInstance> list3 = activitiRuleInstanceMapper.selectByExample(example3);
            if (list3.isEmpty()) {
                ActivitiRuleInstance ruleValueDesc = new ActivitiRuleInstance();
                ruleValueDesc.setActivityId(request.getActivityId());

                String type = "";
                ActivityStrategyInstanceExample example = new ActivityStrategyInstanceExample();
                example.createCriteria().andActivityIdEqualTo(request.getActivityId());
                List<ActivityStrategyInstance> instance1 = activityStrategyInstanceMapper.selectByExample(example);
                for (int j = 0; j < instance1.size(); j++) {
                    if ("ticket_count".equals(instance1.get(j).getRuleValueType())
                            && instance1.get(j).getRuleValueType() != null) {
                        type = instance1.get(j).getRuleValueType();
                    }
                }
                if ("elector".equals(strategyRuleMapper.selectByPrimaryKey(instance.getRuleId()).getRuleType())
                        && "ticket_count".equals(type)) {
//					ActivityStrategyInstanceExample example2 = new ActivityStrategyInstanceExample();
//					example2.createCriteria().andRuleValueTypeEqualTo("ticket_count");
//					List<ActivityStrategyInstance> activityStrategyInstance = activityStrategyInstanceMapper
//							.selectByExample(example2);
//					Integer.valueOf(activityStrategyInstance.get(0).getRuleValue())
                    ruleValueDesc.setUserTicketCount(3);
                }
//				ActivityStrategyInstanceExample strategyInstanceExample = new ActivityStrategyInstanceExample();
//				strategyInstanceExample.createCriteria().andRuleIdEqualTo(instance.getRuleId());
//				activityStrategyInstanceMapper.selectByExample(strategyInstanceExample).get(0).getId()
                ruleValueDesc.setRuleInstanceId(request.getRuleInstanceId());
                ruleValueDesc.setRuleId(instance.getRuleId());
                StrategyRule rule = strategyRuleMapper.selectByPrimaryKey(instance.getRuleId());
                if ("elector".equals(rule.getRuleType())) {
                    ruleValueDesc.setIsElected(false);
                } else if ("elected".equals(rule.getRuleType())) {
                    ruleValueDesc.setIsElected(true);
                }
                ruleValueDesc.setUserId(request.getUserIds()[i]);
                ruleValueDesc.setCreateTime(LocalDateTime.now());
                ruleValueDesc.setModifyTime(LocalDateTime.now());
                ruleValueDesc.setIsDeleted((short) 0);
                if ("user_list".equals(activityStrategyInstanceMapper.selectByPrimaryKey(request.getRuleInstanceId())
                        .getRuleValueType())) {
                    ActivityStrategyInstance activityStrategyInstance1 = activityStrategyInstanceMapper
                            .selectByPrimaryKey(request.getRuleInstanceId());
                    if (Integer.valueOf(activityStrategyInstance1.getRuleValue()) <= 0) {
                        return builder.body(ResponseUtils.getResponseBody("超过限定人数"));
                    }
                    activityStrategyInstance1.setRuleValue(
                            String.valueOf(Integer.valueOf(activityStrategyInstance1.getRuleValue()) - 1));
                    activityStrategyInstanceMapper.updateByPrimaryKey(activityStrategyInstance1);
                }
                synchronized (LOCKLOCK3) {
                    if (activitiRuleInstanceMapper.selectByExample(example3).isEmpty()) {
                        activitiRuleInstanceMapper.insert(ruleValueDesc);
                    }
                }
            } else {
                id = id + list3.get(0).getUserId() + ",";
            }
        }
        return builder.body(ResponseUtils.getResponseBody(id));
    }

    @ApiOperation(value = "生成活动码", notes = "生成活动码")
    @RequestMapping(value = "/creatrCode", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> creatrCode(Integer[] usersId, @RequestParam Integer activityId)
            throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        List<Total> list = new ArrayList<Total>(usersId.length);
        String code = "";
        for (int i = 0; i < usersId.length; i++) {
            ActivitiRuleInstanceExample example = new ActivitiRuleInstanceExample();
            example.createCriteria().andUserIdEqualTo(usersId[i]).andActivityIdEqualTo(activityId);
            List<ActivitiRuleInstance> instance = activitiRuleInstanceMapper.selectByExample(example);
            if (instance.isEmpty()) {
                return builder.body(ResponseUtils.getResponseBody("此活动人不存在"));
            }
            example.clear();
            example.createCriteria().andUserIdEqualTo(usersId[i]);
            List<ActivitiRuleInstance> activitiRuleInstances = activitiRuleInstanceMapper.selectByExample(example);
            ActivitiRuleInstance ruleInstance = instance.get(0);
            for (int j = 0; j < activitiRuleInstances.size(); j++) {
                ActivitiRuleInstance instance2 = activitiRuleInstances.get(j);
                if (!StringUtils.isEmpty(instance2.getRuleInstanceValue())) {
                    code = instance2.getRuleInstanceValue();
                    ruleInstance.setRuleInstanceValue(code);
                    break;
                } else {
                    ruleInstance.setRuleInstanceValue(create());
                }
            }
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
                .andUserIdEqualTo(request.getUserId()).andIsElectedEqualTo(false);
        if (activitiRuleInstanceMapper.selectByExample(ruleInstanceExample).isEmpty()) {
            return builder.body(ResponseUtils.getResponseBody("此投票人不存在"));
        }
        Integer index = activitiRuleInstanceMapper.selectByExample(ruleInstanceExample).get(0).getUserTicketCount();
        if (index <= 0) {
            return builder.body(ResponseUtils.getResponseBody("没票了"));
        }
        ActivityVoteRecordsExample activityVoteRecordsExample = new ActivityVoteRecordsExample();
        activityVoteRecordsExample.createCriteria().andUserIdEqualTo(request.getUserId())
                .andElectedUserIdEqualTo(request.getElectedUserId()).andActivityIdEqualTo(request.getActivityId());
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
        return builder.body(ResponseUtils.getResponseBody(request.getRemark()));
    }

    @ApiOperation(value = "打分", notes = "打分")
    @RequestMapping(value = "/recordScore", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> recordScore(RecordScoreRequest request) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        Double total = 0.00;
        Activity activity = activityMapper.selectByPrimaryKey(request.getActivityId());
        if (activity.getIsTimingStart() == 0) {
            return builder.body(ResponseUtils.getResponseBody("活动未开始"));
        }
        ActivitiRuleInstanceExample ruleInstanceExample = new ActivitiRuleInstanceExample();
        ruleInstanceExample.createCriteria().andActivityIdEqualTo(request.getActivityId())
                .andUserIdEqualTo(request.getUserId()).andIsElectedEqualTo(false);
        if (activitiRuleInstanceMapper.selectByExample(ruleInstanceExample).isEmpty()) {
            return builder.body(ResponseUtils.getResponseBody("此打分人不存在"));
        }
        ActivityVoteRecordsExample activityVoteRecordsExample = new ActivityVoteRecordsExample();
        activityVoteRecordsExample.createCriteria().andUserIdEqualTo(request.getUserId())
                .andElectedUserIdEqualTo(request.getElectedUserId()).andActivityIdEqualTo(request.getActivityId())
                .andUserIdEqualTo(request.getUserId()).andVoteTimesEqualTo(request.getType());
        if (!activityVoteRecordsMapper.selectByExample(activityVoteRecordsExample).isEmpty()) {
            return builder.body(ResponseUtils.getResponseBody("不能重复打分"));
        }
        Integer[] remark = request.getRemark();
        ActivityEvaluateTemplateExample example3 = new ActivityEvaluateTemplateExample();
        example3.createCriteria().andParentTemplateIdEqualTo(request.getActivityId())
                .andIsDeletedEqualTo((short) ((int) request.getType()));
        List<ActivityEvaluateTemplate> list = activityEvaluateTemplateMapper.selectByExample(example3);
        for (int i = 0; i < remark.length; i++) {
            // TODO
            ActivityEvaluateTemplate template = list.get(i);
            if (remark[i] < 0 || remark[i] > 100) {
                return builder.body(ResponseUtils.getResponseBody("超出限定分数"));
            }
            addVoteRecords(request.getActivityId(), request.getUserId(), request.getElectedUserId(), request.getType(),
                    String.valueOf(remark[i]));
            total = remark[i] * Double.valueOf(template.getEvaluateWeight()) + total;
        }
        if (total > 100) {
            return builder.body(ResponseUtils.getResponseBody("超出限定分数"));
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
        double reportScore = 0.00;
        double deedScore = 0.00;
        ActivityVoteRecordsExample example4 = new ActivityVoteRecordsExample();
        example4.createCriteria().andActivityIdEqualTo(request.getActivityId())
                .andElectedUserIdEqualTo(request.getElectedUserId()).andVoteTimesEqualTo(0);
        List<ActivityVoteRecords> list2 = activityVoteRecordsMapper.selectByExample(example4);
        for (int i = 0; i < list2.size(); i++) {
            ActivityVoteRecords records = list2.get(i);
            deedScore = Double.valueOf(records.getRemarks()) + deedScore;
        }
        if (list2.isEmpty()) {
            deedScore = 0.00;
        } else {
            deedScore = (deedScore / list2.size()) * 0.5;
        }
        example4.clear();
        example4.createCriteria().andActivityIdEqualTo(request.getActivityId())
                .andElectedUserIdEqualTo(request.getElectedUserId()).andVoteTimesEqualTo(1);
        list2 = activityVoteRecordsMapper.selectByExample(example4);
        for (int i = 0; i < list2.size(); i++) {
            ActivityVoteRecords records = list2.get(i);
            reportScore = Double.valueOf(records.getRemarks()) + reportScore;
        }
        if (list2.isEmpty()) {
            reportScore = 0.00;
        } else {
            reportScore = (reportScore / list2.size()) * 0.5;
        }
        DecimalFormat df = new DecimalFormat("0.000");
        userElect.setRemarks(String.valueOf(df.format(deedScore + reportScore)));
        activitiRuleInstanceMapper.updateByPrimaryKey(userElect);
        return builder.body(ResponseUtils.getResponseBody("打分成功"));
    }

    @ApiOperation(value = "点赞", notes = "点赞")
    @RequestMapping(value = "/clickPraise", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> clickPraise(RecordScoreRequest request) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        Integer total = 0;
        Activity activity = activityMapper.selectByPrimaryKey(request.getActivityId());
        if (activity.getIsTimingStart() == 0) {
            return builder.body(ResponseUtils.getResponseBody("活动未开始"));
        }
        com.hanfu.activity.center.model.HfUser hfUser = hfUserMapper.selectByPrimaryKey(request.getUserId());
        if (hfUser.getIdDeleted() == 1) {
            return builder.body(ResponseUtils.getResponseBody("今日票数已经用完"));
        }
        hfUser.setIdDeleted((byte) 1);
        hfUserMapper.updateByPrimaryKey(hfUser);
        addVoteRecords(request.getActivityId(), request.getUserId(), request.getElectedUserId(), 1, "1");
        ActivitiRuleInstanceExample example = new ActivitiRuleInstanceExample();
        example.createCriteria().andActivityIdEqualTo(request.getActivityId())
                .andUserIdEqualTo(request.getElectedUserId()).andIsElectedEqualTo(true);
        List<ActivitiRuleInstance> list = activitiRuleInstanceMapper.selectByExample(example);
        if (list.isEmpty()) {
            return builder.body(ResponseUtils.getResponseBody("此人不存在"));
        }
        ActivitiRuleInstance instance = list.get(0);
        if (instance.getUserTicketCount() == null) {
            instance.setUserTicketCount(1);
        } else {
            instance.setUserTicketCount(instance.getUserTicketCount() + 1);
        }
        activitiRuleInstanceMapper.updateByPrimaryKey(instance);
        return builder.body(ResponseUtils.getResponseBody(null));
    }

    @ApiOperation(value = "取消点赞", notes = "取消点赞")
    @RequestMapping(value = "/deleteclickPraise", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> deleteclickPraise(RecordScoreRequest request) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        Integer total = 0;
        Activity activity = activityMapper.selectByPrimaryKey(request.getActivityId());
        if (activity.getIsTimingStart() == 0) {
            return builder.body(ResponseUtils.getResponseBody("活动未开始"));
        }
        ActivityVoteRecordsExample example = new ActivityVoteRecordsExample();
        example.createCriteria().andActivityIdEqualTo(request.getActivityId()).andUserIdEqualTo(request.getUserId())
                .andElectedUserIdEqualTo(request.getElectedUserId());
        List<ActivityVoteRecords> list = activityVoteRecordsMapper.selectByExample(example);
        if (!list.isEmpty()) {
            com.hanfu.activity.center.model.HfUser hfUser = hfUserMapper.selectByPrimaryKey(request.getUserId());
            hfUser.setIdDeleted((byte) 0);
            hfUserMapper.updateByPrimaryKey(hfUser);
            ActivityVoteRecords records = list.get(0);
            activityVoteRecordsMapper.deleteByPrimaryKey(records.getId());
        }
        ActivitiRuleInstanceExample example2 = new ActivitiRuleInstanceExample();
        example2.createCriteria().andActivityIdEqualTo(request.getActivityId())
                .andUserIdEqualTo(request.getElectedUserId()).andIsElectedEqualTo(true);
        List<ActivitiRuleInstance> list2 = activitiRuleInstanceMapper.selectByExample(example2);
        if (list2.isEmpty()) {
            return builder.body(ResponseUtils.getResponseBody("此人不存在"));
        }
        ActivitiRuleInstance instance = list2.get(0);
        instance.setUserTicketCount(instance.getUserTicketCount() - 1);
        activitiRuleInstanceMapper.updateByPrimaryKey(instance);
        return builder.body(ResponseUtils.getResponseBody(null));
    }

    @ApiOperation(value = "判断用户是否点过赞", notes = "判断用户是否点过赞")
    @RequestMapping(value = "/findIsPraise", method = RequestMethod.GET)
    public ResponseEntity<JSONObject> findIsPraise(Integer userId) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        com.hanfu.activity.center.model.HfUser hfUser = hfUserMapper.selectByPrimaryKey(userId);
        if (hfUser.getIdDeleted() == 1) {
            return builder.body(ResponseUtils.getResponseBody("已经点过了"));
        }
        return builder.body(ResponseUtils.getResponseBody(null));
    }

    @ApiOperation(value = "内推提交", notes = "内推提交")
    @RequestMapping(value = "/addElection", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> addElection(@RequestParam Integer activityId, @RequestParam Integer userId)
            throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        ActivitiRuleInstanceExample example = new ActivitiRuleInstanceExample();
        example.createCriteria().andActivityIdEqualTo(activityId).andIsElectedEqualTo(true);
        List<ActivitiRuleInstance> list = activitiRuleInstanceMapper.selectByExample(example);
        for (int i = 0; i < list.size(); i++) {
            ActivitiRuleInstance electedUserId = list.get(i);
            addVoteRecords(activityId, userId, electedUserId.getUserId(), 1, "1");
        }
        return builder.body(ResponseUtils.getResponseBody(null));
    }

    @ApiOperation(value = "查询内推是否提交", notes = "查询内推是否提交")
    @RequestMapping(value = "/findElection", method = RequestMethod.GET)
    public ResponseEntity<JSONObject> findElection(@RequestParam Integer activityId, @RequestParam Integer userId)
            throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        boolean flag = true;
        ActivityVoteRecordsExample example = new ActivityVoteRecordsExample();
        example.createCriteria().andActivityIdEqualTo(activityId).andUserIdEqualTo(userId);
        List<ActivityVoteRecords> list = activityVoteRecordsMapper.selectByExample(example);
        if (list.isEmpty()) {
            flag = false;
        }
        return builder.body(ResponseUtils.getResponseBody(flag));
    }

//	@ApiOperation(value = "统计总分", notes = "统计总分")
//	@RequestMapping(value = "/totalScore", method = RequestMethod.POST)
//	public ResponseEntity<JSONObject> totalScore(@RequestParam Integer activityId) throws JSONException {
//		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
//		ActivityVoteRecordsExample example = new ActivityVoteRecordsExample();
//		example.createCriteria().andActivityIdEqualTo(activityId);
//		List<ActivityVoteRecords>
//		ActivitiRuleInstanceExample example = new ActivitiRuleInstanceExample();
//		example.createCriteria().andActivityIdEqualTo(activityId).andUserIdEqualTo(userId);
//		List<ActivitiRuleInstance> list = activitiRuleInstanceMapper.selectByExample(example);
//		ActivitiRuleInstance instance = list.get(0);
//		instance.setUserScore(offlineScore);
//		
//		return builder.body(ResponseUtils.getResponseBody(activitiRuleInstanceMapper.updateByPrimaryKey(instance)));
//	}

//	@ApiOperation(value = "查看活动列表", notes = "查看活动列表")
//	@RequestMapping(value = "/activities", method = RequestMethod.GET)
//	@ApiImplicitParams({
//			@ApiImplicitParam(paramType = "query", name = "userId", value = "用户id", required = true, type = "Integer") })
//	public ResponseEntity<JSONObject> activities(@RequestParam Integer userId) throws JSONException {
//		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
//		String str = "";
//		ActivityExample example = new ActivityExample();
//		example.createCriteria().andUserIdEqualTo(userId);
//		List<Activity> activity = activityMapper.selectByExample(example);
//		if (!activity.isEmpty()) {
//			for (int i = 0; i < activity.size(); i++) {
//				str = str + activity.get(i).getActivityResult();
//			}
//			return builder.body(ResponseUtils.getResponseBody(str));
//		}
//		ActivitiRuleInstanceExample example2 = new ActivitiRuleInstanceExample();
//		example2.createCriteria().andUserIdEqualTo(userId);
//		List<ActivitiRuleInstance> activitiRuleInstance = activitiRuleInstanceMapper.selectByExample(example2);
//		for (int i = 0; i < activitiRuleInstance.size(); i++) {
//			Activity activity2 = activityMapper.selectByPrimaryKey(activitiRuleInstance.get(i).getActivityId());
//			str = str + activity2.getActivityResult();
//		}
////        TODO 活动发起者查看自己的活动, 或者查看自己参与过的活动列表
//		if (str == "") {
//			return builder.body(ResponseUtils.getResponseBody("未参加活动"));
//		}
//		return builder.body(ResponseUtils.getResponseBody(str));
//	}

    @ApiOperation(value = "开启关闭活动按钮", notes = "开启关闭活动按钮")
    @RequestMapping(value = "/startActivity", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> startActivity(@RequestParam Integer activityId) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        Activity activity = activityMapper.selectByPrimaryKey(activityId);
        if ("praise".equals(activity.getActiviyType())) {
            ActivityExample example = new ActivityExample();
            example.createCriteria().andActiviyTypeEqualTo("praise");
            List<Activity> list = activityMapper.selectByExample(example);
            for (int i = 0; i < list.size(); i++) {
                Activity activity2 = list.get(i);
                activity2.setIsTimingStart((short) 0);
                activityMapper.updateByPrimaryKey(activity2);
            }
        }
        if (activity.getIsTimingStart() == (short) 0) {
            activity.setIsTimingStart((short) 1);
        } else {
            activity.setIsTimingStart((short) 0);
        }
        activityMapper.updateByPrimaryKey(activity);
        return builder.body(ResponseUtils.getResponseBody("修改成功"));
    }


    @ApiOperation(value = "开启关闭活动排行榜", notes = "开启关闭活动排行榜")
    @RequestMapping(value = "/startActivityResult", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> startActivityResult(@RequestParam Integer activityId) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        Activity activity = activityMapper.selectByPrimaryKey(activityId);
//		if ("praise".equals(activity.getActiviyType())) {
//			ActivityExample example = new ActivityExample();
//			example.createCriteria().andActiviyTypeEqualTo("praise");
//			List<Activity> list = activityMapper.selectByExample(example);
//			for (int i = 0; i < list.size(); i++) {
//				Activity activity2 = list.get(i);
//				activity2.setIsTimingStart((short) 0);
//				activityMapper.updateByPrimaryKey(activity2);
//			}
//		}
        if (activity.getIsDeleted() == (short) 0) {
            activity.setIsDeleted((short) 1);
        } else {
            activity.setIsDeleted((short) 0);
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
        Activity activity = activityMapper.selectByPrimaryKey(activityId);
        if ("praise".equals(activity.getActiviyType())) {
            ActivityVoteRecordsExample example = new ActivityVoteRecordsExample();
            example.createCriteria().andActivityIdEqualTo(activityId).andUserIdEqualTo(userId)
                    .andElectedUserIdEqualTo(electedUserId).andIsDeletedEqualTo((short) 0);
            List<ActivityVoteRecords> list = activityVoteRecordsMapper.selectByExample(example);
            if (list.isEmpty()) {
                ActivityVoteRecords activityVoteRecords = new ActivityVoteRecords();
                activityVoteRecords.setActivityId(activityId);
                activityVoteRecords.setUserId(userId);
                activityVoteRecords.setElectedUserId(electedUserId);
                activityVoteRecords.setVoteTimes(voteTimes);
                activityVoteRecords.setRemarks(remarks);
                activityVoteRecords.setCreateTime(LocalDateTime.now());
                activityVoteRecords.setModifyTime(LocalDateTime.now());
                activityVoteRecords.setIsDeleted((short) 0);
                synchronized (LOCKLOCK2) {
                    if (activityVoteRecordsMapper.selectByExample(example).isEmpty()) {
                        activityVoteRecordsMapper.insert(activityVoteRecords);
                    }
                }
            } else {
                return builder.body(ResponseUtils.getResponseBody("今日票数已经用完"));
            }
        } else {
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
        }
        return builder.body(ResponseUtils.getResponseBody(null));
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
        List<HfUser> users = new ArrayList<HfUser>();
        for (int i = 0; i < list.size(); i++) {
            ActivitiRuleInstance instance = list.get(i);
            HfUser user = new HfUser();
            user.setIsElected(instance.getIsElected());
            user.setCode(instance.getRuleInstanceValue());
            com.hanfu.activity.center.model.HfUser hfUser = hfUserMapper.selectByPrimaryKey(instance.getUserId());
            user.setRealName(hfUser.getRealName());
            if (hfUser.getRealName() != null) {
                user.setNickName(hfUser.getRealName());
            } else {
                user.setNickName(hfUser.getNickName());
            }
            user.setId(hfUser.getId());
            users.add(user);
        }
//		Integer index = 0;
//		ActivitiRuleInstanceExample example = new ActivitiRuleInstanceExample();
//		example.createCriteria().andActivityIdEqualTo(activityId);
//		List<ActivitiRuleInstance> list = activitiRuleInstanceMapper.selectByExample(example);
//		System.out.println(list.size());
//		if (list.isEmpty()) {
//			return builder.body(ResponseUtils.getResponseBody(null));
//		}
//		List<HfUser> users = new ArrayList<HfUser>(list.size());
//		List<HfUser> users2 = hfUserDao.findAllUser();
//		for (int i = 0; i < users2.size(); i++) {
//			if (users2.get(i).getRealName() != null) {
//				users2.get(i).setNickName(users2.get(i).getRealName());
//			}
//			for (int j = 0; j < list.size(); j++) {
//				System.out.println(list.get(j).getUserId());
//				if (list.get(j).getUserId() == users2.get(i).getId()) {
//					if (list.get(j).getRuleInstanceValue() != null) {
//						users2.get(i).setCode(list.get(j).getRuleInstanceValue());
//					}
//					if (list.get(j).getUserTicketCount() != null) {
//						users2.get(i).setCount((list.get(j).getUserTicketCount()));
//					}
//					if (list.get(j).getIsElected() != null) {
//						users2.get(i).setIsElected(list.get(j).getIsElected());
//					}
//					users.add(users2.get(i));
//					index++;
//				}
//			}
//		}
//		System.out.println(index);
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
            @ApiImplicitParam(paramType = "query", name = "fileId", value = "文件id", required = true, type = "Integer")})
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

    @RequestMapping(path = "/updateUserBaseInfo", method = RequestMethod.POST)
    @ApiOperation(value = "更改用户基本信息", notes = "更改用户基本信息")
    public ResponseEntity<JSONObject> updateUserBaseInfo(MultipartFile fileInfo, ActivityUserBaseInfoRequest request)
            throws Exception {
        BodyBuilder builder = ResponseUtils.getBodyBuilder();
        com.hanfu.activity.center.model.HfUser hfUser = hfUserMapper.selectByPrimaryKey(request.getUserId());
        if (!StringUtils.isEmpty(request.getUsername())) {
            hfUser.setRealName(request.getUsername());
            hfUserMapper.updateByPrimaryKey(hfUser);
        }
        if (!StringUtils.isEmpty(request.getPhone())) {
            hfUser.setPhone(request.getPhone());
            hfUserMapper.updateByPrimaryKey(hfUser);
        }
        ActivityUserInfoExample example = new ActivityUserInfoExample();
        example.createCriteria().andUserIdEqualTo(request.getUserId());
        List<ActivityUserInfo> list = activityUserInfoMapper.selectByExample(example);
        if (list.isEmpty()) {
            ActivityUserInfo userInfo = new ActivityUserInfo();
            userInfo.setUserId(request.getUserId());
            if (fileInfo != null) {
                userInfo.setFileId(updateUserAvatar(fileInfo, request.getUserId()));
            }
            if (!StringUtils.isEmpty(request.getDepartmentName())) {
                ActivityDepartmentRequest departmentRequest = new ActivityDepartmentRequest();
                departmentRequest.setDepartmentName(request.getDepartmentName());
                userInfo.setDepartmentId(updateDepartment(departmentRequest));
            }
            if (!StringUtils.isEmpty(request.getHiredate())) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                sdf.setLenient(false);
                Date date;
                try {
                    date = sdf.parse(request.getHiredate());
                } catch (ParseException e) {
                    return builder.body(ResponseUtils.getResponseBody("请输入正确的日期格式"));
                }
                Instant instant = date.toInstant();
                ZoneId zoneId = ZoneId.systemDefault();
                LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
                userInfo.setHiredate(localDateTime);
            }
            userInfo.setCreateTime(LocalDateTime.now());
            activityUserInfoMapper.insert(userInfo);
        } else {
            ActivityUserInfo userInfo = list.get(0);
            if (fileInfo != null) {
                userInfo.setFileId(updateUserAvatar(fileInfo, request.getUserId()));
            }
            if (!StringUtils.isEmpty(request.getDepartmentName())) {
                ActivityDepartmentRequest departmentRequest = new ActivityDepartmentRequest();
                departmentRequest.setDepartmentName(request.getDepartmentName());
                userInfo.setDepartmentId(updateDepartment(departmentRequest));
            }
            if (!StringUtils.isEmpty(request.getHiredate())) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                sdf.setLenient(false);
                Date date;
                try {
                    date = sdf.parse(request.getHiredate());
                } catch (ParseException e) {
                    return builder.body(ResponseUtils.getResponseBody("请输入正确的日期格式"));
                }
                Instant instant = date.toInstant();
                ZoneId zoneId = ZoneId.systemDefault();
                LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
                userInfo.setHiredate(localDateTime);
            }
            userInfo.setCreateTime(LocalDateTime.now());
            activityUserInfoMapper.updateByPrimaryKey(userInfo);
        }

        return builder.body(ResponseUtils.getResponseBody(null));
    }

    @RequestMapping(path = "/updateDepartment", method = RequestMethod.POST)
    @ApiOperation(value = "更新部门", notes = "更新部门")
    public Integer updateDepartment(ActivityDepartmentRequest request) throws Exception {
        Integer departmentId = null;
        ActivityDepartmentExample example = new ActivityDepartmentExample();
        example.createCriteria().andDepartmentNameEqualTo(request.getDepartmentName());
        List<ActivityDepartment> list = activityDepartmentMapper.selectByExample(example);
        if (list.isEmpty()) {
            ActivityDepartment department = new ActivityDepartment();
            if (!StringUtils.isEmpty(request.getDepartmentName())) {
                department.setDepartmentName(request.getDepartmentName());
            }
            if (!StringUtils.isEmpty(request.getCompanyName())) {
                department.setCompanyName(request.getCompanyName());
            }

            if (!StringUtils.isEmpty(request.getCompanyId())) {
                department.setComponyId(request.getCompanyId());
            }

            if (!StringUtils.isEmpty(request.getRemarks())) {
                department.setRemarks(request.getRemarks());
            }

            if (!StringUtils.isEmpty(request.getSuperiorId())) {
                department.setSuperiorId(request.getSuperiorId());
            }
            department.setCreateTime(LocalDateTime.now());
            department.setIsDeleted((short) 0);
            department.setModifyTime(LocalDateTime.now());
            activityDepartmentMapper.insert(department);
            departmentId = department.getId();
        } else {
            ActivityDepartment department = list.get(0);
//			if (!StringUtils.isEmpty(request.getCompanyName())) {
//				department.setCompanyName(request.getCompanyName());
//			}
//			if (!StringUtils.isEmpty(request.getRemarks())) {
//				department.setRemarks(request.getRemarks());
//			}
//			department.setModifyTime(LocalDateTime.now());
//			activityDepartmentMapper.updateByPrimaryKey(department);
            departmentId = department.getId();
        }
        return departmentId;
    }

    @RequestMapping(value = "/updateUserAvatar", method = RequestMethod.POST)
    @ApiOperation(value = "更新用户头像", notes = "更新用户头像")
    public Integer updateUserAvatar(MultipartFile fileInfo, @RequestParam Integer userId) throws Exception {
        com.hanfu.activity.center.model.HfUser hfUser = hfUserMapper.selectByPrimaryKey(userId);
        if (hfUser == null) {
            throw new Exception("此人不存在");
        }
        Integer fileId = null;
        FileMangeService fileMangeService = new FileMangeService();
        String arr[];
        arr = fileMangeService.uploadFile(fileInfo.getBytes(), String.valueOf(userId));
        FileDescExample example = new FileDescExample();
        example.createCriteria().andUserIdEqualTo(userId);
        List<FileDesc> list = fileDescMapper.selectByExample(example);
        if (list.isEmpty()) {
            FileDesc fileDesc = new FileDesc();
            fileDesc.setFileName("用户头像");
            fileDesc.setGroupName(arr[0]);
            fileDesc.setRemoteFilename(arr[1]);
            fileDesc.setUserId(userId);
            fileDesc.setCreateTime(LocalDateTime.now());
            fileDesc.setModifyTime(LocalDateTime.now());
            fileDesc.setIsDeleted((short) 0);
            fileDescMapper.insert(fileDesc);
            fileId = fileDesc.getId();
            hfUser.setFileId(fileId);
            hfUserMapper.updateByPrimaryKey(hfUser);
        } else {
            FileDesc fileDesc = list.get(0);
//			fileMangeService.deleteFile(fileDesc.getGroupName(),fileDesc.getRemoteFilename() );
            fileDesc.setGroupName(arr[0]);
            fileDesc.setRemoteFilename(arr[1]);
            fileDesc.setModifyTime(LocalDateTime.now());
            fileDescMapper.updateByPrimaryKey(fileDesc);
            fileId = fileDesc.getId();
            hfUser.setFileId(fileId);
            hfUserMapper.updateByPrimaryKey(hfUser);
        }

        return fileId;
    }

    @RequestMapping(path = "/updateUserExperience", method = RequestMethod.POST)
    @ApiOperation(value = "更改用户经历", notes = "更改用户经历")
    public ResponseEntity<JSONObject> updateUserExperience(@RequestParam String jobContent,
                                                           @RequestParam Integer userId) throws Exception {
        BodyBuilder builder = ResponseUtils.getBodyBuilder();
        ActivityUserExperienceExample example = new ActivityUserExperienceExample();
        example.createCriteria().andUserIdEqualTo(userId);
        List<ActivityUserExperience> list = activityUserExperienceMapper.selectByExample(example);
        if (list.isEmpty()) {
            ActivityUserExperience experience = new ActivityUserExperience();
            experience.setJobContent(jobContent);
            experience.setCreateTime(LocalDateTime.now());
            experience.setModifyTime(LocalDateTime.now());
            experience.setIsDeleted((short) 0);
            experience.setUserId(userId);
            activityUserExperienceMapper.insert(experience);
        } else {
            ActivityUserExperience experience = list.get(0);
            experience.setJobContent(jobContent);
            experience.setModifyTime(LocalDateTime.now());
            activityUserExperienceMapper.updateByPrimaryKey(experience);
        }
        return builder.body(ResponseUtils.getResponseBody(null));
    }

    @RequestMapping(path = "/updateUserEvaluate", method = RequestMethod.POST)
    @ApiOperation(value = "更改用户个人评价", notes = "更改用户个人评价")
    public ResponseEntity<JSONObject> updateUserEvaluate(@RequestParam String evaluate, @RequestParam Integer userId)
            throws Exception {
        BodyBuilder builder = ResponseUtils.getBodyBuilder();
        ActivityUserInfoExample example = new ActivityUserInfoExample();
        example.createCriteria().andUserIdEqualTo(userId);
        List<ActivityUserInfo> list = activityUserInfoMapper.selectByExample(example);
        if (list.isEmpty()) {
            ActivityUserInfo info = new ActivityUserInfo();
            info.setEvaluation(evaluate);
            info.setCreateTime(LocalDateTime.now());
            info.setModifyTime(LocalDateTime.now());
            info.setIsDeleted((short) 0);
            info.setUserId(userId);
            activityUserInfoMapper.insert(info);
        } else {
            ActivityUserInfo info = list.get(0);
            info.setEvaluation(evaluate);
            info.setModifyTime(LocalDateTime.now());
            activityUserInfoMapper.updateByPrimaryKey(info);
        }
        return builder.body(ResponseUtils.getResponseBody(null));
    }

    @RequestMapping(path = "/findUserFormInfo", method = RequestMethod.GET)
    @ApiOperation(value = "查询用户表单信息", notes = "查询用户表单信息")
    public ResponseEntity<JSONObject> findUserFormInfo(@RequestParam Integer userId) throws Exception {
        BodyBuilder builder = ResponseUtils.getBodyBuilder();
        UserFormInfo info = hfUserDao.findByUserId(userId);
//		LocalDateTime localDateTime = info.getHiredate();
//		ZoneId zoneId = ZoneId.systemDefault();
//        ZonedDateTime zdt = localDateTime.atZone(zoneId);
//        Date date = Date.from(zdt.toInstant());
//        SimpleDateFormat bjSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        bjSdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        if (!StringUtils.isEmpty(info.getDepartmentId())) {
            ActivityDepartment department = activityDepartmentMapper.selectByPrimaryKey(info.getDepartmentId());
            if (department != null) {
                if (!StringUtils.isEmpty(department.getDepartmentName())) {
                    info.setDepartmentName(department.getDepartmentName());
                }
            }
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        if (!StringUtils.isEmpty(info.getHiredate())) {
            String date = format.format(info.getHiredate());
            info.setDate(date);
        }
//		ActivityUserEvaluateExample example = new ActivityUserEvaluateExample();
//		example.createCriteria().andUserIdEqualTo(userId);
//		List<ActivityUserEvaluate> activityUserEvaluate = activityUserEvaluateMapper.selectByExample(example);
//		List<ActivityUserEvaluate> list = new ArrayList<ActivityUserEvaluate>(activityUserEvaluate.size());
//		for (int i = 0; i < activityUserEvaluate.size(); i++) {
//			list.set(i, activityUserEvaluate.get(i));
//		}
//		info.setList(list);
        return builder.body(ResponseUtils.getResponseBody(info));
    }

    @RequestMapping(path = "/setActivityVictoryCount", method = RequestMethod.POST)
    @ApiOperation(value = "设置活动胜利人数", notes = "设置活动胜利人数")
    public ResponseEntity<JSONObject> setActivityVictoryCount(@RequestParam Integer count,
                                                              @RequestParam Integer activityId) throws Exception {
        BodyBuilder builder = ResponseUtils.getBodyBuilder();
        Activity activity = activityMapper.selectByPrimaryKey(activityId);
        activity.setActivityStatus(String.valueOf(count));
        activityMapper.updateByPrimaryKey(activity);
        return builder.body(ResponseUtils.getResponseBody(count));
    }

}
