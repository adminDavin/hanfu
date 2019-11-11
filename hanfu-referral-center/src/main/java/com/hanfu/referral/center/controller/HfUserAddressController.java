package com.hanfu.referral.center.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hanfu.referral.center.service.ReferralUserAddressService;
import com.hanfu.referral.center.service.ReferralUserService;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseUtils;
import com.hanfu.utils.response.handler.ResponseEntity.BodyBuilder;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/address")
@Api
public class HfUserAddressController {
	@Autowired
	private ReferralUserAddressService referralUserAddressService;
	@RequestMapping(path = "/findAllUser",method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> findAllOrder(Integer id) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        return builder.body(ResponseUtils.getResponseBody(referralUserAddressService.getUserAddressById(id)));
    }
}
