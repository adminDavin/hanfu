//package com.hanfu.dichan.center.controller;
//
//import java.awt.image.BufferedImage;
//import java.io.ByteArrayInputStream;
//import java.io.OutputStream;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.UUID;
//
//import javax.imageio.ImageIO;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.propertyeditors.CustomDateEditor;
//import org.springframework.http.HttpStatus;
//import org.springframework.util.StringUtils;
//import org.springframework.web.bind.WebDataBinder;
//import org.springframework.web.bind.annotation.InitBinder;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.context.request.WebRequest;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.alibaba.fastjson.JSONObject;
//import com.hanfu.common.service.FileMangeService;
//import com.hanfu.dichan.center.dao.DcCategoryMapper;
//import com.hanfu.dichan.center.dao.DcFileDescMapper;
//import com.hanfu.dichan.center.manual.dao.ManualDao;
//import com.hanfu.dichan.center.model.DcCategory;
//import com.hanfu.dichan.center.model.DcCategoryExample;
//import com.hanfu.dichan.center.model.DcFileDesc;
//import com.hanfu.dichan.center.request.CategoryRequest;
//import com.hanfu.utils.response.handler.ResponseEntity;
//import com.hanfu.utils.response.handler.ResponseEntity.BodyBuilder;
//import com.hanfu.utils.response.handler.ResponseUtils;
//
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//
//@RestController
//@RequestMapping("/ratation")
//@Api
//public class DcRatationController {
//
//	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
//
//	@ApiOperation(value = "添加轮播图", notes = "添加轮播图")
//	@RequestMapping(value = "/addRatation", method = RequestMethod.POST)
//	public ResponseEntity<JSONObject> addRatation() throws Exception {
//		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
//
//		return builder.body(ResponseUtils.getResponseBody(dcCategoryMapper.insert(category)));
//	}
//
//}
