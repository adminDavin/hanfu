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
import com.hanfu.product.center.manual.model.CartList;
import com.hanfu.product.center.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
    @Autowired
    private HfProductCollectMapper hfProductCollectMapper;

    @Override
    public int addCart(String userId, String productId, int num ,Integer stoneId,Integer type) {
        //key为 userId_cart,校验是否已存在
    	Boolean exists = true;
    	String json = "";
    	Cart cart1 = new Cart();
        if(type ==1) {
        	exists = redisService.existsValue(CartPrefix.getCartList, userId+"ofen", productId+String.valueOf(stoneId));
        }else {
        	exists = redisService.existsValue(CartPrefix.getCartList, userId, productId+String.valueOf(stoneId));
        }
        if (exists) {
            //获取现有的购物车中的数据
            if(type ==1) {
            	json = redisService.hget(CartPrefix.getCartList, userId+"ofen", productId+String.valueOf(stoneId));
            }else {
            	json = redisService.hget(CartPrefix.getCartList, userId, productId+String.valueOf(stoneId));
            }
            if (json != null) {
                //转换为java实体类
                cart1 = JSON.toJavaObject(JSONObject.parseObject(json), Cart.class);
                if(type == 1) {
                	cart1.setProductNum(cart1.getProductNum() + num);
                	redisService.hset(CartPrefix.getCartList, userId+"ofen", productId+String.valueOf(stoneId), JSON.toJSON(cart1).toString());
                }else {
                	cart1.setProductNum(cart1.getProductNum() + num);
                	redisService.hset(CartPrefix.getCartList, userId, productId+String.valueOf(stoneId), JSON.toJSON(cart1).toString());
                }
                
            } else {
            	if(type == 1) {
                	return -1;
                }else {
                	return 0;
                }
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
        ProductInstanceExample productInstanceExample = new ProductInstanceExample();
        productInstanceExample.createCriteria().andProductIdEqualTo(hfGoods.getProductId()).andStoneIdEqualTo(stoneId);

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
        cart.setInstanceId(productInstanceMapper.selectByExample(productInstanceExample).get(0).getId());
        cart.setProductStatus(hfGoods.getIsDeleted());
        cart.setProductIcon(productMapper.selectByPrimaryKey(hfGoods.getProductId()).getFileId());
        if(type == 1) {
        	redisService.hset(CartPrefix.getCartList, userId+"ofen", productId+String.valueOf(stoneId), JSON.toJSON(cart).toString());
        }else {
        	redisService.hset(CartPrefix.getCartList, userId, productId+String.valueOf(stoneId), JSON.toJSON(cart).toString());
        }
        return 1;
    }

    /**
     * 展示购物车
     *
     * @param userId
     * @return
     */
    @Override
    public List<Cart> getCartList(String userId,Integer type) {
    	List<String> jsonList = new ArrayList<String>();
    	Boolean exists = false;
    	if(type == 1) {
    		jsonList = redisService.hvals(CartPrefix.getCartList, userId+"ofen");
    	}else {
    		jsonList = redisService.hvals(CartPrefix.getCartList, userId);
    	}
        
        List<Cart> cartDtoList = new LinkedList<>();
        for (String json : jsonList) {
            Cart cartDto = JSON.toJavaObject(JSONObject.parseObject(json), Cart.class);
            exists = redisService.existsValue(CartPrefix.getCartList, userId+"ofen", cartDto.getProductId()+String.valueOf(cartDto.getStoneId()));
            if(exists) {
            	cartDto.setIsOfenBuy(1);
            }else {
            	cartDto.setIsOfenBuy(0);
            }
            HfProductCollectExample example = new HfProductCollectExample();
            example.createCriteria().andUserIdEqualTo(Integer.valueOf(userId)).andProductIdEqualTo(Integer.valueOf(cartDto.getProductsId()));
            if(!CollectionUtils.isEmpty(hfProductCollectMapper.selectByExample(example))) {
            	cartDto.setIsCollect(1);
            }else {
            	cartDto.setIsCollect(1);
            }
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
    public int updateCartNum(String userId, String productId, int num,Integer stoneId, Integer type) {
    	String json = "";
    	if(type == 1) {
    		json = redisService.hget(CartPrefix.getCartList, userId+"ofen", productId+String.valueOf(stoneId));
    	}else {
    		json = redisService.hget(CartPrefix.getCartList, userId, productId+String.valueOf(stoneId));
    	}
        if (json == null) {
            return 0;
        }
        Cart cartDto = JSON.toJavaObject(JSONObject.parseObject(json), Cart.class);
        cartDto.setProductNum(num);
        if(type == 1) {
        	redisService.hset(CartPrefix.getCartList, userId+"ofen", productId+String.valueOf(stoneId), JSON.toJSON(cartDto).toString());
        }else {
        	redisService.hset(CartPrefix.getCartList, userId, productId+String.valueOf(stoneId), JSON.toJSON(cartDto).toString());
        }
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
    public int delCartProduct(String userId, String productId,String stontId,Integer type) {
    		if(type == 1) {
    			redisService.hdel(CartPrefix.getCartList, userId+"ofen", productId+stontId);
    		}else {
    			redisService.hdel(CartPrefix.getCartList, userId, productId+stontId);
    		}
            
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
    /**
     * 购物车基本数据
     *
     * @param userId
     * @return
     */
    @Override
    public CartList cartInfo(String userId) {
    	CartList list = new CartList();
    	List<String> jsonList1 = redisService.hvals(CartPrefix.getCartList, userId);
    	List<String> jsonList2 = redisService.hvals(CartPrefix.getCartList, userId+"ofen");
    	if(CollectionUtils.isEmpty(jsonList1)) {
    		list.setAll(0);
    	}else {
    		list.setAll(jsonList1.size());
    	}
    	if(CollectionUtils.isEmpty(jsonList2)) {
    		list.setOfenBuyCount(0);
    	}else {
    		list.setOfenBuyCount(jsonList2.size());
    	}
    	
    	return list;
    }

}
