package com.hanfu.activity.center.manual.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class StrategyRuleDaoImpl implements StrategyRuleDao {

    @Autowired
    SqlSessionTemplate sqlSessionTemplate;

    @Override
    public List<String> findRuleType() {
        List<String> result = sqlSessionTemplate.selectList("findRuleType");
        return result;
    }

}
