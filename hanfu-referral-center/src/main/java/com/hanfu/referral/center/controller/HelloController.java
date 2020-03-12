package com.hanfu.referral.center.controller;

import com.hanfu.referral.center.service.ReferralHelloService;
import com.hanfu.referral.center.service.ReferralProductService;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseEntity.BodyBuilder;
import com.hanfu.utils.response.handler.ResponseUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/product")
@Api
public class HelloController {

    @Autowired
    private ReferralHelloService referralHelloService;
    @Autowired
    private ReferralProductService referralProductService;

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> responce(HttpServletRequest request) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        referralHelloService.hello();
        return builder.body(ResponseUtils.getResponseBody(referralProductService.getAllProduct()));
    }

    @RequestMapping(path = "/getYear", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> getYear() throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        return builder.body(ResponseUtils.getResponseBody(referralHelloService.getYear()));
    }
}
