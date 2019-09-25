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
import com.hanfu.product.center.dao.CategorySpecMapper;
import com.hanfu.product.center.dao.HfCategoryMapper;
import com.hanfu.product.center.dao.HfGoodsMapper;
import com.hanfu.product.center.dao.HfGoodsPictrueMapper;
import com.hanfu.product.center.dao.ProductInfoMapper;
import com.hanfu.product.center.dao.ProductInstanceMapper;
import com.hanfu.product.center.dao.ProductMapper;
import com.hanfu.product.center.dao.ProductSpecMapper;
import com.hanfu.product.center.model.CategorySpec;
import com.hanfu.product.center.model.CategorySpecExample;
import com.hanfu.product.center.model.HfCategory;
import com.hanfu.product.center.model.HfGoods;
import com.hanfu.product.center.model.HfGoodsExample;
import com.hanfu.product.center.model.HfGoodsPictrue;
import com.hanfu.product.center.model.HfGoodsPictrueExample;
import com.hanfu.product.center.model.Product;
import com.hanfu.product.center.model.ProductExample;
import com.hanfu.product.center.model.ProductInfo;
import com.hanfu.product.center.model.ProductInfoExample;
import com.hanfu.product.center.model.ProductInstance;
import com.hanfu.product.center.model.ProductInstanceExample;
import com.hanfu.product.center.model.ProductSpec;
import com.hanfu.product.center.model.ProductSpecExample;
import com.hanfu.product.center.request.CategoryRequest;
import com.hanfu.product.center.request.CategorySpecRequest;
import com.hanfu.product.center.request.HfGoodsPictureRequest;
import com.hanfu.product.center.request.HfGoodsRequest;
import com.hanfu.product.center.request.ProductInfoRequest;
import com.hanfu.product.center.request.ProductInstanceRequest;
import com.hanfu.product.center.request.ProductRequest;
import com.hanfu.product.center.request.ProductSpecRequest;
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
	private CategorySpecMapper categorySpecMapper;
	
	@Autowired
	private ProductMapper productMapper;
	
	@Autowired
	private ProductInfoMapper productInfoMapper;
	
	@Autowired
	private ProductSpecMapper productSpecMapper;
	
	@Autowired
	private ProductInstanceMapper productInstanceMapper;
	
	@Autowired
	private HfGoodsMapper hfGoodsMapper;
	
	@Autowired
	private HfGoodsPictrueMapper hfGoodsPictureMapper;

	@ApiOperation(value = "获取类目列表")
    @RequestMapping(value = "/get_category", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<JSONObject> listCategory() throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		return builder.body(ResponseUtils.getResponseBody(hfCategoryMapper.selectByExample(null)));
    }
	
	@ApiOperation(value = "添加类目")
    @RequestMapping(value = "/add_category", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<JSONObject> AddCategory(@RequestBody CategoryRequest request) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HfCategory category = new HfCategory();
        category.setLevelId(request.getLevelId());
        category.setHfName(request.getCategory());
        category.setParentCategoryId(request.getParentCategoryId());
        return builder.body(ResponseUtils.getResponseBody(hfCategoryMapper.insert(category)));
    }
	
	@ApiOperation(value = "获取类目规格")
	@RequestMapping(value = "/get_categorySpecs", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<JSONObject> listCategorySpecs() throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		return builder.body(ResponseUtils.getResponseBody(categorySpecMapper.selectByExample(null)));
	}
	
	@ApiOperation(value = "添加类目规格")
	@RequestMapping(value = "/add_categorySpecs", method =RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<JSONObject> addCategorySpecs(@RequestBody CategorySpecRequest request) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		CategorySpec categorySpec = new CategorySpec();
		categorySpec.setHfName(request.getSpecsName());
		categorySpec.setClassicType(request.getClassicType());
		categorySpec.setSpecType(request.getSpecsType());
		categorySpec.setSpecUnit(request.getSpecsUnit());
		categorySpec.setSpecValue(request.getSpecsValue());
		return builder.body(ResponseUtils.getResponseBody(categorySpecMapper.insert(categorySpec)));
	}
	
	@ApiOperation(value = "获取商品列表")
    @RequestMapping(value = "/get_product", method = RequestMethod.GET)
    @ResponseBody
    @ApiParam(name = "商家Id", required = true, type="Integer")
    public ResponseEntity<JSONObject> listProduct(@RequestParam(name = "bossId") Integer bossId) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        ProductExample example = new ProductExample();
		example.createCriteria().andBossIdEqualTo(bossId);
		return builder.body(ResponseUtils.getResponseBody(productMapper.selectByExample(example)));
    }
	
	@ApiOperation(value = "添加商品")
	@RequestMapping(value = "/add_product", method = RequestMethod.POST)
	@ResponseBody
	@ApiParam(name = "商家Id", required = true, type="Integer")
	public ResponseEntity<JSONObject> AddProduct(@RequestParam(name = "bossId") Integer bossId
			,@RequestBody ProductRequest request) throws JSONException {
		 BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		 ProductExample example = new ProductExample();
		 example.createCriteria().andBossIdEqualTo(bossId);
		 Product product = new Product();
		 product.setBossId(request.getBossId());
		 product.setProductDesc(request.getProductDesc());
		 product.setCategoryId(request.getCategoryId());
		 product.setHfName(request.getProdutName());
		 return builder.body(ResponseUtils.getResponseBody(productMapper.insert(product)));
	}
	
	@ApiOperation(value = "获取商品属性")
	@RequestMapping(value = "/get_productValue",method = RequestMethod.GET)
	@ResponseBody
	@ApiParam(name = "产品Id", required = true, type="Integer")
	public ResponseEntity<JSONObject> listProductDesc(
			@RequestParam(name = "productId") Integer productId) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		ProductInfoExample example = new ProductInfoExample();
		example.createCriteria().andProductIdEqualTo(productId);
		return builder.body(ResponseUtils.getResponseBody(productInfoMapper.selectByExample(example)));
	}
	@ApiOperation(value = "添加商品属性")
	@RequestMapping(value = "/add_productValue",method = RequestMethod.POST)
	@ResponseBody
	@ApiParam(name = "产品Id", required = true, type="Integer")
	public ResponseEntity<JSONObject> addProductDesc(
			@RequestParam(name = "productId") Integer productId,
			@RequestBody ProductInfoRequest request) throws JSONException{
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		ProductInfoExample example = new ProductInfoExample();
		example.createCriteria().andProductIdEqualTo(productId);
		ProductInfo productInfo = new ProductInfo();
		productInfo.setProductId(request.getProductId());
		productInfo.setHfName(request.getPropertyName());
		productInfo.setHfValue(request.getPropertyValue());
		productInfo.setValueUnit(request.getPropertyUnit());
		return builder.body(ResponseUtils.getResponseBody(productInfoMapper.insert(productInfo)));
	}
	@ApiOperation(value = "获取商品规格")
	@RequestMapping(value = "/get_prductSpecs", method = RequestMethod.GET)
	@ResponseBody
	@ApiParam(name ="产品Id", required = true, type = "Integer")
	public ResponseEntity<JSONObject> listProductSpecs(
			@RequestParam(name = "productId") Integer productId) throws JSONException{
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		ProductSpecExample example = new ProductSpecExample();
		example.createCriteria().andProductIdEqualTo(productId);		
		return builder.body(ResponseUtils.getResponseBody(productSpecMapper.selectByExample(example)));
	}
	@ApiOperation(value = "添加商品规格")
	@RequestMapping(value = "/add_productSpecs", method = RequestMethod.POST)
	@ResponseBody
	@ApiParam(name = "产品Id", required = true, type="Integer")
	public ResponseEntity<JSONObject> addProductSpecs(
			@RequestParam(name = "productId") Integer productId,
			@RequestBody ProductSpecRequest request) throws JSONException{
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		ProductSpecExample example = new ProductSpecExample();
		example.createCriteria().andProductIdEqualTo(productId);
		ProductSpec productSpec = new ProductSpec();
		productSpec.setHfName(request.getSpecsName());
		productSpec.setProductId(request.getProductId());
		productSpec.setSpecType(request.getSpecsType());
		productSpec.setSpecUnit(request.getSpecsUnit());
		productSpec.setSpecValue(request.getSpecsValue());
		return builder.body(ResponseUtils.getResponseBody(productSpecMapper.insert(productSpec)));
	}
	@ApiOperation(value = "获取商品实体")
	@RequestMapping(value = "/get_prductEntity", method = RequestMethod.GET)
	@ResponseBody
	@ApiParam(name ="商品Id", required = true, type = "Integer")
	public ResponseEntity<JSONObject> listProductEntity(
			@RequestParam(name = "productId") Integer productId) throws JSONException{
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		ProductInstanceExample example = new ProductInstanceExample();
		example.createCriteria().andProductIdEqualTo(productId);		
		return builder.body(ResponseUtils.getResponseBody(productInstanceMapper.selectByExample(example)));
	}
	@ApiOperation(value = "添加商品实体")
	@RequestMapping(value = "/add_productEntity", method = RequestMethod.POST)
	@ResponseBody
	@ApiParam(name = "产品Id", required = true, type="Integer")
	public ResponseEntity<JSONObject> addProductEntity(
			@RequestParam(name = "productId") Integer productId,
			@RequestBody ProductInstanceRequest request) throws JSONException{
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		ProductInstanceExample example = new ProductInstanceExample();
		example.createCriteria().andProductIdEqualTo(productId);
		ProductInstance productInstance = new ProductInstance();
		productInstance.setProductId(request.getProductId());
		productInstance.setStoneId(request.getStoreId());
		return builder.body(ResponseUtils.getResponseBody(productInstanceMapper.insert(productInstance)));
	}
	@ApiOperation(value = "获取商品实体定价单元")
	@RequestMapping(value = "/get_prductEntityUnit", method = RequestMethod.GET)
	@ResponseBody
	@ApiParam(name ="商品实体Id", required = true, type = "Integer")
	public ResponseEntity<JSONObject> listProductEntityUnit(
			@RequestParam(name = "productEntityId") Integer productEntityId) throws JSONException{
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		HfGoodsExample example = new HfGoodsExample();
		example.createCriteria().andInstanceIdEqualTo(productEntityId);
		return builder.body(ResponseUtils.getResponseBody(hfGoodsMapper.selectByExample(example)));
	}
	@ApiOperation(value = "添加商品实体定价单元")
	@RequestMapping(value = "/add_productEntityUnit", method = RequestMethod.POST)
	@ResponseBody
	@ApiParam(name = "商品实体Id", required = true, type="Integer")
	public ResponseEntity<JSONObject> addProductEntityUnit(
			@RequestParam(name = "productEntityId") Integer productEntityId,
			@RequestBody HfGoodsRequest request) throws JSONException{
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		HfGoodsExample example = new HfGoodsExample();
		example.createCriteria().andInstanceIdEqualTo(productEntityId);
		HfGoods hfGoods = new HfGoods();
		hfGoods.setInstanceId(request.getProductEntityId());
		hfGoods.setPriceId(request.getPriceId());
		hfGoods.setQuantity(request.getProductStock());
		hfGoods.setSpecDesc(request.getRemark());
		return builder.body(ResponseUtils.getResponseBody(hfGoodsMapper.insert(hfGoods)));
	}
	@ApiOperation(value = "获取商品实体定价单图片描述")
	@RequestMapping(value = "/get_prductEntityUnitP", method = RequestMethod.GET)
	@ResponseBody
	@ApiParam(name ="商品实体定价单元Id", required = true, type = "Integer")
	public ResponseEntity<JSONObject> listProductEntityUnitP(
			@RequestParam(name = "productEntityUnitId") Integer productEntityUnitId) throws JSONException{
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		HfGoodsPictrueExample example = new HfGoodsPictrueExample();
		example.createCriteria().andInstanceIdEqualTo(productEntityUnitId);
		return builder.body(ResponseUtils.getResponseBody(hfGoodsPictureMapper.selectByExample(example)));
	}
	@ApiOperation(value = "添加商品实体定价单图片描述")
	@RequestMapping(value = "/add_productEntityUnitP", method = RequestMethod.POST)
	@ResponseBody
	@ApiParam(name = "商品实体定价单元Id", required = true, type="Integer")
	public ResponseEntity<JSONObject> addProductEntityUnitP(
			@RequestParam(name = "productEntityUnitId") Integer productEntityUnitId,
			@RequestBody HfGoodsPictureRequest request) throws JSONException{
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		HfGoodsPictrueExample example = new HfGoodsPictrueExample();
		example.createCriteria().andInstanceIdEqualTo(productEntityUnitId);
		HfGoodsPictrue hfGoodsPictrue = new HfGoodsPictrue();
		hfGoodsPictrue.setInstanceId(request.getProductUnitId());
		hfGoodsPictrue.setHfName(request.getPictureName());
		hfGoodsPictrue.setSpecDesc(request.getPictureshow());
		hfGoodsPictrue.setFileId(request.getFileId());
		return builder.body(ResponseUtils.getResponseBody(hfGoodsPictureMapper.insert(hfGoodsPictrue)));
	}
}
