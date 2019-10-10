package com.hanfu.product.center.manual.dao;

import java.util.List;


import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hanfu.product.center.manual.model.Categories;
import com.hanfu.product.center.manual.model.UserInfo;
import com.hanfu.product.center.model.Product;


@Repository
public class ProductDaoImpl implements ProductDao {

	@Autowired
    SqlSessionTemplate sqlSessionTemplate;
	
	@Override
	public Product selectById(Integer productId) {
		Product result = sqlSessionTemplate.selectOne("selectById", productId);
		return result;
	}

}
