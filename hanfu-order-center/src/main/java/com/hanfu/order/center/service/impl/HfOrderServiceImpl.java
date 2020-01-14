package com.hanfu.order.center.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hanfu.inner.sdk.order.center.HfOrderService;
import com.hanfu.order.center.dao.HfOrdersMapper;
import com.hanfu.order.center.model.HfOrders;


@Service("hfOrderService")
@org.apache.dubbo.config.annotation.Service(registry = "dubboProductServer")
public class HfOrderServiceImpl implements HfOrderService {
    @Autowired
    HfOrdersMapper hfOrdersMapper;

    @Override
    public List<com.hanfu.inner.model.product.center.HfOrders> getOrderByUserId(Integer userId) {
        List<HfOrders> hfOrders = hfOrdersMapper.selectByExample(null);
        return JSONArray.parseArray(JSONObject.toJSONString(hfOrders), com.hanfu.inner.model.product.center.HfOrders.class);
    }

}
