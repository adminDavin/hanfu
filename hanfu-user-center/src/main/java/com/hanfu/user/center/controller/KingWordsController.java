package com.hanfu.user.center.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.hanfu.common.service.FileMangeService;
import com.hanfu.user.center.dao.UsersMapper;
import com.hanfu.user.center.model.Users;
import com.hanfu.user.center.model.UsersExample;
import com.hanfu.user.center.request.LoginReuqest;
import com.hanfu.user.center.request.UserInfoRequest;
import com.hanfu.user.center.response.handler.ParamInvalidException;
import com.hanfu.user.center.response.handler.ResponseUtils;
import com.hanfu.user.center.service.CommonService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/user")
public class KingWordsController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CommonService commonService;
	@Autowired
	private UsersMapper usersMapper;


	@Resource
    private RedisTemplate<String, String> redisTemplate;
	
	/**
	 * 展示所有用户
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/")
	public ResponseEntity<JSONObject> listUsers(HttpServletRequest request) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder();
        String user1 = redisTemplate.opsForValue().get("user");
		return builder.body(ResponseUtils.getResponseBody(usersMapper.selectByExample(null)));
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	@ApiOperation(value = "获取商品实体id获取物品列表", notes = "即某商品在店铺内的所有规格")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "authType", value = "鉴权方式,  1:用户登录, 2:手机号登录 ", required = true, type = "String"),
			@ApiImplicitParam(paramType = "query", name = "authKey", value = "鉴权key", required = false, type = "String"),
			@ApiImplicitParam(paramType = "query", name = "passwd", value = "密码", required = false, type = "String"),
			@ApiImplicitParam(paramType = "query", name = "token", value = "token", required = false, type = "String")
			})
	public ResponseEntity<JSONObject> login(@RequestParam String authType, @RequestParam String authKey, @RequestParam String passwd, @RequestParam String token) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		if (StringUtils.isEmpty(token)) {
//			todo UserCenterService
		} else if(! "1".equals(authType)) {
			if (StringUtils.isEmpty(authType)) {
				throw new ParamInvalidException("authType is invalid");
			}
//			todo 发送手机好吗验证
		} else {
//			todo 用户名密码验证
		}
//		todo 记得返回 token 和userId
		Map<String, String> list = null;
		Users record;
		UsersExample example = new UsersExample();
		example.createCriteria().andIdEqualTo(1);  
		return builder.body(ResponseUtils.getResponseBody(list));
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	@ApiOperation(value = "获取商品实体id获取物品列表", notes = "即某商品在店铺内的所有规格")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "authType", value = "鉴权方式", required = true, type = "String"),
			@ApiImplicitParam(paramType = "query", name = "authKey", value = "鉴权key", required = false, type = "String"),
			@ApiImplicitParam(paramType = "query", name = "passwd", value = "密码", required = false, type = "String")
			})
	public ResponseEntity<JSONObject> register(@RequestParam String authType, @RequestParam String authKey, @RequestParam String passwd) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		Map<String, String> list = null;
		Users record;
//		todo 写auth和user 记得返回 token 和userId
		UsersExample example = new UsersExample();
		example.createCriteria().andIdEqualTo(1);  
		return builder.body(ResponseUtils.getResponseBody(list));
	}

	

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ApiOperation(value = "获取商品实体id获取物品列表", notes = "即某商品在店铺内的所有规格")
	public ResponseEntity<JSONObject> update(UserInfoRequest request) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		Map<String, String> list = null;
		Users record;
//		更新user modify_time 更新到当前时间
		UsersExample example = new UsersExample();
		example.createCriteria().andIdEqualTo(1);  
		return builder.body(ResponseUtils.getResponseBody(list));
	}
	
	
	@RequestMapping("/")
	public ResponseEntity<JSONObject> list() throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		UsersExample example = new UsersExample();
		return builder.body(ResponseUtils.getResponseBody(usersMapper.selectByExample(example)));
	}
	
	@RequestMapping("/upload_avatar")
	public ResponseEntity<JSONObject> uploadAvatar(@RequestParam("file") MultipartFile file,
			String userId) throws Exception{
		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		FileMangeService fileManageService = new FileMangeService();
		FileInputStream fis = null;
		try {
			fis = (FileInputStream) file.getInputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[] fileid = fileManageService.uploadFile(fis, userId);
		return builder.body(ResponseUtils.getResponseBody(fileid));
	}
	@RequestMapping("/download_avatar")
	public ResponseEntity<JSONObject> downloadAvatar(String group_name,
			String remoteFilename) throws Exception{
		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		FileMangeService fileManageService = new FileMangeService();
		byte[] fileid = fileManageService.downloadFile(group_name, remoteFilename);
		return builder.body(ResponseUtils.getResponseBody(fileid));
	}
}
