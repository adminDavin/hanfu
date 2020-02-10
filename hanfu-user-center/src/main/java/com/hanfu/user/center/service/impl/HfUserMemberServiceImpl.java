package com.hanfu.user.center.service.impl;

import com.hanfu.user.center.dao.HfUserMemberMapper;
import com.hanfu.user.center.model.HfUserMember;
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
    private HfUserMemberMapper hfUserMemberMapper;

    @Override
    public HfUserMember itExistUserById(Integer userId) {
        return hfUserMemberMapper.itExistUserById(userId);
    }

    @Override
    public void insertthirtyTime(Integer userId, LocalDateTime time, LocalDateTime thirtyTime) {
        hfUserMemberMapper.insertthirtyTime(userId, time, thirtyTime);
    }

    @Override
    public void insertseasonTime(Integer userId, LocalDateTime time, LocalDateTime seasonTime) {
        hfUserMemberMapper.insertseasonTime(userId, time, seasonTime);
    }

    @Override
    public void insertyearTime(Integer userId, LocalDateTime time, LocalDateTime yearTime) {
        hfUserMemberMapper.insertyearTime(userId, time, yearTime);
    }

    @Override
    public void insertBalance(Integer userId, Integer total) {
        hfUserMemberMapper.insertBalance(userId, total);
    }

    @Override
    public String getModifyTime(Integer userId) {
        return hfUserMemberMapper.getModifyTime(userId);
    }

    @Override
    public void updateModify(Integer userId, LocalDateTime time, LocalDateTime thirtyTime, LocalDateTime seasonTime, LocalDateTime yearTime, Integer total, Integer money, Integer number) {
        hfUserMemberMapper.updateModify(userId, time, thirtyTime, seasonTime, yearTime, total, money, number);
    }

    @Override
    public void updateBalance(Integer userId, Integer total) {
        hfUserMemberMapper.updateBalance(userId, total);
    }

    @Override
    public void updateModifyTime(Integer userId, LocalDateTime time, LocalDateTime thirtyTime, LocalDateTime seasonTime, LocalDateTime yearTime, Integer total, Integer money, Integer number) {
        hfUserMemberMapper.updateModifyTime(userId, time, thirtyTime, seasonTime, yearTime, total, money, number);
    }

    @Override
    public void buyupdateModify(Integer userId, LocalDateTime time, LocalDateTime thirtyTime, LocalDateTime seasonTime, LocalDateTime yearTime, Integer total, Integer money, Integer number) {
        hfUserMemberMapper.buyupdateModify(userId, time, thirtyTime, seasonTime, yearTime, total, money, number);
    }

    @Override
    public void buyupdateModifyTime(Integer userId, LocalDateTime time, LocalDateTime thirtyTime, LocalDateTime seasonTime, LocalDateTime yearTime, Integer total, Integer money, Integer number) {
        hfUserMemberMapper.buyupdateModifyTime(userId, time, thirtyTime, seasonTime, yearTime, total, money, number);
    }
}