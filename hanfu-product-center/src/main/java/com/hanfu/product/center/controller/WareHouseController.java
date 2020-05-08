package com.hanfu.product.center.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.hanfu.product.center.dao.HWarehouseRespMapper;
import com.hanfu.product.center.dao.HfGoodsMapper;
import com.hanfu.product.center.dao.HfInStorageMapper;
import com.hanfu.product.center.dao.HfStoneRespMapper;
import com.hanfu.product.center.dao.StoneRespRecordMapper;
import com.hanfu.product.center.dao.WarehouseMapper;
import com.hanfu.product.center.model.HWarehouseResp;
import com.hanfu.product.center.model.HWarehouseRespExample;
import com.hanfu.product.center.model.HfGoods;
import com.hanfu.product.center.model.HfInStorage;
import com.hanfu.product.center.model.HfInStorageExample;
import com.hanfu.product.center.model.HfStoneResp;
import com.hanfu.product.center.model.HfStoneRespExample;
import com.hanfu.product.center.model.StoneRespRecord;
import com.hanfu.product.center.model.Warehouse;
import com.hanfu.product.center.model.WarehouseExample;
import com.hanfu.product.center.model.WarehouseRespRecord;
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
    
    @Autowired
    private HWarehouseRespMapper hWarehouseRespMapper;
    
    @Autowired
    private HfInStorageMapper hfInStorageMapper;
    
    @Autowired
    private HfGoodsMapper hfGoodsMapper;
    
    @Autowired
    private HfStoneRespMapper hfStoneRespMapper;
    
    @Autowired
    private StoneRespRecordMapper stoneRespRecordMapper;

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
    
//    @ApiOperation(value = "查询仓库物品", notes = "查询仓库物品")
//    @RequestMapping(value = "/listWareHouseGood", method = RequestMethod.GET)
//    public ResponseEntity<JSONObject> listWareHouseGood(Integer id)
//            throws JSONException {
//        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
//        HWarehouseRespExample example = new HWarehouseRespExample();
//        example.createCriteria().andWarehouseIdEqualTo(id);
//        List<HWarehouseResp> list = hWarehouseRespMapper.selectByExample(example);
//        return builder.body(ResponseUtils.getResponseBody(warehouseMapper.selectByExample(example)));
//    }

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
    
    
    @ApiOperation(value = "查询待入库物品", notes = "查询待入库物品")
    @RequestMapping(value = "/findGoodsWarsehouse", method = RequestMethod.GET)
    public ResponseEntity<JSONObject> findGoodsWarsehouse()
            throws Exception {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        List<HfInStorage> list = hfInStorageMapper.selectByExample(null);
        return builder.body(ResponseUtils.getResponseBody(hfInStorageMapper.selectByExample(null)));
    }
    
    @ApiOperation(value = "物品入库", notes = "物品入库")
    @RequestMapping(value = "/goodInWarsehouse", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> goodInWarsehouse(Integer warehouseId,Integer goodId, Integer quantity
    		,String typeWho, Integer userId, Integer type)
            throws Exception {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        Integer id = null;
        HWarehouseRespExample example = new HWarehouseRespExample();
        example.createCriteria().andWarehouseIdEqualTo(warehouseId).andGoodIdEqualTo(goodId);
        List<HWarehouseResp> list = hWarehouseRespMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(list)) {
        	HWarehouseResp hWarehouseResp = new HWarehouseResp();
        	hWarehouseResp.setGoodId(goodId);
        	hWarehouseResp.setQuantity(quantity);
        	hWarehouseResp.setWarehouseId(warehouseId);
        	hWarehouseResp.setCreateTime(LocalDateTime.now());
        	hWarehouseResp.setModifyTime(LocalDateTime.now());
        	hWarehouseResp.setIsDeleted((byte) 0);
        	hWarehouseRespMapper.insert(hWarehouseResp);
        	id = hWarehouseResp.getId();
        }else {
        	HWarehouseResp hWarehouseResp = list.get(0);
        	hWarehouseResp.setQuantity(hWarehouseResp.getQuantity()+quantity);
        	hWarehouseResp.setModifyTime(LocalDateTime.now());
        	hWarehouseRespMapper.updateByPrimaryKey(hWarehouseResp);
        	id = hWarehouseResp.getId();
        }
        WarehouseRespRecord record = new WarehouseRespRecord();
        record.setGoodId(goodId);
        record.setQuantity(quantity);
        record.setTypeWho(typeWho);
        record.setType(1);
        record.setUserId(String.valueOf(userId));
        record.setWarehouseId(warehouseId);
        record.setCreateTime(LocalDateTime.now());
        record.setModifyTime(LocalDateTime.now());
        record.setIsDeleted((byte) 0);
        return builder.body(ResponseUtils.getResponseBody(id));
    }
    
    @ApiOperation(value = "拒绝入库", notes = "拒绝入库")
    @RequestMapping(value = "/rejectGoodInWarsehouse", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> rejectGoodInWarsehouse(Integer goodId, Integer stoneId
    		,String typeWho, Integer userId, Integer type)
            throws Exception {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HfInStorageExample example = new HfInStorageExample();
        example.createCriteria().andGoodIdEqualTo(goodId);
        List<HfInStorage> list = hfInStorageMapper.selectByExample(example);
        if(!CollectionUtils.isEmpty(list)) {
        	list.get(0).setIsDeleted((byte) 1);
        }
        hfInStorageMapper.updateByPrimaryKey(list.get(0));
        HfGoods goods = hfGoodsMapper.selectByPrimaryKey(goodId);
        hfGoodsMapper.updateByPrimaryKey(goods);
        return builder.body(ResponseUtils.getResponseBody(goodId));
    }
    
    @ApiOperation(value = "物品出库", notes = "物品出库")
    @RequestMapping(value = "/goodOutWarsehouse", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> goodOutWarsehouse(Integer warehouseId,Integer goodId, Integer stoneId, Integer quantity
    		,String typeWho, Integer userId, Integer type)
            throws Exception {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        Integer id = null;
        HWarehouseRespExample example = new HWarehouseRespExample();
        example.createCriteria().andWarehouseIdEqualTo(warehouseId).andGoodIdEqualTo(goodId);
        List<HWarehouseResp> list = hWarehouseRespMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(list)) {
        	return builder.body(ResponseUtils.getResponseBody(-1));
        }else {
        	HWarehouseResp hWarehouseResp = list.get(0);
        	hWarehouseResp.setQuantity(hWarehouseResp.getQuantity()-quantity);
        	hWarehouseResp.setModifyTime(LocalDateTime.now());
        	hWarehouseRespMapper.updateByPrimaryKey(hWarehouseResp);
        	id = hWarehouseResp.getId();
        }
        WarehouseRespRecord record = new WarehouseRespRecord();
        record.setGoodId(goodId);
        record.setQuantity(quantity);
        record.setTypeWho(typeWho);
        record.setType(0);
        record.setUserId(String.valueOf(userId));
        record.setWarehouseId(warehouseId);
        record.setCreateTime(LocalDateTime.now());
        record.setModifyTime(LocalDateTime.now());
        record.setIsDeleted((byte) 0);
        record.setStoneId(stoneId);
        HfStoneRespExample example2 = new HfStoneRespExample();
        example2.createCriteria().andStoneIdEqualTo(stoneId).andGoodIdEqualTo(goodId);
        List<HfStoneResp> list2 = hfStoneRespMapper.selectByExample(example2);
        if(CollectionUtils.isEmpty(list2)) {
        	HfStoneResp resp = new HfStoneResp();
        	resp.setGoodId(goodId);
        	resp.setStoneId(stoneId);
        	resp.setQuantity(quantity);
        	resp.setCreateTime(LocalDateTime.now());
        	resp.setModifyTime(LocalDateTime.now());
        	resp.setIsDeleted((byte) 0);
        	hfStoneRespMapper.insert(resp);
        }else {
        	HfStoneResp resp = list2.get(0);
        	resp.setQuantity(resp.getQuantity()+quantity);
        	resp.setModifyTime(LocalDateTime.now());
        	hfStoneRespMapper.updateByPrimaryKey(resp);
        }
        StoneRespRecord respRecord = new StoneRespRecord();
        respRecord.setGoodId(goodId);
        respRecord.setStoneId(stoneId);
        respRecord.setType(1);
        respRecord.setQuantity(quantity);
        respRecord.setUserId(userId);
        respRecord.setCreateTime(LocalDateTime.now());
        respRecord.setModifyTime(LocalDateTime.now());
        respRecord.setIsDeleted((byte) 0);
        stoneRespRecordMapper.insert(respRecord);
        return builder.body(ResponseUtils.getResponseBody(id));
    }

}
