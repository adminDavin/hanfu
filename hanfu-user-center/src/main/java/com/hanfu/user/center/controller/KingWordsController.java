package com.hanfu.user.center.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hanfu.user.center.dao.UsersMapper;
import com.hanfu.user.center.model.Users;
import com.hanfu.user.center.model.UsersExample;
import com.hanfu.user.center.request.LoginReuqest;
import com.hanfu.user.center.request.RegisterReuqest;
import com.hanfu.user.center.request.ReturnContant;
import com.hanfu.user.center.response.handler.ResponseUtils;
import com.hanfu.user.center.service.CommonService;
import com.hanfu.user.center.utils.GetMessageCode;

@RestController
@RequestMapping("/user")
public class KingWordsController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CommonService commonService;
	@Autowired
	private UsersMapper usersMapper;
	@Resource
	private ReturnContant returnContant;
	/**
	 * 展示所有用户
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/users")
	public ResponseEntity<JSONObject> listUsers(HttpServletRequest request) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		List<Users> list = commonService.getUserList();
		return builder.body(ResponseUtils.getResponseBody(list));
	}

	@RequestMapping("/login")
	public ResponseEntity<JSONObject> loginUser(@RequestBody LoginReuqest request) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		Map<String, String> list = commonService.loginUser(request);
		LoginReuqest re= JSON.parseObject(JSON.toJSONString(list),LoginReuqest.class);
		if(re.getSourceType().equals(request.getSourceType())) {
			if(re.getAuthKey().equals(request.getAuthKey())) {
				return builder.body(ResponseUtils.getResponseBody(list));
			}
			return null;
		} 
		Users record = usersMapper.selectByPrimaryKey(1);
		UsersExample example = new UsersExample();
		example.createCriteria().andIdEqualTo(1);  
		usersMapper.updateByExample(record, example);
		if(record == null) {
			//提示用户用户名或密码错误，跳转到注册界面
			return null;
		}
		return builder.body(ResponseUtils.getResponseBody(list));
	}
	@RequestMapping("/register")
	public ResponseEntity<JSONObject> registerUser(@RequestBody RegisterReuqest request,String phone) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		Map<String , String> list = commonService.registerUser(request);
		UsersExample example = new UsersExample();
		example.createCriteria().andIdEqualTo(1);
		usersMapper.selectByPrimaryKey(1);
		if(list != null) {
			return null;
		}
		String code=GetMessageCode.getCode(phone); 
		returnContant.setStatus(1);
		returnContant.setData(code);
		if(code == null) {
			return null;
		}
		return builder.body(ResponseUtils.getResponseBody(list));
	}

	@RequestMapping("/")
	public ResponseEntity<JSONObject> list() throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		UsersExample example = new UsersExample();
		return builder.body(ResponseUtils.getResponseBody(usersMapper.selectByExample(example)));
	}
}
