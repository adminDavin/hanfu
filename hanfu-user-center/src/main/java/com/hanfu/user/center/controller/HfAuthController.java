package com.hanfu.user.center.controller;

import java.awt.image.RenderedImage;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.security.auth.message.callback.PrivateKeyCallback;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.github.pagehelper.Page;
import com.hanfu.common.service.FileMangeService;
import com.hanfu.user.center.Jurisdiction.dao.RolesMapper;
import com.hanfu.user.center.Jurisdiction.model.Roles;
import com.hanfu.user.center.Jurisdiction.model.RolesExample;
import com.hanfu.user.center.dao.*;
import com.hanfu.user.center.manual.model.*;
import com.hanfu.user.center.model.*;
import com.hanfu.user.center.model.HfUserMember;
import com.hanfu.user.center.utils.CodeUtil;
import com.hanfu.user.center.utils.Decrypt;
import com.hanfu.user.center.utils.Encrypt;
import io.swagger.models.auth.In;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.curator.shaded.com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import com.alibaba.fastjson.JSONObject;
import com.cedarsoftware.util.io.JsonObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hanfu.inner.model.product.center.HfOrders;
import com.hanfu.user.center.manual.dao.HfBossInfoDao;
import com.hanfu.user.center.manual.dao.UserDao;
import com.hanfu.user.center.request.UserInfoRequest;
import com.hanfu.user.center.response.handler.UserNotExistException;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseUtils;
import com.hanfu.utils.response.handler.ResponseEntity.BodyBuilder;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@RestController
@Api
@RequestMapping("/hf-auth")
@CrossOrigin
public class HfAuthController {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private FileDescMapper fileDescMapper;

	@Autowired
	private HfUserMapper hfUserMapper;

	@Resource
	private RedisTemplate<String, Object> redisTemplate;

	@Autowired
	private HfAuthMapper hfAuthMapper;

	@Autowired
	private HUserBalanceMapper hUserBalanceMapper;

	@Autowired
	private HfStoneMapper hfStoneMapper;

	@Autowired
	private HfBossMapper hfBossMapper;

	@Autowired
	private CancelMapper cancelMapper;

	@Autowired
	private hfStoreMenberMapper hfStoreMenberMappers;

	@Autowired
	private HfMemberLevelMapper hfMemberLevelMapper;

	@Autowired
	private HfLevelDescribeMapper hfLevelDescribleMapper;

	@Autowired
	private HfUserMemberMapper hfUserMemberMapper;

	@Autowired
	private HfBossInfoDao hfBossInfoDao;

	@Autowired
	private HfUserPrivilegeMapper hfUserPrivilegeMapper;

	@Autowired
	private HfUserBalanceMapper hfUserBalanceMapper;

	@Autowired
	private UserDao userDao;

	@Autowired
	private AccountMapper accountMapper;

	@Autowired
	private HfModuleMapper hfModuleMapper;

	@Autowired
	private AccountModelMapper accountModelMapper;

	@Autowired
    private AccountTypeModelMapper accountTypeModelMapper;
	@Autowired
	private RoleModelMapper roleModelMapper;
	@Autowired
	private AccountRolesMapper accountRolesMapper;

	@Autowired
	JedisPool jedisPool;

	@Autowired
	private RolesMapper rolesMapper;

	@Autowired
	private HfBossDetailsMapper hfBossDetailsMapper;

	@Autowired
	private HfOrderMapper hfOrderMapper;


	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ApiOperation(value = "用户登录", notes = "用户登录")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "authType", value = "鉴权方式,  1:用户登录, 2:手机号登录 ", required = true, type = "String"),
			@ApiImplicitParam(paramType = "query", name = "authKey", value = "鉴权key", required = false, type = "String"),
			@ApiImplicitParam(paramType = "query", name = "passwd", value = "密码", required = false, type = "String"), })
	public ResponseEntity<JSONObject> login(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(name = "authType") String authType, @RequestParam(name = "authKey") String authKey,
			@RequestParam(name = "passwd", required = false) Integer passwd, String type) throws Exception {
		Integer userId = null;


		Jedis jedis = jedisPool.getResource();
		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		if (passwd == null) {
			return builder.body(ResponseUtils.getResponseBody("还未填写验证码"));
		}
		if (!String.valueOf(passwd).equals(jedis.get(authKey))) {
			return builder.body(ResponseUtils.getResponseBody("验证码不正确"));
		}
		if(jedis != null) {
			jedis.close();
		}
		HfAuthExample example = new HfAuthExample();
		example.createCriteria().andAuthKeyEqualTo(authKey);
		List<HfAuth> list = hfAuthMapper.selectByExample(example);
		if (list.isEmpty()) {
			HfUser user = new HfUser();
			user.setSourceType(authType);
			user.setPhone(authKey);
			user.setUserStatus("0".getBytes()[0]);
			user.setLastAuthTime(LocalDateTime.now());
			user.setCreateDate(LocalDateTime.now());
			user.setModifyDate(LocalDateTime.now());
			user.setIdDeleted((byte) 0);
			user.setOwnInvitationCode(create());
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
			userId = user.getId();
		} else {
			userId = list.get(0).getUserId();
		}
		HfUser user = hfUserMapper.selectByPrimaryKey(userId);
		Cookie cookie = new Cookie("autologin", authKey);
		response.addCookie(cookie);
		redisTemplate.opsForValue().set("autologin", authKey);
//		Encrypt encrypt = new Encrypt();
//		String token = encrypt.getToken(true, user.getId(), type,1);
//		System.out.println(token);
//		response.addHeader("token", token);
		Map map = new HashMap();
		if (type != null) {
			map.put("id", user.getId());
			map.put("phone", user.getPhone());
			map.put("nickName", user.getNickName());
			map.put("realName", user.getRealName());
			map.put("fileId", user.getFileId());
			AccountExample accountExample = new AccountExample();
			accountExample.createCriteria().andAccountTypeEqualTo(type).andUserIdEqualTo(user.getId())
					.andIsDeletedEqualTo(0);
			List<Account> accounts = accountMapper.selectByExample(accountExample);
			if (accounts.size() == 0) {
				response.sendError(HttpStatus.FORBIDDEN.value(), "无此权限");
			}
			List<HfBoss> hfBosses = new ArrayList<>();
			List<HfStone> hfStones = new ArrayList<>();
			Set<Integer> id= accounts.stream().map(a->a.getMerchantId()).collect(Collectors.toSet());
			if (type.equals("boss")){
				HfBossExample hfBossExample = new HfBossExample();
				hfBossExample.createCriteria().andIsDeletedEqualTo((short) 0).andIdIn(Lists.newArrayList(id));
				hfBosses = hfBossMapper.selectByExample(hfBossExample);
			} else if (type.equals("stone")){
				HfStoneExample hfStoneExample = new HfStoneExample();
				hfStoneExample.createCriteria().andIsDeletedEqualTo((short) 0).andIdIn(Lists.newArrayList(id));
				hfStones = hfStoneMapper.selectByExample(hfStoneExample);
			} else if (type.equals("warehouse")){
				HfBossExample hfBossExample = new HfBossExample();
				hfBossExample.createCriteria().andIsDeletedEqualTo((short) 0).andIdIn(Lists.newArrayList(id));
				hfBosses = hfBossMapper.selectByExample(hfBossExample);
			}
			if (hfBosses.size()!=0){
				map.put("List",hfBosses);
			} else {
				map.put("List",hfStones);
			}
			
			
//			AccountModelExample accountModelExample = new AccountModelExample();
//			accountModelExample.createCriteria().andAccountIdEqualTo(accounts.get(0).getId())
//					.andIdDeletedEqualTo((byte) 0);
//			List<AccountModel> accountModels = accountModelMapper.selectByExample(accountModelExample);
//			Set<Integer> set = accountModels.stream().map(a -> a.getModelId()).collect(Collectors.toSet());
//			HfModuleExample hfModuleExample = new HfModuleExample();
//			hfModuleExample.createCriteria().andIdIn(Lists.newArrayList(set)).andIsDeletedEqualTo((byte) 0);
//			List<HfModule> hfModules = hfModuleMapper.selectByExample(hfModuleExample);
//			Set<String> model = hfModules.stream().map(a -> a.getHfModel()).collect(Collectors.toSet());
//			map.put("model", model);
//			Map<String, String> modelCode = hfModules.stream()
//					.collect(Collectors.toMap(HfModule::getModelCode, HfModule::getModelCode));
//			map.put("modelCode", modelCode);
//			map.put("token", token);
			
			
			map.put("identity",type);
			map.put("BSid",accounts.get(0).getMerchantId());
//			if (token != null && userId != null && type != null) {
//				redisTemplate.opsForValue().set(String.valueOf(userId) + type + "token", token);
//				redisTemplate.expire(String.valueOf(userId) + type + "token", 6000, TimeUnit.SECONDS);
//			}
			return builder.body(ResponseUtils.getResponseBody(map));
		}

		return builder.body(ResponseUtils.getResponseBody(user));
	}
	@RequestMapping(value = "/token", method = RequestMethod.POST)
	@ApiOperation(value = "token获取", notes = "token获取")
	public ResponseEntity<JSONObject> token(HttpServletRequest request, HttpServletResponse response,
												   @RequestParam(name = "userId", required = false) Integer userId,
												   @RequestParam(name = "type", required = false) String type,Integer merId) throws Exception {
		System.out.println("token");
		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		Encrypt encrypt = new Encrypt();
		String token = encrypt.getToken(true, userId, type,merId);
		Map map = new HashMap();
		map.put("identity",type);
		map.put("BSid",merId);
		map.put("token",token);
		AccountExample accountExample = new AccountExample();
		accountExample.createCriteria().andUserIdEqualTo(userId).andAccountTypeEqualTo(type).andMerchantIdEqualTo(merId);
		List<Account> accounts= accountMapper.selectByExample(accountExample);
		map.put("accountId",accounts.get(0).getId());
		AccountRolesExample accountRolesExample = new AccountRolesExample();
		accountRolesExample.createCriteria().andIsDeletedEqualTo((short) 0).andAccountIdEqualTo(accounts.get(0).getId());
		List<AccountRoles> accountRoles= accountRolesMapper.selectByExample(accountRolesExample);
		if (accountRoles.size()!=0){
			Set<Integer> roleId = accountRoles.stream().map(a->a.getRolesId()).collect(Collectors.toSet());
			RolesExample rolesExample = new RolesExample();
			rolesExample.createCriteria().andIdIn(Lists.newArrayList(roleId));
			List<Roles> roles = rolesMapper.selectByExample(rolesExample);
			roles = roles.stream().filter(a->a.getRoleType().equals(type)).collect(Collectors.toList());
			List<Roles> roles1 = new ArrayList<>();
			if (type.equals("boss")){
				roles1 = roles.stream().filter(a->a.getRoleCode().equals("sass")||a.getRoleCode().equals("boss")).collect(Collectors.toList());
			}
			if (type.equals("stone")){
				roles1 = roles.stream().filter(a->a.getRoleCode().equals("boss")).collect(Collectors.toList());
			}
			roles = roles.stream().filter(a->a.getMachId().equals(merId)).collect(Collectors.toList());
			roles = roles.stream().filter(a->a.getRoleCode().equals(a.getRoleType())).collect(Collectors.toList());
			Set<Integer> roleL = roles.stream().map(a->a.getId()).collect(Collectors.toSet());
			System.out.println("RoleL1"+roleL);
			if (roles1.size()!=0){
				roles1.forEach(roles2 -> {
					roleL.add(roles2.getId());
				});
			}
			System.out.println("RoleL2"+roleL);
			RoleModelExample roleModelExample = new RoleModelExample();
			roleModelExample.createCriteria().andRoleIdIn(Lists.newArrayList(roleL));
			List<RoleModel> roleModels = roleModelMapper.selectByExample(roleModelExample);
			if (roleModels.size()!=0){
				Set<Integer> modelsId = roleModels.stream().map(a->a.getModelId()).collect(Collectors.toSet());
				HfModuleExample hfModuleExample = new HfModuleExample();
				hfModuleExample.createCriteria().andIsDeletedEqualTo((byte) 0).andIdIn(Lists.newArrayList(modelsId));
				List<HfModule> hfModules = hfModuleMapper.selectByExample(hfModuleExample);

				Map<String, String> modelCode = hfModules.stream()
						.collect(Collectors.toMap(HfModule::getModelCode, HfModule::getModelCode));
				map.put("model",modelCode);
			}else {
				map.put("model",null);
			}

		} else {
			map.put("model",null);
		}
		if (token != null && userId != null && type != null) {
			redisTemplate.opsForValue().set(String.valueOf(accounts.get(0).getId()) + "token", token);
			redisTemplate.expire(String.valueOf(accounts.get(0).getId()) + "token", 6000, TimeUnit.SECONDS);
//			redisTemplate.opsForValue().set(String.valueOf(userId) + type +String.valueOf(merId)+ "token", token);
//			redisTemplate.expire(String.valueOf(userId) + type +String.valueOf(merId)+ "token", 6000, TimeUnit.SECONDS);1
		}
		return builder.body(ResponseUtils.getResponseBody(map));
	}

	@RequestMapping(value = "/tokentest", method = RequestMethod.POST)
	@ApiOperation(value = "token获取", notes = "token获取")
	public ResponseEntity<JSONObject> tokentest(HttpServletRequest request, HttpServletResponse response,
											String token) throws Exception {

		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		Decrypt decrypt = new Decrypt();
		DecodedJWT jwt = decrypt.deToken((String) token);
		System.out.println(jwt.getClaim("Type").asString());
		System.out.println(jwt.getClaim("userId").asInt());
		System.out.println(jwt.getClaim("merId").asInt());
		System.out.println(redisTemplate.opsForValue().get(String.valueOf(jwt.getClaim("userId").asInt()) + jwt.getClaim("Type").asString()+String.valueOf(jwt.getClaim("merId").asInt()) + "token"));
//		System.out.println(jwt.getClaim("Type").asString());

		return builder.body(ResponseUtils.getResponseBody("注册成功"));
	}
	@RequestMapping(value = "/addAdminUser", method = RequestMethod.POST)
	@ApiOperation(value = "添加管理后台用户", notes = "添加管理后台用户")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "phone", value = "手机号", required = false, type = "String"),
			@ApiImplicitParam(paramType = "query", name = "name", value = "用户名", required = false, type = "String"), })
	public ResponseEntity<JSONObject> addAdminUser(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(name = "phone", required = false) String phone,
			@RequestParam(name = "name", required = false) String name) throws Exception {

		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		HfAuthExample example = new HfAuthExample();
		example.createCriteria().andAuthKeyEqualTo(phone);
		List<HfAuth> list = hfAuthMapper.selectByExample(example);
		if (!list.isEmpty()) {
			return builder.body(ResponseUtils.getResponseBody("该用户已经存在"));
		}
		HfUser hfUser = new HfUser();
		hfUser.setRealName(name);
		hfUser.setPhone(phone);
		hfUser.setUserLevel((byte) 1);
		hfUser.setCreateDate(LocalDateTime.now());
		hfUser.setModifyDate(LocalDateTime.now());
		hfUser.setIdDeleted((byte) 0);
		hfUserMapper.insert(hfUser);
		HfAuth auth = new HfAuth();
		auth.setAuthKey(phone);
		auth.setAuthStatus((byte) 0);
		auth.setAuthType("2");
		auth.setUserId(hfUser.getId());
		auth.setIdDeleted((byte) 0);
		auth.setCreateDate(LocalDateTime.now());
		auth.setModifyDate(LocalDateTime.now());
		hfAuthMapper.insert(auth);
		return builder.body(ResponseUtils.getResponseBody("注册成功"));
	}

	@RequestMapping(value = "/findAdminUser", method = RequestMethod.GET)
	@ApiOperation(value = "查询后台用户", notes = "查询后台用户")

	public ResponseEntity<JSONObject> addAdminUser(Integer pageNum, Integer pageSize, String phone, String code,
			String name, HttpServletRequest request) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		if (pageNum == null) {
			pageNum = 0;
		}
		if (pageSize == null) {
			pageSize = 0;
		}
		List<HfUser> list = new ArrayList<HfUser>();
//		if(!StringUtils.isEmpty(phone)) {
//			HfUserExample example = new HfUserExample();
//			example.createCriteria().andPhoneLike(phone);
//			PageHelper.startPage(pageNum, pageSize);
//			list = hfUserMapper.selectByExample(example);
//		}else {
//			list = hfUserMapper.selectByExample(null);
//		}

		HfUser user = new HfUser();
		user.setPhone(phone);
		user.setOwnInvitationCode(code);
		user.setNickName(name);
		user.setRealName(name);
		if (request.getServletContext().getAttribute("getServletContext")!=null&&request.getServletContext().getAttribute("getServletContextType")!=null){
			if (request.getServletContext().getAttribute("getServletContextType").equals("boss")) {
				user.setBossId((Integer) request.getServletContext().getAttribute("getServletContext"));
			}
		}
//		HfUserExample example = new HfUserExample();
//		example.createCriteria().andPhoneLike(phone).andOwnInvitationCodeLike(code).andNickNameLike(name);
		list = userDao.selectUserOrderByInfo(user);
		List<UserInfo> result = new ArrayList<UserInfo>();
		for (int i = 0; i < list.size(); i++) {
			HfUser hfUser = list.get(i);
			UserInfo info = new UserInfo();
			info.setCreateDate(hfUser.getCreateDate());
			info.setAddress(hfUser.getAddress());
			info.setSex(hfUser.getSex());
			info.setBirthDay(hfUser.getBirthDay());
			info.setFileId(hfUser.getFileId());
			info.setEmail(hfUser.getEmail());
			info.setInvitationCode(hfUser.getInvitationCode());
			info.setOwnInvitationCode(hfUser.getOwnInvitationCode());
			info.setNickName(hfUser.getNickName());
			info.setRealName(hfUser.getRealName());
			info.setPhone(hfUser.getPhone());
			info.setId(hfUser.getId());
			HUserBalanceExample example2 = new HUserBalanceExample();
			example2.createCriteria().andUserIdEqualTo(hfUser.getId());
			if (hUserBalanceMapper.selectByExample(example2).isEmpty()) {
				info.setHfBalance(0);
			} else {
				hUserBalanceMapper.selectByExample(example2).get(0).getHfBalance();
			}

			result.add(info);
		}
		PageInfo<UserInfo> page = new PageInfo<UserInfo>(result);
		return builder.body(ResponseUtils.getResponseBody(page));
	}

	@RequestMapping(value = "/findUserDetails", method = RequestMethod.GET)
	@ApiOperation(value = "后台查询用户详情", notes = "后台查询用户详情")

	public ResponseEntity<JSONObject> findUserDetails(Integer userId) throws Exception {

		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		HfUser hfUser = hfUserMapper.selectByPrimaryKey(userId);
		if (hfUser == null) {
			return builder.body(ResponseUtils.getResponseBody("此用户不存在"));
		}
		UserInfo info = new UserInfo();
		info.setCreateDate(hfUser.getCreateDate());
		info.setAddress(hfUser.getAddress());
		info.setSex(hfUser.getSex());
		info.setBirthDay(hfUser.getBirthDay());
		info.setFileId(hfUser.getFileId());
		info.setEmail(hfUser.getEmail());
		info.setInvitationCode(hfUser.getInvitationCode());
		info.setOwnInvitationCode(hfUser.getOwnInvitationCode());
		info.setNickName(hfUser.getNickName());
		info.setPhone(hfUser.getPhone());
		info.setId(hfUser.getId());
		HUserBalanceExample example2 = new HUserBalanceExample();
		example2.createCriteria().andUserIdEqualTo(hfUser.getId());
		if (hUserBalanceMapper.selectByExample(example2).isEmpty()) {
			info.setHfBalance(0);
		} else {
			hUserBalanceMapper.selectByExample(example2).get(0).getHfBalance();
		}
		return builder.body(ResponseUtils.getResponseBody(info));
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ApiOperation(value = "更新用户信息", notes = "更新用户信息")
	public ResponseEntity<JSONObject> update(UserInfoRequest request) throws Exception {

		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		HfUser user = hfUserMapper.selectByPrimaryKey(request.getUserId());
		if (user == null) {
			throw new UserNotExistException(String.valueOf(request.getUserId()));
		}
		if (!StringUtils.isEmpty(request.getInvitationCode())) {
			user.setInvitationCode(request.getInvitationCode());
		}
		
		if (!StringUtils.isEmpty(request.getPhone())) {
			user.setPhone(request.getPhone());
		}
		
		if (!StringUtils.isEmpty(request.getFileId())) {
			if(user.getFileId() != null) {
				FileMangeService fileMangeService = new FileMangeService();
				FileDesc desc = fileDescMapper.selectByPrimaryKey(user.getFileId());
				fileMangeService.deleteFile(desc.getGroupName(), desc.getRemoteFilename());
				fileDescMapper.deleteByPrimaryKey(user.getFileId());
			}
			user.setFileId(request.getFileId());
		}
		if (!StringUtils.isEmpty(request.getAddress())) {
			user.setAddress(request.getAddress());
		}
		if (!StringUtils.isEmpty(request.getUsername())) {
			user.setUsername(request.getUsername());
		}
		if (!StringUtils.isEmpty(request.getBirthDay())) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = sdf.parse(request.getBirthDay());
			Instant instant = date.toInstant();
			ZoneId zoneId = ZoneId.systemDefault();
			LocalDateTime ldt = instant.atZone(zoneId).toLocalDateTime();
			user.setBirthDay(ldt);
		}
		if (!StringUtils.isEmpty(request.getEmail())) {
			user.setEmail(request.getEmail());
		}
		if (!StringUtils.isEmpty(request.getNickName())) {
			user.setNickName(request.getNickName());
		}
		if (!StringUtils.isEmpty(request.getRealName())) {
			user.setRealName(request.getRealName());
		}
		if (!StringUtils.isEmpty(request.getRegion())) {
			user.setRegion(request.getRegion());
		}
		if (!StringUtils.isEmpty(request.getSex())) {
			user.setSex(request.getSex());
		}
		if (!StringUtils.isEmpty(request.getUserStatus())) {
			user.setUserStatus(request.getUserStatus());
		}
		if (!StringUtils.isEmpty(request.getCancelId())) {
			user.setCancelId(request.getCancelId());
		}
		user.setModifyDate(LocalDateTime.now());
		user.setIdDeleted((byte) 0);

		return builder.body(ResponseUtils.getResponseBody(hfUserMapper.updateByPrimaryKeySelective(user)));
	}

	@RequestMapping(value = "/deleteAdminUser", method = RequestMethod.GET)
	@ApiOperation(value = "删除后台用户", notes = "删除后台用户")

	public ResponseEntity<JSONObject> deleteAdminUser(@RequestParam(name = "userId", required = false) Integer userId)
			throws Exception {

		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		HfUser hfUser = hfUserMapper.selectByPrimaryKey(userId);
		if (hfUser == null) {
			return builder.body(ResponseUtils.getResponseBody("此用户不存在"));
		}
		hfUserMapper.deleteByPrimaryKey(userId);
		HfAuthExample example = new HfAuthExample();
		example.createCriteria().andUserIdEqualTo(userId);
		hfAuthMapper.deleteByExample(example);
		return builder.body(ResponseUtils.getResponseBody("删除成功"));
	}

	@RequestMapping(value = "/findBossInfo", method = RequestMethod.GET)
	@ApiOperation(value = "商家基本信息", notes = "商家基本信息")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "bossId", value = "商家id", required = true, type = "Integer") })
	public ResponseEntity<JSONObject> findBossInfo(HttpServletRequest request,Integer bossId) throws Exception {
		if (request.getServletContext().getAttribute("getServletContextType")!=null&&request.getServletContext().getAttribute("getServletContextType").equals("boss")){
			System.out.println("request.getServletContext().getAttribute得到全局数据："+request.getServletContext().getAttribute("getServletContext"));
			if (request.getServletContext().getAttribute("getServletContext")!=null){
				bossId = (Integer) request.getServletContext().getAttribute("getServletContext");
			}
		}
		ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
		double amount = 0;
		HfBoss boss = hfBossMapper.selectByPrimaryKey(bossId);
		HfUser hfUser = hfUserMapper.selectByPrimaryKey(boss.getUserId());
		HfStoneExample example = new HfStoneExample();
		example.createCriteria().andBossIdEqualTo(bossId);
		List<HfStone> list = hfStoneMapper.selectByExample(example);
		for (int i = 0; i < list.size(); i++) {
			HfStone hfStone = list.get(i);
			System.out.println(hfStone);
			Double a = hfBossInfoDao.selectAllOrderByBossId(hfStone.getId());
			if (a != null) {
				amount += a;
			}
		}
		Integer count = hfBossInfoDao.selectBrowseCountsByBossId(bossId);
		HfBossInfo bossInfo = new HfBossInfo();
		bossInfo.setAmount(amount);
		bossInfo.setBrowseCounts(count);
		bossInfo.setStones(list);
		bossInfo.setBossName(boss.getName());
		bossInfo.setExpireDate(boss.getExpireTime());
		bossInfo.setRegisteredCapital(boss.getRegisteredCapital());
		bossInfo.setBusinessScope(boss.getBusinessScope());
		bossInfo.setPhone(hfUser.getPhone());
		return builder.body(ResponseUtils.getResponseBody(bossInfo));
	}

	@RequestMapping(value = "/selectStoneAdmin", method = RequestMethod.GET)
	@ApiOperation(value = "店铺管理员列表", notes = "店铺管理员列表根据商家id")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "bossId", value = "商家id", required = true, type = "Integer") })
	public ResponseEntity<JSONObject> select(HttpServletRequest requests,Integer bossId, String phone, String code, String name) throws Exception {
		ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
		if (requests.getServletContext().getAttribute("getServletContext")!=null&&requests.getServletContext().getAttribute("getServletContextType")!=null){
			if (requests.getServletContext().getAttribute("getServletContextType").equals("boss")) {
				bossId=(Integer) requests.getServletContext().getAttribute("getServletContext");
			}
		}
		List<StoreUser> storeUsers = new ArrayList<>();
		List<Integer> result = new ArrayList<Integer>();
		HfStoneExample example = new HfStoneExample();
		example.createCriteria().andBossIdEqualTo(bossId);
		List<HfStone> list = hfStoneMapper.selectByExample(example);
		for (int i = 0; i < list.size(); i++) {
			HfStone hfStone = list.get(i);
			hfStoreMenberExample example2 = new hfStoreMenberExample();
			example2.createCriteria().andStoreIdEqualTo(hfStone.getId());
			List<hfStoreMenber> list2 = hfStoreMenberMappers.selectByExample(example2);
			for (int j = 0; j < list2.size(); j++) {
				hfStoreMenber hfStoreMenber = list2.get(j);
				result.add(hfStoreMenber.getUserId());
			}
		}
//		for  ( int  i  =   0 ; i  <  qq.size()  -   1 ; i ++ )  {       
//		      for  ( int  j  =  qq.size()  -   1 ; j  >  i; j -- )  {       
//		           if  (qq.get(j).equals(qq.get(i)))  {       
//		        	   qq.remove(j);       
//		            }        
//		        }        
//		      }        
		HashSet h = new HashSet(result);
		result.clear();
		result.addAll(h);
		if (result.size()==0){
			result.add(0);
		}
//		for (int i = 0; i < result.size(); i++) {
//			StoreUser storeUser = new StoreUser();
//			HfUser hfUser = hfUserMapper.selectByPrimaryKey(result.get(i));
//			if (hfUser != null) {
//				if (!StringUtils.isEmpty(hfUser.getInvitationCode())) {
//					storeUser.setOwnInvitationCode(hfUser.getInvitationCode());
//				}
//				if (!StringUtils.isEmpty(hfUser.getNickName())) {
//					storeUser.setUserName(hfUser.getNickName());
//				}
//				if (!StringUtils.isEmpty(hfUser.getRealName())) {
//					storeUser.setRealName(hfUser.getRealName());
//				}
//				if (!StringUtils.isEmpty(hfUser.getPhone())) {
//					storeUser.setUserPhone(hfUser.getPhone());
//				}
//				storeUser.setUserId(hfUser.getId());
//				storeUsers.add(storeUser);
//			}
//		}
		StoreUser user = new StoreUser();
		user.setPhone(phone);
		user.setOwnInvitationCode(code);
		user.setUserName(name);
		user.setRealName(name);
		user.setId(result);
		storeUsers = userDao.selectStoneMemberByInfo(user);
		return builder.body(ResponseUtils.getResponseBody(storeUsers));
	}

	@ApiOperation(value = "添加用户会员等级", notes = "添加用户会员等级")
	@RequestMapping(value = "/addUserMemberLevel", method = RequestMethod.POST)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "name", value = "等级名称", required = false, type = "String"),
			@ApiImplicitParam(paramType = "query", name = "level", value = "等级", required = false, type = "Integer"),
			@ApiImplicitParam(paramType = "query", name = "levelDescribe", value = "描述", required = false, type = "String"),
			@ApiImplicitParam(paramType = "query", name = "amount", value = "金额", required = false, type = "String") })
	public ResponseEntity<JSONObject> addUserMemberLevel(String name, Integer level, String levelDescribe,
			String amount, HttpServletRequest request) throws JSONException {

		BodyBuilder builder = ResponseUtils.getBodyBuilder();

		HfMemberLevelExample example = new HfMemberLevelExample();
		example.createCriteria().andLevelEqualTo(level);

		List<HfMemberLevel> list = hfMemberLevelMapper.selectByExample(example);
		if (!list.isEmpty()) {
			return builder.body(ResponseUtils.getResponseBody(-1));
		}

		HfMemberLevel hfMemberLevel = new HfMemberLevel();
		hfMemberLevel.setLevelName(name);
		hfMemberLevel.setLevel(level);
		hfMemberLevel.setAmount(amount);
		hfMemberLevel.setLevelDescribe(levelDescribe);
		hfMemberLevel.setCreateTime(LocalDateTime.now());
		hfMemberLevel.setModifyTime(LocalDateTime.now());
		hfMemberLevel.setIsDeleted((byte) 0);
		System.out.println(request.getServletContext().getAttribute("getServletContextType").equals("boss"));
		if (request.getServletContext().getAttribute("getServletContextType").equals("boss")) {
			hfMemberLevel.setBossId((Integer) request.getServletContext().getAttribute("getServletContext"));
			hfMemberLevelMapper.insert(hfMemberLevel);
		}
		System.out.println(request.getServletContext().getAttribute("getServletContext"));
		return builder.body(ResponseUtils.getResponseBody(hfMemberLevel.getId()));
	}

	@ApiOperation(value = "修改用户会员等级", notes = "修改用户会员等级")
	@RequestMapping(value = "/updateUserMemberLevel", method = RequestMethod.POST)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "name", value = "等级名称", required = false, type = "String"),
			@ApiImplicitParam(paramType = "query", name = "id", value = "等级id", required = true, type = "Integer"),
			@ApiImplicitParam(paramType = "query", name = "level", value = "等级", required = false, type = "Integer"),
			@ApiImplicitParam(paramType = "query", name = "levelDescribe", value = "描述", required = false, type = "String"),
			@ApiImplicitParam(paramType = "query", name = "amount", value = "金额", required = false, type = "String") })
	public ResponseEntity<JSONObject> updateUserMemberLevel(@RequestParam(required = false) String name, Integer id,
			@RequestParam(required = false) Integer level, @RequestParam(required = false) String levelDescribe,
			@RequestParam(required = false) String amount) throws JSONException {

		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		HfMemberLevel hfMemberLevel = hfMemberLevelMapper.selectByPrimaryKey(id);

		if (hfMemberLevel == null) {
			return builder.body(ResponseUtils.getResponseBody("数据异常"));
		}

		if (!StringUtils.isEmpty(level)) {

			HfMemberLevelExample example = new HfMemberLevelExample();
			example.createCriteria().andLevelEqualTo(level);

			List<HfMemberLevel> list = hfMemberLevelMapper.selectByExample(example);
			if (!list.isEmpty()) {
				return builder.body(ResponseUtils.getResponseBody(-1));
			}

			hfMemberLevel.setLevel(level);
		}

		if (!StringUtils.isEmpty(name)) {
			hfMemberLevel.setLevelName(name);
		}

		if (!StringUtils.isEmpty(levelDescribe)) {
			hfMemberLevel.setLevelDescribe(levelDescribe);
		}

		if (!StringUtils.isEmpty(amount)) {
			hfMemberLevel.setAmount(amount);
		}
		hfMemberLevel.setModifyTime(LocalDateTime.now());
		hfMemberLevelMapper.updateByPrimaryKey(hfMemberLevel);
		return builder.body(ResponseUtils.getResponseBody("更新成功"));
	}

	@ApiOperation(value = "删除用户会员等级", notes = "删除用户会员等级")
	@RequestMapping(value = "/deleteUserMemberLevel", method = RequestMethod.GET)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "id", value = "等级id", required = true, type = "Integer") })
	public ResponseEntity<JSONObject> deleteUserMemberLevel(Integer id) throws JSONException {

		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		HfMemberLevel hfMemberLevel = hfMemberLevelMapper.selectByPrimaryKey(id);
		HfLevelDescribeExample describeExample = new HfLevelDescribeExample();
		describeExample.createCriteria().andLevelIdEqualTo(id);
		List<HfLevelDescribe> describes = hfLevelDescribleMapper.selectByExample(describeExample);
		List<Integer> prerogative = describes.stream().map(HfLevelDescribe::getId).collect(Collectors.toList());
		HfUserPrivilegeExample privilegeExample = new HfUserPrivilegeExample();
		privilegeExample.createCriteria().andPrivilegeIdIn(prerogative);
		hfUserPrivilegeMapper.deleteByExample(privilegeExample);
		if (hfMemberLevel == null) {
			return builder.body(ResponseUtils.getResponseBody("数据异常"));
		}
		hfMemberLevelMapper.deleteByPrimaryKey(id);
		HfLevelDescribeExample example = new HfLevelDescribeExample();
		example.createCriteria().andLevelIdEqualTo(id);
		hfLevelDescribleMapper.deleteByExample(example);
		HfUserMemberExample example2 = new HfUserMemberExample();
		example2.createCriteria().andLevelIdEqualTo(id);
		List<HfUserMember> list = hfUserMemberMapper.selectByExample(example2);
		for (int i = 0; i < list.size(); i++) {
			HfUserMember hfUserMember = list.get(i);
			hfUserMember.setLevelId(null);
			hfUserMemberMapper.updateByPrimaryKey(hfUserMember);
		}
		return builder.body(ResponseUtils.getResponseBody("删除成功"));
	}

	@ApiOperation(value = "查询用户会员等级", notes = "查询用户会员等级")
	@RequestMapping(value = "/findUserMemberLevel", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> findUserMemberLevel(HttpServletRequest request) throws JSONException {

		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		HfMemberLevelExample example = new HfMemberLevelExample();
		if (request.getServletContext().getAttribute("getServletContext")!=null&&request.getServletContext().getAttribute("getServletContextType")!=null){
			if (request.getServletContext().getAttribute("getServletContextType").equals("boss")) {
				example.createCriteria().andBossIdEqualTo((Integer)request.getServletContext().getAttribute("getServletContext"));
			}
		}
		List<HfMemberLevel> list = hfMemberLevelMapper.selectByExample(example);
		List<HfMemberLevelInfo> result = new ArrayList<HfMemberLevelInfo>();
		for (int i = 0; i < list.size(); i++) {
			HfMemberLevel hfMemberLevel = list.get(i);
			HfMemberLevelInfo info = new HfMemberLevelInfo();
			info.setId(hfMemberLevel.getId());
			info.setLevelName(hfMemberLevel.getLevelName());
			info.setLevel(hfMemberLevel.getLevel());
			info.setCreateTime(hfMemberLevel.getCreateTime());
			info.setModifyTime(hfMemberLevel.getModifyTime());
			info.setIsDeleted(hfMemberLevel.getIsDeleted());
			info.setLevelDescribe(hfMemberLevel.getLevelDescribe());
			info.setAmount(hfMemberLevel.getAmount());
			result.add(info);
		}
		result.sort(new Comparator<HfMemberLevelInfo>() {
			@Override
			public int compare(HfMemberLevelInfo o1, HfMemberLevelInfo o2) {
				return o1.getLevel() - o2.getLevel();
			}
		});
		return builder.body(ResponseUtils.getResponseBody(result));
	}

	@ApiOperation(value = "添加会员", notes = "添加会员")
	@RequestMapping(value = "/addUserMember", method = RequestMethod.POST)
//	Date startTime, Date endTime, HfUserMember hfUserMember,
	public ResponseEntity<JSONObject> addUserMember(Integer levelId, Integer[] userId) throws JSONException {

		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		HfLevelDescribeExample describeExample = new HfLevelDescribeExample();
		describeExample.createCriteria().andLevelIdEqualTo(levelId);
		List<HfLevelDescribe> describes = hfLevelDescribleMapper.selectByExample(describeExample);
//		Instant instant = startTime.toInstant();
//		ZoneId zoneId = ZoneId.systemDefault();
//		LocalDateTime ldt = instant.atZone(zoneId).toLocalDateTime();
//		Instant instant2 = endTime.toInstant();
//		ZoneId zoneId2 = ZoneId.systemDefault();
//		LocalDateTime ldt2 = instant2.atZone(zoneId2).toLocalDateTime();
		HfUserMemberExample hfUserMemberExample = new HfUserMemberExample();
		for (int i = 0; i < userId.length; i++) {
			hfUserMemberExample.clear();
			hfUserMemberExample.createCriteria().andUserIdEqualTo(userId[i]);
			List<HfUserMember> members = hfUserMemberMapper.selectByExample(hfUserMemberExample);
			if (members.isEmpty()) {

				HfUserMember member = new HfUserMember();
				member.setUserId(userId[i]);
				member.setLevelId(levelId);
				for (int j = 0; j < describes.size(); j++) {
					HfUserPrivilege privilege = new HfUserPrivilege();
					privilege.setUserId(userId[i]);
					privilege.setPrivilegeId(describes.get(j).getId());
					;
					hfUserPrivilegeMapper.insert(privilege);
				}
//			member.setStartTme(ldt);
//			member.setEndTime(ldt2);
				member.setCreateTime(LocalDateTime.now());
				member.setModifyTime(LocalDateTime.now());
				member.setIsDeleted((byte) 0);
				hfUserMemberMapper.insert(member);
			}
		}

		return builder.body(ResponseUtils.getResponseBody(userId.length));
	}

	@ApiOperation(value = "修改会员信息", notes = "修改会员信息")
	@RequestMapping(value = "/updateUserMember", method = RequestMethod.POST)
//	Date startTime, Date endTime, 
	public ResponseEntity<JSONObject> updateUserMember(HfUserMember hfUserMember, Integer levelId)
			throws JSONException {

		BodyBuilder builder = ResponseUtils.getBodyBuilder();

		HfUserMember member = hfUserMemberMapper.selectByPrimaryKey(hfUserMember.getId());
		if (member == null) {
			return builder.body(ResponseUtils.getResponseBody("数据异常"));
		}

//		if (!StringUtils.isEmpty(String.valueOf(startTime))) {
//			Instant instant = startTime.toInstant();
//			ZoneId zoneId = ZoneId.systemDefault();
//			LocalDateTime ldt = instant.atZone(zoneId).toLocalDateTime();
//			member.setStartTme(ldt);
//		}
//		if (!StringUtils.isEmpty(String.valueOf(endTime))) {
//			Instant instant = endTime.toInstant();
//			ZoneId zoneId = ZoneId.systemDefault();
//			LocalDateTime ldt = instant.atZone(zoneId).toLocalDateTime();
//			member.setEndTime(ldt);
//		}
		if (!StringUtils.isEmpty(String.valueOf(levelId))) {
			member.setLevelId(levelId);
		}
		member.setModifyTime(LocalDateTime.now());
		hfUserMemberMapper.updateByPrimaryKey(member);
		return builder.body(ResponseUtils.getResponseBody(member.getId()));
	}

	@ApiOperation(value = "删除会员", notes = "删除会员")
	@RequestMapping(value = "/deleteUserMember", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> deleteUserMember(Integer id) throws JSONException {

		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		HfUserMember member = hfUserMemberMapper.selectByPrimaryKey(id);
		hfUserMemberMapper.deleteByPrimaryKey(id);
		HfUserPrivilegeExample example = new HfUserPrivilegeExample();
		example.createCriteria().andUserIdEqualTo(member.getUserId());
		hfUserPrivilegeMapper.deleteByExample(example);
		return builder.body(ResponseUtils.getResponseBody("删除成功"));
	}

	@ApiOperation(value = "查询会员", notes = "查询会员")
	@RequestMapping(value = "/findUserMember", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> findUserMember(Integer pageNum, Integer pageSize, String phone, String code,
			String name, Integer levelId, HttpServletRequest request) throws JSONException {

		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		if (pageNum == null) {
			pageNum = 0;
		}
		if (pageSize == null) {
			pageSize = 0;
		}
		HfUser user = new HfUser();
		user.setPhone(phone);
		user.setOwnInvitationCode(code);
		user.setNickName(name);
		user.setRealName(name);
		if (request.getServletContext().getAttribute("getServletContext")!=null&&request.getServletContext().getAttribute("getServletContextType")!=null){
			if (request.getServletContext().getAttribute("getServletContextType").equals("boss")) {
				user.setBossId((Integer)request.getServletContext().getAttribute("getServletContext"));;
			}
		}
		
//		if (request.getServletContext().getAttribute("getServletContext").equals("boss")) {
//			user.setBossId((Integer) request.getServletContext().getAttribute("getServletContext"));
//		}
		user.setId(levelId);
		PageHelper.startPage(pageNum, pageSize);
		System.out.println("查询会员+++++++++++我是bossId"+(Integer)request.getServletContext().getAttribute("getServletContext"));
		List<HfUserMember> list = userDao.selectHfUserMember(user);
		List<HfUserMemberInfo> result = new ArrayList<HfUserMemberInfo>();
		for (int i = 0; i < list.size(); i++) {
			HfUserMember member = list.get(i);
			HfUserMemberInfo info = new HfUserMemberInfo();
			info.setId(member.getId());
			info.setUserId(member.getUserId());
			info.setUseState(member.getUseState());
			HfUser hfUser = hfUserMapper.selectByPrimaryKey(member.getUserId());
			if (hfUser != null) {
				if (!StringUtils.isEmpty(hfUser.getRealName())) {
					info.setName(hfUser.getRealName());
				}
				if (!StringUtils.isEmpty(hfUser.getPhone())) {
					info.setPhone(hfUser.getPhone());
				}
			}
			info.setCreateTime(member.getCreateTime());
			info.setModifyTime(member.getModifyTime());
			info.setIsDeleted(member.getIsDeleted());
//			info.setStartTime(member.getStartTme());
//			info.setEndTime(member.getEndTime());
			if (member.getLevelId() != null) {
				info.setLevelId(member.getLevelId());
				HfMemberLevel level = hfMemberLevelMapper.selectByPrimaryKey(member.getLevelId());
				info.setLevelName(level.getLevelName());
			}
//			LocalDateTime ldt = LocalDateTime.now();
//			if (ldt.isAfter(member.getStartTme()) && ldt.isBefore(member.getEndTime())) {
//				System.out.println("开始了");
//				info.setUseState(0);
//				member.setUseState(0);
//				hfUserMemberMapper.updateByPrimaryKey(member);
//			}
//			if (ldt.isBefore(member.getStartTme())) {
//				System.out.println("还未开始");
//				info.setUseState(-1);
//				member.setUseState(-1);
//				hfUserMemberMapper.updateByPrimaryKey(member);
//			}
//			if (ldt.isAfter(member.getEndTime())) {
//				System.out.println("结束了");
//				info.setUseState(1);
//				member.setUseState(1);
//				hfUserMemberMapper.updateByPrimaryKey(member);
//			}
			result.add(info);
		}
		return builder.body(ResponseUtils.getResponseBody(result));
	}

	@ApiOperation(value = "填写等级描述", notes = "填写等级描述")
	@RequestMapping(value = "/addMemberLevelDescribe", method = RequestMethod.POST)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "levelId", value = "等级id", required = true, type = "Integer"),
			@ApiImplicitParam(paramType = "query", name = "levelDescribe", value = "特权描述", required = true, type = "String"),
			@ApiImplicitParam(paramType = "query", name = "prerogative", value = "特权名称", required = true, type = "String") })
	public ResponseEntity<JSONObject> addMemberLevelDescribe(Integer levelId, String levelDescribe, String prerogative,
			Date expireTime, Date startTime) throws JSONException {

		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		Date date = new Date();
		Instant instant2 = date.toInstant();
		ZoneId zoneId2 = ZoneId.systemDefault();
		LocalDateTime ldt = instant2.atZone(zoneId2).toLocalDateTime();
		HfLevelDescribe describe = new HfLevelDescribe();
		describe.setLevelId(levelId);
		describe.setPrerogative(prerogative);
		Instant instant = expireTime.toInstant();
		ZoneId zoneId = ZoneId.systemDefault();
		LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
		describe.setExpireTime(localDateTime);
		instant = startTime.toInstant();
		zoneId = ZoneId.systemDefault();
		localDateTime = instant.atZone(zoneId).toLocalDateTime();
		describe.setStartTime(localDateTime);
		describe.setLevelDescribe(levelDescribe);
		describe.setCreateTime(LocalDateTime.now());
		describe.setModifyTime(LocalDateTime.now());
		describe.setIsDeleted((byte) 0);
		hfLevelDescribleMapper.insert(describe);
		HfUserMemberExample example = new HfUserMemberExample();
		example.createCriteria().andLevelIdEqualTo(levelId);
		List<HfUserMember> list = hfUserMemberMapper.selectByExample(example);
		for (int i = 0; i < list.size(); i++) {
			HfUserMember member = list.get(i);
			HfUserPrivilege privilege = new HfUserPrivilege();
			privilege.setUserId(member.getUserId());
			privilege.setPrivilegeId(describe.getId());
			;
			hfUserPrivilegeMapper.insert(privilege);
		}
		return builder.body(ResponseUtils.getResponseBody(describe.getId()));
	}

	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request) {
		// 转换日期
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));// CustomDateEditor为自定义日期编辑器
	}

	@ApiOperation(value = "更新等级描述", notes = "更新等级描述")
	@RequestMapping(value = "/updateMemberLevelDescribe", method = RequestMethod.POST)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "id", value = "id", required = true, type = "Integer"),
			@ApiImplicitParam(paramType = "query", name = "levelDescribe", value = "特权描述", required = false, type = "String"),
			@ApiImplicitParam(paramType = "query", name = "prerogative", value = "特权名称", required = false, type = "String") })
	public ResponseEntity<JSONObject> updateMemberLevelDescribe(Integer id,
			@RequestParam(required = false) String levelDescribe, @RequestParam(required = false) String prerogative,
			@RequestParam(required = false) Date expireTime, @RequestParam(required = false) Date startTime)
			throws JSONException {

		BodyBuilder builder = ResponseUtils.getBodyBuilder();

		HfLevelDescribe describle = hfLevelDescribleMapper.selectByPrimaryKey(id);
		if (describle == null) {
			return builder.body(ResponseUtils.getResponseBody("数据异常"));
		}
		describle.setModifyTime(LocalDateTime.now());
		if (!StringUtils.isEmpty(levelDescribe)) {
			describle.setLevelDescribe(levelDescribe);
		}
		if (!StringUtils.isEmpty(levelDescribe)) {
			describle.setLevelDescribe(levelDescribe);
		}
		if (!StringUtils.isEmpty(prerogative)) {
			describle.setPrerogative(prerogative);
		}
		if (expireTime != null) {
			Instant instant = expireTime.toInstant();
			ZoneId zoneId = ZoneId.systemDefault();
			LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
			describle.setExpireTime(localDateTime);
		}
		if (startTime != null) {
			Instant instant = startTime.toInstant();
			ZoneId zoneId = ZoneId.systemDefault();
			LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
			describle.setExpireTime(localDateTime);
		}

		hfLevelDescribleMapper.updateByPrimaryKey(describle);
		return builder.body(ResponseUtils.getResponseBody("更新成功"));
	}

	@ApiOperation(value = "删除等级输入框", notes = "删除等级输入框")
	@RequestMapping(value = "/deleteMemberLevelDescribe", method = RequestMethod.GET)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "id", value = "id", required = true, type = "Integer") })
	public ResponseEntity<JSONObject> deleteMemberLevelDescribe(Integer id) throws JSONException {

		BodyBuilder builder = ResponseUtils.getBodyBuilder();

		HfLevelDescribe describle = hfLevelDescribleMapper.selectByPrimaryKey(id);
		if (describle == null) {
			return builder.body(ResponseUtils.getResponseBody("数据异常"));
		}

		HfUserMemberExample example = new HfUserMemberExample();
		example.createCriteria().andLevelIdEqualTo(id);
		List<HfUserMember> list = hfUserMemberMapper.selectByExample(example);
		for (int i = 0; i < list.size(); i++) {
			HfUserMember member = list.get(i);
			HfUserPrivilegeExample example2 = new HfUserPrivilegeExample();
			example2.createCriteria().andUserIdEqualTo(member.getUserId()).andPrivilegeIdEqualTo(id);
			hfUserPrivilegeMapper.deleteByExample(example2);
		}
		hfLevelDescribleMapper.deleteByPrimaryKey(id);
		return builder.body(ResponseUtils.getResponseBody("删除成功"));
	}

	@ApiOperation(value = "查询等级描述", notes = "查询等级描述根据等级")
	@RequestMapping(value = "/findMemberLevelDescribe", method = RequestMethod.GET)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "levelId", value = "levelId", required = true, type = "Integer") })
	public ResponseEntity<JSONObject> findMemberLevelDescribe(Integer levelId) throws JSONException {

		BodyBuilder builder = ResponseUtils.getBodyBuilder();

		HfMemberLevel hfMemberLevel = hfMemberLevelMapper.selectByPrimaryKey(levelId);
		if (hfMemberLevel == null) {
			return builder.body(ResponseUtils.getResponseBody("数据异常"));
		}
		HfLevelDescribeExample example = new HfLevelDescribeExample();
		example.createCriteria().andLevelIdEqualTo(levelId);
		List<HfLevelDescribe> list = hfLevelDescribleMapper.selectByExample(example);
		List<HfLevelDescribeInfo> result = new ArrayList<HfLevelDescribeInfo>();
		for (int i = 0; i < list.size(); i++) {
			HfLevelDescribe describle = list.get(i);
			HfLevelDescribeInfo info = new HfLevelDescribeInfo();
			info.setId(describle.getId());
			info.setLevelId(describle.getLevelId());
			info.setPrerogative(describle.getPrerogative());
			if (LocalDateTime.now().isBefore(describle.getStartTime())
					|| LocalDateTime.now().isAfter(describle.getExpireTime())) {
				info.setPrerogativeState(-1);
				describle.setPrerogativeState(-1);
			}
			if (LocalDateTime.now().isAfter(describle.getStartTime())
					&& LocalDateTime.now().isBefore(describle.getExpireTime())) {
				info.setPrerogativeState(1);
				describle.setPrerogativeState(1);
			}
			info.setStartTime(describle.getStartTime());
			info.setLevelDescribe(describle.getLevelDescribe());
			info.setExpireTime(describle.getExpireTime());
			info.setCreateTime(describle.getCreateTime());
			info.setModifyTime(describle.getModifyTime());
			hfLevelDescribleMapper.updateByPrimaryKey(describle);
			info.setIsDeleted((byte) 0);
			result.add(info);
		}

		return builder.body(ResponseUtils.getResponseBody(result));
	}

	@ApiOperation(value = "查询小程序我的基本信息根据用户id", notes = "查询小程序我的基本信息根据用户id")
	@RequestMapping(value = "/findInfoByUserId", method = RequestMethod.GET)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "userId", value = "用户id", required = true, type = "Integer") })
	public ResponseEntity<JSONObject> findInfoByUserId(Integer userId) throws JSONException {

		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		HfUserMemberExample example = new HfUserMemberExample();
		example.createCriteria().andUserIdEqualTo(userId);
		List<HfUserMember> member = hfUserMemberMapper.selectByExample(example);
		HfUserBalanceExample example2 = new HfUserBalanceExample();
		example2.createCriteria().andUserIdEqualTo(userId).andBalanceTypeEqualTo("rechargeAmount");
		List<HfUserBalance> balance = hfUserBalanceMapper.selectByExample(example2);
		example2.clear();
		example2.createCriteria().andUserIdEqualTo(userId).andBalanceTypeEqualTo("integral");
		List<HfUserBalance> balance2 = hfUserBalanceMapper.selectByExample(example2);
		example2.clear();
		example2.createCriteria().andUserIdEqualTo(userId).andBalanceTypeEqualTo("discountCoupon");
		List<HfUserBalance> balance3 = hfUserBalanceMapper.selectByExample(example2);

		Integer browseCount = userDao.selectBrowseCount(userId);
		Integer collectCount = userDao.selectCollectCount(userId);
		Integer concernCount = userDao.selectConcernCount(userId);

		List<UserOrderInfo> orders = new ArrayList<UserOrderInfo>();
		Order order = new Order();
		order.setType("payment");
		order.setUserId(userId);
		Integer count = userDao.selectUserOrderInfo(order);
		orders.add(new UserOrderInfo(count, "payment"));
		order.setType("process");
		count = userDao.selectUserOrderInfo(order);
		orders.add(new UserOrderInfo(count, "process"));
		order.setType("complete");
		count = userDao.selectUserOrderInfo(order);
		orders.add(new UserOrderInfo(count, "complete"));
		order.setType("evaluate");
		count = userDao.selectUserOrderInfo(order);
		orders.add(new UserOrderInfo(count, "evaluate"));

		PurseInfo info = new PurseInfo();
		info.setBrowseCount(browseCount);
		info.setCollectCount(collectCount);
		info.setConcernCount(concernCount);
		info.setOrder(orders);
		if (balance3.isEmpty()) {
			info.setCouponCount(0);
		} else {
			info.setCouponCount(balance3.get(0).getHfBalance());
		}
		if (balance2.isEmpty()) {
			info.setIntegral(0);
		} else {
			info.setIntegral(balance2.get(0).getHfBalance());
		}
		if (balance.isEmpty()) {
			info.setSurplus(0);
		} else {
			info.setSurplus(balance.get(0).getHfBalance());
		}
		if (member.isEmpty()) {
			info.setPrerogative("普通用户");
		} else {
			HfMemberLevel level = hfMemberLevelMapper.selectByPrimaryKey(member.get(0).getLevelId());
			info.setPrerogative(level.getLevelName());
			info.setMember(1);
		}
		return builder.body(ResponseUtils.getResponseBody(info));
	}

	@ApiOperation(value = "查询特权根据用户id", notes = "查询特权根据用户id")
	@RequestMapping(value = "/findMemberPrerogative", method = RequestMethod.GET)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "userId", value = "用户id", required = true, type = "Integer") })
	public ResponseEntity<JSONObject> findMemberPrerogative(Integer userId) throws JSONException {

		BodyBuilder builder = ResponseUtils.getBodyBuilder();

		HfUser hfUser = hfUserMapper.selectByPrimaryKey(userId);
		if (hfUser == null) {
			return builder.body(ResponseUtils.getResponseBody("数据异常"));
		}
		List<HfLevelDescribeInfo> result = new ArrayList<HfLevelDescribeInfo>();
		HfUserPrivilegeExample privilegeExample = new HfUserPrivilegeExample();
		privilegeExample.createCriteria().andUserIdEqualTo(userId);
		List<HfUserPrivilege> privileges = hfUserPrivilegeMapper.selectByExample(privilegeExample);
		List<Integer> privilegeId = privileges.stream().map(HfUserPrivilege::getPrivilegeId)
				.collect(Collectors.toList());
		if (!privilegeId.isEmpty()) {

			HfLevelDescribeExample example = new HfLevelDescribeExample();
			example.createCriteria().andIdIn(privilegeId);
			List<HfLevelDescribe> list = hfLevelDescribleMapper.selectByExample(example);

			for (int i = 0; i < list.size(); i++) {
				HfLevelDescribe describle = list.get(i);
				HfLevelDescribeInfo info = new HfLevelDescribeInfo();
				info.setId(describle.getId());
				info.setLevelId(describle.getLevelId());
				info.setPrerogative(describle.getPrerogative());
				if (LocalDateTime.now().isBefore(describle.getStartTime())
						|| LocalDateTime.now().isAfter(describle.getExpireTime())) {
					info.setPrerogativeState(-1);
					describle.setPrerogativeState(-1);
				}
				if (LocalDateTime.now().isAfter(describle.getStartTime())
						&& LocalDateTime.now().isBefore(describle.getExpireTime())) {
					info.setPrerogativeState(1);
					describle.setPrerogativeState(1);
				}
				info.setStartTime(describle.getStartTime());
				info.setLevelDescribe(describle.getLevelDescribe());
				info.setExpireTime(describle.getExpireTime());
				info.setCreateTime(describle.getCreateTime());
				info.setModifyTime(describle.getModifyTime());
				hfLevelDescribleMapper.updateByPrimaryKey(describle);
				info.setIsDeleted((byte) 0);
				result.add(info);
			}
		}
		return builder.body(ResponseUtils.getResponseBody(result));
	}

	@ApiOperation(value = "账号添加权限", notes = "账号添加权限")
	@RequestMapping(value = "/addJurisdiction", method = RequestMethod.POST)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "LastUser", value = "添加人id", required = true, type = "Integer"),
			@ApiImplicitParam(paramType = "query", name = "userId", value = "被添加人id", required = true, type = "Integer")

	})
	public ResponseEntity<JSONObject> addJurisdiction(Integer userId,String type,String phone,Integer LastUser,Integer BSid) throws JSONException {
		Account account = new Account();
		account.setAccountCode(phone);
//			account.setAccountRole();
		account.setAccountType(type);
		account.setMerchantId(BSid);
		account.setCreateDate(LocalDateTime.now());
		account.setLastModifier(String.valueOf(LastUser));
		account.setModifyDate(LocalDateTime.now());
		account.setUserId(userId);
		account.setIsDeleted(0);
		AccountTypeModelExample accountTypeModelExample = new AccountTypeModelExample();
        accountTypeModelExample.createCriteria().andAccountTypeEqualTo(type).andIsDeletedEqualTo((byte) 0);
        List<AccountTypeModel> accountTypeModels= accountTypeModelMapper.selectByExample(accountTypeModelExample);
        accountTypeModels.forEach(accountTypeModel -> {
            AccountModel accountModel = new AccountModel();
            accountModel.setIdDeleted((byte) 0);
            accountModel.setCreateDate(LocalDateTime.now());
            accountModel.setModifyDate(LocalDateTime.now());
            accountModel.setAccountId(account.getId());
            accountModel.setModelId(accountTypeModel.getModelId());
            accountModelMapper.insertSelective(accountModel);
        });
        BodyBuilder builder = ResponseUtils.getBodyBuilder();


		return builder.body(ResponseUtils.getResponseBody("成功"));
	}
	
	
	@ApiOperation(value = "添加超级账号", notes = "添加超级账号")
	@RequestMapping(value = "/addSup", method = RequestMethod.POST)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "LastUser", value = "添加人id", required = true, type = "Integer"),
//			@ApiImplicitParam(paramType = "query", name = "userId", value = "被添加人id", required = true, type = "Integer"),
			@ApiImplicitParam(paramType = "query", name = "authType", value = "鉴权方式,  1:用户登录, 2:手机号登录 ", required = true, type = "String"),
			@ApiImplicitParam(paramType = "query", name = "authKey", value = "鉴权key", required = false, type = "String"),
//			@ApiImplicitParam(paramType = "query", name = "passwd", value = "密码", required = false, type = "String"),
	})
	public ResponseEntity<JSONObject> addSup(HttpServletRequest request,String type,Integer LastUser,Integer BSid,String authType,String authKey,Integer merchantId) throws JSONException {
		Account account = new Account();
		List<Account> accounts= new ArrayList<>();
		if (request.getServletContext().getAttribute("getServletContextType")!=null&&request.getServletContext().getAttribute("getServletContextType").equals("boss")){
			System.out.println("request.getServletContext().getAttribute得到全局数据："+request.getServletContext().getAttribute("getServletContext"));
			if (request.getServletContext().getAttribute("getServletContext")!=null){
				AccountExample accountExample = new AccountExample();
//				System.out.println(LastUser);
//				System.out.println(request.getServletContext().getAttribute("getServletContext"));
//				System.out.println(request.getServletContext().getAttribute("getServletContextType"));
				accountExample.createCriteria().andUserIdEqualTo(LastUser).andMerchantIdEqualTo((Integer) request.getServletContext().getAttribute("getServletContext")).andAccountTypeEqualTo((String) request.getServletContext().getAttribute("getServletContextType"));
				accounts= accountMapper.selectByExample(accountExample);
				System.out.println(accounts);
				if (accounts.get(0).getAccountRole().equals("Super Admin")&&type.equals("stone")){
					account.setAccountRole("Super Admin");
				}
			}
		} else 	if (request.getServletContext().getAttribute("getServletContextType")!=null&&request.getServletContext().getAttribute("getServletContextType").equals("sass")){
			System.out.println("request.getServletContext().getAttribute得到全局数据："+request.getServletContext().getAttribute("getServletContext"));
			if (request.getServletContext().getAttribute("getServletContext")!=null){
				AccountExample accountExample = new AccountExample();
				accountExample.createCriteria().andUserIdEqualTo(LastUser).andMerchantIdEqualTo((Integer) request.getServletContext().getAttribute("getServletContext")).andAccountTypeEqualTo((String) request.getServletContext().getAttribute("getServletContextType"));
				accounts= accountMapper.selectByExample(accountExample);
				if (accounts.get(0).getAccountRole().equals("Super Admin")&&type.equals("boss")){
					account.setAccountRole("Super Admin");
				}
			}
		}
		BodyBuilder builder = ResponseUtils.getBodyBuilder();

		HfAuthExample example = new HfAuthExample();
		example.createCriteria().andAuthKeyEqualTo(authKey);
		List<HfAuth> list = hfAuthMapper.selectByExample(example);
		Integer userId;
		if (list.isEmpty()) {
			HfUser user = new HfUser();
			if (type.equals("boss")){
				user.setBossId((Integer) request.getServletContext().getAttribute("getServletContext"));
			}else if (type.equals("stone")){
				HfStone hfStone = hfStoneMapper.selectByPrimaryKey((Integer) request.getServletContext().getAttribute("getServletContext"));
				user.setBossId(hfStone.getBossId());
			}
			user.setSourceType(authType);
			user.setPhone(authKey);
			user.setUserStatus("0".getBytes()[0]);
			user.setLastAuthTime(LocalDateTime.now());
			user.setCreateDate(LocalDateTime.now());
			user.setModifyDate(LocalDateTime.now());
			user.setIdDeleted((byte) 0);
			user.setOwnInvitationCode(create());
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
			userId = user.getId();
		} else {
			userId = list.get(0).getUserId();
		}
		AccountExample accountExample = new AccountExample();
		accountExample.createCriteria().andUserIdEqualTo(userId).andAccountTypeEqualTo(type).andMerchantIdEqualTo((Integer) request.getServletContext().getAttribute("getServletContext"));
		if (0 == accountMapper.selectByExample(accountExample).size()){
			account.setAccountCode(authKey);
			account.setAccountType(type);
			if("boss".equals(type)) {
				account.setMerchantId((Integer) request.getServletContext().getAttribute("getServletContext"));
			}else {
				account.setMerchantId(merchantId);
			}
			account.setCreateDate(LocalDateTime.now());
			account.setLastModifier(String.valueOf(LastUser));
			account.setModifyDate(LocalDateTime.now());
			account.setUserId(userId);
			account.setIsDeleted(0);
			if (account.getAccountRole()==null){
				account.setAccountRole(String.valueOf(0));
			}
			accountMapper.insertSelective(account);
			AccountTypeModelExample accountTypeModelExample = new AccountTypeModelExample();
			accountTypeModelExample.createCriteria().andAccountTypeEqualTo(type).andIsDeletedEqualTo((byte) 0);
			List<AccountTypeModel> accountTypeModels= accountTypeModelMapper.selectByExample(accountTypeModelExample);
			accountTypeModels.forEach(accountTypeModel -> {
				AccountModel accountModel = new AccountModel();
				accountModel.setIdDeleted((byte) 0);
				accountModel.setCreateDate(LocalDateTime.now());
				accountModel.setModifyDate(LocalDateTime.now());
				accountModel.setAccountId(account.getId());
				accountModel.setModelId(accountTypeModel.getModelId());
				accountModelMapper.insertSelective(accountModel);
			});
		} else {
			return builder.body(ResponseUtils.getResponseBody("失败"));
		}

		return builder.body(ResponseUtils.getResponseBody("成功"));
	}
	@ApiOperation(value = "状态", notes = "状态")
	@RequestMapping(value = "/state", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> state(HttpServletRequest request) throws JSONException {
		System.out.println(request.getServletContext().getAttribute("getServletContextType"));
		if (request.getServletContext().getAttribute("getServletContextType")!=null&&request.getServletContext().getAttribute("getServletContext")!=null){
			if (request.getServletContext().getAttribute("getServletContextType").equals("boss")){
				HfStoneExample hfStoneExample = new HfStoneExample();
				hfStoneExample.createCriteria().andBossIdEqualTo((Integer) request.getServletContext().getAttribute("getServletContext")).andIsDeletedEqualTo((short) 0);
				List<HfStone> hfStones = hfStoneMapper.selectByExample(hfStoneExample);
				Set<Integer> stoneIds = hfStones.stream().map(a->a.getId()).collect(Collectors.toSet());
				AccountExample accountExample = new AccountExample();
				accountExample.createCriteria().andIsDeletedEqualTo(0).andAccountTypeEqualTo("stone").andMerchantIdIn(Lists.newArrayList(stoneIds));
				List<Account> accounts= accountMapper.selectByExample(accountExample);
				accounts.forEach(account -> {
					if (redisTemplate.opsForValue().get(String.valueOf(account.getUserId()) + account.getAccountType() + "token")!=null){
						System.out.println(account.getAccountCode()+"在线，id:"+account.getId());
					}
				});
			}
//			if (request.getServletContext().getAttribute("getServletContextType").equals("stone")){
//				HfStoneExample hfStoneExample = new HfStoneExample();
//				hfStoneExample.createCriteria().andBossIdEqualTo((Integer) request.getServletContext().getAttribute("getServletContext")).andIsDeletedEqualTo((short) 0);
//				List<HfStone> hfStones = hfStoneMapper.selectByExample(hfStoneExample);
//				Set<Integer> stoneIds = hfStones.stream().map(a->a.getId()).collect(Collectors.toSet());
//				AccountExample accountExample = new AccountExample();
//				accountExample.createCriteria().andIsDeletedEqualTo(0).andAccountTypeEqualTo("stone").andMerchantIdIn(Lists.newArrayList(stoneIds));
//				List<Account> accounts= accountMapper.selectByExample(accountExample);
//				accounts.forEach(account -> {
//					if (redisTemplate.opsForValue().get(String.valueOf(account.getUserId()) + account.getAccountType() + "token")!=null){
//						System.out.println(account.getAccountCode()+"在线，id:"+account.getId());
//					}
//				});
//			}
		}
		BodyBuilder builder = ResponseUtils.getBodyBuilder();
//		AccountExample accountExample = new AccountExample();
//		accountExample.createCriteria().andIsDeletedEqualTo(0);
//		List<Account> accounts= accountMapper.selectByExample(accountExample);

//		if (null != id&& null != type){
//			redisTemplate.delete(String.valueOf(id) + type + "token");
//		}
		return builder.body(ResponseUtils.getResponseBody("成功"));
	}

//	@Scheduled(cron="0/5 * * * * ? ")
//    public void TimeDiscountCoupon()
//            throws Exception {
//		HfUserMemberExample example = new HfUserMemberExample();
//		example.createCriteria().andEndTimeLessThan(LocalDateTime.now());
//		List<HfUserMember> list = hfUserMemberMapper.selectByExample(example);
//		for (int i = 0; i < list.size(); i++) {
//			list.get(i).setUseState(0);
//		}
//        try{
//            Thread.sleep(2000);
//        }catch(Exception e){
//            e.printStackTrace();
//
//        }
//    }

	public static String create() {
		String code = "0123456789qwertyuiopasdfghjklzxcvbnmQWERTYUIZXCVBNM";
		String str = "";
		for (int i = 1; i <= 6; i++) {
			String num = String.valueOf(code.charAt((int) Math.floor(Math.random() * code.length())));
			str += num;
			code = code.replaceAll(num, "");
		}
		return str;
	}
	@ApiOperation(value = "新增sass账号", notes = "新增sass账号")
	@RequestMapping(value = "/AddSass", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> AddSass(Account account,HttpServletResponse response) throws JSONException, NoSuchAlgorithmException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		HfAuthExample hfAuthExample = new HfAuthExample();
		hfAuthExample.createCriteria().andAuthKeyEqualTo(account.getAccountCode()).andIdDeletedEqualTo((byte) 0);
		List<HfAuth> hfAuths = hfAuthMapper.selectByExample(hfAuthExample);
		if (hfAuths.size()!=0){
			AccountExample accountExample = new AccountExample();
			accountExample.createCriteria().andUserIdEqualTo(hfAuths.get(0).getUserId()).andAccountTypeEqualTo("sass").andIsDeletedEqualTo(0);
			List<Account> accountList =  accountMapper.selectByExample(accountExample);
			if (accountList.size()!=0){
				return builder.body(ResponseUtils.getResponseBody("手机号已注册"));
			}
			AccountExample accountExample1 = new AccountExample();
			accountExample1.createCriteria().andUsernameEqualTo(account.getUsername()).andAccountTypeEqualTo("sass").andIsDeletedEqualTo(0);
			List<Account> accountList1 =  accountMapper.selectByExample(accountExample1);
			if (accountList1.size()!=0){
				return builder.body(ResponseUtils.getResponseBody("用户名存在"));
			}
			String encodeStr = DigestUtils.md5Hex(account.getPassword());
			account.setMerchantId(0);
			account.setPassword(encodeStr);
			account.setUserId(hfAuths.get(0).getUserId());
			account.setCreateDate(LocalDateTime.now());
			account.setModifyDate(LocalDateTime.now());
			account.setIsDeleted(0);
			account.setAlreadyUniApp(0);
			account.setAlreadyWeb(0);
			account.setAlreadyMiniProgram(0);
			accountMapper.insertSelective(account);
		} else {
			AccountExample accountExample = new AccountExample();
			accountExample.createCriteria().andUsernameEqualTo(account.getUsername()).andAccountTypeEqualTo("sass").andIsDeletedEqualTo(0);
			List<Account> accountList =  accountMapper.selectByExample(accountExample);
			if (accountList.size()!=0){
				return builder.body(ResponseUtils.getResponseBody("用户名存在"));
			}
			HfUser user = new HfUser();
//			user.setSourceType(authType);
			user.setPhone(account.getAccountCode());
			user.setUserStatus("0".getBytes()[0]);
			user.setLastAuthTime(LocalDateTime.now());
			user.setCreateDate(LocalDateTime.now());
			user.setModifyDate(LocalDateTime.now());
			user.setIdDeleted((byte) 0);
			user.setOwnInvitationCode(create());
			user.setBossId(0);
			hfUserMapper.insert(user);
			String encodeStr = DigestUtils.md5Hex(account.getPassword());
			account.setMerchantId(0);
			account.setPassword(encodeStr);
			account.setUserId(user.getId());
			account.setCreateDate(LocalDateTime.now());
			account.setModifyDate(LocalDateTime.now());
			account.setIsDeleted(0);
			account.setAlreadyUniApp(0);
			account.setAlreadyWeb(0);
			account.setAlreadyMiniProgram(0);
			accountMapper.insertSelective(account);
			HfAuth auth = new HfAuth();
			auth.setAuthKey(account.getAccountCode());
//			auth.setAuthType(authType);
			auth.setUserId(user.getId());
			auth.setAuthStatus((byte) 0);
			auth.setIdDeleted((byte) 0);
			auth.setEncodeType("0");
			auth.setAuthType(String.valueOf(2));
			auth.setCreateDate(LocalDateTime.now());
			auth.setModifyDate(LocalDateTime.now());
			auth.setIdDeleted((byte) 0);
			hfAuthMapper.insertSelective(auth);
		}
		return builder.body(ResponseUtils.getResponseBody("成功"));
	}
	@ApiOperation(value = "修改密码", notes = "修改密码")
	@RequestMapping(value = "/updatePasswd", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> updatePasswd(Integer AccountId,String oldPaddWord,String newPassWord) throws JSONException, NoSuchAlgorithmException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		String encodeStr = DigestUtils.md5Hex(oldPaddWord);
		AccountExample accountExample = new AccountExample();
		accountExample.createCriteria().andIsDeletedEqualTo(0).andIdEqualTo(AccountId).andPasswordEqualTo(encodeStr);
		List<Account> accounts = accountMapper.selectByExample(accountExample);
		if (accounts.size()==0){
			return builder.body(ResponseUtils.getResponseBody("用户名或者密码错误"));
		} else {
			String encodeStr1 = DigestUtils.md5Hex(newPassWord);
			Account account = new Account();
			account.setPassword(encodeStr1);
			accountMapper.updateByExampleSelective(account,accountExample);
		}
		return builder.body(ResponseUtils.getResponseBody("成功"));
	}
	@ApiOperation(value = "操作账号", notes = "操作账号")
	@RequestMapping(value = "/deletedSass", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> deletedSass(Integer AccountId,Integer isDeleted) throws JSONException, NoSuchAlgorithmException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		Account account = new Account();
		account.setId(AccountId);
		account.setIsDeleted(isDeleted);
		accountMapper.updateByPrimaryKeySelective(account);
		return builder.body(ResponseUtils.getResponseBody("成功"));
	}
//	@ApiOperation(value = "修改账号", notes = "修改账号")
//	@RequestMapping(value = "/updateSass", method = RequestMethod.POST)
//	public ResponseEntity<JSONObject> updateSass(Account account) throws JSONException, NoSuchAlgorithmException {
//		BodyBuilder builder = ResponseUtils.getBodyBuilder();
//
//		return builder.body(ResponseUtils.getResponseBody("成功"));
//	}
	@ApiOperation(value = "sass登陆", notes = "sass登陆")
	@RequestMapping(value = "/LoginSass", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> LoginSass(Integer loginType,String username,String password, @RequestParam(name = "authKey", required = false) String authKey,
												@RequestParam(name = "passwd", required = false) Integer passwd) throws JSONException, NoSuchAlgorithmException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		Map map = new HashMap();
		if (loginType==1){
			String encodeStr = DigestUtils.md5Hex(password);
			AccountExample accountExample = new AccountExample();
			accountExample.createCriteria().andIsDeletedEqualTo(0).andUsernameEqualTo(username).andPasswordEqualTo(encodeStr);
			List<Account> accounts = accountMapper.selectByExample(accountExample);
			if (accounts.size()==0){
				return builder.body(ResponseUtils.getResponseBody("账号或密码错误"));
			}
			Encrypt encrypt = new Encrypt();
			String token = encrypt.getToken(true, accounts.get(0).getUserId(), accounts.get(0).getAccountType(),accounts.get(0).getMerchantId());
			HfUser user = hfUserMapper.selectByPrimaryKey(accounts.get(0).getUserId());
			map.put("id", user.getId());
			map.put("phone", user.getPhone());
			map.put("nickName", user.getNickName());
			map.put("realName", user.getRealName());
			map.put("fileId", user.getFileId());
			map.put("identity",accounts.get(0).getAccountType());
			map.put("BSid",accounts.get(0).getMerchantId());
			map.put("token",token);
			map.put("account",accounts.get(0).getId());
			map.put("username",accounts.get(0).getUsername());
			if (token != null) {
				redisTemplate.opsForValue().set(String.valueOf(accounts.get(0).getId()) + "token", token);
				redisTemplate.expire(String.valueOf(accounts.get(0).getId()) + "token", 6000, TimeUnit.SECONDS);
			}
		} else if (loginType==2){
			Jedis jedis = jedisPool.getResource();
			if (passwd == null) {
				return builder.body(ResponseUtils.getResponseBody("还未填写验证码"));
			}
			if (!String.valueOf(passwd).equals(jedis.get(authKey))) {
				return builder.body(ResponseUtils.getResponseBody("验证码不正确"));
			}
			if(jedis != null) {
				jedis.close();
			}
			HfAuthExample example = new HfAuthExample();
			example.createCriteria().andAuthKeyEqualTo(authKey);
			List<HfAuth> list = hfAuthMapper.selectByExample(example);
			Integer userId = 0;
			if (list.isEmpty()) {
				return builder.body(ResponseUtils.getResponseBody("您没有账号请先注册"));
			} else {
				userId = list.get(0).getUserId();
			}
			HfUser user = hfUserMapper.selectByPrimaryKey(userId);
			redisTemplate.opsForValue().set("autologin", authKey);
				map.put("id", user.getId());
				map.put("phone", user.getPhone());
				map.put("nickName", user.getNickName());
				map.put("realName", user.getRealName());
				map.put("fileId", user.getFileId());
				AccountExample accountExample = new AccountExample();
				accountExample.createCriteria().andUserIdEqualTo(user.getId()).andAccountTypeEqualTo("sass").andIsDeletedEqualTo(0);
				List<Account> accounts = accountMapper.selectByExample(accountExample);
			map.put("identity",accounts.get(0).getAccountType());
			map.put("BSid",accounts.get(0).getMerchantId());
			Encrypt encrypt = new Encrypt();
			String token = encrypt.getToken(true, accounts.get(0).getUserId(), accounts.get(0).getAccountType(),accounts.get(0).getMerchantId());
			map.put("token",token);
			map.put("account",accounts.get(0).getId());
			map.put("username",accounts.get(0).getUsername());
		}
		return builder.body(ResponseUtils.getResponseBody(map));
	}
	@ApiOperation(value = "Sass账号信息", notes = "Sass账号信息")
	@RequestMapping(value = "/SassMessage", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> SassMessage(Integer accountId) throws JSONException, NoSuchAlgorithmException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		Account account = accountMapper.selectByPrimaryKey(accountId);
		HfAuthExample hfAuthExample = new HfAuthExample();
		hfAuthExample.createCriteria().andIdDeletedEqualTo((byte) 0).andUserIdEqualTo(account.getUserId());
		List<HfAuth> hfAuth = hfAuthMapper.selectByExample(hfAuthExample);
		HfUser user = hfUserMapper.selectByPrimaryKey(account.getUserId());
		Map map = new HashMap();
		map.put("userId", user.getId());
		map.put("phone", hfAuth.get(0).getAuthKey());
		map.put("nickName", user.getNickName());
		map.put("realName", user.getRealName());
		map.put("fileId", user.getFileId());
		map.put("sumMiniProgram", account.getSumMiniProgram());
		map.put("sumWeb", account.getSumWeb());
		map.put("sumUniApp", account.getSumUniApp());
		map.put("username", account.getUsername());
		map.put("isPerpetual",account.getIsPerpetual());
		if (account.getIsPerpetual()==-1){
			map.put("valid", "永久");
		}else {
			map.put("valid", account.getValid().getYear()+"-"+account.getValid().getMonthValue()+"-"+account.getValid().getDayOfMonth()+" "+account.getValid().getHour()+":"+account.getValid().getMinute()+":"+account.getValid().getSecond());
		}
		return builder.body(ResponseUtils.getResponseBody(map));
	}
	@ApiOperation(value = "修改手机号", notes = "修改手机号")
	@RequestMapping(value = "/updatePhone", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> updatePhone(String authKey,String passwd,Integer accountId) throws JSONException, NoSuchAlgorithmException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		Jedis jedis = jedisPool.getResource();
		if (passwd == null) {
			return builder.body(ResponseUtils.getResponseBody("还未填写验证码"));
		}
		if (!String.valueOf(passwd).equals(jedis.get(authKey))) {
			return builder.body(ResponseUtils.getResponseBody("验证码不正确"));
		}
		if(jedis != null) {
			jedis.close();
		}
		Account account = new Account();
		account.setAccountCode(authKey);
		AccountExample accountExample = new AccountExample();
		accountExample.createCriteria().andIsDeletedEqualTo(0).andIdEqualTo(accountId);
		List<Account> accounts = accountMapper.selectByExample(accountExample);
		accountMapper.updateByExampleSelective(account,accountExample);

			HfAuth hfAuth = new HfAuth();
			hfAuth.setAuthKey(authKey);
			HfAuthExample example1 = new HfAuthExample();
			example1.createCriteria().andUserIdEqualTo(accounts.get(0).getUserId()).andAuthTypeEqualTo(String.valueOf(2));
			hfAuthMapper.updateByExampleSelective(hfAuth,example1);

			HfUser hfUser  = hfUserMapper.selectByPrimaryKey(accounts.get(0).getUserId());
			hfUser.setPhone(authKey);
		    hfUserMapper.updateByPrimaryKeySelective(hfUser);
		return builder.body(ResponseUtils.getResponseBody("成功"));
	}

	@ApiOperation(value = "Sass账号列表", notes = "Sass账号列表")
	@RequestMapping(value = "/SassList", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> SassList(Integer pageNum,Integer pageSize,String NamePhone,String accountAttribute) throws JSONException, NoSuchAlgorithmException {
		        if (pageNum == null) {
            pageNum = 0;
        }
        if (pageSize == null) {
            pageSize = 0;
        }
 BodyBuilder builder = ResponseUtils.getBodyBuilder();
		PageHelper.startPage(pageNum, pageSize);
		AccountExample accountExample = new AccountExample();
		AccountExample.Criteria criteria = accountExample.createCriteria().andAccountTypeEqualTo("sass").andIsDeletedEqualTo(0);
		AccountExample.Criteria criteria2 = accountExample.createCriteria().andAccountTypeEqualTo("sass").andIsDeletedEqualTo(0);
		if (accountAttribute!=null){
			criteria.andAccountAttributeEqualTo(accountAttribute);
		}
		if (NamePhone!=null){
			criteria.andUsernameLike("%"+NamePhone+"%");
			criteria2.andAccountCodeEqualTo(NamePhone);
			accountExample.or(criteria2);
		}

		List<Account> accounts = accountMapper.selectByExample(accountExample);
//		accounts.stream().forEach(a->{
//			if (a.getValid()==null){
//
//			}
//		});
		PageInfo<Account> page = new PageInfo<Account>(accounts);
//		BeanUtils.copyProperties(pageInfo,page);
		return builder.body(ResponseUtils.getResponseBody(page));
	}
	@RequestMapping(value = "/code", method = RequestMethod.GET)
	@ResponseBody
	public void doGet(HttpServletRequest req, HttpServletResponse resp,String uuid){
		// 调用工具类生成的验证码和验证码图片
		Map<String, Object> codeMap = CodeUtil.generateCodeAndPic();

		// 将四位数字的验证码保存到Session中。
		HttpSession session = req.getSession();
		session.setAttribute("code", codeMap.get("code").toString());

		// 禁止图像缓存。
		resp.setHeader("Pragma", "no-cache");
		resp.setHeader("Cache-Control", "no-cache");
		resp.setDateHeader("Expires", -1);
		resp.setHeader("code",codeMap.get("code").toString());
		redisTemplate.opsForValue().set(uuid,codeMap.get("code").toString());
		resp.setContentType("image/jpeg");

		// 将图像输出到Servlet输出流中。
		ServletOutputStream sos;
		try {
			sos = resp.getOutputStream();
			ImageIO.write((RenderedImage) codeMap.get("codePic"), "jpeg", sos);
			sos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@RequestMapping(value = "/codeGit", method = RequestMethod.GET)
	@ResponseBody
	public Object codeGit(String uuid){
		return redisTemplate.opsForValue().get(uuid);
	}
//	@ApiOperation(value = "找回密码", notes = "找回密码")
//	@RequestMapping(value = "/retrievePassword", method = RequestMethod.POST)
//	public ResponseEntity<JSONObject> retrievePassword(String authKey,String passwd,Integer account) throws JSONException, NoSuchAlgorithmException {
//		BodyBuilder builder = ResponseUtils.getBodyBuilder();
//		Jedis jedis = jedisPool.getResource();
//		if (passwd == null) {
//			return builder.body(ResponseUtils.getResponseBody("还未填写验证码"));
//		}
//		if (!String.valueOf(passwd).equals(jedis.get(authKey))) {
//			return builder.body(ResponseUtils.getResponseBody("验证码不正确"));
//		}
//		if(jedis != null) {
//			jedis.close();
//		}
//
//		return builder.body(ResponseUtils.getResponseBody("成功"));
//	}
@Transactional(rollbackFor=Exception.class)
@ApiOperation(value = "添加小程序", notes = "添加小程序")
@RequestMapping(value = "/AddApplet", method = RequestMethod.POST)
public ResponseEntity<JSONObject> AddApplet(String type, String name, @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")LocalDateTime expireTime, int isPerpetual, HttpServletResponse response, String phone, Integer accountId, String domain) throws JSONException, NoSuchAlgorithmException {
	BodyBuilder builder = ResponseUtils.getBodyBuilder();
	Account account2 = accountMapper.selectByPrimaryKey(accountId);
	if (type.equals(TypeDisPlay.Type.APPLET)){
		if (account2.getSumMiniProgram()==0){
			return builder.body(ResponseUtils.getResponseBody("小程序达到上限"));
		} else if (account2.getSumMiniProgram()>0){
			account2.setSumMiniProgram(account2.getSumMiniProgram()-1);
			account2.setAlreadyMiniProgram(account2.getAlreadyMiniProgram()+1);
		}
	}else if (type.equals(TypeDisPlay.Type.WEB)){
		if (account2.getSumWeb()==0){
			return builder.body(ResponseUtils.getResponseBody("网站达到上限"));
		} else if (account2.getSumWeb()>0){
			account2.setSumWeb(account2.getSumWeb()-1);
			account2.setAlreadyWeb(account2.getAlreadyWeb()+1);
		}
	}else if (type.equals(TypeDisPlay.Type.UNIAPP)){
		if (account2.getSumUniApp()==0){
			return builder.body(ResponseUtils.getResponseBody("uni-app达到上限"));
		} else if (account2.getSumUniApp()>0){
			account2.setSumUniApp(account2.getSumUniApp()-1);
			account2.setAlreadyUniApp(account2.getAlreadyUniApp()+1);
		}
	}
	Account account3 = new Account();
	account3.setId(accountId);
	account3.setAlreadyMiniProgram(account2.getAlreadyMiniProgram()+1);
	accountMapper.updateByPrimaryKeySelective(account3);
	HfBoss hfBoss = new HfBoss();
	hfBoss.setName(name);
	hfBoss.setCreateTime(LocalDateTime.now());
	hfBoss.setModifyTime(LocalDateTime.now());
	hfBoss.setIsDeleted((short) 0);
	hfBossMapper.insertSelective(hfBoss);
	HfBossDetails hfBossDetails = new HfBossDetails();
	hfBossDetails.setBossId(hfBoss.getId());
	hfBossDetails.setDetailsType(type);
	hfBossDetails.setOpenAccountId(accountId);
	hfBossDetails.setIsPerpetual((short) isPerpetual);
	hfBossDetails.setExpireTime(expireTime);
	hfBossDetails.setCreateTime(LocalDateTime.now());
	hfBossDetails.setModifyTime(LocalDateTime.now());
	hfBossDetails.setIsDeleted((short) 0);
	hfBossDetails.setDomainName(domain);
	hfBossDetails.setBossName(name);
	hfBossDetailsMapper.insertSelective(hfBossDetails);
	HfAuthExample hfAuthExample = new HfAuthExample();
	hfAuthExample.createCriteria().andAuthKeyEqualTo(phone).andIdDeletedEqualTo((byte) 0);
	List<HfAuth> hfAuths = hfAuthMapper.selectByExample(hfAuthExample);
	if (hfAuths.size()==0){
		HfUser hfUser = new HfUser();
		hfUser.setBossId(0);
		hfUser.setPhone(phone);
		hfUser.setUserStatus((byte) 48);
		hfUser.setRealName("超级管理员");
		hfUser.setNickName("超级管理员");
		hfUser.setModifyDate(LocalDateTime.now());
		hfUser.setCreateDate(LocalDateTime.now());
		hfUser.setIdDeleted((byte) 0);
		hfUserMapper.insertSelective(hfUser);
		HfAuth auth = new HfAuth();
		auth.setAuthKey(phone);
		auth.setAuthType(String.valueOf(2));
		auth.setUserId(hfUser.getId());
		auth.setAuthStatus((byte) 0);
		auth.setIdDeleted((byte) 0);
		auth.setEncodeType("0");
		auth.setCreateDate(LocalDateTime.now());
		auth.setModifyDate(LocalDateTime.now());
		auth.setIdDeleted((byte) 0);
		hfAuthMapper.insert(auth);
		HfBoss hfBoss1 = new HfBoss();
		hfBoss1.setUserId(hfUser.getId());
		HfBossExample hfBossExample = new HfBossExample();
		hfBossExample.createCriteria().andIdEqualTo(hfBoss.getId());
		hfBossMapper.updateByExampleSelective(hfBoss1,hfBossExample);
		Account account = new Account();
		account.setUserId(hfUser.getId());
		account.setAccountCode(phone);
		account.setAccountType("boss");
		account.setAccountRole("Super Admin");
		account.setMerchantId(hfBoss.getId());
		account.setValid(expireTime);
		account.setIsPerpetual(isPerpetual);
		account.setCreateDate(LocalDateTime.now());
		account.setModifyDate(LocalDateTime.now());
		account.setIsDeleted(0);
		account.setLastModifier(String.valueOf(accountId));
		accountMapper.insertSelective(account);
		AccountRoles accountRoles = new AccountRoles();
		accountRoles.setAccountId(account.getId());
		accountRoles.setRolesId(1);
		accountRoles.setCreateTime(LocalDateTime.now());
		accountRoles.setModifyTime(LocalDateTime.now());
		accountRoles.setIsDeleted((short) 0);
		accountRolesMapper.insertSelective(accountRoles);
	}else {
		Account account = new Account();
		account.setUserId(hfAuths.get(0).getUserId());
		account.setAccountCode(phone);
		account.setAccountType("boss");
		account.setAccountRole("Super Admin");
		account.setMerchantId(hfBoss.getId());
		account.setValid(expireTime);
		account.setIsPerpetual(isPerpetual);
		account.setCreateDate(LocalDateTime.now());
		account.setModifyDate(LocalDateTime.now());
		account.setIsDeleted(0);
		account.setLastModifier(String.valueOf(accountId));
		accountMapper.insertSelective(account);
		AccountRoles accountRoles = new AccountRoles();
		accountRoles.setAccountId(account.getId());
		accountRoles.setRolesId(1);
		accountRoles.setCreateTime(LocalDateTime.now());
		accountRoles.setModifyTime(LocalDateTime.now());
		accountRoles.setIsDeleted((short) 0);
		accountRolesMapper.insertSelective(accountRoles);
		HfBoss hfBoss1 = new HfBoss();
		hfBoss1.setUserId(hfAuths.get(0).getUserId());
		HfBossExample hfBossExample = new HfBossExample();
		hfBossExample.createCriteria().andIdEqualTo(hfBoss.getId());
		hfBossMapper.updateByExampleSelective(hfBoss1,hfBossExample);
	}
	return builder.body(ResponseUtils.getResponseBody("成功"));
}
	@ApiOperation(value = "小程序网站app列表", notes = "小程序网站app列表")
	@RequestMapping(value = "/AppletList", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> AppletList(Integer pageNum,Integer pageSize, String Name, String type, int isDeleted) throws JSONException, NoSuchAlgorithmException {
		if (pageNum == null) {
			pageNum = 0;
		}
		if (pageSize == null) {
			pageSize = 0;
		}
		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		HfBossDetailsExample hfBossDetailsExample = new HfBossDetailsExample();
		hfBossDetailsExample.createCriteria().andIsDeletedEqualTo((short) isDeleted).andDetailsTypeEqualTo(type);
		Page page =PageHelper.startPage(pageNum, pageSize);
		List<HfBossDetails> hfBossDetails = hfBossDetailsMapper.selectByExample(hfBossDetailsExample);
		long total = page.getTotal();
//		System.out.println(total+"1111");
		PageInfo<HfBossDetails> pageInfo = new PageInfo<>(hfBossDetails);
		List<BossDetail> bossDetails = new ArrayList<>();
//		System.out.println(hfBossDetails);
		hfBossDetails.forEach(hfBossDetails1 -> {
			HfBossExample hfBossExample = new HfBossExample();
			hfBossExample.createCriteria().andIdEqualTo(hfBossDetails1.getBossId()).andIsDeletedEqualTo((short) 0);
			List<HfBoss> hfBosses = hfBossMapper.selectByExample(hfBossExample);
			AppletName appletName = new AppletName();
			appletName.setName(hfBossDetails1.getBossName());
			HfUser hfUser = hfUserMapper.selectByPrimaryKey(hfBosses.get(0).getUserId());
//			System.out.println(hfUser.getPhone());
			appletName.setPhone(hfUser.getPhone());
			//开户人
			OpenAccount openAccount = new OpenAccount();
			Account account = accountMapper.selectByPrimaryKey(hfBossDetails1.getOpenAccountId());
			openAccount.setType(account.getAccountAttribute());
			openAccount.setPhoneType(account.getAccountCode());
			//数据
			Statistics statistics = new Statistics();
			HfUserExample hfUserExample = new HfUserExample();
			hfUserExample.createCriteria().andIdDeletedEqualTo((byte) 0).andBossIdEqualTo(hfBossDetails1.getBossId());
			List<HfUser> hfUsers= hfUserMapper.selectByExample(hfUserExample);
			statistics.setUserNum(hfUsers.size());
			HfStoneExample hfStoneExample = new HfStoneExample();
			hfStoneExample.createCriteria().andBossIdEqualTo(hfBossDetails1.getBossId()).andIsDeletedEqualTo((short) 0);
			List<HfStone> hfStones = hfStoneMapper.selectByExample(hfStoneExample);
			Set<Integer> stonesId = hfStones.stream().map(a->a.getId()).collect(Collectors.toSet());
//			System.out.println(stonesId);
			if (stonesId.size()!=0){
				HfOrderExample hfOrderExample = new HfOrderExample();
				hfOrderExample.createCriteria().andIdDeletedEqualTo((byte) 0).andStoneIdIn(Lists.newArrayList(stonesId));
				List<HfOrder> hfOrders = hfOrderMapper.selectByExample(hfOrderExample);
				statistics.setOrderNum(hfOrders.size());
			}
			BossDetail bossDetail = new BossDetail();
			bossDetail.setAppletName(appletName);
			bossDetail.setBossId(hfBossDetails1.getBossId());
			bossDetail.setOpenAccount(openAccount);
			bossDetail.setStatistics(statistics);
			if (hfBossDetails1.getIsPerpetual()!=-1){
				bossDetail.setExpireTime(hfBossDetails1.getExpireTime().getYear()+"-"+hfBossDetails1.getExpireTime().getMonthValue()+"-"+hfBossDetails1.getExpireTime().getDayOfMonth()+" "+hfBossDetails1.getExpireTime().getHour()+":"+hfBossDetails1.getExpireTime().getMinute()+":"+hfBossDetails1.getExpireTime().getSecond());
			}
			bossDetail.setIsPerpetual(hfBossDetails1.getIsPerpetual());
			bossDetails.add(bossDetail);
		});
//		PageInfo<BossDetail> pageInfo1 = BeanCopyUtils.copyBean(pageInfo, PageInfo.class);
		PageInfo<BossDetail> page1 = new PageInfo<BossDetail>(bossDetails);
		page1.setTotal(total);
		return builder.body(ResponseUtils.getResponseBody(page1));
	}

	@ApiOperation(value = "小程序编辑", notes = "小程序编辑")
	@RequestMapping(value = "/updateApp", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> updateApp(Integer bossId,String name,@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")LocalDateTime expireTime,int isPerpetual,String type,String domain) throws JSONException, NoSuchAlgorithmException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		HfBossDetails hfBossDetails = new HfBossDetails();
		hfBossDetails.setExpireTime(expireTime);
		hfBossDetails.setBossName(name);
		hfBossDetails.setIsPerpetual((short) isPerpetual);
		hfBossDetails.setDomainName(domain);
		HfBossDetailsExample hfBossDetailsExample = new HfBossDetailsExample();
		hfBossDetailsExample.createCriteria().andBossIdEqualTo(bossId).andDetailsTypeEqualTo(type).andIsDeletedEqualTo((short) 0);
		hfBossDetailsMapper.updateByExampleSelective(hfBossDetails,hfBossDetailsExample);
		return builder.body(ResponseUtils.getResponseBody("成功"));
	}

	@ApiOperation(value = "修改版权", notes = "修改版权")
	@RequestMapping(value = "/updateCopyright", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> updateCopyright(Integer bossId, String type, MultipartFile multipartFile ,String Copyright) throws JSONException, NoSuchAlgorithmException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		try {
			FileMangeService fileMangeService = new FileMangeService();
			String arr[];
			MultipartFile fileInfo = multipartFile;
			arr = fileMangeService.uploadFile(fileInfo.getBytes(), String.valueOf(bossId));
			FileDesc fileDesc = new FileDesc();
			fileDesc.setFileName(fileInfo.getName());
			fileDesc.setGroupName(arr[0]);
			fileDesc.setRemoteFilename(arr[1]);
			fileDesc.setUserId(bossId);
			fileDesc.setCreateTime(LocalDateTime.now());
			fileDesc.setModifyTime(LocalDateTime.now());
			fileDesc.setIsDeleted((short) 0);
			fileDescMapper.insert(fileDesc);
			HfBossDetails hfBossDetails = new HfBossDetails();
			hfBossDetails.setCopyrightFileId(fileDesc.getId());
			hfBossDetails.setCopyrightCharacter(Copyright);
			HfBossDetailsExample hfBossDetailsExample = new HfBossDetailsExample();
			hfBossDetailsExample.createCriteria().andBossIdEqualTo(bossId).andDetailsTypeEqualTo(type).andIsDeletedEqualTo((short) 0);
			hfBossDetailsMapper.updateByExampleSelective(hfBossDetails,hfBossDetailsExample);
		} catch (IOException e) {
			logger.error("add picture failed", e);
		}
		return builder.body(ResponseUtils.getResponseBody("成功"));
	}

	@ApiOperation(value = "操作", notes = "回收1,恢复0,删除2")
	@RequestMapping(value = "/deleted", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> deleted(Integer bossId, String type,int isDeleted) throws JSONException, NoSuchAlgorithmException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		HfBossDetails hfBossDetails = new HfBossDetails();
		hfBossDetails.setIsDeleted((short) isDeleted);
		HfBossDetailsExample hfBossDetailsExample = new HfBossDetailsExample();
		hfBossDetailsExample.createCriteria().andBossIdEqualTo(bossId).andDetailsTypeEqualTo(type);
		hfBossDetailsMapper.updateByExampleSelective(hfBossDetails,hfBossDetailsExample);
		return builder.body(ResponseUtils.getResponseBody("成功"));
	}

	@ApiOperation(value = "小程序迁移", notes = "小程序迁移")
	@RequestMapping(value = "/migration", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> migration(Integer bossId,String type,Integer accountId) throws JSONException, NoSuchAlgorithmException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		HfBossDetails hfBossDetails = new HfBossDetails();
		hfBossDetails.setOpenAccountId(accountId);
		HfBossDetailsExample hfBossDetailsExample = new HfBossDetailsExample();
		hfBossDetailsExample.createCriteria().andBossIdEqualTo(bossId).andDetailsTypeEqualTo(type);
		hfBossDetailsMapper.updateByExampleSelective(hfBossDetails,hfBossDetailsExample);
		return builder.body(ResponseUtils.getResponseBody("成功"));
	}
}
