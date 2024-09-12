package com.hanfu.seckill.center.service.impl;

import com.hanfu.seckill.center.dao.SeckillConnectDao;
import com.hanfu.seckill.center.model.SeckillConnect;
import com.hanfu.seckill.center.service.SeckillConnectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author:gyj
 * @date: 2019/12/31
 * @time: 11:14
 */
@Service
public class SeckillConnectServiceImpl implements SeckillConnectService {
    @Autowired
    SeckillConnectDao seckillConnectDao;
    @Override
    public SeckillConnect selectByUserId(Integer id) {
        return seckillConnectDao.selectByUserId(id);
    }
    @Override
    public void insert(Integer id, Integer seckillId) {
        seckillConnectDao.insert(id,seckillId);
    }

    @Override
    public void updateIsDeleted(Integer userId) {
        seckillConnectDao.updateIsDeleted(userId);
    }

    @Override
    public SeckillConnect selectBySeckillId(Integer id, Integer seckillId) {
        return seckillConnectDao.selectBySeckillId(id,seckillId);
    }
}
