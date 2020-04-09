package com.hanfu.product.center.manual.dao;

import java.util.List;


import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

//import com.hanfu.product.center.manual.model.AwardInfo;
import com.hanfu.product.center.manual.model.Categories;
import com.hanfu.product.center.manual.model.CheckResp;
import com.hanfu.product.center.manual.model.HfGoodsDisplay;
import com.hanfu.product.center.manual.model.PriceRanking;
import com.hanfu.product.center.manual.model.ProductForValue;
import com.hanfu.product.center.manual.model.UserInfo;
import com.hanfu.product.center.model.HfGoods;


@Repository
public class HfMemberDaoImpl implements HfMemberDao {

    @Autowired
    SqlSessionTemplate sqlSessionTemplate;

    @Override
    public Integer selectIsMember(Integer userId) {
    	Integer result = sqlSessionTemplate.selectOne("selectIsMember", userId);
    	return result;
    }
   
    @Override
    public String selectNameByUserId(Integer userId) {
    	String result = sqlSessionTemplate.selectOne("selectNameByUserId", userId);
    	return result;
    }
}
