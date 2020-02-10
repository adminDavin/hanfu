package com.hanfu.cart.center.manual.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hanfu.cart.center.manual.model.HfGoods;

@Repository
public class HfGoodsDaoImpl implements HfGoodsDao {
    @Autowired
    SqlSessionTemplate sqlSessionTemplate;

    @Override
    public HfGoods findProductById(String productId) {
        HfGoods result = sqlSessionTemplate.selectOne("selectGoods", productId);
        return result;
    }
}
