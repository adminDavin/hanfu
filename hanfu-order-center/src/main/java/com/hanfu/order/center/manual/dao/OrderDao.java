package com.hanfu.order.center.manual.dao;

import java.util.List;

import com.hanfu.order.center.model.HfOrderLogistics;
import com.hanfu.order.center.model.HfOrderStatus;

public interface OrderDao {

	List<HfOrderLogistics> selectOrderList();

	List<HfOrderStatus> selectOrderStatus();

}
