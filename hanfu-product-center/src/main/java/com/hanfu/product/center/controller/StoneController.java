package com.hanfu.product.center.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.hanfu.common.service.FileMangeService;
import com.hanfu.product.center.dao.FileDescMapper;
import com.hanfu.product.center.dao.HfGoodsMapper;
import com.hanfu.product.center.dao.HfGoodsPictrueMapper;
import com.hanfu.product.center.dao.HfGoodsSpecMapper;
import com.hanfu.product.center.dao.HfOrderMapper;
import com.hanfu.product.center.dao.HfPriceMapper;
import com.hanfu.product.center.dao.HfRespMapper;
import com.hanfu.product.center.dao.HfStoneMapper;
import com.hanfu.product.center.dao.HfStonePictureMapper;
import com.hanfu.product.center.dao.HfUserBrowseRecordMapper;
import com.hanfu.product.center.dao.ProductInstanceMapper;
import com.hanfu.product.center.manual.model.HomePageInfo;
import com.hanfu.product.center.model.EvluateInstancePicture;
import com.hanfu.product.center.model.FileDesc;
import com.hanfu.product.center.model.HfGoods;
import com.hanfu.product.center.model.HfGoodsExample;
import com.hanfu.product.center.model.HfGoodsPictrue;
import com.hanfu.product.center.model.HfGoodsPictrueExample;
import com.hanfu.product.center.model.HfGoodsSpecExample;
import com.hanfu.product.center.model.HfOrder;
import com.hanfu.product.center.model.HfOrderExample;
import com.hanfu.product.center.model.HfPriceExample;
import com.hanfu.product.center.model.HfRespExample;
import com.hanfu.product.center.model.HfStone;
import com.hanfu.product.center.model.HfStoneExample;
import com.hanfu.product.center.model.HfStonePicture;
import com.hanfu.product.center.model.HfStonePictureExample;
import com.hanfu.product.center.model.HfUserBrowseRecord;
import com.hanfu.product.center.model.HfUserBrowseRecordExample;
import com.hanfu.product.center.request.HfStoneRequest;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseEntity.BodyBuilder;
import com.hanfu.utils.response.handler.ResponseUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/stone")
@Api
public class StoneController {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private HfStoneMapper hfStoneMapper;

    @Autowired
    private HfGoodsMapper hfGoodsMapper;

    @Autowired
    private ProductInstanceMapper productInstanceMapper;

    @Autowired
    private HfGoodsSpecMapper hfGoodsSpecMapper;

    @Autowired
    private HfGoodsPictrueMapper hfGoodsPictrueMapper;

    @Autowired
    private FileDescMapper fileDescMapper;

    @Autowired
    private HfRespMapper hfRespMapper;

    @Autowired
    private HfPriceMapper hfPriceMapper;
    
    @Autowired
    private HfStonePictureMapper hfStonePictureMapper;
    
    @Autowired
    private HfOrderMapper hfOrderMapper;
    
    @Autowired
    private HfUserBrowseRecordMapper hfUserBrowseRecordMapper;
    
    @ApiOperation(value = "获取店铺列表", notes = "根据商家或缺店铺列表")
    @RequestMapping(value = "/byBossId", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "bossId", value = "商家ID", required = true, type = "Integer")})
    public ResponseEntity<JSONObject> listStone(@RequestParam(name = "bossId") Integer bossId) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HfStoneExample example = new HfStoneExample();
        example.createCriteria().andBossIdEqualTo(bossId);
        return builder.body(ResponseUtils.getResponseBody(hfStoneMapper.selectByExample(example)));
    }
    
    @ApiOperation(value = "获取店铺图片", notes = "获取店铺图片")
    @RequestMapping(value = "/getStonePicture", method = RequestMethod.GET)
    public ResponseEntity<JSONObject> getStonePicture(Integer stoneId) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HfStonePictureExample example = new HfStonePictureExample();
        example.createCriteria().andStoneIdEqualTo(stoneId);
        List<HfStonePicture> list = hfStonePictureMapper.selectByExample(example);
        return builder.body(ResponseUtils.getResponseBody(list));
    }

    @ApiOperation(value = "添加商铺", notes = "添加一个新的商铺")
    @RequestMapping(value = "/addStone", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> addProductInfo(HfStoneRequest request) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HfStone item = new HfStone();
        item.setHfName(request.getHfName());
        item.setBossId(request.getBossId());
        item.setHfDesc(request.getHfDesc());
        item.setHfStatus(request.getHfStatus());
        item.setUserId(request.getUserId());
        item.setConcernCount(0);
        item.setCreateTime(LocalDateTime.now());
        LocalDateTime expireTime = LocalDateTime.now();
        expireTime.plusYears(10);
        item.setExpireTime(expireTime);
        item.setIsDeleted((short) 0);
        item.setAddress(request.getAddress());
        return builder.body(ResponseUtils.getResponseBody(hfStoneMapper.insert(item)));
    }
    
    @ApiOperation(value = "添加商铺图片", notes = "添加商铺图片")
    @RequestMapping(value = "/addStonePicture", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> addStonePicture(Integer stoneId,@RequestPart(required = false) MultipartFile[] file) throws JSONException, IOException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HfStone stone = hfStoneMapper.selectByPrimaryKey(stoneId);
        for(MultipartFile f:file) {
			String arr[];
			FileMangeService fileMangeService = new FileMangeService();
			arr = fileMangeService.uploadFile(f.getBytes(), String.valueOf(0));
			FileDesc fileDesc = new FileDesc();
			fileDesc.setFileName(f.getName());
			fileDesc.setGroupName(arr[0]);
			fileDesc.setRemoteFilename(arr[1]);
			fileDesc.setCreateTime(LocalDateTime.now());
			fileDesc.setModifyTime(LocalDateTime.now());
			fileDesc.setIsDeleted((short) 0);
			fileDescMapper.insert(fileDesc);
			HfStonePicture picture = new HfStonePicture();
			picture.setStoneId(stoneId);
			picture.setFileId(fileDesc.getId());
			picture.setHfName("店铺图片");
			picture.setHfDesc("店铺图片描述");
			picture.setCreateTime(LocalDateTime.now());
			picture.setModifyTime(LocalDateTime.now());
			picture.setIsDeleted((byte) 0);
			hfStonePictureMapper.insert(picture);
		}
        return builder.body(ResponseUtils.getResponseBody(stone.getId()));
    }

    @ApiOperation(value = "删除商铺", notes = "删除商铺")
    @RequestMapping(value = "/deleteStone", method = RequestMethod.GET)
    public ResponseEntity<JSONObject> deleteStone(Integer stoneId) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HfGoodsExample hfGoodsExample = new HfGoodsExample();
        hfGoodsExample.createCriteria().andStoneIdEqualTo(stoneId);
        List<HfGoods> hfGoods = hfGoodsMapper.selectByExample(hfGoodsExample);
        for (int i = 0; i < hfGoods.size(); i++) {
            deleteStoneGoods(hfGoods.get(i).getId());
        }
        return builder.body(ResponseUtils.getResponseBody(hfStoneMapper.deleteByPrimaryKey(stoneId)));
    }

    @ApiOperation(value = "删除店铺内的物品", notes = "将店铺内的一个物品删除")
    @RequestMapping(value = "/deleteGood", method = RequestMethod.GET)
    public ResponseEntity<JSONObject> deleteStoneGoods(Integer hfGoodsId) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HfGoods hfGoods = hfGoodsMapper.selectByPrimaryKey(hfGoodsId);
        productInstanceMapper.deleteByPrimaryKey(hfGoods.getInstanceId());
        HfGoodsSpecExample example = new HfGoodsSpecExample();
        example.createCriteria().andGoodsIdEqualTo(hfGoodsId);
        hfGoodsSpecMapper.deleteByExample(example);
        HfGoodsPictrueExample example2 = new HfGoodsPictrueExample();
        example2.createCriteria().andGoodsIdEqualTo(hfGoodsId);
        List<HfGoodsPictrue> list = hfGoodsPictrueMapper.selectByExample(example2);
        if (!list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                FileDesc fileDesc = fileDescMapper.selectByPrimaryKey(list.get(i).getFileId());
                FileMangeService fileMangeService = new FileMangeService();
                fileMangeService.deleteFile(fileDesc.getGroupName(), fileDesc.getRemoteFilename());
                fileDescMapper.deleteByPrimaryKey(fileDesc.getId());
            }
        }
        hfGoodsPictrueMapper.deleteByExample(example2);
        HfRespExample example3 = new HfRespExample();
        example3.createCriteria().andGoogsIdEqualTo(hfGoodsId);
        hfRespMapper.deleteByExample(example3);
        HfPriceExample example4 = new HfPriceExample();
        example4.createCriteria().andGoogsIdEqualTo(hfGoodsId);
        hfPriceMapper.deleteByExample(example4);
        return builder.body(ResponseUtils.getResponseBody(hfGoodsMapper.deleteByPrimaryKey(hfGoodsId)));
    }


    @ApiOperation(value = "修改商铺", notes = "修改商铺")
    @RequestMapping(value = "/updateStone", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> updateStone(MultipartFile fileInfo, HfStoneRequest request) throws Exception {
        HfStone hfStone = hfStoneMapper.selectByPrimaryKey(request.getStoneId());
        if (hfStone == null) {
            throw new Exception("店铺不存在");
        }
        if (!StringUtils.isEmpty(request.getHfName())) {
            hfStone.setHfName(request.getHfName());
            hfStone.setAddress(request.getAddress());
            hfStone.setHfDesc(request.getHfDesc());
            hfStone.setHfStatus(request.getHfStatus());
            hfStone.setExpireTime(LocalDateTime.now());
        }
//        FileMangeService fileMangeService = new FileMangeService();
//		String arr[];
//			arr = fileMangeService.uploadFile(fileInfo.getBytes(), String.valueOf(request.getUserId()));
//			FileDesc fileDesc = new FileDesc();
//			fileDesc.setFileName(fileInfo.getName());
//			fileDesc.setGroupName(arr[0]);
//			fileDesc.setRemoteFilename(arr[1]);
//			fileDesc.setUserId(request.getUserId());	
//			fileDesc.setCreateTime(LocalDateTime.now());
//			fileDesc.setModifyTime(LocalDateTime.now());
//			fileDesc.setIsDeleted((short) 0);
//			fileDescMapper.insert(fileDesc);
//        if (!StringUtils.isEmpty(request.getHfStatus())) {
//            hfStone.setHfStatus((request.getHfStatus()));
//        }
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        return builder.body(ResponseUtils.getResponseBody(hfStoneMapper.updateByPrimaryKey(hfStone)));
    }

    @ApiOperation(value = "根据id查询商铺信息", notes = "根据id查询商铺信息")
    @RequestMapping(value = "/selectById",method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "商铺id", required = true, type = "Integer"),
    })
    public  ResponseEntity<JSONObject> selectById( Integer id) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        return builder.body(ResponseUtils.getResponseBody(hfStoneMapper.selectByPrimaryKey(id)));
    }

    @ApiOperation(value = "修改商铺状态", notes = "修改商铺状态")
    @RequestMapping(value = "/updateStatus",method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "商铺id", required = true, type = "Integer"),
    })
    public  ResponseEntity<JSONObject> updateStatus(Integer id,Integer hfStatus) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HfStone hfStone = new HfStone();
        hfStone.setHfStatus(hfStatus);
        HfStoneExample hfStoneExample = new HfStoneExample();
        hfStoneExample.createCriteria().andIdEqualTo(id).andIsDeletedEqualTo((short) 0);
        return builder.body(ResponseUtils.getResponseBody(hfStoneMapper.updateByExampleSelective(hfStone,hfStoneExample)));
    }

    @ApiOperation(value = "商铺状态", notes = "商铺状态")
    @RequestMapping(value = "/stoneStatus",method = RequestMethod.GET)

    public  ResponseEntity<JSONObject> stoneStatus() throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        List<Map<String, String>> Scope = new ArrayList<>();
        Map<String, String> params = new HashMap<>();
        params.put("name", "营业");
        params.put("code", "0");
        Map<String, String> params1 = new HashMap<>();
        params1.put("name", "未营业");
        params1.put("code", "1");
        Scope.add(params);
        Scope.add(params1);
        return builder.body(ResponseUtils.getResponseBody(Scope));
    }
    
    @ApiOperation(value = "获取店铺收入金额数据", notes = "获取店铺收入金额数据")
	@RequestMapping(value = "/findAmountDataByStone", method = RequestMethod.GET)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "店铺id", value = "stoneId", required = true, type = "Integer") })
	public ResponseEntity<JSONObject> findAmountDataByStone(Integer stoneId) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		double amountDay = 0;
		double amountMouth = 0;
		List<Integer> paymentCountDay = new ArrayList<Integer>();
		List<Integer> paymentCountYestday = new ArrayList<Integer>();
		List<Integer> paymentCountMouth = new ArrayList<Integer>();
		List<Integer> paymentCountLastMouth = new ArrayList<Integer>();
		Integer orderCountDay = 0;
		Integer orderCountYestday = 0;
		Integer orderCountMouth = 0;
		Integer orderCountLastMouth = 0;
		LocalDateTime dayStart = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
		LocalDateTime dayEnd = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
		LocalDateTime yestdayStart = LocalDateTime.of(LocalDate.now(), LocalTime.MIN).plusDays(-1);
		LocalDateTime yestdayEnd = LocalDateTime.of(LocalDate.now(), LocalTime.MAX).plusDays(-1);
		LocalDateTime mouthStart = LocalDateTime
				.of(LocalDateTime.now().with(TemporalAdjusters.firstDayOfMonth()).toLocalDate(), LocalTime.MIN);
		LocalDateTime mouthEnd = LocalDateTime
				.of(LocalDateTime.now().with(TemporalAdjusters.lastDayOfMonth()).toLocalDate(), LocalTime.MAX);
		LocalDateTime lastMouthStart = LocalDateTime
				.of(LocalDateTime.now().with(TemporalAdjusters.firstDayOfMonth()).toLocalDate(), LocalTime.MIN)
				.plusMonths(-1);
		LocalDateTime lastMouthEnd = LocalDateTime
				.of(LocalDateTime.now().with(TemporalAdjusters.lastDayOfMonth()).toLocalDate(), LocalTime.MAX)
				.plusMonths(-1);
			HfStone hfStone = hfStoneMapper.selectByPrimaryKey(stoneId);
			HfOrderExample example2 = new HfOrderExample();
			example2.createCriteria().andStoneIdEqualTo(hfStone.getId()).andOrderStatusEqualTo("complete")
					.andCreateTimeBetween(dayStart, dayEnd);
			List<HfOrder> hfOrderDays = hfOrderMapper.selectByExample(example2);

			example2.clear();
			example2.createCriteria().andStoneIdEqualTo(hfStone.getId()).andOrderStatusEqualTo("complete")
					.andCreateTimeBetween(yestdayStart, yestdayEnd);
			List<HfOrder> hfOrderYesterday = hfOrderMapper.selectByExample(example2);

			example2.clear();
			example2.createCriteria().andStoneIdEqualTo(hfStone.getId()).andOrderStatusEqualTo("complete")
					.andCreateTimeBetween(mouthStart, mouthEnd);
			List<HfOrder> hfOrderMouths = hfOrderMapper.selectByExample(example2);

			example2.clear();
			example2.createCriteria().andStoneIdEqualTo(hfStone.getId()).andOrderStatusEqualTo("complete")
					.andCreateTimeBetween(lastMouthStart, lastMouthEnd);
			List<HfOrder> hfOrderLastMouths = hfOrderMapper.selectByExample(example2);

			for (int j = 0; j < hfOrderDays.size(); j++) {
				HfOrder order = hfOrderDays.get(j);
				paymentCountDay.add(order.getUserId());
				amountDay += order.getAmount();
			}

			orderCountDay += hfOrderDays.size();

			for (int j = 0; j < hfOrderYesterday.size(); j++) {
				HfOrder order = hfOrderYesterday.get(j);
				paymentCountYestday.add(order.getUserId());
			}

			orderCountYestday += hfOrderYesterday.size();

			for (int j = 0; j < hfOrderMouths.size(); j++) {
				HfOrder order = hfOrderMouths.get(j);
				paymentCountMouth.add(order.getUserId());
				amountMouth += order.getAmount();
			}

			orderCountMouth += hfOrderMouths.size();

			for (int j = 0; j < hfOrderLastMouths.size(); j++) {
				HfOrder order = hfOrderLastMouths.get(j);
				paymentCountLastMouth.add(order.getUserId());
			}

			orderCountLastMouth += hfOrderLastMouths.size();
		HashSet h = new HashSet(paymentCountDay);
		paymentCountDay.clear();
		paymentCountDay.addAll(h);
		h = new HashSet(paymentCountYestday);
		paymentCountYestday.clear();
		paymentCountYestday.addAll(h);
		h = new HashSet(paymentCountMouth);
		paymentCountMouth.clear();
		paymentCountMouth.addAll(h);
		h = new HashSet(paymentCountLastMouth);
		paymentCountLastMouth.clear();
		paymentCountLastMouth.addAll(h);
//		HfUserBrowseRecordExample browseRecordExample = new HfUserBrowseRecordExample();
//		browseRecordExample.createCriteria().andBossIdEqualTo(bossId).andBrowseDateBetween(dayStart, dayEnd);
//		List<HfUserBrowseRecord> browseCountsDay = hfUserBrowseRecordMapper.selectByExample(browseRecordExample);
//		browseRecordExample.clear();
//		browseRecordExample.createCriteria().andBossIdEqualTo(bossId).andBrowseDateBetween(yestdayStart, yestdayEnd);
//		List<HfUserBrowseRecord> browseCountsYestday = hfUserBrowseRecordMapper.selectByExample(browseRecordExample);
//		browseRecordExample.clear();
//		browseRecordExample.createCriteria().andBossIdEqualTo(bossId).andBrowseDateBetween(mouthStart, mouthEnd);
//		List<HfUserBrowseRecord> browseCountsMouth = hfUserBrowseRecordMapper.selectByExample(browseRecordExample);
//		browseRecordExample.clear();
//		browseRecordExample.createCriteria().andBossIdEqualTo(bossId).andBrowseDateBetween(lastMouthStart,
//				lastMouthEnd);
//		List<HfUserBrowseRecord> browseCountsLastMouth = hfUserBrowseRecordMapper.selectByExample(browseRecordExample);
		HomePageInfo info = new HomePageInfo();
		info.setAmountDay(amountDay);
		info.setOrderCountsDay(orderCountDay);
		info.setOrderCountsYestday(orderCountYestday);
		info.setPaymentConutsDay(paymentCountDay.size());
		info.setPaymentConutsYestday(paymentCountYestday.size());
//		info.setBrowseCountsDay(browseCountsDay.size());
//		info.setBrowseCountsYestday(browseCountsYestday.size());
		info.setAmountMouth(amountMouth);
		info.setOrderConutsMouth(orderCountMouth);
		info.setOrderConutsLastMouth(orderCountLastMouth);
		info.setPaymentConutsMouth(paymentCountMouth.size());
		info.setPaymentConutsLastMouth(paymentCountLastMouth.size());
//		info.setBrowseCountsMouth(browseCountsMouth.size());
//		info.setBrowseCountsLastMouth(browseCountsLastMouth.size());
		return builder.body(ResponseUtils.getResponseBody(info));
	}

}
