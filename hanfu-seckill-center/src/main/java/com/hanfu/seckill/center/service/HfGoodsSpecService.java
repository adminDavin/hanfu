package com.hanfu.seckill.center.service;

import com.hanfu.seckill.center.model.HfGoodsSpec;

import java.util.List;

public interface HfGoodsSpecService {
    List<HfGoodsSpec> selectByPrimaryKey(Integer goodsId );
}
