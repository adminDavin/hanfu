package com.hanfu.product.center.dao;



import com.hanfu.product.center.model.HfOrders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;


public interface HfOrdersGroupMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(HfOrders record);

    int insertSelective(HfOrders record);

    HfOrders selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(HfOrders record);

    int updateByPrimaryKey(HfOrders record);
    HfOrders selectByOrderType(@Param("orderType")String orderType ,@Param("userId") Integer userId );
    HfOrders selectByUserId(@Param("orderType") String orderType, @Param("userId") Integer userId,
                            @Param("time") Date time);
}