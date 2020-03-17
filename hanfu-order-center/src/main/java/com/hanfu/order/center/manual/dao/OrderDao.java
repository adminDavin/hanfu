package com.hanfu.order.center.manual.dao;

import java.util.List;

import com.hanfu.order.center.manual.model.OrderFindValue;
import com.hanfu.order.center.manual.model.OrderInfo;
import com.hanfu.order.center.model.HfOrderStatus;
import com.hanfu.order.center.model.HfOrdersDetail;

public interface OrderDao {

    List<OrderInfo> selectOrderList();

    List<HfOrderStatus> selectOrderStatus();

    OrderInfo selectOrderDetail(Integer id);

    List<OrderInfo> selectOrder(OrderFindValue orderFindValue);

    List<OrderInfo> selectOrderByUserId(Integer userId);

	List<OrderInfo> selectOrders(Integer id);


}