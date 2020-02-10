package com.hanfu.user.center.service;

import com.hanfu.user.center.model.HfCoupon;
import com.hanfu.user.center.model.HfUserCoupon;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface HfCouponService {
    List<HfCoupon> selectCoupon(@Param("id") Integer id);//查询所有优惠券,也可根据id来查找

    void insertCoupon(@Param("money") Integer money,@Param("total") Integer total,@Param("body") String body);//添加优惠券

    void updateCoupon(@Param("id") Integer id, @Param("money") Integer money,@Param("total") Integer total,@Param("body") String body);//修改优惠券

    void deleteCoupon(@Param("id") Integer id);//删除优惠券

    List<HfUserCoupon> selectUserCoupon(@Param("userId") Integer userId);//用户查询自己优惠券

    void receiveCoupon(@Param("userId") Integer userId,@Param("couponId") Integer couponId,@Param("time") LocalDateTime time);//用户领取优惠券

    HfCoupon selectOneCoupon(@Param("couponId") Integer couponId);//查询当前用户要用的优惠券信息

    Integer deleteUserCoupon(@Param("id") Integer id,@Param("userId") Integer userId,@Param("couponId") Integer couponId);//用户使用优惠券之后删除优惠券
}