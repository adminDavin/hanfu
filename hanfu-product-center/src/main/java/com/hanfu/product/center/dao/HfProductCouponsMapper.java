package com.hanfu.product.center.dao;

import com.hanfu.product.center.model.HfProductCoupons;
import com.hanfu.product.center.model.Seckill;
import org.apache.ibatis.annotations.Param;

;import java.sql.ClientInfoStatus;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public interface HfProductCouponsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(HfProductCoupons record);

    int insertSelective(HfProductCoupons record);

    HfProductCoupons selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(HfProductCoupons record);

    int updateByPrimaryKey(HfProductCoupons record);

    List<Date> selectByDate(@Param("type")String type,@Param("time")String time);

    List<HfProductCoupons> selectDate(@Param("type")String type,@Param("startTime") Date startTime);
    HfProductCoupons seletById(Integer id);
}