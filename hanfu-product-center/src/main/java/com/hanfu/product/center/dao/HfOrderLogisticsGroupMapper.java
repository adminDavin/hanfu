package com.hanfu.product.center.dao;

import com.hanfu.product.center.model.HfOrderLogistics;
import org.apache.ibatis.annotations.Mapper;


public interface HfOrderLogisticsGroupMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(HfOrderLogistics record);

    int insertSelective(HfOrderLogistics record);

    HfOrderLogistics selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(HfOrderLogistics record);

    int updateByPrimaryKey(HfOrderLogistics record);
}
