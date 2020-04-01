package com.hanfu.order.center.controller;

import com.hanfu.order.center.dao.HfOrderLogisticsMapper;
import com.hanfu.order.center.manual.model.Logistics;
import com.hanfu.order.center.model.HfOrderLogistics;
import com.hanfu.order.center.model.HfOrderLogisticsExample;
import org.springframework.beans.factory.annotation.Autowired;
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

import java.util.*;

@CrossOrigin
@RestController
@RequestMapping("/query")
@Api
public class QueryLogisticsController {
    @Autowired
    private HfOrderLogisticsMapper hfOrderLogisticsMapper;
    @ApiOperation(value = "查询物流", notes = "查询物流")
    @RequestMapping(value = "/logistics", method = RequestMethod.GET)
    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "query", name = "expCode", value = "快递名称", required = true, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "orderId", value = "订单号", required = true, type = "Integer")
    })
    public ResponseEntity<JSONObject> logistics(Integer orderId)
            throws Exception {
        HfOrderLogisticsExample hfOrderLogisticsExample = new HfOrderLogisticsExample();
        hfOrderLogisticsExample.createCriteria().andOrdersIdEqualTo(orderId).andIsDeletedEqualTo((short) 0);
        List<HfOrderLogistics> hfOrderLogistics= hfOrderLogisticsMapper.selectByExample(hfOrderLogisticsExample);
        String expNo=hfOrderLogistics.get(0).getLogisticsOrdersId();
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        KdniaoTrackQueryAPI kdniaoTrackQueryAPI = new KdniaoTrackQueryAPI();
        try {
            String result = kdniaoTrackQueryAPI.getOrderTracesByJson(logisticselect(expNo).get("ShipperCode"), expNo);
            System.out.println(result);
            JSONObject json = JSONObject.parseObject(result);
//            result = result.substring(0, result.length() - 1);
//            result = result.substring(1, result.length());
            Logistics logistics = new Logistics();
            logistics.setLogisticCode(json.get("LogisticCode"));
            logistics.setShipperCode(json.get("ShipperCode"));
            logistics.setTraces(json.get("Traces"));
            logistics.setState(json.get("State"));
            logistics.seteBusinessID(json.get("EBusinessID"));
            logistics.setCompany(logisticselect(expNo).get("ShipperName"));
            return builder.body(ResponseUtils.getResponseBody(logistics));
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
    public Map<String,String> logisticselect(String expNo)
            throws Exception {
        String ShipperCode = null;
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        KdniaoTrackQueryAPI kdniaoTrackQueryAPI = new KdniaoTrackQueryAPI();
        Map<String,String> map = new HashMap<>();
        try {
            String result = kdniaoTrackQueryAPI.getOrderTracesByJson(expNo);
            JSONObject json = JSONObject.parseObject(result);
//            System.out.println(json.getString("Shippers"));
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
                map.put("ShipperCode",value);
//                map.put("ShipperName",value)
//                ShipperCode=value;
            }
            if (key.equals("ShipperName")){
//                map.put("ShipperCode",key);
                map.put("ShipperName",value);
//                ShipperCode=value;
            }
//            System.out.println(map);
		}
//            System.out.println(ShipperCode);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("0","0");
            return map;
        }

    }
}
