package com.hanfu.seckill.center.dao;


import com.hanfu.seckill.center.model.HfOrders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

@Mapper
public interface HfOrdersMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(HfOrders record);

    int insertSelective(HfOrders record);

    HfOrders selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(HfOrders record);

    int updateByPrimaryKey(HfOrders record);
    HfOrders selectByUserId(@Param("orderType")String orderType ,@Param("userId") Integer userId );
    HfOrders selectByDate(@Param("orderType")String orderType ,@Param("userId") Integer userId ,
                            @Param("time")Date time);
}