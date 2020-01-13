package com.hanfu.seckill.center.dao;


import com.hanfu.seckill.center.model.HfOrderStatus;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HfOrderStatusMapper {

    HfOrderStatus selectByPrimaryKey(Integer id);
}