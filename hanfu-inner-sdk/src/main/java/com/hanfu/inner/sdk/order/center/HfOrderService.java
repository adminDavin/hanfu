package com.hanfu.inner.sdk.order.center;

import java.util.List;

import com.hanfu.inner.model.product.center.HfOrders;

public interface HfOrderService {
    public List<HfOrders> getOrderByUserId(Integer userId);
}