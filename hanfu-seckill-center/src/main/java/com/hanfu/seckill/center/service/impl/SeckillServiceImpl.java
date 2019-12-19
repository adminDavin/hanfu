package com.hanfu.seckill.center.service.impl;

import com.hanfu.seckill.center.dao.SeckillDao;
import com.hanfu.seckill.center.service.SeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author:gyj
 * @date: 2019/12/11
 * @time: 10:43
 */
@Service
public class SeckillServiceImpl implements SeckillService {
    @Autowired
    private SeckillDao seckillDao;
    @Override
    public Integer getRepertory(Integer goodsId, Integer bossId) {
        return seckillDao.getRepertory(goodsId,bossId);
    }

    @Override
    public void updateRepertory(Integer goodsId, Integer bossId,Integer repertory) {
        seckillDao.updateRepertory(goodsId,bossId,repertory);
    }
  @Override
    public void insertSeckill(Integer bossId, Integer goodsId, Date startTime, Integer categoryId, Double price, Integer repertory) {
      Short isDeleted=0;
      seckillDao.insertSeckill(bossId,goodsId,startTime,categoryId,price, repertory,isDeleted);
    }


    @Override
    public void updateIsDeleted(Integer goodsId, Integer bossId) {
        Short isDeleted=0;
      seckillDao.updateIsDeleted(goodsId,bossId,isDeleted);
    }

    @Override
    public void deleteByPrimaryKey(Integer id) {
        seckillDao.deleteByPrimaryKey(id);
    }

    @Override
    public void updateByPrimaryKey(Integer id, Integer bossId, Integer goodsId, Date startTime, Integer categoryId, Double price, Integer repertory) {
        seckillDao.updateByPrimaryKey(id,  bossId,  goodsId,  startTime,  categoryId,  price,  repertory);
    }


}
