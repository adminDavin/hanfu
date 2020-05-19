package com.hanfu.product.center.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.hanfu.product.center.dao.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.WebRequest;

import com.alibaba.fastjson.JSONObject;
import com.hanfu.product.center.dao.HfBossMapper;
import com.hanfu.product.center.manual.dao.WarehouseDao;
import com.hanfu.product.center.manual.model.WarehouseFindConditional;
import com.hanfu.product.center.manual.model.WarehouseGoodDisplay;
import com.hanfu.product.center.model.HWarehouseResp;
import com.hanfu.product.center.model.HWarehouseRespExample;
import com.hanfu.product.center.model.HfBoss;
import com.hanfu.product.center.model.HfCategory;
import com.hanfu.product.center.model.HfGoodApply;
import com.hanfu.product.center.model.HfGoods;
import com.hanfu.product.center.model.HfGoodsExample;
import com.hanfu.product.center.model.HfInStorage;
import com.hanfu.product.center.model.HfResp;
import com.hanfu.product.center.model.HfStone;
import com.hanfu.product.center.model.HfStoneResp;
import com.hanfu.product.center.model.HfStoneRespExample;
import com.hanfu.product.center.model.Product;
import com.hanfu.product.center.model.ProductInstance;
import com.hanfu.product.center.model.ProductInstanceExample;
import com.hanfu.product.center.model.StoneRespRecord;
import com.hanfu.product.center.model.Warehouse;
import com.hanfu.product.center.model.WarehouseApplyGood;
import com.hanfu.product.center.model.WarehouseApplyGoodExample;
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

	private static final String REST_URL_PREFIX = "https://www.tjsichuang.cn:1443/api/user/";

	@Autowired
	private RestTemplate restTemplate;

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

	@Autowired
	private HfRespMapper hfRespMapper;

	@Autowired
	private HfCategoryMapper hfCategoryMapper;

	@Autowired
	private HfBossMapper hfBossMapper;

	@Autowired
	private HfStoneMapper hfStoneMapper;

	@Autowired
	private WarehouseRespRecordMapper warehouseRespRecordMapper;

	@Autowired
	private HfGoodApplyMapper hfGoodApplyMapper;

	@Autowired
	private ProductMapper productMapper;

	@Autowired
	private WarehouseDao warehouseDao;

	@Autowired
	private WarehouseApplyGoodMapper warehouseApplyGoodMapper;
	
	@Autowired
	private ProductInstanceMapper productInstanceMapper;

	@ApiOperation(value = "查询仓库", notes = "每个商家都有自己的仓库")
	@RequestMapping(value = "/listWareHouse", method = RequestMethod.GET)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "bossId", value = "商家id", required = true, type = "Integer") })
	public ResponseEntity<JSONObject> listWareHouse(@RequestParam Integer bossId) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		WarehouseExample example = new WarehouseExample();
		example.createCriteria().andBossidEqualTo(bossId).andIsDeletedEqualTo((short) 0);
		return builder.body(ResponseUtils.getResponseBody(warehouseMapper.selectByExample(example)));
	}

	@ApiOperation(value = "添加仓库", notes = "为商家创建仓库")
	@RequestMapping(value = "/createWareHouse", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> addWareHouse(WareHouseRequest request) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		Warehouse warehouse = new Warehouse();
		warehouse.setBossid(request.getBossId());
		warehouse.setHfDesc(request.getHfDesc());
		warehouse.setHfName(request.getHfName());
		warehouse.setHfRegion(request.getHfRegion());
		warehouse.setCreateTime(LocalDateTime.now());
		warehouse.setModifyTime(LocalDateTime.now());
		JSONObject js1 = restTemplate.getForObject(REST_URL_PREFIX + "hf-auth/findUserDetails?userId={userId}",
				JSONObject.class, request.getUserId());
		warehouse.setIsDeleted((short) 0);
		System.out.println(request.getUserId());
		System.out.println(js1.getJSONObject("data").getString("nickName"));
		warehouse.setLastModifier(js1.getJSONObject("data").getString("nickName"));
		warehouseMapper.insert(warehouse);
		return builder.body(ResponseUtils.getResponseBody(warehouse.getId()));
	}

//    @ApiOperation(value = "查询仓库物品", notes = "查询仓库物品")
//    @RequestMapping(value = "/listWareHouseGood", method = RequestMethod.GET)
//    public ResponseEntity<JSONObject> listWareHouseGood(Integer id)
//            throws JSONException {
//        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
//        HWarehouseRespExample example = new HWarehouseRespExample();
//        example.createCriteria().andWarehouseIdEqualTo(id);
//        List<HWarehouseResp> list = hWarehouseRespMapper.selectByExample(example);
//        return builder.body(ResponseUtils.getResponseBody());
//    }

	@ApiOperation(value = "删除仓库", notes = "删除仓库")
	@RequestMapping(value = "/deleteWareHouse", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> deleteWareHouse(Integer wareHouseId) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		WarehouseExample example = new WarehouseExample();
		example.createCriteria().andIdEqualTo(wareHouseId);
		Warehouse warehouse = warehouseMapper.selectByPrimaryKey(wareHouseId);
		warehouseMapper.updateByPrimaryKey(warehouse);
		return builder.body(ResponseUtils.getResponseBody(warehouse.getId()));
	}

	@ApiOperation(value = "修改仓库", notes = "商家修改仓库")
	@RequestMapping(value = "/updateWareHouse", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> updateWareHouse(WareHouseRequest request) throws Exception {
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
		if (!StringUtils.isEmpty(request.getHfRegion())) {
			warehouse.setHfRegion(request.getHfRegion());
		}
		warehouse.setModifyTime(LocalDateTime.now());
		return builder.body(ResponseUtils.getResponseBody(warehouseMapper.updateByPrimaryKey(warehouse)));
	}

	@ApiOperation(value = "查询商家物品", notes = "查询商家物品")
	@RequestMapping(value = "/findBossGoods", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> findBossGoods(Integer bossId) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		List<WarehouseGoodDisplay> result = new ArrayList<WarehouseGoodDisplay>();
		HfGoodsExample example = new HfGoodsExample();
		example.createCriteria().andBossIdEqualTo(bossId);
		List<HfGoods> list = hfGoodsMapper.selectByExample(example);
		list = list.stream().filter(h -> h.getInstanceId() == null).collect(Collectors.toList());
		for (int i = 0; i < list.size(); i++) {
			HfGoods good = list.get(i);
			WarehouseGoodDisplay display = new WarehouseGoodDisplay();
			display.setGoodName(good.getHfName());
			display.setGoodDesc(good.getGoodsDesc());
			HfCategory category = hfCategoryMapper.selectByPrimaryKey(good.getCategoryId());
			display.setCategory(category.getHfName());
			HfResp resp = hfRespMapper.selectByPrimaryKey(good.getRespId());
			display.setQuantity(resp.getQuantity());
			display.setBossId(bossId);
			display.setProductId(good.getProductId());
			display.setGoodId(good.getId());
			result.add(display);
		}
		return builder.body(ResponseUtils.getResponseBody(result));
	}

	@ApiOperation(value = "仓库申请货物", notes = "仓库申请货物")
	@RequestMapping(value = "/applyGoodsWarsehouse", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> applyGoodsWarsehouse(Integer productId, Integer goodId, Integer userId,
			Integer warehouseId, Integer bossId, Integer quantity) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		HfGoods goods = hfGoodsMapper.selectByPrimaryKey(goodId);
		HfResp hfResp = hfRespMapper.selectByPrimaryKey(goods.getRespId());
		if (hfResp.getQuantity() < quantity) {
			return builder.body(ResponseUtils.getResponseBody(0));
		}
		HfInStorage inStorage = new HfInStorage();
		inStorage.setDataType("1");
		inStorage.setStatus(1);
		inStorage.setBossId(1);
		inStorage.setProducId(productId);
		inStorage.setGoodId(goodId);
		inStorage.setUserId(userId);
		inStorage.setQuantity(quantity);
		inStorage.setCreateTime(LocalDateTime.now());
		inStorage.setModifyTime(LocalDateTime.now());
		inStorage.setIsDeleted((byte) 0);
		inStorage.setWarehouseId(warehouseId);
		hfInStorageMapper.insert(inStorage);
		WarehouseApplyGood applyGood = new WarehouseApplyGood();
		applyGood.setApplyId(inStorage.getId());
		applyGood.setStatus(2);
		applyGood.setBossId(bossId);
		applyGood.setGoodId(goodId);
		applyGood.setProductId(productId);
		applyGood.setQuantity(quantity);
		applyGood.setUserId(userId);
		applyGood.setWarehouseId(warehouseId);
		applyGood.setCreateTime(LocalDateTime.now());
		applyGood.setModifyTime(LocalDateTime.now());
		applyGood.setIsDeleted((byte) 0);
		warehouseApplyGoodMapper.insert(applyGood);
		return builder.body(ResponseUtils.getResponseBody(applyGood.getId()));
	}

	@ApiOperation(value = "查看由仓库提交得申请列表", notes = "查看由仓库提交得申请列表")
	@RequestMapping(value = "/findApplyforWarehouse", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> findApplyforWarehouse(Integer bossId) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		List<WarehouseGoodDisplay> result = new ArrayList<WarehouseGoodDisplay>();
		WarehouseApplyGoodExample example = new WarehouseApplyGoodExample();
		example.createCriteria().andBossIdEqualTo(bossId);
		List<WarehouseApplyGood> list = warehouseApplyGoodMapper.selectByExample(example);
		for (int i = 0; i < list.size(); i++) {
			WarehouseApplyGood applyGood = list.get(i);
			WarehouseGoodDisplay display = new WarehouseGoodDisplay();
			HfGoods good = hfGoodsMapper.selectByPrimaryKey(applyGood.getGoodId());
			display.setId(applyGood.getId());
			HfResp resp = hfRespMapper.selectByPrimaryKey(good.getRespId());
			display.setTotal(resp.getQuantity());
			display.setGoodName(good.getHfName());
			display.setGoodDesc(good.getGoodsDesc());
			display.setGoodId(good.getId());
			display.setStatus(applyGood.getStatus());
			display.setTime(applyGood.getCreateTime());
			JSONObject js1 = restTemplate.getForObject(REST_URL_PREFIX + "hf-auth/findUserDetails?userId={userId}",
					JSONObject.class, applyGood.getUserId());
			display.setName(js1.getJSONObject("data").getString("nickName"));
			HfCategory hfCategory = hfCategoryMapper.selectByPrimaryKey(good.getCategoryId());
			display.setCategory(hfCategory.getHfName());
			display.setProductId(good.getProductId());
			display.setQuantity(applyGood.getQuantity());
			display.setWarehouseId(applyGood.getWarehouseId());
			Warehouse warehouse = warehouseMapper.selectByPrimaryKey(applyGood.getWarehouseId());
			display.setWarehouseName(warehouse.getHfName());
			display.setWarehouseRegoin(warehouse.getHfRegion());
			result.add(display);
		}
		return builder.body(ResponseUtils.getResponseBody(result));
	}

	@ApiOperation(value = "查询待入库物品", notes = "查询待入库物品/出库物品")
	@RequestMapping(value = "/findGoodsWarsehouse", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> findGoodsWarsehouse(Integer warehouseId, String dataType, String goodName,
			String categoryName) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		List<WarehouseGoodDisplay> result = new ArrayList<WarehouseGoodDisplay>();
		WarehouseFindConditional wfc = new WarehouseFindConditional();
		wfc.setGoodName(goodName);
		wfc.setData_type(dataType);
		wfc.setCategoryName(categoryName);
		wfc.setWarehousrId(warehouseId);
		List<HfInStorage> list = warehouseDao.findHfInStorage(wfc);
		System.out.println(list);
		for (int i = 0; i < list.size(); i++) {
			HfInStorage storage = list.get(i);
			HWarehouseRespExample hWarehouseRespExample = new HWarehouseRespExample();
			System.out.println(storage.getWarehouseId());
			hWarehouseRespExample.createCriteria().andWarehouseIdEqualTo(warehouseId).andGoodIdEqualTo(storage.getGoodId());
			List<HWarehouseResp> resps = hWarehouseRespMapper.selectByExample(hWarehouseRespExample);
			WarehouseGoodDisplay display = new WarehouseGoodDisplay();
			display.setTotal(resps.get(0).getQuantity());
			display.setId(storage.getId());
			display.setStatus(storage.getStatus());
			display.setGoodId(storage.getGoodId());
			display.setProductId(storage.getProducId());
			HfGoods goods = hfGoodsMapper.selectByPrimaryKey(storage.getGoodId());
//			HfResp hfResp = hfRespMapper.selectByPrimaryKey(goods.getRespId());
			HfCategory category = hfCategoryMapper.selectByPrimaryKey(goods.getCategoryId());
			display.setGoodName(goods.getHfName());
			display.setGoodDesc(goods.getGoodsDesc());
			display.setQuantity(storage.getQuantity());
			display.setBossId(storage.getBossId());
			if ("0".equals(dataType)) {
				HfGoodApply apply = hfGoodApplyMapper.selectByPrimaryKey(storage.getApplyId());
				display.setWarehouseId(apply.getWarehouseId());
				Warehouse warehouse = warehouseMapper.selectByPrimaryKey(apply.getWarehouseId());
				display.setWarehouseName(warehouse.getHfName());
			}
			if ("0".equals(storage.getType())) {
				HfBoss boss = hfBossMapper.selectByPrimaryKey(storage.getBossId());
				display.setTypeName(boss.getName());
			}
			if ("1".equals(storage.getType())) {
				HfStone hfStone = hfStoneMapper.selectByPrimaryKey(storage.getStoneId());
				display.setTypeName(hfStone.getHfName());
				display.setStoneId(hfStone.getId());
			}
			display.setType(storage.getType());
			display.setCategory(category.getHfName());
			display.setTime(storage.getModifyTime());
			JSONObject js1 = restTemplate.getForObject(REST_URL_PREFIX + "hf-auth/findUserDetails?userId={userId}",
					JSONObject.class, storage.getUserId());
			display.setName(js1.getJSONObject("data").getString("nickName"));
			result.add(display);
		}
		return builder.body(ResponseUtils.getResponseBody(result));
	}

	@ApiOperation(value = "查询仓库物品", notes = "查询仓库物品")
	@RequestMapping(value = "/findGoodsByWarsehouse", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> findGoodsByWarsehouse(Integer warehouseId, String goodName, String categoryName)
			throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		List<WarehouseGoodDisplay> result = new ArrayList<WarehouseGoodDisplay>();
		WarehouseFindConditional wfc = new WarehouseFindConditional();
		wfc.setWarehousrId(warehouseId);
		wfc.setGoodName(goodName);
		wfc.setCategoryName(categoryName);
		List<HWarehouseResp> list = warehouseDao.findHWarehouseResp(wfc);
		for (int i = 0; i < list.size(); i++) {
			HWarehouseResp storage = list.get(i);
			WarehouseGoodDisplay display = new WarehouseGoodDisplay();
			display.setGoodId(storage.getGoodId());
			display.setProductId(storage.getProductId());
			HfGoods goods = hfGoodsMapper.selectByPrimaryKey(storage.getGoodId());
//			HfResp hfResp = hfRespMapper.selectByPrimaryKey(goods.getRespId());
			HfCategory category = hfCategoryMapper.selectByPrimaryKey(goods.getCategoryId());
			display.setGoodName(goods.getHfName());
			display.setGoodDesc(goods.getGoodsDesc());
			display.setQuantity(storage.getQuantity());
//			if("0".equals(storage.getType())) {
//				HfBoss boss = hfBossMapper.selectByPrimaryKey(storage.getBossId());
//				display.setTypeName(boss.getName());
//			}
//			if("1".equals(storage.getType())) {
//				HfStone hfStone = hfStoneMapper.selectByPrimaryKey(storage.getStoneId());
//				display.setTypeName(hfStone.getHfName());
//			}
//			display.setType(storage.getType());
			display.setCategory(category.getHfName());
			display.setTime(storage.getModifyTime());
//			JSONObject js1 = restTemplate.getForObject(REST_URL_PREFIX + "hf-auth/findUserDetails?userId={userId}",
//					JSONObject.class, storage.getUserId());
//			display.setName(js1.getJSONObject("data").getString("nickName"));
			result.add(display);
		}
		return builder.body(ResponseUtils.getResponseBody(result));
	}

//    @ApiOperation(value = "物品入库", notes = "物品入库")
//    @RequestMapping(value = "/goodInWarsehouse", method = RequestMethod.POST)
//    public ResponseEntity<JSONObject> goodInWarsehouse(Integer inStorgeId, Integer warehouseId, Integer productId, Integer goodId, Integer quantity
//    		,String typeWho, Integer userId, Integer type, Integer bossId, String stoneId)
//            throws Exception {
//        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
//        HfInStorage hfInStorage = hfInStorageMapper.selectByPrimaryKey(inStorgeId);
//        Integer id = null;
//        HWarehouseRespExample example = new HWarehouseRespExample();
//        example.createCriteria().andWarehouseIdEqualTo(warehouseId).andGoodIdEqualTo(goodId);
//        List<HWarehouseResp> list = hWarehouseRespMapper.selectByExample(example);
//        if(CollectionUtils.isEmpty(list)) {
//        	HWarehouseResp hWarehouseResp = new HWarehouseResp();
//        	hWarehouseResp.setProductId(productId);
//        	hWarehouseResp.setGoodId(goodId);
//        	hWarehouseResp.setQuantity(quantity);
//        	hWarehouseResp.setWarehouseId(warehouseId);
//        	hWarehouseResp.setCreateTime(LocalDateTime.now());
//        	hWarehouseResp.setModifyTime(LocalDateTime.now());
//        	hWarehouseResp.setIsDeleted((byte) 0);
//        	hWarehouseRespMapper.insert(hWarehouseResp);
//        	id = hWarehouseResp.getId();
//        }else {
//        	HWarehouseResp hWarehouseResp = list.get(0);
//        	hWarehouseResp.setQuantity(hWarehouseResp.getQuantity()+quantity);
//        	hWarehouseResp.setModifyTime(LocalDateTime.now());
//        	hWarehouseRespMapper.updateByPrimaryKey(hWarehouseResp);
//        	id = hWarehouseResp.getId();
//        }
//        WarehouseRespRecord record = new WarehouseRespRecord();
//        record.setProductId(productId);
//        record.setGoodId(goodId);
//        record.setQuantity(quantity);
//        record.setTypeWho(typeWho);
//        if("1".equals(typeWho)) {
//        	record.setStoneId(Integer.valueOf(stoneId));
//        }
//        record.setBossId(bossId);
//        record.setType(1);
//        record.setUserId(String.valueOf(userId));
//        record.setWarehouseId(warehouseId);
//        record.setCreateTime(LocalDateTime.now());
//        record.setModifyTime(LocalDateTime.now());
//        record.setIsDeleted((byte) 0);
//        warehouseRespRecordMapper.insert(record);
//        hfInStorage.setIsDeleted((byte) 1);
//        hfInStorageMapper.updateByPrimaryKey(hfInStorage);
//        HfGoods goods = hfGoodsMapper.selectByPrimaryKey(hfInStorage.getGoodId());
//        HfResp resp = hfRespMapper.selectByPrimaryKey(goods.getRespId());
//        if(resp.getQuantity() < quantity) {
//        	return builder.body(ResponseUtils.getResponseBody(0));
//        }
//        resp.setQuantity(resp.getQuantity()-quantity);
//        hfRespMapper.updateByPrimaryKey(resp);
//        return builder.body(ResponseUtils.getResponseBody(id));
//    }

	@ApiOperation(value = "拒绝出库申请", notes = "拒绝出库申请")
	@RequestMapping(value = "/rejectGoodOutWarsehouse", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> rejectGoodOutWarsehouse(Integer outStorageId) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		HfInStorage hfInStorage = hfInStorageMapper.selectByPrimaryKey(outStorageId);
		hfInStorage.setStatus(2);
		hfInStorageMapper.updateByPrimaryKey(hfInStorage);
		HfGoodApply apply = hfGoodApplyMapper.selectByPrimaryKey(hfInStorage.getApplyId());
		apply.setModifyTime(LocalDateTime.now());
		apply.setStatus(3);
		hfGoodApplyMapper.updateByPrimaryKey(apply);
		return builder.body(ResponseUtils.getResponseBody(apply.getId()));
	}

	@ApiOperation(value = "店铺申请物品从仓库", notes = "店铺申请物品从仓库")
	@RequestMapping(value = "/stoneApplyGood", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> stoneApplyGood(Integer stoneId, Integer productId, Integer goodId,
			Integer quantity, Integer userId, Integer warehouseId) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		HWarehouseRespExample respExample = new HWarehouseRespExample();
		respExample.createCriteria().andWarehouseIdEqualTo(warehouseId).andGoodIdEqualTo(goodId);
		List<HWarehouseResp> resps = hWarehouseRespMapper.selectByExample(respExample);
		if(!CollectionUtils.isEmpty(resps)) {
			HWarehouseResp resp = resps.get(0);
			if(resp.getQuantity() < quantity) {
				return builder.body(ResponseUtils.getResponseBody(0));
			}
		}
		Product product = productMapper.selectByPrimaryKey(productId);
		HfGoodApply apply = new HfGoodApply();
		apply.setGoodId(goodId);
		apply.setProductId(productId);
		apply.setQuantity(quantity);
		apply.setStoneId(stoneId);
		apply.setStatus(1);
		apply.setWarehouseId(warehouseId);
		apply.setCreateTime(LocalDateTime.now());
		apply.setModifyTime(LocalDateTime.now());
		apply.setIsDeleted((byte) 0);
		hfGoodApplyMapper.insert(apply);
		HfInStorage storage = new HfInStorage();
		storage.setBossId(product.getBossId());
		storage.setStatus(1);
		storage.setDataType("0");
		storage.setApplyId(apply.getId());
		storage.setGoodId(goodId);
		storage.setStoneId(stoneId);
		storage.setProducId(productId);
		storage.setStoneId(stoneId);
		storage.setType("1");
		storage.setQuantity(quantity);
		storage.setUserId(userId);
		storage.setWarehouseId(warehouseId);
		storage.setCreateTime(LocalDateTime.now());
		storage.setModifyTime(LocalDateTime.now());
		storage.setIsDeleted((byte) 0);
		hfInStorageMapper.insert(storage);
		return builder.body(ResponseUtils.getResponseBody(apply.getId()));
	}

	@ApiOperation(value = "商家同意仓库申请物品", notes = "商家同意仓库申请物品/拒絕")
	@RequestMapping(value = "/bossAgreeApply", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> bossAgreeApply(Integer applyId, Integer type) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		WarehouseApplyGood apply = warehouseApplyGoodMapper.selectByPrimaryKey(applyId);
		HfInStorage hfInStorage = hfInStorageMapper.selectByPrimaryKey(apply.getApplyId());
		HfGoods goods = hfGoodsMapper.selectByPrimaryKey(hfInStorage.getGoodId());
		HfResp hfResp = hfRespMapper.selectByPrimaryKey(goods.getRespId());
		if (type == 1) {
			if (hfResp.getQuantity() < apply.getQuantity()) {
				return builder.body(ResponseUtils.getResponseBody(0));
			}
			hfResp.setQuantity(hfResp.getQuantity() - apply.getQuantity());
			hfRespMapper.updateByPrimaryKey(hfResp);
			hfInStorage.setStatus(2);
			hfInStorageMapper.updateByPrimaryKey(hfInStorage);
			HWarehouseRespExample example = new HWarehouseRespExample();
			example.createCriteria().andWarehouseIdEqualTo(hfInStorage.getWarehouseId())
					.andGoodIdEqualTo(hfInStorage.getGoodId());
			List<HWarehouseResp> list = hWarehouseRespMapper.selectByExample(example);
			apply.setStatus(1);
			warehouseApplyGoodMapper.updateByPrimaryKey(apply);
			if (CollectionUtils.isEmpty(list)) {
				HWarehouseResp resp = new HWarehouseResp();
				resp.setWarehouseId(apply.getWarehouseId());
				resp.setProductId(apply.getProductId());
				resp.setGoodId(apply.getGoodId());
				resp.setQuantity(apply.getQuantity());
				resp.setCreateTime(LocalDateTime.now());
				resp.setModifyTime(LocalDateTime.now());
				resp.setIsDeleted((byte) 0);
				hWarehouseRespMapper.insert(resp);
			} else {
				HWarehouseResp resp = list.get(0);
				resp.setQuantity(resp.getQuantity() + hfInStorage.getQuantity());
				resp.setModifyTime(LocalDateTime.now());
				hWarehouseRespMapper.updateByPrimaryKey(resp);
			}
			WarehouseRespRecord record = new WarehouseRespRecord();
			record.setProductId(apply.getProductId());
			record.setGoodId(apply.getGoodId());
			record.setQuantity(apply.getQuantity());
//            record.setTypeWho(typeWho);
//            if("1".equals(typeWho)) {
//            	record.setStoneId(Integer.valueOf(stoneId));
//            }
			record.setBossId(apply.getBossId());
			record.setType(1);
			record.setUserId(String.valueOf(apply.getUserId()));
			record.setWarehouseId(apply.getWarehouseId());
			record.setCreateTime(LocalDateTime.now());
			record.setModifyTime(LocalDateTime.now());
			record.setIsDeleted((byte) 0);
			warehouseRespRecordMapper.insert(record);
			hfInStorage.setIsDeleted((byte) 1);
			hfInStorageMapper.updateByPrimaryKey(hfInStorage);
		} else {
			apply.setStatus(1);
			warehouseApplyGoodMapper.updateByPrimaryKey(apply);
			hfInStorage = hfInStorageMapper.selectByPrimaryKey(apply.getApplyId());
			hfInStorage.setStatus(3);
			hfInStorageMapper.updateByPrimaryKey(hfInStorage);
		}
		return builder.body(ResponseUtils.getResponseBody(apply.getId()));
	}

	@ApiOperation(value = "物品出库", notes = "物品出库")
	@RequestMapping(value = "/goodOutWarsehouse", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> goodOutWarsehouse(Integer outStorageId, Integer userId)
			throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		HfInStorage hfInStorage = hfInStorageMapper.selectByPrimaryKey(outStorageId);
		HWarehouseRespExample example = new HWarehouseRespExample();
		example.createCriteria().andWarehouseIdEqualTo(hfInStorage.getWarehouseId()).andGoodIdEqualTo(hfInStorage.getGoodId());
		List<HWarehouseResp> list = hWarehouseRespMapper.selectByExample(example);
		Integer id = null;
		if (CollectionUtils.isEmpty(list)) {
			return builder.body(ResponseUtils.getResponseBody(-1));
		} else {
			HWarehouseResp hWarehouseResp = list.get(0);
			if (hWarehouseResp.getQuantity() < hfInStorage.getQuantity()) {
				return builder.body(ResponseUtils.getResponseBody(0));
			}
			hWarehouseResp.setQuantity(hWarehouseResp.getQuantity() - hfInStorage.getQuantity());
			hWarehouseResp.setModifyTime(LocalDateTime.now());
			hWarehouseRespMapper.updateByPrimaryKey(hWarehouseResp);
			id = hWarehouseResp.getId();
		}
		
		HfGoods goods = hfGoodsMapper.selectByPrimaryKey(hfInStorage.getGoodId());
		hfInStorage.setStatus(2);
		hfInStorage.setModifyTime(LocalDateTime.now());
		hfInStorageMapper.updateByPrimaryKey(hfInStorage);
		HfGoodApply apply = hfGoodApplyMapper.selectByPrimaryKey(hfInStorage.getApplyId());
		apply.setModifyTime(LocalDateTime.now());
		apply.setStatus(2);
		hfGoodApplyMapper.updateByPrimaryKey(apply);
		WarehouseRespRecord record = new WarehouseRespRecord();
		record.setProductId(hfInStorage.getProducId());
		record.setGoodId(hfInStorage.getGoodId());
		record.setQuantity(hfInStorage.getQuantity());
//		record.setTypeWho(typeWho);
		record.setType(0);
		record.setBossId(hfInStorage.getBossId());
		record.setUserId(String.valueOf(userId));
		record.setWarehouseId(hfInStorage.getWarehouseId());
		record.setCreateTime(LocalDateTime.now());
		record.setModifyTime(LocalDateTime.now());
		record.setIsDeleted((byte) 0);
		record.setStoneId(hfInStorage.getStoneId());
		warehouseRespRecordMapper.insert(record);
		
		Integer instanceId = null;
		ProductInstanceExample productInstanceExample = new ProductInstanceExample();
		productInstanceExample.createCriteria().andStoneIdEqualTo(hfInStorage.getStoneId()).andProductIdEqualTo(hfInStorage.getProducId());
		productInstanceMapper.selectByExample(productInstanceExample);
		List<ProductInstance> instances = productInstanceMapper.selectByExample(productInstanceExample);
		if (CollectionUtils.isEmpty(instances)){
			ProductInstance productInstance = new ProductInstance();
			productInstance.setCreateTime(LocalDateTime.now());
			productInstance.setModifyTime(LocalDateTime.now());
			productInstance.setLastModifier(String.valueOf(userId));
			productInstance.setIsDeleted((short) 0);
			productInstance.setStoneId(hfInStorage.getStoneId());
			productInstance.setEvaluateCount(0);
			productInstance.setProductId(hfInStorage.getProducId());
			productInstance.setBossId(goods.getBossId());
			productInstance.setCategoryId(goods.getCategoryId());
			productInstance.setBrandId(1);
			productInstanceMapper.insertSelective(productInstance);
			instanceId = productInstance.getId();
		}else {
			instanceId = instances.get(0).getId();
		}
		HfStoneRespExample example2 = new HfStoneRespExample();
		example2.createCriteria().andStoneIdEqualTo(hfInStorage.getStoneId()).andGoodIdEqualTo(hfInStorage.getGoodId());
		List<HfStoneResp> list2 = hfStoneRespMapper.selectByExample(example2);
		if (CollectionUtils.isEmpty(list2)) {
			HfStoneResp resp = new HfStoneResp();
			resp.setProductId(hfInStorage.getProducId());
			resp.setGoodId(hfInStorage.getGoodId());
			resp.setStoneId(hfInStorage.getStoneId());
			resp.setQuantity(hfInStorage.getQuantity());
			resp.setInstanceId(instanceId);
			resp.setCreateTime(LocalDateTime.now());
			resp.setModifyTime(LocalDateTime.now());
			resp.setIsDeleted((byte) 0);
			resp.setType("2");
			hfStoneRespMapper.insert(resp);
		} else {
			HfStoneResp resp = list2.get(0);
			resp.setQuantity(resp.getQuantity() + hfInStorage.getQuantity());
			resp.setModifyTime(LocalDateTime.now());
			hfStoneRespMapper.updateByPrimaryKey(resp);
		}
		StoneRespRecord respRecord = new StoneRespRecord();
		respRecord.setProductId(hfInStorage.getProducId());
		respRecord.setGoodId(hfInStorage.getGoodId());
		respRecord.setStoneId(hfInStorage.getStoneId());
		respRecord.setType(1);
		respRecord.setQuantity(hfInStorage.getQuantity());
		respRecord.setUserId(userId);
		respRecord.setCreateTime(LocalDateTime.now());
		respRecord.setModifyTime(LocalDateTime.now());
		respRecord.setIsDeleted((byte) 0);
		stoneRespRecordMapper.insert(respRecord);
		hfInStorage.setIsDeleted((byte) 1);
		hfInStorageMapper.updateByPrimaryKey(hfInStorage);
		return builder.body(ResponseUtils.getResponseBody(id));
	}

	@ApiOperation(value = "查询出入库记录", notes = "查询出入库记录")
	@RequestMapping(value = "/findWarsehouseRecord", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> findWarsehouseRecord(String goodName, Integer warehouseId, Date start, Date end,
			Integer type) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		List<WarehouseGoodDisplay> result = new ArrayList<WarehouseGoodDisplay>();
		WarehouseFindConditional wfc = new WarehouseFindConditional();
		wfc.setEnd(end);
		wfc.setGoodName(goodName);
		wfc.setStart(start);
		wfc.setType(type);
		wfc.setWarehousrId(warehouseId);
		List<WarehouseRespRecord> list = warehouseDao.findWarehouseRespRecord(wfc);
		for (int i = 0; i < list.size(); i++) {
			WarehouseRespRecord storage = list.get(i);
			WarehouseGoodDisplay display = new WarehouseGoodDisplay();
			Warehouse warehouse = warehouseMapper.selectByPrimaryKey(storage.getWarehouseId());
			display.setWarehouseName(warehouse.getHfName());
			display.setGoodId(storage.getGoodId());
			display.setProductId(storage.getProductId());
			HfGoods goods = hfGoodsMapper.selectByPrimaryKey(storage.getGoodId());
//			HfResp hfResp = hfRespMapper.selectByPrimaryKey(goods.getRespId());
			HfCategory category = hfCategoryMapper.selectByPrimaryKey(goods.getCategoryId());
			display.setGoodName(goods.getHfName());
			display.setGoodDesc(goods.getGoodsDesc());
			display.setQuantity(storage.getQuantity());
//			if ("0".equals(storage.getTypeWho())) {
//				HfBoss boss = hfBossMapper.selectByPrimaryKey(storage.getBossId());
//				display.setTypeName(boss.getName());
//			}
			if ("0".equals(String.valueOf(storage.getType()))) {
				HfStone hfStone = hfStoneMapper.selectByPrimaryKey(storage.getStoneId());
				System.out.println("是否进入");
				display.setTypeName(hfStone.getHfName());
				display.setStoneId(hfStone.getId());
			}
			display.setBossId(storage.getBossId());
			display.setType(String.valueOf(storage.getType()));
			display.setCategory(category.getHfName());
			display.setTime(storage.getModifyTime());
			JSONObject js1 = restTemplate.getForObject(REST_URL_PREFIX + "hf-auth/findUserDetails?userId={userId}",
					JSONObject.class, storage.getUserId());
			display.setName(js1.getJSONObject("data").getString("nickName"));
			result.add(display);
		}
		return builder.body(ResponseUtils.getResponseBody(result));
	}

	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request) {
		// 转换日期
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));// CustomDateEditor为自定义日期编辑器
	}

}
