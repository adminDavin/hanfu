package com.hanfu.product.center.manual.dao;

import java.util.List;

import com.hanfu.product.center.manual.model.WarehouseFindConditional;
import com.hanfu.product.center.model.HWarehouseResp;
import com.hanfu.product.center.model.HfInStorage;
import com.hanfu.product.center.model.WarehouseRespRecord;

public interface WarehouseDao {
	
	List<WarehouseRespRecord> findWarehouseRespRecord(WarehouseFindConditional wfc);
	List<HfInStorage> findHfInStorage(WarehouseFindConditional wfc);
	List<HWarehouseResp> findHWarehouseResp(WarehouseFindConditional wfc);
}
