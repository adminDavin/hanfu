package com.hanfu.user.center.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.hanfu.user.center.dao.HfUserAddresseMapper;
import com.hanfu.user.center.manual.dao.AddressDao;
import com.hanfu.user.center.model.HfUser;
import com.hanfu.user.center.model.HfUserAddresse;
import com.hanfu.user.center.model.HfUserAddresseExample;
import com.hanfu.user.center.request.UserAddressRequest;
import com.hanfu.user.center.response.handler.UserAddressNotException;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseUtils;
import com.hanfu.utils.response.handler.ResponseEntity.BodyBuilder;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@Api
@RequestMapping("/user/address")
@CrossOrigin
public class HfUserAddressManager {

	@Autowired
	private HfUserAddresseMapper hfUserAddresseMapper;
    @Autowired
    private AddressDao addressDao;
	@RequestMapping(value = "/queryAddress", method = RequestMethod.GET)
	@ApiOperation(value = "获取用戶地址列表", notes = "获取用戶地址列表")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "userId", value = "用戶id", required = true, type = "Integer"),
		@ApiImplicitParam(paramType = "query", name = "token", value = "token", required = false, type = "String")
	})
	public ResponseEntity<JSONObject> query(@RequestParam Integer userId, @RequestParam String token) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		HfUserAddresseExample e = new HfUserAddresseExample();
		e.createCriteria().andUserIdEqualTo(userId);
		return builder.body(ResponseUtils.getResponseBody(hfUserAddresseMapper.selectByExample(e)));
	}
	@CrossOrigin
	@RequestMapping(value = "/addAddress", method = RequestMethod.GET)
	@ApiOperation(value = "添加用戶地址", notes = "添加用戶地址")
	public ResponseEntity<JSONObject> add(UserAddressRequest request) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		HfUser hfUser = new HfUser();
		int i  = 0;
		HfUserAddresse hfUserAddress = new HfUserAddresse();
		hfUserAddress.setUserId(request.getUserId());
		hfUserAddress.setContact(request.getContact());
		hfUserAddress.setHfConty(request.getHfConty());
		hfUserAddress.setCreateTime(LocalDateTime.now());
		hfUserAddress.setHfAddressDetail(request.getHfAddressDetail());
		hfUserAddress.setHfCity(request.getHfCity());
		hfUserAddress.setHfDesc(request.getHfDesc());
		hfUserAddress.setHfProvince(request.getHfProvince());
		hfUserAddress.setIsFaultAddress(request.getIsFaultAddress());
		hfUserAddress.setModifyTime(LocalDateTime.now());
		hfUserAddress.setIsDeleted((short) 0);
		hfUserAddress.setLastModifier("1");
		hfUserAddress.setPhoneNumber(request.getPhoneNumber());
		if(StringUtils.isEmpty(hfUser.getAddress())) {
			hfUserAddress.setIsFaultAddress(1);
		}
		i = addressDao.updateAddress(request.getId());
		return builder.body(ResponseUtils.getResponseBody(hfUserAddresseMapper.insert(hfUserAddress)));
	}
	@RequestMapping(value = "/deleteAddress", method = RequestMethod.GET)
	@ApiOperation(value = "删除用戶地址", notes = "删除用戶地址")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "id", value = "地址id", required = true, type = "Integer")})
	public ResponseEntity<JSONObject> delete(@RequestParam(name = "id") Integer id) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		HfUserAddresseExample example = new HfUserAddresseExample(); 
		example.createCriteria().andIdEqualTo(id);
		return builder.body(ResponseUtils.getResponseBody(hfUserAddresseMapper.deleteByPrimaryKey(id)));
	}
	@RequestMapping(value = "/updateAddress", method = RequestMethod.POST)
	@ApiOperation(value = "更新用戶地址", notes = "更改用戶地址")
	public ResponseEntity<JSONObject> update(UserAddressRequest request) throws Exception {
		HfUserAddresse  hfUserAddresse = hfUserAddresseMapper.selectByPrimaryKey(request.getId());
		if(hfUserAddresse == null) {
			throw new UserAddressNotException(String.valueOf(request.getUserId()));
		}
		if(!StringUtils.isEmpty(request.getContact())) {
			hfUserAddresse.setContact(request.getContact());
		}
		if(!StringUtils.isEmpty(request.getHfAddressDetail())) {
			hfUserAddresse.setHfAddressDetail(request.getHfAddressDetail());
		}
		if(!StringUtils.isEmpty(request.getHfCity())) {
			hfUserAddresse.setHfCity(request.getHfCity());
		}
		if(!StringUtils.isEmpty(request.getHfConty())) {
			hfUserAddresse.setHfConty(request.getHfConty());
		}
		if(!StringUtils.isEmpty(request.getHfDesc())) {
			hfUserAddresse.setHfDesc(request.getHfDesc());
		}
		if(!StringUtils.isEmpty(request.getHfProvince())) {
			hfUserAddresse.setHfProvince(request.getHfProvince());
		}
		if(StringUtils.isEmpty(request.getIsFaultAddress())) {
			hfUserAddresse.setIsFaultAddress(request.getIsFaultAddress());
		}
		if(!StringUtils.isEmpty(request.getUserId())) {
			hfUserAddresse.setUserId(request.getUserId());
		}
		if(!StringUtils.isEmpty(request.getPhoneNumber())) {
			hfUserAddresse.setPhoneNumber(request.getPhoneNumber());
		}
		hfUserAddresse.setModifyTime(LocalDateTime.now());
		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		return builder.body(ResponseUtils.getResponseBody(hfUserAddresseMapper.updateByPrimaryKeySelective(hfUserAddresse)));
	}
	@RequestMapping(value = "/addressDetail", method = RequestMethod.GET)
	@ApiOperation(value = "查询地址详情", notes = "查询地址详情")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "id", value = "地址id", required = true, type = "Integer")})
	public ResponseEntity<JSONObject> addressDetail(@RequestParam(name = "id") Integer id) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		HfUserAddresseExample example = new HfUserAddresseExample(); 
		example.createCriteria().andIdEqualTo(id);
		return builder.body(ResponseUtils.getResponseBody(hfUserAddresseMapper.selectByPrimaryKey(id)));
	}
	@RequestMapping(value = "/searchAddress", method = RequestMethod.GET)
	@ApiOperation(value = "搜索地址", notes = "搜索地址")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "phoneNumber", value = "手机号", required = true, type = "String")})
	public ResponseEntity<JSONObject> search(@RequestParam String phoneNumber) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		HfUserAddresseExample example = new HfUserAddresseExample(); 
		example.createCriteria().andPhoneNumberEqualTo(phoneNumber);
		return builder.body(ResponseUtils.getResponseBody(hfUserAddresseMapper.selectByExample(example)));
	}
}
