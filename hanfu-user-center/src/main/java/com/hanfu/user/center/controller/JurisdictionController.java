//package com.hanfu.user.center.controller;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.github.pagehelper.PageHelper;
//import com.github.pagehelper.PageInfo;
//import com.hanfu.common.service.FileMangeService;
//import com.hanfu.user.center.dao.AuthorizationMapper;
//import com.hanfu.user.center.dao.FileDescMapper;
//import com.hanfu.user.center.dao.HfAuthMapper;
//import com.hanfu.user.center.dao.HfUserMapper;
//import com.hanfu.user.center.manual.dao.UserDao;
//import com.hanfu.user.center.manual.model.ActivityUserInfo;
//import com.hanfu.user.center.manual.model.UserQuery;
//import com.hanfu.user.center.manual.model.test;
//import com.hanfu.user.center.model.*;
//import com.hanfu.user.center.request.EmployeeInfoRequest;
//import com.hanfu.user.center.request.UserInfoRequest;
//import com.hanfu.user.center.response.handler.AuthKeyIsExistException;
//import com.hanfu.user.center.response.handler.ParamInvalidException;
//import com.hanfu.user.center.response.handler.UserNotExistException;
//import com.hanfu.user.center.utils.GetMessageCode;
//import com.hanfu.utils.response.handler.ResponseEntity;
//import com.hanfu.utils.response.handler.ResponseEntity.BodyBuilder;
//import com.hanfu.utils.response.handler.ResponseUtils;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiImplicitParam;
//import io.swagger.annotations.ApiImplicitParams;
//import io.swagger.annotations.ApiOperation;
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.util.EntityUtils;
//import org.bouncycastle.jce.provider.BouncyCastleProvider;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.ui.Model;
//import org.springframework.util.StringUtils;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.annotation.Resource;
//import javax.crypto.BadPaddingException;
//import javax.crypto.Cipher;
//import javax.crypto.IllegalBlockSizeException;
//import javax.crypto.NoSuchPaddingException;
//import javax.crypto.spec.IvParameterSpec;
//import javax.crypto.spec.SecretKeySpec;
//import java.io.IOException;
//import java.io.UnsupportedEncodingException;
//import java.security.*;
//import java.security.spec.InvalidParameterSpecException;
//import java.time.LocalDateTime;
//import java.util.*;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
////import com.hanfu.user.center.service.UserCenterService;
//
//@RestController
//@Api
//@RequestMapping("/jurisdiction")
//@CrossOrigin
//public class JurisdictionController {
//    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
//
//    @Autowired
//    private AuthorizationMapper authorizationMapper;
//    @Autowired
//    private HfAuthMapper hfAuthMapper;
//    @Autowired
//    private FileDescMapper fileDescMapper;
//    @RequestMapping(value = "/login", method = RequestMethod.GET)
//    @ApiOperation(value = "用户登录", notes = "用户登录")
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "query", name = "authType", value = "鉴权方式,  1:用户登录, 2:手机号登录 ", required = true, type = "String"),
//            @ApiImplicitParam(paramType = "query", name = "authKey", value = "鉴权key", required = false, type = "String"),
//            @ApiImplicitParam(paramType = "query", name = "passwd", value = "密码", required = false, type = "String"),
//    })
//    public ResponseEntity<JSONObject> login(@RequestParam(name = "authType") String authType, @RequestParam(name = "authKey") String authKey, @RequestParam(name = "passwd") Integer passwd) throws Exception {
//        BodyBuilder builder = ResponseUtils.getBodyBuilder();
//        
//        return builder.body(ResponseUtils.getResponseBody("成功"));
//    }
    
//    @RequestMapping(value = "/addAdministrator", method = RequestMethod.POST)
//    @ApiOperation(value = "添加管理员", notes = "添加管理员")
//    public ResponseEntity<JSONObject> login(EmployeeInfoRequest request,MultipartFile fileInfo) throws Exception {
//        BodyBuilder builder = ResponseUtils.getBodyBuilder();
//        Authorization authorization = new Authorization();
//        if(fileInfo != null) {
//        	updateUserAvatar(fileInfo,request.getEmployeeCode());
//        }
//        if(StringUtils.isEmpty(request.getEmployeeDepartment())) {
//        	 authorization.setEmployeeDepartment(request.getEmployeeDepartment());
//        }
//        if(StringUtils.isEmpty(request.getEmployeeEmail())) {
//        	authorization.setEmployeeEmail(request.getEmployeeEmail());
//        }
//        if(StringUtils.isEmpty(request.getEmployeeName())) {
//        	authorization.setEmployeeName(request.getEmployeeName());
//        }
//        if(StringUtils.isEmpty(request.getEmployeeEmail())) {
//        	authorization.setEmployeeEmail(request.getEmployeeEmail());
//        }
//        if(StringUtils.isEmpty(request.getEmployeeSex())) {
//        	authorization.setEmployeeSex(request.getEmployeeSex());
//        }
//        if(StringUtils.isEmpty(request.getEmployeeSite())) {
//        	authorization.setEmployeeSite(request.getEmployeeSite());
//        }
//        if(StringUtils.isEmpty(request.getIdCard())) {
//        	authorization.setIdCard(request.getIdCard());
//        }
//        if(StringUtils.isEmpty(request.getPhone())) {
//        	 authorization.setPhone(request.getPhone());
//        }
//        if(StringUtils.isEmpty(request.getRealName())) {
//        	authorization.setRealName(request.getRealName());
//        }
//        if(StringUtils.isEmpty(request.getPosition())) {
//        	authorization.setPosition(request.getPosition());
//        }
//        if(StringUtils.isEmpty(request.getRemark())) {
//        	authorization.setRemark(request.getRemark());
//        }
//        if(StringUtils.isEmpty(request.getState())) {
//        	authorization.setState(request.getState());
//        }
//        authorization.setEmployeeCode(request.getEmployeeCode());
//        authorization.setIdDeleted((byte) 0);
//        authorization.setFileId(updateUserAvatar(fileInfo,request.getEmployeeCode()));
//        authorization.setCreateDate(LocalDateTime.now());
//        authorization.setModifyDate(LocalDateTime.now());
//        return builder.body(ResponseUtils.getResponseBody(authorizationMapper.insert(authorization)));
//    }
//    
//    @RequestMapping(value = "/findAdministratorIsExists", method = RequestMethod.POST)
//    @ApiOperation(value = "查询是否添加过此管理员", notes = "查询是否添加过此管理员")
//    public ResponseEntity<JSONObject> findAdministratorIsExists(@RequestParam(required = true) String employeeCode) throws Exception {
//        BodyBuilder builder = ResponseUtils.getBodyBuilder();
//        boolean flag = true;
//        AuthorizationExample example = new AuthorizationExample();
//        example.createCriteria().andEmployeeCodeEqualTo(employeeCode);
//        List<Authorization> list = authorizationMapper.selectByExample(example);
//        if(!list.isEmpty()) {
//        	flag = false;
//        	return builder.body(ResponseUtils.getResponseBody(flag));
//        }
//        return builder.body(ResponseUtils.getResponseBody(true));
//    }
//    
//    @RequestMapping(value = "/updateUserAvatar", method = RequestMethod.POST)
//    @ApiOperation(value = "更新用户头像", notes = "更新用户头像")
//    public Integer updateUserAvatar(MultipartFile fileInfo, @RequestParam String employeeCode) throws Exception {
//        Integer fileId = null;
//        FileMangeService fileMangeService = new FileMangeService();
//        String arr[];
//        arr = fileMangeService.uploadFile(fileInfo.getBytes(),"-1");
//        FileDescExample example = new FileDescExample();
//        example.createCriteria().andFileNameEqualTo(employeeCode);
//        List<FileDesc> list = fileDescMapper.selectByExample(example);
//        if (list.isEmpty()) {
//            FileDesc fileDesc = new FileDesc();
//            fileDesc.setFileName(employeeCode);
//            fileDesc.setGroupName(arr[0]);
//            fileDesc.setRemoteFilename(arr[1]);
//            fileDesc.setUserId(-1);
//            fileDesc.setCreateTime(LocalDateTime.now());
//            fileDesc.setModifyTime(LocalDateTime.now());
//            fileDesc.setIsDeleted((short) 0);
//            fileDescMapper.insert(fileDesc);
//            fileId = fileDesc.getId();
//        } else {
//            FileDesc fileDesc = list.get(0);
//			fileMangeService.deleteFile(fileDesc.getGroupName(),fileDesc.getRemoteFilename() );
//            fileDesc.setGroupName(arr[0]);
//            fileDesc.setRemoteFilename(arr[1]);
//            fileDesc.setModifyTime(LocalDateTime.now());
//            fileDescMapper.updateByPrimaryKey(fileDesc);
//            fileId = fileDesc.getId();
//        }
//        return fileId;
//    }
//}