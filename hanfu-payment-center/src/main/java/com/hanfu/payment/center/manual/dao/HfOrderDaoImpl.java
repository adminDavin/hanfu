package com.hanfu.payment.center.manual.dao;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.curator.shaded.com.google.common.collect.Lists;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hanfu.payment.center.manual.model.HfGoodsDisplay;
import com.hanfu.payment.center.manual.model.HfOrderDisplay;
import com.hanfu.payment.center.manual.model.HfUser;

 
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
    public List<HfGoodsDisplay> selectGoodsInfo(Set<Integer> goodsIds) {
        return sqlSessionTemplate.selectList("selectGoodsInfo", Lists.newArrayList(goodsIds));
    }

    @Override
    public HfUser selectHfUser(Integer userId) {
        // TODO Auto-generated method stub
        return sqlSessionTemplate.selectOne("selectHfUser", userId);
    }

    @Override
    public HfOrderDisplay selectHfOrderbyCode(String orderCode) {
        return sqlSessionTemplate.selectOne("selectHfOrderbyCode", orderCode);
    }
    
    public void updateHfOrderStatus(String orderCode, String orderStatus, LocalDateTime modifyTime) {
        HfOrderDisplay hfOrder = new HfOrderDisplay();
        hfOrder.setOrderCode(orderCode);
        hfOrder.setOrderStatus(orderStatus);
        hfOrder.setModifyTime(modifyTime);
        sqlSessionTemplate.update("updateHfOrderStatus", hfOrder);
    }
}
