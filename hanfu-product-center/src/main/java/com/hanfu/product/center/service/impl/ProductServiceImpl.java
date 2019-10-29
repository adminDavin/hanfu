package com.hanfu.product.center.service.impl;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hanfu.product.center.dao.ProductMapper;
import com.hanfu.product.center.manual.dao.ManualDao;
import com.hanfu.product.center.model.Product;

@Service("productService")
@org.apache.dubbo.config.annotation.Service(registry = "dubboProductServer")
public class ProductServiceImpl implements com.hanfu.inner.sdk.product.center.ProductService {
	@Autowired
	private ManualDao manualDao;
	
	@Autowired
    private ProductMapper productMapper;

	@Override
	public void getProductByStone(Integer stoneId) {
		manualDao.selectProductByStone(stoneId);
		System.out.println("hello word");
	}

    @Override
    public List<com.hanfu.inner.model.product.center.Product> getProduct() {
        List<Product> products = productMapper.selectByExample(null);
        return JSONArray.parseArray(JSONObject.toJSONString(products), com.hanfu.inner.model.product.center.Product.class);
    }



}
