package com.hanfu.product.center.manual.dao;

import java.util.List;


import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hanfu.product.center.manual.model.Categories;
import com.hanfu.product.center.manual.model.UserInfo;
import com.hanfu.product.center.model.Product;


@Repository
public class ProductInstanceDaoImpl implements ProductInstanceDao {

	@Autowired
    SqlSessionTemplate sqlSessionTemplate;
	
	@Override
	public Integer deleteProductInstance(Integer productId, Integer stoneId) {
		Integer row = sqlSessionTemplate.delete("deleteProductInstance", productId);
		return row;
	}

}
