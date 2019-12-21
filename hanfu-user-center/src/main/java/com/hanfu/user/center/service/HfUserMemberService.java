package com.hanfu.user.center.service;

import com.hanfu.user.center.model.HUserBalance;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

public interface HfUserMemberService {

    HUserBalance itExistUserById(@Param("userId") Integer userId);

    void memberTime(@Param("userId") Integer userId,
                    @Param("time") LocalDateTime time,
                    @Param("thirtyTime") LocalDateTime thirtyTime,
                    @Param("seasonTime") LocalDateTime seasonTime,
                    @Param("yearTime") LocalDateTime yearTime,
                    @Param("total") Integer total);//成为会员的时间

    void insertBalance(@Param("userId") Integer userId,@Param("total") Integer total);//把用户冲的钱放入余额表

    String getModifyTime(@Param("userId") Integer userId);

    void updateBalance(@Param("userId") Integer userId, @Param("total") Integer total);//因为他开通过会员  余额表已经数据了  直接添加他冲的钱就行了

    void updateModifyTime(@Param("userId") Integer userId,
                          @Param("time") LocalDateTime time,
                          @Param("thirtyTime") LocalDateTime thirtyTime,
                          @Param("seasonTime") LocalDateTime seasonTime,
                          @Param("yearTime") LocalDateTime yearTime,
                          @Param("total") Integer total);

    String getCreateTime(@Param("userId") Integer userId);//判断是不是第一次购买会员

    void buymemberTime(@Param("userId") Integer userId,
                       @Param("time") LocalDateTime time,
                       @Param("thirtyTime") LocalDateTime thirtyTime,
                       @Param("seasonTime") LocalDateTime seasonTime,
                       @Param("yearTime") LocalDateTime yearTime,
                       @Param("total") Integer total);//第一次购买会员

    void updateTime(@Param("userId") Integer userId,
                    @Param("time") LocalDateTime time,
                    @Param("thirtyTime") LocalDateTime thirtyTime,
                    @Param("seasonTime") LocalDateTime seasonTime,
                    @Param("yearTime") LocalDateTime yearTime,
                    @Param("total") Integer total);//购买会员的 没有过期的添加时间
}