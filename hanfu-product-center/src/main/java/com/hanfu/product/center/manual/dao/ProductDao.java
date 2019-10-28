package com.hanfu.product.center.manual.dao;

import java.util.List;

import com.hanfu.product.center.manual.model.ProductDispaly;

import com.hanfu.product.center.model.Product;

public interface ProductDao {
    public List<Product> selectProductById(Integer storeId);

    public List<Product> selectProductBycategoryId(ProductDispaly productDispaly);

    public List<ProductDispaly> selectProductDisplay(Integer bossId);

    public Integer deleteSelectProduct(Integer[] productId);

    public Integer updateProduct(ProductDispaly productDispaly);
}
