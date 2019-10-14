package com.hanfu.product.center.manual.dao;

import java.util.List;


import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hanfu.product.center.model.HfStone;


@Repository
public class StoreDaoImpl implements StoreDao {

	@Autowired
    SqlSessionTemplate sqlSessionTemplate;
	
	@Override
	public List<HfStone> selectStoreById(Integer productId) {
		List<HfStone> result = sqlSessionTemplate.selectList("selectStoreById", productId);
		return result;
	}

}
