package com.hanfu.product.center.cart.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hanfu.product.center.cart.manual.dao.HfGoodDao;
import com.hanfu.product.center.cart.manual.model.HfGoods;
import com.hanfu.product.center.cart.model.Product;
import com.hanfu.product.center.cart.service.ProductService;
import com.hanfu.product.center.cart.service.RedisService;
import com.hanfu.product.center.cart.utils.CartPrefix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service

public class ProductServiceImpl implements ProductService {

	@Autowired

	RedisService redisService;

	@Autowired

	HfGoodDao hfGoodDao;

	  @Override

	    public int addCart(String userId, String goodsId) {

	        //key为 userId_cart,校验是否已存在

	        Boolean exists = redisService.existsValue(CartPrefix.getCartList,userId,goodsId);

	        if (exists){

	            //获取现有的购物车中的数据

	            String json = redisService.hget(CartPrefix.getCartList,userId,goodsId);

	            if (json !=null){

	                //转换为java实体类

	            	Product cart = JSON.toJavaObject(JSONObject.parseObject(json),Product.class);

	                redisService.hset(CartPrefix.getCartList,userId,goodsId,JSON.toJSON(cart).toString());

	            }else {

	                return 0;

	            }

	            return 1;

	        }

	        //根据商品id获取商品

	        HfGoods hfGoods = hfGoodDao.findProductById(goodsId);

	        if (hfGoods==null){

	            return 0;

	        }

	        //设置购物车值

	        Product cart = new Product();

	        cart.setProductId(goodsId);

	        cart.setProductName(hfGoods.getGoodName());

	        cart.setProductPrice(hfGoods.getSellPrice());

	        cart.setProductStatus(hfGoods.getFrames());

	        cart.setCheck("0");

	        cart.setProductIcon(hfGoods.getFileId());

	        redisService.hset(CartPrefix.getCartList,userId,goodsId,JSON.toJSON(cart).toString());

	        return 1;

	    }

	    /**

	     * 展示购物车

	     * @param userId

	     * @return

	     */

	    @Override

	    public List<Product> getCartList(String userId) {

	        List<String> jsonList = redisService.hvals(CartPrefix.getCartList,userId);

	        List<Product> cartDtoList = new LinkedList<>();

	        for (String json:jsonList){

	        	Product cartDto = JSON.toJavaObject(JSONObject.parseObject(json),Product.class);

	            cartDtoList.add(cartDto);

	        }

	        return cartDtoList;

	    }



	    /**

	     * 删除商品

	     * @param userId

	     * @param

	     * @return

	     */

	    @Override

	    public int delCartProduct(String userId, String goodsId) {

	        redisService.hdel(CartPrefix.getCartList,userId,goodsId);

	        return 1;

	    }

}