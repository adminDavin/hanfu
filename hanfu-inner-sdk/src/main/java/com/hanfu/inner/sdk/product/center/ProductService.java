package com.hanfu.inner.sdk.product.center;

import java.util.List;

import com.hanfu.inner.model.product.center.Product;

public interface ProductService {

	public void getProductByStone(Integer stoneId);

    public List<Product> getProduct();
}
