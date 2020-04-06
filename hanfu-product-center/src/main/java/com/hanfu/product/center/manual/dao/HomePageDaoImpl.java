package com.hanfu.product.center.manual.dao;

import java.util.List;


import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hanfu.product.center.manual.model.Categories;
import com.hanfu.product.center.manual.model.HomePageInfo;
import com.hanfu.product.center.manual.model.ProductActivityInfo;
import com.hanfu.product.center.manual.model.UserBrowseInfo;
import com.hanfu.product.center.manual.model.UserInfo;
import com.hanfu.product.center.model.HfActivity;
import com.hanfu.user.center.model.HfUser;


@Repository
public class HomePageDaoImpl implements HomePageDao {

    @Autowired
    SqlSessionTemplate sqlSessionTemplate;
    
    @Override
    public List<HomePageInfo> findSalesVolume(List<Integer> orderDetailId) {
    	List<HomePageInfo> result = sqlSessionTemplate.selectList("findSalesVolume", orderDetailId);
    	return result;
    }
    
    @Override
    public List<HomePageInfo> findOrderTypeCount(List<Integer> orderId) {
    	List<HomePageInfo> result = sqlSessionTemplate.selectList("findOrderTypeCount", orderId);
    	return result;
    }
    
    @Override
    public List<String> groupBytime(Integer userId) {
    	List<String> result = sqlSessionTemplate.selectList("groupBytime",userId);
    	return result;
    }
    
    @Override
    public List<String> groupBytimeCollect(Integer userId) {
    	List<String> result = sqlSessionTemplate.selectList("groupBytimeCollect",userId);
    	return result;
    }
    
    @Override
    public List<String> groupBytimeConcern(Integer userId) {
    	List<String> result = sqlSessionTemplate.selectList("groupBytimeConcern",userId);
    	return result;
    }
}
