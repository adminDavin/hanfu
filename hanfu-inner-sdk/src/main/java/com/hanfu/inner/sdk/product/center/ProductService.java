package com.hanfu.inner.sdk.product.center;

import com.hanfu.inner.model.product.center.HfCategory;
import com.hanfu.inner.model.product.center.Product;

import java.util.List;

public interface ProductService {

    public void getProductByStone(Integer stoneId);

    public List<Product> getProduct();

    public List<HfCategory> listCategoryApp(Integer parentCategoryId, Integer categoryId, Integer levelId, Integer page, Integer size) throws Exception;

}