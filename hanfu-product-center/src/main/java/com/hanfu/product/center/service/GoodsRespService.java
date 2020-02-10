package com.hanfu.product.center.service;

import com.hanfu.product.center.model.HfResp;

import java.util.List;

public interface GoodsRespService {
    List<HfResp> selectGoodsResp(Integer id);
}
