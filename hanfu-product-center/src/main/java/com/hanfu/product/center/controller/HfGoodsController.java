package com.hanfu.product.center.controller;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
import com.hanfu.product.center.dao.HfGoodsMapper;
import com.hanfu.product.center.dao.ProductMapper;
import com.hanfu.product.center.manual.model.HfGoodsDisplayInfo;
import com.hanfu.product.center.manual.model.HfProductDisplay;
import com.hanfu.product.center.model.HfGoods;
import com.hanfu.product.center.model.HfGoodsExample;
import com.hanfu.product.center.model.HfStone;
import com.hanfu.product.center.model.HfStoneExample;
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

}
