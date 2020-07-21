package com.hanfu.dichan.center.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.csource.common.NameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.hanfu.common.service.FileMangeService;
import com.hanfu.dichan.center.dao.DcCategoryMapper;
import com.hanfu.dichan.center.dao.DcFileDescMapper;
import com.hanfu.dichan.center.dao.DcRatationMapper;
import com.hanfu.dichan.center.manual.dao.ManualDao;
import com.hanfu.dichan.center.manual.model.RatationType.RatationTypeEnum;
import com.hanfu.dichan.center.model.DcCategory;
import com.hanfu.dichan.center.model.DcCategoryExample;
import com.hanfu.dichan.center.model.DcFileDesc;
import com.hanfu.dichan.center.model.DcRatation;
import com.hanfu.dichan.center.model.DcRatationExample;
import com.hanfu.dichan.center.request.CategoryRequest;
import com.hanfu.dichan.center.request.RatationRequest;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseEntity.BodyBuilder;
import com.hanfu.utils.response.handler.ResponseUtils;
import com.sun.mail.handlers.image_gif;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.coobird.thumbnailator.Thumbnails;

@RestController
@RequestMapping("/ratation")
@Api
public class DcRatationController {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private DcRatationMapper dcRatationMapper;

	@Autowired
	private DcFileDescMapper dcFileDescMapper;

	@ApiOperation(value = "添加轮播图", notes = "添加轮播图")
	@RequestMapping(value = "/addRatation", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> addRatation(RatationRequest request, MultipartFile file) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		Integer bossId = 1;
		DcRatation ratation = new DcRatation();
		if (file != null) {
			DcFileDesc fileDesc = new DcFileDesc();
			FileMangeService fileMangeService = new FileMangeService();
			ByteArrayOutputStream os = new ByteArrayOutputStream();
		    Thumbnails.of(file.getInputStream()).scale(0.8f).outputFormat("jpg").outputQuality(0.6).toOutputStream(os);
			String arr[];
			arr = fileMangeService.uploadFile(os.toByteArray(), String.valueOf(request.getUserId()));
			fileDesc = new DcFileDesc();
			fileDesc.setGroupName(arr[0]);
			fileDesc.setRemoteFilename(arr[1]);
			fileDesc.setModifyTime(LocalDateTime.now());
			fileDesc.setCreateTime(LocalDateTime.now());
			fileDesc.setFileName(file.getOriginalFilename());
			fileDesc.setIsDeleted((short) 0);
			dcFileDescMapper.insert(fileDesc);
			ratation.setFileId(fileDesc.getId());
		}
		ratation.setBossId(bossId);
		ratation.setProjectId(request.getProjectId());
		ratation.setType(request.getType());
		ratation.setCreateTime(LocalDateTime.now());
		ratation.setModifyTime(LocalDateTime.now());
		ratation.setIsDeleted((byte) 0);
		dcRatationMapper.insert(ratation);
		return builder.body(ResponseUtils.getResponseBody(ratation.getId()));

	}

	@ApiOperation(value = "删除轮播图", notes = "删除轮播图")
	@RequestMapping(value = "/deleteRatation", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> deleteRatation(Integer id) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		DcRatation ratation = dcRatationMapper.selectByPrimaryKey(id);
		DcFileDesc fileDesc = dcFileDescMapper.selectByPrimaryKey(ratation.getFileId());
		dcRatationMapper.deleteByPrimaryKey(id);
		FileMangeService fileMangeService = new FileMangeService();
		fileMangeService.deleteFile(fileDesc.getGroupName(), fileDesc.getRemoteFilename());
		return builder.body(ResponseUtils.getResponseBody(id));
	}

	@ApiOperation(value = "编辑轮播图", notes = "修改轮播图")
	@RequestMapping(value = "/updateRatation", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> updateRatation(Integer id, MultipartFile file) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		DcRatation ratation = dcRatationMapper.selectByPrimaryKey(id);
		DcFileDesc fileDesc = dcFileDescMapper.selectByPrimaryKey(ratation.getFileId());
		FileMangeService fileMangeService = new FileMangeService();
		String arr[];
		ByteArrayOutputStream os = new ByteArrayOutputStream();
	    Thumbnails.of(file.getInputStream()).scale(0.8f).outputFormat("jpg").outputQuality(0.6).toOutputStream(os);
		arr = fileMangeService.uploadFile(os.toByteArray(), String.valueOf(id));
		if (fileDesc != null) {
			fileMangeService.deleteFile(fileDesc.getGroupName(), fileDesc.getRemoteFilename());
			fileDesc.setGroupName(arr[0]);
			fileDesc.setRemoteFilename(arr[1]);
			fileDesc.setModifyTime(LocalDateTime.now());
			dcFileDescMapper.updateByPrimaryKey(fileDesc);
		} else {
			fileDesc = new DcFileDesc();
			fileDesc.setGroupName(arr[0]);
			fileDesc.setRemoteFilename(arr[1]);
			fileDesc.setModifyTime(LocalDateTime.now());
			fileDesc.setCreateTime(LocalDateTime.now());
			fileDesc.setFileName(file.getOriginalFilename());
			fileDesc.setIsDeleted((short) 0);
			dcFileDescMapper.insert(fileDesc);
		}
		ratation.setFileId(fileDesc.getId());
		dcRatationMapper.updateByPrimaryKey(ratation);
		return builder.body(ResponseUtils.getResponseBody(fileDesc.getId()));
	}

	@ApiOperation(value = "查询轮播图", notes = "查询轮播图")
	@RequestMapping(value = "/getRatation", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> getRatation(Integer projectId, String type) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		Integer bossId = 1;
		if (RatationTypeEnum.OUR.getRatationType().equals(type)) {
			DcRatationExample example = new DcRatationExample();
			example.createCriteria().andBossIdEqualTo(bossId).andTypeEqualTo(type);
			List<DcRatation> list = dcRatationMapper.selectByExample(example);
			return builder.body(ResponseUtils.getResponseBody(list));
		}
		if (RatationTypeEnum.HOME_PAGE.getRatationType().equals(type)) {
			DcRatationExample example = new DcRatationExample();
			example.createCriteria().andBossIdEqualTo(bossId).andTypeEqualTo(type).andProjectIdEqualTo(projectId);
			List<DcRatation> list = dcRatationMapper.selectByExample(example);
			return builder.body(ResponseUtils.getResponseBody(list));
		}
		return builder.body(ResponseUtils.getResponseBody(null));
	}
	
}
