package com.hanfu.order.center.manual.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.hanfu.order.center.manual.model.HfGoodsDisplay;
import com.hanfu.order.center.manual.model.HfOrderDisplay;
import com.hanfu.order.center.manual.model.HfOrderStatistics;

public interface HfOrderDao {

    void insertOrderAddress(Integer addressId, Integer orderId);
    List<com.hanfu.order.center.manual.model.HfOrderDisplay> selectHfOrder(Map<String, Object> params);
    List<HfGoodsDisplay> selectGoodsInfo(Set<Integer> goodsIds);
    List<HfOrderStatistics> selectHfOrderStatistics(Integer userId);
    //增加库存

    /**
     *
     * @param respId 库存id
     * @param quantity 数量id
     * @return
     */
    int addResp(Integer respId,Integer quantity);


}