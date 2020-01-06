package com.hanfu.payment.center.manual.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hanfu.payment.center.manual.model.Categories;
import com.hanfu.payment.center.manual.model.UserInfo;


@Repository("dbDao")
public class ManualDaoImpl implements ManualDao {

    @Autowired
    SqlSessionTemplate sqlSessionTemplate;

    @Override
    public List<UserInfo> getSimpleUserInfo() {
        List<UserInfo> result = sqlSessionTemplate.selectList("getSimpleUserInfo");
        return result;
    }

    @Override
    public void selectProductByStone(Integer stoneId) {
        List<UserInfo> result = sqlSessionTemplate.selectList("selectProductByStone");
        System.out.println(result);

    }

    @Override
    public List<Categories> selectCategories() {
        List<Categories> result = sqlSessionTemplate.selectList("selectCategories");
        return result;
    }

}
