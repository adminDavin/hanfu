package com.hanfu.product.center.dao;

import com.hanfu.product.center.model.HfResp;
import org.apache.ibatis.annotations.Mapper;

public interface HfRespGroupMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(HfResp record);

    int insertSelective(HfResp record);

    HfResp selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(HfResp record);

    int updateByPrimaryKey(HfResp record);
}