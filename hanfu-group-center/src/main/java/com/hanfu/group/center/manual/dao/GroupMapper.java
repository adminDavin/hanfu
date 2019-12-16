package com.hanfu.group.center.manual.dao;


import com.hanfu.group.center.manual.model.Group;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.Date;

@Mapper
public interface GroupMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(@Param("bossId") Integer bossId,@Param("goodsId")  Integer goodsId, @Param("price") Double price,@Param("number")  Integer number,@Param("startTime")  Date startTime, @Param("stopTime") Date stopTime,@Param("repertory") Integer repertory);

    int insertSelective(Group record);

    Group selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Group record);

    int updateByPrimaryKey(Group record);
    void updateRrepertory(Integer id,Integer repertory);
}