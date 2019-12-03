
package com.hanfu.user.center.controller;

import java.io.UnsupportedEncodingException;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.spec.InvalidParameterSpecException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Resource;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.ui.Model;
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
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.hanfu.common.service.FileMangeService;
import com.hanfu.user.center.dao.FileDescMapper;
import com.hanfu.user.center.dao.HfAuthMapper;
import com.hanfu.user.center.dao.HfUserMapper;
import com.hanfu.user.center.manual.dao.UserDao;
import com.hanfu.user.center.model.FileDesc;
import com.hanfu.user.center.model.HfAuth;
import com.hanfu.user.center.model.HfAuthExample;
import com.hanfu.user.center.model.HfUser;
import com.hanfu.user.center.model.HfUserExample;
import com.hanfu.user.center.request.UserInfoRequest;
import com.hanfu.user.center.response.handler.AuthKeyIsExistException;
import com.hanfu.user.center.response.handler.ParamInvalidException;
import com.hanfu.user.center.response.handler.UserNotExistException;
import com.hanfu.user.center.service.UserCenterService;
import com.hanfu.user.center.utils.GetMessageCode;
import com.hanfu.user.center.utils.UrlUtil;

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
		HfAuth hfAuth = userDao.selectAuthList(authKey);
		if(hfAuth == null) {
			return builder.body(ResponseUtils.getResponseBody("还未注册"));
		}
		System.out.println(redisTemplate.opsForValue().get(hfAuth.getUserId()));
		if(redisTemplate.opsForValue().get(String.valueOf(hfAuth.getUserId())) == null) {
			System.out.println("1111111111111111111111111111111111");
			String token ="_"+UUID.randomUUID().toString().replaceAll("-", "");
			redisTemplate.opsForValue().set(String.valueOf(hfAuth.getUserId()),token);
		}else {
			return builder.body(ResponseUtils.getResponseBody("1"));
		}
		if(!passwd.equals(redisTemplate.opsForValue().get(authKey))) {
			return builder.body(ResponseUtils.getResponseBody("验证码不正确"));
		}
//		Map<String , Integer> list = new HashMap<>();
		
		//将token存入redis
		
//		if (!StringUtils.isEmpty(redisTemplate.opsForValue().get(String.valueOf(hfAuth.getUserId())))) { 
//			userCenterService.checkToken(token);
//		}
//		if(!"1".equals(authType)) {
//			if(!(hfAuth.getAuthKey()).equals(authKey)) {
//				if(passwd != redisTemplate.opsForValue().get(authKey)) {
//					throw new ParamInvalidException("authType is invalid");
//				}
//			}		
//		}
//		list.put(token, hfAuth.getUserId());
		return builder.body(ResponseUtils.getResponseBody("成功"));
	}
	@RequestMapping(path = "/code",  method = RequestMethod.GET)
	@ApiOperation(value = "发送验证码", notes = "发送验证码")
	public ResponseEntity<JSONObject> code(String phone) throws Exception{
		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		Integer code = GetMessageCode.sendSms(phone);
		System.out.println(code);
		redisTemplate.opsForValue().set(phone, String.valueOf(code));
		return builder.body(ResponseUtils.getResponseBody(code));
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
		System.out.println(redisTemplate.opsForValue().get(authKey));
		if(!passwd.equals(redisTemplate.opsForValue().get(authKey))) {
			throw new ParamInvalidException("authKey is invalid");
		}
		HfUser user = new HfUser();
		user.setSourceType(authType);
		user.setPhone(authKey);
		user.setUsername(authKey);
		user.setUserStatus("0".getBytes()[0]);
		user.setBirthDay(LocalDateTime.now());
		user.setSex((byte)1);
		//user.setAddress(IpAddress.findOne(IpAddress.getRemortIP(request)));
		user.setLastAuthTime(LocalDateTime.now());
		user.setCreateDate(LocalDateTime.now());
		user.setModifyDate(LocalDateTime.now());
		user.setIdDeleted((byte) 0);
		hfUserMapper.insert(user);
		HfAuth auth = new HfAuth(); 
		auth.setAuthKey(authKey);
		auth.setAuthType(authType);
		auth.setUserId(user.getId());
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
		map.put(token, String.valueOf(user.getId()));
		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		return builder.body(ResponseUtils.getResponseBody(map));
	}



	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ApiOperation(value = "更新用户信息", notes = "更新用户信息")
	public ResponseEntity<JSONObject> update(UserInfoRequest request) throws Exception {
//		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//		LocalDateTime ldt = LocalDateTime.parse("request.getBirthDay()",df);
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
			user.setNickName(request.getNickName());
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
	@RequestMapping(path = "/userList",  method = RequestMethod.GET)
	@ApiOperation(value = "用户列表", notes = "用户列表")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "userId", value = "用户Id", required = false, type = "Integer")
	})
	public ResponseEntity<JSONObject> userList(Integer userId) throws Exception{
		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		if(!StringUtils.isEmpty(userId)) {
			HfUserExample hfUserExample = new HfUserExample();
			hfUserExample.createCriteria().andIdNotEqualTo(userId);
			return builder.body(ResponseUtils.getResponseBody(hfUserMapper.selectByPrimaryKey(userId)));
		}	
		return builder.body(ResponseUtils.getResponseBody(userDao.selectUserList()));
	}
	
	
	@RequestMapping(path = "/wxLogin",  method = RequestMethod.GET)
	@ApiOperation(value = "微信登录", notes = "微信登录")
	public ResponseEntity<JSONObject> wxLogin(Model model,                                  
			@RequestParam(value = "code",required = false) String code,                                  
			@RequestParam(value = "rawData",required = false) String rawData,                                  
			@RequestParam(value = "signature",required = false) String signature,                                  
			@RequestParam(value = "encrypteData",required = false) String encrypteData,                                  
			@RequestParam(value = "iv",required = false) String iv
			) throws Exception{
		logger.info( "Start get SessionKey" );
		Map<String,Object> map = new HashMap<String, Object>(  );    
		//JSONObject rawDataJson = JSON.parseObject( rawData );     
		JSONObject SessionKeyOpenId = getSessionKeyOrOpenId( code );    	
		Integer openid = (Integer) SessionKeyOpenId.get("openid");   
		String sessionKey = (String) SessionKeyOpenId.get( "session_key" );    
		//uuid生成唯一key    
		System.out.println("openid="+openid+",session_key="+sessionKey);
		String skey = UUID.randomUUID().toString();    
		//根据openid查询skey是否存在   
		String skey_redis =redisTemplate.opsForValue().get( openid ); 
		if(StringUtils.isEmpty(skey_redis)){    
			//存在 删除 skey 重新生成skey 将skey返回    
			redisTemplate.delete( skey_redis );   
		}      
		//  缓存一份新的       
		JSONObject sessionObj = new JSONObject();      
		sessionObj.put( "openId",openid );     
		sessionObj.put( "sessionKey",sessionKey );  
		redisTemplate.opsForValue().set( skey,sessionObj.toJSONString() );
		redisTemplate.opsForValue().set( openid.toString(),skey );     
		//把新的sessionKey和oppenid返回给小程序      
		map.put( "skey",skey );  
		map.put( "result","0" );  
		JSONObject userInfo = getUserInfo( encrypteData, sessionKey, iv ); 
		map.put( "userInfo",userInfo );  
		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		return builder.body(ResponseUtils.getResponseBody(map));
	}

	private JSONObject getUserInfo(String encrypteData, String sessionKey, String iv) {
		// 被加密的数据
		byte[] dataByte = Base64.getDecoder().decode(encrypteData);
		// 加密秘钥
		byte[] keyByte = Base64.getDecoder().decode(sessionKey);
		// 偏移量
		byte[] ivByte = Base64.getDecoder().decode(iv);
		try {
			// 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
			int base = 16;
			if (keyByte.length % base != 0) {
				int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
				byte[] temp = new byte[groups * base];
				Arrays.fill(temp, (byte) 0);
				System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
				keyByte = temp;
			}
			// 初始化
			Security.addProvider(new BouncyCastleProvider());
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding","BC");
			SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
			AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
			parameters.init(new IvParameterSpec(ivByte));
			cipher.init( Cipher.DECRYPT_MODE, spec, parameters);// 初始化
			byte[] resultByte = cipher.doFinal(dataByte);
			if (null != resultByte && resultByte.length > 0) {
				String result = new String(resultByte, "UTF-8");
				return JSON.parseObject(result);
			}
		} catch (NoSuchAlgorithmException e) {
			logger.error(e.getMessage(), e);
		} catch (NoSuchPaddingException e) {
			logger.error(e.getMessage(), e);
		} catch (InvalidParameterSpecException e) {
			logger.error(e.getMessage(), e);
		} catch (IllegalBlockSizeException e) {
			logger.error(e.getMessage(), e);
		} catch (BadPaddingException e) {
			logger.error(e.getMessage(), e);
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		} catch (InvalidKeyException e) {
			logger.error(e.getMessage(), e);
		} catch (InvalidAlgorithmParameterException e) {
			logger.error(e.getMessage(), e);
		} catch (NoSuchProviderException e) {
			logger.error(e.getMessage(), e);
		}
		return null;

	}

	private JSONObject getSessionKeyOrOpenId(String code) {
		//微信端登录code
		String wxCode = code;
		String requestUrl = "https://api.weixin.qq.com/sns/jscode2session";
		Map<String,String> requestUrlParam = new HashMap<String, String>(  );
		requestUrlParam.put( "appid","wx16159fcc93b0400c" );//小程序appId
		requestUrlParam.put( "secret","1403f2e207dfa2f1f348910626f5aa42" );
		requestUrlParam.put( "js_code",wxCode );//小程序端返回的code
		requestUrlParam.put( "grant_type","authorization_code" );//默认参数
		//发送post请求读取调用微信接口获取openid用户唯一标识
		JSONObject jsonObject = JSON.parseObject(UrlUtil.sendPost( requestUrl,requestUrlParam ));
		return jsonObject;
	}
}
