package com.hanfu.order.center.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.hanfu.order.center.utils.KdniaoTrackQueryAPI;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseUtils;
import com.hanfu.utils.response.handler.ResponseEntity.BodyBuilder;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.ArrayList;
import java.util.Iterator;

@CrossOrigin
@RestController
@RequestMapping("/query")
@Api
public class QueryLogisticsController {
    @ApiOperation(value = "查询物流", notes = "查询物流")
    @RequestMapping(value = "/logistics", method = RequestMethod.GET)
    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "query", name = "expCode", value = "快递名称", required = true, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "expNo", value = "快递单号", required = true, type = "String")
    })
    public ResponseEntity<JSONObject> logistics( String expNo)
            throws Exception {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        KdniaoTrackQueryAPI kdniaoTrackQueryAPI = new KdniaoTrackQueryAPI();
        try {
            String result = kdniaoTrackQueryAPI.getOrderTracesByJson(logisticselect(expNo), expNo);
            JSONObject json = JSONObject.parseObject(result);
            return builder.body(ResponseUtils.getResponseBody(json));
        } catch (Exception e) {
            e.printStackTrace();
            return builder.body(ResponseUtils.getResponseBody("查询失败"));
        }

    }

//    @ApiOperation(value = "单号识别", notes = "查询物流")
//    @RequestMapping(value = "/logisticselect", method = RequestMethod.GET)
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "query", name = "expNo", value = "快递单号", required = true, type = "String")
//    })
    public String logisticselect(String expNo)
            throws Exception {
        String ShipperCode = null;
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        KdniaoTrackQueryAPI kdniaoTrackQueryAPI = new KdniaoTrackQueryAPI();
        try {
            String result = kdniaoTrackQueryAPI.getOrderTracesByJson(expNo);
            JSONObject json = JSONObject.parseObject(result);
            System.out.println(json.getString("Shippers"));
            String a= json.getString("Shippers").replace("[","");
            String b= a.replace("]","");

            JSONObject specs = JSONObject.parseObject(b);
		Iterator<String> iterator = specs.keySet().iterator();
		ArrayList<String> strings = new ArrayList<>();

		while(iterator.hasNext()){
// 获得key
			String key = iterator.next();
			String value = specs.getString(key);
			strings.add(key);
			System.out.println("key: "+key+",value:"+value);
            if (key.equals("ShipperCode")){
                ShipperCode=value;
            }
		}
            System.out.println(ShipperCode);
            return ShipperCode;
        } catch (Exception e) {
            e.printStackTrace();
            return "查询失败";
        }

    }
}
