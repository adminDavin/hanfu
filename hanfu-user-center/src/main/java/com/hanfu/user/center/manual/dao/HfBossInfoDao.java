package com.hanfu.user.center.manual.dao;

import java.util.List;

import com.hanfu.inner.model.product.center.HfOrders;
import com.hanfu.user.center.manual.model.UserInfo;

public interface HfBossInfoDao {
    
	public Double selectAllOrderByBossId(Integer stoneId);
	
	public Integer selectBrowseCountsByBossId(Integer bossId);
}
