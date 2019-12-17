package com.hanfu.group.center.manual.dao;

import com.hanfu.group.center.manual.model.GroupOpen;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface GroupOpenMapper {
    int deleteByPrimaryKey(Integer id);

    int deleteByGroupId(Integer groupId);

    int insert(@Param("groupId") Integer groupId,@Param("startTime") Date startTime,@Param("stopTime") Date stopTime);

    int insertSelective(GroupOpenMapper record);
//    根据groupId查询所在开表编号
    List<Integer> selectId(Integer groupId);

    GroupOpenMapper selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GroupOpenMapper record);

    int updateByPrimaryKey(GroupOpenMapper record);
    int selectNumber(Integer groupId);
    List<Integer> selectUserId(Integer groupId);
    //    查看开团信息
    GroupOpen selectByGroupOpen(Integer groupId);
    GroupOpen selectByStopTime(@Param("groupId") Integer groupId,@Param("stopTime") Date stopTime);
}