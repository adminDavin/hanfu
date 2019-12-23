package com.hanfu.seckill.center.service.impl;

import com.hanfu.seckill.center.dao.SeckillDao;
import com.hanfu.seckill.center.model.Seckill;
import com.hanfu.seckill.center.service.SeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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
    public void insertSeckill(Integer bossId, Integer goodsId, Date startTime, Date stopTime, Integer categoryId, Double price, Integer repertory) {
      Short isDeleted=0;
      seckillDao.insertSeckill(bossId,goodsId,startTime,stopTime, categoryId,price, repertory,isDeleted);
    }


    @Override
    public void updateIsDeleted(Integer goodsId,Short isDeleted, Integer bossId) {

      seckillDao.updateIsDeleted(goodsId,bossId,isDeleted);
    }

    @Override
    public void deleteByPrimaryKey(Integer id) {
        seckillDao.deleteByPrimaryKey(id);
    }

    @Override
    public void updateByPrimaryKey(Integer id, Integer bossId, Integer goodsId, Date startTime,Date stopTime,  Integer categoryId, Double price, Integer repertory) {
        seckillDao.updateByPrimaryKey(id,  bossId,  goodsId,  startTime, stopTime, categoryId,  price,  repertory);
    }

    @Override
    public List<Seckill> selectSeckill(Integer bossId) {
        return seckillDao.selectSeckill(bossId);
    }

    @Override
    public List<Seckill> selectGoodsId(Integer goodsId) {
        return seckillDao.selectGoodsId(goodsId);
    }

    @Override
    public void updateState(Integer id) {
        seckillDao.updateState(id);
    }

    @Override
    public Seckill selectId(Integer id) {
        return seckillDao.selectId(id);
    }

    @Override
    public List<Seckill> selectAll(Integer bossId) {
        return seckillDao.selectAll(bossId);
    }


}