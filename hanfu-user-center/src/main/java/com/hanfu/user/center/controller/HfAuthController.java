package com.hanfu.user.center.controller;

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
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.hanfu.user.center.dao.*;
import com.hanfu.user.center.model.*;
import com.hanfu.user.center.utils.Decrypt;
import com.hanfu.user.center.utils.Encrypt;
import org.apache.curator.shaded.com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.alibaba.fastjson.JSONObject;
import com.cedarsoftware.util.io.JsonObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hanfu.inner.model.product.center.HfOrders;
import com.hanfu.user.center.manual.dao.HfBossInfoDao;
import com.hanfu.user.center.manual.dao.UserDao;
import com.hanfu.user.center.manual.model.HfBossInfo;
import com.hanfu.user.center.manual.model.HfLevelDescribeInfo;
import com.hanfu.user.center.manual.model.HfMemberLevelInfo;
import com.hanfu.user.center.manual.model.HfUserMemberInfo;
import com.hanfu.user.center.manual.model.Order;
import com.hanfu.user.center.manual.model.PurseInfo;
import com.hanfu.user.center.manual.model.StoreUser;
import com.hanfu.user.center.manual.model.UserInfo;
import com.hanfu.user.center.manual.model.UserOrderInfo;
import com.hanfu.user.center.request.UserInfoRequest;
import com.hanfu.user.center.response.handler.UserNotExistException;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseUtils;
import com.hanfu.utils.response.handler.ResponseEntity.BodyBuilder;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

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
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ApiOperation(value = "用户登录", notes = "用户登录")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "authType", value = "鉴权方式,  1:用户登录, 2:手机号登录 ", required = true, type = "String"),
			@ApiImplicitParam(paramType = "query", name = "authKey", value = "鉴权key", required = false, type = "String"),
			@ApiImplicitParam(paramType = "query", name = "passwd", value = "密码", required = false, type = "String"), })
	public ResponseEntity<JSONObject> login(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(name = "authType") String authType, @RequestParam(name = "authKey") String authKey,
			@RequestParam(name = "passwd", required = false) Integer passwd,String type) throws Exception {
		Integer userId = null;

		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		if (passwd == null) {
			return builder.body(ResponseUtils.getResponseBody("还未填写验证码"));
		}
		if (!String.valueOf(passwd).equals(redisTemplate.opsForValue().get(authKey))) {
			return builder.body(ResponseUtils.getResponseBody("验证码不正确"));
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
		Encrypt encrypt = new Encrypt();
		String token = encrypt.getToken(true, user.getId(), type);
		System.out.println(token);
		response.addHeader("token",token);
		Map map = new HashMap();
		if (type!=null){
			map.put("id",user.getId());
			map.put("phone",user.getPhone());
			map.put("nickName",user.getNickName());
			map.put("realName",user.getRealName());
			map.put("fileId",user.getFileId());
			AccountExample accountExample = new AccountExample();
			accountExample.createCriteria().andAccountTypeEqualTo(type).andUserIdEqualTo(user.getId()).andIsDeletedEqualTo(0);
			List<Account> accounts= accountMapper.selectByExample(accountExample);
			if (accounts.size()==0){
				response.sendError(HttpStatus.FORBIDDEN.value(), "无此权限");
			}
			AccountModelExample accountModelExample = new AccountModelExample();
			accountModelExample.createCriteria().andAccountIdEqualTo(accounts.get(0).getId()).andIdDeletedEqualTo((byte) 0);
			List<AccountModel> accountModels= accountModelMapper.selectByExample(accountModelExample);
			Set<Integer> set =accountModels.stream().map(a->a.getModelId()).collect(Collectors.toSet());
			HfModuleExample hfModuleExample = new HfModuleExample();
			hfModuleExample.createCriteria().andIdIn(Lists.newArrayList(set)).andIsDeletedEqualTo((byte) 0);
			List<HfModule> hfModules= hfModuleMapper.selectByExample(hfModuleExample);
			Set<String> model = hfModules.stream().map(a->a.getHfModel()).collect(Collectors.toSet());
			map.put("model",model);
			Map<String,String> modelCode = hfModules.stream().collect(Collectors.toMap(HfModule::getModelCode,HfModule::getModelCode));
			map.put("modelCode",modelCode);
			map.put("token",token);
			return builder.body(ResponseUtils.getResponseBody(map));
		}

		return builder.body(ResponseUtils.getResponseBody(user));
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

	public ResponseEntity<JSONObject> addAdminUser(Integer pageNum, Integer pageSize ,String phone,String code,String name) throws Exception {
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
	public ResponseEntity<JSONObject> findBossInfo(Integer bossId) throws Exception {
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
	public ResponseEntity<JSONObject> select(Integer bossId, String phone, String code, String name) throws Exception {
		ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
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
			@ApiImplicitParam(paramType = "query", name = "amount", value = "金额", required = false, type = "String")})
	public ResponseEntity<JSONObject> addUserMemberLevel(String name, Integer level, String levelDescribe ,String amount)
			throws JSONException {

		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		
		HfMemberLevelExample example = new HfMemberLevelExample();
		example.createCriteria().andLevelEqualTo(level);
		
		List<HfMemberLevel> list = hfMemberLevelMapper.selectByExample(example);
		if(!list.isEmpty()) {
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
		hfMemberLevelMapper.insert(hfMemberLevel);
		return builder.body(ResponseUtils.getResponseBody(hfMemberLevel.getId()));
	}

	@ApiOperation(value = "修改用户会员等级", notes = "修改用户会员等级")
	@RequestMapping(value = "/updateUserMemberLevel", method = RequestMethod.POST)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "name", value = "等级名称", required = false, type = "String"),
			@ApiImplicitParam(paramType = "query", name = "id", value = "等级id", required = true, type = "Integer"),
			@ApiImplicitParam(paramType = "query", name = "level", value = "等级", required = false, type = "Integer"),
			@ApiImplicitParam(paramType = "query", name = "levelDescribe", value = "描述", required = false, type = "String"),
			@ApiImplicitParam(paramType = "query", name = "amount", value = "金额", required = false, type = "String")})
	public ResponseEntity<JSONObject> updateUserMemberLevel(@RequestParam(required = false) String name, Integer id,
			@RequestParam(required = false) Integer level, @RequestParam(required = false) String levelDescribe,
			@RequestParam(required = false) String amount)
			throws JSONException {

		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		HfMemberLevel hfMemberLevel = hfMemberLevelMapper.selectByPrimaryKey(id);

		if (hfMemberLevel == null) {
			return builder.body(ResponseUtils.getResponseBody("数据异常"));
		}
		
		if (!StringUtils.isEmpty(level)) {
			
			HfMemberLevelExample example = new HfMemberLevelExample();
			example.createCriteria().andLevelEqualTo(level);
			
			List<HfMemberLevel> list = hfMemberLevelMapper.selectByExample(example);
			if(!list.isEmpty()) {
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
	public ResponseEntity<JSONObject> findUserMemberLevel() throws JSONException {

		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		List<HfMemberLevel> list = hfMemberLevelMapper.selectByExample(null);
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
				return o1.getLevel()-o2.getLevel();
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
	public ResponseEntity<JSONObject> findUserMember(Integer pageNum, Integer pageSize ,String phone,String code,String name
			, Integer levelId) throws JSONException {

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
		user.setId(levelId);
		PageHelper.startPage(pageNum, pageSize);
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
		orders.add(new UserOrderInfo(count,"payment"));
		order.setType("process");
		count = userDao.selectUserOrderInfo(order);
		orders.add(new UserOrderInfo(count,"process"));
		order.setType("complete");
		count = userDao.selectUserOrderInfo(order);
		orders.add(new UserOrderInfo(count,"complete"));
		order.setType("evaluate");
		count = userDao.selectUserOrderInfo(order);
		orders.add(new UserOrderInfo(count,"evaluate"));
		
		PurseInfo info = new PurseInfo();
		info.setBrowseCount(browseCount);
		info.setCollectCount(collectCount);
		info.setConcernCount(concernCount);
		info.setOrder(orders);
		if(balance3.isEmpty()) {
			info.setCouponCount(0);
		}else {
			info.setCouponCount(balance3.get(0).getHfBalance());
		}
		if(balance2.isEmpty()) {
			info.setIntegral(0);
		}else {
			info.setIntegral(balance2.get(0).getHfBalance());
		}
		if(balance.isEmpty()) {
			info.setSurplus(0);
		}else {
			info.setSurplus(balance.get(0).getHfBalance());
		}
		if(member.isEmpty()) {
			info.setPrerogative("普通用户");
		}else {
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

}
