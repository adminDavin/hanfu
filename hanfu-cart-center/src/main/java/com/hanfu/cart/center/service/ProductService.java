package com.hanfu.cart.center.service;



import java.util.List;



import com.hanfu.cart.center.model.Product;



public interface ProductService {

	List<Product> getCartList(String string);



	int delCartProduct(String string, String string2);

	

	int addCart(String userId, String goodsId);



}