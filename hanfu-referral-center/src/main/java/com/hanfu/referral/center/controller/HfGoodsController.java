package com.hanfu.referral.center.controller;

import com.hanfu.referral.center.service.ReferralGoodsService;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseEntity.BodyBuilder;
import com.hanfu.utils.response.handler.ResponseUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/goods")
@Api
public class HfGoodsController {

    @Autowired
    private ReferralGoodsService referralGoodsService;

    @RequestMapping(path = "/findAllGoods", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> findAllGoods(
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        return builder.body(ResponseUtils.getResponseBody(referralGoodsService.getAllGoods(page, size)));
    }

    @RequestMapping(path = "/findGoodsById", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> findGoodsById(Integer goodsId) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        return builder.body(ResponseUtils.getResponseBody(referralGoodsService.getGoodsInfo(goodsId)));
    }

    @RequestMapping(path = "/findAllPicture", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> findAllPictureId() throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        return builder.body(ResponseUtils.getResponseBody(referralGoodsService.findAllPicture()));
    }

//	@RequestMapping(path = "/getPicture",method = RequestMethod.GET)
//	public void getlunbotu(@RequestParam(name = "fileId") Integer fileId, HttpServletResponse response) throws Exception{
//        referralGoodsService.getPicture(fileId,response);
//	}

}
