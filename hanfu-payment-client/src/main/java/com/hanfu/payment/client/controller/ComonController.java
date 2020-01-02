package com.hanfu.payment.client.controller;


import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseUtils;
import com.hanfu.utils.response.handler.ResponseEntity.BodyBuilder;


@RestController
@RequestMapping("/")
public class ComonController extends BasicErrorController {
    public ComonController(ErrorAttributes errorAttributes, ErrorProperties errorProperties) {
        super(errorAttributes, errorProperties);
    }

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());


    @RequestMapping(path = "/error", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> responce(HttpServletRequest request) {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(getStatus(request));
        Map<String, Object> body = getErrorAttributes(request, isIncludeStackTrace(request, MediaType.ALL));
        return builder.body(body);
    }
}
