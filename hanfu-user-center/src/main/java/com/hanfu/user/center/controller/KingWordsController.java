package com.hanfu.user.center.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.util.StringUtils; 
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hanfu.common.service.FileMangeService;
import com.hanfu.user.center.dao.HfAuthMapper;
import com.hanfu.user.center.dao.HfUserMapper;
import com.hanfu.user.center.model.HfAuth;
import com.hanfu.user.center.model.HfAuthExample;
import com.hanfu.user.center.model.HfUser;
import com.hanfu.user.center.model.HfUserExample;
import com.hanfu.user.center.request.UserInfoRequest;
import com.hanfu.user.center.response.handler.AuthKeyIsExistException;
import com.hanfu.user.center.response.handler.ParamInvalidException;
import com.hanfu.user.center.response.handler.ResponseUtils;
import com.hanfu.user.center.response.handler.UserNotExistException;
import com.hanfu.user.center.service.UserCenterService;
import com.hanfu.user.center.utils.GetMessageCode;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@Api 
@RequestMapping("/user")
public class KingWordsController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private HfUserMapper hfUserMapper;
	@Autowired
	private UserCenterService userCenterService;
	@Resource
    private RedisTemplate<String, String> redisTemplate;
	@Autowired HfAuthMapper hfAuthMapper;

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
		Map<String,Integer> list = userCenterService.login();
		String json = JSON.toJSONString(list);//map转String
		JSONObject jsonObject = JSON.parseObject(json);//String转json
		if (StringUtils.isEmpty(token)) {
			userCenterService.checkToken(token);
		} else if(! "1".equals(authType)) {
			if (StringUtils.isEmpty(authType)) {
				throw new ParamInvalidException("authType is invalid");
			}
			GetMessageCode.getCode(authKey);
		}
//		todo 记得返回 token 和userId 
		return builder.body(ResponseUtils.getResponseBody(list));
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	@ApiOperation(value = "用户注册", notes = "用户注册")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "authType", value = "鉴权方式. 2  手机号码注册", required = true, type = "String"),
			@ApiImplicitParam(paramType = "query", name = "authKey", value = "鉴权key", required = false, type = "String"),
			@ApiImplicitParam(paramType = "query", name = "passwd", value = "密码", required = false, type = "String")
			})
	public ResponseEntity<JSONObject> register(@RequestParam String authType, @RequestParam String authKey, @RequestParam String passwd) throws Exception {
		HfAuthExample example = new HfAuthExample();
		example.createCriteria().andAuthKeyEqualTo(authKey);
		long authCount = hfAuthMapper.countByExample(example);
		if (authCount > 0) { 
			throw new AuthKeyIsExistException(authKey);
		}
		HfUser user = new HfUser();
		user.setSourceType(authType);
		user.setPhone(authKey);
		user.setUsername(UUID.randomUUID().toString());
		user.setUserStatus("0".getBytes()[0]);
		int userId = hfUserMapper.insert(user);
		HfAuth auth = new HfAuth(); 
		auth.setAuthKey(authKey);
		auth.setAuthType(authType);
		auth.setUserId(userId);
		hfAuthMapper.insert(auth);
		UUID uuid = UUID.randomUUID();
		String token ="_"+uuid.toString().replaceAll("-", "");
		Map<String, String> map = new HashMap<String, String>();
		map.put(token, String.valueOf(userId));
		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		return builder.body(ResponseUtils.getResponseBody(map));
	}

	

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ApiOperation(value = "更新用户信息", notes = "更新用户信息")
	public ResponseEntity<JSONObject> update(UserInfoRequest request) throws Exception {
		HfUser user = hfUserMapper.selectByPrimaryKey(request.getUserId());
		if (user == null) {
			throw new UserNotExistException(String.valueOf(request.getUserId()));
		}
		if (!StringUtils.isEmpty(request.getAddress())) {
			user.setAddress(request.getAddress());
		}
		if(!StringUtils.isEmpty(request.getBirthDay())) {
			user.setBirthDay(request.getBirthDay());
		}
		if(!StringUtils.isEmpty(request.getEmail())) {
			user.setEmail(request.getEmail());
		}
		if(!StringUtils.isEmpty(request.getNickName())) {
			user.setEmail(request.getNickName());
		}
		if(!StringUtils.isEmpty(request.getRealName())) {
			user.setRealName(request.getRealName());
		}
		if(!StringUtils.isEmpty(request.getRegion())) {
			user.setRegion(request.getRegion());
		}
		if(!StringUtils.isEmpty(request.getSex())) {
			user.setSex(request.getSex());
		}
		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		return builder.body(ResponseUtils.getResponseBody(hfUserMapper.updateByPrimaryKeySelective(user)));
	}
	
	@RequestMapping(path = "/upload_avatar",  method = RequestMethod.POST)
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
	@RequestMapping(path = "/download_avatar",  method = RequestMethod.GET)
	public ResponseEntity<JSONObject> downloadAvatar(String group_name,
			String remoteFilename) throws Exception{
		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		FileMangeService fileManageService = new FileMangeService();
		byte[] fileid = fileManageService.downloadFile(group_name, remoteFilename);
		return builder.body(ResponseUtils.getResponseBody(fileid));
	}
}
