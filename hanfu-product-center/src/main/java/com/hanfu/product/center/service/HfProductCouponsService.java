package com.hanfu.product.center.service;

import com.hanfu.product.center.model.HfProductCoupons;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public interface HfProductCouponsService {
    int deleteByPrimaryKey(Integer id);

    int insert(HfProductCoupons record);

    int insertSelective(HfProductCoupons record);

    HfProductCoupons selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(HfProductCoupons record);

    int updateByPrimaryKey(HfProductCoupons record);
    List<Date> selectByDate(String time);
    List<HfProductCoupons> selectDate(Date startTime);
    HfProductCoupons seletById(Integer id);

}
