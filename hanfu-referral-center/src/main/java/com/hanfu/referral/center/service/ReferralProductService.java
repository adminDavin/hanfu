package com.hanfu.referral.center.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.hanfu.inner.model.product.center.HfCategory;
import com.hanfu.inner.model.product.center.Product;
import com.hanfu.utils.response.handler.ResponseEntity;

public interface ReferralProductService {

    public List<Product> getAllProduct();

    public List<HfCategory> listCategory(Integer parentCategoryId, Integer categoryId, Integer levelId, Integer page, Integer size) throws Exception;


}
