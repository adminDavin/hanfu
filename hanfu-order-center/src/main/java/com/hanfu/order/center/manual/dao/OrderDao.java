package com.hanfu.order.center.manual.dao;

import java.util.List;

import com.hanfu.order.center.model.HfOrdersDetail;

public interface OrderDao {

	List<HfOrdersDetail> selectOrderList(Integer id);

}
