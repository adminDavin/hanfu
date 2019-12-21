package com.hanfu.user.center.service.impl;

import com.hanfu.user.center.dao.HUserBalanceMapper;
import com.hanfu.user.center.model.HUserBalance;
import com.hanfu.user.center.service.HfUserMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @ClassName HfUserMemberServiceImpl
 * @Date 2019/12/19 10:11
 * @Author CONSAK
 **/
@Service
public class HfUserMemberServiceImpl implements HfUserMemberService {

    @Autowired
    private HUserBalanceMapper hUserBalanceMapper;

    @Override
    public HUserBalance itExistUserById(Integer userId) {
        return hUserBalanceMapper.itExistUserById(userId);
    }

    @Override
    public void memberTime(Integer userId, LocalDateTime time,LocalDateTime thirtyTime,LocalDateTime seasonTime,LocalDateTime yearTime,Integer total) {
        hUserBalanceMapper.memberTime(userId,time,thirtyTime,seasonTime,yearTime,total);
    }

    @Override
    public void insertBalance(Integer userId, Integer total) {
        hUserBalanceMapper.insertBalance(userId,total);
    }

    @Override
    public String getModifyTime(Integer userId) {
        return hUserBalanceMapper.getModifyTime(userId);
    }

    @Override
    public void updateBalance(Integer userId, Integer total) {
        hUserBalanceMapper.updateBalance(userId,total);
    }

    @Override
    public void updateModifyTime(Integer userId, LocalDateTime time, LocalDateTime thirtyTime, LocalDateTime seasonTime, LocalDateTime yearTime, Integer total) {
        hUserBalanceMapper.updateModifyTime(userId,time,thirtyTime,seasonTime,yearTime,total);
    }

    @Override
    public String getCreateTime(Integer userId) {
        return hUserBalanceMapper.getCreateTime(userId);
    }

    @Override
    public void buymemberTime(Integer userId, LocalDateTime time, LocalDateTime thirtyTime, LocalDateTime seasonTime, LocalDateTime yearTime, Integer total) {
        hUserBalanceMapper.buymemberTime(userId,time,thirtyTime,seasonTime,yearTime,total);
    }

    @Override
    public void updateTime(Integer userId, LocalDateTime time, LocalDateTime thirtyTime, LocalDateTime seasonTime, LocalDateTime yearTime, Integer total) {
        hUserBalanceMapper.updateTime(userId,time,thirtyTime,seasonTime,yearTime,total);
    }
}