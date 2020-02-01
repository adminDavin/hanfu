package com.hanfu.order.center.manual.dao;

import java.util.HashMap;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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

     
}
