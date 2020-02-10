package com.hanfu.product.center.dao;

import com.hanfu.product.center.model.GoodsResp;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GoodsRespMapper {
    List<GoodsResp> selectGoodsResp(@Param("id") Integer id);
}
