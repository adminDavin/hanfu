package com.hanfu.group.center.service;



import com.hanfu.group.center.manual.model.HfGoodsSpec;

import java.util.List;


public interface HfGoodsSpecService {
    List<HfGoodsSpec> selectByPrimaryKey(Integer goodsId);
}
