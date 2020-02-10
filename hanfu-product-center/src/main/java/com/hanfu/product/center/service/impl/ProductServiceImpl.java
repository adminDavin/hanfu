package com.hanfu.product.center.service.impl;


import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.hanfu.inner.model.product.center.HfCategory;
import com.hanfu.product.center.dao.HfCategoryMapper;
import com.hanfu.product.center.dao.ProductMapper;
import com.hanfu.product.center.manual.dao.ManualDao;
import com.hanfu.product.center.manual.model.Categories;
import com.hanfu.product.center.model.HfCategoryExample;
import com.hanfu.product.center.model.Product;
import com.hanfu.product.center.service.ProductService;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseUtils;
import com.hanfu.utils.response.handler.ResponseEntity.BodyBuilder;

@Service("productService")
@org.apache.dubbo.config.annotation.Service(registry = "dubboProductServer")
public class ProductServiceImpl implements com.hanfu.inner.sdk.product.center.ProductService, ProductService {
    @Autowired
    private ManualDao manualDao;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private HfCategoryMapper hfCategoryMapper;
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

    @Override
    public ResponseEntity<JSONObject> listCategory(Integer parentCategoryId, Integer categoryId, Integer levelId, Integer page, Integer size) throws Exception {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        return builder.body(ResponseUtils.getResponseBody(listAllCategory(parentCategoryId, categoryId, levelId, page, size)));
    }

    @Override
    public List<com.hanfu.inner.model.product.center.HfCategory> listCategoryApp(Integer parentCategoryId, Integer categoryId, Integer levelId, Integer page, Integer size) throws Exception {
        List<com.hanfu.product.center.model.HfCategory> list = listAllCategory(parentCategoryId, categoryId, levelId, page, size);
        return JSONArray.parseArray(JSONObject.toJSONString(list), com.hanfu.inner.model.product.center.HfCategory.class);
    }

    public List<com.hanfu.product.center.model.HfCategory> listAllCategory(Integer parentCategoryId, Integer categoryId, Integer levelId, Integer page, Integer size) throws Exception {

        HfCategoryExample example = new HfCategoryExample();
        example.createCriteria().andParentCategoryIdEqualTo(parentCategoryId);
        if (categoryId != null) {
            example.clear();
            example.createCriteria().andIdEqualTo(categoryId);
        }
        if (levelId != null) {
            example.clear();
            example.createCriteria().andLevelIdEqualTo(levelId);
        }
//		return manualDao.selectCategories();
        if (!StringUtils.isEmpty(page)) {
            if (!StringUtils.isEmpty(size)) {
                PageHelper.startPage(page, size);
            }
        }
        return hfCategoryMapper.selectByExample(example);
    }

}
