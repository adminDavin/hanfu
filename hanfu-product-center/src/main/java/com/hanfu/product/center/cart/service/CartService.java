package com.hanfu.product.center.cart.service;

import com.hanfu.product.center.cart.model.Cart;
import com.hanfu.product.center.manual.model.CartList;

import java.util.List;

public interface CartService {

    List<Cart> getCartList(String string,Integer type);

    int checkAll(String string, String string2);

    int delCart(String string);

    int updateCartNum(String userId, String productId, int num,Integer stoneId, Integer type);

    int addCart(String userId, String productId, int num,Integer stontId,Integer type);

	int delCartProduct(String userId, String productId,String stoneId,Integer type);
	
	CartList cartInfo(String userId);
}
