package com.hanfu.product.center.dao;


import com.hanfu.product.center.model.GroupOpen;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface GroupOpenMapper {
    int deleteByPrimaryKey(Integer id);

    int deleteByGroupId(Integer groupId);

    int insert(@Param("groupId") Integer groupId, @Param("startTime") Date startTime, @Param("stopTime") Date stopTime);

    int insertSelective(GroupOpenMapper record);
//    根据groupId查询所在开表编号
    List<Integer> selectId(Integer groupId);
    //
    List<Date> getStopTime();
//    根据停止时间查
    GroupOpen selectStopTime(Date stopTime);

    GroupOpen selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GroupOpenMapper record);

    int updateByPrimaryKey(GroupOpenMapper record);
    int selectNumber(Integer groupId);
    List<Integer> selectUserId(Integer groupId);
    //    查看开团信息
    List<GroupOpen> selectByGroupOpen(Integer groupId);
    GroupOpen selectByStopTime(@Param("groupId") Integer groupId, @Param("stopTime") Date stopTime);
//    更改开团转态
    void updateByIsDeleted(Integer id);
    GroupOpen selectById(Integer id);
    List<Integer> selectByGroupOpenId(Integer groupId);
    GroupOpen selectByGroup(@Param("groupId") Integer groupId, @Param("userId") Integer userId);
    List<Integer> selectByUserId(Integer groupId);
    //查询全部id
    List<Integer> selectAllUserId(Integer groupId);
}