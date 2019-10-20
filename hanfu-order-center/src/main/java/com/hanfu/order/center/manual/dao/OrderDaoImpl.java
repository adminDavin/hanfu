package com.hanfu.order.center.manual.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hanfu.order.center.model.HfOrderLogistics;


@Repository
public class OrderDaoImpl implements OrderDao{
	@Autowired
    SqlSessionTemplate sqlSessionTemplate;
	@Override
	public List<HfOrderLogistics> selectOrderList(Integer id) {
		List<HfOrderLogistics> result = sqlSessionTemplate.selectList("selectOrderList", id);
		return result;
	}

}
