package com.hanfu.user.center.controller;

import com.alibaba.fastjson.JSONObject;
import com.hanfu.user.center.Jurisdiction.dao.DepartmentMapper;
import com.hanfu.user.center.Jurisdiction.dao.DepartmentPersonnelMapper;
import com.hanfu.user.center.Jurisdiction.model.Department;
import com.hanfu.user.center.Jurisdiction.model.DepartmentPersonnel;
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
import java.util.List;

@RestController
@Api
@RequestMapping("/jurisdictionMapper")
@CrossOrigin
public class JurisdictionMapper {
    @Autowired
    private DepartmentPersonnelMapper departmentPersonnelMapper;
    @Autowired
    private DepartmentMapper departmentMapper;
    @ApiOperation(value = "",notes = "")
    @RequestMapping(value = "/addDepartment",method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "优惠券id", required = false, type = "Integer")
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
        departmentMapper.insertSelective(department);
        return builder.body(ResponseUtils.getResponseBody(0));
    }
}
