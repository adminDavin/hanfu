package com.hanfu.order.center.manual.dao;

import java.util.List;

import com.hanfu.order.center.model.HfOrderLogistics;
import com.hanfu.order.center.model.HfOrderStatus;
import com.hanfu.order.center.model.HfOrdersDetail;

public interface OrderDao {

	List<HfOrderLogistics> selectOrderList();

	List<HfOrderStatus> selectOrderStatus();

	List<HfOrdersDetail> selectOrderDetail(Integer id);


}
