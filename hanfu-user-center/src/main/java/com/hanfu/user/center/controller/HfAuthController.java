package com.hanfu.user.center.controller;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hanfu.user.center.dao.CancelMapper;
import com.hanfu.user.center.dao.FileDescMapper;
import com.hanfu.user.center.dao.HUserBalanceMapper;
import com.hanfu.user.center.dao.HfAuthMapper;
import com.hanfu.user.center.dao.HfBossMapper;
import com.hanfu.user.center.dao.HfLevelDescribleMapper;
import com.hanfu.user.center.dao.HfMemberLevelMapper;
import com.hanfu.user.center.dao.HfStoneMapper;
import com.hanfu.user.center.dao.HfUserMapper;
import com.hanfu.user.center.dao.HfUserMemberMapper;
import com.hanfu.user.center.dao.hfStoreMenberMapper;
import com.hanfu.user.center.manual.dao.UserDao;
import com.hanfu.user.center.manual.model.HfLevelDescribeInfo;
import com.hanfu.user.center.manual.model.HfMemberLevelInfo;
import com.hanfu.user.center.manual.model.HfUserMemberInfo;
import com.hanfu.user.center.manual.model.StoreUser;
import com.hanfu.user.center.manual.model.UserInfo;
import com.hanfu.user.center.model.CancelExample;
import com.hanfu.user.center.model.HUserBalanceExample;
import com.hanfu.user.center.model.HfAuth;
import com.hanfu.user.center.model.HfAuthExample;
import com.hanfu.user.center.model.HfBoss;
import com.hanfu.user.center.model.HfBossExample;
import com.hanfu.user.center.model.HfLevelDescrible;
import com.hanfu.user.center.model.HfLevelDescribleExample;
import com.hanfu.user.center.model.HfMemberLevel;
import com.hanfu.user.center.model.HfStone;
import com.hanfu.user.center.model.HfStoneExample;
import com.hanfu.user.center.model.HfUser;
import com.hanfu.user.center.model.HfUserExample;
import com.hanfu.user.center.model.HfUserMember;
import com.hanfu.user.center.model.HfUserMemberExample;
import com.hanfu.user.center.model.hfStoreMenber;
import com.hanfu.user.center.model.hfStoreMenberExample;
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
	private HfLevelDescribleMapper hfLevelDescribleMapper;

	@Autowired
	private HfUserMemberMapper hfUserMemberMapper;
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ApiOperation(value = "用户登录", notes = "用户登录")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "authType", value = "鉴权方式,  1:用户登录, 2:手机号登录 ", required = true, type = "String"),
			@ApiImplicitParam(paramType = "query", name = "authKey", value = "鉴权key", required = false, type = "String"),
			@ApiImplicitParam(paramType = "query", name = "passwd", value = "密码", required = false, type = "String"), })
	public ResponseEntity<JSONObject> login(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(name = "authType") String authType, @RequestParam(name = "authKey") String authKey,
			@RequestParam(name = "passwd", required = false) Integer passwd) throws Exception {
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

	public ResponseEntity<JSONObject> addAdminUser(Integer pageNum, Integer pageSize) throws Exception {

		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		if (pageNum == null) {
			pageNum = 0;
		}
		if (pageSize == null) {
			pageSize = 0;
		}

		PageHelper.startPage(pageNum, pageSize);
		List<HfUser> list = hfUserMapper.selectByExample(null);
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

	@RequestMapping(value = "/selectStoneAdmin", method = RequestMethod.GET)
	@ApiOperation(value = "店铺管理员列表", notes = "店铺管理员列表根据商家id")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "bossId", value = "商家id", required = true, type = "Integer") })
	public ResponseEntity<JSONObject> select(Integer bossId) throws Exception {
		ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
		List<StoreUser> storeUsers = new ArrayList<>();
		HfStoneExample example = new HfStoneExample();
		example.createCriteria().andBossIdEqualTo(bossId);
		List<HfStone> list = hfStoneMapper.selectByExample(example);
		list.forEach(hfStoneInfo -> {
			hfStoreMenberExample hfStoreMenbersExample = new hfStoreMenberExample();
			hfStoreMenbersExample.createCriteria().andStoreIdEqualTo(hfStoneInfo.getId())
					.andIsDeletedEqualTo((short) 0);
			List<hfStoreMenber> hfStoreMenber = hfStoreMenberMappers.selectByExample(hfStoreMenbersExample);
//        storeUsers.forEach(storeUser -> {
			hfStoreMenber.forEach(hfStoreMenber1 -> {
				StoreUser storeUser = new StoreUser();
				storeUser.setCreatetime(hfStoreMenber1.getCreateTime());
				storeUser.setIsCancel(hfStoreMenber1.getIsCancel());
				storeUser.setLastModifier(hfStoreMenber1.getLastModifier());
				storeUser.setPhone(hfStoreMenber1.getPhone());
				storeUser.setStoreId(hfStoreMenber1.getStoreId());
				storeUser.setUserId(hfStoreMenber1.getUserId());
				storeUser.setStoreRole(hfStoreMenber1.getStoreRole());
				HfStoneExample hfStoneExample = new HfStoneExample();
				hfStoneExample.createCriteria().andIdEqualTo(hfStoreMenber1.getStoreId())
						.andIsDeletedEqualTo((short) 0);
				List<HfStone> hfStones = hfStoneMapper.selectByExample(hfStoneExample);
				hfStones.forEach(hfStone -> {
					storeUser.setStoreDesc(hfStone.getHfDesc());
					storeUser.setStoreName(hfStone.getHfName());
					storeUser.setStoreAddress(hfStone.getAddress());
				});
				HfBossExample hfBossExample = new HfBossExample();
				hfBossExample.createCriteria().andIdEqualTo(bossId).andIsDeletedEqualTo((short) 0);
				List<HfBoss> hfBosses = hfBossMapper.selectByExample(hfBossExample);
				hfBosses.forEach(hfBoss -> {
					storeUser.setBossName(hfBoss.getName());
					storeUser.setBossId(bossId);
				});

				HfUserExample hfUserExample = new HfUserExample();
				hfUserExample.createCriteria().andIdEqualTo(hfStoreMenber1.getUserId()).andIdDeletedEqualTo((byte) 0);
				List<HfUser> hfUsers = hfUserMapper.selectByExample(hfUserExample);
				storeUser.setUserName(hfUsers.get(0).getRealName());
				storeUser.setRealName(hfUsers.get(0).getNickName());
				storeUser.setUserPhone(hfUsers.get(0).getPhone());
				storeUser.setOwnInvitationCode(hfUsers.get(0).getOwnInvitationCode());

				CancelExample cancelExample = new CancelExample();
//                System.out.println(hfStoreMenber1.getUserId()+"qwqwqwq"+hfStoreMenber1.getIsCancel());
				cancelExample.createCriteria().andUserIdEqualTo(hfStoreMenber1.getUserId()).andIsDeletedEqualTo(0)
						.andIdEqualTo(hfStoreMenber1.getIsCancel());
//                System.out.println(cancelMapper.selectByExample(cancelExample).get(0).getId()+"--------------1");
//                System.out.println(cancelMapper.selectByExample(cancelExample).get(0).getUserId()+"--------------2");
				if (cancelMapper.selectByExample(cancelExample).size() != 0) {
					storeUser.setIsCancel(1);
					storeUser.setCancelId(hfStoreMenber1.getIsCancel());
				} else {
					storeUser.setIsCancel(0);
				}
				storeUsers.add(storeUser);
			});
		});
//        });
		return builder.body(ResponseUtils.getResponseBody(storeUsers));
	}

	@ApiOperation(value = "添加用户会员等级", notes = "添加用户会员等级")
	@RequestMapping(value = "/addUserMemberLevel", method = RequestMethod.POST)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "name", value = "等级名称", required = false, type = "String") })
	public ResponseEntity<JSONObject> addUserMemberLevel(String name) throws JSONException {

		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		HfMemberLevel hfMemberLevel = new HfMemberLevel();
		hfMemberLevel.setLevelName(name);
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
			@ApiImplicitParam(paramType = "query", name = "id", value = "等级id", required = true, type = "Integer") })
	public ResponseEntity<JSONObject> updateUserMemberLevel(String name, Integer id) throws JSONException {

		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		HfMemberLevel hfMemberLevel = hfMemberLevelMapper.selectByPrimaryKey(id);

		if (hfMemberLevel == null) {
			return builder.body(ResponseUtils.getResponseBody("数据异常"));
		}
		if (!StringUtils.isEmpty(name)) {
			hfMemberLevel.setLevelName(name);
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

		if (hfMemberLevel == null) {
			return builder.body(ResponseUtils.getResponseBody("数据异常"));
		}
		hfMemberLevelMapper.deleteByPrimaryKey(id);
		HfLevelDescribleExample example = new HfLevelDescribleExample();
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
			info.setCreateTime(hfMemberLevel.getCreateTime());
			info.setModifyTime(hfMemberLevel.getModifyTime());
			info.setIsDeleted(hfMemberLevel.getIsDeleted());
			result.add(info);
		}
		return builder.body(ResponseUtils.getResponseBody(result));
	}

	@ApiOperation(value = "添加会员", notes = "添加会员")
	@RequestMapping(value = "/addUserMember", method = RequestMethod.POST)
//	Date startTime, Date endTime, HfUserMember hfUserMember,
	public ResponseEntity<JSONObject> addUserMember(Integer levelId, Integer[] userId) throws JSONException {

		BodyBuilder builder = ResponseUtils.getBodyBuilder();
//		Instant instant = startTime.toInstant();
//		ZoneId zoneId = ZoneId.systemDefault();
//		LocalDateTime ldt = instant.atZone(zoneId).toLocalDateTime();
//		Instant instant2 = endTime.toInstant();
//		ZoneId zoneId2 = ZoneId.systemDefault();
//		LocalDateTime ldt2 = instant2.atZone(zoneId2).toLocalDateTime();
		for (int i = 0; i < userId.length; i++) {
			HfUserMember member = new HfUserMember();
			member.setUserId(userId[i]);
			member.setLevelId(levelId);
//			member.setStartTme(ldt);
//			member.setEndTime(ldt2);
			member.setCreateTime(LocalDateTime.now());
			member.setModifyTime(LocalDateTime.now());
			member.setIsDeleted((byte) 0);
			hfUserMemberMapper.insert(member);
		}

		return builder.body(ResponseUtils.getResponseBody(userId.length));
	}

	@ApiOperation(value = "修改会员信息", notes = "修改会员信息")
	@RequestMapping(value = "/updateUserMember", method = RequestMethod.POST)
//	Date startTime, Date endTime, 
	public ResponseEntity<JSONObject> updateUserMember(HfUserMember hfUserMember,
			Integer levelId) throws JSONException {

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
		hfUserMemberMapper.deleteByPrimaryKey(id);
		return builder.body(ResponseUtils.getResponseBody("删除成功"));
	}

	@ApiOperation(value = "查询会员", notes = "查询会员")
	@RequestMapping(value = "/findUserMember", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> findUserMember() throws JSONException {

		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		List<HfUserMember> list = hfUserMemberMapper.selectByExample(null);
		List<HfUserMemberInfo> result = new ArrayList<HfUserMemberInfo>();
		for (int i = 0; i < list.size(); i++) {
			HfUserMember member = list.get(i);
			HfUserMemberInfo info = new HfUserMemberInfo();
			info.setId(member.getId());
			info.setUserId(member.getUserId());
			HfUser hfUser = hfUserMapper.selectByPrimaryKey(member.getUserId());
			if(hfUser != null) {
				if(!StringUtils.isEmpty(hfUser.getRealName())) {
					info.setName(hfUser.getRealName());
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

	@ApiOperation(value = "增加会员等级描述输入框", notes = "增加会员等级描述输入框")
	@RequestMapping(value = "/addMemberLevelDescribe", method = RequestMethod.POST)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "levelId", value = "等级id", required = true, type = "Integer") })
	public ResponseEntity<JSONObject> addMemberLevelDescribe(Integer levelId) throws JSONException {

		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		HfLevelDescrible describle = new HfLevelDescrible();
		describle.setLevelId(levelId);
		describle.setCreateTime(LocalDateTime.now());
		describle.setModifyTime(LocalDateTime.now());
		describle.setIsDeleted((byte) 0);
		hfLevelDescribleMapper.insert(describle);
		return builder.body(ResponseUtils.getResponseBody(describle.getId()));
	}

	@ApiOperation(value="填写等级描述",notes="填写等级描述")
	@RequestMapping(value="/updateMemberLevelDescribe",method=RequestMethod.POST)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "id", value = "id", required = true, type = "Integer"),
		@ApiImplicitParam(paramType = "query", name = "levelDescribe", value = "等级描述", required = true, type = "String")})
	public ResponseEntity<JSONObject> updateMemberLevelDescribe(Integer id,String levelDescribe) throws JSONException {

		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		
		HfLevelDescrible describle = hfLevelDescribleMapper.selectByPrimaryKey(id);
		if(describle == null) {
			return builder.body(ResponseUtils.getResponseBody("数据异常"));
		}
		describle.setModifyTime(LocalDateTime.now());
		describle.setLevelDescribe(levelDescribe);
		hfLevelDescribleMapper.updateByPrimaryKey(describle);
		return builder.body(ResponseUtils.getResponseBody("更新成功"));
	}
	
	@ApiOperation(value="删除等级输入框",notes="删除等级输入框")
	@RequestMapping(value="/deleteMemberLevelDescribe",method=RequestMethod.GET)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "id", value = "id", required = true, type = "Integer")})
	public ResponseEntity<JSONObject> deleteMemberLevelDescribe(Integer id) throws JSONException {

		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		
		HfLevelDescrible describle = hfLevelDescribleMapper.selectByPrimaryKey(id);
		if(describle == null) {
			return builder.body(ResponseUtils.getResponseBody("数据异常"));
		}
		
		hfLevelDescribleMapper.deleteByPrimaryKey(id);
		return builder.body(ResponseUtils.getResponseBody("删除成功"));
	}
	
	@ApiOperation(value="查询等级描述",notes="查询等级描述根据等级")
	@RequestMapping(value="/findMemberLevelDescribe",method=RequestMethod.GET)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "levelId", value = "levelId", required = true, type = "Integer")})
	public ResponseEntity<JSONObject> findMemberLevelDescribe(Integer levelId) throws JSONException {

		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		
		HfMemberLevel hfMemberLevel = hfMemberLevelMapper.selectByPrimaryKey(levelId);
		if(hfMemberLevel == null) {
			return builder.body(ResponseUtils.getResponseBody("数据异常"));
		}
		HfLevelDescribleExample example = new HfLevelDescribleExample();
		example.createCriteria().andLevelIdEqualTo(levelId);
		List<HfLevelDescrible> list = hfLevelDescribleMapper.selectByExample(example);
		List<HfLevelDescribeInfo> result = new ArrayList<HfLevelDescribeInfo>();
		for (int i = 0; i < list.size(); i++) {
			HfLevelDescrible describle = list.get(i);
			HfLevelDescribeInfo info = new HfLevelDescribeInfo();
			info.setId(describle.getId());
			info.setLevelId(describle.getLevelId());
			info.setLevelDescribe(describle.getLevelDescribe());
			info.setCreateTime(describle.getCreateTime());
			info.setModifyTime(describle.getModifyTime());
			info.setIsDeleted((byte) 0);
			result.add(info);
		}
		
		return builder.body(ResponseUtils.getResponseBody(result));
	}

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
