package com.hanfu.seckill.center.dao;


import com.hanfu.seckill.center.model.HfOrderLogistics;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HfOrderLogisticsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(HfOrderLogistics record);

    int insertSelective(HfOrderLogistics record);

    HfOrderLogistics selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(HfOrderLogistics record);

    int updateByPrimaryKey(HfOrderLogistics record);
}