package com.hanfu.user.center.controller;

import com.alibaba.fastjson.JSONObject;
import com.hanfu.user.center.Jurisdiction.dao.DepartmentMapper;
import com.hanfu.user.center.Jurisdiction.dao.DepartmentPersonnelMapper;
import com.hanfu.user.center.Jurisdiction.model.Department;
import com.hanfu.user.center.Jurisdiction.model.DepartmentPersonnel;
import com.hanfu.user.center.dao.AccountMapper;
import com.hanfu.user.center.model.Account;
import com.hanfu.user.center.model.AccountExample;
import com.hanfu.user.center.model.HfCoupon;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@Api
@RequestMapping("/jurisdictionMapper")
@CrossOrigin
public class JurisdictionController {
    @Autowired
    private DepartmentPersonnelMapper departmentPersonnelMapper;
    @Autowired
    private DepartmentMapper departmentMapper;
    @Autowired
    private AccountMapper accountMapper;
    @ApiOperation(value = "",notes = "")
    @RequestMapping(value = "/addDepartment",method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "DepartmentName", value = "店铺名称", required = false, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "accountId", value = "账号名称", required = false, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "userId", value = "店铺名称", required = false, type = "Integer")
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
        if (request.getServletContext().getAttribute("getServletContextType").equals("boss")){
            System.out.println("request.getServletContext().getAttribute得到全局数据："+request.getServletContext().getAttribute("getServletContext"));
            if (request.getServletContext().getAttribute("getServletContext")!=null){
                accountExample.createCriteria().andAccountTypeEqualTo("boss").andMerchantIdEqualTo((Integer) request.getServletContext().getAttribute("getServletContext")).andIsDeletedEqualTo(0);
                account = accountMapper.selectByExample(accountExample);
            }
        }else if (request.getServletContext().getAttribute("getServletContextType").equals("stone")){
            System.out.println("request.getServletContext().getAttribute得到全局数据："+request.getServletContext().getAttribute("getServletContext"));
            if (request.getServletContext().getAttribute("getServletContext")!=null){
                accountExample.createCriteria().andAccountTypeEqualTo("stone").andMerchantIdEqualTo((Integer) request.getServletContext().getAttribute("getServletContext")).andIsDeletedEqualTo(0);
                account = accountMapper.selectByExample(accountExample);
            }
        } else {
            accountExample.createCriteria().andIsDeletedEqualTo(0);
            account = accountMapper.selectByExample(accountExample);
        }

        return builder.body(ResponseUtils.getResponseBody(account));
    }
}
