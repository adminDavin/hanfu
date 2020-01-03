package com.hanfu.cart.center.controller;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSONObject;
import com.hanfu.cart.center.model.Cart;
import com.hanfu.cart.center.service.CartService;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseEntity.BodyBuilder;
import com.hanfu.utils.response.handler.ResponseUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
@RequestMapping("/cart")
@Api
public class CartCenterController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	CartService cartService;
	@Resource
    private RedisTemplate<String, Object> redisTemplate;
	@RequestMapping(path = "/add",  method = RequestMethod.GET)
    @ApiOperation(value = "添加购物车", notes = "添加购物车")
    @ApiImplicitParams({  
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户Id", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "goodsId", value = "商品Id", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "num", value = "商品数量", required = true, type = "Integer"),
    })
    public ResponseEntity<JSONObject> addCart(Integer userId,Integer goodsId, Integer  num) throws Exception{
        BodyBuilder builder = ResponseUtils.getBodyBuilder();
        int effectNum = cartService.addCart(userId.toString(),goodsId.toString(),num);
        if (effectNum<=0){ 
            return builder.body(ResponseUtils.getResponseBody("添加购物车失败"));
        }
        return builder.body(ResponseUtils.getResponseBody("成功加入购物车"));
    }
    @RequestMapping(path = "/getCartList",  method = RequestMethod.GET)
    @ApiOperation(value = "获取购物车列表", notes = "获取购物车列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户Id", required = true, type = "Integer"),
    })
    public ResponseEntity<JSONObject> getCartList(Integer  userId) throws Exception{
        BodyBuilder builder = ResponseUtils.getBodyBuilder();
        List<Cart> cartDtoList = cartService.getCartList(userId.toString());
        return builder.body(ResponseUtils.getResponseBody(cartDtoList));
    }
    @RequestMapping(path = "/updateCartNum",  method = RequestMethod.GET)
    @ApiOperation(value = "修改购物车数量", notes = "修改购物车数量")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户Id", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "goodsId", value = "商品Id", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "num", value = "商品数量", required = true, type = "Integer"),
    })
    public ResponseEntity<JSONObject> updateCartNum(Integer userId,Integer goodsId, Integer  num) throws Exception{
        BodyBuilder builder = ResponseUtils.getBodyBuilder();
        int effectNum = cartService.updateCartNum(userId.toString(),goodsId.toString(),num);
        if (effectNum <=0){
            return builder.body(ResponseUtils.getResponseBody("修改数量失败"));
        }
        return builder.body(ResponseUtils.getResponseBody("修改数量成功"));
    }
    @RequestMapping(path = "/delCartProduct",  method = RequestMethod.GET)
    @ApiOperation(value = "删除商品数量", notes = "删除商品数量")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户Id", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "goodsId", value = "商品Id", required = true, type = "Integer"),
    })
    public ResponseEntity<JSONObject> delCartProduct(Integer goodsId, Integer userId) throws Exception{
        BodyBuilder builder = ResponseUtils.getBodyBuilder();
        int effectNum = cartService.delCartProduct(userId.toString(),goodsId.toString());
        if (effectNum <=0){
            return builder.body(ResponseUtils.getResponseBody(""));
        }
        return builder.body(ResponseUtils.getResponseBody(""));
    }
    @RequestMapping(path = "/checkAll",  method = RequestMethod.GET)
    @ApiOperation(value = "选择商品", notes = "选择商品")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户Id", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "check", value = "勾选框", required = false, type = "Integer"),
    })
    public ResponseEntity<JSONObject> checkAll( Integer userId,Integer check) throws Exception{
        BodyBuilder builder = ResponseUtils.getBodyBuilder();
        int effectNum = cartService.checkAll(userId.toString(),check.toString());
        if (effectNum <=0){
            return builder.body(ResponseUtils.getResponseBody("选择商品失败"));
        }
        return builder.body(ResponseUtils.getResponseBody("选择商品成功"));
    }
    @RequestMapping(path = "/remove",  method = RequestMethod.GET)
    @ApiOperation(value = "清空购物车", notes = "清空购物车")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户Id", required = false, type = "Integer"),
    })
    public ResponseEntity<JSONObject> removeCart( Integer userId) throws Exception{
        BodyBuilder builder = ResponseUtils.getBodyBuilder();
        int effectNum = cartService.delCart(userId.toString());
        if (effectNum <=0){
            return builder.body(ResponseUtils.getResponseBody("清空购物车失败")); 
        }
        return builder.body(ResponseUtils.getResponseBody("清空购物车成功"));
    } 
    @RequestMapping(path = "/delGoods",  method = RequestMethod.GET)
    @ApiOperation(value = "删除商品", notes = "删除商品")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户Id", required = false, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "productId", value = "商品Id", required = false, type = "Integer"),
    })
    public ResponseEntity<JSONObject> delGoods( Integer userId,Integer productId) throws Exception{
        BodyBuilder builder = ResponseUtils.getBodyBuilder();
        int effectNum = cartService.delCartProduct(userId.toString(), productId.toString());
        if (effectNum <=0){
            return builder.body(ResponseUtils.getResponseBody("删除商品失败")); 
        }
        return builder.body(ResponseUtils.getResponseBody("删除商品成功"));
    } 
    @RequestMapping(path = "/Settlemen",  method = RequestMethod.GET)
    @ApiOperation(value = "结算", notes = "结算")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户Id", required = false, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "productMessage", value = "商品信息", required = false, type = "String[]"),
    })
    public ResponseEntity<JSONObject> Settlemen( Integer userId,String[] productMessage) throws Exception{
        BodyBuilder builder = ResponseUtils.getBodyBuilder();
        redisTemplate.opsForValue().set(userId.toString(), productMessage);
        return builder.body(ResponseUtils.getResponseBody(redisTemplate.opsForValue().get(userId.toString())));
    } 
}
