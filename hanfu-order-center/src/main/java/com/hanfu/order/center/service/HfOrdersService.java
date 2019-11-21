package com.hanfu.order.center.service;


import java.util.List;

import com.hanfu.order.center.request.HfOrderLogisticsRequest;
import com.hanfu.order.center.request.HfOrdersDetailRequest;
import com.hanfu.order.center.request.HfOrdersRequest;

public interface HfOrdersService {

	@SuppressWarnings("rawtypes")
	List creatOrder(HfOrdersDetailRequest request, HfOrdersRequest hfOrder, HfOrderLogisticsRequest hfOrderLogistics);

	@SuppressWarnings("rawtypes")
	List updateOrder(HfOrdersDetailRequest request, HfOrdersRequest hfOrder, HfOrderLogisticsRequest hfOrderLogistics) throws Exception;

}
