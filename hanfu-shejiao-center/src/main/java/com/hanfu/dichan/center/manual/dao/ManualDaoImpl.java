package com.hanfu.dichan.center.manual.dao;

import java.util.List;


import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hanfu.dichan.center.model.DcCategory;

@Repository
public class ManualDaoImpl implements ManualDao {

    @Autowired
    SqlSessionTemplate sqlSessionTemplate;


    
    @Override
    public List<DcCategory> findCategoryByInfo(DcCategory h) {
    	List<DcCategory> result = sqlSessionTemplate.selectList("findCategoryByInfo", h);
    	System.out.println(h.getLevelId());
    	return result;
    }
}
