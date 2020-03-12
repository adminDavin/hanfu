package com.hanfu.product.center.controller;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.hanfu.product.center.dao.WarehouseMapper;
import com.hanfu.product.center.model.Warehouse;
import com.hanfu.product.center.model.WarehouseExample;
import com.hanfu.product.center.request.WareHouseRequest;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseEntity.BodyBuilder;
import com.hanfu.utils.response.handler.ResponseUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/wareHouse")
@Api
public class WareHouseController {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WarehouseMapper warehouseMapper;

    @ApiOperation(value = "查询仓库", notes = "每个商家都有自己的仓库")
    @RequestMapping(value = "/listWareHouse", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "bossId", value = "商家id", required = true, type = "Integer")})
    public ResponseEntity<JSONObject> listWareHouse(@RequestParam Integer bossId)
            throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        WarehouseExample example = new WarehouseExample();
        example.createCriteria().andBossidEqualTo(bossId);
        return builder.body(ResponseUtils.getResponseBody(warehouseMapper.selectByExample(example)));
    }

    @ApiOperation(value = "添加仓库", notes = "为商家创建仓库")
    @RequestMapping(value = "/createWareHouse", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> addWareHouse(WareHouseRequest request)
            throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        Warehouse warehouse = new Warehouse();
        warehouse.setBossid(request.getBossId());
        warehouse.setHfDesc(request.getHfDesc());
        warehouse.setHfName(request.getHfName());
        warehouse.setHfRegion(request.getHfRegion());
        warehouse.setCreateTime(LocalDateTime.now());
        warehouse.setModifyTime(LocalDateTime.now());
        warehouse.setIsDeleted((short) 0);
        warehouse.setLastModifier(request.getUsername());
        return builder.body(ResponseUtils.getResponseBody(warehouseMapper.insert(warehouse)));
    }

    @ApiOperation(value = "删除仓库", notes = "删除仓库")
    @RequestMapping(value = "/deleteWareHouse", method = RequestMethod.GET)
    public ResponseEntity<JSONObject> deleteWareHouse(Integer wareHouseId)
            throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        WarehouseExample example = new WarehouseExample();
        example.createCriteria().andIdEqualTo(wareHouseId);

        return builder.body(ResponseUtils.getResponseBody(warehouseMapper.deleteByPrimaryKey(wareHouseId)));
    }

    @ApiOperation(value = "修改仓库", notes = "商家修改仓库")
    @RequestMapping(value = "/updateWareHouse", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> updateWareHouse(WareHouseRequest request)
            throws Exception {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        Warehouse warehouse = warehouseMapper.selectByPrimaryKey(request.getId());
        if (warehouse == null) {
            throw new Exception("仓库不存在");
        }
        if (!StringUtils.isEmpty(request.getHfDesc())) {
            warehouse.setHfDesc(request.getHfDesc());
        }
        if (!StringUtils.isEmpty(request.getHfName())) {
            warehouse.setHfName(request.getHfName());
        }
        if (!StringUtils.isEmpty(request.getUsername())) {
            warehouse.setLastModifier(request.getUsername());
        }
        warehouse.setModifyTime(LocalDateTime.now());
        return builder.body(ResponseUtils.getResponseBody(warehouseMapper.updateByPrimaryKey(warehouse)));
    }


//	@ApiOperation(value = "查询库存", notes = "某个仓库物品库存")
//	@RequestMapping(value = "/resp", method = RequestMethod.GET)
//	@ApiImplicitParams({
//			@ApiImplicitParam(paramType = "query", name = "wareHouseId", value = "仓库id", required = true, type = "Integer") })
//	public ResponseEntity<JSONObject> listRespByWareHouseId(@RequestParam Integer wareHouseId)
//			throws JSONException {
//		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
//		return builder.body(ResponseUtils.getResponseBody(""));
//	}
//	
//	
//	@ApiOperation(value = "添加库存", notes = "为某一个物品添加库存")
//	@RequestMapping(value = "/addResp", method = RequestMethod.POST)
//	public ResponseEntity<JSONObject> addResp(HfRespRequest instanceId)
//			throws JSONException {
//		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
//		return builder.body(ResponseUtils.getResponseBody(""));
//	}

}
