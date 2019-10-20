package com.hanfu.order.center.manual.dao;

import java.util.List;

import com.hanfu.order.center.model.HfOrderLogistics;

public interface OrderDao {

	List<HfOrderLogistics> selectOrderList(Integer id);

}
