package com.hanfu.user.center.manual.dao;

import java.util.List;


import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hanfu.inner.model.product.center.HfOrders;
import com.hanfu.user.center.manual.model.UserInfo;


@Repository
public class HfBossInfoDaoImpl implements HfBossInfoDao {

    @Autowired
    SqlSessionTemplate sqlSessionTemplate;

    @Override
    public Double selectAllOrderByBossId(Integer stoneId) {
    	Double result = sqlSessionTemplate.selectOne("selectAllOrderByBossId", stoneId);
    	return result;
    }
    
    @Override
    public Integer selectBrowseCountsByBossId(Integer bossId) {
    	Integer result = sqlSessionTemplate.selectOne("selectBrowseCountsByBossId", bossId);
    	return result;
    }
}
