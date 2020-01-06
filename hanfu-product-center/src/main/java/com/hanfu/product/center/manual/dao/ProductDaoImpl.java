package com.hanfu.product.center.manual.dao;

import java.util.List;


import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hanfu.product.center.manual.model.ProductDispaly;
import com.hanfu.product.center.model.Product;


@Repository
public class ProductDaoImpl implements ProductDao {

    @Autowired
    SqlSessionTemplate sqlSessionTemplate;

    @Override
    public List<Product> selectProductById(Integer storeId) {
        List<Product> result = sqlSessionTemplate.selectList("selectProductById", storeId);
        return result;
    }

    @Override
    public List<Product> selectProductBycategoryId(ProductDispaly productDispaly) {
        List<Product> result = sqlSessionTemplate.selectList("selectProductBycategoryId", productDispaly);
        return result;
    }

    @Override
    public List<ProductDispaly> selectProductDisplay(Integer bossId) {
        List<ProductDispaly> result = sqlSessionTemplate.selectList("selectProductDisplay", bossId);
        return result;
    }

    @Override
    public Integer deleteSelectProduct(Integer[] productId) {
        Integer row = sqlSessionTemplate.delete("deleteSelectProduct", productId);
        return row;
    }

    @Override
    public Integer updateProduct(ProductDispaly productDispaly) {
        Integer row = sqlSessionTemplate.update("updateProduct", productDispaly);
        return row;
    }
}
