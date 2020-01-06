package com.hanfu.group.center.service;



import com.hanfu.group.center.manual.model.HfGoodsSpec;

import java.util.List;

/**
 * @author:gyj
 * @date: 2019/12/27
 * @time: 16:00
 */
public interface HfGoodsSpecService {
    List<HfGoodsSpec> selectByPrimaryKey(Integer goodsId);
}
