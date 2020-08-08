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
import com.hanfu.product.center.manual.model.WarehouseFindConditional;
import com.hanfu.product.center.manual.model.WarehouseGoodDisplay;
import com.hanfu.product.center.model.HfBoss;
import com.hanfu.product.center.model.HfCategory;
import com.hanfu.product.center.model.HfGoods;
import com.hanfu.product.center.model.HfGoodsExample;
import com.hanfu.product.center.model.HfResp;
import com.hanfu.product.center.model.HfStone;
import com.hanfu.product.center.model.Product;
import com.hanfu.product.center.model.ProductInstance;
import com.hanfu.product.center.model.ProductInstanceExample;
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

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/wareHouse")
@Api
public class WareHouseController {

	@Autowired
	private WarehouseMapper warehouseMapper;
	
	@ApiOperation(value = "查询仓库", notes = "每个商家都有自己的仓库")
	@RequestMapping(value = "/listWareHouse", method = RequestMethod.GET)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "bossId", value = "商家id", required = true, type = "Integer") })
	public ResponseEntity<com.alibaba.fastjson.JSONObject> listWareHouse(@RequestParam Integer bossId,HttpServletRequest request) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		if (request.getServletContext().getAttribute("getServletContext")!=null&&request.getServletContext().getAttribute("getServletContextType")!=null){
			if (request.getServletContext().getAttribute("getServletContextType").equals("boss")||request.getServletContext().getAttribute("getServletContextType").equals("warehouse")) {
				bossId=(Integer) request.getServletContext().getAttribute("getServletContext");
			}
		}
		WarehouseExample example = new WarehouseExample();
		example.createCriteria().andBossidEqualTo(bossId).andIsDeletedEqualTo((short) 0);
		return builder.body(ResponseUtils.getResponseBody(warehouseMapper.selectByExample(example)));
	}
}