package com.hanfu.product.center.manual.dao;

import java.util.List;

import com.hanfu.product.center.manual.model.WarehouseFindConditional;
import com.hanfu.product.center.model.WarehouseRespRecord;

public interface WarehouseDao {
	List<WarehouseRespRecord> findWarehouseRespRecord(WarehouseFindConditional wfc);
}
