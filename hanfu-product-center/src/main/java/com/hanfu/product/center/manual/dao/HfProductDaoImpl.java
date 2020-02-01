package com.hanfu.product.center.manual.dao;

import java.util.List;

import com.hanfu.product.center.manual.model.HfGoodsDisplay;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hanfu.product.center.manual.model.HfProductDisplay;
import com.hanfu.product.center.model.Product;

@Repository("hfProductDao")
public class HfProductDaoImpl implements HfProductDao {

    @Autowired
    SqlSessionTemplate sqlSessionTemplate;
    
    @Override
    public List<HfProductDisplay> selectProductForRotation(Integer quantity) {
        List<HfProductDisplay> result = sqlSessionTemplate.selectList("selectProductForRotation", quantity);
        return result;
    }
    
    @Override
    public HfProductDisplay selectProduct(Integer productId) {
        HfProductDisplay result = sqlSessionTemplate.selectOne("selectProductByProductId", productId);
        return result;
    }

    @Override
    public List<HfProductDisplay> selectProductCategory(Integer cagetoryId) {
        List<HfProductDisplay> result = sqlSessionTemplate.selectList("selectProductCategory", cagetoryId);
        return result;
    }
}
