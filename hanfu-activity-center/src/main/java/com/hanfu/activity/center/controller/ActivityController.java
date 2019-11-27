package com.hanfu.activity.center.controller;

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

import com.alibaba.fastjson.JSONObject;
import com.hanfu.activity.center.dao.ActivitiRuleInstanceMapper;
import com.hanfu.activity.center.dao.ActivitiStrategyMapper;
import com.hanfu.activity.center.dao.ActivityMapper;
import com.hanfu.activity.center.dao.ActivityStrategyInstanceMapper;
import com.hanfu.activity.center.dao.ActivityVoteRecordsMapper;
import com.hanfu.activity.center.dao.HfUserMapper;
import com.hanfu.activity.center.dao.StrategyRuleMapper;
import com.hanfu.activity.center.dao.StrategyRuleRelateMapper;
import com.hanfu.activity.center.manual.model.ActivityInfo;
import com.hanfu.activity.center.model.ActivitiRuleInstance;
import com.hanfu.activity.center.model.ActivitiRuleInstanceExample;
import com.hanfu.activity.center.model.ActivitiStrategy;
import com.hanfu.activity.center.model.ActivitiStrategyExample;
import com.hanfu.activity.center.model.Activity;
import com.hanfu.activity.center.model.ActivityStrategyInstance;
import com.hanfu.activity.center.model.ActivityStrategyInstanceExample;
import com.hanfu.activity.center.model.ActivityVoteRecords;
import com.hanfu.activity.center.model.ActivityVoteRecordsExample;
import com.hanfu.activity.center.model.HfUser;
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
	
	@ApiOperation(value = "查询参加该活动参与人员", notes = "查询参加该活动参与人员")
	@RequestMapping(value = "/listActivityUser", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> listActivityUser(@RequestParam Integer activityId) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		ActivitiRuleInstanceExample example = new ActivitiRuleInstanceExample();
		example.createCriteria().andActivityIdEqualTo(activityId).andIsElectedEqualTo(true);
		List<ActivitiRuleInstance> list = activitiRuleInstanceMapper.selectByExample(example);
		List<Total> result = new ArrayList<Total>();
		if (!list.isEmpty()) {
			Integer index = 1;
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
				HfUser hfUser = hfUserMapper.selectByPrimaryKey(list.get(j).getUserId());
				total.setFileId(hfUser.getFileId());
				total.setUsername(hfUser.getUsername());
				total.setPosition(index);
				total.setUserId(list.get(j).getUserId());
				total.setActivityId(list.get(j).getActivityId());
				result.add(total);
				index++;
			}
		}
		return builder.body(ResponseUtils.getResponseBody(result));
	}
	
	@ApiOperation(value = "查询参加打分活动的人员情况", notes = "查询参加打分活动的人员情况")
	@RequestMapping(value = "/listTotalScore", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> listTotalScore(
			@RequestParam Integer activityId) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		ActivitiRuleInstanceExample example = new ActivitiRuleInstanceExample();
		example.createCriteria().andActivityIdEqualTo(activityId).andIsElectedEqualTo(true);
		List<ActivitiRuleInstance> list = activitiRuleInstanceMapper.selectByExample(example);
		List<Total> result = new ArrayList<Total>();
		if (!list.isEmpty()) {
//			Integer index = 1;
			for (int j = 0; j < list.size(); j++) {
				Total total = new Total();
				HfUser hfUser = hfUserMapper.selectByPrimaryKey(list.get(j).getUserId());
				total.setSocre(Integer.valueOf(list.get(j).getRemarks())/list.get(j).getUserTicketCount()+list.get(j).getUserScore());
				total.setFileId(hfUser.getFileId());
				total.setUsername(hfUser.getUsername());
//				total.setPosition(index);
				total.setUserId(list.get(j).getUserId());
				total.setActivityId(list.get(j).getActivityId());
				result.add(total);
			}
		}
		return builder.body(ResponseUtils.getResponseBody(result));
	}
	
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

	@ApiOperation(value = "查询活动", notes = "公司每次举行活动的获取")
	@RequestMapping(value = "/listActivity", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> listWareHouse() throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		List<Activity> list = activityMapper.selectByExample(null);
		String type = "";
		List<ActivityInfo> activityInfos = new ArrayList<ActivityInfo>(list.size());
		for (int i = 0; i < list.size(); i++) {
			ActivitiStrategy strategy = activitiStrategyMapper.selectByPrimaryKey(list.get(i).getStrategyId());
			ActivityStrategyInstanceExample example = new ActivityStrategyInstanceExample();
			example.createCriteria().andActivityIdEqualTo(list.get(i).getId());
			List<ActivityStrategyInstance> instance = activityStrategyInstanceMapper.selectByExample(example);
			for (int j = 0; j < instance.size(); j++) {
				if(!"user_list".equals(instance.get(j).getRuleValueType()) && instance.get(j).getRuleValueType() != null) {
					type = instance.get(j).getRuleValueType();
				}
			}
			ActivityInfo activityInfo = new ActivityInfo();
			activityInfo.setId(list.get(i).getId());
			activityInfo.setActivityName(list.get(i).getActivityName());
			activityInfo.setActivityDesc(list.get(i).getActivityDesc());
			activityInfo.setActivityResult(list.get(i).getActivityResult());
			activityInfo.setActivityStatus(list.get(i).getActivityStatus());
			activityInfo.setActiviyType(list.get(i).getActiviyType());
			activityInfo.setCreateTime(list.get(i).getCreateTime());
			activityInfo.setEndTime(list.get(i).getEndTime());
			activityInfo.setIsDeleted(list.get(i).getIsDeleted());
			activityInfo.setIsTimingStart(list.get(i).getIsTimingStart());
			activityInfo.setModifyTime(list.get(i).getModifyTime());
			activityInfo.setStrategyId(strategy.getId());
			activityInfo.setUserId(list.get(i).getUserId());
			activityInfo.setStrategyName(strategy.getStrategyName());
			activityInfo.setStartTime(list.get(i).getStartTime());
			activityInfo.setType(type);
			activityInfos.add(activityInfo);
			type = "";
		}
		return builder.body(ResponseUtils.getResponseBody(activityInfos));
	}

	@ApiOperation(value = "删除活动", notes = "公司每次举行活动的删除")
	@RequestMapping(value = "/deleteActivity", method = RequestMethod.POST)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "activityId", value = "活动id", required = true, type = "Integer") })
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
			@ApiImplicitParam(paramType = "query", name = "activityStrategyId", value = "活动策略id", required = true, type = "Integer") })
	public ResponseEntity<JSONObject> deleteActivityStrategy(@RequestParam Integer activityStrategyId) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		return builder.body(ResponseUtils.getResponseBody(activitiStrategyMapper.deleteByPrimaryKey(activityStrategyId)));
	}
	
	@ApiOperation(value = "查询活动策略实体", notes = "公司每次举行活动的活动策略实体")
	@RequestMapping(value = "/listActivityStrategyInstance", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> listActivityStrategyInstance(@RequestParam Integer activityId) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		ActivityStrategyInstanceExample example = new ActivityStrategyInstanceExample();
		example.createCriteria().andActivityIdEqualTo(activityId);
		return builder.body(ResponseUtils.getResponseBody(activityStrategyInstanceMapper.selectByExample(example)));
	}
	
	@ApiOperation(value = "删除活动策略实体", notes = "公司每次举行活动策略实体的删除")
	@RequestMapping(value = "/deleteActivityStrategyInstance", method = RequestMethod.POST)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "activityStrategyInstanceId", value = "活动策略实体id", required = true, type = "Integer") })
	public ResponseEntity<JSONObject> deleteActivityStrategyInstance(@RequestParam Integer activityStrategyInstanceId) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		return builder.body(ResponseUtils.getResponseBody(activityStrategyInstanceMapper.deleteByPrimaryKey(activityStrategyInstanceId)));
	}

	@ApiOperation(value = "修改活动策略实体", notes = "公司每次举行活动策略实体的修改")
	@RequestMapping(value = "/updateActivityStrategyInstance", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> updateActivityStrategyInstance(ActivityStrategyInstanceRequest request) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		ActivityStrategyInstance activityStrategyInstance = activityStrategyInstanceMapper.selectByPrimaryKey(request.getId());
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
		return builder.body(ResponseUtils.getResponseBody(activityStrategyInstanceMapper.updateByPrimaryKey(activityStrategyInstance)));
	}
	
	@ApiOperation(value = "查询活动码", notes = "查询活动码")
	@RequestMapping(value = "/listActivityCode", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> listActivityCode(@RequestParam String code,@RequestParam Integer activityId) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		boolean flag = true;
		ActivitiRuleInstanceExample example = new ActivitiRuleInstanceExample();
		example.createCriteria().andRuleInstanceValueEqualTo(code).andActivityIdEqualTo(activityId);
		List<ActivitiRuleInstance> list = activitiRuleInstanceMapper.selectByExample(example);
		if(list.isEmpty() || list.get(0).getIsElected() == true) {
			flag = false;
		}else {
			list.get(0).setIsDeleted((short) 1);
			activitiRuleInstanceMapper.updateByPrimaryKey(list.get(0));
		}
		return builder.body(ResponseUtils.getResponseBody(flag));
	}
	
	
	@ApiOperation(value = "是否已经输过邀请码", notes = "是否已经输过邀请码")
	@RequestMapping(value = "/isUseCode", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> isUseCode(@RequestParam Integer userId,@RequestParam Integer activityId) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		boolean flag = true;
		ActivitiRuleInstanceExample example = new ActivitiRuleInstanceExample();
		example.createCriteria().andUserIdEqualTo(userId).andActivityIdEqualTo(activityId);
		List<ActivitiRuleInstance> list = activitiRuleInstanceMapper.selectByExample(example);
		if((short) list.get(0).getIsDeleted() == 0) {
			flag = false;
		}
		return builder.body(ResponseUtils.getResponseBody(flag));
	}
	
	@ApiOperation(value = "根据活动查询活动结果", notes = "根据活动查询活动结果")
	@RequestMapping(value = "/findActivityResult", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> findActivityResult(@RequestParam Integer activityId) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		ActivityStrategyInstanceExample activityStrategyInstanceExample = new ActivityStrategyInstanceExample();
		activityStrategyInstanceExample.createCriteria().andActivityIdEqualTo(activityId)
				.andRuleValueTypeEqualTo("ticket_count");
		ActivitiRuleInstanceExample activitiRuleInstanceExample = new ActivitiRuleInstanceExample();
		activitiRuleInstanceExample.createCriteria().andActivityIdEqualTo(activityId)
				.andIsElectedEqualTo(false);
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
		ActivitiRuleInstanceExample example = new ActivitiRuleInstanceExample();
		example.createCriteria().andActivityIdEqualTo(activityId).andIsElectedEqualTo(true);
		example.setOrderByClause("user_ticket_count DESC,user_score DESC");
		List<ActivitiRuleInstance> list1 = activitiRuleInstanceMapper.selectByExample(example);
		List<Total> result = new ArrayList<Total>();
		if (!list1.isEmpty()) {
			Integer index = 1;
			for (int j = 0; j < list1.size(); j++) {
				Total total = new Total();
				if (list1.get(j).getUserScore() == null) {
					total.setSocre(0);
				} else {
					total.setSocre(list1.get(j).getUserScore());
				}
				if (list1.get(j).getUserTicketCount() == null) {
					total.setVoteCount(0);
				} else {
					total.setVoteCount(list1.get(j).getUserTicketCount());
				}
				HfUser hfUser = hfUserMapper.selectByPrimaryKey(list1.get(j).getUserId());
				total.setFileId(hfUser.getFileId());
				total.setUsername(hfUser.getUsername());
				total.setPosition(index);
				total.setUserId(list1.get(j).getUserId());
				total.setActivityId(list1.get(j).getActivityId());
				result.add(total);
				index++;
			}
		}
		return builder.body(ResponseUtils.getResponseBody(result));
	}
	
}
