package com.hanfu.product.center.cart.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hanfu.product.center.cart.manual.dao.HfGoodDao;
import com.hanfu.product.center.cart.manual.model.HfGoods;
import com.hanfu.product.center.cart.model.Cart;
import com.hanfu.product.center.cart.service.CartService;
import com.hanfu.product.center.cart.service.RedisService;
import com.hanfu.product.center.cart.utils.CartPrefix;
import com.hanfu.product.center.dao.*;
import com.hanfu.product.center.model.HfGoodsSpec;
import com.hanfu.product.center.model.HfGoodsSpecExample;
import com.hanfu.product.center.model.HfStone;
import com.hanfu.product.center.model.ProductSpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    RedisService redisService;
    @Autowired
    HfGoodDao hfGoodDao;
    @Autowired
    ProductMapper productMapper;
    @Autowired
    private HfGoodsSpecMapper hfGoodsSpecMapper;
    @Autowired
    private ProductSpecMapper productSpecMapper;
    @Autowired
    private ProductInstanceMapper productInstanceMapper;
    @Autowired
    private HfStoneMapper hfStoneMapper;

    @Override
    public int addCart(String userId, String productId, int num ,Integer stoneId) {
        //key为 userId_cart,校验是否已存在
        Boolean exists = redisService.existsValue(CartPrefix.getCartList, userId, productId+String.valueOf(stoneId));
        if (exists) {
            //获取现有的购物车中的数据
            String json = redisService.hget(CartPrefix.getCartList, userId, productId+String.valueOf(stoneId));
            if (json != null) {
                //转换为java实体类
                Cart cart = JSON.toJavaObject(JSONObject.parseObject(json), Cart.class);
                cart.setProductNum(cart.getProductNum() + num);
                redisService.hset(CartPrefix.getCartList, userId, productId+String.valueOf(stoneId), JSON.toJSON(cart).toString());
            } else {
                return 0;
            }
            return 1;
        }
        //根据商品id获取商品
        HfGoods hfGoods = hfGoodDao.findProductById(productId);
        if (hfGoods == null) {
            return 0;
        }
        Cart cart = new Cart();
        HfGoodsSpecExample hfGoodsSpecExample = new HfGoodsSpecExample();
        hfGoodsSpecExample.createCriteria().andGoodsIdEqualTo(hfGoods.getId());
        List<HfGoodsSpec> hfGoodsSpecs= hfGoodsSpecMapper.selectByExample(hfGoodsSpecExample);
        List<Map<String,String>> list = new ArrayList<>();
        hfGoodsSpecs.forEach(hfGoodsSpec -> {
            Map<String,String> map = new HashMap<>();
            ProductSpec productSpec= productSpecMapper.selectByPrimaryKey(Integer.valueOf(hfGoodsSpec.getHfSpecId()));
            map.put("productSpec",productSpec.getHfName());
            map.put("productUnit",productSpec.getSpecUnit());
            map.put("goodsSpec",hfGoodsSpec.getHfValue());
            list.add(map);
            cart.setGoodsSpec(list);
        });
        //设置购物车值
        cart.setStoneId(stoneId);
        HfStone hfStone = hfStoneMapper.selectByPrimaryKey(stoneId);
        cart.setStoneName(hfStone.getHfName());
        cart.setProductsId(String.valueOf(hfGoods.getProductId()));
        cart.setProductId(productId);
        cart.setProductName(hfGoods.getGoodName());
        cart.setProductPrice(hfGoods.getSellPrice());
        cart.setProductNum(num);
        cart.setCheck("1");
        cart.setProductStatus(hfGoods.getIsDeleted());
        cart.setProductIcon(productMapper.selectByPrimaryKey(hfGoods.getProductId()).getFileId());
        redisService.hset(CartPrefix.getCartList, userId, productId+String.valueOf(stoneId), JSON.toJSON(cart).toString());
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
    public int updateCartNum(String userId, String productId, int num,Integer stoneId) {
        String json = redisService.hget(CartPrefix.getCartList, userId, productId+String.valueOf(stoneId));
        if (json == null) {
            return 0;
        }
        Cart cartDto = JSON.toJavaObject(JSONObject.parseObject(json), Cart.class);
        cartDto.setProductNum(num);
        redisService.hset(CartPrefix.getCartList, userId, productId+String.valueOf(stoneId), JSON.toJSON(cartDto).toString());
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
