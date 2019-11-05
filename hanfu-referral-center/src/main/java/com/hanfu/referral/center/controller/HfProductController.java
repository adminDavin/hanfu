package com.hanfu.referral.center.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.hanfu.referral.center.service.ReferralProductService;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseUtils;
import com.hanfu.utils.response.handler.ResponseEntity.BodyBuilder;

import io.swagger.annotations.Api;
@CrossOrigin
@RestController
@RequestMapping("/product")
@Api
public class HfProductController {
	
	@Autowired
	private ReferralProductService referralProductService;
	
	@RequestMapping(path = "/findAllProduct",method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> findAllProduct() throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        return builder.body(ResponseUtils.getResponseBody(referralProductService.getAllProduct()));
    }
	
	@RequestMapping(path = "/listCategory",method = RequestMethod.GET)
	public ResponseEntity<JSONObject> listCategory(
			@RequestParam(name = "parentCategoryId", required = false, defaultValue = "-1") Integer parentCategoryId,
			@RequestParam(name = "categoryId", required = false) Integer categoryId,
			@RequestParam(name = "levelId", required = false, defaultValue = "0") Integer levelId)
			throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        return builder.body(ResponseUtils.getResponseBody(referralProductService.listCategory(parentCategoryId, categoryId, levelId)));

	}
	
}
