package com.hanfu.user.center.service.impl;

import com.hanfu.user.center.dao.HUserBalanceMapper;
import com.hanfu.user.center.service.HfUserBalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName HfUserBalanceServiceImpl
 * @Date 2019/12/17 10:03
 * @Author CONSAK
 **/
@Service
public class HfUserBalanceServiceImpl implements HfUserBalanceService {

    @Autowired
    HUserBalanceMapper hUserBalanceMapper;

    @Override
    public void balanceCutTotal(Integer userId, Integer hfBalance, Integer total) {
        hUserBalanceMapper.balanceCutTotal(userId, hfBalance, total);
    }
}