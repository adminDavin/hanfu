package com.hanfu.referral.center.controller;

import com.hanfu.referral.center.service.ReferralOrderService;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseEntity.BodyBuilder;
import com.hanfu.utils.response.handler.ResponseUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/order")
@Api
public class HfOrderController {
    @Autowired
    private ReferralOrderService referralOrderService;

    @RequestMapping(path = "/findAllOrder", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> findAllOrder(Integer userId) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        return builder.body(ResponseUtils.getResponseBody(referralOrderService.getOrderByUserId(userId)));
    }
}
