package com.hanfu.group.center.manual.dao;


import com.hanfu.group.center.manual.model.GroupOpenConnect;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface GroupOpenConnectMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(@Param("userId") Integer userId,@Param("groupOpenId") Integer groupOpenId);

    int insertSelective(GroupOpenConnect record);

    GroupOpenConnect selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GroupOpenConnect record);

    int updateisDeleted(@Param("userId") Integer userId,@Param("groupOpenId") Integer groupOpenId);
}