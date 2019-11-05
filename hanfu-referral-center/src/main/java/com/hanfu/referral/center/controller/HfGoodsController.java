package com.hanfu.referral.center.controller;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
 
import com.hanfu.referral.center.service.ReferralGoodsService;
import com.hanfu.referral.center.service.ReferralProductService;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseUtils;
import com.hanfu.utils.response.handler.ResponseEntity.BodyBuilder;

import io.swagger.annotations.Api;
@CrossOrigin
@RestController
@RequestMapping("/goods")
@Api
public class HfGoodsController {
	
	@Autowired
	private ReferralGoodsService referralGoodsService;
	
	@RequestMapping(path = "/findAllGoods",method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> findAllGoods() throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        return builder.body(ResponseUtils.getResponseBody(referralGoodsService.getAllGoods()));
    }
	
	@RequestMapping(path = "/findGoodsById",method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> findGoodsById(Integer goodsId) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        return builder.body(ResponseUtils.getResponseBody(referralGoodsService.getGoodsInfo(goodsId)));
    }
	
	@RequestMapping(path = "/findAllPicture",method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> findAllPictureId() throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        return builder.body(ResponseUtils.getResponseBody(referralGoodsService.findAllPicture()));
    }
	
//	@RequestMapping(path = "/getPicture",method = RequestMethod.GET)
//	public void getlunbotu(@RequestParam(name = "fileId") Integer fileId, HttpServletResponse response) throws Exception{
//        referralGoodsService.getPicture(fileId,response);
//	}
	
}
