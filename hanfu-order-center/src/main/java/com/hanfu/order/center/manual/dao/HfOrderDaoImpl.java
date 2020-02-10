package com.hanfu.order.center.manual.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.curator.shaded.com.google.common.collect.Lists;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hanfu.order.center.manual.model.HfGoodsDisplay;
import com.hanfu.order.center.manual.model.HfOrderDisplay;
import com.hanfu.order.center.manual.model.HfOrderStatistics;

@Repository
public class HfOrderDaoImpl implements HfOrderDao {
    @Autowired
    SqlSessionTemplate sqlSessionTemplate;

    @Override
    public void insertOrderAddress(Integer addressId, Integer orderId) {
        Map<String, Integer> params = new HashMap<>();
        params.put("addressId", addressId);
        params.put("orderId", orderId);
        sqlSessionTemplate.update("insertOrderAddress", params);   
    }

    @Override
    public List<HfOrderDisplay> selectHfOrder(Map<String, Object> params) {
        return sqlSessionTemplate.selectList("selectHfOrder", params);
    }


    @Override
    public List<HfGoodsDisplay> selectGoodsInfo(Set<Integer> goodsIds) {
        return sqlSessionTemplate.selectList("selectGoodsInfo", Lists.newArrayList(goodsIds));
    }

    @Override
    public List<HfOrderStatistics> selectHfOrderStatistics(Integer userId) {
        return sqlSessionTemplate.selectList("selectHfOrderStatistics", userId);
    }

}
