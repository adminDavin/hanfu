package com.hanfu.product.center.manual.dao;

import java.util.List;

import org.apache.curator.shaded.com.google.common.collect.Lists;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hanfu.product.center.manual.model.HfGoodsDisplayInfo;
import com.hanfu.product.center.manual.model.HfGoodsSpecDisplay;

@Repository("hfGoodsDisplayDao")
public class HfGoodsDisplayDaoImpl implements HfGoodsDisplayDao {
    @Autowired
    SqlSessionTemplate sqlSessionTemplate;
    
    @Override
    public List<HfGoodsDisplayInfo> selectHfGoodsDisplay(Integer productId) {
        List<HfGoodsDisplayInfo> result = sqlSessionTemplate.selectList("selectHfGoodsDisplay", Lists.newArrayList(productId));
        return result;
    }
    
    @Override
    public List<HfGoodsDisplayInfo> selectHfGoodsDisplay(List<Integer> productIds) {
        List<HfGoodsDisplayInfo> result = sqlSessionTemplate.selectList("selectHfGoodsDisplay", productIds);
        return result;
    }

    @Override
    public HfGoodsDisplayInfo selectHfGoods(Integer goodsId) {
        HfGoodsDisplayInfo result = sqlSessionTemplate.selectOne("selectHfGoods", goodsId);
        return result;
    }

    @Override
    public List<HfGoodsSpecDisplay> selectHfGoodsSpec(Integer goodsId) {
        List<HfGoodsSpecDisplay> result = sqlSessionTemplate.selectList("selectHfGoodsSpec", Lists.newArrayList(goodsId));
        return result;
    }
    
    @Override
    public List<HfGoodsSpecDisplay> selectHfGoodsSpec(List<Integer> goodsIds) {
        List<HfGoodsSpecDisplay> result = sqlSessionTemplate.selectList("selectHfGoodsSpec", goodsIds);
        return result;
    }
}
