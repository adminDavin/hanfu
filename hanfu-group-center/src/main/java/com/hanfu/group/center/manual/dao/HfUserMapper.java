package com.hanfu.group.center.manual.dao;


import com.hanfu.group.center.manual.model.HfUser;

public interface HfUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(HfUser record);

    int insertSelective(HfUser record);

    HfUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(HfUser record);

    int updateByPrimaryKey(HfUser record);
}