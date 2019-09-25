package com.hanfu.product.center.controller;




import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.hanfu.product.center.dao.FileDescMapper;
import com.hanfu.product.center.dao.GoodsSpecMapper;
import com.hanfu.product.center.dao.HfCategoryMapper;
import com.hanfu.product.center.dao.ProductMapper;
import com.hanfu.product.center.model.FileDesc;
import com.hanfu.product.center.model.GoodsSpec;
import com.hanfu.product.center.model.HfCategory;
import com.hanfu.product.center.model.ProductExample;
import com.hanfu.product.center.request.CategoryRequest;
import com.hanfu.product.center.request.FileRequest;
import com.hanfu.product.center.request.PriceRequest;
import com.hanfu.product.center.response.handler.ResponseUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/product")
@Api(tags = "ProductController")
public class ProductController {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private HfCategoryMapper hfCategoryMapper;
	
	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private GoodsSpecMapper goodsSpecMapper;
	@Autowired
	private FileDescMapper fileDescMapper;
//	@ApiOperation(value = "获取全部品牌列表")
//    @RequestMapping(value = "/product", method = RequestMethod.GET)
//    @ResponseBody
//    public ResponseEntity<JSONObject> getList() throws JSONException {
//        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
//		return builder.body(ResponseUtils.getResponseBody("hello world"));
//    }
//	
	@ApiOperation(value = "获取类目列表")
    @RequestMapping(value = "/category", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<JSONObject> listCategory() throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		return builder.body(ResponseUtils.getResponseBody(hfCategoryMapper.selectByExample(null)));
    }
	
	@ApiOperation(value = "添加类目")
    @RequestMapping(value = "/category", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<JSONObject> AddCategory(@RequestBody CategoryRequest request) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HfCategory category = new HfCategory();
        category.setLevelId(request.getLevelId());
        category.setHfName(request.getCategory());
        category.setParentCategoryId(request.getParentCategoryId());
        return builder.body(ResponseUtils.getResponseBody(hfCategoryMapper.insert(category)));
    }
	
	
	@ApiOperation(value = "获取商品列表")
    @RequestMapping(value = "/byBossId", method = RequestMethod.GET)
    @ResponseBody
    @ApiParam(name = "商家Id", required = true, type="Integer")
    public ResponseEntity<JSONObject> listProduct(@RequestParam(name = "bossId") Integer bossId) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        ProductExample example = new ProductExample();
		example.createCriteria().andBossIdEqualTo(bossId);
		return builder.body(ResponseUtils.getResponseBody(productMapper.selectByExample(example)));
    }
	
//	@ApiOperation(value = "添加商品")
//    @RequestMapping(value = "/byUserId", method = RequestMethod.POST)
//    @ResponseBody
//    @ApiParam(name = "商家Id", required = true, type="Integer")
//    public ResponseEntity<JSONObject> AddProduct(@RequestParam(name = "bossId") Integer bossId) throws JSONException {
//        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
//        ProductExample example = new ProductExample();
//		example.createCriteria().andBossIdEqualTo(bossId);
//		return builder.body(ResponseUtils.getResponseBody(productMapper.selectByExample(example)));
//    }
	@ApiOperation(value = "获取商品实体定价单元规格")
    @RequestMapping(value = "/price", method = RequestMethod.GET)
    @ResponseBody
    @ApiParam(name = "实体定价Id", required = true, type="Integer")
	 public ResponseEntity<JSONObject> listPric(@RequestParam(name = "price") Integer priceId) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		return builder.body(ResponseUtils.getResponseBody(goodsSpecMapper.selectByExample(null)));
    }
	@ApiOperation(value = "添加商品实体定价单元规格")
    @RequestMapping(value = "/price", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<JSONObject> AddPrice(@RequestBody PriceRequest request) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        GoodsSpec goodsSpec = new GoodsSpec();
        goodsSpec.setInstanceId(request.getInstanceId());
        goodsSpec.setHfSpec(request.getHfSpec());
        goodsSpec.setSpecValue(request.getSpecValue());
        goodsSpec.setSpecDesc(request.getSpecDesc());
        return builder.body(ResponseUtils.getResponseBody(goodsSpecMapper.insert(goodsSpec)));
    }
	@ApiOperation(value = "获取文件描述")
    @RequestMapping(value = "/file", method = RequestMethod.GET)
    @ResponseBody
    @ApiParam(name = "文件Id", required = true, type="Integer")
	 public ResponseEntity<JSONObject> listFile(@RequestParam(name = "fileId") Integer priceId) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		return builder.body(ResponseUtils.getResponseBody(fileDescMapper.selectByExample(null)));
    }
	@ApiOperation(value = "添加文件描述")
    @RequestMapping(value = "/file", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<JSONObject> AddFile(@RequestBody FileRequest request) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        FileDesc fileDesc = new FileDesc();
        fileDesc.setFileName(request.getFileName());
        fileDesc.setUserId(request.getUserId());
        fileDesc.setGroupName(request.getGroupName());
        fileDesc.setRemoteFilename(request.getRemoteFileName());
        return builder.body(ResponseUtils.getResponseBody(fileDescMapper.insert(fileDesc)));
    }
	
	
}
