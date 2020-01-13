package com.hanfu.group.center.service;


import com.hanfu.group.center.manual.model.GroupOpenConnect;
import org.apache.ibatis.annotations.Param;


public interface GroupOpenConnectService {
    int deleteByPrimaryKey(Integer id);
    void deleteByGroupOpenId(Integer id);
    void  insert(Integer userId,Integer groupOpenId,Integer ordersId,String hfDesc,Integer addressId);

    int insertSelective(GroupOpenConnect groupOpenConnect);

    GroupOpenConnect selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GroupOpenConnect groupOpenConnect);

    void updateIsDeleted( Integer userId,Integer groupOpenId);
    void updateState(Integer userId,Integer groupOpenId) ;
}
