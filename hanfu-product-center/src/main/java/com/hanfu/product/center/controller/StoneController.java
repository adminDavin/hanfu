package com.hanfu.product.center.controller;

import java.time.LocalDateTime;
import java.util.List;

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
import com.hanfu.product.center.dao.HfPriceMapper;
import com.hanfu.product.center.dao.HfRespMapper;
import com.hanfu.product.center.dao.HfStoneMapper;
import com.hanfu.product.center.dao.ProductInstanceMapper;
import com.hanfu.product.center.model.FileDesc;
import com.hanfu.product.center.model.HfGoods;
import com.hanfu.product.center.model.HfGoodsExample;
import com.hanfu.product.center.model.HfGoodsPictrue;
import com.hanfu.product.center.model.HfGoodsPictrueExample;
import com.hanfu.product.center.model.HfGoodsSpecExample;
import com.hanfu.product.center.model.HfPriceExample;
import com.hanfu.product.center.model.HfRespExample;
import com.hanfu.product.center.model.HfStone;
import com.hanfu.product.center.model.HfStoneExample;
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
        item.setCreateTime(LocalDateTime.now());
        LocalDateTime expireTime = LocalDateTime.now();
        expireTime.plusYears(10);
        item.setExpireTime(expireTime);
        item.setIsDeleted((short) 0);
        return builder.body(ResponseUtils.getResponseBody(hfStoneMapper.insert(item)));
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

}
