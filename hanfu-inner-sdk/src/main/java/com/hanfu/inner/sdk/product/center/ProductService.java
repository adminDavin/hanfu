package com.hanfu.inner.sdk.product.center;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.hanfu.inner.model.product.center.HfCategory;
import com.hanfu.inner.model.product.center.Product;

public interface ProductService {

	public void getProductByStone(Integer stoneId);

    public List<Product> getProduct();

	public List<HfCategory> listCategoryApp(Integer parentCategoryId, Integer categoryId, Integer levelId,Integer page ,Integer size) throws Exception;

}
