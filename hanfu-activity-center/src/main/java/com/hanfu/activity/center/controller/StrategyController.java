package com.hanfu.activity.center.controller;

import com.alibaba.fastjson.JSONObject;
import com.hanfu.activity.center.dao.*;
import com.hanfu.activity.center.manual.dao.StrategyRuleDao;
import com.hanfu.activity.center.manual.model.Evaluate;
import com.hanfu.activity.center.model.*;
import com.hanfu.activity.center.request.ActivityCompanyRequest;
import com.hanfu.activity.center.request.ActivityEvaluateTemplateRequest;
import com.hanfu.activity.center.request.ActivityUserEvaluateRequest;
import com.hanfu.common.service.FileMangeService;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseEntity.BodyBuilder;
import com.hanfu.utils.response.handler.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    private ActivityVoteRecordsMapper activityVoteRecordsMapper;

    @Autowired
    private ActivityDepartmentMapper activityDepartmentMapper;

    @Autowired
    private ActivityUserInfoMapper activityUserInfoMapper;

    @ApiOperation(value = "查询策略规则", notes = "公司每次举行活动的策略规则")
    @RequestMapping(value = "/listStrategyRule", method = RequestMethod.GET)
    public ResponseEntity<JSONObject> listStrategyRule() throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
//		StrategyRuleExample example = new StrategyRuleExample();
//		example.setDistinct(true);
        List<String> list = strategyRuleDao.findRuleType();
        for (int i = 0; i < list.size(); i++) {
            String str = list.get(i);
            if ("elector".equals(list.get(i))) {
                list.set(i, "投票人");
            }
            if ("elected".equals(list.get(i))) {
                list.set(i, "被投票人");
            }
            if ("vote_ticket_count".equals(list.get(i))) {
                list.set(i, "星星投票");
            }
            if ("record_score".equals(list.get(i))) {
                list.set(i, "线上线下评分");
            }
            if ("internal_election".equals(list.get(i))) {
                list.set(i, "内部选举");
            }
            if ("public_praise".equals(list.get(i))) {
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
        FileDescExample example = new FileDescExample();
        //TODO
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
        if (!StringUtils.isEmpty(request.getEvaluateContent())) {
            template.setEvaluateContent(request.getEvaluateContent());
        }
        template.setEvaluateType(request.getEvaluateType());
        if (!StringUtils.isEmpty(request.getRemarks())) {
            template.setRemarks(request.getRemarks());
        }
        ActivityEvaluateTemplateExample example = new ActivityEvaluateTemplateExample();
        example.createCriteria().andParentTemplateIdEqualTo(request.getParentTemplateId());
        List<ActivityEvaluateTemplate> list = activityEvaluateTemplateMapper.selectByExample(example);
        Double count = 0.0;
//		if(Double.valueOf(request.getEvaluateWeight()) < 0.00 || Double.valueOf(request.getEvaluateWeight()) > 1.00) {
//			return builder.body(ResponseUtils.getResponseBody("非法，比重分配超过1"));
//		}
//		for (int i = 0; i < list.size(); i++) {
//			ActivityEvaluateTemplate template1 = list.get(i);
//			count = count + Double.valueOf(template1.getEvaluateWeight());
//			if(count + Double.valueOf(request.getEvaluateWeight()) > 1) {
//				return builder.body(ResponseUtils.getResponseBody("非法，比重分配超过1"));
//			}
//		}
        template.setEvaluateWeight(request.getEvaluateWeight());
        template.setParentTemplateId(request.getParentTemplateId());
        template.setCreateTime(LocalDateTime.now());
        template.setModifyTime(LocalDateTime.now());
        template.setIsDeleted(request.getIsDeleted());
        activityEvaluateTemplateMapper.insert(template);
        return builder.body(ResponseUtils.getResponseBody(template.getId()));
    }

    @RequestMapping(path = "/findEvaluationTemplateWeight", method = RequestMethod.GET)
    @ApiOperation(value = "查询现有模板权重", notes = "查询现有模板权重")
    public ResponseEntity<JSONObject> findEvaluationTemplateWeight(@RequestParam Integer activityId, @RequestParam Short type)
            throws Exception {
        BodyBuilder builder = ResponseUtils.getBodyBuilder();
        BigDecimal num = new BigDecimal("0.0");
        ActivityEvaluateTemplateExample example = new ActivityEvaluateTemplateExample();
        example.createCriteria().andParentTemplateIdEqualTo(activityId).andIsDeletedEqualTo(type);
        List<ActivityEvaluateTemplate> list = activityEvaluateTemplateMapper.selectByExample(example);
        if (list.isEmpty()) {
            return builder.body(ResponseUtils.getResponseBody(0));
        }
        for (int i = 0; i < list.size(); i++) {
            ActivityEvaluateTemplate activityEvaluateTemplate = list.get(i);
            BigDecimal a1 = new BigDecimal(activityEvaluateTemplate.getEvaluateWeight());
            num = num.add(a1);
//			Double.valueOf(activityEvaluateTemplate.getEvaluateWeight())
        }
        return builder.body(ResponseUtils.getResponseBody(num));
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
    public String updateUserEvaluationTemplate(ActivityEvaluateTemplateRequest request)
            throws Exception {
        BodyBuilder builder = ResponseUtils.getBodyBuilder();
        ActivityEvaluateTemplate activityEvaluateTemplate = activityEvaluateTemplateMapper
                .selectByPrimaryKey(request.getId());
        if (activityEvaluateTemplate == null) {
            return "此模板不存在";
        }
        ActivityEvaluateTemplateExample example = new ActivityEvaluateTemplateExample();
        example.createCriteria().andParentTemplateIdEqualTo(activityEvaluateTemplate.getParentTemplateId()).andIsDeletedEqualTo(request.getIsDeleted());
        List<ActivityEvaluateTemplate> list = activityEvaluateTemplateMapper.selectByExample(example);
        double count = 0;
        if (Double.valueOf(request.getEvaluateWeight()) < 0.00 || Double.valueOf(request.getEvaluateWeight()) > 1.00) {
            return "true";
        }
        for (int i = 0; i < list.size(); i++) {
            ActivityEvaluateTemplate template = list.get(i);
            if (template.getId() != request.getId()) {
                count = count + Double.valueOf(template.getEvaluateWeight());
            }
            if (count + Double.valueOf(request.getEvaluateWeight()) > 1) {
                return "false";
            }
        }
        if (!StringUtils.isEmpty(request.getEvaluateContent())) {
            activityEvaluateTemplate.setEvaluateContent(request.getEvaluateContent());
        }
        if (!StringUtils.isEmpty(request.getEvaluateType())) {
            activityEvaluateTemplate.setEvaluateType(request.getEvaluateType());
        }
        if (!StringUtils.isEmpty(request.getEvaluateWeight())) {
            activityEvaluateTemplate.setEvaluateWeight(request.getEvaluateWeight());
        }
        if (!StringUtils.isEmpty(request.getRemarks())) {
            activityEvaluateTemplate.setRemarks(request.getRemarks());
        }
        activityEvaluateTemplate.setModifyTime(LocalDateTime.now());
        activityEvaluateTemplateMapper.updateByPrimaryKey(activityEvaluateTemplate);
        return "activityEvaluateTemplate.getId()";
    }

    @RequestMapping(path = "/findUserEvaluationTemplate", method = RequestMethod.GET)
    @ApiOperation(value = "查询用户评价模板", notes = "查询用户评价模板")
    public ResponseEntity<JSONObject> findUserEvaluationTemplate(
            @RequestParam(required = true) Integer activityId,
            @RequestParam(required = true) Short type,
            @RequestParam(required = false) Integer userId,
            @RequestParam(required = false) Integer electedId) throws Exception {
        BodyBuilder builder = ResponseUtils.getBodyBuilder();
        ActivityEvaluateTemplateExample example = new ActivityEvaluateTemplateExample();
        example.createCriteria().andParentTemplateIdEqualTo(activityId).andIsDeletedEqualTo(type);
        List<ActivityEvaluateTemplate> list = activityEvaluateTemplateMapper.selectByExample(example);
        List<Evaluate> userEvaluateInfo = new ArrayList<Evaluate>(list.size());
        int index = 65;
        if (userId == null && electedId == null) {
            for (int i = 0; i < list.size(); i++) {
                Evaluate evaluate = new Evaluate();
                ActivityEvaluateTemplate template = list.get(i);
                evaluate.setEvaluateTemplateId(template.getId());
                evaluate.setEvaluateType(template.getEvaluateType());
                evaluate.setRemarks(template.getRemarks());
                evaluate.setEvaluateWeight(template.getEvaluateWeight());
                evaluate.setEvaluateContent(template.getEvaluateContent());
                evaluate.setZimu((char) index);
                index++;
                userEvaluateInfo.add(evaluate);
            }
            return builder.body(ResponseUtils.getResponseBody(userEvaluateInfo));
        }
        if (userId != null) {
            ActivityVoteRecordsExample recordsExample = new ActivityVoteRecordsExample();
            recordsExample.createCriteria().andActivityIdEqualTo(activityId).andVoteTimesEqualTo((int) type)
                    .andElectedUserIdEqualTo(electedId).andUserIdEqualTo(userId);
            List<ActivityVoteRecords> activityVoteRecords = activityVoteRecordsMapper.selectByExample(recordsExample);
            for (int i = 0; i < list.size(); i++) {
                Evaluate evaluate = new Evaluate();
                evaluate.setZimu((char) index);
                index++;
                ActivityEvaluateTemplate template = list.get(i);
                ActivityUserEvaluateExample example2 = new ActivityUserEvaluateExample();
                example2.createCriteria().andUserIdEqualTo(electedId).andEvaluateTemplateIdEqualTo(template.getId());
                List<ActivityUserEvaluate> list2 = activityUserEvaluateMapper.selectByExample(example2);
                if (!StringUtils.isEmpty(template.getEvaluateContent()) && !"undefined".equals(template.getEvaluateContent())) {
                    evaluate.setTemplateContent(template.getEvaluateContent());
                }
                if (!list2.isEmpty()) {
                    if (!StringUtils.isEmpty(list2.get(0).getEvaluateContent())) {
                        evaluate.setEvaluateContent(list2.get(0).getEvaluateContent());
                    }
                }
                evaluate.setRemarks(template.getRemarks());
                evaluate.setEvaluateType(template.getEvaluateType());
                evaluate.setEvaluateTemplateId(template.getId());
                if (activityVoteRecords.isEmpty()) {
                    evaluate.setScore("0");
                } else {
                    if (!StringUtils.isEmpty(activityVoteRecords.get(i).getRemarks())) {
                        evaluate.setScore(activityVoteRecords.get(i).getRemarks());
                    }
                }
                userEvaluateInfo.add(evaluate);
            }
            return builder.body(ResponseUtils.getResponseBody(userEvaluateInfo));

        } else {
            for (int i = 0; i < list.size(); i++) {
                Evaluate evaluate = new Evaluate();
                evaluate.setZimu((char) index);
                index++;
                ActivityEvaluateTemplate template = list.get(i);
                ActivityUserEvaluateExample example2 = new ActivityUserEvaluateExample();
                example2.createCriteria().andUserIdEqualTo(electedId).andEvaluateTemplateIdEqualTo(template.getId());
                List<ActivityUserEvaluate> list2 = activityUserEvaluateMapper.selectByExample(example2);
                if (!list2.isEmpty()) {
                    if (!StringUtils.isEmpty(list2.get(0).getEvaluateContent())) {
                        evaluate.setEvaluateContent(list2.get(0).getEvaluateContent());
                    }
                }
                evaluate.setRemarks(template.getRemarks());
                evaluate.setEvaluateType(template.getEvaluateType());
                evaluate.setEvaluateTemplateId(template.getId());
                userEvaluateInfo.add(evaluate);
            }
            return builder.body(ResponseUtils.getResponseBody(userEvaluateInfo));
        }
    }


    @RequestMapping(path = "/findUserIsRecord", method = RequestMethod.GET)
    @ApiOperation(value = "查询此评委是否给此人打过分", notes = "查询此评委是否给此人打过分")
    public ResponseEntity<JSONObject> findUserIsRecord(
            @RequestParam(required = true) Integer activityId,
            @RequestParam(required = true) Short type,
            @RequestParam(required = false) Integer userId,
            @RequestParam(required = false) Integer electedId) throws Exception {
        BodyBuilder builder = ResponseUtils.getBodyBuilder();
        Boolean flag = true;
        ActivityVoteRecordsExample recordsExample = new ActivityVoteRecordsExample();
        recordsExample.createCriteria().andActivityIdEqualTo(activityId).andVoteTimesEqualTo((int) type)
                .andElectedUserIdEqualTo(electedId).andUserIdEqualTo(userId);
        List<ActivityVoteRecords> activityVoteRecords = activityVoteRecordsMapper.selectByExample(recordsExample);
        if (activityVoteRecords.isEmpty()) {
            flag = false;
        }
        return builder.body(ResponseUtils.getResponseBody(flag));
    }

    @RequestMapping(path = "/userAddEvaluation", method = RequestMethod.POST)
    @ApiOperation(value = "用户填写评价", notes = "用户填写评价")
    public ResponseEntity<JSONObject> userAddEvaluation(ActivityUserEvaluateRequest request) throws Exception {
        BodyBuilder builder = ResponseUtils.getBodyBuilder();
        ActivityUserEvaluateExample example = new ActivityUserEvaluateExample();
        for (int i = 0; i < request.getEvaluateTemplateId().length; i++) {
            example.createCriteria().andUserIdEqualTo(request.getUserId()).andEvaluateTemplateIdEqualTo(request.getEvaluateTemplateId()[i]);
            List<ActivityUserEvaluate> list = activityUserEvaluateMapper.selectByExample(example);
            example.clear();
            if (!list.isEmpty()) {
                String evaluateContent = request.getEvaluateContent().get(i);
                if (!StringUtils.isEmpty(evaluateContent)) {
                    userUpdateEvaluation(list.get(0).getId(), evaluateContent);
                } else {
                    userUpdateEvaluation(list.get(0).getId(), null);
                }
            } else {
                ActivityUserEvaluate activityUserEvaluate = new ActivityUserEvaluate();
                activityUserEvaluate.setUserId(request.getUserId());
                activityUserEvaluate.setEvaluateTemplateId(request.getEvaluateTemplateId()[i]);
                if (request.getEvaluateContent().get(i) != null) {
                    activityUserEvaluate.setEvaluateContent(request.getEvaluateContent().get(i));
                }
//					activityUserEvaluate.setEvaluateResult(request.getEvaluateResult());
                activityUserEvaluate.setCreateTime(LocalDateTime.now());
                activityUserEvaluate.setModifyTime(LocalDateTime.now());
                activityUserEvaluate.setIsDeleted((short) 0);
                activityUserEvaluateMapper.insert(activityUserEvaluate);
            }
        }
        return builder.body(ResponseUtils.getResponseBody("更新成功"));
    }

    @RequestMapping(path = "/userUpdateEvaluation", method = RequestMethod.POST)
    @ApiOperation(value = "用户更新个人评价", notes = "用户更新个人评价")
    public ResponseEntity<JSONObject> userUpdateEvaluation(@RequestParam Integer userEvaluateId, @RequestParam String evaluateContent) throws Exception {
        BodyBuilder builder = ResponseUtils.getBodyBuilder();
        ActivityUserEvaluate activityUserEvaluate = activityUserEvaluateMapper.selectByPrimaryKey(userEvaluateId);
        if (activityUserEvaluate == null) {
            return builder.body(ResponseUtils.getResponseBody("异常异常"));
        }
        if (!"null".equals(evaluateContent)) {
            activityUserEvaluate.setEvaluateContent(evaluateContent);
        }
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

    @RequestMapping(path = "/findCompany", method = RequestMethod.GET)
    @ApiOperation(value = "查询公司", notes = "查询公司")
    public ResponseEntity<JSONObject> addCompany() throws Exception {
        BodyBuilder builder = ResponseUtils.getBodyBuilder();
        return builder.body(ResponseUtils.getResponseBody(activityComponyMapper.selectByExample(null)));
    }

    @RequestMapping(path = "/findDepartmentByCompany", method = RequestMethod.GET)
    @ApiOperation(value = "根据公司编号查询部门", notes = "根据公司编号查询部门")
    public ResponseEntity<JSONObject> findDepartmentByCompany(@RequestParam(required = false) String companyCode, @RequestParam(required = false) Integer userId) throws Exception {
        BodyBuilder builder = ResponseUtils.getBodyBuilder();
        if (!StringUtils.isEmpty(companyCode)) {
            ActivityComponyExample example = new ActivityComponyExample();
            example.createCriteria().andCompanyInfoEqualTo(companyCode);
            List<ActivityCompony> list = activityComponyMapper.selectByExample(example);
            if (list.isEmpty()) {
                return builder.body(ResponseUtils.getResponseBody("您输入的公司编码不存在"));
            } else {
                ActivityCompony compony = list.get(0);
                ActivityDepartmentExample example2 = new ActivityDepartmentExample();
                example2.createCriteria().andComponyIdEqualTo(compony.getId());
                List<ActivityDepartment> list2 = activityDepartmentMapper.selectByExample(example2);
                String[] departmentName = new String[list2.size()];
                for (int i = 0; i < list2.size(); i++) {
                    ActivityDepartment department = list2.get(i);
                    departmentName[i] = department.getDepartmentName();
                }
                return builder.body(ResponseUtils.getResponseBody(departmentName));
            }
        } else {
            ActivityUserInfoExample example = new ActivityUserInfoExample();
            example.createCriteria().andUserIdEqualTo(userId);
            List<ActivityUserInfo> list = activityUserInfoMapper.selectByExample(example);
            ActivityUserInfo info = list.get(0);
            ActivityCompony compony = activityComponyMapper.selectByPrimaryKey(info.getDepartmentId());
            ActivityDepartmentExample example2 = new ActivityDepartmentExample();
            example2.createCriteria().andComponyIdEqualTo(compony.getId());
            List<ActivityDepartment> list2 = activityDepartmentMapper.selectByExample(example2);
            String[] departmentName = new String[list2.size()];
            for (int i = 0; i < list2.size(); i++) {
                ActivityDepartment department = list2.get(i);
                departmentName[i] = department.getDepartmentName();
            }
            return builder.body(ResponseUtils.getResponseBody(departmentName));
        }
    }

    @RequestMapping(path = "/intoActivity", method = RequestMethod.POST)
    @ApiOperation(value = "进入活动首页", notes = "进入活动首页")
    public ResponseEntity<JSONObject> intoActivity(@RequestParam(required = false) String code, @RequestParam Integer userId, @RequestParam(required = false) String departmentName) throws Exception {
        BodyBuilder builder = ResponseUtils.getBodyBuilder();
        boolean flag = true;
        if (!StringUtils.isEmpty(code)) {
            ActivityComponyExample activityComponyExample = new ActivityComponyExample();
            activityComponyExample.createCriteria().andCompanyInfoEqualTo(code);
            List<ActivityCompony> componies = activityComponyMapper.selectByExample(activityComponyExample);
            if (componies.isEmpty()) {
                return builder.body(ResponseUtils.getResponseBody(false));
            } else {
                return builder.body(ResponseUtils.getResponseBody(true));
            }
        }
        if (!StringUtils.isEmpty(departmentName)) {
            ActivityUserInfoExample example = new ActivityUserInfoExample();
            example.createCriteria().andUserIdEqualTo(userId);
            List<ActivityUserInfo> list = activityUserInfoMapper.selectByExample(example);
            ActivityDepartmentExample example2 = new ActivityDepartmentExample();
            example2.createCriteria().andDepartmentNameEqualTo(departmentName);
            List<ActivityDepartment> list2 = activityDepartmentMapper.selectByExample(example2);
            ActivityDepartment department = list2.get(0);
            if (list.isEmpty()) {
                ActivityUserInfo userInfo = new ActivityUserInfo();
                userInfo.setUserId(userId);
                userInfo.setDepartmentId(department.getId());
                userInfo.setCreateTime(LocalDateTime.now());
                userInfo.setModifyTime(LocalDateTime.now());
                userInfo.setIsDeleted((short) 0);
                activityUserInfoMapper.insert(userInfo);
            } else {
                ActivityUserInfo userInfo = list.get(0);
                userInfo.setDepartmentId(department.getId());
                activityUserInfoMapper.updateByPrimaryKey(userInfo);
            }
        }
        return builder.body(ResponseUtils.getResponseBody("进入成功"));
    }

    @RequestMapping(path = "/findUserIsDepartment", method = RequestMethod.GET)
    @ApiOperation(value = "查询此人是否提交过部门", notes = "查询此人是否提交过部门")
    public ResponseEntity<JSONObject> findUserIsDepartment(Integer userId) throws Exception {
        BodyBuilder builder = ResponseUtils.getBodyBuilder();
        boolean flag = true;
        ActivityUserInfoExample example = new ActivityUserInfoExample();
        example.createCriteria().andUserIdEqualTo(userId);
        List<ActivityUserInfo> list = activityUserInfoMapper.selectByExample(example);
        if (list.isEmpty()) {
            flag = false;
        }
        return builder.body(ResponseUtils.getResponseBody(flag));
    }

    @RequestMapping(path = "/findDepartmentByCompanyId", method = RequestMethod.GET)
    @ApiOperation(value = "根据公司id查询部门", notes = "根据公司id查询部门")
    public ResponseEntity<JSONObject> findDepartmentByCompanyId(Integer companyId) throws Exception {
        BodyBuilder builder = ResponseUtils.getBodyBuilder();
        ActivityDepartmentExample example = new ActivityDepartmentExample();
        example.createCriteria().andComponyIdEqualTo(companyId);
        List<ActivityDepartment> list = activityDepartmentMapper.selectByExample(example);
        return builder.body(ResponseUtils.getResponseBody(list));
    }

}
