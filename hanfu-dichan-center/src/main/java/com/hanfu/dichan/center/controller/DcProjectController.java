package com.hanfu.dichan.center.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
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
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hanfu.common.service.FileMangeService;
import com.hanfu.dichan.center.dao.DcCategoryMapper;
import com.hanfu.dichan.center.dao.DcFileDescMapper;
import com.hanfu.dichan.center.dao.DcProjectMapper;
import com.hanfu.dichan.center.dao.DcRatationMapper;
import com.hanfu.dichan.center.manual.dao.ManualDao;
import com.hanfu.dichan.center.manual.model.RatationType.RatationTypeEnum;
import com.hanfu.dichan.center.model.DcCategory;
import com.hanfu.dichan.center.model.DcCategoryExample;
import com.hanfu.dichan.center.model.DcFileDesc;
import com.hanfu.dichan.center.model.DcProject;
import com.hanfu.dichan.center.model.DcProjectExample;
import com.hanfu.dichan.center.model.DcRatation;
import com.hanfu.dichan.center.model.DcRatationExample;
import com.hanfu.dichan.center.request.CategoryRequest;
import com.hanfu.dichan.center.request.RatationRequest;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseEntity.BodyBuilder;
import com.hanfu.utils.response.handler.ResponseUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/project")
@Api
public class DcProjectController {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private DcProjectMapper dcProjectMapper;
	
	@Autowired
	private DcFileDescMapper dcFileDescMapper;

	@ApiOperation(value = "添加项目", notes = "添加项目")
	@RequestMapping(value = "/addProject", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> addRatation(String projectName, String projectDesc ,MultipartFile file) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		Integer bossId = 1;
		DcProject project = new DcProject();
		project.setProjectName(projectName);
		project.setProjectDesc(projectDesc);
		project.setBossId(bossId);
		project.setCreateTime(LocalDateTime.now());
		project.setModifyTime(LocalDateTime.now());
		project.setIsDeleted((byte) 0);
		if(file != null) {
			FileMangeService fileMangeService = new FileMangeService();
			String arr[];
			arr = fileMangeService.uploadFile(file.getBytes(), String.valueOf("1"));
			DcFileDesc fileDesc = new DcFileDesc();
			fileDesc.setFileName(file.getName());
			fileDesc.setGroupName(arr[0]);
			fileDesc.setRemoteFilename(arr[1]);
			fileDesc.setCreateTime(LocalDateTime.now());
			fileDesc.setModifyTime(LocalDateTime.now());
			fileDesc.setIsDeleted((short) 0);
			dcFileDescMapper.insert(fileDesc);
			project.setFileId(fileDesc.getId());
		}
		dcProjectMapper.insert(project);
		return builder.body(ResponseUtils.getResponseBody(project.getId()));
	}

	@ApiOperation(value = "删除项目", notes = "删除项目")
	@RequestMapping(value = "/deleteProject", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> deleteProject(Integer id) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		dcProjectMapper.deleteByPrimaryKey(id);
		return builder.body(ResponseUtils.getResponseBody(id));
	}

	@ApiOperation(value = "编辑项目", notes = "编辑项目")
	@RequestMapping(value = "/updateProject", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> updateProject(Integer id, String name, MultipartFile file) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		DcProject project = dcProjectMapper.selectByPrimaryKey(id);
		if (file != null) {
			FileMangeService fileMangeService = new FileMangeService();
			String arr[];
			arr = fileMangeService.uploadFile(file.getBytes(), String.valueOf("1"));
			if (project.getFileId() == null) {
				DcFileDesc fileDesc = new DcFileDesc();
				fileDesc.setFileName(file.getName());
				fileDesc.setGroupName(arr[0]);
				fileDesc.setRemoteFilename(arr[1]);
				fileDesc.setCreateTime(LocalDateTime.now());
				fileDesc.setModifyTime(LocalDateTime.now());
				fileDesc.setIsDeleted((short) 0);
				dcFileDescMapper.insert(fileDesc);
				project.setFileId(fileDesc.getId());
			} else {
				DcFileDesc desc = dcFileDescMapper.selectByPrimaryKey(project.getFileId());
				fileMangeService.deleteFile(desc.getGroupName(), desc.getRemoteFilename());
				desc.setGroupName(arr[0]);
				desc.setRemoteFilename(arr[1]);
				desc.setModifyTime(LocalDateTime.now());
				dcFileDescMapper.updateByPrimaryKey(desc);
			}

		}
		if (!StringUtils.isEmpty(name)) {
			project.setProjectName(name);
		}
		project.setModifyTime(LocalDateTime.now());
		dcProjectMapper.updateByPrimaryKey(project);
		return builder.body(ResponseUtils.getResponseBody(project.getId()));
	}

	@ApiOperation(value = "查询项目", notes = "查询项目")
	@RequestMapping(value = "/getProject", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> getRatation(Integer id) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		Integer bossId = 1;
		if (id == null) {
			DcProjectExample example = new DcProjectExample();
			example.createCriteria().andBossIdEqualTo(bossId);
			List<DcProject> list = dcProjectMapper.selectByExample(example);
			return builder.body(ResponseUtils.getResponseBody(list));
		} else {
			DcProject project = dcProjectMapper.selectByPrimaryKey(id);
			return builder.body(ResponseUtils.getResponseBody(project));
		}
	}

}
