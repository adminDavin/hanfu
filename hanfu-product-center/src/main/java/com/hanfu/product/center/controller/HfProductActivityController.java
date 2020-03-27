package com.hanfu.product.center.controller;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.hanfu.product.center.dao.*;
import com.hanfu.product.center.manual.model.DistributionActivity;
import com.hanfu.product.center.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hanfu.common.service.FileMangeService;
import com.hanfu.product.center.manual.dao.ManualDao;
import com.hanfu.product.center.manual.model.ActivityProductInfo;
import com.hanfu.product.center.manual.model.DistributionDiscount;
import com.hanfu.product.center.manual.model.ProductActivityInfo;
import com.hanfu.product.center.request.HfSeniorityRequest;
import com.hanfu.product.center.request.ProductActivityInfoRequest;
import com.hanfu.product.center.request.ProductActivityRequest;
import com.hanfu.product.center.request.ProductActivityRequest.ActivityTypeEnum;
import com.hanfu.user.center.model.HfUser;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseEntity.BodyBuilder;
import com.hanfu.utils.response.handler.ResponseUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
@RequestMapping("/hfProductActivity")
@Api
public class HfProductActivityController {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ManualDao manualDao;

	@Autowired
	private HfActivityMapper hfActivityMapper;

	@Autowired
	private HfActivityProductMapper hfActivityProductMapper;

	@Autowired
	private ProductMapper productMapper;

	@Autowired
	private FileDescMapper fileDescMapper;
	@Autowired
	private HfActivityGroupMapper hfActivityGroupMapper;
	@Autowired
	private HfActivityCountMapper hfActivityCountMapper;
	@Autowired
	private HfGoodsMapper hfGoodsMapper;
	@Autowired
	private HfRespMapper hfRespMapper;
	@Autowired
	private HfUsersMapper hfUsersMapper;
	@Autowired
	private HfUserBalanceMapper hfUserBalanceMapper;

	@ApiOperation(value = "添加活动", notes = "添加活动（秒杀，团购，精选，分销）")
	@RequestMapping(value = "/addProdcutActivity", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> addProdcutActivity(ProductActivityRequest request) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		HfActivity hfActivity = new HfActivity();
		hfActivity.setActivityName(request.getActivityName());
		hfActivity.setActivityType(request.getActivityType());
		if (!StringUtils.isEmpty(request.getStartTime())) {
			Instant instant = request.getStartTime().toInstant();
			ZoneId zoneId = ZoneId.systemDefault();
			LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
			hfActivity.setStartTime(localDateTime);
		}
		if (!StringUtils.isEmpty(request.getEndTime())) {
			Instant instant = request.getEndTime().toInstant();
			ZoneId zoneId = ZoneId.systemDefault();
			LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
			hfActivity.setEndTime(localDateTime);
		}
		HfUser hfUser = manualDao.select(request.getUserId());
		if (hfUser != null) {
			if (hfUser.getNickName() != null) {
				hfActivity.setLastModifier(hfUser.getNickName());
			}

		}
		hfActivity.setCreateTime(LocalDateTime.now());
		hfActivity.setModifyTime(LocalDateTime.now());
		hfActivity.setIsDeleted((byte) 0);
		hfActivityMapper.insert(hfActivity);
		return builder.body(ResponseUtils.getResponseBody(hfActivity.getId()));
	}

	@ApiOperation(value = "查询活动", notes = "查询活动")
	@RequestMapping(value = "/findProdcutActivity", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> addProdcutActivity(String activityType) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		List<ProductActivityInfo> result = manualDao.selectProductActivityList(activityType);
		for (int i = 0; i < result.size(); i++) {
			ProductActivityInfo productActivityInfo = result.get(i);
			productActivityInfo.setActivityType(ActivityTypeEnum.getActivityTypeEnum(activityType).getName());
		}
		return builder.body(ResponseUtils.getResponseBody(result));
	}

	@ApiOperation(value = "删除活动", notes = "删除活动")
	@RequestMapping(value = "/deleteProdcutActivity", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> deleteProdcutActivity(Integer id) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		hfActivityMapper.deleteByPrimaryKey(id);
		HfActivityProductExample example = new HfActivityProductExample();
		example.createCriteria().andActivityIdEqualTo(id);
		hfActivityProductMapper.deleteByExample(example);
		return builder.body(ResponseUtils.getResponseBody("删除成功"));
	}

	@ApiOperation(value = "修改活动相关信息", notes = "修改活动相关信息")
	@RequestMapping(value = "/updateProdcutActivity", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> updateProdcutActivity(String activityName, Integer id, MultipartFile fileInfo)
			throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		HfActivity activity = hfActivityMapper.selectByPrimaryKey(id);
		if (activity != null) {
			String uuid = UUID.randomUUID().toString();
			uuid = uuid.replace("-", "");
			if (fileInfo != null) {
				FileMangeService fileMangeService = new FileMangeService();
				String arr[];
				arr = fileMangeService.uploadFile(fileInfo.getBytes(), "-1");
				if (activity.getFileId() == null) {
					FileDesc fileDesc = new FileDesc();
					fileDesc.setFileName(uuid);
					fileDesc.setGroupName(arr[0]);
					fileDesc.setRemoteFilename(arr[1]);
					fileDesc.setUserId(-1);
					fileDesc.setCreateTime(LocalDateTime.now());
					fileDesc.setModifyTime(LocalDateTime.now());
					fileDesc.setIsDeleted((short) 0);
					fileDescMapper.insert(fileDesc);
					activity.setFileId(fileDesc.getId());
				} else {
					FileDesc fileDesc = fileDescMapper.selectByPrimaryKey(activity.getFileId());
					fileMangeService.deleteFile(fileDesc.getGroupName(), fileDesc.getRemoteFilename());
					fileDesc.setGroupName(arr[0]);
					fileDesc.setRemoteFilename(arr[1]);
					fileDesc.setModifyTime(LocalDateTime.now());
					fileDescMapper.updateByPrimaryKey(fileDesc);
				}
			}
			if (!StringUtils.isEmpty(activityName)) {
				activity.setActivityName(activityName);
			}
			activity.setModifyTime(LocalDateTime.now());
			hfActivityMapper.updateByPrimaryKey(activity);
		}
		return builder.body(ResponseUtils.getResponseBody("修改成功"));
	}

	@ApiOperation(value = "给活动绑定商品", notes = "给活动绑定商品")
	@RequestMapping(value = "/intoActivityProduct", method = RequestMethod.POST)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "id", value = "活动id", required = true, type = "Integer"),
			@ApiImplicitParam(paramType = "query", name = "productId", value = "商品id", required = true, type = "Integer"), })
	public ResponseEntity<JSONObject> intoActivityProduct(@RequestParam(required = true) Integer id,
			@RequestParam(required = true) Integer productId) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		HfActivityProduct hfActivityProduct = new HfActivityProduct();
		if (productId != null) {
			hfActivityProduct.setActivityId(id);
			hfActivityProduct.setProductId(productId);
			hfActivityProduct.setCreateTime(LocalDateTime.now());
			hfActivityProduct.setModifyTime(LocalDateTime.now());
			hfActivityProduct.setIsDeleted((byte) 0);
			hfActivityProductMapper.insert(hfActivityProduct);
			return builder.body(ResponseUtils.getResponseBody("添加成功"));
		}
		return builder.body(ResponseUtils.getResponseBody("添加失败"));
	}

	@ApiOperation(value = "给活动删除商品", notes = "给活动删除商品")
	@RequestMapping(value = "/deleteActivityProduct", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> deleteActivityProduct(Integer id) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		hfActivityProductMapper.deleteByPrimaryKey(id);
		return builder.body(ResponseUtils.getResponseBody("删除成功"));
	}

	@ApiOperation(value = "查询活动商品列表信息", notes = "查询活动商品列表信息")
	@RequestMapping(value = "/getActivityProductList", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> getActivityProductList(Integer id) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		HfActivityProductExample example = new HfActivityProductExample();
		example.createCriteria().andActivityIdEqualTo(id);
		List<HfActivityProduct> list = hfActivityProductMapper.selectByExample(example);
		if (!list.isEmpty()) {
			List<ActivityProductInfo> result = new ArrayList<ActivityProductInfo>();
			for (int i = 0; i < list.size(); i++) {
				HfActivityProduct activityProduct = list.get(i);
				ActivityProductInfo activityProductInfo = new ActivityProductInfo();
				activityProductInfo.setId(activityProduct.getId());
				activityProductInfo.setAcivityId(activityProduct.getActivityId());
				activityProductInfo.setProductId(activityProduct.getProductId());
				Product product = productMapper.selectByPrimaryKey(activityProduct.getProductId());
				activityProductInfo.setProductName(product.getHfName());
				activityProductInfo.setDiscountRatio(activityProduct.getDiscountRatio());
				activityProductInfo.setDistributionRatio(activityProduct.getDistributionRatio());
				activityProductInfo.setFavoravlePrice(activityProduct.getFavoravlePrice());
				activityProductInfo.setGroupNum(activityProduct.getGroupNum());
				activityProductInfo.setInventoryCelling(activityProduct.getInventoryCelling());
				activityProductInfo.setCreateTime(activityProduct.getCreateTime());
				activityProductInfo.setModifyTime(activityProduct.getModifyTime());
				result.add(activityProductInfo);
			}
			return builder.body(ResponseUtils.getResponseBody(result));
		}
		return builder.body(ResponseUtils.getResponseBody("还未添加信息"));
	}

	@ApiOperation(value = "完善活动商品信息", notes = "完善活动商品信息")
	@RequestMapping(value = "/updateActivityProduct", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> updateActivityProduct(Integer id, ProductActivityInfoRequest request)
			throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		HfActivityProduct hfActivityProduct = hfActivityProductMapper.selectByPrimaryKey(id);
		if (hfActivityProduct == null) {
			return builder.body(ResponseUtils.getResponseBody("数据不存在"));
		}
		if (!StringUtils.isEmpty(request.getDistributionRatio())) {
			ArrayList<DistributionDiscount> list = new ArrayList<DistributionDiscount>();
			String[] str = request.getDistributionRatio().split(",");
			String nameInfo = str[0];
			String ratioInfo = str[1];
			String[] name = nameInfo.split(":");
			String[] ratio = ratioInfo.split(":");
			list.add(new DistributionDiscount(name[1], ratio[1]));
			JSONArray array = JSONArray.parseArray(JSON.toJSONString(list));
			if(!StringUtils.isEmpty(hfActivityProduct.getDistributionRatio())) {
				JSONArray jsonArray = JSONArray.parseArray(hfActivityProduct.getDistributionRatio());
				JSONArray resultArray = new JSONArray();
				jsonArray.forEach(action -> {
					resultArray.add(action);
				});
				resultArray.add(array.get(0));
				hfActivityProduct.setDistributionRatio(resultArray.toJSONString());
			}else {
				hfActivityProduct.setDistributionRatio(array.toString());
			}
		}
		if (!StringUtils.isEmpty(String.valueOf(request.getDiscountRatio()))) {
			hfActivityProduct.setDiscountRatio(request.getDiscountRatio());
		}
		if (!StringUtils.isEmpty(String.valueOf(request.getFavoravlePrice()))) {
			hfActivityProduct.setFavoravlePrice(request.getFavoravlePrice());
		}
		if (!StringUtils.isEmpty(request.getGroupNum())) {
			hfActivityProduct.setGroupNum(request.getGroupNum());
		}
		if (!StringUtils.isEmpty(request.getInventoryCelling())) {
			hfActivityProduct.setInventoryCelling(request.getInventoryCelling());
		}
		hfActivityProduct.setModifyTime(LocalDateTime.now());
		hfActivityProductMapper.updateByPrimaryKey(hfActivityProduct);
		return builder.body(ResponseUtils.getResponseBody("修改成功"));
	}

	@ApiOperation(value = "获取商品活动类型", notes = "获取商品活动类型")
	@RequestMapping(value = "/getProdcutActivityType", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> getProdcutActivityType() throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> params1 = new HashMap<String, Object>();
		Map<String, Object> params2 = new HashMap<String, Object>();
		Map<String, Object> params3 = new HashMap<String, Object>();
		params.put("activityType", "seckillActivity");
		params.put("activityDesc", "秒杀");
		params1.put("activityType", "groupActivity");
		params1.put("activityDesc", "团购");

		params2.put("activityType", "seniorityActivity");
		params2.put("activityDesc", "精选");

		params3.put("activityType", "distributionActivity");
		params3.put("activityDesc", "分销");

		List<Object> list = new ArrayList<>();
		list.add(0, params);
		list.add(1, params1);
		list.add(2, params2);
		list.add(3, params3);
		return builder.body(ResponseUtils.getResponseBody(list));
	}


	@ApiOperation(value = "开团", notes = "开团")
	@RequestMapping(value = "/addGroup", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> addGroup(Integer activityId,Integer goodsId,Integer userId) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		HfGoods hfGoods= hfGoodsMapper.selectByPrimaryKey(goodsId);

		HfActivityGroup hfActivityGroup = new HfActivityGroup();
		hfActivityGroup.setCreateTime(LocalDateTime.now());
		hfActivityGroup.setModifyTime(LocalDateTime.now());
		hfActivityGroup.setActivityId(activityId);
		hfActivityGroup.setClusteringTime(LocalDateTime.now());
		hfActivityGroup.setState(0);
		hfActivityGroup.setIsDeleted((byte) 0);
		hfActivityGroup.setUserId(userId);
		hfActivityGroup.setProductId(hfGoods.getProductId());
		hfActivityGroupMapper.insertSelective(hfActivityGroup);


		HfActivityCount hfActivityCount = new HfActivityCount();
		hfActivityCount.setCreateTime(LocalDateTime.now());
		hfActivityCount.setModifyTime(LocalDateTime.now());
		hfActivityCount.setIsDeleted((byte) 0);
		hfActivityCount.setUserId(userId);
		hfActivityCount.setGoodsId(goodsId);
		hfActivityCount.setClusteringTime(LocalDateTime.now());
		hfActivityCount.setState(0);
		hfActivityCount.setGroupId(hfActivityGroup.getId());
		hfActivityCountMapper.insert(hfActivityCount);
		Map<String,Integer> map = new HashMap<String, Integer>();
		HfActivityCountExample hfActivityCountExample = new HfActivityCountExample();
		hfActivityCountExample.createCriteria().andGroupIdEqualTo(hfActivityGroup.getId()).andIsDeletedEqualTo((byte) 0).andStateEqualTo(0);
		map.put("num",hfActivityCountMapper.selectByExample(hfActivityCountExample).size());
		map.put("hfActivityGroupId",hfActivityGroup.getId());
		return builder.body(ResponseUtils.getResponseBody(map));
	}

	@ApiOperation(value = "进团", notes = "进团")
	@RequestMapping(value = "/entranceGroup", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> entranceGroup(Integer hfActivityGroupId,Integer userId,Integer goodsId) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);

		HfActivityCountExample hfActivityCountExample2 = new HfActivityCountExample();
		hfActivityCountExample2.createCriteria().andGroupIdEqualTo(hfActivityGroupId).andIsDeletedEqualTo((byte) 0).andStateEqualTo(0).andUserIdEqualTo(userId);
		List<HfActivityCount> hfActivityCount2= hfActivityCountMapper.selectByExample(hfActivityCountExample2);
		if (hfActivityCount2.size()!=0){
			return builder.body(ResponseUtils.getResponseBody("已经参与了团购"));
		}
		//拿商品
		HfGoods hfGoods= hfGoodsMapper.selectByPrimaryKey(goodsId);
		//活动创建
		HfActivityGroupExample hfActivityGroupExample = new HfActivityGroupExample();
		hfActivityGroupExample.createCriteria().andIdEqualTo(hfActivityGroupId).andIsDeletedEqualTo((byte) 0).andStateEqualTo(0);
		List<HfActivityGroup> hfActivityGroup= hfActivityGroupMapper.selectByExample(hfActivityGroupExample);

		//活动人数
		HfActivityCountExample hfActivityCountExample = new HfActivityCountExample();
		hfActivityCountExample.createCriteria().andGroupIdEqualTo(hfActivityGroupId).andIsDeletedEqualTo((byte) 0).andStateEqualTo(0);
		List<HfActivityCount> hfActivityCount= hfActivityCountMapper.selectByExample(hfActivityCountExample);

		HfActivityProductExample hfActivityProductExample = new HfActivityProductExample();
		hfActivityProductExample.createCriteria().andProductIdEqualTo(hfGoods.getProductId()).andActivityIdEqualTo(hfActivityGroup.get(0).getActivityId());
		List<HfActivityProduct> hfActivityProductList= hfActivityProductMapper.selectByExample(hfActivityProductExample);
		if (hfActivityProductList.get(0).getGroupNum()<=hfActivityCount.size()){
			return builder.body(ResponseUtils.getResponseBody("团购已满"));
		}
		HfActivityCount hfActivityCount1 = new HfActivityCount();
		hfActivityCount1.setCreateTime(LocalDateTime.now());
		hfActivityCount1.setModifyTime(LocalDateTime.now());
		hfActivityCount1.setIsDeleted((byte) 0);
		hfActivityCount1.setUserId(userId);
		hfActivityCount1.setGoodsId(goodsId);
		hfActivityCount1.setClusteringTime(LocalDateTime.now());
		hfActivityCount1.setState(0);
		hfActivityCount1.setGroupId(hfActivityGroupId);
		hfActivityCountMapper.insert(hfActivityCount1);
		HfActivityCountExample hfActivityCountExample1 = new HfActivityCountExample();
		hfActivityCountExample1.createCriteria().andGroupIdEqualTo(hfActivityGroupId).andIsDeletedEqualTo((byte) 0).andStateEqualTo(0);
		return builder.body(ResponseUtils.getResponseBody(hfActivityCountMapper.selectByExample(hfActivityCountExample)));
	}
	@ApiOperation(value = "取消成团", notes = "取消成团")
	@RequestMapping(value = "/cancelGroup", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> cancelGroup(Integer userId,Integer hfActivityGroupId) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		HfActivityCount hfActivityCount = new HfActivityCount();
		hfActivityCount.setState(1);
		HfActivityCountExample hfActivityCountExample2 = new HfActivityCountExample();
		hfActivityCountExample2.createCriteria().andGroupIdEqualTo(hfActivityGroupId).andIsDeletedEqualTo((byte) 0).andStateEqualTo(0).andUserIdEqualTo(userId);
		hfActivityCountMapper.updateByExampleSelective(hfActivityCount,hfActivityCountExample2);
		HfActivityCountExample hfActivityCountExample = new HfActivityCountExample();
		hfActivityCountExample.createCriteria().andGroupIdEqualTo(hfActivityGroupId).andIsDeletedEqualTo((byte) 0).andStateEqualTo(0);
		List<HfActivityCount> hfActivityCount3= hfActivityCountMapper.selectByExample(hfActivityCountExample);
		if (hfActivityCount3.size()==0){
			HfActivityGroup hfActivityGroup = new HfActivityGroup();
			hfActivityGroup.setState(1);
			HfActivityGroupExample hfActivityGroupExample = new HfActivityGroupExample();
			hfActivityGroupExample.createCriteria().andIdEqualTo(hfActivityGroupId).andIsDeletedEqualTo((byte) 0).andStateEqualTo(0);
			hfActivityGroupMapper.updateByExampleSelective(hfActivityGroup,hfActivityGroupExample);
			return builder.body(ResponseUtils.getResponseBody("无人参与团购，团购取消"));
		}
		return builder.body(ResponseUtils.getResponseBody(0));
	}

//	@ApiOperation(value = "分销计算", notes = "分销计算")
//	@RequestMapping(value = "/distributionActivityCalculate", method = RequestMethod.GET)
//	public ResponseEntity<JSONObject> distributionActivityCalculate(Integer activityId,Integer goodsId,Integer userId) throws JSONException {
//		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
//		//用户
//        HfUsers hfUsers= hfUsersMapper.selectByPrimaryKey(userId);
//		//物品
//		HfGoods hfGoods= hfGoodsMapper.selectByPrimaryKey(goodsId);
//		//价格
//		HfResp hfResp= hfRespMapper.selectByPrimaryKey(hfGoods.getRespId());
//		//活动
//		HfActivityProductExample hfActivityProductExample = new HfActivityProductExample();
//		hfActivityProductExample.createCriteria().andProductIdEqualTo(hfGoods.getProductId()).andActivityIdEqualTo(activityId);
//		List<HfActivityProduct> hfActivityProductList= hfActivityProductMapper.selectByExample(hfActivityProductExample);
//		//转对象
//		List<DistributionActivity> list = JSONArray.parseArray(hfActivityProductList.get(0).getDistributionRatio(), DistributionActivity.class);
//		list.forEach(lists ->{
//		HfUsersExample hfUsersExample = new HfUsersExample();
//		hfUsersExample.createCriteria().andOwnInvitationCodeEqualTo(hfUsers.getOwnInvitationCode());
//		//分销上级用户
//		List<HfUsers> hfUsers1=	hfUsersMapper.selectByExample(hfUsersExample);
//		HfUserBalanceExample hfUsersExample1 = new HfUserBalanceExample();
//		hfUsersExample1.createCriteria().andUserIdEqualTo(hfUsers1.get(0).getId());
//        List<HfUserBalance> hfUserBalances=hfUserBalanceMapper.selectByExample(hfUsersExample1);
//        HfUserBalance hfUserBalance = new HfUserBalance();
//        hfUserBalance.setHfBalance(hfUserBalances.get(0).getHfBalance());
//			hfUserBalanceMapper.updateByExampleSelective()
//		});
//
//		return builder.body(ResponseUtils.getResponseBody(0));
//	}
}
