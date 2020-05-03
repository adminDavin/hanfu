package com.hanfu.product.center.manual.dao;

import java.util.List;


import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hanfu.product.center.manual.model.Categories;
import com.hanfu.product.center.manual.model.ProductActivityInfo;
import com.hanfu.product.center.manual.model.UserInfo;
import com.hanfu.product.center.model.HfActivity;
import com.hanfu.product.center.model.HfCategory;
import com.hanfu.user.center.model.HfUser;


@Repository
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
    
    @Override
    public HfUser select(Integer userId) {
    	HfUser result = sqlSessionTemplate.selectOne("select");
    	return result;
    }
    
    @Override
    public List<ProductActivityInfo> selectProductActivityList(ProductActivityInfo pai) {
    	List<ProductActivityInfo> result = sqlSessionTemplate.selectList("selectProductActivityList",pai);
    	return result;
    }
    
    @Override
    public void updateActivityState(ProductActivityInfo activity) {
    	sqlSessionTemplate.update("updateActivityState", activity);
    }
    
    @Override
    public List<HfCategory> findCategoryByInfo(HfCategory h) {
    	List<HfCategory> result = sqlSessionTemplate.selectList("findCategoryByInfo", h);
    	System.out.println(h.getLevelId());
    	return result;
    }
}
