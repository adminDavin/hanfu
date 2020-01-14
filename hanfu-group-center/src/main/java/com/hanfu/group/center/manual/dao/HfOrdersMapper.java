package com.hanfu.group.center.manual.dao;



import com.hanfu.group.center.manual.model.HfOrders;
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
    HfOrders selectByUserId(@Param("orderType")String orderType  ,@Param("userId") Integer userId ,
                            @Param("time") Date time);
}