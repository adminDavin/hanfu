package com.hanfu.group.center.service.impl;

import com.hanfu.group.center.manual.dao.HfGoodsSpecMapper;
import com.hanfu.group.center.manual.model.HfGoodsSpec;
import com.hanfu.group.center.service.HfGoodsSpecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author:gyj
 * @date: 2019/12/27
 * @time: 16:02
 */
@Service
@Transactional
public class HfGoodsSpecServiceImpl implements HfGoodsSpecService {
    @Autowired
    HfGoodsSpecMapper hfGoodsMapper;
    @Override
    public List<HfGoodsSpec> selectByPrimaryKey(Integer goodsId) {
        return hfGoodsMapper.selectByPrimaryKey(goodsId);
    }
}
