package com.hanfu.user.center.manual.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * @author shihao
 * @Title: HfVipDaoImpl
 * @ProjectName Second-order-center
 * @Description:
 * @date Created in
 * @Version: $
 */
@Repository
public class HfVipDaoImpl implements  HfVipDao{
    @Autowired
    SqlSessionTemplate sqlSessionTemplate;

    @Override
    public Integer minusDay(Integer hfVipUserId,Integer vipDay) {
        Map<String,Integer> map = new HashMap<>();
        map.put("hfVipUserId",hfVipUserId);
        map.put("vipDay",vipDay);
        int result = sqlSessionTemplate.update("minusDay", map);
        return result;
    }
}
