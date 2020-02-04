package com.hanfu.payment.center.manual.dao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.hanfu.payment.center.manual.model.HfGoodsDisplay;
import com.hanfu.payment.center.manual.model.HfOrderDisplay;
import com.hanfu.payment.center.manual.model.HfUser;

public interface HfOrderDao {

    void insertOrderAddress(Integer addressId, Integer orderId);
    List<HfGoodsDisplay> selectGoodsInfo(Set<Integer> goodsIds);
    HfUser selectHfUser(Integer userId);
    HfOrderDisplay selectHfOrderbyCode(String orderCode);
    void updateHfOrderStatus(@Param("orderCode") String orderCode, @Param("orderStatus") String orderStatus, @Param("modifyTime") LocalDateTime modifyTime);

}