package com.hanfu.product.center.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.hanfu.product.center.dao.HfCategoryMapper;
import com.hanfu.product.center.dao.ProductInfoMapper;
import com.hanfu.product.center.dao.ProductInstanceMapper;
import com.hanfu.product.center.dao.ProductMapper;
import com.hanfu.product.center.dao.ProductSpecMapper;
import com.hanfu.product.center.model.HfCategory;
import com.hanfu.product.center.model.HfGoods;
import com.hanfu.product.center.model.HfStone;
import com.hanfu.product.center.model.ProductExample;
import com.hanfu.product.center.model.ProductInfo;
import com.hanfu.product.center.model.ProductInfoExample;
import com.hanfu.product.center.model.ProductInstance;
import com.hanfu.product.center.model.ProductInstanceExample;
import com.hanfu.product.center.model.ProductSpec;
import com.hanfu.product.center.model.ProductSpecExample;
import com.hanfu.product.center.request.CategoryRequest;
import com.hanfu.product.center.request.ProductInfoRequest;
import com.hanfu.product.center.request.ProductInstanceRequest;
import com.hanfu.product.center.request.ProductSpecRequest;
import com.hanfu.product.center.response.handler.ResponseEntity;
import com.hanfu.product.center.response.handler.ResponseEntity.BodyBuilder;
import com.hanfu.product.center.response.handler.ResponseUtils;
import com.hanfu.product.center.service.GoodsService;
import com.hanfu.product.center.service.StoreService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/product")
@Api
public class ProductController {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private HfCategoryMapper hfCategoryMapper;

	@Autowired
	private ProductMapper productMapper;
	
	@Autowired
	private ProductInfoMapper productInfoMapper;
	
	@Autowired
	private ProductSpecMapper productSpecMapper;
	
	@Autowired
	private ProductInstanceMapper productInstanceMapper;
	
	@Autowired
	private GoodsService goodsService;
	
	@Autowired
	private StoreService storeService;

	@ApiOperation(value = "获取类目列表", notes = "获取系统支持的商品类目")
	@RequestMapping(value = "/category", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> listCategory() throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		return builder.body(ResponseUtils.getResponseBody(hfCategoryMapper.selectByExample(null)));
	}

	@ApiOperation(value = "添加类目", notes = "添加系统支持的商品类目")
	@RequestMapping(value = "/category", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> AddCategory(CategoryRequest request) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		HfCategory category = new HfCategory();
		category.setLevelId(request.getLevelId());
		category.setHfName(request.getCategory());
		category.setParentCategoryId(request.getParentCategoryId());
		return builder.body(ResponseUtils.getResponseBody(hfCategoryMapper.insert(category)));
	}

	@ApiOperation(value = "获取商品列表", notes = "根据商家获取商家录入的商品列表")
	@RequestMapping(value = "/byBossId", method = RequestMethod.GET)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "bossId", value = "商家ID", required = true, type = "Integer") })
	public ResponseEntity<JSONObject> listProduct(@RequestParam(name = "bossId") Integer bossId) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		ProductExample example = new ProductExample();
		example.createCriteria().andBossIdEqualTo(bossId);
		return builder.body(ResponseUtils.getResponseBody(productMapper.selectByExample(example)));
	}

	@ApiOperation(value = "获取商品属性", notes = "根据商品id获取商品的属性值")
	@RequestMapping(value = "/attributes", method = RequestMethod.GET)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "productId", value = "商家ID", required = true, type = "Integer") })
	public ResponseEntity<JSONObject> getProductInfo(@RequestParam(name = "productId") Integer productId) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		ProductInfoExample example = new ProductInfoExample();
		example.createCriteria().andProductIdEqualTo(productId);
		return builder.body(ResponseUtils.getResponseBody(productInfoMapper.selectByExample(example)));
	}

	@ApiOperation(value = "添加商品属性", notes = "为某一个商品添加属性")
	@RequestMapping(value = "/addAttribute", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> addProductInfo(ProductInfoRequest request) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		ProductInfo item = new ProductInfo();
		item.setHfName(request.getHfName());
		item.setHfRemark(request.getHfRemark());
		item.setHfValue(request.getInfoValue());
		item.setLastModifier(request.getLastModifier());
		item.setProductId(request.getProductId());
		item.setValueUnit(request.getInfoDesc());
		return builder.body(ResponseUtils.getResponseBody(productInfoMapper.insert(item)));
	}
	
	@ApiOperation(value = "获取商品规格", notes = "根据商品id获取商品的规格描述")
	@RequestMapping(value = "/specifies", method = RequestMethod.GET)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "productId", value = "商家ID", required = true, type = "Integer", example = "0") })
	public ResponseEntity<JSONObject> getProductSpec(@RequestParam(name = "productId") Integer productId) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		ProductSpecExample example = new ProductSpecExample();
		example.createCriteria().andProductIdEqualTo(productId);
		return builder.body(ResponseUtils.getResponseBody(productSpecMapper.selectByExample(example)));
	}
	
	@ApiOperation(value = "添加商品规格", notes = "为某一个商品添加规格")
	@RequestMapping(value = "/addSpecify", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> addProductSpec(ProductSpecRequest request) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		ProductSpec item = new ProductSpec();
		item.setProductId(request.getProductId());
		item.setHfName(request.getHfName());
		item.setSpecType(request.getSpecType());
		item.setSpecUnit(request.getSpecUnit());
		item.setSpecValue(request.getSpecUnit());
		return builder.body(ResponseUtils.getResponseBody(productSpecMapper.insert(item)));
	}
	
	@ApiOperation(value = "获取商品所在店铺的信息", notes = "根据商品获取商品所在的店铺信息")
	@RequestMapping(value = "/stones", method = RequestMethod.GET)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "productId", value = "商家ID", required = true, type = "Integer") })
	public ResponseEntity<JSONObject> getStones(@RequestParam(name = "productId") Integer productId) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		ProductSpecExample example = new ProductSpecExample();
		example.createCriteria().andProductIdEqualTo(productId);
//		todo 查看商品所在的店铺信息
		HfStone hfStone = storeService.findByProductId(productId);
		return builder.body(ResponseUtils.getResponseBody(hfStone));
	}
	
	@ApiOperation(value = "获取店铺所有商品", notes = "根据商铺id获取商铺的所有商品")
	@RequestMapping(value = "/byStoneId", method = RequestMethod.GET)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "stoneId", value = "商鋪id", required = true, type = "Integer") })
	public ResponseEntity<JSONObject> getStoneProduct(@RequestParam(name = "stoneId") Integer stoneId) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		ProductInstanceExample example = new ProductInstanceExample();
		example.createCriteria().andStoneIdEqualTo(stoneId);
//		todo 获取店铺内所有商品的详情
		Integer[] instanceId = goodsService.findAllByStoreId(stoneId);
		List<HfGoods> result = goodsService.findAllByInstanceId(instanceId);
		return builder.body(ResponseUtils.getResponseBody(result));
	}
	
	
	@ApiOperation(value = "商品添加到店铺", notes = "将商品添加到某一个店铺")
	@RequestMapping(value = "/addToStone", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> addStone(ProductInstanceRequest request) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		ProductInstance item = new ProductInstance();
		item.setProductId(request.getProductId());
		item.setStoneId(request.getStoneId());
		item.setLastModifier(request.getLastModifier());
		return builder.body(ResponseUtils.getResponseBody(productInstanceMapper.insert(item)));
	}
	
}
