package com.hanfu.product.center.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.hanfu.common.service.FileMangeService;
import com.hanfu.product.center.dao.DistributionProductMapper;
import com.hanfu.product.center.dao.FileDescMapper;
import com.hanfu.product.center.dao.HfGoodsPictrueMapper;
import com.hanfu.product.center.dao.HfPriceMapper;
import com.hanfu.product.center.dao.HfSeniorityMapper;
import com.hanfu.product.center.dao.ProductMapper;
import com.hanfu.product.center.dao.SeniorityInstanceMapper;
import com.hanfu.product.center.manual.dao.HfGoodsDao;
import com.hanfu.product.center.manual.model.HfGoodsDisplay;
import com.hanfu.product.center.model.DistributionProduct;
import com.hanfu.product.center.model.FileDesc;
import com.hanfu.product.center.model.FileDescExample;
import com.hanfu.product.center.model.HfGoodsPictrue;
import com.hanfu.product.center.model.HfGoodsPictrueExample;
import com.hanfu.product.center.model.HfPrice;
import com.hanfu.product.center.model.HfSeniority;
import com.hanfu.product.center.model.Product;
import com.hanfu.product.center.model.ProductExample;
import com.hanfu.product.center.model.SeniorityInstance;
import com.hanfu.product.center.model.SeniorityInstanceExample;
import com.hanfu.product.center.request.HfSeniorityRequest;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseUtils;
import com.hanfu.utils.response.handler.ResponseEntity.BodyBuilder;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
@RequestMapping("/distribution")
@Api
public class DistributionController {
	
	@Autowired
	ProductMapper productMapper;
	
	@Autowired
	DistributionProductMapper distributionProductMapper;

    @ApiOperation(value = "给某商品配置分销信息", notes = "某商品配置分销信息")
    @RequestMapping(value = "/addDistributionInfo", method = RequestMethod.POST)
    @ApiImplicitParams({
      @ApiImplicitParam(paramType = "query", name = "productId", value = "商品id", required = true, type = "Integer"),
      @ApiImplicitParam(paramType = "query", name = "classAprofit", value = "一级利润", required = false, type = "double"),
      @ApiImplicitParam(paramType = "query", name = "classBprofit", value = "二级利润", required = false, type = "double"),
})
    public ResponseEntity<JSONObject> addDistributionInfo(Integer productId,double classAprofit,double classBprofit) throws Exception {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        Product product = productMapper.selectByPrimaryKey(productId);
        if(product == null) {
        	return builder.body(ResponseUtils.getResponseBody("此商品不存在"));
        }
        DistributionProduct distributionProduct = new DistributionProduct();
        distributionProduct.setProductId(productId);
        distributionProduct.setClassaProfit(classAprofit);
        distributionProduct.setClassbProfit(classBprofit);
        distributionProduct.setCreateTime(LocalDateTime.now());
        distributionProduct.setModifyTime(LocalDateTime.now());
        distributionProduct.setIsDeleted((byte) 0) ;
        return builder.body(ResponseUtils.getResponseBody(distributionProductMapper.insert(distributionProduct)));
    }

}
