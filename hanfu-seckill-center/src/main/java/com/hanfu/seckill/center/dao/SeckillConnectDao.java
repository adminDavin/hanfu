package com.hanfu.seckill.center.dao;

import com.hanfu.seckill.center.model.SeckillConnect;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author:gyj
 * @date: 2019/12/31
 * @time: 11:06
 */
@Mapper
public interface SeckillConnectDao {
    SeckillConnect selectByUserId(Integer id);
    void insert(@Param("id") Integer id,@Param("seckillId") Integer seckillId);
    void updateIsDeleted(@Param("userId") Integer userId);
    SeckillConnect selectBySeckillId(@Param("id") Integer id,@Param("seckillId") Integer seckillId);
}
