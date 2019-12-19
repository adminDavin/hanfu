package com.hanfu.user.center.service;

import com.hanfu.user.center.model.HUserBalance;
import org.apache.ibatis.annotations.Param;

public interface HfUserMemberService {

    HUserBalance itExistUserById(@Param("userId") Integer userId);
}