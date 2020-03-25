package com.hanfu.product.center.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import com.hanfu.product.center.dao.*;
import com.hanfu.product.center.model.*;
import org.apache.curator.shaded.com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.hanfu.product.center.manual.model.HfGoodsDisplayInfo;
import com.hanfu.product.center.manual.model.HfProductDisplay;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseUtils;
import com.hanfu.utils.response.handler.ResponseEntity.BodyBuilder;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
@RequestMapping("/hf-goods")
@Api
public class HfGoodsController {

    @Autowired
    private HfGoodsMapper hfGoodsMapper;

    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private HfRespMapper hfRespMapper;
    @Autowired
    private HfPriceMapper hfPriceMapper;
    @Autowired
    private HfActivityMapper hfActivityMapper;
    @Autowired
    private HfActivityProductMapper hfActivityProductMapper;
    @ApiOperation(value = "商品列表", notes = "根据商品id删除商品列表")
    @RequestMapping(value = "/getHfGoodsByProductId", method = RequestMethod.GET)
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "query", name = "productId", value = "商品id", required = false,
            type = "Integer") })
    public ResponseEntity<JSONObject> getProductsForRotation(@RequestParam(name = "productId") Integer productId)
            throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        
        HfGoodsExample example = new HfGoodsExample();
        example.createCriteria().andProductIdEqualTo(productId);
        List<HfGoods> list = hfGoodsMapper.selectByExample(example);
        
        return builder.body(ResponseUtils.getResponseBody(list));
    }


//    @ApiOperation(value = "校检库存", notes = "校检库存")
//    @RequestMapping(value = "/checkResp", method = RequestMethod.POST)
//    public ResponseEntity<JSONObject> checkResp(Integer GoodsNum,Integer goodsId,Integer activityId)
//            throws JSONException {
//        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
//        if(goodsId!=null) {
//            HfGoods hfGoods= hfGoodsMapper.selectByPrimaryKey(goodsId);
//            HfActivityProductExample hfActivityProductExample = new HfActivityProductExample();
//            hfActivityProductExample.createCriteria().andActivityIdEqualTo(activityId).andProductIdEqualTo(hfGoods.getProductId());
//            if (activityId!=null){
//                Date date1 = new Date();
//                Date date2 = new Date();
//                SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                try {
//                    date1 = f.parse(f.format(new Date())); //这是获取当前时间
//                    date2 = f.parse(f.format(discountCoupon.getStartTime()));
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//
//            }
//            product(goodsId,GoodsNum);
//        }
//        return builder.body(ResponseUtils.getResponseBody("goods null"));
//    }

    private ResponseEntity<JSONObject> product(Integer goodsId, Integer GoodsNum) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HfRespExample exampleResp = new HfRespExample();
        exampleResp.createCriteria().andGoogsIdEqualTo(goodsId);
        if (hfRespMapper.selectByExample(exampleResp).get(0).getQuantity()<GoodsNum){
            return builder.body(ResponseUtils.getResponseBody("库存不足"));
        }
        HfPriceExample examplePrice= new HfPriceExample();
        examplePrice.createCriteria().andGoogsIdEqualTo(goodsId);
        hfPriceMapper.selectByExample(examplePrice);
        Amount amount = new Amount();
        amount.setGoodsId(goodsId);
        amount.setGoodsNum(hfRespMapper.selectByExample(exampleResp).get(0).getQuantity());
        amount.setMoney(hfPriceMapper.selectByExample(examplePrice).get(0).getSellPrice()*GoodsNum);
        amount.setDiscountMoney(hfPriceMapper.selectByExample(examplePrice).get(0).getSellPrice()*GoodsNum);
        return builder.body(ResponseUtils.getResponseBody(amount));
    }

}
