package com.hanfu.product.center.manual.dao;

import java.util.List;


import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hanfu.product.center.manual.model.Categories;
import com.hanfu.product.center.manual.model.UserInfo;
import com.hanfu.product.center.model.Product;
import com.hanfu.product.center.model.ProductInstance;
import com.hanfu.product.center.request.ProductInfoRequest;
import com.hanfu.product.center.request.ProductInstanceRequest;


@Repository
public class ProductInstanceDaoImpl implements ProductInstanceDao {

    @Autowired
    SqlSessionTemplate sqlSessionTemplate;

    @Override
    public Integer deleteProductInstance(ProductInstance example) {
        Integer row = sqlSessionTemplate.delete("deleteProductInstance", example);
        return row;
    }

    @Override
    public ProductInstance selectProductInstance(ProductInstanceRequest productInstanceRequest) {
        ProductInstance instances = sqlSessionTemplate.selectOne("selectProductInstance", productInstanceRequest);
        return instances;
    }
}
