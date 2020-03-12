package com.hanfu.referral.center.service.impl;

import java.util.List;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.hanfu.inner.model.product.center.HfCategory;
import com.hanfu.inner.model.product.center.Product;
import com.hanfu.inner.sdk.product.center.ProductService;
import com.hanfu.referral.center.service.ReferralProductService;
import com.hanfu.utils.response.handler.ResponseEntity;


@Service("referralProductService")
public class RefferralProductServiceImpl implements ReferralProductService {

    @Reference(registry = "dubboProductServer", url = "dubbo://127.0.0.1:1900/com.hanfu.inner.sdk.product.center.ProductService")
    private ProductService productService;

    @Override
    public List<Product> getAllProduct() {
        return productService.getProduct();
    }

    @Override
    public List<HfCategory> listCategory(Integer parentCategoryId, Integer categoryId, Integer levelId, Integer page, Integer size) throws Exception {
        return productService.listCategoryApp(parentCategoryId, categoryId, levelId, page, size);
    }
}
