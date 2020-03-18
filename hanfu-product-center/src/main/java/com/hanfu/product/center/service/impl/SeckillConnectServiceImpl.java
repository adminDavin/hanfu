package com.hanfu.product.center.service.impl;

import com.hanfu.product.center.dao.SeckillConnectDao;
import com.hanfu.product.center.model.SeckillConnect;
import com.hanfu.product.center.service.SeckillConnectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
