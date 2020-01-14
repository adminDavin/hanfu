package com.hanfu.product.center.service;

import com.hanfu.product.center.model.GoodsResp;

import java.util.List;

public interface GoodsRespService {
    List<GoodsResp> selectGoodsResp(Integer id);
}
