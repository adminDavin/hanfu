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


    @ApiOperation(value = "校检库存", notes = "校检库存")
    @RequestMapping(value = "/checkResp", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> checkResp(Integer GoodsNum,Integer goodsId,Integer activityId)
            throws JSONException {
        Amount amount = new Amount();
        amount.setGoodsId(goodsId);
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        if(goodsId!=null) {
            HfGoods hfGoods= hfGoodsMapper.selectByPrimaryKey(goodsId);
            HfActivityProductExample hfActivityProductExample = new HfActivityProductExample();
            hfActivityProductExample.createCriteria().andActivityIdEqualTo(activityId).andProductIdEqualTo(hfGoods.getProductId());
            List<HfActivityProduct> hfActivityProductList= hfActivityProductMapper.selectByExample(hfActivityProductExample);
            if (hfActivityProductList.size()!=0){
                if (hfActivityProductList.get(0).getInventoryCelling()!=null&&hfActivityProductList.get(0).getInventoryCelling()!=0){
                    if (hfActivityProductList.get(0).getInventoryCelling()<GoodsNum){
                        return builder.body(ResponseUtils.getResponseBody("understock"));
                    }
                    amount.setGoodsNum(hfActivityProductList.get(0).getInventoryCelling());
                } else {
                    if (selectPriceResp(goodsId).get("hfResps")<GoodsNum){
                        return builder.body(ResponseUtils.getResponseBody("understock"));
                    }
                    amount.setGoodsNum(selectPriceResp(goodsId).get("hfResps"));
                }
                if (hfActivityProductList.get(0).getFavoravlePrice()==null||hfActivityProductList.get(0).getFavoravlePrice()==0){
                  if (hfActivityProductList.get(0).getDiscountRatio()==null||hfActivityProductList.get(0).getDiscountRatio()==0){
                      return product(goodsId,GoodsNum);
                  } else {
                      amount.setMoney((int) (selectPriceResp(goodsId).get("hfPrices")*hfActivityProductList.get(0).getDiscountRatio())/100);
                      amount.setDiscountMoney((int) (selectPriceResp(goodsId).get("linePrice")*hfActivityProductList.get(0).getDiscountRatio())/100);
                      return builder.body(ResponseUtils.getResponseBody(amount));
                  }
                } else {
                    amount.setMoney((int) (selectPriceResp(goodsId).get("hfPrices")-hfActivityProductList.get(0).getFavoravlePrice()));
                    amount.setDiscountMoney((int) (selectPriceResp(goodsId).get("linePrice")-hfActivityProductList.get(0).getFavoravlePrice()));
                    return builder.body(ResponseUtils.getResponseBody(amount));
                }
            }
            return product(goodsId,GoodsNum);
        }
        return builder.body(ResponseUtils.getResponseBody("goods null"));
    }

    private ResponseEntity<JSONObject> product(Integer goodsId, Integer GoodsNum) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        if (selectPriceResp(goodsId).get("hfResps")<GoodsNum){
            return builder.body(ResponseUtils.getResponseBody("understock"));
        }
        Amount amount = new Amount();
        amount.setGoodsId(goodsId);
        amount.setGoodsNum(selectPriceResp(goodsId).get("hfResps"));
        amount.setMoney(selectPriceResp(goodsId).get("hfPrices")*GoodsNum);
        amount.setDiscountMoney(selectPriceResp(goodsId).get("linePrice")*GoodsNum);
        return builder.body(ResponseUtils.getResponseBody(amount));
    }


    private Map<String, Integer> selectPriceResp(Integer goodsId) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        //价格
        HfPriceExample examplePrice= new HfPriceExample();
        examplePrice.createCriteria().andGoogsIdEqualTo(goodsId);
        List<HfPrice> hfPrices = hfPriceMapper.selectByExample(examplePrice);
        //数量
        HfRespExample exampleResp = new HfRespExample();
        exampleResp.createCriteria().andGoogsIdEqualTo(goodsId).andIsDeletedEqualTo((short) 0);
        List<HfResp> hfResps= hfRespMapper.selectByExample(exampleResp);


        Map<String, Integer> params = new HashMap<>();
        params.put("hfPrices", hfPrices.get(0).getSellPrice());
        System.out.println(hfPrices.get(0).getSellPrice());
        System.out.println(hfResps.get(0).getQuantity());
        System.out.println(hfPrices.get(0).getLinePrice());
        params.put("hfResps", hfResps.get(0).getQuantity());
        params.put("linePrice",hfPrices.get(0).getLinePrice());

        return params;
    }

}
