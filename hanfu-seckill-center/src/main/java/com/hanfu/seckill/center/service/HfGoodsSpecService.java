package com.hanfu.seckill.center.service;

import com.hanfu.seckill.center.model.HfGoodsSpec;

import java.util.List;

/**
 * @author:gyj
 * @date: 2019/12/27
 * @time: 16:00
 */
public interface HfGoodsSpecService {
    List<HfGoodsSpec> selectByPrimaryKey(Integer goodsId );
}
