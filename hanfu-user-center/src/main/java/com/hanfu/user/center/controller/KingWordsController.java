package com.hanfu.user.center.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
//import com.github.pagehelper.PageHelper;
//import com.github.pagehelper.PageInfo;
import com.hanfu.common.service.FileMangeService;
//import com.hanfu.user.center.config.PermissionConstants;
import com.hanfu.user.center.config.WxLoginConfig;
import com.hanfu.user.center.dao.ActivityUserMapper;
import com.hanfu.user.center.dao.FileDescMapper;
import com.hanfu.user.center.dao.HfAuthMapper;
import com.hanfu.user.center.dao.HfBossMapper;
import com.hanfu.user.center.dao.HfMessageApplyMapper;
import com.hanfu.user.center.dao.HfMessageInfoMapper;
import com.hanfu.user.center.dao.HfMessageInstanceMapper;
import com.hanfu.user.center.dao.HfMessageTemplateMapper;
import com.hanfu.user.center.dao.HfTemplateParamMapper;
import com.hanfu.user.center.dao.HfUserMapper;
import com.hanfu.user.center.manual.dao.UserDao;
import com.hanfu.user.center.manual.model.MessageApply;
import com.hanfu.user.center.manual.model.MessageType;
import com.hanfu.user.center.manual.model.MessageType.MessageContentTypeEnum;
import com.hanfu.user.center.manual.model.MessageType.MessageTypeEnum;
//import com.hanfu.user.center.manual.model.ActivityUserInfo;
import com.hanfu.user.center.manual.model.UserInfo;
//import com.hanfu.user.center.manual.model.UserQuery;
//import com.hanfu.user.center.manual.model.test;
import com.hanfu.user.center.model.*;
import com.hanfu.user.center.request.UserInfoRequest;
import com.hanfu.user.center.response.handler.AuthKeyIsExistException;
import com.hanfu.user.center.response.handler.ParamInvalidException;
import com.hanfu.user.center.utils.GetMessageCode;
import com.hanfu.user.center.utils.Message;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseEntity.BodyBuilder;
import com.hanfu.utils.response.handler.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
//import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.*;
import java.security.spec.InvalidParameterSpecException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

//import com.hanfu.user.center.service.UserCenterService;

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
	// @Autowired
//	private UserCenterService userCenterService;
	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	@Autowired
	HfAuthMapper hfAuthMapper;
	@Autowired
	UserDao userDao;
	@Autowired
	private HfMessageInfoMapper hfMessageInfoMapper;
	@Autowired
	private HfMessageTemplateMapper hfMessageTemplateMapper;
	@Autowired
	private HfMessageApplyMapper hfMessageApplyMapper;
	@Autowired
	private HfTemplateParamMapper hfTemplateParamMapper;
	@Autowired
	JedisPool jedisPool;
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private HfMessageInstanceMapper hfMessageInstanceMapper;
	@Autowired
	private JavaMailSender javaMailSender;
	@Autowired
	private HfBossMapper hfBossMapper;
	@Autowired
	private ActivityUserMapper activityUserMapper;
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	@ApiOperation(value = "用户登录", notes = "用户登录")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "authType", value = "鉴权方式,  1:用户登录, 2:手机号登录 ", required = true, type = "String"),
			@ApiImplicitParam(paramType = "query", name = "authKey", value = "鉴权key", required = false, type = "String"),
			@ApiImplicitParam(paramType = "query", name = "passwd", value = "密码", required = false, type = "String"), })
	public ResponseEntity<JSONObject> login(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(name = "authType") String authType, @RequestParam(name = "authKey") String authKey,
			@RequestParam(name = "passwd") Integer passwd) throws Exception {
		Cookie cookie = new Cookie("autologin", authKey);
		response.addCookie(cookie);

		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		HfAuth hfAuth = userDao.selectAuthList(authKey);
		if (hfAuth == null) {
			return builder.body(ResponseUtils.getResponseBody("还未注册"));
		}

		if (redisTemplate.opsForValue().get(String.valueOf(hfAuth.getUserId())) == null) {
			String token = "_" + UUID.randomUUID().toString().replaceAll("-", "");
			redisTemplate.opsForValue().set(String.valueOf(hfAuth.getUserId()), token);
		} else {
			return builder.body(ResponseUtils.getResponseBody("1"));
		}
		if (!passwd.equals(redisTemplate.opsForValue().get(authKey))) {
			return builder.body(ResponseUtils.getResponseBody("验证码不正确"));
		}

		return builder.body(ResponseUtils.getResponseBody("成功"));
	}

	@RequestMapping(path = "/addTemplateParam", method = RequestMethod.POST)
	@ApiOperation(value = "添加信息模板参数", notes = "添加信息模板参数")
	public ResponseEntity<JSONObject> addTemplateParam(String type, String param, String name) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		HfTemplateParamExample example = new HfTemplateParamExample();
		example.createCriteria().andTypeEqualTo(type).andParamNameEqualTo(param);
		List<HfTemplateParam> list = hfTemplateParamMapper.selectByExample(example);
		Integer id = null;
		if (CollectionUtils.isEmpty(list)) {
			StringBuilder stringBuilder = new StringBuilder();
			HfTemplateParam templateParam = new HfTemplateParam();
			templateParam.setType(type);
			stringBuilder.append("$");
			stringBuilder.append(param);
			stringBuilder.append("$");
			templateParam.setName(name);
			templateParam.setParamName(stringBuilder.toString());
			templateParam.setCreateTime(LocalDateTime.now());
			templateParam.setModifyTime(LocalDateTime.now());
			templateParam.setIsDeleted((byte) 0);
			hfTemplateParamMapper.insert(templateParam);
			id = templateParam.getId();
		} else {
			id = list.get(0).getId();
		}
		return builder.body(ResponseUtils.getResponseBody(id));
	}

	@RequestMapping(path = "/getTemplateParam", method = RequestMethod.GET)
	@ApiOperation(value = "查询类型参数", notes = "查询类型参数")
	public ResponseEntity<JSONObject> getTemplateParam(String type) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		HfTemplateParamExample example = new HfTemplateParamExample();
		example.createCriteria().andTypeEqualTo(type);
		List<HfTemplateParam> list = hfTemplateParamMapper.selectByExample(example);
		return builder.body(ResponseUtils.getResponseBody(list));
	}

	@RequestMapping(path = "/getMessageType", method = RequestMethod.POST)
	@ApiOperation(value = "查询消息类型", notes = "查询消息类型")
	public ResponseEntity<JSONObject> getMessageType() throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		List<String> str = new ArrayList<String>();
		for (MessageTypeEnum item : MessageTypeEnum.values()) {
			str.add(item.getMessageType());
		}
		return builder.body(ResponseUtils.getResponseBody(str));
	}

	@RequestMapping(path = "/getMessageContent", method = RequestMethod.GET)
	@ApiOperation(value = "查询是否发送", notes = "查询消息内容")
	public ResponseEntity<JSONObject> getMessageContent(String messageType, String contentType,
			HttpServletRequest request) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		Integer bossId = null;
		Integer infoId = null;
		HfMessageTemplate messageTemplate = null;
		if (request.getServletContext().getAttribute("getServletContext") != null) {
			if (request.getServletContext().getAttribute("getServletContextType").equals("boss")) {
				bossId = (Integer) request.getServletContext().getAttribute("getServletContext");
			}
		}
		HfMessageInfoExample example = new HfMessageInfoExample();
		example.createCriteria().andBossIdEqualTo(bossId).andTypeEqualTo(messageType);
		List<HfMessageInfo> list = hfMessageInfoMapper.selectByExample(example);
		if (!CollectionUtils.isEmpty(list)) {
			infoId = list.get(0).getId();
		}
		if (infoId != null) {
			HfMessageTemplateExample templateExample = new HfMessageTemplateExample();
			templateExample.createCriteria().andTypeEqualTo(contentType).andMessageIdEqualTo(infoId);
			List<HfMessageTemplate> list2 = hfMessageTemplateMapper.selectByExample(templateExample);
			if (!CollectionUtils.isEmpty(list2)) {
				messageTemplate = list2.get(0);
			}
		}
		return builder.body(ResponseUtils.getResponseBody(messageTemplate));
	}
	
	@RequestMapping(path = "/getMessageInstanceList", method = RequestMethod.GET)
	@ApiOperation(value = "查询消息实体列表", notes = "查询消息实体列表")
	public ResponseEntity<JSONObject> getMessageInstanceList(String messageType, String contentType,
			HttpServletRequest request) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		Integer bossId = null;
		Integer infoId = null;
		HfMessageTemplate messageTemplate = null;
		if (request.getServletContext().getAttribute("getServletContext") != null) {
			if (request.getServletContext().getAttribute("getServletContextType").equals("boss")) {
				bossId = (Integer) request.getServletContext().getAttribute("getServletContext");
			}
		}
		HfMessageInfoExample example = new HfMessageInfoExample();
		example.createCriteria().andBossIdEqualTo(bossId).andTypeEqualTo(messageType);
		List<HfMessageInfo> list = hfMessageInfoMapper.selectByExample(example);
		if (!CollectionUtils.isEmpty(list)) {
			infoId = list.get(0).getId();
		}
		if (infoId != null) {
			HfMessageTemplateExample templateExample = new HfMessageTemplateExample();
			templateExample.createCriteria().andTypeEqualTo(contentType).andMessageIdEqualTo(infoId);
			List<HfMessageTemplate> list2 = hfMessageTemplateMapper.selectByExample(templateExample);
			if (!CollectionUtils.isEmpty(list2)) {
				messageTemplate = list2.get(0);
			}
		}
		HfMessageInstanceExample instanceExample = new HfMessageInstanceExample();
		instanceExample.createCriteria().andTemplateTypeIdEqualTo(messageTemplate.getId());
		List<HfMessageInstance> result = hfMessageInstanceMapper.selectByExample(instanceExample);
		return builder.body(ResponseUtils.getResponseBody(result));
	}
	
	@RequestMapping(path = "/updateMessageInstance", method = RequestMethod.GET)
	@ApiOperation(value = "消息实体具体选择哪个", notes = "消息实体具体选择哪个")
	public ResponseEntity<JSONObject> updateMessageInstance(String messageType, String contentType,
			HttpServletRequest request, Integer id) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		Integer bossId = null;
		Integer infoId = null;
		HfMessageTemplate messageTemplate = null;
		if (request.getServletContext().getAttribute("getServletContext") != null) {
			if (request.getServletContext().getAttribute("getServletContextType").equals("boss")) {
				bossId = (Integer) request.getServletContext().getAttribute("getServletContext");
			}
		}
		HfMessageInfoExample example = new HfMessageInfoExample();
		example.createCriteria().andBossIdEqualTo(bossId).andTypeEqualTo(messageType);
		List<HfMessageInfo> list = hfMessageInfoMapper.selectByExample(example);
		if (!CollectionUtils.isEmpty(list)) {
			infoId = list.get(0).getId();
		}
		if (infoId != null) {
			HfMessageTemplateExample templateExample = new HfMessageTemplateExample();
			templateExample.createCriteria().andTypeEqualTo(contentType).andMessageIdEqualTo(infoId);
			List<HfMessageTemplate> list2 = hfMessageTemplateMapper.selectByExample(templateExample);
			if (!CollectionUtils.isEmpty(list2)) {
				messageTemplate = list2.get(0);
			}
		}
		HfMessageInstanceExample instanceExample = new HfMessageInstanceExample();
		instanceExample.createCriteria().andTemplateTypeIdEqualTo(messageTemplate.getId());
		List<HfMessageInstance> result = hfMessageInstanceMapper.selectByExample(instanceExample);
		List<Integer> instanceId = result.stream().map(HfMessageInstance::getId).collect(Collectors.toList());
		instanceExample.clear();
		instanceExample.createCriteria().andIdIn(instanceId);
		HfMessageInstance record = new HfMessageInstance();
		record.setIsDeleted((byte) 0);
		hfMessageInstanceMapper.updateByExampleSelective(record, instanceExample);
		HfMessageInstance instance = hfMessageInstanceMapper.selectByPrimaryKey(id);
		instance.setIsDeleted((byte) 1);
		hfMessageInstanceMapper.updateByPrimaryKey(instance);
		return builder.body(ResponseUtils.getResponseBody(instance.getId()));
	}
	
	
	@RequestMapping(path = "/updateIsUse", method = RequestMethod.GET)
	@ApiOperation(value = "修改是否发送", notes = "修改是否发送")
	public ResponseEntity<JSONObject> updateIsUse(Integer id, Byte isUse) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		HfMessageTemplate messageTemplate = hfMessageTemplateMapper.selectByPrimaryKey(id);
		messageTemplate.setIsDeleted(isUse);
		hfMessageTemplateMapper.updateByPrimaryKey(messageTemplate);
		return builder.body(ResponseUtils.getResponseBody(messageTemplate.getId()));
	}

	@RequestMapping(path = "/getMessageContentType", method = RequestMethod.GET)
	@ApiOperation(value = "查询消息内容类型", notes = "查询消息内容类型")
	public ResponseEntity<JSONObject> getMessageContentType() throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		List<String> str = new ArrayList<String>();
		for (MessageContentTypeEnum item : MessageContentTypeEnum.values()) {
			str.add(item.getMessageContentType());
		}
		return builder.body(ResponseUtils.getResponseBody(str));
	}
	
	@RequestMapping(path = "/getMessageApplyList", method = RequestMethod.GET)
	@ApiOperation(value = "查询消息申请列表", notes = "查询消息申请列表")
	public ResponseEntity<JSONObject> getMessageApplyList() throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		List<HfMessageApply> list = hfMessageApplyMapper.selectByExample(null);
		List<MessageApply> result = new ArrayList<MessageApply>();
		for (int i = 0; i < list.size(); i++) {
			HfMessageApply apply = list.get(i);
			HfBoss boss = hfBossMapper.selectByPrimaryKey(apply.getBossId());
			MessageApply messageApply = new MessageApply();
			messageApply.setId(apply.getId());
			messageApply.setBossName(boss.getName());
			messageApply.setContent(apply.getContent());
			messageApply.setContentType(apply.getContentType());
			messageApply.setCreateTime(apply.getCreateTime());
			messageApply.setInstaceId(apply.getMessageInstanceId());
			messageApply.setStatus(apply.getStatus());
			result.add(messageApply);
		}
		return builder.body(ResponseUtils.getResponseBody(result));
	}
	
	@RequestMapping(path = "/agreeMessageApply", method = RequestMethod.POST)
	@ApiOperation(value = "同意申请的消息", notes = "同意申请的消息")
	public ResponseEntity<JSONObject> agreeMessageApply(Integer applyId, String templateId) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		HfMessageApply apply = hfMessageApplyMapper.selectByPrimaryKey(applyId);
		apply.setStatus(2);
		hfMessageApplyMapper.updateByPrimaryKey(apply);
		HfMessageInstance instance = hfMessageInstanceMapper.selectByPrimaryKey(apply.getMessageInstanceId());
		instance.setStatus(2);
		instance.setTemplateId(templateId);
		hfMessageInstanceMapper.updateByPrimaryKey(instance);
		return builder.body(ResponseUtils.getResponseBody(instance.getId()));
	}
	
	@RequestMapping(path = "/refuseMessageApply", method = RequestMethod.POST)
	@ApiOperation(value = "拒绝申请的消息", notes = "拒绝申请的消息")
	public ResponseEntity<JSONObject> refuseMessageApply(Integer applyId, String refuseReason) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		HfMessageApply apply = hfMessageApplyMapper.selectByPrimaryKey(applyId);
		apply.setStatus(2);
		hfMessageApplyMapper.updateByPrimaryKey(apply);
		HfMessageInstance instance = hfMessageInstanceMapper.selectByPrimaryKey(apply.getMessageInstanceId());
		instance.setStatus(3);
		instance.setRefuseReason(refuseReason);
		hfMessageInstanceMapper.updateByPrimaryKey(instance);
		return builder.body(ResponseUtils.getResponseBody(instance.getId()));
	}
	
	@RequestMapping(path = "/getRefuse", method = RequestMethod.GET)
	@ApiOperation(value = "查询拒绝原因", notes = "查询拒绝原因")
	public ResponseEntity<JSONObject> getRefuse(Integer id) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		HfMessageInstance instance = hfMessageInstanceMapper.selectByPrimaryKey(id);
		return builder.body(ResponseUtils.getResponseBody(instance));
	}

	@RequestMapping(path = "/cs", method = RequestMethod.POST)
	@ApiOperation(value = "添加信息模板", notes = "添加信息模板")
	public ResponseEntity<JSONObject> cs() throws Exception {
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
		map.add("username", "孙旺达");
		map.add("orderId", "e28c4a93fb7b4084");
		map.add("total", "500");
		map.add("userId", "1065");
		map.add("type", "orderCreate");
		map.add("bossId", 1);
		restTemplate.postForObject("http://localhost:8082/user/Message", map, JSONObject.class);
		return null;
	}
	
	
	@RequestMapping(path = "/code", method = RequestMethod.POST)
	@ApiOperation(value = "发送验证码", notes = "发送验证码")
	public ResponseEntity<JSONObject> code(@RequestParam String phone) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		if (!StringUtils.isEmpty(phone)) {
			String s2 = "^[1](([3|5|8][\\d])|([4][4,5,6,7,8,9])|([6][2,5,6,7])|([7][^9])|([9][1,8,9]))[\\d]{8}$";
			Pattern p = Pattern.compile(s2);
			Matcher m = p.matcher(phone);
			boolean b = m.matches();
			if (b) {
				Integer code = Message.sendSms(phone);
				Jedis jedis = new Jedis();
				jedis = jedisPool.getResource();
				jedis.set(phone, String.valueOf(code));
				jedis.expire(phone, 300);
				if (jedis != null) {
					jedis.close();
				}
				return builder.body(ResponseUtils.getResponseBody(code));
			}
			return builder.body(ResponseUtils.getResponseBody("手机号有误"));
		} else {
			return builder.body(ResponseUtils.getResponseBody("请输入手机号"));
		}
	}

	@RequestMapping(path = "/Message", method = RequestMethod.POST)
	@ApiOperation(value = "发送验证码", notes = "发送验证码")
	public ResponseEntity<JSONObject> Message(@RequestParam Map<String, String> map) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		String s = map.get("bossId");
		Integer bossId = s == null ? null:Integer.valueOf(s);
		String type;
		if ("login".equals(type = map.get("type"))) {
			bossId = 1;
		}
		HfMessageInfoExample example = new HfMessageInfoExample();
		example.createCriteria().andBossIdEqualTo(bossId).andIsDeletedEqualTo((byte) 1);
		List<HfMessageInfo> list = hfMessageInfoMapper.selectByExample(example);
		if (CollectionUtils.isEmpty(list)) {
			return builder.body(ResponseUtils.getResponseBody(-1));
		}
		List<Integer> infoId = list.stream().map(HfMessageInfo::getId).collect(Collectors.toList());

		HfMessageTemplateExample example2 = new HfMessageTemplateExample();
		example2.createCriteria().andMessageIdIn(infoId).andTypeEqualTo(type).andIsDeletedEqualTo((byte) 1);
		List<HfMessageTemplate> list2 = hfMessageTemplateMapper.selectByExample(example2);
		if (CollectionUtils.isEmpty(list2)) {
			return builder.body(ResponseUtils.getResponseBody(-1));
		}
		List<Integer> templateId = list2.stream().map(HfMessageTemplate::getId).collect(Collectors.toList());
		HfMessageInstanceExample example3 = new HfMessageInstanceExample();
		example3.createCriteria().andTemplateTypeIdIn(templateId).andIsDeletedEqualTo((byte) 1).andStatusEqualTo(2);
		List<HfMessageInstance> list3 = hfMessageInstanceMapper.selectByExample(example3);
		if (CollectionUtils.isEmpty(list3)) {
			return builder.body(ResponseUtils.getResponseBody(-1));
		}
		String phone = "";
		String userId = map.get("userId");
		if (StringUtils.isEmpty(userId)) {
			phone = map.get("phone");
		} else {
			HfUser hfUser = hfUserMapper.selectByPrimaryKey(Integer.valueOf(userId));
			phone = hfUser.getPhone();
			map.put("username", hfUser.getRealName());
			map.put("phone", phone);
			map.put("email", hfUser.getEmail());
		}
		if (!StringUtils.isEmpty(phone)) {
			String s2 = "^[1](([3|5|8][\\d])|([4][4,5,6,7,8,9])|([6][2,5,6,7])|([7][^9])|([9][1,8,9]))[\\d]{8}$";
			Pattern p = Pattern.compile(s2);
			Matcher m = p.matcher(phone);
			boolean b = m.matches();
			if (b) {
				Integer code = GetMessageCode.sendSms(list, map);
				return builder.body(ResponseUtils.getResponseBody(code));
			}
			return builder.body(ResponseUtils.getResponseBody("手机号有误"));
		} else {
			return builder.body(ResponseUtils.getResponseBody("请输入手机号"));
		}
	}

	@RequestMapping(path = "/addTemplateMessage", method = RequestMethod.POST)
	@ApiOperation(value = "添加信息模板", notes = "添加信息模板")
	public ResponseEntity<JSONObject> addTemplateMessage(Integer bossId, Integer type, String content,
			String contentType, String messageType, String subject) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		HfMessageInfoExample example = new HfMessageInfoExample();
		if (type == 1) {
//			example.createCriteria().andBossIdEqualTo(bossId).andTypeEqualTo(messageType);
//			List<HfMessageInfo> list = hfMessageInfoMapper.selectByExample(example);
//			if (CollectionUtils.isEmpty(list)) {
//				HfMessageInfo info = new HfMessageInfo();
//				info.setBossId(bossId);
//				info.setType(messageType);
//				info.setSignName(content);
//				info.setStatus(1);
//				info.setCreateTime(LocalDateTime.now());
//				info.setModifyTime(LocalDateTime.now());
//				info.setIsDeleted((byte) 0);
//				hfMessageInfoMapper.insert(info);
//				if (MessageTypeEnum.SHORT_BREATH.getMessageType().equals(messageType)) {
//					addTemplateMessageMethod(info.getId(), bossId, type, content, contentType, messageType);
//				}
		} else {
			example.createCriteria().andBossIdEqualTo(bossId).andTypeEqualTo(messageType);
			List<HfMessageInfo> list = hfMessageInfoMapper.selectByExample(example);
			HfMessageInfo info;
			if (CollectionUtils.isEmpty(list)) {
				info = new HfMessageInfo();
				info.setBossId(bossId);
				info.setType(messageType);
//				info.setSignName(content);
//				info.setStatus(1);
				info.setCreateTime(LocalDateTime.now());
				info.setModifyTime(LocalDateTime.now());
				info.setIsDeleted((byte) 0);
				hfMessageInfoMapper.insert(info);
			} else {
				info = list.get(0);
			}
			HfMessageTemplate template = null;
			HfMessageTemplateExample templateExample = new HfMessageTemplateExample();
			templateExample.createCriteria().andMessageIdEqualTo(info.getId()).andTypeEqualTo(contentType);
			List<HfMessageTemplate> templates = hfMessageTemplateMapper.selectByExample(templateExample);
			if (CollectionUtils.isEmpty(templates)) {
				template = new HfMessageTemplate();
				template.setMessageId(info.getId());
				template.setType(contentType);

				template.setCreateTime(LocalDateTime.now());
				template.setModifyTime(LocalDateTime.now());
				template.setIsDeleted((byte) 0);
				hfMessageTemplateMapper.insert(template);
			} else {
				template = templates.get(0);
			}
			String result = parseLine(content);
			content = URLDecoder.decode(content, "utf-8");
			HfMessageInstance instance = new HfMessageInstance();
			instance.setTemplateTypeId(template.getId());
			instance.setStatus(2);
			if (MessageTypeEnum.SHORT_BREATH.getMessageType().equals(messageType)) {
				instance.setStatus(1);
			}
			instance.setTemplateParam(result);
			instance.setContent(content);
			instance.setSubject(subject);
			instance.setIsDeleted((byte) 0);
			hfMessageInstanceMapper.insert(instance);
//				template.setMessageInstanceId(instance.getId());
//				hfMessageTemplateMapper.updateByPrimaryKey(template);
			if (MessageTypeEnum.SHORT_BREATH.getMessageType().equals(messageType)) {
				addTemplateMessageMethod(instance.getId(), bossId, type, content, contentType, messageType);
			}
		}
		return null;
	}

	public Integer addTemplateMessageMethod(Integer instanceId, Integer bossId, Integer type, String content,
			String contentType, String messageType) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		HfMessageApply apply = new HfMessageApply();
		apply.setMessageInstanceId(instanceId);
		apply.setStatus(1);
		apply.setBossId(bossId);
		apply.setContent(content);
		apply.setMessageType(messageType);
		apply.setContentType(contentType);
		apply.setType(type);
		apply.setCreateTime(LocalDateTime.now());
		apply.setModifyTime(LocalDateTime.now());
		apply.setIsDeleted((byte) 0);
		Integer id = hfMessageApplyMapper.insert(apply);
		return id;
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	@ApiOperation(value = "用户注册", notes = "用户注册")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "authType", value = "鉴权方式. 2  手机号码注册", required = true, type = "String"),
			@ApiImplicitParam(paramType = "query", name = "authKey", value = "鉴权key", required = false, type = "String"),
			@ApiImplicitParam(paramType = "query", name = "passwd", value = "密码", required = false, type = "String") })
	public ResponseEntity<JSONObject> register(@RequestParam(name = "authType") String authType,
			@RequestParam(name = "authKey") String authKey, @RequestParam("passwd") String passwd) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		HfAuth hfAuth = userDao.selectAuthList(authKey);
		if (hfAuth == null) {
			HfAuthExample example = new HfAuthExample();
			example.createCriteria().andAuthKeyEqualTo(authKey);
			long authCount = hfAuthMapper.countByExample(example);
			if (authCount > 0) {
				throw new AuthKeyIsExistException(authKey);
			}
			System.out.println(redisTemplate.opsForValue().get(authKey));
			if (!passwd.equals(redisTemplate.opsForValue().get(authKey))) {
				throw new ParamInvalidException("authKey is invalid");
			}
			HfUser user = new HfUser();
			user.setSourceType(authType);
			user.setPhone(authKey);
			user.setUsername(authKey);
			user.setUserStatus("0".getBytes()[0]);
			user.setBirthDay(LocalDateTime.now());
			user.setSex((byte) 1);
			// user.setAddress(IpAddress.findOne(IpAddress.getRemortIP(request)));
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
			Map<String, String> map = new HashMap<String, String>();
			map.put("UserId", String.valueOf(user.getId()));
			map.put("FileId", String.valueOf(user.getFileId()));
			return builder.body(ResponseUtils.getResponseBody(map));
		}
		if (!passwd.equals(redisTemplate.opsForValue().get(authKey))) {
			return builder.body(ResponseUtils.getResponseBody("验证码不正确"));
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("UserId", String.valueOf(hfAuth.getUserId()));
		HfUser hfUser = hfUserMapper.selectByPrimaryKey(hfAuth.getUserId());
		map.put("FileId", String.valueOf(hfUser.getFileId()));
		return builder.body(ResponseUtils.getResponseBody(map));
	}

	@RequestMapping(value = "/upload_avatar", method = RequestMethod.POST)
	@ApiOperation(value = "上传头像", notes = "上传头像")
	public ResponseEntity<JSONObject> uploadAvatar(@RequestParam(value = "file", required = false) MultipartFile file,
			@RequestParam(value = "userId", required = false) Integer userId) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		FileMangeService fileMangeService = new FileMangeService();
		String arr[];
		if (file != null) {
			arr = fileMangeService.uploadFile(file.getBytes(), String.valueOf(userId));
			FileDescExample example = new FileDescExample();
			example.createCriteria().andUserIdEqualTo(userId);
			List<FileDesc> list = fileDescMapper.selectByExample(example);
			if (list.isEmpty()) {
				FileDesc fileDesc = new FileDesc();
				fileDesc.setFileName(file.getName());
				fileDesc.setGroupName(arr[0]);
				fileDesc.setRemoteFilename(arr[1]);
				fileDesc.setUserId(userId);
				fileDesc.setCreateTime(LocalDateTime.now());
				fileDesc.setModifyTime(LocalDateTime.now());
				fileDesc.setIsDeleted((short) 0);
				fileDescMapper.insert(fileDesc);
				HfUser hfUser = hfUserMapper.selectByPrimaryKey(userId);
				hfUser.setFileId(fileDesc.getId());
				hfUserMapper.updateByPrimaryKey(hfUser);
			} else {
				FileDesc fileDesc = list.get(0);
				fileDesc.setFileName(file.getName());
				fileDesc.setGroupName(arr[0]);
				fileDesc.setRemoteFilename(arr[1]);
				fileDesc.setModifyTime(LocalDateTime.now());
				fileDescMapper.updateByPrimaryKey(fileDesc);
			}
		}
		return builder.body(ResponseUtils.getResponseBody(null));
	}

	@RequestMapping(path = "/download_avatar", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> downloadAvatar(String group_name, String remoteFilename) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		FileMangeService fileManageService = new FileMangeService();
		byte[] fileid = fileManageService.downloadFile(group_name, remoteFilename);
		return builder.body(ResponseUtils.getResponseBody(fileid));
	}

//	@RequestMapping(path = "/userList",  method = RequestMethod.GET)
//    @ApiOperation(value = "用户列表", notes = "用户列表")
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户Id", required = false, type = "Integer")
//    })
//    public ResponseEntity<JSONObject> userList(Integer userId,Integer pageNum,Integer pageSize) throws Exception{
//            BodyBuilder builder = ResponseUtils.getBodyBuilder();
//            if(pageNum==null) {
//            	pageNum=0;
//            }if(pageSize==null) {
//            	pageSize=0;
//            }
//            if(!StringUtils.isEmpty(userId)) {
//                    HfUserExample hfUserExample = new HfUserExample();
//                    hfUserExample.createCriteria().andIdNotEqualTo(userId);
//                    return builder.body(ResponseUtils.getResponseBody(hfUserMapper.selectByPrimaryKey(userId)));
//            }
//            PageHelper.startPage(pageNum,pageSize);
//            List<ActivityUserInfo> list = userDao.findActivityUserInfo();
//            System.out.println(list);
//            for (int i = 0; i < list.size(); i++) {
//                    ActivityUserInfo info = list.get(i);
//                    if(info != null) {
//                            if(info.getDepartmentId() != null) {
//                                    String departmentName = userDao.findDepartmentName(info.getDepartmentId());
//                                    info.setDepartmentName(departmentName);
//                                    System.out.println(departmentName);
//                            }
//                    }
//            }
//
//            PageInfo<ActivityUserInfo> page = new PageInfo<ActivityUserInfo>(list);
//            System.out.println(page);
//            return builder.body(ResponseUtils.getResponseBody(page));
//    }
//    @RequiredPermission(PermissionConstants.ADMIN_PRODUCT_LIST)
//    @RequestMapping(path = "/userList",  method = RequestMethod.GET)
//    @ApiOperation(value = "用户列表", notes = "用户列表")
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户Id", required = false, type = "Integer"),
//            @ApiImplicitParam(paramType = "query", name = "time", value = "排序方式1降序2升序,3微信名降序4升序", required = false, type = "Integer")
//    })
//    public ResponseEntity<JSONObject> userList(Integer userId, test test) throws Exception{
//        if (test.getPageNum()==null){
//            test.setPageNum(0);
//        }if (test.getPageSize()==null){
//            test.setPageSize(0);
//        }if(test.getTime()==null){
//            test.setPageNum(1);
//        }
//        BodyBuilder builder = ResponseUtils.getBodyBuilder();
//        if(!StringUtils.isEmpty(userId)) {
//            HfUserExample hfUserExample = new HfUserExample();
//            hfUserExample.createCriteria().andIdNotEqualTo(userId);
//            return builder.body(ResponseUtils.getResponseBody(hfUserMapper.selectByPrimaryKey(userId)));
//        }
//        PageHelper.startPage(test.getPageNum(),test.getPageSize());
//        List<ActivityUserInfo> list = userDao.findActivityUserInfo(test);
//        System.out.println(list);
//        for (int i = 0; i < list.size(); i++) {
//            ActivityUserInfo info = list.get(i);
//            if(info != null) {
//                if(info.getDepartmentId() != null) {
//                    String departmentName = userDao.findDepartmentName(info.getDepartmentId());
//                    info.setDepartmentName(departmentName);
//                    System.out.println(departmentName);
//                }
//            }
//        }
//
//        PageInfo<ActivityUserInfo> page = new PageInfo<ActivityUserInfo>(list);
//        System.out.println(page);
//        return builder.body(ResponseUtils.getResponseBody(page));
//    }
//
//
//    @RequestMapping(path = "/userListTP", method = RequestMethod.GET)
//    @ApiOperation(value = "用户列表查询", notes = "用户列表查询")
//    public ResponseEntity<JSONObject> userListTP(UserQuery userQuery, Integer pageNum, Integer pageSize) throws Exception {
//        System.out.println(userQuery);
//        if (pageNum==null){
//            pageNum=0;
//        }if (pageSize==null){
//            pageSize=0;
//        }
//        BodyBuilder builder = ResponseUtils.getBodyBuilder();
//        PageHelper.startPage(pageNum, pageSize);
//        List<ActivityUserInfo> list = userDao.findActivityUserInfoTP(userQuery);
//        System.out.println(list + "list-----");
//        for (int i = 0; i < list.size(); i++) {
//            ActivityUserInfo info = list.get(i);
//            if (info != null) {
//                if (info.getDepartmentId() != null) {
//                    String departmentName = userDao.findDepartmentName(info.getDepartmentId());
//                    info.setDepartmentName(departmentName);
//                    System.out.println(departmentName);
//                }
//            }
//        }
//
//        PageInfo<ActivityUserInfo> page = new PageInfo<ActivityUserInfo>(list);
//        System.out.println(page);
//        return builder.body(ResponseUtils.getResponseBody(page));
//    }
//
//    @RequestMapping(path = "/deleteUser", method = RequestMethod.GET)
//    @ApiOperation(value = "删除人", notes = "删除人")
//    public ResponseEntity<JSONObject> deleteUser(Integer userId) throws Exception {
//        BodyBuilder builder = ResponseUtils.getBodyBuilder();
//        HfUser hfUser = hfUserMapper.selectByPrimaryKey(userId);
//        if (hfUser == null) {
//            return builder.body(ResponseUtils.getResponseBody("此用户不存在"));
//        }
//        return builder.body(ResponseUtils.getResponseBody(hfUserMapper.deleteByPrimaryKey(userId)));
//    }
	@RequestMapping(path = "/selectList", method = RequestMethod.POST)
	@ApiOperation(value = "用户列表", notes = "用户列表")
	public ResponseEntity<JSONObject> selectList(UserInfo userInfo) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		if (!StringUtils.isEmpty(userInfo.getTime())) {
			DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime ldt = LocalDateTime.parse(userInfo.getTime(), df);
			System.out.println(ldt);
			userInfo.setCreateDate(ldt);
		}
		return builder.body(ResponseUtils.getResponseBody(userDao.selectUserList(userInfo)));
	}

	@RequestMapping(path = "/wxLogin", method = RequestMethod.GET)
	@ApiOperation(value = "微信登录", notes = "微信登录")
	public ResponseEntity<JSONObject> wxLogin(Model model, @RequestParam(value = "code", required = false) String code,
			@RequestParam(value = "rawData", required = false) String rawData,
			@RequestParam(value = "signature", required = false) String signature,
			@RequestParam(value = "encryptedData", required = false) String encryptedData,
			@RequestParam(value = "iv", required = false) String iv) throws Exception {
		logger.info("Start get SessionKey");
		Integer userId = null;
		Map<String, Object> map = new HashMap<String, Object>();
		// JSONObject rawDataJson = JSON.parseObject( rawData );
		JSONObject SessionKeyOpenId = getSessionKeyOrOpenId(code);
		System.out.println("SessionKeyOpenId++++++"+SessionKeyOpenId);
		String openid = SessionKeyOpenId.get("openid").toString();
		String sessionKey = String.valueOf(SessionKeyOpenId.get("session_key"));
		// uuid生成唯一key
		String skey = UUID.randomUUID().toString();
		// 根据openid查询skey是否存在
		String skey_redis = String.valueOf(redisTemplate.opsForValue().get(openid));
		if (!StringUtils.isEmpty(skey_redis)) {
			// 存在 删除 skey 重新生成skey 将skey返回
			redisTemplate.delete(skey_redis);
			skey = UUID.randomUUID().toString();
		}
		// 缓存一份新的
		JSONObject sessionObj = new JSONObject();
		sessionObj.put("openId", openid);
		sessionObj.put("sessionKey", sessionKey);
		redisTemplate.opsForValue().set(skey, sessionObj.toJSONString());
		redisTemplate.opsForValue().set(openid.toString(), skey);
		// 把新的sessionKey和oppenid返回给小程序
		map.put("skey", skey);
		map.put("result", "0");
		JSONObject userInfo = getUserInfo(encryptedData, sessionKey, iv);
		String unionId = "";
		String nickName = "";
		String avatarUrl = "";
		if (userInfo != null) {
			if (userInfo.get("unionId") != null) {
				unionId = (String) userInfo.get("unionId");
			}
			nickName = userInfo.getString("nickName");
			avatarUrl = userInfo.getString("avatarUrl");
		}
		System.out.println("unionId+++++"+unionId);
		if (!StringUtils.isEmpty(unionId)) {
			ActivityUserExample example = new ActivityUserExample();
			example.createCriteria().andUsernameEqualTo(unionId);
			List<ActivityUser> list = activityUserMapper.selectByExample(example);
			if (list.isEmpty()) {
				ActivityUser hfUser = new ActivityUser();
				hfUser.setAddress(avatarUrl);
				hfUser.setNickName(nickName);
				hfUser.setUsername(unionId);
				hfUser.setCreateDate(LocalDateTime.now());
				hfUser.setModifyDate(LocalDateTime.now());
				hfUser.setIdDeleted((byte) 0);
				hfUser.setCancelId(0);
				hfUser.setUserStatus((byte) 0);
				try {
					activityUserMapper.insert(hfUser);
				} catch (Exception e) {
					hfUser.setAddress(avatarUrl);
					ActivityUserExample example2 = new ActivityUserExample();
					example2.createCriteria().andNickNameLike("未知昵称%");
					List<ActivityUser> list2 = activityUserMapper.selectByExample(example2);
					hfUser.setNickName("未知昵称" + list2.size() + 1);
					hfUser.setUsername(unionId);
					hfUser.setCreateDate(LocalDateTime.now());
					hfUser.setModifyDate(LocalDateTime.now());
					hfUser.setIdDeleted((byte) 0);
					hfUser.setCancelId(0);
					hfUser.setUserStatus((byte) 0);
					activityUserMapper.insert(hfUser);
				}
				userId = hfUser.getId();
			} else {
				ActivityUser hfUser = list.get(0);
				hfUser.setAddress(avatarUrl);
				hfUser.setNickName(nickName);
				try {
					activityUserMapper.updateByPrimaryKey(hfUser);
				} catch (Exception e) {
					hfUser.setAddress(avatarUrl);
					hfUser.setNickName(list.get(0).getNickName());
					hfUser.setUsername(unionId);
					hfUser.setCreateDate(LocalDateTime.now());
					hfUser.setModifyDate(LocalDateTime.now());
					hfUser.setIdDeleted((byte) 0);
					hfUser.setCancelId(0);
					hfUser.setUserStatus((byte) 0);
					activityUserMapper.updateByPrimaryKey(hfUser);
				}
				userId = hfUser.getId();
			}
		}
		map.put("userId", userId);
		map.put("userInfo", userInfo);
		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		return builder.body(ResponseUtils.getResponseBody(map));
	}

	private JSONObject getUserInfo(String encryptedData, String sessionKey, String iv) {
		// 被加密的数据
		byte[] dataByte = Base64.getDecoder().decode(encryptedData);
		// 加密秘钥
		byte[] keyByte = Base64.getDecoder().decode(sessionKey);
		// 偏移量
		byte[] ivByte = Base64.getDecoder().decode(iv);
		try {
			// 如果密钥不足16位，那么就补足. 这个if 中的内容很重要
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
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
			SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
			AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
			parameters.init(new IvParameterSpec(ivByte));
			cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
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
		// 微信端登录code
		// String wxCode = code;
		// Map<String,String> requestUrlParam = new HashMap<String, String>( );
//		requestUrlParam.put( "appid","wx16159fcc93b0400c" );//小程序appId
//		requestUrlParam.put( "secret","1403f2e207dfa2f1f348910626f5aa42" );
//		requestUrlParam.put( "js_code",wxCode );//小程序端返回的code
//		requestUrlParam.put( "grant_type","authorization_code" );//默认参数 
//		//发送post请求读取调用微信接口获取openid用户唯一标识
//		String str = UrlUtil.sendPost( requestUrl,requestUrlParam );
//		JSONObject jsonObject = JSON.parseObject(UrlUtil.sendPost( requestUrl,requestUrlParam ));
		String requestUrl = "https://api.weixin.qq.com/sns/jscode2session?appid=wx16159fcc93b0400c&secret=1403f2e207dfa2f1f348910626f5aa42&js_code="
				+ code + "&grant_type=authorization_code";
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(requestUrl);
		JSONObject jsonObject = null;
		try {
			HttpResponse response = httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String result = EntityUtils.toString(entity, "UTF-8");
				jsonObject = JSONObject.parseObject(result);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return jsonObject;
	}

	public String parseLine(String str) {
		// 正则表达式
		String pattern = "(\\$)(.*?)(\\$)"; // Java正则表达式以括号分组，第一个括号表示以"（乙方）:"开头，第三个括号表示以" "(空格)结尾，中间括号为目标值，
		// 创建 Pattern 对象
		Pattern r = Pattern.compile(pattern);
		// 创建 matcher 对象
		Matcher m = r.matcher(str);

		StringBuilder builder = new StringBuilder();
		while (m.find()) {
			/*
			 * 自动遍历打印所有结果 group方法打印捕获的组内容，以正则的括号角标从1开始计算，我们这里要第2个括号里的 值， 所以取 m.group(2)，
			 * m.group(0)取整个表达式的值，如果越界取m.group(4),则抛出异常
			 */
			System.out.println("Found value: " + m.group(2));
			builder.append(m.group(2));
			builder.append(",");
		}
		return builder.toString();
	}

}
