package com.hanfu.product.center.manual.dao;

import com.hanfu.product.center.manual.model.HfGroup;
import com.hanfu.product.center.manual.model.HfProductDisplay;
import io.swagger.models.auth.In;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HfGroupDaoImpl implements HfGroupDao {
    @Autowired
    SqlSessionTemplate sqlSessionTemplate;
    @Override
    public List<HfGroup> groupList(Integer groupId) {
        List<HfGroup> result = sqlSessionTemplate.selectList("groupList",groupId);
        return result;
    }
}
