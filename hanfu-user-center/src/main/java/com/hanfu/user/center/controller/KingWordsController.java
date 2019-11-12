package com.hanfu.user.center.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseEntity.BodyBuilder;
import com.hanfu.utils.response.handler.ResponseUtils;
import com.alibaba.fastjson.JSONObject;
import com.hanfu.common.service.FileMangeService;
import com.hanfu.user.center.dao.FileDescMapper;
import com.hanfu.user.center.dao.HfAuthMapper;
import com.hanfu.user.center.dao.HfUserMapper;
import com.hanfu.user.center.manual.dao.UserDao;
import com.hanfu.user.center.model.FileDesc;
import com.hanfu.user.center.model.HfAuth;
import com.hanfu.user.center.model.HfAuthExample;
import com.hanfu.user.center.model.HfUser;
import com.hanfu.user.center.request.UserInfoRequest;
import com.hanfu.user.center.response.handler.AuthKeyIsExistException;
import com.hanfu.user.center.response.handler.ParamInvalidException;
import com.hanfu.user.center.response.handler.UserNotExistException;
import com.hanfu.user.center.service.UserCenterService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@Api
@RequestMapping("/user")
@CrossOrigin
public class KingWordsController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	FileDescMapper fileDescMapper;
	@Autowired
	private HfUserMapper hfUserMapper;
	@Autowired
	private UserCenterService userCenterService;
	@Resource
    private RedisTemplate<String, String> redisTemplate;
	@Autowired 
	HfAuthMapper hfAuthMapper;
    @Autowired
    UserDao userDao;
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	@ApiOperation(value = "用户登录", notes = "用户登录")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "authType", value = "鉴权方式,  1:用户登录, 2:手机号登录 ", required = true, type = "String"),
			@ApiImplicitParam(paramType = "query", name = "authKey", value = "鉴权key", required = false, type = "String"),
			@ApiImplicitParam(paramType = "query", name = "passwd", value = "密码", required = false, type = "String"),
			})
	public ResponseEntity<JSONObject> login(@RequestParam(name = "authType") String authType, @RequestParam(name = "authKey") String authKey, @RequestParam(name = "passwd") String passwd) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		HfAuth hfAuth = userDao.selectList(authKey);
		Map<String , Integer> list = new HashMap<>();
		String token ="_"+UUID.randomUUID().toString().replaceAll("-", "");
		//将token存入redis
		redisTemplate.opsForValue().set(token, String.valueOf(hfAuth.getUserId()));
		if (StringUtils.isEmpty(token)) { 
			userCenterService.checkToken(token);
		}
		if(!"1".equals(authType)) {
			if(!(hfAuth.getAuthKey()).equals(authKey)) {
//				if(passwd != GetMessageCode.getCode(authKey)) {
				throw new ParamInvalidException("authType is invalid");
//				}
			}		
		}
		list.put(token, hfAuth.getUserId());
//		HashMap<String, Object> result = new HashMap<>();
//		result.put("token", "ss");
//		result.put("userId", 5);
//		result.put("userInfo", new HfUser());
		return builder.body(ResponseUtils.getResponseBody(list));
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	@ApiOperation(value = "用户注册", notes = "用户注册")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "authType", value = "鉴权方式. 2  手机号码注册", required = true, type = "String"),
			@ApiImplicitParam(paramType = "query", name = "authKey", value = "鉴权key", required = false, type = "String"),
			@ApiImplicitParam(paramType = "query", name = "passwd", value = "密码", required = false, type = "String")
			})
	public ResponseEntity<JSONObject> register(@RequestParam(name = "authType") String authType, @RequestParam(name = "authKey") String authKey, @RequestParam("passwd") String passwd) throws Exception {
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
		user.setBirthDay(LocalDateTime.now());
		//user.setAddress(IpAddress.findOne(IpAddress.getRemortIP(request))); 	
		user.setLastAuthTime(LocalDateTime.now());
		user.setCreateDate(LocalDateTime.now());
		user.setModifyDate(LocalDateTime.now());
		user.setIdDeleted((byte) 0);
		int userId = hfUserMapper.insert(user);
		HfAuth auth = new HfAuth(); 
		auth.setAuthKey(authKey);
		auth.setAuthType(authType);
		auth.setUserId(userId);
		auth.setAuthStatus((byte) 0);
		auth.setIdDeleted((byte) 0);
		auth.setEncodeType("0");
		auth.setCreateDate(LocalDateTime.now());
		auth.setModifyDate(LocalDateTime.now());
		auth.setIdDeleted((byte) 0);
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
//		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//		String localTime = df.format(request.getBirthDay());
//		LocalDateTime ldt = LocalDateTime.parse(localTime,df);
		HfUser user = hfUserMapper.selectByPrimaryKey(request.getUserId());
		if (user == null) {
			throw new UserNotExistException(String.valueOf(request.getUserId()));
		}
		if (!StringUtils.isEmpty(request.getAddress())) {
			user.setAddress(request.getAddress());
		}
		if(!StringUtils.isEmpty(request.getUsername())) {
			user.setUsername(request.getUsername());
		}
//		if(!StringUtils.isEmpty(request.getBirthDay())) {
//			user.setBirthDay(ldt);
//		}
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
		user.setModifyDate(LocalDateTime.now());
		user.setIdDeleted((byte) 0);
		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		return builder.body(ResponseUtils.getResponseBody(hfUserMapper.updateByPrimaryKeySelective(user)));
	}
	
	@RequestMapping(path = "/upload_avatar",  method = RequestMethod.POST)
	@ApiOperation(value = "上传头像", notes = "上传头像")
	public ResponseEntity<JSONObject> uploadAvatar(MultipartFile file,
			Integer userId) throws Exception{
		BodyBuilder builder = ResponseUtils.getBodyBuilder();
			FileMangeService fileMangeService = new FileMangeService();
			String arr[];
            arr = fileMangeService.uploadFile(file.getBytes(), String.valueOf(userId));
			FileDesc fileDesc = new FileDesc();
			fileDesc.setFileName(file.getName());
			fileDesc.setGroupName(arr[0]);
			fileDesc.setRemoteFilename(arr[1]);
			fileDesc.setUserId(userId);
			fileDesc.setCreateTime(LocalDateTime.now());
			fileDesc.setModifyTime(LocalDateTime.now());
			fileDesc.setIsDeleted((short) 0);
			fileDescMapper.insert(fileDesc);
			HfUser hfUser = new HfUser();
			hfUser.setFileId(fileDesc.getId());
			hfUserMapper.insert(hfUser);	
		return builder.body(ResponseUtils.getResponseBody(hfUserMapper.updateByPrimaryKeySelective(hfUser)));
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
