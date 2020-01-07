package com.hanfu.cart.center.service;

import java.util.List;

import com.hanfu.cart.center.model.Cart;

public interface CartService {

    List<Cart> getCartList(String string);

    int checkAll(String string, String string2);

    int delCart(String string);

    int updateCartNum(String userId, String productId, int num);

    int addCart(String userId, String productId, int num);

	int delCartProduct(String userId, String productId);

}
