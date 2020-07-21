package com.hanfu.dichan.center.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
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
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.hanfu.common.service.FileMangeService;
import com.hanfu.dichan.center.dao.DcCategoryDetailMapper;
import com.hanfu.dichan.center.dao.DcCategoryMapper;
import com.hanfu.dichan.center.dao.DcEvaluateMapper;
import com.hanfu.dichan.center.dao.DcFileDescMapper;
import com.hanfu.dichan.center.dao.DcGeneralFileMapper;
import com.hanfu.dichan.center.dao.DcPraiseRecordMapper;
import com.hanfu.dichan.center.dao.DcUserMapper;
import com.hanfu.dichan.center.manual.dao.ManualDao;
import com.hanfu.dichan.center.manual.model.Catrgory;
import com.hanfu.dichan.center.manual.model.Comment;
import com.hanfu.dichan.center.model.DcCategory;
import com.hanfu.dichan.center.model.DcCategoryDetail;
import com.hanfu.dichan.center.model.DcCategoryDetailExample;
import com.hanfu.dichan.center.model.DcCategoryExample;
import com.hanfu.dichan.center.model.DcEvaluate;
import com.hanfu.dichan.center.model.DcEvaluateExample;
import com.hanfu.dichan.center.model.DcFileDesc;
import com.hanfu.dichan.center.model.DcFileDescExample;
import com.hanfu.dichan.center.model.DcGeneralFile;
import com.hanfu.dichan.center.model.DcGeneralFileExample;
import com.hanfu.dichan.center.model.DcPraiseRecord;
import com.hanfu.dichan.center.model.DcPraiseRecordExample;
import com.hanfu.dichan.center.model.DcUser;
import com.hanfu.dichan.center.request.CategoryRequest;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseEntity.BodyBuilder;
import com.hanfu.utils.response.handler.ResponseUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.coobird.thumbnailator.Thumbnails;

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

	@Autowired
	private DcGeneralFileMapper dcGeneralFileMapper;
	
	@Autowired
	private DcCategoryDetailMapper dcCategoryDetailMapper;
	
	@Autowired
	private DcPraiseRecordMapper dcPraiseRecordMapper;
	
	@Autowired
	private DcEvaluateMapper dcEvaluateMapper;
	
	@Autowired
	private DcUserMapper dcUserMapper;

	@ApiOperation(value = "添加类目", notes = "添加类目")
	@RequestMapping(value = "/addCategory", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> AddCategory(CategoryRequest request, HttpServletRequest requests,
			MultipartFile file) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		DcCategory category = new DcCategory();
		Integer bossId = 1;
		category.setBossId(bossId);
		category.setProjectId(request.getProjectId());
		category.setLevelId(request.getLevelId());
		category.setHfName(request.getCategory());
		category.setParentCategoryId(request.getParentCategoryId());
		category.setCreateTime(LocalDateTime.now());
		category.setModifyTime(LocalDateTime.now());
		category.setIsDeleted((short) 0);
		if(file != null) {
			FileMangeService fileMangeService = new FileMangeService();
			String arr[];
			ByteArrayOutputStream os = new ByteArrayOutputStream();
		    Thumbnails.of(file.getInputStream()).scale(0.8f).outputFormat("jpg").outputQuality(0.6).toOutputStream(os);
			arr = fileMangeService.uploadFile(os.toByteArray(), String.valueOf(request.getUserId()));
			DcFileDesc fileDesc = new DcFileDesc();
			fileDesc.setFileName(file.getName());
			fileDesc.setGroupName(arr[0]);
			fileDesc.setRemoteFilename(arr[1]);
			fileDesc.setCreateTime(LocalDateTime.now());
			fileDesc.setModifyTime(LocalDateTime.now());
			fileDesc.setIsDeleted((short) 0);
			dcFileDescMapper.insert(fileDesc);
			category.setFileId(fileDesc.getId());
		}
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
		dcCategoryMapper.deleteByPrimaryKey(categoryId);
		return builder.body(ResponseUtils.getResponseBody(dcFileDescMapper.deleteByPrimaryKey(categoryId)));
	}

	@ApiOperation(value = "编辑类目", notes = "编辑类目")
	@RequestMapping(value = "/updateCategory", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> updateCategory(CategoryRequest request, @RequestParam Integer categoryId,
			MultipartFile fileInfo) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);

		DcCategory hfCategory = dcCategoryMapper.selectByPrimaryKey(categoryId);
		if (fileInfo != null) {
			FileMangeService fileMangeService = new FileMangeService();
			String arr[];
			ByteArrayOutputStream os = new ByteArrayOutputStream();
		    Thumbnails.of(fileInfo.getInputStream()).scale(0.8f).outputFormat("jpg").outputQuality(0.6).toOutputStream(os);
			arr = fileMangeService.uploadFile(os.toByteArray(), String.valueOf(request.getUserId()));
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
		Map<String, Object> map = new HashMap<String, Object>();
		DcCategoryExample categoryExample = new DcCategoryExample();
		categoryExample.createCriteria().andBossIdEqualTo(bossId).andParentCategoryIdEqualTo(parentCategoryId)
				.andIsDeletedEqualTo((short) 0);
		if (parentCategoryId == -1) {
			categoryExample.createCriteria().andProjectIdEqualTo(projectId);
		}
		List<DcCategory> list = dcCategoryMapper.selectByExample(categoryExample);
		if (CollectionUtils.isEmpty(list) || list.size() == 0) {
			DcGeneralFileExample example = new DcGeneralFileExample();
			example.createCriteria().andGeneralIdEqualTo(parentCategoryId);
			List<DcGeneralFile> files = dcGeneralFileMapper.selectByExample(example);
			map.put("type", "detail");
			map.put("list", files);
			return builder.body(ResponseUtils.getResponseBody(map));
		}
		map.put("type", "category");
		map.put("list", list);
		return builder.body(ResponseUtils.getResponseBody(map));
	}

	@ApiOperation(value = "查询下级类目", notes = "查询下级类目")
	@RequestMapping(value = "/findDownCategory", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> findDownCategory(Integer parentCategoryId, Integer projectId) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		Integer bossId = 1;
		List<Catrgory> result = new ArrayList<Catrgory>();
		DcCategoryExample categoryExample = new DcCategoryExample();
		categoryExample.createCriteria().andBossIdEqualTo(bossId).andParentCategoryIdEqualTo(parentCategoryId)
				.andIsDeletedEqualTo((short) 0);
		if (parentCategoryId == -1) {
			categoryExample.createCriteria().andProjectIdEqualTo(projectId);
		}
		List<DcCategory> list = dcCategoryMapper.selectByExample(categoryExample);
		System.out.println(list.toString());
		for (int i = 0; i < list.size(); i++) {
			DcCategory dcCategory = list.get(i);
			Catrgory catrgory = new Catrgory();
			catrgory.setCategoryId(dcCategory.getId());
			catrgory.setCategoryName(dcCategory.getHfName());
			catrgory.setFileId(dcCategory.getFileId());
			catrgory.setHasChildren(true);
			catrgory.setType(2);
			catrgory.setCategoryDetailId(dcCategory.getCategoryDetailId());
			categoryExample.clear();
			categoryExample.createCriteria().andParentCategoryIdEqualTo(dcCategory.getId())
					.andIsDeletedEqualTo((short) 0);
			List<DcCategory> dcCategories = dcCategoryMapper.selectByExample(categoryExample);
			if (CollectionUtils.isEmpty(dcCategories)) {
				catrgory.setHasChildren(false);
				if(dcCategory.getCategoryDetailId() == null) {
					catrgory.setType(3);
				}else {
					catrgory.setType(4);
				}
			}
			result.add(catrgory);
		}
		return builder.body(ResponseUtils.getResponseBody(result));
	}

	@ApiOperation(value = "根据名字等级获取类目", notes = "根据名字等级获取类目")
	@RequestMapping(value = "/getCategoryByNameOrLevel", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> getCategoryByNameOrLevel(HttpServletRequest request, Integer level, String name,
			Integer projectId) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		List<Catrgory> result = new ArrayList<Catrgory>();
		DcCategoryExample categoryExample = new DcCategoryExample();
		Integer bossId = 1;
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
		for (int i = 0; i < list.size(); i++) {
			DcCategory dcCategory = list.get(i);
			Catrgory catrgory = new Catrgory();
			catrgory.setCategoryId(dcCategory.getId());
			catrgory.setCategoryName(dcCategory.getHfName());
			catrgory.setFileId(dcCategory.getFileId());
			catrgory.setHasChildren(true);
			catrgory.setType(2);
			catrgory.setCategoryDetailId(dcCategory.getCategoryDetailId());
			categoryExample.clear();
			categoryExample.createCriteria().andParentCategoryIdEqualTo(dcCategory.getId())
					.andIsDeletedEqualTo((short) 0);
			List<DcCategory> dcCategories = dcCategoryMapper.selectByExample(categoryExample);
			if (CollectionUtils.isEmpty(dcCategories)) {
				catrgory.setHasChildren(false);
				if(dcCategory.getCategoryDetailId() == null) {
					catrgory.setType(3);
				}else {
					catrgory.setType(4);
				}
			}
			result.add(catrgory);
		}
		return builder.body(ResponseUtils.getResponseBody(result));
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
			ImageIO.write(readImg, "jpg", outputStream);
			outputStream.close();
		}
	}

	@ApiOperation(value = "删除图片", notes = "删除图片")
	@RequestMapping(value = "/deletePicture", method = RequestMethod.GET)
	public Integer deletePicture(Integer id) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
//		DcCategory category = dcCategoryMapper.selectByPrimaryKey(id);
		DcFileDesc fileDesc = dcFileDescMapper.selectByPrimaryKey(id);
		FileMangeService fileManageService = new FileMangeService();
		fileManageService.deleteFile(fileDesc.getGroupName(), fileDesc.getRemoteFilename());
		//
		return fileDesc.getId();
	}

	@ApiOperation(value = "添加类目详情", notes = "添加类目详情")
	@RequestMapping(value = "/addCategoryDetail", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> addCategoryDetail(Integer id, MultipartFile file) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		DcCategory dcCategory = dcCategoryMapper.selectByPrimaryKey(id);
		Integer categoryDetailId = null;
		DcCategoryDetailExample example = new DcCategoryDetailExample();
		example.createCriteria().andCategoryIdEqualTo(id);
		List<DcCategoryDetail> list = dcCategoryDetailMapper.selectByExample(example);
		if(CollectionUtils.isEmpty(list)) {
			DcCategoryDetail detail = new DcCategoryDetail();
			detail.setCategoryId(id);
			detail.setComment(0);
			detail.setPraise(0);
			detail.setCreateTime(LocalDateTime.now());
			detail.setModifyTime(LocalDateTime.now());
			detail.setIsDeleted((byte) 0);
			dcCategoryDetailMapper.insert(detail);
			categoryDetailId = detail.getId();
		}else {
			DcCategoryDetail detail = list.get(0);
			categoryDetailId = detail.getId();
		}
		dcCategory.setCategoryDetailId(categoryDetailId);
		dcCategoryMapper.updateByPrimaryKey(dcCategory);
		FileMangeService fileManageService = new FileMangeService();
		String arr[];
		arr = fileManageService.uploadFile(file.getBytes(), "1");
		DcFileDesc fileDesc = new DcFileDesc();
		fileDesc.setFileName(file.getName());
		fileDesc.setGroupName(arr[0]);
		fileDesc.setRemoteFilename(arr[1]);
		fileDesc.setCreateTime(LocalDateTime.now());
		fileDesc.setModifyTime(LocalDateTime.now());
		fileDesc.setIsDeleted((short) 0);
		dcFileDescMapper.insert(fileDesc);
		DcGeneralFile dcGeneralFile = new DcGeneralFile();
		dcGeneralFile.setFileId(fileDesc.getId());
		dcGeneralFile.setGeneralId(id);
		dcGeneralFile.setCreateTime(LocalDateTime.now());
		dcGeneralFile.setModifyTime(LocalDateTime.now());
		dcGeneralFile.setIsDeleted((byte) 0);
		dcGeneralFile.setCategoryDetailId(categoryDetailId);
		dcGeneralFileMapper.insert(dcGeneralFile);
		return builder.body(ResponseUtils.getResponseBody(1));
	}

	@ApiOperation(value = "删除类目详情", notes = "删除类目详情")
	@RequestMapping(value = "/deleteCategoryDetail", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> deleteCategoryDetail(Integer id) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		DcGeneralFile dcGeneralFile = dcGeneralFileMapper.selectByPrimaryKey(id);
		dcGeneralFileMapper.deleteByPrimaryKey(id);
		DcFileDesc fileDesc = dcFileDescMapper.selectByPrimaryKey(dcGeneralFile.getFileId());
		FileMangeService fileMangeService = new FileMangeService();
		fileMangeService.deleteFile(fileDesc.getGroupName(), fileDesc.getFileName());
		dcGeneralFileMapper.deleteByPrimaryKey(id);
		return builder.body(ResponseUtils.getResponseBody(fileDesc.getId()));
	}

	@ApiOperation(value = "修改类目详情", notes = "修改类目详情")
	@RequestMapping(value = "/updateCategoryDetail", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> updateCategoryDetail(Integer id, MultipartFile file) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		DcGeneralFile dcGeneralFile = dcGeneralFileMapper.selectByPrimaryKey(id);
		DcFileDesc fileDesc = dcFileDescMapper.selectByPrimaryKey(dcGeneralFile.getFileId());
		FileMangeService fileManageService = new FileMangeService();
		String arr[];
		arr = fileManageService.uploadFile(file.getBytes(), "1");
		fileManageService.deleteFile(fileDesc.getGroupName(), fileDesc.getRemoteFilename());
		fileDesc.setGroupName(arr[0]);
		fileDesc.setRemoteFilename(arr[1]);
		fileDesc.setModifyTime(LocalDateTime.now());
		dcFileDescMapper.updateByPrimaryKey(fileDesc);
		return builder.body(ResponseUtils.getResponseBody(dcGeneralFile.getId()));
	}

	@ApiOperation(value = "查询类目详情", notes = "查询类目详情")
	@RequestMapping(value = "/selectCategoryDetail", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> selectCategoryDetail(Integer categoryId) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		DcGeneralFileExample example = new DcGeneralFileExample();
		example.createCriteria().andGeneralIdEqualTo(categoryId);
		List<DcGeneralFile> files = dcGeneralFileMapper.selectByExample(example);
		return builder.body(ResponseUtils.getResponseBody(files));
	}
	
	@ApiOperation(value = "给类目详情点赞", notes = "给类目详情点赞")
	@RequestMapping(value = "/categoryDetailPraise", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> categoryDetailPraise(Integer categoryDetailId, Integer userId, String type) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		DcPraiseRecordExample recordExample = new DcPraiseRecordExample();
		recordExample.createCriteria().andEvaluateEqualTo(categoryDetailId).andTypeEqualTo(type);
		List<DcPraiseRecord> list = dcPraiseRecordMapper.selectByExample(recordExample);
		if(CollectionUtils.isEmpty(list)) {
			DcPraiseRecord record = new DcPraiseRecord();
			record.setType(type);
			record.setUserId(userId);
			record.setEvaluate(categoryDetailId);
			record.setCreateTime(LocalDateTime.now());
			record.setModifyTime(LocalDateTime.now());
			record.setIsDeleted((byte) 1);
			dcPraiseRecordMapper.insert(record);
			if("categoryDetail".equals(type)) {
				updateCatrgotyDetailPraise(1,categoryDetailId);
			}
		}else {
			System.out.println("else");
			DcPraiseRecord record = list.get(0);
			if(record.getIsDeleted() == 0) {
				System.out.println("deleted=====0");
				record.setIsDeleted((byte) 1);
				dcPraiseRecordMapper.updateByPrimaryKey(record);
				if("categoryDetail".equals(type)) {
					updateCatrgotyDetailPraise(1,categoryDetailId);
				}
			}else {
				record.setIsDeleted((byte) 0);
				dcPraiseRecordMapper.updateByPrimaryKey(record);
				if("categoryDetail".equals(type)) {
					updateCatrgotyDetailPraise(0,categoryDetailId);
				}
			}
//			if(record.getIsDeleted() == 1) {
//				
//			}
		}
		return builder.body(ResponseUtils.getResponseBody(1));
	}
	
	public void updateCatrgotyDetailPraise(Integer result, Integer categoryDetailId) {
		if(result == 0) {
			DcCategoryDetail categoryDetail = dcCategoryDetailMapper.selectByPrimaryKey(categoryDetailId);
			categoryDetail.setPraise(categoryDetail.getPraise()-1);
			dcCategoryDetailMapper.updateByPrimaryKey(categoryDetail);
		}
		if(result == 1) {
			DcCategoryDetail categoryDetail = dcCategoryDetailMapper.selectByPrimaryKey(categoryDetailId);
			categoryDetail.setPraise(categoryDetail.getPraise()+1);
			dcCategoryDetailMapper.updateByPrimaryKey(categoryDetail);
		}
	}
	
	@ApiOperation(value = "查看点赞数量和是否点赞", notes = "查看点赞数量和是否点赞")
	@RequestMapping(value = "/findPraise", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> findPraise(Integer categoryDetailId, Integer userId) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		Map<String, Object> map = new HashMap<String, Object>();
		DcCategoryDetail detail = dcCategoryDetailMapper.selectByPrimaryKey(categoryDetailId);
		DcPraiseRecordExample example = new DcPraiseRecordExample();
		example.createCriteria().andTypeEqualTo("categoryDetail").andEvaluateEqualTo(categoryDetailId)
		.andUserIdEqualTo(userId).andIsDeletedEqualTo((byte) 1);
		map.put("count", detail.getPraise());
		if(CollectionUtils.isEmpty(dcPraiseRecordMapper.selectByExample(example))) {
			map.put("isPraise", 2);
		}else {
			map.put("isPraise", 3);
		}
		
		return builder.body(ResponseUtils.getResponseBody(map));
	}
	
	
	@ApiOperation(value = "给类目详情评论", notes = "给类目详情评论")
	@RequestMapping(value = "/categoryDetailComment", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> categoryDetailComment(Integer categoryDetailId, Integer userId, String type
			,String typeContent, Integer parentCommtentId, String comment) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		DcEvaluate evaluate = new DcEvaluate();
		evaluate.setType("categoryDetail");
		evaluate.setTypeContent("heart");
		evaluate.setParentEvaluateId(parentCommtentId);
		evaluate.setInstanceId(categoryDetailId);
		evaluate.setUserId(userId);
		evaluate.setEvaluate(comment);
		dcEvaluateMapper.insert(evaluate);
		DcCategoryDetail categoryDetail = dcCategoryDetailMapper.selectByPrimaryKey(categoryDetailId);
		categoryDetail.setComment(categoryDetail.getComment()+1);
		dcCategoryDetailMapper.updateByPrimaryKey(categoryDetail);
		return builder.body(ResponseUtils.getResponseBody(1));
	}
	
	@ApiOperation(value = "查询详情评论", notes = "查询详情评论")
	@RequestMapping(value = "/findcategoryDetailComment", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> findcategoryDetailComment(Integer categoryDetailId) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		Map<String, Object> map = new HashMap<String, Object>();
		List<Comment> result = new ArrayList<Comment>();
		DcEvaluateExample evaluateExample = new DcEvaluateExample();
		evaluateExample.createCriteria().andInstanceIdEqualTo(categoryDetailId).andTypeEqualTo("categoryDetail");
		List<DcEvaluate> list = dcEvaluateMapper.selectByExample(evaluateExample);
		for (int i = 0; i < list.size(); i++) {
			DcEvaluate evaluate = list.get(i);
			Comment comment = new Comment();
			DcUser dcUser = dcUserMapper.selectByPrimaryKey(evaluate.getUserId());
			comment.setName(dcUser.getRealName());
			comment.setContent(evaluate.getEvaluate());
			result.add(comment);
		}
		DcCategoryDetail detail = dcCategoryDetailMapper.selectByPrimaryKey(categoryDetailId);
		map.put("list", result);
		map.put("count", detail.getComment());
		return builder.body(ResponseUtils.getResponseBody(map));
	}

	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request) {
		// 转换日期
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));// CustomDateEditor为自定义日期编辑器
	}

}
