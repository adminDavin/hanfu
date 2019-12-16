package com.hanfu.group.center.service;


import com.hanfu.group.center.manual.model.GroupOpenConnect;
import org.apache.ibatis.annotations.Param;

/**
 * @author:gyj
 * @date: 2019/12/13
 * @time: 11:03
 */
public interface GroupOpenConnectService {
    int deleteByPrimaryKey(Integer id);

    int insert(Integer userId,Integer groupOpenId);

    int insertSelective(GroupOpenConnect groupOpenConnect);

    GroupOpenConnect selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GroupOpenConnect groupOpenConnect);

    void updateIsDeleted( Integer userId,Integer groupOpenId);
}
