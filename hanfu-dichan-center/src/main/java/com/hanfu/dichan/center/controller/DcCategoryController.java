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
import com.hanfu.common.service.FileMangeService;
import com.hanfu.dichan.center.dao.DcCategoryMapper;
import com.hanfu.dichan.center.dao.DcFileDescMapper;
import com.hanfu.dichan.center.manual.dao.ManualDao;
import com.hanfu.dichan.center.model.DcCategory;
import com.hanfu.dichan.center.model.DcCategoryExample;
import com.hanfu.dichan.center.model.DcFileDesc;
import com.hanfu.dichan.center.request.CategoryRequest;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseEntity.BodyBuilder;
import com.hanfu.utils.response.handler.ResponseUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/category")
@Api
public class DcCategoryController {
	private static final String LOCK = "LOCK";

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final String REST_URL_PREFIX = "https://www.tjsichuang.cn:1443/api/user/";

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private DcCategoryMapper dcCategoryMapper;

	@Autowired
	private DcFileDescMapper dcFileDescMapper;

	@Autowired
	private ManualDao manualDao;

	@ApiOperation(value = "添加类目", notes = "添加类目")
	@RequestMapping(value = "/addCategory", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> AddCategory(CategoryRequest request, HttpServletRequest requests)
			throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		DcCategory category = new DcCategory();
		String uuid = UUID.randomUUID().toString();
		Integer bossId = 1;
//		if (requests.getServletContext().getAttribute("getServletContext")!=null){
//			if (requests.getServletContext().getAttribute("getServletContextType").equals("boss")){
//				bossId = (Integer) requests.getServletContext().getAttribute("getServletContext");
//			}
//		}
		category.setBossId(bossId);
		category.setProjectId(request.getProjectId());
		category.setLevelId(request.getLevelId());
		category.setHfName(request.getCategory());
		category.setParentCategoryId(request.getParentCategoryId());
		category.setCreateTime(LocalDateTime.now());
		category.setModifyTime(LocalDateTime.now());
		category.setIsDeleted((short) 0);
		return builder.body(ResponseUtils.getResponseBody(dcCategoryMapper.insert(category)));
	}

	@ApiOperation(value = "删除类目", notes = "删除类目")
	@RequestMapping(value = "/deleteCategory", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> deleteCategory(Integer categoryId) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		FileMangeService fileMangeService = new FileMangeService();
		DcCategory hfCategory = dcCategoryMapper.selectByPrimaryKey(categoryId);
		if (hfCategory.getFileId() != null) {
			DcFileDesc fileDesc = dcFileDescMapper.selectByPrimaryKey(hfCategory.getFileId());
			fileMangeService.deleteFile(fileDesc.getGroupName(), fileDesc.getRemoteFilename());
			dcFileDescMapper.deleteByPrimaryKey(hfCategory.getFileId());
		}
		return builder.body(ResponseUtils.getResponseBody(dcFileDescMapper.deleteByPrimaryKey(categoryId)));
	}

	@ApiOperation(value = "编辑类目", notes = "编辑类目")
	@RequestMapping(value = "/updateCategory", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> updateCategory(CategoryRequest request, @RequestParam Integer catrgoryId,
			MultipartFile fileInfo) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);

		DcCategory hfCategory = dcCategoryMapper.selectByPrimaryKey(catrgoryId);
		if (fileInfo != null) {
			FileMangeService fileMangeService = new FileMangeService();
			String arr[];
			arr = fileMangeService.uploadFile(fileInfo.getBytes(), String.valueOf(request.getUserId()));
			if (hfCategory.getFileId() == null) {
				DcFileDesc fileDesc = new DcFileDesc();
				fileDesc.setFileName(fileInfo.getName());
				fileDesc.setGroupName(arr[0]);
				fileDesc.setRemoteFilename(arr[1]);
				fileDesc.setCreateTime(LocalDateTime.now());
				fileDesc.setModifyTime(LocalDateTime.now());
				fileDesc.setIsDeleted((short) 0);
				dcFileDescMapper.insert(fileDesc);
				hfCategory.setFileId(fileDesc.getId());
			} else {
				DcFileDesc desc = dcFileDescMapper.selectByPrimaryKey(hfCategory.getFileId());
				fileMangeService.deleteFile(desc.getGroupName(), desc.getRemoteFilename());
				desc.setGroupName(arr[0]);
				desc.setRemoteFilename(arr[1]);
				desc.setModifyTime(LocalDateTime.now());
				dcFileDescMapper.updateByPrimaryKey(desc);
			}

		}
		if (!StringUtils.isEmpty(request.getLevelId())) {
			hfCategory.setLevelId(request.getLevelId());
		}
		if (!StringUtils.isEmpty(request.getCategory())) {
			hfCategory.setHfName(request.getCategory());
		}
		if (!StringUtils.isEmpty(request.getParentCategoryId())) {
			hfCategory.setParentCategoryId(request.getParentCategoryId());
		}
		hfCategory.setModifyTime(LocalDateTime.now());
		return builder.body(ResponseUtils.getResponseBody(dcCategoryMapper.updateByPrimaryKey(hfCategory)));
	}

	@ApiOperation(value = "获取类目根据条件", notes = "获取类目根据条件")
	@RequestMapping(value = "/getCategoryByInfo", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> getCategoryByInfo(Integer parentCategoryId, Integer projectId) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		Integer bossId = 1;
		DcCategoryExample categoryExample = new DcCategoryExample();
		categoryExample.createCriteria().andBossIdEqualTo(bossId).andParentCategoryIdEqualTo(parentCategoryId)
				.andIsDeletedEqualTo((short) 0);
		if(parentCategoryId == -1) {
			categoryExample.createCriteria().andProjectIdEqualTo(projectId);
		}
		List<DcCategory> list = dcCategoryMapper.selectByExample(categoryExample);
		return builder.body(ResponseUtils.getResponseBody(list));
	}
	
	@ApiOperation(value = "根据名字等级获取类目", notes = "根据名字等级获取类目")
	@RequestMapping(value = "/getCategoryByNameOrLevel", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> getCategoryByNameOrLevel(HttpServletRequest request,Integer level, String name, Integer projectId) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		Integer bossId =1;
//		if (request.getServletContext().getAttribute("getServletContextType").equals("boss")){
//			System.out.println("request.getServletContext().getAttribute得到全局数据："+request.getServletContext().getAttribute("getServletContext"));
//			if (request.getServletContext().getAttribute("getServletContext")!=null){
//				bossId=(Integer) request.getServletContext().getAttribute("getServletContext");
//			}
//		}
		DcCategory h = new DcCategory();
		h.setLevelId(level);
		h.setHfName(name);
		h.setBossId(bossId);
		h.setProjectId(projectId);
		List<DcCategory> list = manualDao.findCategoryByInfo(h);
        return builder.body(ResponseUtils.getResponseBody(list));
    }

	@ApiOperation(value = "获取图片", notes = "获取图片")
	@RequestMapping(value = "/getPicture", method = RequestMethod.GET)
	public void getPicture(Integer id, HttpServletResponse response) throws Exception {
		response.addHeader("Access-Control-Allow-Origin", "*");
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		Integer bossId = 1;
		DcFileDesc fileDesc = dcFileDescMapper.selectByPrimaryKey(id);
		if (fileDesc == null) {
			throw new Exception("file not exists");
		}
		FileMangeService fileManageService = new FileMangeService();
		synchronized (LOCK) {
			byte[] file = fileManageService.downloadFile(fileDesc.getGroupName(), fileDesc.getRemoteFilename());
			ByteArrayInputStream stream = new ByteArrayInputStream(file);
			BufferedImage readImg = ImageIO.read(stream);
			stream.reset();
			OutputStream outputStream = response.getOutputStream();
			ImageIO.write(readImg, "png", outputStream);
			outputStream.close();
		}
	}

	@ApiOperation(value = "删除图片", notes = "删除图片")
	@RequestMapping(value = "/deletePicture", method = RequestMethod.GET)
	public Integer deletePicture(Integer id) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		DcCategory category = dcCategoryMapper.selectByPrimaryKey(id);
		DcFileDesc fileDesc = dcFileDescMapper.selectByPrimaryKey(category.getFileId());
		FileMangeService fileManageService = new FileMangeService();
		fileManageService.deleteFile(fileDesc.getGroupName(), fileDesc.getRemoteFilename());
		//
		return fileDesc.getId();
	}

	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request) {
		// 转换日期
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));// CustomDateEditor为自定义日期编辑器
	}

}
