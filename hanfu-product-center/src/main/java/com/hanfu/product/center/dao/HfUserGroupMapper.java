package com.hanfu.product.center.dao;

import com.hanfu.product.center.model.HfUser;

public interface HfUserGroupMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(HfUser record);

    int insertSelective(HfUser record);

    HfUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(HfUser record);

    int updateByPrimaryKey(HfUser record);
}
