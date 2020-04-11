package com.hanfu.product.center.cart.service;

import com.hanfu.product.center.cart.model.Cart;

import java.util.List;

public interface CartService {

    List<Cart> getCartList(String string);

    int checkAll(String string, String string2);

    int delCart(String string);

    int updateCartNum(String userId, String productId, int num,Integer stoneId);

    int addCart(String userId, String productId, int num,Integer stontId);

	int delCartProduct(String userId, String productId,String stoneId);

}
