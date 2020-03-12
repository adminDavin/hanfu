package com.hanfu.payment.client.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hanfu.inner.sdk.product.center.HelloTestService;
import com.hanfu.inner.sdk.product.center.ProductService;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseUtils;
import com.hanfu.utils.response.handler.ResponseEntity.BodyBuilder;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/product")
@Api
public class HelloController {
    @Reference(registry = "dubboProductServer", url = "dubbo://127.0.0.1:1900/com.hanfu.inner.sdk.product.center.HelloTestService")
    private HelloTestService helloTestService;


    @RequestMapping(path = "/", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> responce(HttpServletRequest request) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        helloTestService.test();
        return builder.body(ResponseUtils.getResponseBody("hello"));
    }
}
