package com.hanfu.group.center.manual.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GroupOpenMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GroupOpenMapper record);

    int insertSelective(GroupOpenMapper record);

    GroupOpenMapper selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GroupOpenMapper record);

    int updateByPrimaryKey(GroupOpenMapper record);
    int selectNumber(Integer groupId);
    List<Integer> selectUserId(Integer groupId);
}