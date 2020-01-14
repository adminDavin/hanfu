package com.hanfu.referral.center.service;

import java.util.List;

import com.hanfu.inner.model.product.center.HfOrders;

public interface ReferralOrderService {
    public List<HfOrders> getOrderByUserId(Integer userId);

}
