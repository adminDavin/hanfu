package com.hanfu.product.center.cart.service;


import com.hanfu.product.center.cart.model.Product;

import java.util.List;


public interface ProductService {

	List<Product> getCartList(String string);



	int delCartProduct(String string, String string2);

	

	int addCart(String userId, String goodsId);



}