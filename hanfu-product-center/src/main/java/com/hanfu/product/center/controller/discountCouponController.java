package com.hanfu.product.center.controller;

import com.alibaba.fastjson.JSONObject;
import com.hanfu.common.service.FileMangeService;
import com.hanfu.product.center.dao.DiscountCouponMapper;
import com.hanfu.product.center.dao.FileDescMapper;
import com.hanfu.product.center.manual.model.DiscountCouponScope;
import com.hanfu.product.center.model.DiscountCoupon;
import com.hanfu.product.center.model.DiscountCouponExample;
import com.hanfu.product.center.model.FileDesc;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

@CrossOrigin
@RestController
@RequestMapping("/discountCoupon")
@Api
public class discountCouponController {
@Autowired
private DiscountCouponMapper discountCouponMapper;
@Autowired
private FileDescMapper fileDescMapper;
    @ApiOperation(value = "添加优惠券", notes = "添加优惠券")
    @RequestMapping(value = "/addDiscountCoupon", method = RequestMethod.POST)
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "query", name = "productId", value = "商品id", required = true, type = "Integer") })
    public ResponseEntity<JSONObject> getGoodsSpecs(Date startTime,Date stopTime,DiscountCoupon discountCoupon, MultipartFile fileInfo)
            throws Exception {
        DiscountCouponScope.ScopeTypeEnum scopeTypeEnum = DiscountCouponScope.ScopeTypeEnum.getOrderTypeEnum(discountCoupon.getScope());
        System.out.println(scopeTypeEnum);
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        String arr[];
        FileDesc fileDesc = new FileDesc();
        if (fileInfo!=null){
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
        params.put("discountCouponId",discountCoupon.getId());
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
    public ResponseEntity<JSONObject> getGoodsSpecs(Integer bossId)
            throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        DiscountCouponExample discountCouponExample = new DiscountCouponExample();
        discountCouponExample.createCriteria().andIdDeletedEqualTo((byte) 0).andBossIdEqualTo(bossId);
        List<DiscountCoupon> discountCoupons= discountCouponMapper.selectByExample(discountCouponExample);
        discountCoupons.forEach(discountCoupon -> {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date date1 = df.parse(df.format(discountCoupon.getStartTime()));
                Date date2 = df.parse(df.format(discountCoupon.getStopTime()));
                Date date3 = df.parse(df.format(new Date()));
                System.out.println("---1---"+date1+"---2---"+date2+"----3---"+date3);
                // 得到微秒级别的差值
                long diff = date3.getTime() - date1.getTime();
                long diff1= date2.getTime() - date3.getTime();
                // 将级别提升到天
                long days = diff /(1000 * 60); //当前时间减去开始时间
                long days2 = diff1 /(1000 * 60);//结束时间减去当前时间
                System.out.println(days+"相减时间");
                System.out.println(days2+"相减时间2");
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });

        return builder.body(ResponseUtils.getResponseBody(discountCoupons));
    }


    @ApiOperation(value = "编辑优惠券", notes = "编辑优惠券")
    @RequestMapping(value = "/updateDiscountCoupon", method = RequestMethod.POST)
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "query", name = "productId", value = "商品id", required = true, type = "Integer") })
    public ResponseEntity<JSONObject> updateDiscountCoupon(Date startTime,Date stopTime,DiscountCoupon discountCoupon, MultipartFile fileInfo)
            throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        String arr[];
        arr = FileMangeService.uploadFile(fileInfo.getBytes(), String.valueOf(discountCoupon.getCreator()));
        FileDesc fileDesc = new FileDesc();
        fileDesc.setFileName(fileInfo.getName());
        fileDesc.setGroupName(arr[0]);
        fileDesc.setRemoteFilename(arr[1]);
        fileDesc.setUserId(discountCoupon.getCreator());
        fileDesc.setCreateTime(LocalDateTime.now());
        fileDesc.setModifyTime(LocalDateTime.now());
        fileDescMapper.insertSelective(fileDesc);

        discountCoupon.setCreateDate(LocalDateTime.now());
        discountCoupon.setModifyDate(LocalDateTime.now());
        discountCoupon.setIdDeleted((byte) 0);
        discountCoupon.setFileId(fileDesc.getId());
        discountCoupon.setStartTime(startTime);
        discountCoupon.setStopTime(stopTime);
        discountCouponMapper.updateByPrimaryKeySelective(discountCoupon);
        Map<String, Integer> params = new HashMap<>();
        params.put("fileId", fileDesc.getId());
        params.put("discountCouponId",discountCoupon.getId());
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
        return builder.body(ResponseUtils.getResponseBody(discountCouponMapper.updateByExampleSelective(discountCoupon,discountCouponExample)));
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
        params.put("name","全部用户");
        params.put("code", "allUser");
        Map<String, String> params1 = new HashMap<>();
        params1.put("name","会员用户");
        params1.put("code","vipUser");
        Scope.add(params);
        Scope.add(params1);
        return builder.body(ResponseUtils.getResponseBody(Scope));
    }
}
