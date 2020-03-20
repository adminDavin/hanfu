package com.hanfu.product.center.service.impl;

import com.hanfu.product.center.dao.HfGoodsMapper;
import com.hanfu.product.center.dao.SeckillDao;
import com.hanfu.product.center.model.HfGoods;
import com.hanfu.product.center.model.Seckill;
import com.hanfu.product.center.service.SeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;


@Service
public class SeckillServiceImpl implements SeckillService {
    @Autowired
    private SeckillDao seckillDao;
    @Autowired
    private HfGoodsMapper hfGoodsMapper;
    @Override
    public Integer getRepertory(Integer id) {
        return seckillDao.getRepertory(id);
    }

    @Override
    public void updateRepertory(Integer goodsId, Integer bossId,Integer repertory) {
        seckillDao.updateRepertory(goodsId,bossId,repertory);
    }
  @Override
    public void insertSeckill(Integer bossId, Integer goodsId, Date startTime, Date stopTime, Integer categoryId, Double price, Integer repertory) throws Exception {
      Short isDeleted=0;
      HfGoods goods = hfGoodsMapper.selectByPrimaryKey(goodsId);
      if(goods == null) {
    	  throw new Exception("物品不存在");
      }
//      Date time1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startTime);
//      Date time2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(stopTime);
      seckillDao.insertSeckill(bossId,goodsId,startTime,stopTime,categoryId,price, repertory,isDeleted);
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

    @Override
    public List<Seckill> selectDate(String startTime) {
        return seckillDao.selectDate(startTime);
    }

    @Override
    public Seckill seletById(Integer id) {
        return seckillDao.seletById(id);
    }

    @Override
    public List<Date> selectByDate(String time) {
        return seckillDao.selectByDate(time);
    }


}
