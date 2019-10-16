package com.hanfu.product.center.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

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
import com.hanfu.common.service.FileMangeService;
import com.hanfu.common.utils.FdfsClient;
import com.hanfu.product.center.dao.FileDescMapper;
import com.hanfu.product.center.dao.GoodsSpecMapper;
import com.hanfu.product.center.dao.HfGoodsMapper;
import com.hanfu.product.center.dao.HfGoodsPictrueMapper;
import com.hanfu.product.center.dao.HfPriceMapper;
import com.hanfu.product.center.dao.HfRespMapper;
import com.hanfu.product.center.dao.ProductSpecMapper;
import com.hanfu.product.center.manual.model.HfGoodsDisplay;
import com.hanfu.product.center.model.FileDesc;
import com.hanfu.product.center.model.GoodsSpec;
import com.hanfu.product.center.model.GoodsSpecExample;
import com.hanfu.product.center.model.HfGoods;
import com.hanfu.product.center.model.HfGoodsExample;
import com.hanfu.product.center.model.HfGoodsPictrue;
import com.hanfu.product.center.model.HfPrice;
import com.hanfu.product.center.model.HfResp;
import com.hanfu.product.center.model.ProductSpec;
import com.hanfu.product.center.request.GoodsPictrueRequest;
import com.hanfu.product.center.request.GoodsSpecRequest;
import com.hanfu.product.center.request.HfGoodsRequest;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseEntity.BodyBuilder;
import com.hanfu.utils.response.handler.ResponseUtils;

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
	
	@Autowired
	private HfPriceMapper hfPriceMapper;
	
	@Autowired
	private HfRespMapper hfRespMapper;
	
	@ApiOperation(value = "获取商品实体id获取物品列表", notes = "即某商品在店铺内的所有规格")
	@RequestMapping(value = "/byInstanceId", method = RequestMethod.GET)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "instanceId", value = "商品实体id", required = true, type = "Integer") })
	public ResponseEntity<JSONObject> listProduct(@RequestParam Integer instanceId)
			throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		HfGoodsExample example = new HfGoodsExample();
		example.createCriteria().andInstanceIdEqualTo(instanceId);
		return builder.body(ResponseUtils.getResponseBody(hfGoodsMapper.selectByExample(example)));
	}
	
	@ApiOperation(value = "编辑物品", notes = "编辑物品")
	@RequestMapping(value = "/updategood", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> updateGood(HfGoodsDisplay hfGoodsDisplay) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);		
		HfGoods hfGoods = hfGoodsMapper.selectByPrimaryKey(hfGoodsDisplay.getId());
		HfGoodsExample example = new HfGoodsExample();
		example.createCriteria().andBrandIdEqualTo(hfGoodsDisplay.getId());
		HfPrice hfPrice = new HfPrice();
		HfResp hfResp = new HfResp();
		GoodsSpec goodsSpec = new GoodsSpec();
		hfPrice.setSellPrice(hfGoodsDisplay.getSellPrice());
		hfPrice.setGoogsId(hfGoodsDisplay.getId());
		hfPriceMapper.insert(hfPrice);
		hfResp.setQuantity(String.valueOf(hfGoodsDisplay.getQuantity()));
		hfResp.setGoogsId(hfGoodsDisplay.getId());
		hfRespMapper.insert(hfResp);
		goodsSpec.setHfValue(hfGoodsDisplay.getSpecValue());
		goodsSpec.setGoodsId(hfGoodsDisplay.getId());
		goodsSpec.setHfSpecId(String.valueOf(hfGoodsDisplay.getProductSpecId()));
		goodsSpecMapper.insert(goodsSpec);
		hfGoods.setPriceId(hfPrice.getId());
		hfGoods.setGoodsDesc(hfGoodsDisplay.getGoodsDesc());
		hfGoods.setRespId(hfResp.getId());
		
		return builder.body(ResponseUtils.getResponseBody(hfGoodsMapper.updateByExample(hfGoods, example)));
	}
	
	@ApiOperation(value = "获取物品规格", notes = "获取物品规格")
	@RequestMapping(value = "/specifies", method = RequestMethod.GET)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "goodsId", value = "物品id", required = true, type = "Integer") })
	public ResponseEntity<JSONObject> getGoodsSpec(@RequestParam(name = "goodsId") Integer goodsId) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		GoodsSpecExample example = new GoodsSpecExample();
		example.createCriteria().andGoodsIdEqualTo(goodsId);
		return builder.body(ResponseUtils.getResponseBody(goodsSpecMapper.selectByExample(example)));
	}
	
	
	@ApiOperation(value = "添加物品规格", notes = "添加物品规格")
	@RequestMapping(value = "/addSpecify", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> addGoodsSpec(GoodsSpecRequest request) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		GoodsSpec item = new GoodsSpec();
//		item.setHfSpecId(hfSpecId);(request.getSpecName());
		item.setGoodsId(request.getGoodsId());
		item.setLastModifier(request.getUsername());
//		item.set(request.getSpecDesc());
		item.setHfValue(request.getSpecValue());
		return builder.body(ResponseUtils.getResponseBody(goodsSpecMapper.insert(item)));
	}

	
	@ApiOperation(value = "获取物品图片", notes = "获取物品图片")
	@RequestMapping(value = "/pictures", method = RequestMethod.GET)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "goodsId", value = "物品id", required = true, type = "Integer") })
	public ResponseEntity<JSONObject> getGoodsPicture(@RequestParam(name = "goodsId") Integer goodsId) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		GoodsSpecExample example = new GoodsSpecExample();
		example.createCriteria().andGoodsIdEqualTo(goodsId);
		FileDesc fileDesc = fileDescMapper.selectByPrimaryKey(goodsId);
		FileMangeService fileMangeService = new FileMangeService();
		return builder.body(ResponseUtils.getResponseBody(fileMangeService.downloadFile(fileDesc.getGroupName(), fileDesc.getRemoteFilename())));
	}
	
	
	@ApiOperation(value = "添加物品图片", notes = "添加物品图片")
	@RequestMapping(value = "/addPicture", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> addGoodsPicture(GoodsPictrueRequest request) throws JSONException, IOException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
 		HfGoodsPictrue item = new HfGoodsPictrue();
		item.setHfName(request.getPictureName());
		item.setGoodsId(request.getGoodsId());
		item.setLastModifier(request.getUsername());
		item.setSpecDesc(request.getPrictureDesc());
		System.out.println("===============================");
		File file = new  File("C:\\\\Users\\\\123\\\\Desktop\\\\timg.jpg");
		System.out.println("-------------------------------");
		FileInputStream fis = new  FileInputStream(file);
		FileMangeService fileMangeService = new FileMangeService();
		System.out.println("++++++++++++++++++++++++++++++++");
		String arr[] = fileMangeService.uploadFile(FdfsClient.streamToByte(fis), String.valueOf(request.getUserId()));
		fis.close();
		FileDesc fileDesc = new FileDesc();
		fileDesc.setFileName(request.getPictureName());
		fileDesc.setGroupName(arr[0]);
		fileDesc.setRemoteFilename(arr[1]);
		fileDesc.setUserId(request.getUserId());
		int fileId = fileDescMapper.insert(fileDesc);
		item.setFileId(fileId);
		return builder.body(ResponseUtils.getResponseBody(hfGoodsPictrueMapper.insert(item)));
	}

}
