package com.hanfu.product.center.manual.dao;

import java.util.List;

import com.hanfu.product.center.manual.model.*;
import com.hanfu.product.center.model.SelectProductGoods;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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

    @Override
    public List<HfProductDisplay> selectProductByStoneId(IsDelete isDelete) {
        System.out.println(isDelete);
        List<HfProductDisplay> result = sqlSessionTemplate.selectList("selectProductByStoneId", isDelete);
        return result;
    }

    @Override
    public List<HfProductDisplay> selectProductByUserId(Integer userId) {
        List<HfProductDisplay> result = sqlSessionTemplate.selectList("selectProductByUserId", userId);
        return result;
    }

    @Override
    public List<HfProductDisplay> selectProductSeniorityId(Integer seniorityId) {
        List<HfProductDisplay> result = sqlSessionTemplate.selectList("selectProductSeniorityId", seniorityId);
        return result;
    }

    @Override
    public List<HfProductDisplay> selectProductName(ProductNameSelect productNameSelect) {
        List<HfProductDisplay> result = sqlSessionTemplate.selectList("selectProductName", productNameSelect);
        return result;
    }

    @Override
    public List<ProductGoods> selectProductGoods(SelectProductGoods ProductId) {
        List<ProductGoods> result = sqlSessionTemplate.selectList("selectProductGoods", ProductId);
        return result;
    }
}
