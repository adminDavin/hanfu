package com.hanfu.referral.center.service.impl;

import java.util.List;

import org.apache.dubbo.config.annotation.Reference;

import com.hanfu.inner.model.product.center.HfOrders;
import com.hanfu.inner.sdk.order.center.HfOrderService;
import com.hanfu.referral.center.service.ReferralOrderService;

public class ReferralOrderServiceImpl implements ReferralOrderService {
    @Reference(registry = "dubboProductServer", url = "dubbo://127.0.0.1:1901/com.hanfu.inner.sdk.product.center.HfOrderService")
    private HfOrderService hfOrderService;
    
    @Override
    public List<HfOrders> getOrderByUserId(Integer userId) {
        return hfOrderService.getOrderByUserId(userId);
    }
}
