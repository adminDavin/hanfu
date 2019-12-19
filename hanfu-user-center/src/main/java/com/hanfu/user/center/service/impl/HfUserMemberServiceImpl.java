package com.hanfu.user.center.service.impl;

import com.hanfu.user.center.dao.HUserBalanceMapper;
import com.hanfu.user.center.model.HUserBalance;
import com.hanfu.user.center.service.HfUserMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}