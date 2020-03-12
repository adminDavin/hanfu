package com.hanfu.cart.center.service.impl;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hanfu.cart.center.manual.dao.HfGoodsDao;
import com.hanfu.cart.center.manual.model.HfGoods;
import com.hanfu.cart.center.model.Cart;
import com.hanfu.cart.center.service.CartService;
import com.hanfu.cart.center.service.RedisService;
import com.hanfu.cart.center.utils.CartPrefix;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    RedisService redisService;
    @Autowired
    HfGoodsDao hfGoodsDao;

    @Override
    public int addCart(String userId, String productId, int num) {
        //key为 userId_cart,校验是否已存在
        Boolean exists = redisService.existsValue(CartPrefix.getCartList, userId, productId);
        if (exists) {
            //获取现有的购物车中的数据
            String json = redisService.hget(CartPrefix.getCartList, userId, productId);
            if (json != null) {
                //转换为java实体类
                Cart cart = JSON.toJavaObject(JSONObject.parseObject(json), Cart.class);
                cart.setProductNum(cart.getProductNum() + num);
                redisService.hset(CartPrefix.getCartList, userId, productId, JSON.toJSON(cart).toString());
            } else {
                return 0;
            }
            return 1;
        }
        //根据商品id获取商品
        HfGoods hfGoods = hfGoodsDao.findProductById(productId);
        if (hfGoods == null) {
            return 0;
        }
        //设置购物车值
        Cart cart = new Cart();
        cart.setProductId(productId);
        cart.setProductName(hfGoods.getGoodName());
        cart.setProductPrice(hfGoods.getSellPrice());
        cart.setProductNum(num);
        cart.setCheck("1");
        cart.setProductStatus(hfGoods.getIsDeleted());
        cart.setProductIcon(hfGoods.getFileId());
        redisService.hset(CartPrefix.getCartList, userId, productId, JSON.toJSON(cart).toString());
        return 1;
    }

    /**
     * 展示购物车
     *
     * @param userId
     * @return
     */
    @Override
    public List<Cart> getCartList(String userId) {
        List<String> jsonList = redisService.hvals(CartPrefix.getCartList, userId);
        List<Cart> cartDtoList = new LinkedList<>();
        for (String json : jsonList) {
            Cart cartDto = JSON.toJavaObject(JSONObject.parseObject(json), Cart.class);
            cartDtoList.add(cartDto);
        }
        return cartDtoList;
    }

    /**
     * 更新数量
     *
     * @param userId
     * @param productId
     * @param num
     * @return
     */
    @Override
    public int updateCartNum(String userId, String productId, int num) {
        String json = redisService.hget(CartPrefix.getCartList, userId, productId);
        if (json == null) {
            return 0;
        }
        Cart cartDto = JSON.toJavaObject(JSONObject.parseObject(json), Cart.class);
        cartDto.setProductNum(num);
        redisService.hset(CartPrefix.getCartList, userId, productId, JSON.toJSON(cartDto).toString());
        return 1;
    }

    /**
     * 全选商品
     *
     * @param userId
     * @param checked
     * @return
     */
    @Override
    public int checkAll(String userId, String checked) {
        //获取商品列表
        List<String> jsonList = redisService.hvals(CartPrefix.getCartList, userId);
        for (String json : jsonList) {
            Cart cartDto = JSON.toJavaObject(JSONObject.parseObject(json), Cart.class);
            if ("true".equals(checked)) {
                cartDto.setCheck("1");
            } else if ("false".equals(checked)) {
                cartDto.setCheck("0");
            } else {
                return 0;
            }
            redisService.hset(CartPrefix.getCartList, userId, cartDto.getProductId(), JSON.toJSON(cartDto).toString());
        }
        return 1;
    }

    /**
     * 删除商品
     *
     * @param userId
     * @param productId
     * @return
     */
    @Override
    public int delCartProduct(String userId, String productId) {
            redisService.hdel(CartPrefix.getCartList, userId, productId);
            return 1;
    }

    /**
     * 清空购物车
     *
     * @param userId
     * @return
     */
    @Override
    public int delCart(String userId) {
        redisService.delete(CartPrefix.getCartList, userId);
        return 1;
    }

}
