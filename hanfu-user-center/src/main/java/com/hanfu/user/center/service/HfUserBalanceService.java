package com.hanfu.user.center.service;

import org.apache.ibatis.annotations.Param;

public interface HfUserBalanceService {

    void balanceCutTotal(@Param("userId") Integer userId, @Param("hfBalance") Integer hfBalance, @Param("total") Integer total);
}