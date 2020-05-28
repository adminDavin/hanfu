package com.hanfu.user.center.controller;

import com.alibaba.fastjson.JSONObject;
import com.hanfu.user.center.Jurisdiction.dao.*;
import com.hanfu.user.center.Jurisdiction.model.*;
import com.hanfu.user.center.dao.AccountMapper;
import com.hanfu.user.center.model.Account;
import com.hanfu.user.center.model.AccountExample;
import com.hanfu.user.center.model.HfCoupon;
import com.hanfu.user.center.model.Role;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@Api
@RequestMapping("/jurisdiction")
@CrossOrigin
public class JurisdictionController {
    @Autowired
    private DepartmentPersonnelMapper departmentPersonnelMapper;
    @Autowired
    private DepartmentMapper departmentMapper;
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private RolesMapper rolesMapper;
    @Autowired
    private JurisdictionMapper jurisdictionMapper;
    @Autowired
    private RoleJurisdictionMapper roleJurisdictionMapper;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @ApiOperation(value = "",notes = "")
    @RequestMapping(value = "/addDepartment",method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "DepartmentName", value = "部门名称", required = false, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "accountId", value = "账号", required = false, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户", required = false, type = "Integer")
    })
    @Transactional
    public ResponseEntity<JSONObject> addDepartment(String DepartmentName, Integer accountId, HttpServletRequest request,Integer userId) throws JSONException {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
        Department department = new Department();
        department.setCreateDate(LocalDateTime.now());
        department.setModifyDate(LocalDateTime.now());
        department.setIsDeleted(0);
        if (request.getServletContext().getAttribute("getServletContextType").equals("boss")){
            System.out.println("request.getServletContext().getAttribute得到全局数据："+request.getServletContext().getAttribute("getServletContext"));
            if (request.getServletContext().getAttribute("getServletContext")!=null){
                department.setMerchantId((Integer) request.getServletContext().getAttribute("getServletContext"));
                department.setDepartmentType("boss");
            }
        }
        department.setAccountId(accountId);
        department.setLastModifier(String.valueOf(userId));
        department.setDepartmentName(DepartmentName);
        departmentMapper.insertSelective(department);
        return builder.body(ResponseUtils.getResponseBody(0));
    }
    @ApiOperation(value = "账号查询",notes = "账号查询")
    @RequestMapping(value = "/selectAccount",method = RequestMethod.GET)
    public ResponseEntity<JSONObject> selectAccount(HttpServletRequest request) throws JSONException {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
        AccountExample accountExample = new AccountExample();
        List<Account> account = new ArrayList<>();
        if (request.getServletContext().getAttribute("getServletContextType")!=null&&request.getServletContext().getAttribute("getServletContextType").equals("boss")){
            System.out.println("request.getServletContext().getAttribute得到全局数据："+request.getServletContext().getAttribute("getServletContext"));
            if (request.getServletContext().getAttribute("getServletContext")!=null){
                accountExample.createCriteria().andAccountTypeEqualTo("boss").andMerchantIdEqualTo((Integer) request.getServletContext().getAttribute("getServletContext")).andIsDeletedEqualTo(0);
                account = accountMapper.selectByExample(accountExample);
            }
        }else if (request.getServletContext().getAttribute("getServletContextType")!=null&&request.getServletContext().getAttribute("getServletContextType").equals("stone")){
            System.out.println("request.getServletContext().getAttribute得到全局数据："+request.getServletContext().getAttribute("getServletContext"));
            if (request.getServletContext().getAttribute("getServletContext")!=null){
                accountExample.createCriteria().andAccountTypeEqualTo("stone").andMerchantIdEqualTo((Integer) request.getServletContext().getAttribute("getServletContext")).andIsDeletedEqualTo(0);
                account = accountMapper.selectByExample(accountExample);
            }
        } else {
            accountExample.createCriteria().andIsDeletedEqualTo(0);
            account = accountMapper.selectByExample(accountExample);
        }
        account.forEach(account1 -> {
            if (redisTemplate.opsForValue().get(String.valueOf(account1.getUserId()) + account1.getAccountType() + "token")!=null){
                System.out.println(account1.getAccountCode()+"在线，id:"+account1.getId());
                account1.setIsDeleted(2);
            }
        });
        return builder.body(ResponseUtils.getResponseBody(account));
    }

    @ApiOperation(value = "添加角色",notes = "添加角色")
    @RequestMapping(value = "/addRole",method = RequestMethod.GET)
    public ResponseEntity<JSONObject> addRole(HttpServletRequest request,String roleName,Integer userId) throws JSONException {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
        Roles roles = new Roles();
        if (request.getServletContext().getAttribute("getServletContextType")!=null&&request.getServletContext().getAttribute("getServletContextType").equals("boss")){
            System.out.println("request.getServletContext().getAttribute得到全局数据："+request.getServletContext().getAttribute("getServletContext"));
            if (request.getServletContext().getAttribute("getServletContext")!=null){
                roles.setRoleType("boss");
                roles.setMachId((Integer) request.getServletContext().getAttribute("getServletContext"));
            }
        }else if (request.getServletContext().getAttribute("getServletContextType")!=null&&request.getServletContext().getAttribute("getServletContextType").equals("stone")){
            System.out.println("request.getServletContext().getAttribute得到全局数据："+request.getServletContext().getAttribute("getServletContext"));
            if (request.getServletContext().getAttribute("getServletContext")!=null){
                roles.setRoleType("boss");
                roles.setMachId((Integer) request.getServletContext().getAttribute("getServletContext"));
            }
        }
        roles.setRoleName(roleName);
        roles.setCreateDate(LocalDateTime.now());
        roles.setModifyDate(LocalDateTime.now());
        roles.setLastModifier(String.valueOf(userId));
        return builder.body(ResponseUtils.getResponseBody(0));
    }

    @ApiOperation(value = "角色查询",notes = "角色查询")
    @RequestMapping(value = "/selectRole",method = RequestMethod.GET)
    public ResponseEntity<JSONObject> selectRole(HttpServletRequest request) throws JSONException {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
        RolesExample rolesExample = new RolesExample();

        if (request.getServletContext().getAttribute("getServletContextType")!=null&&request.getServletContext().getAttribute("getServletContextType").equals("boss")){
            System.out.println("request.getServletContext().getAttribute得到全局数据："+request.getServletContext().getAttribute("getServletContext"));
            if (request.getServletContext().getAttribute("getServletContext")!=null){
                rolesExample.createCriteria().andRoleTypeEqualTo("boss").andIsDeletedEqualTo(0).andMachIdEqualTo((Integer) request.getServletContext().getAttribute("getServletContext"));
            }
        }else if (request.getServletContext().getAttribute("getServletContextType")!=null&&request.getServletContext().getAttribute("getServletContextType").equals("stone")){
            System.out.println("request.getServletContext().getAttribute得到全局数据："+request.getServletContext().getAttribute("getServletContext"));
            if (request.getServletContext().getAttribute("getServletContext")!=null){
                rolesExample.createCriteria().andRoleTypeEqualTo("stone").andIsDeletedEqualTo(0).andMachIdEqualTo((Integer) request.getServletContext().getAttribute("getServletContext"));
            }
        } else {
            rolesExample.createCriteria().andIsDeletedEqualTo(0);
        }
        List<Roles> roles= rolesMapper.selectByExample(rolesExample);
        return builder.body(ResponseUtils.getResponseBody(roles));
    }

    @ApiOperation(value = "权限查询",notes = "权限查询")
    @RequestMapping(value = "/selectJurisdiction",method = RequestMethod.GET)
    public ResponseEntity<JSONObject> selectJurisdiction() throws JSONException {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
        JurisdictionExample jurisdictionExample = new JurisdictionExample();
        jurisdictionExample.createCriteria().andIsDeletedEqualTo((short) 0);
        return builder.body(ResponseUtils.getResponseBody(jurisdictionMapper.selectByExample(jurisdictionExample)));
    }
    @ApiOperation(value = "下线",notes = "下线")
    @RequestMapping(value = "/deleteAccount",method = RequestMethod.GET)
    public ResponseEntity<JSONObject> delete(Integer id,String type) throws JSONException {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
//        Account account= accountMapper.selectByPrimaryKey(id);
        		if (null != id&& null != type){
			redisTemplate.delete(String.valueOf(id) + "token");
			System.out.println(redisTemplate.opsForValue().get(String.valueOf(id) + "token"));
//			redisTemplate.delete(String.valueOf(account.getUserId()) + type + String.valueOf(BSid)+ "token");
		}
        return builder.body(ResponseUtils.getResponseBody(0));
    }
    @ApiOperation(value = "角色添加权限",notes = "角色添加权限")
    @RequestMapping(value = "/roleAddJurisdiction",method = RequestMethod.GET)
    public ResponseEntity<JSONObject> roleAddJurisdiction(Integer roleId,Integer[] JurisdictionIds) throws JSONException {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
        for (Integer jurId:JurisdictionIds){
            RoleJurisdiction roleJurisdiction = new RoleJurisdiction();
            roleJurisdiction.setCreateTime(LocalDateTime.now());
            roleJurisdiction.setModifyTime(LocalDateTime.now());
            roleJurisdiction.setIsDeleted((short) 0);
            roleJurisdiction.setRoleId(roleId);
            roleJurisdiction.setJurisdictionId(jurId);
            roleJurisdictionMapper.insertSelective(roleJurisdiction);
        }
        return builder.body(ResponseUtils.getResponseBody(0));
    }
}
