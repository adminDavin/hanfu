package com.hanfu.order.center.manual.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hanfu.order.center.manual.model.OrderFindValue;
import com.hanfu.order.center.manual.model.OrderInfo;
import com.hanfu.order.center.model.HfOrderLogistics;
import com.hanfu.order.center.model.HfOrderStatus;
import com.hanfu.order.center.model.HfOrdersDetail;


@Repository
public class OrderDaoImpl implements OrderDao {
    @Autowired
    SqlSessionTemplate sqlSessionTemplate;

    @Override
    public List<OrderInfo> selectOrderList() {
        List<OrderInfo> result = sqlSessionTemplate.selectList("selectOrderList");
        return result;
    }

    @Override
    public List<HfOrderStatus> selectOrderStatus() {
        List<HfOrderStatus> result = sqlSessionTemplate.selectList("selectOrderStatus");
        return result;
    }

    @Override
    public List<HfOrdersDetail> selectOrderDetail(Integer id) {
        List<HfOrdersDetail> result = sqlSessionTemplate.selectList("selectOrderDetail", id);
        return result;
    }

    @Override
    public List<OrderInfo> selectOrder(OrderFindValue orderFindValue
    ) {
        List<OrderInfo> result = sqlSessionTemplate.selectList("selectOrder", orderFindValue);
        return result;
    }

    @Override
    public List<OrderInfo> selectOrderByUserId(Integer userId) {
        List<OrderInfo> result = sqlSessionTemplate.selectList("selectOrderByUserId", userId);
        return result;
    }

	@Override
	public List<OrderInfo> selectOrders(Integer id) {
		List<OrderInfo> result = sqlSessionTemplate.selectList("selectOrders", id);
		return result;
	}

}
