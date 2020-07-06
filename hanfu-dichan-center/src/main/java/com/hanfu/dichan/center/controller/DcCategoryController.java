package com.hanfu.dichan.center.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.hanfu.dichan.center.dao.DcCategoryMapper;
import com.hanfu.dichan.center.model.DcCategory;
import com.hanfu.dichan.center.request.CategoryRequest;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseEntity.BodyBuilder;
import com.hanfu.utils.response.handler.ResponseUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/wareHouse")
@Api
public class DcCategoryController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final String REST_URL_PREFIX = "https://www.tjsichuang.cn:1443/api/user/";

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private DcCategoryMapper dcCategoryMapper;

	@ApiOperation(value = "添加类目", notes = "添加系统支持的商品类目")
	@RequestMapping(value = "/addCategory", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> AddCategory(CategoryRequest request,MultipartFile fileInfo,HttpServletRequest requests) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		DcCategory category = new DcCategory();
		String uuid = UUID.randomUUID().toString();
//		uuid = uuid.replace("-", "");
//		if(fileInfo != null) {
//			category.setFileId(updateCategoryPicture(fileInfo,uuid,"无"));
//		}
		Integer bossId=null;
		if (requests.getServletContext().getAttribute("getServletContext")!=null){
			if (requests.getServletContext().getAttribute("getServletContextType").equals("boss")){
				bossId = (Integer) requests.getServletContext().getAttribute("getServletContext");
			}
		}
		category.setBossId(bossId);
		category.setLevelId(request.getLevelId());
		category.setHfName(request.getCategory());
		category.setParentCategoryId(request.getParentCategoryId());
		category.setCreateTime(LocalDateTime.now());
		category.setModifyTime(LocalDateTime.now());
		category.setIsDeleted((short) 0);
		return builder.body(ResponseUtils.getResponseBody(hfCategoryMapper.insert(category)));
	}

	@ApiOperation(value = "获取类目根据条件", notes = "获取类目根据条件")
	@RequestMapping(value = "/getCategoryByInfo", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> getCategoryByInfo() throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		
        return builder.body(ResponseUtils.getResponseBody("123"));
    }	

	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request) {
		// 转换日期
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));// CustomDateEditor为自定义日期编辑器
	}

}
