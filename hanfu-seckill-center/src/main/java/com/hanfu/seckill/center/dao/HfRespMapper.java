package com.hanfu.seckill.center.dao;


import com.hanfu.seckill.center.model.HfResp;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HfRespMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(HfResp record);

    int insertSelective(HfResp record);

    HfResp selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(HfResp record);

    int updateByPrimaryKey(HfResp record);
}