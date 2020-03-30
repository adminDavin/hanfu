package com.hanfu.product.center.cart.manual.dao;

import com.hanfu.product.center.cart.manual.model.HfGoods;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class HfGoodDaoImpl implements HfGoodDao {
    @Autowired
    SqlSessionTemplate sqlSessionTemplate;

    @Override
    public HfGoods findProductById(String productId) {
        HfGoods result = sqlSessionTemplate.selectOne("selectGoods", productId);
        return result;
    }
}
