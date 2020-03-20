package com.hanfu.order.center.manual.dao;

import com.hanfu.order.center.manual.model.DiscoverUser;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class DiscoverDaoImpl implements DiscoverDao{
    @Autowired
    SqlSessionTemplate sqlSessionTemplate;
    @Override
    public List<DiscoverUser> selectDiscoverAll(Integer userId) {
        return sqlSessionTemplate.selectList("selectDiscoverAll",userId);
    }
}
