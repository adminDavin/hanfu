package com.hanfu.product.center.service.impl;

import com.hanfu.product.center.dao.HfProductCouponsMapper;
import com.hanfu.product.center.model.HfProductCoupons;
import com.hanfu.product.center.service.HfProductCouponsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
@Service
public class HfProductCouponsServiceImpl implements HfProductCouponsService {
    @Autowired
    HfProductCouponsMapper hfProductCouponsMapper;
    @Override
    public int deleteByPrimaryKey(Integer id) {
        return 0;
    }

    @Override
    public int insert(HfProductCoupons record) {
        return 0;
    }

    @Override
    public int insertSelective(HfProductCoupons record) {
        return 0;
    }

    @Override
    public HfProductCoupons selectByPrimaryKey(Integer id) {
        return null;
    }

    @Override
    public int updateByPrimaryKeySelective(HfProductCoupons record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(HfProductCoupons record) {
        return 0;
    }

    @Override
    public List<Date> selectByDate( String time) {
        String type="秒杀";
        return hfProductCouponsMapper.selectByDate(type,time);
    }

    @Override
    public List<HfProductCoupons> selectDate(Date startTime) {
        String type="秒杀";
        return hfProductCouponsMapper.selectDate(type,startTime);
    }

    @Override
    public HfProductCoupons seletById(Integer id) {
        return hfProductCouponsMapper.seletById(id);
    }
}
