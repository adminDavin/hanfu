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
import com.hanfu.product.center.dao.HfCategoryMapper;
import com.hanfu.product.center.dao.ProductMapper;
import com.hanfu.product.center.model.CategorySpecExample;
import com.hanfu.product.center.model.HfCategory;
import com.hanfu.product.center.model.ProductExample;
import com.hanfu.product.center.request.CategoryRequest;
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
	
	
	
	
	
}
