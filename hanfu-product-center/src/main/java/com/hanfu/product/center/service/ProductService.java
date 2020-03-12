package com.hanfu.product.center.service;

import com.alibaba.fastjson.JSONObject;
import com.hanfu.utils.response.handler.ResponseEntity;

public interface ProductService {

    public ResponseEntity<JSONObject> listCategory(Integer parentCategoryId, Integer categoryId, Integer levelId, Integer page, Integer size) throws Exception;

}
