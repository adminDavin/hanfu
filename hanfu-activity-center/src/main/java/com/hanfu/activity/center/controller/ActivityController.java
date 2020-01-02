package com.hanfu.activity.center.controller;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hanfu.activity.center.dao.ActivitiRuleInstanceMapper;
import com.hanfu.activity.center.dao.ActivitiStrategyMapper;
import com.hanfu.activity.center.dao.ActivityDepartmentMapper;
import com.hanfu.activity.center.dao.ActivityEvaluateTemplateMapper;
import com.hanfu.activity.center.dao.ActivityMapper;
import com.hanfu.activity.center.dao.ActivityStrategyInstanceMapper;
import com.hanfu.activity.center.dao.ActivityUserInfoMapper;
import com.hanfu.activity.center.dao.ActivityVoteRecordsMapper;
import com.hanfu.activity.center.dao.HfUserMapper;
import com.hanfu.activity.center.dao.StrategyRuleMapper;
import com.hanfu.activity.center.dao.StrategyRuleRelateMapper;
import com.hanfu.activity.center.manual.dao.ActivityDao;
import com.hanfu.activity.center.manual.dao.VoteRecordsDao;
import com.hanfu.activity.center.manual.model.ActivityInfo;
import com.hanfu.activity.center.manual.model.Evaluate;
import com.hanfu.activity.center.manual.model.TimeTime;
import com.hanfu.activity.center.manual.model.VoteEntity;
import com.hanfu.activity.center.manual.model.VoteRecordsEntity;
import com.hanfu.activity.center.model.ActivitiRuleInstance;
import com.hanfu.activity.center.model.ActivitiRuleInstanceExample;
import com.hanfu.activity.center.model.ActivitiStrategy;
import com.hanfu.activity.center.model.ActivitiStrategyExample;
import com.hanfu.activity.center.model.Activity;
import com.hanfu.activity.center.model.ActivityDepartment;
import com.hanfu.activity.center.model.ActivityEvaluateTemplate;
import com.hanfu.activity.center.model.ActivityEvaluateTemplateExample;
import com.hanfu.activity.center.model.ActivityExample;
import com.hanfu.activity.center.model.ActivityStrategyInstance;
import com.hanfu.activity.center.model.ActivityStrategyInstanceExample;
import com.hanfu.activity.center.model.ActivityUserInfo;
import com.hanfu.activity.center.model.ActivityUserInfoExample;
import com.hanfu.activity.center.model.ActivityVoteRecords;
import com.hanfu.activity.center.model.ActivityVoteRecordsExample;
import com.hanfu.activity.center.model.HfUser;
import com.hanfu.activity.center.model.HfUserExample;
import com.hanfu.activity.center.model.StrategyRule;
import com.hanfu.activity.center.model.StrategyRuleExample;
import com.hanfu.activity.center.model.StrategyRuleRelate;
import com.hanfu.activity.center.model.StrategyRuleRelateExample;
import com.hanfu.activity.center.model.Total;
import com.hanfu.activity.center.request.ActivityRequest;
import com.hanfu.activity.center.request.ActivityStrategyInstanceRequest;
import com.hanfu.activity.center.request.ActivityStrategyRequest;
import com.hanfu.activity.center.request.RuleValueDescRequest;
import com.hanfu.activity.center.request.StrategyRuleRequest;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseUtils;
import com.hanfu.utils.response.handler.ResponseEntity.BodyBuilder;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/activity")
@Api
public class ActivityController {

    @Autowired
    private ActivityMapper activityMapper;

    @Autowired
    private ActivitiStrategyMapper activitiStrategyMapper;

    @Autowired
    private ActivityStrategyInstanceMapper activityStrategyInstanceMapper;

    @Autowired
    private ActivitiRuleInstanceMapper activitiRuleInstanceMapper;

    @Autowired
    private StrategyRuleRelateMapper strategyRuleRelateMapper;

    @Autowired
    private HfUserMapper hfUserMapper;

    @Autowired
    private ActivityVoteRecordsMapper activityVoteRecordsMapper;

    @Autowired
    private StrategyRuleMapper strategyRuleMapper;

    @Autowired
    private ActivityDao activityDao;

    @Autowired
    private ActivityUserInfoMapper activityUserInfoMapper;

    @Autowired
    private ActivityDepartmentMapper activityDepartmentMapper;

    @Autowired
    private VoteRecordsDao voteRecordsDao;

    @Autowired
    private ActivityEvaluateTemplateMapper activityEvaluateTemplateMapper;

    @ApiOperation(value = "查询参加该活动参与人员", notes = "查询参加该活动参与人员")
    @RequestMapping(value = "/listActivityUser", method = RequestMethod.GET)
    public ResponseEntity<JSONObject> listActivityUser(@RequestParam Integer activityId,
                                                       @RequestParam(required = false) Integer userId) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        Activity activity = activityMapper.selectByPrimaryKey(activityId);
        ActivitiRuleInstanceExample example = new ActivitiRuleInstanceExample();
        example.createCriteria().andActivityIdEqualTo(activityId).andIsElectedEqualTo(true);
        List<ActivitiRuleInstance> list = activitiRuleInstanceMapper.selectByExample(example);
        List<Total> result = new ArrayList<Total>();
        if (!list.isEmpty()) {
            Integer index = 1;
            for (int j = 0; j < list.size(); j++) {
                Total total = new Total();
                if (userId != null) {
                    ActivityVoteRecordsExample activityVoteRecordsExample = new ActivityVoteRecordsExample();
                    activityVoteRecordsExample.createCriteria().andActivityIdEqualTo(activityId)
                            .andUserIdEqualTo(userId).andElectedUserIdEqualTo(list.get(j).getUserId());
                    if (activityVoteRecordsMapper.selectByExample(activityVoteRecordsExample).isEmpty()) {
                        total.setRecord(false);
                    } else {
                        total.setRecord(true);
                    }
                    if ("praise".equals(activity.getActiviyType())) {
                        activityVoteRecordsExample.clear();
                        activityVoteRecordsExample.createCriteria().andActivityIdEqualTo(activityId)
                                .andUserIdEqualTo(userId).andElectedUserIdEqualTo(list.get(j).getUserId())
                                .andIsDeletedEqualTo((short) 0);
                        if (activityVoteRecordsMapper.selectByExample(activityVoteRecordsExample).isEmpty()) {
                            total.setRecord(false);
                        } else {
                            total.setRecord(true);
                        }
                    }
                }
                if (list.get(j).getUserScore() == null) {
                    total.setSocre(0);
                } else {
                    total.setSocre(list.get(j).getUserScore());
                }
                if (list.get(j).getUserTicketCount() == null) {
                    total.setVoteCount(0);
                } else {
                    total.setVoteCount(list.get(j).getUserTicketCount());
                }
                HfUser hfUser = hfUserMapper.selectByPrimaryKey(list.get(j).getUserId());
                if (hfUser != null) {
                    ActivityUserInfoExample example2 = new ActivityUserInfoExample();
                    example2.createCriteria().andUserIdEqualTo(list.get(j).getUserId());
                    List<ActivityUserInfo> list2 = activityUserInfoMapper.selectByExample(example2);
                    if (!list2.isEmpty()) {
                        ActivityUserInfo userInfo = list2.get(0);
                        ActivityDepartment department = activityDepartmentMapper
                                .selectByPrimaryKey(userInfo.getDepartmentId());
                        if (department != null && department.getDepartmentName() != null) {
                            total.setDepartmentName(department.getDepartmentName());
                        }
                    }
                    if (hfUser.getFileId() != null) {
                        total.setFileId(hfUser.getFileId());
                    }
                    if (hfUser.getRealName() == null) {
                        total.setUsername(hfUser.getNickName());
                    } else {
                        total.setUsername(hfUser.getRealName());
                    }
                    if ("score".equals(activityMapper.selectByPrimaryKey(activityId).getActiviyType())) {
                        if (StringUtils.isEmpty(list.get(j).getRemarks())) {
                            total.setTotalScore(0.00);
                        } else {
                            total.setTotalScore(Double.valueOf(list.get(j).getRemarks()));
                        }
                    }
                    total.setPosition(index);
                    total.setUserId(list.get(j).getUserId());
                    total.setActivityId(list.get(j).getActivityId());
                }
                index++;
                result.add(total);
            }
        }
        return builder.body(ResponseUtils.getResponseBody(result));
    }

//	@ApiOperation(value = "查询此人打分情况", notes = "查询此人打分情况")
//	@RequestMapping(value = "/findUserScore", method = RequestMethod.GET)
//	public ResponseEntity<JSONObject> findUserScore(@RequestParam Integer activityId,@RequestParam Integer userId) throws JSONException {
//		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
//		Activity activity = activityMapper.selectByPrimaryKey(activityId);
//		ActivityVoteRecordsExample example = new ActivityVoteRecordsExample();
//		if("score".equals(activity.getActiviyType())) {
//			example.createCriteria().andActivityIdEqualTo(activityId).andUserIdEqualTo(userId).andIsDeletedEqualTo(value)
//		}
//		example.createCriteria().andActivityIdEqualTo(activityId).andUserIdEqualTo(userId);
//		List<ActivityVoteRecords> list = activityVoteRecordsMapper.selectByExample(example);
//		if(list.isEmpty()) {
//			return builder.body(ResponseUtils.getResponseBody("还未打过分"));
//		}else {
//			ActivityVoteRecords records = list.get(0);
//			return builder.body(ResponseUtils.getResponseBody(records));
//		}
//	}

    @ApiOperation(value = "查询参加该活动投票人员", notes = "查询参加该活动投票人员")
    @RequestMapping(value = "/listActivityVoteUser", method = RequestMethod.GET)
    public ResponseEntity<JSONObject> listActivityVoteUser(@RequestParam Integer activityId) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        ActivitiRuleInstanceExample example = new ActivitiRuleInstanceExample();
        example.createCriteria().andActivityIdEqualTo(activityId).andIsElectedEqualTo(false);
        return builder.body(ResponseUtils.getResponseBody(activitiRuleInstanceMapper.selectByExample(example)));
    }

    @ApiOperation(value = "查询参加活动的所有人员", notes = "查询参加活动的所有人员")
    @RequestMapping(value = "/listAllActivityUser", method = RequestMethod.GET)
    public ResponseEntity<JSONObject> listAllActivityUser(@RequestParam Integer activityId) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        ActivitiRuleInstanceExample example = new ActivitiRuleInstanceExample();
        example.createCriteria().andActivityIdEqualTo(activityId);
        return builder.body(ResponseUtils.getResponseBody(activitiRuleInstanceMapper.selectByExample(example)));
    }

    @ApiOperation(value = "查询此人参加过哪些活动", notes = "查询此人参加过哪些活动")
    @RequestMapping(value = "/findUserAddActivity", method = RequestMethod.GET)
    public ResponseEntity<JSONObject> findUserAddActivity(@RequestParam Integer userId) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        ActivitiRuleInstanceExample example = new ActivitiRuleInstanceExample();
        example.createCriteria().andUserIdEqualTo(userId).andIsElectedEqualTo(true);
        List<ActivitiRuleInstance> list = activitiRuleInstanceMapper.selectByExample(example);
        List<ActivityInfo> activityInfos = new ArrayList<ActivityInfo>();
        for (int i = 0; i < list.size(); i++) {
            ActivitiRuleInstance instance = list.get(i);
            Activity activity = activityMapper.selectByPrimaryKey(instance.getActivityId());
            ActivityInfo info = new ActivityInfo();
            info.setActivityName(activity.getActivityName());
            info.setActiviyType(activity.getActiviyType());
            info.setId(activity.getId());
            activityInfos.add(info);
        }
        return builder.body(ResponseUtils.getResponseBody(activityInfos));
    }

    @ApiOperation(value = "查询此人是哪些活动的评委", notes = "查询此人是哪些活动的评委")
    @RequestMapping(value = "/findUserActivityVote", method = RequestMethod.GET)
    public ResponseEntity<JSONObject> findUserActivityVote(@RequestParam Integer userId) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        ActivitiRuleInstanceExample example = new ActivitiRuleInstanceExample();
        example.createCriteria().andUserIdEqualTo(userId).andIsElectedEqualTo(false);
        List<ActivitiRuleInstance> list = activitiRuleInstanceMapper.selectByExample(example);
        List<ActivityInfo> activityInfos = new ArrayList<ActivityInfo>();
        for (int i = 0; i < list.size(); i++) {
            ActivitiRuleInstance instance = list.get(i);
            Activity activity = activityMapper.selectByPrimaryKey(instance.getActivityId());
            ActivityInfo info = new ActivityInfo();
            info.setActivityName(activity.getActivityName());
            info.setActiviyType(activity.getActiviyType());
            info.setId(activity.getId());
            info.setActivityDesc(activity.getActivityDesc());
            info.setIsTimingStart(activity.getIsTimingStart());
            info.setUserId(activity.getUserId());
            activityInfos.add(info);
        }
        return builder.body(ResponseUtils.getResponseBody(activityInfos));
    }

    @ApiOperation(value = "查询活动", notes = "公司每次举行活动的获取")
    @RequestMapping(value = "/listActivity", method = RequestMethod.GET)
    public ResponseEntity<JSONObject> listWareHouse() throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        List<Activity> list = activityMapper.selectByExample(null);
        String type = "";
        List<ActivityInfo> activityInfos = new ArrayList<ActivityInfo>(list.size());
        for (int i = 0; i < list.size(); i++) {
            ActivitiStrategy strategy = activitiStrategyMapper.selectByPrimaryKey(list.get(i).getStrategyId());
            ActivityInfo activityInfo = new ActivityInfo();
            activityInfo.setId(list.get(i).getId());
            activityInfo.setActivityName(list.get(i).getActivityName());
            activityInfo.setActivityDesc(list.get(i).getActivityDesc());
            activityInfo.setActivityResult(list.get(i).getActivityResult());
            if (StringUtils.isEmpty(list.get(i).getActivityStatus())) {
                activityInfo.setActivityStatus("0");
            } else {
                activityInfo.setActivityStatus(list.get(i).getActivityStatus());
            }
            activityInfo.setCreateTime(DateTimeFormatter.ofPattern("yyyy-MM-dd HH：mm：ss")
                    .format(list.get(i).getCreateTime().plusHours(8L)));
//			activityInfo.setEndTime(DateTimeFormatter.ofPattern("yyyy-MM-dd HH：mm：ss").format(list.get(i).getEndTime().plusHours(8L)));
            activityInfo.setIsDeleted(list.get(i).getIsDeleted());
            activityInfo.setIsTimingStart(list.get(i).getIsTimingStart());
            activityInfo.setModifyTime(DateTimeFormatter.ofPattern("yyyy-MM-dd HH：mm：ss")
                    .format(list.get(i).getModifyTime().plusHours(8L)));
            activityInfo.setUserId(list.get(i).getUserId());
            activityInfo.setStrategyName(strategy.getStrategyName());
//			activityInfo.setStartTime(DateTimeFormatter.ofPattern("yyyy-MM-dd HH：mm：ss").format(list.get(i).getStartTime().plusHours(8L)));
            activityInfo.setType(list.get(i).getActiviyType());
            activityInfos.add(activityInfo);
            type = "";
        }
        return builder.body(ResponseUtils.getResponseBody(activityInfos));
    }

    @ApiOperation(value = "删除活动", notes = "公司每次举行活动的删除")
    @RequestMapping(value = "/deleteActivity", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "activityId", value = "活动id", required = true, type = "Integer")})
    public ResponseEntity<JSONObject> deleteActivity(@RequestParam Integer activityId) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        return builder.body(ResponseUtils.getResponseBody(activityMapper.deleteByPrimaryKey(activityId)));
    }

    @ApiOperation(value = "修改活动", notes = "公司每次举行活动的修改")
    @RequestMapping(value = "/updateActivity", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> updateActivity(ActivityRequest request) throws Exception {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        Activity activity = activityMapper.selectByPrimaryKey(request.getId());
        if (activity == null) {
            throw new Exception("活动不存在");
        }
        if (!StringUtils.isEmpty(request.getActivityName())) {
            activity.setActivityName(request.getActivityName());
        }
        if (!StringUtils.isEmpty(request.getActivityDesc())) {
            activity.setActivityDesc(request.getActivityDesc());
        }
        if (!StringUtils.isEmpty(request.getActivityStatus())) {
            activity.setActivityStatus(request.getActivityStatus());
        }
        if (!StringUtils.isEmpty(request.getActiviyType())) {
            activity.setActiviyType(request.getActiviyType());
        }
        if (!StringUtils.isEmpty(request.getStrategyId())) {
            activity.setStrategyId(request.getStrategyId());
        }
        activity.setModifyTime(LocalDateTime.now());
        return builder.body(ResponseUtils.getResponseBody(activityMapper.updateByPrimaryKey(activity)));
    }

    @ApiOperation(value = "查询活动策略", notes = "公司每次举行活动的活动策略")
    @RequestMapping(value = "/listActivityStrategy", method = RequestMethod.GET)
    public ResponseEntity<JSONObject> listActivityStrategy() throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        return builder.body(ResponseUtils.getResponseBody(activitiStrategyMapper.selectByExample(null)));
    }

    @ApiOperation(value = "删除活动策略", notes = "公司每次举行活动策略的删除")
    @RequestMapping(value = "/deleteActivityStrategy", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "activityStrategyId", value = "活动策略id", required = true, type = "Integer")})
    public ResponseEntity<JSONObject> deleteActivityStrategy(@RequestParam Integer activityStrategyId)
            throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        return builder
                .body(ResponseUtils.getResponseBody(activitiStrategyMapper.deleteByPrimaryKey(activityStrategyId)));
    }

    @ApiOperation(value = "查询活动策略实体", notes = "公司每次举行活动的活动策略实体")
    @RequestMapping(value = "/listActivityStrategyInstance", method = RequestMethod.GET)
    public ResponseEntity<JSONObject> listActivityStrategyInstance(@RequestParam Integer activityId)
            throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        ActivityStrategyInstanceExample example = new ActivityStrategyInstanceExample();
        example.createCriteria().andActivityIdEqualTo(activityId);
        return builder.body(ResponseUtils.getResponseBody(activityStrategyInstanceMapper.selectByExample(example)));
    }

    @ApiOperation(value = "删除活动策略实体", notes = "公司每次举行活动策略实体的删除")
    @RequestMapping(value = "/deleteActivityStrategyInstance", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "activityStrategyInstanceId", value = "活动策略实体id", required = true, type = "Integer")})
    public ResponseEntity<JSONObject> deleteActivityStrategyInstance(@RequestParam Integer activityStrategyInstanceId)
            throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        return builder.body(ResponseUtils
                .getResponseBody(activityStrategyInstanceMapper.deleteByPrimaryKey(activityStrategyInstanceId)));
    }

    @ApiOperation(value = "修改活动策略实体", notes = "公司每次举行活动策略实体的修改")
    @RequestMapping(value = "/updateActivityStrategyInstance", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> updateActivityStrategyInstance(ActivityStrategyInstanceRequest request)
            throws Exception {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        ActivityStrategyInstance activityStrategyInstance = activityStrategyInstanceMapper
                .selectByPrimaryKey(request.getId());
        if (activityStrategyInstance == null) {
            throw new Exception("此活动策略实体不存在");
        }
        if (!StringUtils.isEmpty(request.getRuleName())) {
            activityStrategyInstance.setRuleName(request.getRuleName());
        }
        if (!StringUtils.isEmpty(request.getRuleDesc())) {
            activityStrategyInstance.setRuleDesc(request.getRuleDesc());
        }
        if (!StringUtils.isEmpty(request.getRuleStatus())) {
            activityStrategyInstance.setRuleStatus(request.getRuleStatus());
        }
        if (!StringUtils.isEmpty(request.getRuleValue())) {
            activityStrategyInstance.setRuleValue(request.getRuleValue());
        }
        if (!StringUtils.isEmpty(request.getRuleValueType())) {
            activityStrategyInstance.setRuleValueType(request.getRuleValueType());
        }
        if (!StringUtils.isEmpty(request.getActivityId())) {
            activityStrategyInstance.setActivityId(request.getActivityId());
        }
        if (!StringUtils.isEmpty(request.getRuleId())) {
            activityStrategyInstance.setRuleId(request.getRuleId());
        }
        activityStrategyInstance.setModifyTime(LocalDateTime.now());
        return builder.body(ResponseUtils
                .getResponseBody(activityStrategyInstanceMapper.updateByPrimaryKey(activityStrategyInstance)));
    }

    @ApiOperation(value = "查询活动码", notes = "查询活动码")
    @RequestMapping(value = "/listActivityCode", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> listActivityCode(@RequestParam String code, @RequestParam Integer activityId)
            throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        boolean flag = true;
        ActivitiRuleInstanceExample example = new ActivitiRuleInstanceExample();
        example.createCriteria().andRuleInstanceValueEqualTo(code).andActivityIdEqualTo(activityId);
        List<ActivitiRuleInstance> list = activitiRuleInstanceMapper.selectByExample(example);
        if (list.isEmpty() || list.get(0).getIsElected() == true) {
            flag = false;
        } else {
            list.get(0).setIsDeleted((short) 1);
            activitiRuleInstanceMapper.updateByPrimaryKey(list.get(0));
        }
        return builder.body(ResponseUtils.getResponseBody(flag));
    }

    @ApiOperation(value = "是否已经输过邀请码", notes = "是否已经输过邀请码")
    @RequestMapping(value = "/isUseCode", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> isUseCode(@RequestParam Integer userId, @RequestParam Integer activityId)
            throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        boolean flag = true;
        ActivitiRuleInstanceExample example = new ActivitiRuleInstanceExample();
        example.createCriteria().andUserIdEqualTo(userId).andActivityIdEqualTo(activityId);
        List<ActivitiRuleInstance> list = activitiRuleInstanceMapper.selectByExample(example);
        if (list.isEmpty()) {
            return builder.body(ResponseUtils.getResponseBody("此人在此活动不存在"));
        }
        if ((short) list.get(0).getIsDeleted() == 0) {
            flag = false;
        }
        return builder.body(ResponseUtils.getResponseBody(flag));
    }

    @ApiOperation(value = "根据活动查询活动结果", notes = "根据活动查询活动结果")
    @RequestMapping(value = "/findActivityResult", method = RequestMethod.GET)
    public ResponseEntity<JSONObject> findActivityResult(@RequestParam Integer activityId) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        String victoryCount = "";
        Activity activity = activityMapper.selectByPrimaryKey(activityId);
        if ("ticket_count".equals(activity.getActiviyType())) {
            ActivityStrategyInstanceExample activityStrategyInstanceExample = new ActivityStrategyInstanceExample();
            activityStrategyInstanceExample.createCriteria().andActivityIdEqualTo(activityId)
                    .andRuleValueTypeEqualTo("ticket_count");
            ActivitiRuleInstanceExample activitiRuleInstanceExample = new ActivitiRuleInstanceExample();
            activitiRuleInstanceExample.createCriteria().andActivityIdEqualTo(activityId).andIsElectedEqualTo(false);
            List<ActivitiRuleInstance> list = activitiRuleInstanceMapper.selectByExample(activitiRuleInstanceExample);
            for (int j = 0; j < list.size(); j++) {
                ActivityVoteRecordsExample activityVoteRecordsExample = new ActivityVoteRecordsExample();
                activityVoteRecordsExample.createCriteria().andUserIdEqualTo(list.get(j).getUserId());
                List<ActivityVoteRecords> activityVoteRecords = activityVoteRecordsMapper
                        .selectByExample(activityVoteRecordsExample);
                if (!activityVoteRecords.isEmpty()) {
                    if (activityVoteRecords.size() < Integer.valueOf(activityStrategyInstanceMapper
                            .selectByExample(activityStrategyInstanceExample).get(0).getRuleValue())) {
                        for (int k = 0; k < activityVoteRecords.size(); k++) {
                            ActivitiRuleInstanceExample activitiRuleInstanceExample2 = new ActivitiRuleInstanceExample();
                            activitiRuleInstanceExample2.createCriteria().andActivityIdEqualTo(activityId)
                                    .andUserIdEqualTo(activityVoteRecords.get(k).getElectedUserId());
                            List<ActivitiRuleInstance> activitiRuleInstance = activitiRuleInstanceMapper
                                    .selectByExample(activitiRuleInstanceExample2);
                            ActivitiRuleInstance instance = activitiRuleInstance.get(0);
                            instance.setUserTicketCount(instance.getUserTicketCount() - 1);
                            instance.setUserScore(
                                    instance.getUserScore() - Integer.valueOf(activityVoteRecords.get(k).getRemarks()));
                            activitiRuleInstanceMapper.updateByPrimaryKey(instance);
                        }
                        for (int k = 0; k < Integer.valueOf(activityStrategyInstanceMapper
                                .selectByExample(activityStrategyInstanceExample).get(0).getRuleValue())
                                - activityVoteRecords.size(); k++) {
                            ActivityVoteRecords records = new ActivityVoteRecords();
                            records.setUserId(activityVoteRecords.get(0).getUserId());
                            activityVoteRecordsMapper.insert(records);
                        }
                    }
                }
            }
        }
        ActivitiRuleInstanceExample example = new ActivitiRuleInstanceExample();
        example.createCriteria().andActivityIdEqualTo(activityId).andIsElectedEqualTo(true);
        example.setOrderByClause("user_ticket_count DESC,user_score DESC,remarks DESC");
        List<ActivitiRuleInstance> list1 = activitiRuleInstanceMapper.selectByExample(example);
        List<Total> result = new ArrayList<Total>();
        if (!list1.isEmpty()) {
            Integer index = 1;
            for (int j = 0; j < list1.size(); j++) {
                Total total = new Total();
                if ("score".equals(activity.getActiviyType())) {
                    if (list1.get(j).getRemarks() == null) {
                        total.setTotalScore(0);
                    } else {
                        total.setTotalScore(Double.valueOf(list1.get(j).getRemarks()));
                    }
                } else {
                    if (list1.get(j).getUserScore() == null) {
                        total.setSocre(0);
                    } else {
                        total.setSocre(list1.get(j).getUserScore());
                    }
                }
                if (list1.get(j).getUserTicketCount() == null) {
                    total.setVoteCount(0);
                } else {
                    total.setVoteCount(list1.get(j).getUserTicketCount());
                }
                HfUser hfUser = hfUserMapper.selectByPrimaryKey(list1.get(j).getUserId());
                if (hfUser != null) {
                    if (hfUser.getFileId() != null) {
                        total.setFileId(hfUser.getFileId());
                    }
                    if (hfUser.getRealName() == null) {
                        total.setUsername(hfUser.getNickName());
                    } else {
                        total.setUsername(hfUser.getRealName());
                    }
                }
//				total.setVictoryCount(Integer.valueOf(activity.getActivityStatus()));
                total.setPosition(index);
                total.setUserId(list1.get(j).getUserId());
                total.setActivityId(list1.get(j).getActivityId());
                result.add(total);
                index++;
            }
            if ("score".equals(activity.getActiviyType())) {
                for (int i = 0; i < result.size() - 1; i++) {
                    Total total1 = result.get(i);
                    for (int j = 0; j < result.size() - 1 - i; j++) {
                        Total total2 = result.get(j);
                        Total total3 = result.get(j + 1);
                        if (total2.getTotalScore() < total3.getTotalScore()) {
                            Total total4 = total3;
                            result.set(j + 1, total2);
                            result.set(j, total4);
                        }
                    }
                }
            }
//			if(!StringUtils.isEmpty(victoryCount)) {
//				victoryCount = activity.getActivityStatus();
//			}
        }
        return builder.body(ResponseUtils.getResponseBody(result));
    }

//	@ApiOperation(value = "刷新点赞", notes = "刷新点赞")
//	@RequestMapping(value = "/updatePraise", method = RequestMethod.POST)

    @Scheduled(cron = "0 0 0 * * ?")
    public void ssgx() {
        HfUserExample example = new HfUserExample();
        example.createCriteria().andIdDeletedEqualTo((byte) 1);
        List<HfUser> list = hfUserMapper.selectByExample(example);
        for (int i = 0; i < list.size(); i++) {
            HfUser hfUser = list.get(i);
            hfUser.setIdDeleted((byte) 0);
            hfUserMapper.updateByPrimaryKey(hfUser);
        }
        List<ActivityVoteRecords> list2 = activityVoteRecordsMapper.selectByExample(null);
        for (int i = 0; i < list2.size(); i++) {
            ActivityVoteRecords records = list2.get(i);
            records.setIsDeleted((short) 1);
            activityVoteRecordsMapper.updateByPrimaryKey(records);
        }
    }

//	@ApiOperation(value = "设置活动开始与结束时间", notes = "设置活动开始与结束时间")
//	@RequestMapping(value = "/setActivityTime", method = RequestMethod.POST)
//	public ResponseEntity<JSONObject> setActivityTime(@RequestParam Integer activityId, @RequestParam String startTime,
//			@RequestParam String endTime) throws JSONException {
//		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
//		Activity activity = activityMapper.selectByPrimaryKey(activityId);
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		sdf.setLenient(false);
//		Date date;
//		Date date2;
//		try {
//			date = sdf.parse(startTime);
//			date2 = sdf.parse(endTime);
//		} catch (ParseException e) {
//			return builder.body(ResponseUtils.getResponseBody("请输入正确的日期格式"));
//		}
//		Instant instant = date.toInstant();
//		Instant instant2 = date2.toInstant();
//		ZoneId zoneId = ZoneId.systemDefault();
//		LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
//		LocalDateTime localDateTime2 = instant2.atZone(zoneId).toLocalDateTime();
//		System.out.println(localDateTime.toString());
//		System.out.println(localDateTime2.toString());
//		activity.setStartTime(localDateTime);
//		activity.setEndTime(localDateTime2);
//		activityMapper.updateByPrimaryKey(activity);
//		return builder.body(ResponseUtils.getResponseBody(null));
//	}

    //	@Scheduled(cron = "*/1 * * * * ?")
//	public void activity() {
//		ActivityExample example = new ActivityExample();
//		example.createCriteria().andStartTimeIsNotNull().andEndTimeIsNotNull();
//		List<Activity> list = activityMapper.selectByExample(example);
//		if (!list.isEmpty()) {
//			for (int i = 0; i < list.size(); i++) {
//				Activity activity = list.get(i);
//				LocalDateTime date = activity.getStartTime();
////				System.out.println(date.toString());
//				LocalDateTime date2 = activity.getEndTime();
////				System.out.println(date2.toString());
//				ZoneId z = ZoneId.of("UTC");
//				LocalDateTime localDateTime = LocalDateTime.now(z);
////				System.out.println(localDateTime.toString());
//				if (date.isBefore(localDateTime) && localDateTime.isBefore(date2)) {
//					if (activity.getIsTimingStart() != 1) {
//						activity.setIsTimingStart((short) 1);
//						activityDao.updateActivityStart(activity.getId());
//						System.out.println("开始开始");
//					}
//				}
//				if (date2.isBefore(localDateTime)) {
//					if (activity.getIsTimingStart() != 0) {
//						activity.setIsTimingStart((short) 0);
//						activityDao.updateActivityEnd(activity.getId());
//						System.out.println("停止停止");
//					}
//				}
//			}
//		}
//	}
//	
    @ApiOperation(value = "参选记录", notes = "参选记录")
    @RequestMapping(value = "/activityRecords", method = RequestMethod.GET)
    public ResponseEntity<JSONObject> activityRecords(@RequestParam Integer userId) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        ActivitiRuleInstanceExample example = new ActivitiRuleInstanceExample();
        example.createCriteria().andUserIdEqualTo(userId).andIsElectedEqualTo(true);
        List<ActivitiRuleInstance> list = activitiRuleInstanceMapper.selectByExample(example);
        List<Total> result = new ArrayList<Total>();
        if (!list.isEmpty()) {
            for (int j = 0; j < list.size(); j++) {
                Total total = new Total();
                if (list.get(j).getUserScore() == null) {
                    total.setSocre(0);
                } else {
                    total.setSocre(list.get(j).getUserScore());
                }
                if (list.get(j).getUserTicketCount() == null) {
                    total.setVoteCount(0);
                } else {
                    total.setVoteCount(list.get(j).getUserTicketCount());
                }
                Activity activity = activityMapper.selectByPrimaryKey(list.get(j).getActivityId());
                if (activity != null) {
                    if ("score".equals(activity.getActiviyType())) {
                        if (StringUtils.isEmpty(list.get(j).getRemarks())) {
                            total.setTotalScore(0.000);
                        } else {
                            total.setTotalScore(Double.valueOf(list.get(j).getRemarks()));
                        }
                    }
                    total.setActivityName(activity.getActivityName());
                    total.setActivityType(activity.getActiviyType());
                }
                if (!StringUtils.isEmpty(total.getActivityName())) {
                    result.add(total);
                }
            }
        }
        return builder.body(ResponseUtils.getResponseBody(result));
    }

    @ApiOperation(value = "投票记录", notes = "投票记录")
    @RequestMapping(value = "/voteRecords", method = RequestMethod.GET)
    public ResponseEntity<JSONObject> voteRecords(@RequestParam Integer userId, @RequestParam Integer activityId)
            throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        Activity activity = activityMapper.selectByPrimaryKey(activityId);
        Total total = new Total();
        List<Total> result = new ArrayList<Total>();
        VoteRecordsEntity entity = new VoteRecordsEntity();
        entity.setActivityId(activityId);
        entity.setUserId(userId);
        List<Integer> list = voteRecordsDao.distinctUserId(entity);
        for (int i = 0; i < list.size(); i++) {
            Integer electedId = list.get(i);
            HfUser hfUser = hfUserMapper.selectByPrimaryKey(electedId);
            if (hfUser != null) {
                ActivityUserInfoExample example2 = new ActivityUserInfoExample();
                example2.createCriteria().andUserIdEqualTo(electedId);
                List<ActivityUserInfo> list2 = activityUserInfoMapper.selectByExample(example2);
                if (!list2.isEmpty()) {
                    ActivityUserInfo userInfo = list2.get(0);
                    ActivityDepartment department = activityDepartmentMapper
                            .selectByPrimaryKey(userInfo.getDepartmentId());
                    if (department != null && department.getDepartmentName() != null) {
                        total.setDepartmentName(department.getDepartmentName());
                    }
                }
                if (hfUser.getFileId() != null) {
                    total.setFileId(hfUser.getFileId());
                }
                if (hfUser.getRealName() == null) {
                    total.setUsername(hfUser.getNickName());
                } else {
                    total.setUsername(hfUser.getRealName());
                }
                total.setUserId(electedId);
            }
            total.setActivityType(activity.getActiviyType());
            ActivityVoteRecordsExample example = new ActivityVoteRecordsExample();
            example.createCriteria().andActivityIdEqualTo(activityId).andUserIdEqualTo(userId)
                    .andElectedUserIdEqualTo(electedId);
            List<ActivityVoteRecords> list2 = activityVoteRecordsMapper.selectByExample(example);
            if (!list2.isEmpty()) {
                if ("praise".equals(activity.getActiviyType())) {
                    Integer count = 0;
                    for (int j = 0; j < list2.size(); j++) {
                        ActivityVoteRecords records2 = list2.get(j);
                        count++;
                    }
                    total.setVoteCount(count);
                }
                if ("score".equals(activity.getActiviyType())) {
                    double score = 0;
                    for (int j = 0; j < list2.size(); j++) {
                        ActivityVoteRecords records2 = list2.get(j);
                        score = score + Double.valueOf(records2.getRemarks());
                    }
                    total.setTotalScore(score);
                }
                if ("ticket_count".equals(activity.getActiviyType())) {
                    Integer count = 0;
                    Integer score = 0;
                    for (int j = 0; j < list2.size(); j++) {
                        ActivityVoteRecords records2 = list2.get(j);
                        score = score + Integer.valueOf(records2.getRemarks());
                        count++;
                    }
                    total.setSocre(score);
                    total.setVoteCount(count);
                }
            }
            result.add(total);
        }
        return builder.body(ResponseUtils.getResponseBody(result));
    }

//	@ApiOperation(value = "后台整个活动投票记录", notes = "后台整个活动投票记录")
//	@RequestMapping(value = "/ActivityvoteRecords", method = RequestMethod.GET)
//	public ResponseEntity<JSONObject> ActivityvoteRecords(@RequestParam(required = false) Integer userId,@RequestParam Integer activityId)
//			throws JSONException {
//		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
//
//		Activity activity = activityMapper.selectByPrimaryKey(activityId);
//		ActivityVoteRecordsExample example = new ActivityVoteRecordsExample();
//		if(userId == null) {
//			example.createCriteria().andActivityIdEqualTo(activityId);
//			List<ActivityVoteRecords> list = activityVoteRecordsMapper.selectByExample(example);
//			List<VoteRecordsEntity> result = new ArrayList<VoteRecordsEntity>();
//			for (int i = 0; i < list.size(); i++) {
//				ActivityVoteRecords records = list.get(i);
//				HfUser votePerson = hfUserMapper.selectByPrimaryKey(records.getUserId());
//				HfUser electedPeson = hfUserMapper.selectByPrimaryKey(records.getElectedUserId());
//				VoteRecordsEntity entity = new VoteRecordsEntity();
//				if(votePerson != null) {
//					if(votePerson.getRealName() != null) {
//						entity.setVoteName(votePerson.getRealName());
//					}else {
//						entity.setVoteName(votePerson.getNickName());
//					}
//				}
//				if(electedPeson != null) {
//					if(electedPeson.getRealName() != null) {
//						entity.setEceltedName(electedPeson.getRealName());
//					}else {
//						entity.setEceltedName(electedPeson.getNickName());
//					}
//				}
//				if("praise".equals(activity.getActiviyType())) {
//					entity.setTotalScore(1);
//				}
//				if("score".equals(activity.getActiviyType())) {
//					if(!StringUtils.isEmpty(records.getRemarks())) {
//						entity.setTotalScore(Double.valueOf(records.getRemarks()));
//					}else {
//						entity.setTotalScore(0);
//					}
//				}
//				entity.setVoteTimes(DateTimeFormatter.ofPattern("yyyy-MM-dd HH：mm：ss").format(records.getCreateTime().plusHours(8L)));
//				result.add(entity);
//			}
//			return builder.body(ResponseUtils.getResponseBody(result));
//		}
//		return builder.body(ResponseUtils.getResponseBody(null));
//	}

    @ApiOperation(value = "后台整个活动投票记录", notes = "后台整个活动投票记录")
    @RequestMapping(value = "/ActivityvoteRecords", method = RequestMethod.GET)
    public ResponseEntity<JSONObject> ActivityvoteRecords(@RequestParam Integer activityId) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        Activity activity = activityMapper.selectByPrimaryKey(activityId);
        ActivitiRuleInstanceExample example = new ActivitiRuleInstanceExample();
        List<VoteRecordsEntity> result = new ArrayList<VoteRecordsEntity>();
        if ("praise".equals(activity.getActiviyType())) {
            example.createCriteria().andActivityIdEqualTo(activityId).andIsElectedEqualTo(true);
            example.setOrderByClause("user_ticket_count DESC");
            List<ActivitiRuleInstance> list = activitiRuleInstanceMapper.selectByExample(example);
            for (int i = 0; i < list.size(); i++) {
                VoteRecordsEntity entity = new VoteRecordsEntity();
                ActivitiRuleInstance instance = list.get(i);
                HfUser hfUser = hfUserMapper.selectByPrimaryKey(instance.getUserId());
                if (StringUtils.isEmpty(hfUser.getRealName())) {
                    entity.setEceltedName(hfUser.getNickName());
                } else {
                    entity.setEceltedName(hfUser.getRealName());
                }
                entity.setTotalScore(String.valueOf(instance.getUserTicketCount()));
                entity.setUserId(instance.getUserId());
                result.add(entity);
            }
        }

        if ("score".equals(activity.getActiviyType())) {
            List<Integer> count = new ArrayList<Integer>();
            example.createCriteria().andActivityIdEqualTo(activityId).andIsElectedEqualTo(true);
            example.setOrderByClause("remarks DESC");
            List<ActivitiRuleInstance> list = activitiRuleInstanceMapper.selectByExample(example);
            for (int i = 0; i < list.size(); i++) {
                VoteRecordsEntity entity = new VoteRecordsEntity();
                ActivitiRuleInstance instance = list.get(i);
                HfUser hfUser = hfUserMapper.selectByPrimaryKey(instance.getUserId());
                if (StringUtils.isEmpty(hfUser.getRealName())) {
                    entity.setEceltedName(hfUser.getNickName());
                } else {
                    entity.setEceltedName(hfUser.getRealName());
                }
                DecimalFormat df = new DecimalFormat("0.000");
//				entity.setTotalScore(df.format(Double.valueOf(instance.getRemarks())));
                entity.setUserId(instance.getUserId());
                double reportScore = 0.00;
                double deedScore = 0.00;
                ActivityVoteRecordsExample example4 = new ActivityVoteRecordsExample();
                example4.createCriteria().andActivityIdEqualTo(activityId).andElectedUserIdEqualTo(instance.getUserId())
                        .andVoteTimesEqualTo(0);
                List<ActivityVoteRecords> list2 = activityVoteRecordsMapper.selectByExample(example4);
                VoteRecordsEntity entity2 = new VoteRecordsEntity();
                entity2.setActivityId(activityId);
                entity2.setType(Integer.valueOf(0));
                entity2.setElectedId(instance.getUserId());
                count = voteRecordsDao.distinctUserIdvote(entity2);
                for (int j = 0; j < list2.size(); j++) {
                    ActivityVoteRecords records = list2.get(j);
                    deedScore = Double.valueOf(records.getRemarks()) + deedScore;
                }
                if (list2.isEmpty()) {
                    deedScore = 0.00;
                } else {
                    deedScore = (deedScore / list2.size()) * 0.5;
                }
                example4.clear();
                example4.createCriteria().andActivityIdEqualTo(activityId).andElectedUserIdEqualTo(instance.getUserId())
                        .andVoteTimesEqualTo(1);
                list2 = activityVoteRecordsMapper.selectByExample(example4);
                entity2.setActivityId(activityId);
                entity2.setType(Integer.valueOf(0));
                entity2.setElectedId(instance.getUserId());
                voteRecordsDao.distinctUserIdvote(entity2);
                for (int j = 0; j < list2.size(); j++) {
                    ActivityVoteRecords records = list2.get(j);
                    reportScore = Double.valueOf(records.getRemarks()) + reportScore;
                }
                if (list2.isEmpty()) {
                    reportScore = 0.00;
                } else {
                    reportScore = (reportScore / list2.size()) * 0.5;
                }
                entity.setOnlineScore(df.format(deedScore));
                entity.setOfflineScore(df.format(reportScore));
                if (!entity.getOnlineScore().equals("0.000") && !entity.getOfflineScore().equals("0.000")) {
                    entity.setTotalScore(df.format(deedScore + reportScore));
                } else if (!entity.getOnlineScore().equals("0.000") && entity.getOfflineScore().equals("0.000")) {
                    entity.setTotalScore(df.format(deedScore));
                } else if (entity.getOnlineScore().equals("0.000") && !entity.getOfflineScore().equals("0.000")) {
                    entity.setTotalScore(df.format(reportScore));
                }
                if (count.size() >= voteRecordsDao.distinctUserIdvote(entity2).size()) {
                    entity.setVoteCount(count.size());
                } else {
                    entity.setVoteCount(voteRecordsDao.distinctUserIdvote(entity2).size());
                }
                result.add(entity);
            }
        }

        if ("election".equals(activity.getActiviyType())) {
            ActivityVoteRecordsExample example2 = new ActivityVoteRecordsExample();
            example2.createCriteria().andActivityIdEqualTo(activityId);
            List<ActivityVoteRecords> list = activityVoteRecordsMapper.selectByExample(example2);
            for (int i = 0; i < list.size(); i++) {
                ActivityVoteRecords records = list.get(i);
                HfUser votePerson = hfUserMapper.selectByPrimaryKey(records.getUserId());
                HfUser electedPeson = hfUserMapper.selectByPrimaryKey(records.getElectedUserId());
                VoteRecordsEntity entity = new VoteRecordsEntity();
                if (votePerson != null) {
                    if (votePerson.getRealName() != null) {
                        entity.setVoteName(votePerson.getRealName());
                    } else {
                        entity.setVoteName(votePerson.getNickName());
                    }
                }
                if (electedPeson != null) {
                    if (electedPeson.getRealName() != null) {
                        entity.setEceltedName(electedPeson.getRealName());
                    } else {
                        entity.setEceltedName(electedPeson.getNickName());
                    }
                }
                entity.setVoteTimes(DateTimeFormatter.ofPattern("yyyy-MM-dd HH：mm：ss")
                        .format(records.getCreateTime().plusHours(8L)));
                result.add(entity);
            }
        }
        return builder.body(ResponseUtils.getResponseBody(result));
    }

    @ApiOperation(value = "后台活动投票记录详情查询", notes = "后台活动投票记录详情查询")
    @RequestMapping(value = "/ActivityvoteRecordsDetail", method = RequestMethod.GET)
    public ResponseEntity<JSONObject> ActivityvoteRecords(@RequestParam Integer userId,
                                                          @RequestParam Integer activityId, @RequestParam(required = false) String type, Integer pageNum,
                                                          Integer pageSize) throws JSONException {
        if (pageNum == null) {
            pageNum = 0;
        }
        if (pageSize == null) {
            pageSize = 0;
        }
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        Activity activity = activityMapper.selectByPrimaryKey(activityId);
        List<VoteRecordsEntity> result = new ArrayList<VoteRecordsEntity>();
        if ("praise".equals(activity.getActiviyType())) {
            List<Object> objects = new ArrayList<Object>();
            ActivityVoteRecordsExample example = new ActivityVoteRecordsExample();
            example.createCriteria().andActivityIdEqualTo(activityId).andElectedUserIdEqualTo(userId);
            PageHelper.startPage(pageNum, pageSize);
            List<ActivityVoteRecords> list = activityVoteRecordsMapper.selectByExample(example);
            PageInfo<ActivityVoteRecords> page = new PageInfo<ActivityVoteRecords>(list);
            for (int i = 0; i < list.size(); i++) {
                VoteRecordsEntity entity = new VoteRecordsEntity();
                ActivityVoteRecords records = list.get(i);
                HfUser votePerson = hfUserMapper.selectByPrimaryKey(records.getUserId());
                if (!StringUtils.isEmpty(votePerson.getRealName())) {
                    entity.setVoteRealName(votePerson.getRealName());
                } else {
                    entity.setVoteRealName("");
                }
                if (!StringUtils.isEmpty(votePerson.getNickName())) {
                    entity.setVoteNickName(votePerson.getNickName());
                } else {
                    entity.setVoteNickName("");
                }
                entity.setTotalScore("1");
                entity.setVoteTimes(DateTimeFormatter.ofPattern("yyyy-MM-dd HH：mm：ss")
                        .format(records.getCreateTime().plusHours(8L)));
                result.add(entity);
            }
            objects.add(result);
            objects.add(page);
            return builder.body(ResponseUtils.getResponseBody(objects));
        }
        if ("score".equals(activity.getActiviyType())) {
            VoteEntity voteEntity = new VoteEntity();
            List<Object> object = new ArrayList<Object>();
            ActivityEvaluateTemplateExample example2 = new ActivityEvaluateTemplateExample();
            example2.createCriteria().andParentTemplateIdEqualTo(activityId).andIsDeletedEqualTo(Short.valueOf(type));
            List<ActivityEvaluateTemplate> list2 = activityEvaluateTemplateMapper.selectByExample(example2);
            List<Evaluate> userEvaluateInfo = new ArrayList<Evaluate>();
            int index = 65;
            for (int i = 0; i < list2.size(); i++) {
                Evaluate evaluate = new Evaluate();
                ActivityEvaluateTemplate template = list2.get(i);
                evaluate.setEvaluateTemplateId(template.getId());
                evaluate.setEvaluateType(template.getEvaluateType());
                evaluate.setRemarks(template.getRemarks());
                evaluate.setEvaluateWeight(template.getEvaluateWeight());
                evaluate.setEvaluateContent(template.getEvaluateContent());
                evaluate.setZimu((char) index);
                index++;
                userEvaluateInfo.add(evaluate);
            }
            object.add(userEvaluateInfo);
            VoteRecordsEntity entity = new VoteRecordsEntity();
            ActivityVoteRecordsExample example = new ActivityVoteRecordsExample();
            entity.setActivityId(activityId);
            entity.setType(Integer.valueOf(type));
            entity.setElectedId(userId);
            PageHelper.startPage(pageNum, pageSize);
            List<Integer> list = voteRecordsDao.distinctUserIdvote(entity);
            PageInfo<Integer> page = new PageInfo<Integer>(list);
            for (int i = 0; i < list.size(); i++) {
                DecimalFormat df = new DecimalFormat("0.000");
                double totalScore = 0.000;
                List<Double> score = new ArrayList<Double>();
                VoteRecordsEntity entity2 = new VoteRecordsEntity();
                HfUser votePerson = hfUserMapper.selectByPrimaryKey(list.get(i));
                example.createCriteria().andActivityIdEqualTo(activityId).andElectedUserIdEqualTo(userId)
                        .andVoteTimesEqualTo(Integer.valueOf(type)).andUserIdEqualTo(votePerson.getId());
                for (int j = 0; j < activityVoteRecordsMapper.selectByExample(example).size(); j++) {
                    totalScore = totalScore
                            + Double.valueOf(activityVoteRecordsMapper.selectByExample(example).get(j).getRemarks())
                            * Double.valueOf(userEvaluateInfo.get(j).getEvaluateWeight());
                    score.add(Double.valueOf(df.format(
                            Double.valueOf(activityVoteRecordsMapper.selectByExample(example).get(j).getRemarks()))));
                }

                if (!StringUtils.isEmpty(votePerson.getRealName())) {
                    entity2.setVoteRealName(votePerson.getRealName());
                } else {
                    entity2.setVoteRealName("");
                }
                if (!StringUtils.isEmpty(votePerson.getNickName())) {
                    entity2.setVoteNickName(votePerson.getNickName());
                } else {
                    entity2.setVoteNickName("");
                }
                entity2.setElectedId(userId);
                entity2.setUserId(votePerson.getId());
                entity2.setSocre(score);
                entity2.setTotalScore(df.format(totalScore));
                entity2.setVoteTimes(DateTimeFormatter.ofPattern("yyyy-MM-dd HH：mm：ss").format(
                        activityVoteRecordsMapper.selectByExample(example).get(0).getCreateTime().plusHours(8L)));
                result.add(entity2);
                example.clear();
            }
            object.add(result);
            object.add(page);
            return builder.body(ResponseUtils.getResponseBody(object));
        }
        return builder.body(ResponseUtils.getResponseBody(result));
    }

    @ApiOperation(value = "后台修改投票分数", notes = "后台整个活动投票记录")
    @RequestMapping(value = "/ActivityvoteRecordsUpdate", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> ActivityvoteRecords(@RequestParam Integer userId,
                                                          @RequestParam Integer activityId, @RequestParam String[] score, @RequestParam String type,
                                                          @RequestParam Integer electedId) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        ActivitiRuleInstanceExample instanceExample = new ActivitiRuleInstanceExample();
        instanceExample.createCriteria().andActivityIdEqualTo(activityId).andUserIdEqualTo(electedId);
        List<ActivitiRuleInstance> list3 = activitiRuleInstanceMapper.selectByExample(instanceExample);
        ActivitiRuleInstance userElect = list3.get(0);
        ActivityVoteRecordsExample example = new ActivityVoteRecordsExample();
        example.createCriteria().andActivityIdEqualTo(activityId).andElectedUserIdEqualTo(electedId)
                .andUserIdEqualTo(userId).andVoteTimesEqualTo(Integer.valueOf(type));
        List<ActivityVoteRecords> list = activityVoteRecordsMapper.selectByExample(example);
        for (int i = 0; i < list.size(); i++) {
            ActivityVoteRecords records = list.get(i);
            if (!StringUtils.isEmpty(score[i])) {
                records.setRemarks(score[i]);
            } else {
                records.setRemarks("0");
            }
            activityVoteRecordsMapper.updateByPrimaryKey(records);
        }
        double reportScore = 0.00;
        double deedScore = 0.00;
        ActivityVoteRecordsExample example4 = new ActivityVoteRecordsExample();
        example4.createCriteria().andActivityIdEqualTo(activityId).andElectedUserIdEqualTo(electedId)
                .andVoteTimesEqualTo(0);
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
        example4.createCriteria().andActivityIdEqualTo(activityId).andElectedUserIdEqualTo(electedId)
                .andVoteTimesEqualTo(1);
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
        return builder.body(ResponseUtils.getResponseBody(null));
    }

}
