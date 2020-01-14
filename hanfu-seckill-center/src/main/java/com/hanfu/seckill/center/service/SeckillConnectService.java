package com.hanfu.seckill.center.service;

import com.hanfu.seckill.center.model.SeckillConnect;
import org.apache.ibatis.annotations.Param;

public interface SeckillConnectService {
    SeckillConnect selectByUserId(@Param("id") Integer id);
    void insert(@Param("id") Integer id,@Param("seckillId") Integer seckillId);
    void updateIsDeleted(@Param("userId") Integer userId);
    SeckillConnect selectBySeckillId( Integer id, Integer seckillId);
}
