package com.hanfu.order.center.manual.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hanfu.order.center.model.HfOrderLogistics;
import com.hanfu.order.center.model.HfOrderStatus;
import com.hanfu.order.center.model.HfOrdersDetail;



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
	@Override
	public List<HfOrdersDetail> selectOrderDetail(Integer id) {
		List<HfOrdersDetail> result = sqlSessionTemplate.selectList("selectOrderDetail",id);
		return result;
	}
	@Override
	public List<HfOrderLogistics> selectOrder(Integer orderId, String hfName, String payMethodType, String orderDetailStatus,
			LocalDateTime creatTime) {
		List<HfOrderLogistics> result = sqlSessionTemplate.selectList("selectOrder");
		return result;
	}

}
