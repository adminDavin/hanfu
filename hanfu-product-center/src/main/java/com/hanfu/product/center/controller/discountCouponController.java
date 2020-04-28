package com.hanfu.product.center.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hanfu.common.service.FileMangeService;
import com.hanfu.product.center.dao.*;
import com.hanfu.product.center.manual.dao.HfMemberDao;
import com.hanfu.product.center.manual.model.BalanceType.BalanceTypeEnum;
import com.hanfu.product.center.manual.model.DiscountCouponScope;
import com.hanfu.product.center.manual.model.GoodsList;
import com.hanfu.product.center.model.*;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/discountCoupon")
@Api
public class discountCouponController {
    @Autowired
    private DiscountCouponMapper discountCouponMapper;
    @Autowired
    private FileDescMapper fileDescMapper;
    @Autowired
    private HfUserCouponsMapper hfUserCouponsMapper;
    @Autowired
    private HfUserBalanceMapper hfUserBalanceMapper;
    @Autowired
    private HfMemberDao hfMemberDao;
    @Autowired
    private HfGoodsMapper hfGoodsMapper;
    @Autowired
    private HfPriceMapper hfPriceMapper;
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ApiOperation(value = "添加优惠券", notes = "添加优惠券")
    @RequestMapping(value = "/addDiscountCoupon", method = RequestMethod.POST)
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "query", name = "productId", value = "商品id", required = true, type = "Integer") })
    public ResponseEntity<JSONObject> getGoodsSpecs(Date startTime, Date stopTime, DiscountCoupon discountCoupon, MultipartFile fileInfo)
            throws Exception {
        DiscountCouponScope.ScopeTypeEnum scopeTypeEnum = DiscountCouponScope.ScopeTypeEnum.getOrderTypeEnum(discountCoupon.getScope());
        System.out.println(scopeTypeEnum);
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        String arr[];
        FileDesc fileDesc = new FileDesc();
        if (fileInfo != null) {
            arr = FileMangeService.uploadFile(fileInfo.getBytes(), String.valueOf(discountCoupon.getCreator()));
            fileDesc.setFileName(fileInfo.getName());
            fileDesc.setGroupName(arr[0]);
            fileDesc.setRemoteFilename(arr[1]);
            fileDesc.setUserId(discountCoupon.getCreator());
            fileDesc.setCreateTime(LocalDateTime.now());
            fileDesc.setModifyTime(LocalDateTime.now());
            fileDescMapper.insertSelective(fileDesc);
        }

        discountCoupon.setCreateDate(LocalDateTime.now());
        discountCoupon.setModifyDate(LocalDateTime.now());
        discountCoupon.setIdDeleted((byte) 0);
        discountCoupon.setFileId(fileDesc.getId());
        discountCoupon.setUseState(0);
        discountCoupon.setStartTime(startTime);
        discountCoupon.setStopTime(stopTime);
        discountCoupon.setScope(scopeTypeEnum.getOrderType());
        discountCouponMapper.insertSelective(discountCoupon);
        Map<String, Integer> params = new HashMap<>();
        params.put("fileId", fileDesc.getId());
        params.put("discountCouponId", discountCoupon.getId());
        return builder.body(ResponseUtils.getResponseBody(params));
    }

    //转换时间格式
    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request) {
        //转换日期
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));// CustomDateEditor为自定义日期编辑器
    }

    @ApiOperation(value = "优惠券列表", notes = "优惠券列表")
    @RequestMapping(value = "/selectDiscountCoupon", method = RequestMethod.GET)
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "query", name = "productId", value = "商品id", required = true, type = "Integer") })
    public ResponseEntity<JSONObject> getGoodsSpecs(Integer bossId,String DiscountCouponName,String DiscountCouponType)
            throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        DiscountCouponExample discountCouponExample = new DiscountCouponExample();
        discountCouponExample.createCriteria().andIdDeletedEqualTo((byte) 0).andBossIdEqualTo(bossId);
        discountCouponExample.setOrderByClause("use_state ASC,'modify_time' DESC");
        List<DiscountCoupon> discountCoupons = discountCouponMapper.selectByExample(discountCouponExample);
        if (null != DiscountCouponName){
            discountCoupons = discountCoupons.stream().filter(discountCoupon-> discountCoupon.getDiscountCouponName().contains(DiscountCouponName)).collect(Collectors.toList());
        }
        if (null!=DiscountCouponType){
            discountCoupons = discountCoupons.stream().filter(discountCoupon -> discountCoupon.getDiscountCouponType().equals(DiscountCouponType)).collect(Collectors.toList());
        }
        discountCoupons.forEach(discountCoupon -> {
            Date date1 = new Date();
            Date date2 = new Date();
            Date date3 = new Date();
            SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                date1 = f.parse(f.format(new Date())); //这是获取当前时间
                date2 = f.parse(f.format(discountCoupon.getStartTime()));
                date3 = f.parse(f.format(discountCoupon.getStopTime()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (date1.getTime() > date3.getTime()) {
                discountCoupon.setUseState(1);
                discountCouponMapper.updateByPrimaryKeySelective(discountCoupon);
            }
            if (date1.getTime() < date2.getTime()) {
                discountCoupon.setUseState(-1);
                discountCouponMapper.updateByPrimaryKeySelective(discountCoupon);
            }
            if (date2.getTime() < date1.getTime() && date1.getTime() < date3.getTime()) {
                discountCoupon.setUseState(0);
                discountCouponMapper.updateByPrimaryKeySelective(discountCoupon);
            }
        });

        return builder.body(ResponseUtils.getResponseBody(discountCoupons));
    }


    @ApiOperation(value = "编辑优惠券", notes = "编辑优惠券")
    @RequestMapping(value = "/updateDiscountCoupon", method = RequestMethod.POST)
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "query", name = "productId", value = "商品id", required = true, type = "Integer") })
    public ResponseEntity<JSONObject> updateDiscountCoupon(Date startTime, Date stopTime, DiscountCoupon discountCoupon, MultipartFile fileInfo)
            throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        String arr[];
        FileDesc fileDesc = new FileDesc();
        if (fileInfo != null) {
            arr = FileMangeService.uploadFile(fileInfo.getBytes(), String.valueOf(discountCoupon.getCreator()));
            fileDesc.setFileName(fileInfo.getName());
            fileDesc.setGroupName(arr[0]);
            fileDesc.setRemoteFilename(arr[1]);
            fileDesc.setUserId(discountCoupon.getCreator());
            fileDesc.setCreateTime(LocalDateTime.now());
            fileDesc.setModifyTime(LocalDateTime.now());
            fileDescMapper.insertSelective(fileDesc);
        }
        discountCoupon.setCreateDate(LocalDateTime.now());
        discountCoupon.setModifyDate(LocalDateTime.now());
        discountCoupon.setIdDeleted((byte) 0);
        discountCoupon.setFileId(fileDesc.getId());
        discountCoupon.setStartTime(startTime);
        discountCoupon.setStopTime(stopTime);
        discountCouponMapper.updateByPrimaryKeySelective(discountCoupon);
        Map<String, Integer> params = new HashMap<>();
        params.put("fileId", fileDesc.getId());
        params.put("discountCouponId", discountCoupon.getId());
        return builder.body(ResponseUtils.getResponseBody(params));
    }

    @ApiOperation(value = "删除优惠券", notes = "删除优惠券")
    @RequestMapping(value = "/deletedDiscountCoupon", method = RequestMethod.GET)
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "query", name = "Id", value = "优惠券id", required = true, type = "Integer") })
    public ResponseEntity<JSONObject> deletedDiscountCoupon(@RequestParam(name = "Id") Integer Id)
            throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        DiscountCoupon discountCoupon = new DiscountCoupon();
        discountCoupon.setIdDeleted((byte) 1);
        DiscountCouponExample discountCouponExample = new DiscountCouponExample();
        discountCouponExample.createCriteria().andIdDeletedEqualTo((byte) 0).andIdEqualTo(Id);
        return builder.body(ResponseUtils.getResponseBody(discountCouponMapper.updateByExampleSelective(discountCoupon, discountCouponExample)));
    }

    @ApiOperation(value = "使用范围", notes = "使用范围")
    @RequestMapping(value = "/selectScope", method = RequestMethod.GET)
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "query", name = "productId", value = "商品id", required = true, type = "Integer") })
    public ResponseEntity<JSONObject> selectScope()
            throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);

        List<Map<String, String>> Scope = new ArrayList<>();

        Map<String, String> params = new HashMap<>();
        params.put("name", "全部用户");
        params.put("code", "allUser");
        Map<String, String> params1 = new HashMap<>();
        params1.put("name", "会员用户");
        params1.put("code", "vipUser");
        Scope.add(params);
        Scope.add(params1);
        return builder.body(ResponseUtils.getResponseBody(Scope));
    }

    @ApiOperation(value = "优惠券图片", notes = "优惠券图片")
    @RequestMapping(value = "/discountCouponMap", method = RequestMethod.POST)
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "query", name = "productId", value = "商品id", required = true, type = "Integer") })
    public ResponseEntity<JSONObject> discountCouponMap(MultipartFile fileInfo, Integer discountCouponId)
            throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        String arr[];
        FileDesc fileDesc = new FileDesc();
        if (fileInfo != null) {
            arr = FileMangeService.uploadFile(fileInfo.getBytes(), String.valueOf(discountCouponId));
            fileDesc.setFileName(fileInfo.getName());
            fileDesc.setGroupName(arr[0]);
            fileDesc.setRemoteFilename(arr[1]);
            fileDesc.setUserId(discountCouponId);
            fileDesc.setCreateTime(LocalDateTime.now());
            fileDesc.setModifyTime(LocalDateTime.now());
            fileDescMapper.insertSelective(fileDesc);
        }
        DiscountCoupon discountCoupon = new DiscountCoupon();
        discountCoupon.setId(discountCouponId);
        discountCoupon.setFileId(fileDesc.getId());
        discountCouponMapper.updateByPrimaryKeySelective(discountCoupon);
        return builder.body(ResponseUtils.getResponseBody(fileDesc.getId()));
    }

    @ApiOperation(value = "删除优惠券图片", notes = "删除优惠券图片")
    @RequestMapping(value = "/deletedMap", method = RequestMethod.POST)
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "query", name = "productId", value = "商品id", required = true, type = "Integer") })
    public ResponseEntity<JSONObject> deletedMap(Integer discountCouponId, Integer fileId)
            throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        FileDesc fileDesc = fileDescMapper.selectByPrimaryKey(fileId);
        FileMangeService fileManageService = new FileMangeService();
        if (fileDesc != null) {
            fileManageService.deleteFile(fileDesc.getGroupName(), fileDesc.getRemoteFilename());
            fileDescMapper.deleteByPrimaryKey(fileDesc.getId());
        }
        return builder.body(ResponseUtils.getResponseBody(0));
    }

    @Scheduled(cron = "0/5 * * * * ? ")
    @ApiOperation(value = "优惠券", notes = "优惠券")
    @RequestMapping(value = "/TimeDiscountCoupon", method = RequestMethod.GET)
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "query", name = "productId", value = "商品id", required = true, type = "Integer") })
    public void TimeDiscountCoupon()
            throws Exception {
//        logger.info(Thread.currentThread().getName() + " cron=* * * * * ? --- " + new Date());
        DiscountCouponExample discountCouponExample = new DiscountCouponExample();
        discountCouponExample.createCriteria().andIdDeletedEqualTo((byte) 0);
        List<DiscountCoupon> discountCoupons = discountCouponMapper.selectByExample(discountCouponExample);
        discountCoupons.forEach(discountCoupon -> {
            Date date1 = new Date();
            Date date2 = new Date();
            Date date3 = new Date();
            SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                date1 = f.parse(f.format(new Date())); //这是获取当前时间
                date2 = f.parse(f.format(discountCoupon.getStartTime()));
                date3 = f.parse(f.format(discountCoupon.getStopTime()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (date1.getTime() > date3.getTime()) {
                discountCoupon.setUseState(1);
                discountCouponMapper.updateByPrimaryKeySelective(discountCoupon);
            }
            if (date1.getTime() < date2.getTime()) {
                discountCoupon.setUseState(-1);
                discountCouponMapper.updateByPrimaryKeySelective(discountCoupon);
            }
            if (date2.getTime() < date1.getTime() && date1.getTime() < date3.getTime()) {
                discountCoupon.setUseState(0);
                discountCouponMapper.updateByPrimaryKeySelective(discountCoupon);
            }
        });
//        Random r = new Random();
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ApiOperation(value = "领券大厅", notes = "领券大厅")
    @RequestMapping(value = "/couponHall", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "scope", value = "优惠券范围", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户id", required = true, type = "Integer")})
    public ResponseEntity<JSONObject> couponHall(Integer scope, Integer userId)
            throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HfUserCouponsExample example = new HfUserCouponsExample();
        List<DiscountCoupon> list = new ArrayList<DiscountCoupon>();
        DiscountCouponExample coupons = new DiscountCouponExample();
        if (scope == 0) {
            coupons.createCriteria().andScopeEqualTo("allUser").andIdDeletedEqualTo((byte) 0)
                    .andStopTimeGreaterThanOrEqualTo(LocalDateTime.now());
            list = discountCouponMapper.selectByExample(coupons);
            for (int i = 0; i < list.size(); i++) {
                List<HfUserCoupons> userCoupons = new ArrayList<HfUserCoupons>();
                example.clear();
                example.createCriteria().andUserIdEqualTo(userId).andCouponsIdEqualTo(list.get(i).getId());
                userCoupons = hfUserCouponsMapper.selectByExample(example);
                if (userCoupons.isEmpty()) {
                    list.get(i).setUseState(1);
                } else {
                    list.get(i).setUseState(-1);
                }
            }
        }
        if (scope == 1) {
            coupons.createCriteria().andScopeEqualTo("vipUser").andIdDeletedEqualTo((byte) 0)
                    .andStopTimeGreaterThanOrEqualTo(LocalDateTime.now());
            list = discountCouponMapper.selectByExample(coupons);
            for (int i = 0; i < list.size(); i++) {
                List<HfUserCoupons> userCoupons = new ArrayList<HfUserCoupons>();
                example.clear();
                example.createCriteria().andUserIdEqualTo(userId).andCouponsIdEqualTo(list.get(i).getId());
                userCoupons = hfUserCouponsMapper.selectByExample(example);
                if (userCoupons.isEmpty()) {
                    list.get(i).setUseState(1);
                } else {
                    list.get(i).setUseState(-1);
                }
            }
        }
        return builder.body(ResponseUtils.getResponseBody(list));
    }


    @ApiOperation(value = "我的优惠券", notes = "我的优惠券")
    @RequestMapping(value = "/couponMy", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "state", value = "优惠券状态", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户id", required = true, type = "Integer")})
    public ResponseEntity<JSONObject> couponMy(Integer state, Integer userId, Integer goodsId ,Integer GoodsNum, String goodsList)
            throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);

        HfUserCouponsExample userCouponsExample = new HfUserCouponsExample();
        userCouponsExample.createCriteria().andUserIdEqualTo(userId);
        List<HfUserCoupons> coupons = hfUserCouponsMapper.selectByExample(userCouponsExample);
        List<Integer> couponId = new ArrayList<Integer>();
        List<DiscountCoupon> list = new ArrayList<DiscountCoupon>();
        if (!coupons.isEmpty()) {
            DiscountCouponExample couponExample = new DiscountCouponExample();
            if (state == 0) {
            	userCouponsExample.clear();
            	userCouponsExample.createCriteria().andUserIdEqualTo(userId).andIdDeletedEqualTo((byte) 0);
            	coupons = hfUserCouponsMapper.selectByExample(userCouponsExample);
            	couponId = coupons.stream().map(HfUserCoupons::getCouponsId).collect(Collectors.toList());
                couponExample.createCriteria().andIdIn(couponId).andIdDeletedEqualTo((byte) 0)
                        .andStopTimeGreaterThan(LocalDateTime.now());
                list = discountCouponMapper.selectByExample(couponExample);
                if (goodsList!=null){
                    JSONArray jsonArray= JSONArray.parseArray(goodsList);
                    List<GoodsList> lists = JSONObject.parseArray(jsonArray.toJSONString(), GoodsList.class);
                    Integer moneys = 0;
                    for (GoodsList goodsList1:lists){
                        moneys= hfPriceMapper.selectByPrimaryKey(hfGoodsMapper.selectByPrimaryKey(goodsList1.getGoodsId()).getPriceId()).getSellPrice()*goodsList1.getQuantity()+moneys;
                    }

//                    list.forEach(list1 -> {
                        for (DiscountCoupon list1:list){
                        JSONObject specs = JSONObject.parseObject(list1.getUseLimit());
                        Iterator<String> iterator = specs.keySet().iterator();
                        while (iterator.hasNext()) {
// 获得key
                            String key = iterator.next();
                            String value = specs.getString(key);
                            if (key.equals("full")){
//                                System.out.println(value);
                                if (Integer.valueOf(value)>moneys){
//                                    System.out.println("-----------"+hfPriceMapper.selectByPrimaryKey(hfGoodsMapper.selectByPrimaryKey(goodsId).getPriceId()).getSellPrice());
                                    list1.setIdDeleted((byte) 1);
                                }
                            }
                        }

                    }
                }
                if (goodsId != null) {
                    list.forEach(lists -> {
                        JSONObject specs = JSONObject.parseObject(lists.getUseLimit());
                        Iterator<String> iterator = specs.keySet().iterator();
                        while (iterator.hasNext()) {
// 获得key
                            String key = iterator.next();
                            String value = specs.getString(key);
                            if (key.equals("full")){
//                                System.out.println(value);
                                if (Integer.valueOf(value)>hfPriceMapper.selectByPrimaryKey(hfGoodsMapper.selectByPrimaryKey(goodsId).getPriceId()).getSellPrice()*GoodsNum){
//                                    System.out.println("-----------"+hfPriceMapper.selectByPrimaryKey(hfGoodsMapper.selectByPrimaryKey(goodsId).getPriceId()).getSellPrice());
                                    lists.setIdDeleted((byte) 1);
                                }
                            }
                        }

                    });
                }
            }
            if (state == 1) {
                userCouponsExample.clear();
                userCouponsExample.createCriteria().andUserIdEqualTo(userId).andIdDeletedEqualTo((byte) 1);
                coupons = hfUserCouponsMapper.selectByExample(userCouponsExample);
                if (!coupons.isEmpty()) {
                    couponId = coupons.stream().map(HfUserCoupons::getCouponsId).collect(Collectors.toList());
                    couponExample.createCriteria().andIdIn(couponId).andStopTimeGreaterThan(LocalDateTime.now());
                    list = discountCouponMapper.selectByExample(couponExample);
                }
            }
            if (state == 2) {
            	couponId = coupons.stream().map(HfUserCoupons::getCouponsId).collect(Collectors.toList());
                couponExample.createCriteria().andIdIn(couponId)
                        .andStopTimeLessThan(LocalDateTime.now());
                list = discountCouponMapper.selectByExample(couponExample);
            }
        }
        return builder.body(ResponseUtils.getResponseBody(list));
    }

    @ApiOperation(value = "用户领取优惠券", notes = "用户领取优惠券")
    @RequestMapping(value = "/addCouponForUser", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "couponId", value = "优惠券id", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户id", required = true, type = "Integer")})
    public ResponseEntity<JSONObject> addCouponForUser(Integer couponId, Integer userId)
            throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);

        Integer result = hfMemberDao.selectIsMember(userId);
        DiscountCoupon discountCoupon = discountCouponMapper.selectByPrimaryKey(couponId);
        if (result == 0 && "vipUser".equals(discountCoupon.getScope())) {
            return builder.body(ResponseUtils.getResponseBody(-1));
        }

        HfUserBalance balance = null;

        HfUserCoupons coupons = new HfUserCoupons();
        coupons.setCouponsId(couponId);
        coupons.setUserId(userId);
        coupons.setIdDeleted((byte) 0);
        coupons.setCreateDate(LocalDateTime.now());
        coupons.setModifyDate(LocalDateTime.now());
        hfUserCouponsMapper.insert(coupons);

        HfUserBalanceExample example = new HfUserBalanceExample();
        example.createCriteria().andBalanceTypeEqualTo(BalanceTypeEnum.getBalanceType("DISCOUNT_COUPON"))
                .andUserIdEqualTo(userId);

        List<HfUserBalance> hfBalance = hfUserBalanceMapper.selectByExample(example);

        if (hfBalance.isEmpty()) {
            balance = new HfUserBalance();
            balance.setBalanceType(BalanceTypeEnum.getBalanceType("DISCOUNT_COUPON"));
            balance.setUserId(userId);
            balance.setHfBalance(1);
            balance.setCreateTime(LocalDateTime.now());
            balance.setModifyTime(LocalDateTime.now());
            balance.setIsDeleted((short) 0);
            hfUserBalanceMapper.insert(balance);
        } else {
            balance = hfBalance.get(0);
            balance.setHfBalance(balance.getHfBalance() + 1);
            balance.setModifyTime(LocalDateTime.now());
            hfUserBalanceMapper.updateByPrimaryKey(balance);
        }
        return builder.body(ResponseUtils.getResponseBody(coupons.getId()));
    }
    @ApiOperation(value = "使用", notes = "使用")
    @RequestMapping(value = "/useDis", method = RequestMethod.GET)
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "query", name = "productId", value = "商品id", required = true, type = "Integer") })
    public ResponseEntity<JSONObject> useDis(Integer discountCouponId, Integer userId)
            throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HfUserCoupons coupons = new HfUserCoupons();
        coupons.setIdDeleted((byte) 1);
        coupons.setModifyDate(LocalDateTime.now());
        HfUserCouponsExample userCouponsExample = new HfUserCouponsExample();
        userCouponsExample.createCriteria().andUserIdEqualTo(userId).andCouponsIdEqualTo(discountCouponId);
        hfUserCouponsMapper.updateByExampleSelective(coupons,userCouponsExample);
        HfUserBalanceExample balanceExample = new HfUserBalanceExample();
        balanceExample.createCriteria().andBalanceTypeEqualTo("discountCoupon").andUserIdEqualTo(userId);
        List<HfUserBalance> balances = hfUserBalanceMapper.selectByExample(balanceExample);
        if(balances.isEmpty()) {
        	return builder.body(ResponseUtils.getResponseBody("数据异常"));
        }
        HfUserBalance balance = balances.get(0);
        if(balance.getHfBalance() -1 < 0) {
        	return builder.body(ResponseUtils.getResponseBody("数据异常"));
        }
        balance.setHfBalance(balance.getHfBalance()-1);
        hfUserBalanceMapper.updateByPrimaryKey(balance);
        return builder.body(ResponseUtils.getResponseBody(0));
    }
}
