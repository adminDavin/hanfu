package com.hanfu.shejiao.center.controller;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.hanfu.common.service.FileMangeService;
import com.hanfu.shejiao.center.dao.SjFileDescMapper;
import com.hanfu.shejiao.center.dao.SjUserMapper;
import com.hanfu.shejiao.center.model.SjFileDesc;
import com.hanfu.shejiao.center.model.SjUser;
import com.hanfu.shejiao.center.utils.Message;
import com.hanfu.shejiao.center.utils.RandomString;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseUtils;
import com.hanfu.utils.response.handler.ResponseEntity.BodyBuilder;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.coobird.thumbnailator.Thumbnails;

@RestController
@Api
@RequestMapping("/login")
@CrossOrigin
public class SjLoginController {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource
	private RedisTemplate<String, Object> redisTemplate;

	@Autowired
	private SjUserMapper sjUserMapper;
	
	@Autowired
	private SjFileDescMapper sjFileDescMapper;

	@RequestMapping(path = "/loginCode", method = RequestMethod.POST)
	@ApiOperation(value = "手机号验证码登录", notes = "手机号验证码登录")
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户Id", required = true, type = "Integer"),
//            @ApiImplicitParam(paramType = "query", name = "goodsId", value = "商品Id", required = true, type = "Integer"),
//            @ApiImplicitParam(paramType = "query", name = "num", value = "商品数量", required = true, type = "Integer"),
//    })
	public ResponseEntity<JSONObject> loginCode(String phone, Integer code, HttpServletResponse response)
			throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		Object result = redisTemplate.opsForValue().get(phone + "shejiao");
		if (code == null) {
			return builder.body(ResponseUtils.getResponseBody("验证码为null"));
		}
		if (result == null || !(Integer.valueOf((String) result) == code)) {
			response.sendError(HttpStatus.UNAUTHORIZED.value(), "无权限");
			return builder.body(ResponseUtils.getResponseBody("0"));
		}
		SjUser user = new SjUser();
		user.setPhone(phone);
		user.setUsername("SJ" + RandomString.GetRandomString(8));
		sjUserMapper.insert(user);
		return builder.body(ResponseUtils.getResponseBody(user.getId()));
	}

	@RequestMapping(path = "/getCode", method = RequestMethod.GET)
	@ApiOperation(value = "获取验证码", notes = "获取验证码")
	public ResponseEntity<JSONObject> getCode(String phone) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		if (!StringUtils.isEmpty(phone)) {
			String s2 = "^[1](([3|5|8][\\d])|([4][4,5,6,7,8,9])|([6][2,5,6,7])|([7][^9])|([9][1,8,9]))[\\d]{8}$";
			Pattern p = Pattern.compile(s2);
			Matcher m = p.matcher(phone);
			boolean b = m.matches();
			if (b) {
				Integer code = Message.sendSms(phone);
				redisTemplate.opsForValue().set(phone + "shejiao", String.valueOf(code));
				return builder.body(ResponseUtils.getResponseBody(code));
			}
			return builder.body(ResponseUtils.getResponseBody("手机号有误"));
		} else {
			return builder.body(ResponseUtils.getResponseBody("请输入手机号"));
		}
	}

	@RequestMapping(path = "/updateUserInfo", method = RequestMethod.POST)
	@ApiOperation(value = "更新用户信息", notes = "更新用户信息")
	public ResponseEntity<JSONObject> updateUserInfo(SjUser info, MultipartFile file) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		SjUser user = sjUserMapper.selectByPrimaryKey(info.getId());
		if (file != null) {
			SjFileDesc fileDesc = new SjFileDesc();
			FileMangeService fileMangeService = new FileMangeService();
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			Thumbnails.of(file.getInputStream()).scale(0.8f).outputFormat("jpg").outputQuality(0.6).toOutputStream(os);
			String arr[];
			arr = fileMangeService.uploadFile(os.toByteArray(), String.valueOf(user.getId()));
			fileDesc = new SjFileDesc();
			fileDesc.setGroupName(arr[0]);
			fileDesc.setRemoteFilename(arr[1]);
			fileDesc.setModifyTime(LocalDateTime.now());
			fileDesc.setCreateTime(LocalDateTime.now());
			fileDesc.setFileName(file.getOriginalFilename());
			fileDesc.setIsDeleted((short) 0);
			sjFileDescMapper.insert(fileDesc);
			user.setFileId(fileDesc.getId());
		}
		if (!StringUtils.isEmpty(info.getUsername())) {
			user.setUsername(info.getUsername());
		}
		if (!StringUtils.isEmpty(info.getSex())) {
			user.setSex(info.getSex());
		}
		if (!StringUtils.isEmpty(info.getBirthDay())) {
			user.setBirthDay(info.getBirthDay());
		}
		if (!StringUtils.isEmpty(info.getRegion())) {
			user.setRegion(info.getRegion());
		}
		if (!StringUtils.isEmpty(info.getAddress())) {
			user.setAddress(info.getAddress());
		}
		sjUserMapper.updateByPrimaryKey(user);
		return builder.body(ResponseUtils.getResponseBody(user.getId()));
	}
	
	@RequestMapping(path = "/adminLogin", method = RequestMethod.POST)
	@ApiOperation(value = "cs", notes = "cs")
	public ResponseEntity<JSONObject> adminLogin(String username, String password) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		System.out.println("username" + username);
		if("sunwangda".equals(username) && "123456".equals(password)) {
			return builder.body(ResponseUtils.getResponseBody(1));
		}
		return builder.body(ResponseUtils.getResponseBody(0));
	}
}
