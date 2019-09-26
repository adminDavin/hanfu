package com.hanfu.product.center.controller;

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
import com.hanfu.product.center.dao.FileDescMapper;
import com.hanfu.product.center.dao.GoodsSpecMapper;
import com.hanfu.product.center.dao.HfGoodsMapper;
import com.hanfu.product.center.dao.HfGoodsPictrueMapper;
import com.hanfu.product.center.model.FileDesc;
import com.hanfu.product.center.model.GoodsSpec;
import com.hanfu.product.center.model.GoodsSpecExample;
import com.hanfu.product.center.model.HfGoods;
import com.hanfu.product.center.model.HfGoodsExample;
import com.hanfu.product.center.model.HfGoodsPictrue;
import com.hanfu.product.center.request.GoodsPictrueRequest;
import com.hanfu.product.center.request.GoodsSpecRequest;
import com.hanfu.product.center.request.HfGoodsRequest;
import com.hanfu.product.center.response.handler.ResponseEntity;
import com.hanfu.product.center.response.handler.ResponseUtils;
import com.hanfu.product.center.response.handler.ResponseEntity.BodyBuilder;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/goods")
@Api
public class GoodsController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private HfGoodsMapper hfGoodsMapper;
	@Autowired
	private GoodsSpecMapper goodsSpecMapper;
	@Autowired
	private HfGoodsPictrueMapper hfGoodsPictrueMapper;
	
	@Autowired
	private FileDescMapper fileDescMapper;
	
	@ApiOperation(value = "获取商品实体id获取物品列表", notes = "即某商品在店铺内的所有规格")
	@RequestMapping(value = "/byInstanceId", method = RequestMethod.GET)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "instanceId", value = "商品实体id", required = true, type = "Integer") })
	public ResponseEntity<JSONObject> listProduct(@RequestParam(name = "instanceId") Integer instanceId)
			throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		HfGoodsExample example = new HfGoodsExample();
		example.createCriteria().andInstanceIdEqualTo(instanceId);
		return builder.body(ResponseUtils.getResponseBody(hfGoodsMapper.selectByExample(example)));
	}

	@ApiOperation(value = "添加物品", notes = "添加物品")
	@RequestMapping(value = "/addgood", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> addGood(HfGoodsRequest request) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		HfGoods item = new HfGoods();
		item.setInstanceId(request.getInstanceId());
		item.setPriceId(request.getPriceId());
		item.setQuantity(request.getQuantity());
		item.setSpecDesc(request.getGoodDesc());
		return builder.body(ResponseUtils.getResponseBody(hfGoodsMapper.insert(item)));
	}
	
	
	@ApiOperation(value = "获取物品规格", notes = "获取物品规格")
	@RequestMapping(value = "/specifies", method = RequestMethod.GET)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "goodsId", value = "物品id", required = true, type = "Integer") })
	public ResponseEntity<JSONObject> getGoodsSpec(@RequestParam(name = "goodsId") Integer goodsId) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		GoodsSpecExample example = new GoodsSpecExample();
		example.createCriteria().andInstanceIdEqualTo(goodsId);
		return builder.body(ResponseUtils.getResponseBody(goodsSpecMapper.selectByExample(example)));
	}
	
	
	@ApiOperation(value = "添加物品规格", notes = "添加物品规格")
	@RequestMapping(value = "/addSpecify", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> addGoodsSpec(GoodsSpecRequest request) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		GoodsSpec item = new GoodsSpec();
		item.setHfSpec(request.getSpecName());
		item.setInstanceId(request.getGoodsId());
		item.setLastModifier(request.getUsername());
		item.setSpecDesc(request.getSpecDesc());
		item.setSpecValue(request.getSpecValue());
		return builder.body(ResponseUtils.getResponseBody(goodsSpecMapper.insert(item)));
	}

	
	@ApiOperation(value = "获取物品图片", notes = "获取物品图片")
	@RequestMapping(value = "/pictures", method = RequestMethod.GET)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "goodsId", value = "物品id", required = true, type = "Integer") })
	public ResponseEntity<JSONObject> getGoodsPicture(@RequestParam(name = "goodsId") Integer goodsId) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		GoodsSpecExample example = new GoodsSpecExample();
		example.createCriteria().andInstanceIdEqualTo(goodsId);
		return builder.body(ResponseUtils.getResponseBody(goodsSpecMapper.selectByExample(example)));
	}
	
	
	@ApiOperation(value = "添加物品图片", notes = "添加物品图片")
	@RequestMapping(value = "/addPicture", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> addGoodsPicture(GoodsPictrueRequest request) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		HfGoodsPictrue item = new HfGoodsPictrue();
		item.setHfName(request.getPictureName());
		item.setInstanceId(request.getGoodsId());
		item.setLastModifier(request.getUsername());
		item.setSpecDesc(request.getPrictureDesc());
//		上传文件到fastdfs
		FileDesc fileDesc = new FileDesc();
//		fileDesc.setFileName(fileName);
//		fileDesc.setGroupName(groupName);
//		fileDesc.setRemoteFilename(remoteFilename);
		fileDesc.setUserId(request.getUserId());
		int fileId = fileDescMapper.insert(fileDesc);
		item.setFileId(fileId);
		return builder.body(ResponseUtils.getResponseBody(hfGoodsPictrueMapper.insert(item)));
	}

}
