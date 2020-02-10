package com.hanfu.product.center.dao;



import com.hanfu.product.center.model.HfOrdersDetail;
import org.apache.ibatis.annotations.Mapper;


public interface HfOrdersDetailGroupMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(HfOrdersDetail record);

    int insertSelective(HfOrdersDetail record);

    HfOrdersDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(HfOrdersDetail record);

    int updateByPrimaryKey(HfOrdersDetail record);
    HfOrdersDetail selectByOrdersId(Integer id);
}