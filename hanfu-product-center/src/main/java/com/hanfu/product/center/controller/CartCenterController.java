package com.hanfu.product.center.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hanfu.product.center.cart.model.Cart;
import com.hanfu.product.center.cart.model.Product;
import com.hanfu.product.center.cart.service.CartService;
import com.hanfu.product.center.cart.service.ProductService;
import com.hanfu.product.center.cart.utils.CartPrefix;
import com.hanfu.product.center.manual.model.CartList;
import com.hanfu.product.center.manual.model.ProductStone;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseEntity.BodyBuilder;
import com.hanfu.utils.response.handler.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/cart")
@Api
public class CartCenterController {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    CartService cartService;
    @Autowired
    ProductService productService;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @RequestMapping(path = "/add", method = RequestMethod.GET)
    @ApiOperation(value = "添加购物车", notes = "添加购物车")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户Id", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "goodsId", value = "商品Id", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "num", value = "商品数量", required = true, type = "Integer"),
    })
    public ResponseEntity<JSONObject> addCart(Integer userId, Integer goodsId, Integer num, Integer stoneId, Integer type) throws Exception {
        BodyBuilder builder = ResponseUtils.getBodyBuilder();
        int effectNum = cartService.addCart(userId.toString(), goodsId.toString(), num, stoneId, type);
        if(effectNum == -1) {
        	return builder.body(ResponseUtils.getResponseBody("已经存在常买"));
        }
        if (effectNum <= 0) {
            return builder.body(ResponseUtils.getResponseBody("添加购物车失败"));
        }
        return builder.body(ResponseUtils.getResponseBody("成功加入购物车"));
    }

    @RequestMapping(path = "/getCartList", method = RequestMethod.GET)
    @ApiOperation(value = "获取购物车列表", notes = "获取购物车列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户Id", required = true, type = "Integer"),
    })
    public ResponseEntity<JSONObject> getCartList(Integer userId,Integer type) throws Exception {
        BodyBuilder builder = ResponseUtils.getBodyBuilder();
        Boolean exists = false;
        List<Cart> cartDtoList = cartService.getCartList(userId.toString(),type);
        List<CartList> result = new ArrayList<CartList>();
        Map<String, List<Cart>> resultList = cartDtoList.stream().collect(Collectors.groupingBy(Cart::getStoneName));
        Set<Entry<String, List<Cart>>> set = resultList.entrySet();
        for (Entry<String, List<Cart>> entry : set) {
            CartList cartList = new CartList();
            cartList.setName(entry.getKey());
            cartList.setGoodList(entry.getValue());
            result.add(cartList);
        }
        return builder.body(ResponseUtils.getResponseBody(result));
    }
    
    @RequestMapping(path = "/getCartListInfo", method = RequestMethod.GET)
    @ApiOperation(value = "获取购物车基础数据", notes = "获取购物车基础数据")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户Id", required = true, type = "Integer"),
    })
    public ResponseEntity<JSONObject> getCartListInfo(Integer userId) throws Exception {
        BodyBuilder builder = ResponseUtils.getBodyBuilder();
        CartList list = new CartList();
        list = cartService.cartInfo(userId.toString());
        return builder.body(ResponseUtils.getResponseBody(list));
    }

    @RequestMapping(path = "/updateCartNum", method = RequestMethod.GET)
    @ApiOperation(value = "修改购物车数量", notes = "修改购物车数量")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户Id", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "goodsId", value = "商品Id", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "num", value = "商品数量", required = true, type = "Integer"),
    })
    public ResponseEntity<JSONObject> updateCartNum(Integer userId, Integer goodsId, Integer num, Integer stoneId, Integer type) throws Exception {
        BodyBuilder builder = ResponseUtils.getBodyBuilder();
        int effectNum = cartService.updateCartNum(userId.toString(), goodsId.toString(), num, stoneId, type);
        if (effectNum <= 0) {
            return builder.body(ResponseUtils.getResponseBody("修改数量失败"));
        }
        return builder.body(ResponseUtils.getResponseBody("修改数量成功"));
    }

//    @RequestMapping(path = "/delCartProduct", method = RequestMethod.GET)
//    @ApiOperation(value = "删除商品数量", notes = "删除商品数量")
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户Id", required = true, type = "Integer"),
//            @ApiImplicitParam(paramType = "query", name = "goodsId", value = "商品Id", required = true, type = "Integer"),
//    })
//    public ResponseEntity<JSONObject> delCartProduct(Integer goodsId, Integer userId, Integer stontId) throws Exception {
//        BodyBuilder builder = ResponseUtils.getBodyBuilder();
//        int effectNum = cartService.delCartProduct(userId.toString(), goodsId.toString(), stontId.toString());
//        if (effectNum <= 0) {
//            return builder.body(ResponseUtils.getResponseBody(""));
//        }
//        return builder.body(ResponseUtils.getResponseBody(""));
//    }

    @RequestMapping(path = "/checkAll", method = RequestMethod.GET)
    @ApiOperation(value = "选择物品", notes = "选择物品")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户Id", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "check", value = "勾选框", required = false, type = "Integer"),
    })
    public ResponseEntity<JSONObject> checkAll(Integer userId, Integer check) throws Exception {
        BodyBuilder builder = ResponseUtils.getBodyBuilder();
        int effectNum = cartService.checkAll(userId.toString(), check.toString());
        if (effectNum <= 0) {
            return builder.body(ResponseUtils.getResponseBody("选择物品失败"));
        }
        return builder.body(ResponseUtils.getResponseBody("选择物品成功"));
    }

    @RequestMapping(path = "/remove", method = RequestMethod.GET)
    @ApiOperation(value = "清空购物车", notes = "清空购物车")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户Id", required = false, type = "Integer"),
    })
    public ResponseEntity<JSONObject> removeCart(Integer userId) throws Exception {
        BodyBuilder builder = ResponseUtils.getBodyBuilder();
        int effectNum = cartService.delCart(userId.toString());
        if (effectNum <= 0) {
            return builder.body(ResponseUtils.getResponseBody("清空购物车失败"));
        }
        return builder.body(ResponseUtils.getResponseBody("清空购物车成功"));
    }

    @RequestMapping(path = "/delGoods", method = RequestMethod.GET)
    @ApiOperation(value = "删除物品", notes = "删除物品")
    public ResponseEntity<JSONObject> delGoods(Integer userId, Integer productId, Integer stoneId, String productStoneId, Integer type) throws Exception {
        BodyBuilder builder = ResponseUtils.getBodyBuilder();


        if (productStoneId != null) {
            JSONArray jsonArray = JSONArray.parseArray(productStoneId);
            //转list
            List<ProductStone> list = JSONObject.parseArray(jsonArray.toJSONString(), ProductStone.class);
            for (ProductStone productStone:list){
//            list.forEach(lists->{
                int effectNum = cartService.delCartProduct(userId.toString(), productStone.getProductId().toString(),productStone.getStoneId().toString(),type);
                if (effectNum <= 0) {
                    return builder.body(ResponseUtils.getResponseBody("删除物品失败"));
                }
            }
        }else {
            int effectNum = cartService.delCartProduct(userId.toString(), productId.toString(), stoneId.toString(), type);
            if (effectNum <= 0) {
                return builder.body(ResponseUtils.getResponseBody("删除物品失败"));
            }
        }

        return builder.body(ResponseUtils.getResponseBody("删除物品成功"));

//        return builder.body(ResponseUtils.getResponseBody(""));
    }

    @RequestMapping(path = "/settlemen", method = RequestMethod.POST)
    @ApiOperation(value = "结算", notes = "结算")
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public ResponseEntity<JSONObject> Settlemen(Integer userId, JSONObject productMessage) throws Exception {
        BodyBuilder builder = ResponseUtils.getBodyBuilder();
        redisTemplate.opsForValue().set(userId.toString(), productMessage);
        redisTemplate.opsForValue().get(userId.toString());
        return builder.body(ResponseUtils.getResponseBody(""));
    }

    @RequestMapping(path = "/selSettlemen", method = RequestMethod.POST)
    @ApiOperation(value = "查看结算", notes = "查看结算")
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public ResponseEntity<JSONObject> selSettlemen(Integer userId) throws Exception {
        BodyBuilder builder = ResponseUtils.getBodyBuilder();
        return builder.body(ResponseUtils.getResponseBody(redisTemplate.opsForValue().get(userId.toString())));
    }

    @ApiOperation(value = "设为常买", notes = "设为常买")
    @RequestMapping(value = "/OftenBuy", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户id", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "goodsId", value = "物品id", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "stoneId", value = "店铺id", required = true, type = "Integer")})
    public ResponseEntity<JSONObject> OftenBuy(Integer userId, Integer goodsId, Integer stoneId)
            throws Exception {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        int effectNum = productService.addCart("often" + userId.toString(), goodsId.toString());
        if (effectNum <= 0) {
            return builder.body(ResponseUtils.getResponseBody("设置失败"));
        }
        return builder.body(ResponseUtils.getResponseBody("设置成功"));
    }

    @ApiOperation(value = "查看常买", notes = "查看常买")
    @RequestMapping(value = "/selectOftenBuy", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户id", required = true, type = "Integer")})
    public ResponseEntity<JSONObject> selectOftenBuy(Integer userId, Integer goodsId)
            throws Exception {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        List<Product> cartDtoList = productService.getCartList("often" + userId.toString());
        return builder.body(ResponseUtils.getResponseBody(cartDtoList));
    }

//    @ApiOperation(value = "取消常买", notes = "取消常买")
//    @RequestMapping(value = "/delOftenbuy", method = RequestMethod.GET)
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户id", required = true, type = "Integer"),
//            @ApiImplicitParam(paramType = "query", name = "goodsId", value = "物品id", required = true, type = "Integer"),})
//    public ResponseEntity<JSONObject> delOftenbuy(Integer userId, Integer goodsId, Integer stoneId)
//            throws Exception {
//        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
//        int effectNum = cartService.delCartProduct("often" + userId.toString(), goodsId.toString(), stoneId.toString());
//        if (effectNum <= 0) {
//            return builder.body(ResponseUtils.getResponseBody("取消失败"));
//        }
//        return builder.body(ResponseUtils.getResponseBody("取消成功"));
//    }

    @ApiOperation(value = "设置关注", notes = "设置关注")
    @RequestMapping(value = "/Concern", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "openId", value = "openid", required = true, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "goodsId", value = "物品id", required = true, type = "Integer")})
    public ResponseEntity<JSONObject> Concern(String openId, Integer goodsId)
            throws Exception {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        redisTemplate.opsForValue().set("attention" + openId, goodsId);
        return builder.body(ResponseUtils.getResponseBody("关注成功"));
    }

//    @ApiOperation(value = "取消关注", notes = "取消关注")
//    @RequestMapping(value = "/delConcern", method = RequestMethod.GET)
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "query", name = "openId", value = "openid", required = true, type = "String"),
//            @ApiImplicitParam(paramType = "query", name = "goodsId", value = "goodsId", required = true, type = "Integer"),})
//    public ResponseEntity<JSONObject> delConcern(String openId, Integer goodsId, Integer stoneId)
//            throws Exception {
//        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
//        int effectNum = cartService.delCartProduct("attention" + openId, goodsId.toString(), stoneId.toString());
//        if (effectNum <= 0) {
//            return builder.body(ResponseUtils.getResponseBody("取消失败"));
//        }
//        return builder.body(ResponseUtils.getResponseBody("取消成功"));
//    }

    @ApiOperation(value = "查看关注", notes = "查看关注")
    @RequestMapping(value = "/selectConcern", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "openId", value = "openId", required = true, type = "Integer")})
    public ResponseEntity<JSONObject> selectConcern(Integer openId)
            throws Exception {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        return builder.body(ResponseUtils.getResponseBody(redisTemplate.opsForValue().get("attention" + openId)));
    }
    public static void main(String[] args) {
		System.out.println(JSONArray.parseArray("[{\"name\":\"3\",\"ratio\":\"10\"}]"));
	}
}
