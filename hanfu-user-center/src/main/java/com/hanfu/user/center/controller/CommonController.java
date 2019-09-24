package com.hanfu.user.center.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.hanfu.user.center.response.handler.ResponseUtils;


@RestController
@RequestMapping("/")
public class CommonController extends BasicErrorController {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	public CommonController(ErrorAttributes errorAttributes, ErrorProperties errorProperties) {
		super(errorAttributes, errorProperties);
	}

	@RequestMapping(value = "/index")
	public ResponseEntity<JSONObject> index(HttpServletRequest request) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		return builder.body(ResponseUtils.getResponseBody("hello world"));
	}

	@RequestMapping("/error")
	public ResponseEntity<Map<String, Object>> responce(HttpServletRequest request) {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(getStatus(request));
		Map<String, Object> body = getErrorAttributes(request, isIncludeStackTrace(request, MediaType.ALL));
		return builder.body(body);
	}
}
