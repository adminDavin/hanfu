package com.hanfu.activity.center.controller;

import java.util.ArrayList;
import java.util.List;

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
import com.hanfu.activity.center.dao.ActivityVoteRecordsMapper;
import com.hanfu.activity.center.model.ActivitiRuleInstance;
import com.hanfu.activity.center.model.ActivitiRuleInstanceExample;
import com.hanfu.activity.center.model.ActivityVoteRecords;
import com.hanfu.activity.center.model.ActivityVoteRecordsExample;
import com.hanfu.activity.center.model.Total;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseUtils;
import com.hanfu.utils.response.handler.ResponseEntity.BodyBuilder;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/activity/vote")
@Api
public class ActivityVoteController {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private ActivityVoteRecordsMapper activityVoteRecordsMapper;
    
    @ApiOperation(value = "获取评优事迹平均分某人", notes = "获取评优事迹平均分某人")
	@RequestMapping(value = "/getEvaluationSocre", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> creatrCode(Integer usersId, @RequestParam Integer activityId)
			throws JSONException {
    	BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
    	double deedScore = 0.000;
    	 ActivityVoteRecordsExample example4 = new ActivityVoteRecordsExample();
    		example4.createCriteria().andActivityIdEqualTo(activityId)
    				.andElectedUserIdEqualTo(usersId).andVoteTimesEqualTo(0);
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
    		System.out.println(deedScore);
    		return builder.body(ResponseUtils.getResponseBody(null));
	}
    
}
