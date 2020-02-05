package com.hanfu.user.center.service.impl;

import com.hanfu.user.center.dao.HfCouponMapper;
import com.hanfu.user.center.model.HfCoupon;
import com.hanfu.user.center.model.HfUserCoupon;
import com.hanfu.user.center.service.HfCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @ClassName HfCouponServiceImpl
 * @Date 2020/1/14 17:39
 * @Author CONSAK
 **/
@Service
public class HfCouponServiceImpl implements HfCouponService {

    @Autowired
    private HfCouponMapper hfCouponMapper;

    @Override
    public List<HfCoupon> selectCoupon(Integer id) {
        return hfCouponMapper.selectCoupon(id);
    }

    @Override
    public void insertCoupon(Integer money, Integer total, String body) {
        hfCouponMapper.insertCoupon(money,total,body);
    }

    @Override
    public void updateCoupon(Integer id, Integer money, Integer total, String body) {
        hfCouponMapper.updateCoupon(id,money,total,body);
    }

    @Override
    public void deleteCoupon(Integer id) {
        hfCouponMapper.deleteCoupon(id);
    }

    @Override
    public List<HfUserCoupon> selectUserCoupon(Integer userId) {
        return hfCouponMapper.selectUserCoupon(userId);
    }

    @Override
    public void receiveCoupon(Integer userId, Integer couponId, LocalDateTime time) {
        hfCouponMapper.receiveCoupon(userId,couponId,time);
    }

    @Override
    public HfCoupon selectOneCoupon(Integer couponId) {
        return hfCouponMapper.selectOneCoupon(couponId);
    }

    @Override
    public Integer deleteUserCoupon(Integer id, Integer userId, Integer couponId) {
        return hfCouponMapper.deleteUserCoupon(id,userId,couponId);
    }
}