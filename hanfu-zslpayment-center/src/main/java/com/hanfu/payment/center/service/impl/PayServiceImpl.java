package com.hanfu.payment.center.service.impl;

import com.hanfu.payment.center.dao.PayMapper;
import com.hanfu.payment.center.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName PayServiceImpl
 * @Date 2019/12/23 15:23
 * @Author CONSAK
 **/
@Service
public class PayServiceImpl implements PayService {

    @Autowired(required = false)
    private PayMapper payMapper;

    public PayMapper getPayMapper() {
        return payMapper;
    }

    public void setPayMapper(PayMapper payMapper) {
        this.payMapper = payMapper;
    }
}