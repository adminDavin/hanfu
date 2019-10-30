package com.hanfu.order.center.manual.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hanfu.order.center.model.HfOrderLogistics;
import com.hanfu.order.center.model.HfOrderStatus;



@Repository
public class OrderDaoImpl implements OrderDao{
	@Autowired
    SqlSessionTemplate sqlSessionTemplate;
	@Override
	public List<HfOrderLogistics> selectOrderList() {
		List<HfOrderLogistics> result = sqlSessionTemplate.selectList("selectOrderList");
		return result;
	}
	@Override
	public List<HfOrderStatus> selectOrderStatus() {
		List<HfOrderStatus> result = sqlSessionTemplate.selectList("selectOrderStatus");
		return result;
	}

}
