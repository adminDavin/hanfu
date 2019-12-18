package com.hanfu.payment.center.service.impl;

import com.hanfu.payment.center.dao.HfUserMemberMapper;
import com.hanfu.payment.center.service.HfUserMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

/**
 * @ClassName HfUserMemberServiceImpl
 * @Date 2019/12/18 15:45
 * @Author CONSAK
 **/
@Service
@Configuration
public class HfUserMemberServiceImpl implements HfUserMemberService {

    @Autowired
    private HfUserMemberMapper hfUserMemberMapper;

    @Override
    public void rechargeMember(Integer userId, Integer money) {
        //hfUserMemberMapper.rechargeMember(userId,money);
    }
}