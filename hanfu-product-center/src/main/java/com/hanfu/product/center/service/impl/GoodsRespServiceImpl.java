package com.hanfu.product.center.service.impl;

import com.hanfu.product.center.dao.GoodsRespMapper;
import com.hanfu.product.center.model.GoodsResp;
import com.hanfu.product.center.service.GoodsRespService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class GoodsRespServiceImpl implements GoodsRespService {
    @Autowired
    private GoodsRespMapper goodsRespMapper;
    @Override
    public List<GoodsResp> selectGoodsResp(Integer id) {
        System.out.println(id);
        return goodsRespMapper.selectGoodsResp(id);
    }
}
