package com.hanfu.seckill.center.dao;


import com.hanfu.seckill.center.model.HfOrdersDetail;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HfOrdersDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(HfOrdersDetail record);

    int insertSelective(HfOrdersDetail record);

    HfOrdersDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(HfOrdersDetail record);

    int updateByPrimaryKey(HfOrdersDetail record);
}