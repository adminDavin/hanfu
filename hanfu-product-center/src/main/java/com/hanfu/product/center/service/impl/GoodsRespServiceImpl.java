package com.hanfu.product.center.service.impl;

import com.hanfu.product.center.dao.HfRespMapper;
import com.hanfu.product.center.model.HfResp;
import com.hanfu.product.center.model.HfRespExample;
import com.hanfu.product.center.service.GoodsRespService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class GoodsRespServiceImpl implements GoodsRespService {
    @Autowired
    private HfRespMapper hfRespMapper;
    @Override
    public List<HfResp> selectGoodsResp(Integer id) {
        
        HfRespExample example = new HfRespExample();
        example.createCriteria().andGoogsIdEqualTo(id);
        return hfRespMapper.selectByExample(example);
    }
}
