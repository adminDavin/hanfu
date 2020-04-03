package com.hanfu.product.center.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hanfu.common.service.FileMangeService;
import com.hanfu.product.center.dao.*;
import com.hanfu.product.center.manual.dao.HfGroupDao;
import com.hanfu.product.center.manual.dao.ManualDao;
import com.hanfu.product.center.manual.model.*;
import com.hanfu.product.center.model.*;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@CrossOrigin
@RestController
@RequestMapping("/hfProductActivity")
@Api
public class HfProductActivityController {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final String KEY_NAME = "name";

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
    @Autowired
    private HfOrderMapper hfOrderMapper;
    @Autowired
    private HfOrderDetailMapper hfOrderDetailMapper;
    @Autowired
    private HfGroupDao hfGroupDao;
    @Autowired
    private HfPriceMapper hfPriceMapper;

    @ApiOperation(value = "添加活动", notes = "添加活动（秒杀，团购，精选，分销）")
    @RequestMapping(value = "/addProdcutActivity", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> addProdcutActivity(ProductActivityRequest request) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HfActivity hfActivity = new HfActivity();
        hfActivity.setActivityName(request.getActivityName());
        hfActivity.setActivityType(request.getActivityType());
        if (!StringUtils.isEmpty(request.getStartTime())) {
//            Instant instant = request.getStartTime().toInstant();
//            ZoneId zoneId = ZoneId.systemDefault();
//            LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
            hfActivity.setStartTime(request.getStartTime());
        }
        if (!StringUtils.isEmpty(request.getEndTime())) {
            Instant instant = request.getEndTime().toInstant();
            ZoneId zoneId = ZoneId.systemDefault();
            LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
            hfActivity.setEndTime(request.getEndTime());
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
//			HfActivity activity = hfActivityMapper.selectByPrimaryKey(productActivityInfo.getId());
            productActivityInfo.setActivityType(ActivityTypeEnum.getActivityTypeEnum(activityType).getName());
//			SimpleDateFormat sdf = new SimpleDateFormat("HH:ss:mm");
//			productActivityInfo.setStartTimes(sdf.format(productActivityInfo.getStartTime()));
//			productActivityInfo.setEndTimes(sdf.format(productActivityInfo.getEndTime()));
//			Date date = new Date();
//			if(date.before(productActivityInfo.getStartTime())) {
//				System.out.println("活动未开始");
//				productActivityInfo.setActivityState(-1);
//				activity.setActivityState(-1);
//				hfActivityMapper.updateByPrimaryKey(activity);
//			}
//			if(date.after(productActivityInfo.getStartTime()) && date.after(productActivityInfo.getEndTime())) {
//				System.out.println("活动开始中");
//				productActivityInfo.setActivityState(0);
//				activity.setActivityState(0);
//				hfActivityMapper.updateByPrimaryKey(activity);
//			}
//			if(date.after(productActivityInfo.getEndTime())) {
//				System.out.println("活动结束了");
//				productActivityInfo.setActivityState(1);
//				activity.setActivityState(1);
//				hfActivityMapper.updateByPrimaryKey(activity);
//			}
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
    public ResponseEntity<JSONObject> updateProdcutActivity(String activityName, Integer id, MultipartFile fileInfo,
                                                            Date startTime, Date endTime) throws Exception {
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
            if (startTime != null) {
                Instant instant = startTime.toInstant();
                ZoneId zoneId = ZoneId.systemDefault();
                LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
                activity.setStartTime(new Date());
            }
            if (endTime != null) {
                Instant instant = endTime.toInstant();
                ZoneId zoneId = ZoneId.systemDefault();
                LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
                activity.setStartTime(new Date());
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
            @ApiImplicitParam(paramType = "query", name = "productId", value = "商品id", required = true, type = "Integer"),})
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
            String[] names = nameInfo.split(":");
            String[] ratio = ratioInfo.split(":");
            list.add(new DistributionDiscount(names[1], ratio[1]));
            JSONArray array = JSONArray.parseArray(JSON.toJSONString(list));
            JSONObject jsonObj = JSONObject.parseObject(array.getString(0).toString());
            if (!StringUtils.isEmpty(hfActivityProduct.getDistributionRatio())) {
                JSONArray jsonArray = JSONArray.parseArray(hfActivityProduct.getDistributionRatio());
                JSONArray resultArray = new JSONArray();
                jsonArray.forEach(action -> {
                    resultArray.add(action);
                });
                resultArray.add(array.get(0));

                JSONArray sortedJsonArray = new JSONArray();
                List<JSONObject> jsonValues = new ArrayList<JSONObject>();
                for (int i = 0; i < resultArray.size(); i++) {
                    jsonValues.add(resultArray.getJSONObject(i));
                }

                if (Integer.valueOf(names[1]) - 1 != jsonValues.get(jsonValues.size() - 2).getInteger("name")) {
                    return builder.body(ResponseUtils.getResponseBody("-1"));
                }
                Collections.sort(jsonValues, new Comparator<JSONObject>() {
                    @Override
                    public int compare(JSONObject a, JSONObject b) {
                        String valA = new String();
                        String valB = new String();
                        String aStr = a.getString(KEY_NAME);
                        valA = aStr.replaceAll("-", "");
                        String bStr = b.getString(KEY_NAME);
                        valB = bStr.replaceAll("-", "");
                        return -valB.compareTo(valA);
                    }
                });
                for (int i = 0; i < resultArray.size(); i++) {
                    sortedJsonArray.add(jsonValues.get(i));
                }
                hfActivityProduct.setDistributionRatio(sortedJsonArray.toJSONString());
            } else {
                if (Integer.valueOf(names[1]) != 1) {
                    return builder.body(ResponseUtils.getResponseBody("-1"));
                }
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
    public ResponseEntity<JSONObject> addGroup(Integer activityId, Integer goodsId, Integer userId, Integer orderId) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HfGoods hfGoods = hfGoodsMapper.selectByPrimaryKey(goodsId);
        List<GroupList> groupLists = new ArrayList<>();
        GroupList groupList = new GroupList();
        HfActivityGroup hfActivityGroup = new HfActivityGroup();
        hfActivityGroup.setCreateTime(LocalDateTime.now());
        hfActivityGroup.setModifyTime(LocalDateTime.now());
        hfActivityGroup.setActivityId(activityId);
        hfActivityGroup.setClusteringTime(new Date());
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
        hfActivityCount.setOrderId(orderId);
        hfActivityCountMapper.insert(hfActivityCount);
        //---------
        HfActivityCountExample hfActivityCountExample3 = new HfActivityCountExample();
        hfActivityCountExample3.createCriteria().andGroupIdEqualTo(hfActivityGroup.getId()).andIsDeletedEqualTo((byte) 0);
        List<HfActivityCount> hfActivityCountList= hfActivityCountMapper.selectByExample(hfActivityCountExample3);
        List<Map<String, String>> lists = new ArrayList<>();
        hfActivityCountList.forEach(hfActivityCount4 -> {
            Map<String,String> map = new HashMap<>();
            HfUsers hfUsers1 = hfUsersMapper.selectByPrimaryKey(hfActivityCount4.getUserId());
            map.put("userName", hfUsers1.getNickName());
            map.put("fileId", String.valueOf(hfUsers1.getFileId()));
            lists.add(map);
        });
        groupList.setUser(lists);
        //成团
        HfActivityGroupExample hfActivityGroupExample7 = new HfActivityGroupExample();
        hfActivityGroupExample7.createCriteria().andIdEqualTo(hfActivityGroup.getId()).andIsDeletedEqualTo((byte) 0);
        List<HfActivityGroup> hfActivityGroup7 = hfActivityGroupMapper.selectByExample(hfActivityGroupExample7);
        //活动
        HfActivity hfActivity = hfActivityMapper.selectByPrimaryKey(hfActivityGroup7.get(0).getActivityId());
        //活动商品
        HfActivityProductExample hfActivityProductExample7 = new HfActivityProductExample();
        hfActivityProductExample7.createCriteria().andActivityIdEqualTo(hfActivityGroup7.get(0).getActivityId()).andProductIdEqualTo(hfActivityGroup7.get(0).getProductId());
        List<HfActivityProduct> hfActivityProduct = hfActivityProductMapper.selectByExample(hfActivityProductExample7);

        Date date1 = new Date();
        Date date4 = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            date1 = f.parse(f.format(new Date()));
            date4 = f.parse(f.format(hfActivityGroup7.get(0).getClusteringTime()));

        } catch (ParseException e) {
            e.printStackTrace();
        }
        HfUsers hfUsers = hfUsersMapper.selectByPrimaryKey(hfActivityGroup7.get(0).getUserId());
        Product product = productMapper.selectByPrimaryKey(hfActivityGroup7.get(0).getProductId());
        groupList.setGroupId(hfActivityGroup.getId());
        groupList.setGroupSum(hfActivityProduct.get(0).getGroupNum());
        groupList.setNowSum(hfActivityCountList.size());
        groupList.setTime(86400000 - (date1.getTime() - date4.getTime()));
        groupList.setGroupUserName(hfUsers.getNickName());
        groupList.setGroupFileId(hfUsers.getFileId());
        groupList.setProductId(product.getId());
        groupList.setProductName(product.getHfName());
        groupList.setProductFileId(product.getFileId());
        groupList.setSellPrice(price(product.getId()).get("sellPrice"));
        groupList.setLinePrice(price(product.getId()).get("linePrice"));
        groupLists.add(groupList);
        //---------
//        Map<String, Integer> map1 = new HashMap<String, Integer>();
//        HfActivityCountExample hfActivityCountExample = new HfActivityCountExample();
//        hfActivityCountExample.createCriteria().andGroupIdEqualTo(hfActivityGroup.getId()).andIsDeletedEqualTo((byte) 0).andStateEqualTo(0);
//        map1.put("num", hfActivityCountMapper.selectByExample(hfActivityCountExample).size());
//        map1.put("hfActivityGroupId", hfActivityGroup.getId());
        return builder.body(ResponseUtils.getResponseBody(groupList));
    }

    @ApiOperation(value = "进团", notes = "进团")
    @RequestMapping(value = "/entranceGroup", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> entranceGroup(Integer hfActivityGroupId, Integer userId, Integer goodsId,Integer orderId) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        List<GroupList> groupLists = new ArrayList<>();
        GroupList groupList = new GroupList();
        HfActivityCountExample hfActivityCountExample2 = new HfActivityCountExample();
        hfActivityCountExample2.createCriteria().andGroupIdEqualTo(hfActivityGroupId).andIsDeletedEqualTo((byte) 0).andStateEqualTo(0).andUserIdEqualTo(userId);
        List<HfActivityCount> hfActivityCount2 = hfActivityCountMapper.selectByExample(hfActivityCountExample2);
        if (hfActivityCount2.size() != 0) {
            return builder.body(ResponseUtils.getResponseBody("已经参与了团购"));
        }
        //拿商品
        HfGoods hfGoods = hfGoodsMapper.selectByPrimaryKey(goodsId);
        //活动创建
        HfActivityGroupExample hfActivityGroupExample = new HfActivityGroupExample();
        hfActivityGroupExample.createCriteria().andIdEqualTo(hfActivityGroupId).andIsDeletedEqualTo((byte) 0).andStateEqualTo(0);
        List<HfActivityGroup> hfActivityGroup = hfActivityGroupMapper.selectByExample(hfActivityGroupExample);

        //活动人数
        HfActivityCountExample hfActivityCountExample = new HfActivityCountExample();
        hfActivityCountExample.createCriteria().andGroupIdEqualTo(hfActivityGroupId).andIsDeletedEqualTo((byte) 0).andStateEqualTo(0);
        List<HfActivityCount> hfActivityCount = hfActivityCountMapper.selectByExample(hfActivityCountExample);

        HfActivityProductExample hfActivityProductExample = new HfActivityProductExample();
        hfActivityProductExample.createCriteria().andProductIdEqualTo(hfGoods.getProductId()).andActivityIdEqualTo(hfActivityGroup.get(0).getActivityId());
        List<HfActivityProduct> hfActivityProductList = hfActivityProductMapper.selectByExample(hfActivityProductExample);
        if (hfActivityProductList.get(0).getGroupNum() <= hfActivityCount.size()) {
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
        hfActivityCount1.setOrderId(orderId);
        hfActivityCountMapper.insert(hfActivityCount1);
//----
        HfActivityCountExample hfActivityCountExample3 = new HfActivityCountExample();
        hfActivityCountExample3.createCriteria().andGroupIdEqualTo(hfActivityGroupId).andIsDeletedEqualTo((byte) 0);
        List<HfActivityCount> hfActivityCountList= hfActivityCountMapper.selectByExample(hfActivityCountExample3);

        List<Map<String, String>> lists = new ArrayList<>();
        hfActivityCountList.forEach(hfActivityCount4 -> {
            Map<String,String> map = new HashMap<>();
            HfUsers hfUsers1 = hfUsersMapper.selectByPrimaryKey(hfActivityCount4.getUserId());
            map.put("userName", hfUsers1.getNickName());
            map.put("fileId", String.valueOf(hfUsers1.getFileId()));
            lists.add(map);
        });
        groupList.setUser(lists);
        //成团
        HfActivityGroupExample hfActivityGroupExample7 = new HfActivityGroupExample();
        hfActivityGroupExample7.createCriteria().andIdEqualTo(hfActivityGroupId).andIsDeletedEqualTo((byte) 0);
        List<HfActivityGroup> hfActivityGroup7 = hfActivityGroupMapper.selectByExample(hfActivityGroupExample7);
        //活动
        HfActivity hfActivity = hfActivityMapper.selectByPrimaryKey(hfActivityGroup7.get(0).getActivityId());
        //活动商品
        HfActivityProductExample hfActivityProductExample7 = new HfActivityProductExample();
        hfActivityProductExample7.createCriteria().andActivityIdEqualTo(hfActivityGroup7.get(0).getActivityId()).andProductIdEqualTo(hfActivityGroup7.get(0).getProductId());
        List<HfActivityProduct> hfActivityProduct = hfActivityProductMapper.selectByExample(hfActivityProductExample7);

        Date date1 = new Date();
        Date date4 = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            date1 = f.parse(f.format(new Date()));
            date4 = f.parse(f.format(hfActivityGroup7.get(0).getClusteringTime()));

        } catch (ParseException e) {
            e.printStackTrace();
        }
        HfUsers hfUsers = hfUsersMapper.selectByPrimaryKey(hfActivityGroup7.get(0).getUserId());
        Product product = productMapper.selectByPrimaryKey(hfActivityGroup7.get(0).getProductId());
        groupList.setGroupId(hfActivityGroupId);
        groupList.setGroupSum(hfActivityProduct.get(0).getGroupNum());
        groupList.setNowSum(hfActivityCountList.size());
        groupList.setTime(86400000 - (date1.getTime() - date4.getTime()));
        groupList.setGroupUserName(hfUsers.getNickName());
        groupList.setGroupFileId(hfUsers.getFileId());
        groupList.setProductId(product.getId());
        groupList.setProductName(product.getHfName());
        groupList.setProductFileId(product.getFileId());
        groupList.setSellPrice(price(product.getId()).get("sellPrice"));
        groupList.setLinePrice(price(product.getId()).get("linePrice"));
        groupLists.add(groupList);
        //----
        HfActivityCountExample hfActivityCountExample5 = new HfActivityCountExample();
        hfActivityCountExample5.createCriteria().andGroupIdEqualTo(hfActivityGroupId).andIsDeletedEqualTo((byte) 0).andStateEqualTo(0);
        List<HfActivityCount> hfActivityCount5 = hfActivityCountMapper.selectByExample(hfActivityCountExample);
        if (hfActivityProductList.get(0).getGroupNum() == hfActivityCount5.size()) {
            HfActivityGroup hfActivityGroup1 = new HfActivityGroup();
            hfActivityGroup1.setState(3);
            HfActivityGroupExample hfActivityGroupExample1 = new HfActivityGroupExample();
            hfActivityGroupExample1.createCriteria().andIdEqualTo(hfActivityGroupId).andIsDeletedEqualTo((byte) 0).andStateEqualTo(0);
            hfActivityGroupMapper.updateByExampleSelective(hfActivityGroup1,hfActivityGroupExample1);
            HfActivityCount hfActivityCount3 = new HfActivityCount();
            hfActivityCount3.setState(3);
            HfActivityCountExample hfActivityCountExample1 = new HfActivityCountExample();
            hfActivityCountExample1.createCriteria().andGroupIdEqualTo(hfActivityGroupId).andStateEqualTo(0).andIsDeletedEqualTo((byte) 0);
            hfActivityCountMapper.updateByExampleSelective(hfActivityCount3,hfActivityCountExample1);
            return builder.body(ResponseUtils.getResponseBody(groupLists));
        }
        HfActivityCountExample hfActivityCountExample1 = new HfActivityCountExample();
        hfActivityCountExample1.createCriteria().andGroupIdEqualTo(hfActivityGroupId).andIsDeletedEqualTo((byte) 0).andStateEqualTo(0);
        return builder.body(ResponseUtils.getResponseBody(groupLists));
    }

    @ApiOperation(value = "取消成团", notes = "取消成团")
    @RequestMapping(value = "/cancelGroup", method = RequestMethod.GET)
    public ResponseEntity<JSONObject> cancelGroup(Integer userId, Integer hfActivityGroupId) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HfActivityCount hfActivityCount = new HfActivityCount();
        hfActivityCount.setIsDeleted((byte) 1);
        HfActivityCountExample hfActivityCountExample2 = new HfActivityCountExample();
        hfActivityCountExample2.createCriteria().andGroupIdEqualTo(hfActivityGroupId).andIsDeletedEqualTo((byte) 0).andStateEqualTo(0).andUserIdEqualTo(userId);
        hfActivityCountMapper.updateByExampleSelective(hfActivityCount, hfActivityCountExample2);
        HfActivityCountExample hfActivityCountExample = new HfActivityCountExample();
        hfActivityCountExample.createCriteria().andGroupIdEqualTo(hfActivityGroupId).andIsDeletedEqualTo((byte) 0).andStateEqualTo(0);
        List<HfActivityCount> hfActivityCount3 = hfActivityCountMapper.selectByExample(hfActivityCountExample);
        if (hfActivityCount3.size() == 0) {
            HfActivityGroup hfActivityGroup = new HfActivityGroup();
            hfActivityGroup.setIsDeleted((byte) 1);
            HfActivityGroupExample hfActivityGroupExample = new HfActivityGroupExample();
            hfActivityGroupExample.createCriteria().andIdEqualTo(hfActivityGroupId).andIsDeletedEqualTo((byte) 0).andStateEqualTo(0);
            hfActivityGroupMapper.updateByExampleSelective(hfActivityGroup, hfActivityGroupExample);
            return builder.body(ResponseUtils.getResponseBody("无人参与团购，团购取消"));
        }
        return builder.body(ResponseUtils.getResponseBody(0));
    }

    @ApiOperation(value = "团购列表", notes = "团购列表")
    @RequestMapping(value = "/ListGroup", method = RequestMethod.GET)
    public ResponseEntity<JSONObject> ListGroup(Integer bossId, Integer groupId,Integer productId,Integer sum) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        List<HfGroup> hfGroupList = hfGroupDao.groupList(groupId);

        List<GroupList> groupLists = new ArrayList<>();
//		List<Map<String, String>> groupLists = new ArrayList<>();
        hfGroupList.forEach(hfGroup -> {
            GroupList groupList = new GroupList();
            //成团
            HfActivityGroupExample hfActivityGroupExample = new HfActivityGroupExample();
            hfActivityGroupExample.createCriteria().andIdEqualTo(hfGroup.getGroupId()).andIsDeletedEqualTo((byte) 0);
            List<HfActivityGroup> hfActivityGroup = hfActivityGroupMapper.selectByExample(hfActivityGroupExample);
            //活动
            HfActivity hfActivity = hfActivityMapper.selectByPrimaryKey(hfActivityGroup.get(0).getActivityId());
            System.out.println(hfActivity);
            //活动商品
            HfActivityProductExample hfActivityProductExample = new HfActivityProductExample();
            hfActivityProductExample.createCriteria().andActivityIdEqualTo(hfActivityGroup.get(0).getActivityId()).andProductIdEqualTo(hfActivityGroup.get(0).getProductId());
            List<HfActivityProduct> hfActivityProduct = hfActivityProductMapper.selectByExample(hfActivityProductExample);
            Product product = productMapper.selectByPrimaryKey(hfActivityGroup.get(0).getProductId());
            if (product.getId()==productId) {
                //时间
                Date date1 = new Date();
                Date date2 = new Date();
                Date date3 = new Date();
                Date date4 = new Date();
                SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                try {
                    date1 = f.parse(f.format(new Date())); //这是获取当前时间
                    date2 = f.parse(f.format(hfActivity.getStartTime()));
                    date3 = f.parse(f.format(hfActivity.getEndTime()));
                    date4 = f.parse(f.format(hfActivityGroup.get(0).getClusteringTime()));

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                long hours = (date1.getTime() - date4.getTime()) / (1000 * 60 * 60);
                if (date1.getTime() > date2.getTime() && date1.getTime() < date3.getTime() && hours < 24) {
//				long hours = (date3.getTime()-date1.getTime()) / (1000 * 60 * 60);
//				long minutes = ((date3.getTime()-date1.getTime())-hours*(1000 * 60 * 60 ))/(1000* 60);
                    String[] arr = hfGroup.getUserId().split(",");
                    HfUsers hfUsers = hfUsersMapper.selectByPrimaryKey(hfActivityGroup.get(0).getUserId());
                    List<String> list = Arrays.asList(arr);
                    //list
                    List<Map<String, String>> lists = new ArrayList<>();
                    Map<String, String> map = new HashMap<>();
//				map.put("groupSum", String.valueOf(hfActivityProduct.get(0).getGroupNum()));
//				map.put("nowSum", String.valueOf(list.size()));
                    //团购详情
                    if (groupId != null && hfGroup.getGroupId().equals(groupId)) {
//                    list.forEach(list1 -> {
                        for (int i = 0; i < arr.length; i++) {
                            HfUsers hfUsers1 = hfUsersMapper.selectByPrimaryKey(Integer.valueOf(arr[i]));
                            map.put("userName", hfUsers1.getNickName());
                            map.put("fileId", String.valueOf(hfUsers1.getFileId()));
                            lists.add(map);
                            groupList.setUser(lists);
                        }
                        groupList.setGroupId(hfGroup.getGroupId());
                        groupList.setGroupSum(hfActivityProduct.get(0).getGroupNum());
                        groupList.setNowSum(list.size());
                        groupList.setTime(86400000 - (date1.getTime() - date4.getTime()));
                        groupList.setGroupUserName(hfUsers.getNickName());
                        groupList.setGroupFileId(hfUsers.getFileId());
                        groupList.setProductId(product.getId());
                        groupList.setProductName(product.getHfName());
                        groupList.setProductFileId(product.getFileId());
                        groupList.setSellPrice(price(product.getId()).get("sellPrice"));
                        groupList.setLinePrice(price(product.getId()).get("linePrice"));
                        groupLists.add(groupList);
                    } else {
                        groupList.setGroupId(hfGroup.getGroupId());
                        groupList.setUser(lists);
                        groupList.setGroupSum(hfActivityProduct.get(0).getGroupNum());
                        groupList.setNowSum(list.size());
                        groupList.setTime(86400000 - (date1.getTime() - date4.getTime()));
                        groupList.setGroupUserName(hfUsers.getNickName());
                        groupList.setGroupFileId(hfUsers.getFileId());
                        groupLists.add(groupList);
                    }
//				map.put("userName", hfUsers.getNickName());
//				map.put("fileId", String.valueOf(hfUsers.getFileId()));
//				map.put("time", String.valueOf(date1.getTime()-date4.getTime()));
                }
            }
        });
        if (sum!=null){
            if (groupLists.size()==0){
                return builder.body(ResponseUtils.getResponseBody(groupLists));
            }
            if (groupLists.size()<2){
                return builder.body(ResponseUtils.getResponseBody(groupLists.subList(0,1)));
            }
            return builder.body(ResponseUtils.getResponseBody(groupLists.subList(0,sum)));
        }
        return builder.body(ResponseUtils.getResponseBody(groupLists));
    }

    private Map<String, Integer> price(Integer proudctId) {
        HfGoodsExample hfGoodsExample = new HfGoodsExample();
        hfGoodsExample.createCriteria().andProductIdEqualTo(proudctId);
        List<HfGoods> hfGoods = hfGoodsMapper.selectByExample(hfGoodsExample);
        List<Integer> sellPrice = new ArrayList<>();
        List<Integer> linePrice = new ArrayList<>();

        hfGoods.forEach(hfGoods1 -> {
            HfPrice hfPrice = hfPriceMapper.selectByPrimaryKey(hfGoods1.getPriceId());
            sellPrice.add(hfPrice.getSellPrice());
            linePrice.add(hfPrice.getLinePrice());
        });


        Map<String,Integer> map = new HashMap();
        map.put("sellPrice", Collections.min(sellPrice));
        map.put("linePrice", Collections.min(linePrice));
        return map;
    }

    @ApiOperation(value = "分销计算", notes = "分销计算")
    @RequestMapping(value = "/distributionActivityCalculate", method = RequestMethod.GET)
    public ResponseEntity<JSONObject> distributionActivityCalculate(Integer activityId, Integer orderId) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HfOrder hfOrder = hfOrderMapper.selectByPrimaryKey(orderId);
        //物品
        HfOrderDetailExample hfOrderDetailExample = new HfOrderDetailExample();
        hfOrderDetailExample.createCriteria().andOrderIdEqualTo(orderId);
        List<HfOrderDetail> hfOrderDetail = hfOrderDetailMapper.selectByExample(hfOrderDetailExample);
        Integer goodsId = hfOrderDetail.get(0).getGoodsId();
        Integer userId = hfOrder.getUserId();
        HfGoods hfGoods = hfGoodsMapper.selectByPrimaryKey(goodsId);
        //活动
        HfActivityProductExample hfActivityProductExample = new HfActivityProductExample();
        hfActivityProductExample.createCriteria().andProductIdEqualTo(hfGoods.getProductId()).andActivityIdEqualTo(activityId);
        List<HfActivityProduct> hfActivityProductList = hfActivityProductMapper.selectByExample(hfActivityProductExample);
        //转对象
        List<DistributionActivity> list = JSONArray.parseArray(hfActivityProductList.get(0).getDistributionRatio(), DistributionActivity.class);
        for (DistributionActivity lists : list) {
//		list.forEach(lists ->{
            //订单
            //分销上级用户
            //用户
            HfUsers hfUsers = hfUsersMapper.selectByPrimaryKey(userId);
            HfUsersExample hfUsersExample = new HfUsersExample();
            if (hfUsers.getInvitationCode() == null) {
                return builder.body(ResponseUtils.getResponseBody(0));
            }
            hfUsersExample.createCriteria().andOwnInvitationCodeEqualTo(hfUsers.getInvitationCode());
            List<HfUsers> hfUsers1 = hfUsersMapper.selectByExample(hfUsersExample);
            userId = hfUsers1.get(0).getId();
            HfUserBalanceExample hfUsersExample1 = new HfUserBalanceExample();
            hfUsersExample1.createCriteria().andUserIdEqualTo(hfUsers1.get(0).getId());
            List<HfUserBalance> hfUserBalances = hfUserBalanceMapper.selectByExample(hfUsersExample1);
//增加上级余额
            HfUserBalanceExample hfUsersExample2 = new HfUserBalanceExample();
            hfUsersExample2.createCriteria().andUserIdEqualTo(hfUsers1.get(0).getId());
            HfUserBalance hfUserBalance = new HfUserBalance();
            hfUserBalance.setHfBalance(hfUserBalances.get(0).getHfBalance() + ((hfOrder.getAmount() * lists.getRatio()) / 100));
            hfUserBalanceMapper.updateByExampleSelective(hfUserBalance, hfUsersExample2);
        }
        ;
        return builder.body(ResponseUtils.getResponseBody(0));
    }
    @ApiOperation(value = "拼团状态", notes = "拼团状态")
    @RequestMapping(value = "/groupStatus",method = RequestMethod.GET)

    public  ResponseEntity<JSONObject> groupStatus(Integer groupId,Integer userId) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HfActivityCountExample hfActivityCountExample2 = new HfActivityCountExample();
        hfActivityCountExample2.createCriteria().andGroupIdEqualTo(groupId).andIsDeletedEqualTo((byte) 0).andStateEqualTo(0).andUserIdEqualTo(userId);
        List<HfActivityCount> hfActivityCount2 = hfActivityCountMapper.selectByExample(hfActivityCountExample2);
        if (hfActivityCount2.size() != 0) {
            return builder.body(ResponseUtils.getResponseBody("-1"));
        }
        return builder.body(ResponseUtils.getResponseBody("0"));
    }


    @Scheduled(cron="0/5 * * * * ? ")
    @ApiOperation(value = "团购", notes = "团购")
    @RequestMapping(value = "/TimeGroup", method = RequestMethod.GET)
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "query", name = "productId", value = "商品id", required = true, type = "Integer") })
    public void TimeGroup()
            throws Exception {
//        logger.info(Thread.currentThread().getName() + " cron=* * * * * ? --- " + new Date());0
        HfActivityGroupExample hfActivityGroupExample = new HfActivityGroupExample();
        hfActivityGroupExample.createCriteria().andStateEqualTo(0).andIsDeletedEqualTo((byte) 0);
       List<HfActivityGroup> hfActivityGroupList = hfActivityGroupMapper.selectByExample(hfActivityGroupExample);
        hfActivityGroupList.forEach(discountCoupon -> {

            Date date1 = new Date();
            Date date2 = new Date();
            SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                date1 = f.parse(f.format(new Date())); //这是获取当前时间
                date2 = f.parse(f.format(discountCoupon.getClusteringTime()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if ((date1.getTime()-date2.getTime())>86400000){
                discountCoupon.setIsDeleted((byte) 1);
                hfActivityGroupMapper.updateByPrimaryKeySelective(discountCoupon);
                HfActivityCount hfActivityCount = new HfActivityCount();
                hfActivityCount.setIsDeleted((byte) 1);
                HfActivityCountExample hfActivityCountExample = new HfActivityCountExample();
                hfActivityCountExample.createCriteria().andGroupIdEqualTo(discountCoupon.getId());
                hfActivityCountMapper.updateByExampleSelective(hfActivityCount,hfActivityCountExample);
            }
        });
//        Random r = new Random();
        try{
            Thread.sleep(2000);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
