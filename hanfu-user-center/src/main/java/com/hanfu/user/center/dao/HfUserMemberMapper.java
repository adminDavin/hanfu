package com.hanfu.user.center.dao;

import com.hanfu.user.center.model.HfUserMember;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

public interface HfUserMemberMapper {

    HfUserMember itExistUserById(@Param("userId") Integer userId);//判断用户是不是第一次充值会员

    void insertthirtyTime(@Param("userId") Integer userId, @Param("time") LocalDateTime time, @Param("thirtyTime") LocalDateTime thirtyTime);

    void insertseasonTime(@Param("userId") Integer userId, @Param("time") LocalDateTime time, @Param("seasonTime") LocalDateTime seasonTime);

    void insertyearTime(@Param("userId") Integer userId, @Param("time") LocalDateTime time, @Param("yearTime") LocalDateTime yearTime);

    void insertBalance(@Param("userId") Integer userId, @Param("total") Integer total);

    String getModifyTime(@Param("userId") Integer userId);//判断会员过期没有

    void updateModify(@Param("userId") Integer userId,
                      @Param("time") LocalDateTime time,
                      @Param("thirtyTime") LocalDateTime thirtyTime,
                      @Param("seasonTime") LocalDateTime seasonTime,
                      @Param("yearTime") LocalDateTime yearTime,
                      @Param("total") Integer total,
                      @Param("money") Integer money,
                      @Param("number") Integer number);//过期重新开通会员的时间

    void updateBalance(@Param("userId") Integer userId, @Param("total") Integer total);//会员过期重新开通  余额表的余额添加就好

    void updateModifyTime(@Param("userId") Integer userId,
                          @Param("time") LocalDateTime time,
                          @Param("thirtyTime") LocalDateTime thirtyTime,
                          @Param("seasonTime") LocalDateTime seasonTime,
                          @Param("yearTime") LocalDateTime yearTime,
                          @Param("total") Integer total,
                          @Param("money") Integer money,
                          @Param("number") Integer number);//没过期的会员  直接给他修改过期的日期就可以了

    void buyupdateModify(@Param("userId") Integer userId,
                         @Param("time") LocalDateTime time,
                         @Param("thirtyTime") LocalDateTime thirtyTime,
                         @Param("seasonTime") LocalDateTime seasonTime,
                         @Param("yearTime") LocalDateTime yearTime,
                         @Param("total") Integer total,
                         @Param("money") Integer money,
                         @Param("number") Integer number);//购买会员  已经过期的  重新设置时间

    void buyupdateModifyTime(@Param("userId") Integer userId,
                             @Param("time") LocalDateTime time,
                             @Param("thirtyTime") LocalDateTime thirtyTime,
                             @Param("seasonTime") LocalDateTime seasonTime,
                             @Param("yearTime") LocalDateTime yearTime,
                             @Param("total") Integer total,
                             @Param("money") Integer money,
                             @Param("number") Integer number);//购买会员的  没有过期的
}