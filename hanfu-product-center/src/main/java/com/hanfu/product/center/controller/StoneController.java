package com.hanfu.product.center.controller;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.ChecksumException;
import com.google.zxing.EncodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.ImageReader;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DecoderResult;
import com.google.zxing.datamatrix.decoder.Decoder;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.hanfu.common.service.FileMangeService;
import com.hanfu.product.center.dao.FileDescMapper;
import com.hanfu.product.center.dao.HfGoodsMapper;
import com.hanfu.product.center.dao.HfGoodsPictrueMapper;
import com.hanfu.product.center.dao.HfGoodsSpecMapper;
import com.hanfu.product.center.dao.HfOrderDetailMapper;
import com.hanfu.product.center.dao.HfOrderMapper;
import com.hanfu.product.center.dao.HfPriceMapper;
import com.hanfu.product.center.dao.HfRespMapper;
import com.hanfu.product.center.dao.HfStoneConcernMapper;
import com.hanfu.product.center.dao.HfStoneMapper;
import com.hanfu.product.center.dao.HfStonePictureMapper;
import com.hanfu.product.center.dao.HfUserBrowseRecordMapper;
import com.hanfu.product.center.dao.ProductInstanceMapper;
import com.hanfu.product.center.dao.ProductMapper;
import com.hanfu.product.center.manual.dao.HomePageDao;
import com.hanfu.product.center.manual.model.HfStoneInfo;
import com.hanfu.product.center.manual.model.HomePageInfo;
import com.hanfu.product.center.manual.model.ProductStone.StonePictureTypeEnum;
import com.hanfu.product.center.model.EvluateInstancePicture;
import com.hanfu.product.center.model.FileDesc;
import com.hanfu.product.center.model.HfGoods;
import com.hanfu.product.center.model.HfGoodsExample;
import com.hanfu.product.center.model.HfGoodsPictrue;
import com.hanfu.product.center.model.HfGoodsPictrueExample;
import com.hanfu.product.center.model.HfGoodsSpecExample;
import com.hanfu.product.center.model.HfOrder;
import com.hanfu.product.center.model.HfOrderDetail;
import com.hanfu.product.center.model.HfOrderDetailExample;
import com.hanfu.product.center.model.HfOrderExample;
import com.hanfu.product.center.model.HfPriceExample;
import com.hanfu.product.center.model.HfRespExample;
import com.hanfu.product.center.model.HfStone;
import com.hanfu.product.center.model.HfStoneConcernExample;
import com.hanfu.product.center.model.HfStoneExample;
import com.hanfu.product.center.model.HfStonePicture;
import com.hanfu.product.center.model.HfStonePictureExample;
import com.hanfu.product.center.model.HfUserBrowseRecord;
import com.hanfu.product.center.model.HfUserBrowseRecordExample;
import com.hanfu.product.center.model.Product;
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
    
    @Autowired
    private HfOrderDetailMapper hfOrderDetailMapper;
    
    @Autowired
    private HomePageDao homePageDao;
    
    @Autowired
    private ProductMapper productMapper;
    
    @Autowired
    private HfStoneConcernMapper hfStoneConcernMapper;
    
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
    public ResponseEntity<JSONObject> getStonePicture(Integer stoneId, String type) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HfStonePictureExample example = new HfStonePictureExample();
        example.createCriteria().andStoneIdEqualTo(stoneId).andTypeEqualTo(type);
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
    public ResponseEntity<JSONObject> addStonePicture(String type, Integer stoneId,MultipartFile file) throws JSONException, IOException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HfStone stone = hfStoneMapper.selectByPrimaryKey(stoneId);
//        for(MultipartFile f:file) {
			String arr[];
			FileMangeService fileMangeService = new FileMangeService();
			arr = fileMangeService.uploadFile(file.getBytes(), String.valueOf(0));
			FileDesc fileDesc = new FileDesc();
			fileDesc.setFileName(file.getName());
			fileDesc.setGroupName(arr[0]);
			fileDesc.setRemoteFilename(arr[1]);
			fileDesc.setCreateTime(LocalDateTime.now());
			fileDesc.setModifyTime(LocalDateTime.now());
			fileDesc.setIsDeleted((short) 0);
			fileDescMapper.insert(fileDesc);
			HfStonePicture picture = new HfStonePicture();
			picture.setStoneId(stoneId);
			picture.setType(StonePictureTypeEnum.getStonePictureTypeEnum(type).getStonePictureType());
			picture.setFileId(fileDesc.getId());
			picture.setHfName("店铺图片");
			picture.setHfDesc("店铺图片描述");
			picture.setCreateTime(LocalDateTime.now());
			picture.setModifyTime(LocalDateTime.now());
			picture.setIsDeleted((byte) 0);
			hfStonePictureMapper.insert(picture);
//		}
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
    public  ResponseEntity<JSONObject> selectById(Integer id, Integer userId) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HfStone hfStone = hfStoneMapper.selectByPrimaryKey(id);
        HfStoneInfo info = new HfStoneInfo();
        info.setStoneName(hfStone.getHfName());
        info.setStoneDesc(hfStone.getHfDesc());
        info.setAddress(hfStone.getAddress());
        info.setConcernCount(hfStone.getConcernCount());
        info.setCreateTime(hfStone.getCreateTime());
        info.setExpireTime(hfStone.getExpireTime());
        HfStonePictureExample example = new HfStonePictureExample();
        example.createCriteria().andStoneIdEqualTo(id).andTypeEqualTo("avatar");
        List<HfStonePicture> pictures = hfStonePictureMapper.selectByExample(example);
        if(!CollectionUtils.isEmpty(pictures)) {
        	info.setAvatarId(pictures.get(0).getFileId());
        }
        example.clear();
        example.createCriteria().andStoneIdEqualTo(id).andTypeEqualTo("background");
        pictures = hfStonePictureMapper.selectByExample(example);
        if(!CollectionUtils.isEmpty(pictures)) {
        	info.setBackgroundId(pictures.get(0).getFileId());
        }
        info.setIsConcern(0);
        if(userId != null) {
        	 HfStoneConcernExample example2 = new HfStoneConcernExample();
             example2.createCriteria().andUserIdEqualTo(userId).andStoneIdEqualTo(id);
             if(CollectionUtils.isEmpty(hfStoneConcernMapper.selectByExample(example2))) {
             	info.setIsConcern(0);
             }else {
             	info.setIsConcern(1);
             }
        }
        return builder.body(ResponseUtils.getResponseBody(info));
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
			@ApiImplicitParam(paramType = "query", name = "stoneId", value = "店铺id", required = true, type = "Integer") })
	public ResponseEntity<JSONObject> findAmountDataByStone(Integer stoneId) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		List<String> status = new ArrayList<String>();
		status.add("complete");
		status.add("evaluate");
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
			HfOrderDetailExample example2 = new HfOrderDetailExample();
//			HfOrderExample example2 = new HfOrderExample();
//			example2.createCriteria().andStoneIdEqualTo(hfStone.getId()).andOrderStatusEqualTo("complete")
//					.andCreateTimeBetween(dayStart, dayEnd);
			example2.createCriteria().andStoneIdEqualTo(hfStone.getId()).andHfStatusIn(status)
			.andCreateTimeBetween(dayStart, dayEnd);
			List<HfOrderDetail> hfOrderDays = hfOrderDetailMapper.selectByExample(example2);

			example2.clear();
//			example2.createCriteria().andStoneIdEqualTo(hfStone.getId()).andOrderStatusEqualTo("complete")
//					.andCreateTimeBetween(yestdayStart, yestdayEnd);
			example2.createCriteria().andStoneIdEqualTo(hfStone.getId()).andHfStatusIn(status)
			.andCreateTimeBetween(yestdayStart, yestdayEnd);
			List<HfOrderDetail> hfOrderYesterday = hfOrderDetailMapper.selectByExample(example2);

			example2.clear();
//			example2.createCriteria().andStoneIdEqualTo(hfStone.getId()).andOrderStatusEqualTo("complete")
//					.andCreateTimeBetween(mouthStart, mouthEnd);
			example2.createCriteria().andStoneIdEqualTo(hfStone.getId()).andHfStatusIn(status)
			.andCreateTimeBetween(mouthStart, mouthEnd);
			List<HfOrderDetail> hfOrderMouths = hfOrderDetailMapper.selectByExample(example2);

			example2.clear();
//			example2.createCriteria().andStoneIdEqualTo(hfStone.getId()).andOrderStatusEqualTo("complete")
//					.andCreateTimeBetween(lastMouthStart, lastMouthEnd);
			example2.createCriteria().andStoneIdEqualTo(hfStone.getId()).andHfStatusIn(status)
			.andCreateTimeBetween(lastMouthStart, lastMouthEnd);
			List<HfOrderDetail> hfOrderLastMouths = hfOrderDetailMapper.selectByExample(example2);

			for (int j = 0; j < hfOrderDays.size(); j++) {
				HfOrderDetail order = hfOrderDays.get(j);
//				paymentCountDay.add(order.getUserId());
//				amountDay += order.getAmount();
				amountDay += order.getActualPrice();
			}

			orderCountDay += hfOrderDays.size();

//			for (int j = 0; j < hfOrderYesterday.size(); j++) {
//				HfOrder order = hfOrderYesterday.get(j);
//				paymentCountYestday.add(order.getUserId());
//			}

			orderCountYestday += hfOrderYesterday.size();

			for (int j = 0; j < hfOrderMouths.size(); j++) {
				HfOrderDetail order = hfOrderMouths.get(j);
//				paymentCountMouth.add(order.getUserId());
//				amountMouth += order.getAmount();
				amountMouth += order.getActualPrice();
			}

			orderCountMouth += hfOrderMouths.size();

//			for (int j = 0; j < hfOrderLastMouths.size(); j++) {
//				HfOrder order = hfOrderLastMouths.get(j);
//				paymentCountLastMouth.add(order.getUserId());
//			}

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
		HfUserBrowseRecordExample browseRecordExample = new HfUserBrowseRecordExample();
		browseRecordExample.createCriteria().andStoneIdEqualTo(stoneId).andBrowseDateBetween(dayStart, dayEnd);
		List<HfUserBrowseRecord> browseCountsDay = hfUserBrowseRecordMapper.selectByExample(browseRecordExample);
		browseRecordExample.clear();
		browseRecordExample.createCriteria().andStoneIdEqualTo(stoneId).andBrowseDateBetween(yestdayStart, yestdayEnd);
		List<HfUserBrowseRecord> browseCountsYestday = hfUserBrowseRecordMapper.selectByExample(browseRecordExample);
		browseRecordExample.clear();
		browseRecordExample.createCriteria().andStoneIdEqualTo(stoneId).andBrowseDateBetween(mouthStart, mouthEnd);
		List<HfUserBrowseRecord> browseCountsMouth = hfUserBrowseRecordMapper.selectByExample(browseRecordExample);
		browseRecordExample.clear();
		browseRecordExample.createCriteria().andStoneIdEqualTo(stoneId).andBrowseDateBetween(lastMouthStart,
				lastMouthEnd);
		List<HfUserBrowseRecord> browseCountsLastMouth = hfUserBrowseRecordMapper.selectByExample(browseRecordExample);
		HomePageInfo info = new HomePageInfo();
		info.setAmountDay(amountDay);
		info.setOrderCountsDay(orderCountDay);
		info.setOrderCountsYestday(orderCountYestday);
		info.setPaymentConutsDay(paymentCountDay.size());
		info.setPaymentConutsYestday(paymentCountYestday.size());
		info.setBrowseCountsDay(browseCountsDay.size());
		info.setBrowseCountsYestday(browseCountsYestday.size());
		info.setAmountMouth(amountMouth);
		info.setOrderConutsMouth(orderCountMouth);
		info.setOrderConutsLastMouth(orderCountLastMouth);
		info.setPaymentConutsMouth(paymentCountMouth.size());
		info.setPaymentConutsLastMouth(paymentCountLastMouth.size());
		info.setBrowseCountsMouth(browseCountsMouth.size());
		info.setBrowseCountsLastMouth(browseCountsLastMouth.size());
		return builder.body(ResponseUtils.getResponseBody(info));
	}
    
    @ApiOperation(value = "获取店铺销量排行数据", notes = "获取店铺销量排行数据")
	@RequestMapping(value = "/findSalesVolumeDataByStone", method = RequestMethod.GET)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "stoneId", value = "店铺id", required = true, type = "Integer") })
	public ResponseEntity<JSONObject> findSalesVolumeDataByStone(Integer stoneId) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		Integer salesCountAll = 0;
		List<HomePageInfo> infos = new ArrayList<HomePageInfo>();
		HfOrderDetailExample example3 = new HfOrderDetailExample();
		example3.createCriteria().andStoneIdEqualTo(stoneId);
		List<HfOrderDetail> hfOrderDetails = hfOrderDetailMapper.selectByExample(example3);
		List<Integer> orderDetailId = hfOrderDetails.stream().map(HfOrderDetail::getId).collect(Collectors.toList());
		List<HomePageInfo> result = homePageDao.findSalesVolume(orderDetailId);
		List<Integer> productId = new ArrayList<Integer>();
		for (int i = 0; i < result.size(); i++) {
			HomePageInfo info = result.get(i);
			HfGoods goods = hfGoodsMapper.selectByPrimaryKey(info.getGoodId());
			if (goods != null) {
				info.setProductId(goods.getProductId());
				productId.add(goods.getProductId());
			}
		}
		HashSet h = new HashSet(productId);
		productId.clear();
		productId.addAll(h);
		for (int i = 0; i < productId.size(); i++) {
			Product product = productMapper.selectByPrimaryKey(productId.get(i));
			salesCountAll = 0;
			List<HomePageInfo> pageInfos = new ArrayList<HomePageInfo>();
			for (int j = 0; j < result.size(); j++) {
				if (productId.get(i) == result.get(j).getProductId()) {
					salesCountAll += result.get(j).getSalesCount();
					pageInfos.add(result.get(j));
				}
			}
			HomePageInfo info = new HomePageInfo();
			info.setSalesCountAll(salesCountAll);
			info.setProductId(productId.get(i));
			info.setGoodsInfo(pageInfos);
			info.setProductName(product.getHfName());
			infos.add(info);
		}
		infos.sort(new Comparator<HomePageInfo>() {// Comparator 比较器. 需要实现比较方法
			@Override
			public int compare(HomePageInfo o1, HomePageInfo o2) {
				return o2.getSalesCountAll() - o1.getSalesCountAll();// 从小到大 , 如果是o2.age-o1.age 则表示从大到小
			}
		});
		return builder.body(ResponseUtils.getResponseBody(infos));
	}
    
    public BufferedImage createCode(Integer stoneId) throws IOException, JSONException {
    	
    	 String str = String.valueOf(stoneId);
         byte[] b = Base64.getEncoder().encode(str.getBytes());
         BufferedImage image = null;
        //二维码的宽高
        int width = 200;
        int height = 200;
        OutputStream bOut = null;
        //其他参数，如字符集编码
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        //容错级别为H
        hints.put(EncodeHintType.ERROR_CORRECTION , ErrorCorrectionLevel.H);
        //白边的宽度，可取0~4
        hints.put(EncodeHintType.MARGIN , 0);

        BitMatrix bitMatrix = null;
        try {
            //生成矩阵，因为我的业务场景传来的是编码之后的URL，所以先解码
        	MultiFormatWriter writer = new MultiFormatWriter();
            bitMatrix = new MultiFormatWriter().encode(new String(b),
                    BarcodeFormat.QR_CODE, width, height,hints);
            //bitMatrix = deleteWhite(bitMatrix);
            bOut = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix,"png", bOut);
            image = MatrixToImageWriter.toBufferedImage(bitMatrix);
//            ResponseEntity<JSONObject> pictures = getStonePicture(stoneId,"avatar");
//            List<HfStonePicture> list = pictures.getBody().getObject("data",List.class);
//            if(!CollectionUtils.isEmpty(list)) {
//            	FileDesc desc = fileDescMapper.selectByPrimaryKey(list.get(0).getFileId());
//            	FileMangeService fileMangeService = new FileMangeService();
//            	byte[] file = fileMangeService.downloadFile(desc.getGroupName(), desc.getRemoteFilename());
//            	ByteArrayInputStream stream = new ByteArrayInputStream(file);
//                BufferedImage readImg = ImageIO.read(stream);
//                stream.reset();
//                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//                ImageIO.write(readImg, "png", outputStream);
//                ByteArrayInputStream is = new ByteArrayInputStream(outputStream.toByteArray());
////            	BufferedImage bi = ImageIO.read(is);
//            	Image im = ImageIO.read(is);
//            	System.out.println("im" + im);
//            	Graphics2D g = image.createGraphics();
////            	int widthLogo = im.getWidth(null);
////            	int heightLogo = im.getHeight(null);
//                g.drawImage(im, 10, 10, 30, 30, null);
//                image.flush();
//                return image;
//            }
        } catch (WriterException e) {
            e.printStackTrace();
        }
		return image;
    }
    
   
    @ApiOperation(value = "店铺二维码", notes = "店铺二维码")
    @RequestMapping(value = "/StoneCode", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> StoneCode(Integer stoneId) throws JSONException, IOException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HfStone stone = hfStoneMapper.selectByPrimaryKey(stoneId);
        
        BufferedImage stream = createCode(stoneId);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            ImageIO.write(stream, "png", out);
        } catch (IOException e) {
            //log.error(e.getMessage());
        }
        String arr[];
		FileMangeService fileMangeService = new FileMangeService();
		arr = fileMangeService.uploadFile(out.toByteArray(),String.valueOf(0));
        
        HfStonePictureExample hfStonePictureExample = new HfStonePictureExample();
        hfStonePictureExample.createCriteria().andStoneIdEqualTo(stoneId).andTypeEqualTo("code");
        List<HfStonePicture> pictures = hfStonePictureMapper.selectByExample(hfStonePictureExample);
        if(CollectionUtils.isEmpty(pictures)) {
        	FileDesc fileDesc = new FileDesc();
    		fileDesc.setFileName("店铺二维码");
    		fileDesc.setGroupName(arr[0]);
    		fileDesc.setRemoteFilename(arr[1]);
    		fileDesc.setCreateTime(LocalDateTime.now());
    		fileDesc.setModifyTime(LocalDateTime.now());
    		fileDesc.setIsDeleted((short) 0);
    		fileDescMapper.insert(fileDesc);
    		HfStonePicture picture = new HfStonePicture();
    		picture.setStoneId(stoneId);
    		picture.setType(StonePictureTypeEnum.CODE.getStonePictureType());
    		picture.setFileId(fileDesc.getId());
    		picture.setHfName("店铺二维码");
    		picture.setHfDesc("店铺二维码描述");
    		picture.setCreateTime(LocalDateTime.now());
    		picture.setModifyTime(LocalDateTime.now());
    		picture.setIsDeleted((byte) 0);
    		hfStonePictureMapper.insert(picture);
        }else {
        	HfStonePicture picture = pictures.get(0);
        	FileDesc desc = fileDescMapper.selectByPrimaryKey(picture.getFileId());
        	fileMangeService.deleteFile(desc.getGroupName(), desc.getRemoteFilename());
        	desc.setModifyTime(LocalDateTime.now());
        	desc.setGroupName(arr[0]);
        	desc.setRemoteFilename(arr[1]);
        	fileDescMapper.updateByPrimaryKey(desc);
        }
        return builder.body(ResponseUtils.getResponseBody(stone.getId()));
    }
}
