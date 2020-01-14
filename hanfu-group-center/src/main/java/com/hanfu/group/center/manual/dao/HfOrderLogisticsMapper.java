package com.hanfu.group.center.manual.dao;


import com.hanfu.group.center.manual.model.HfOrderLogistics;
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