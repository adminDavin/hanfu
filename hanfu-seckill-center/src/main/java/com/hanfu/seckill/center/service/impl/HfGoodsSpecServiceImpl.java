package com.hanfu.seckill.center.service.impl;
import com.hanfu.seckill.center.dao.HfGoodsSpecMapper;
import com.hanfu.seckill.center.model.HfGoodsSpec;
import com.hanfu.seckill.center.service.HfGoodsSpecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author:gyj
 * @date: 2019/12/27
 * @time: 16:02
 */
@Service
public class HfGoodsSpecServiceImpl implements HfGoodsSpecService {
    @Autowired
    HfGoodsSpecMapper hfGoodsMapper;
    @Override
    public List<HfGoodsSpec> selectByPrimaryKey(Integer goodsId) {
        return hfGoodsMapper.selectByPrimaryKey(goodsId);
    }
}
