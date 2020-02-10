package com.hanfu.referral.center.controller;

import com.hanfu.referral.center.service.PermissionService;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/Tset111")
@Api
public class Ptest {
    @Autowired
    private PermissionService permissionService;
    @RequestMapping(path = "/test12345", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> test123() throws JSONException {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        return builder.body(ResponseUtils.getResponseBody(permissionService.test()));
    }
}
