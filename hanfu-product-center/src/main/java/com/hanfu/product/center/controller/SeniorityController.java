package com.hanfu.product.center.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.hanfu.common.service.FileMangeService;
import com.hanfu.product.center.dao.FileDescMapper;
import com.hanfu.product.center.dao.HfGoodsPictrueMapper;
import com.hanfu.product.center.dao.HfPriceMapper;
import com.hanfu.product.center.dao.HfSeniorityMapper;
import com.hanfu.product.center.dao.ProductMapper;
import com.hanfu.product.center.dao.SeniorityInstanceMapper;
import com.hanfu.product.center.manual.dao.HfGoodsDao;
import com.hanfu.product.center.manual.model.HfGoodsDisplay;
import com.hanfu.product.center.model.FileDesc;
import com.hanfu.product.center.model.FileDescExample;
import com.hanfu.product.center.model.HfGoodsPictrue;
import com.hanfu.product.center.model.HfGoodsPictrueExample;
import com.hanfu.product.center.model.HfPrice;
import com.hanfu.product.center.model.HfSeniority;
import com.hanfu.product.center.model.Product;
import com.hanfu.product.center.model.ProductExample;
import com.hanfu.product.center.model.SeniorityInstance;
import com.hanfu.product.center.model.SeniorityInstanceExample;
import com.hanfu.product.center.request.HfSeniorityRequest;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseUtils;
import com.hanfu.utils.response.handler.ResponseEntity.BodyBuilder;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
@RequestMapping("/seniority")
@Api
public class SeniorityController {

    @Autowired
    private HfSeniorityMapper hfSeniorityMapper;

    @Autowired
    private FileDescMapper fileDescMapper;

    @Autowired
    private SeniorityInstanceMapper seniorityInstanceMapper;

    @Autowired
    private HfGoodsDao hfGoodsDao;
    @Autowired
    private HfPriceMapper    hfPriceMapper;

    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private HfGoodsPictrueMapper hfGoodsPictrueMapper;
    @ApiOperation(value = "添加排行相关信息", notes = "添加排行相关信息")
    @RequestMapping(value = "/addSeniorityInfo", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> addSeniorityInfo(HfSeniorityRequest request,MultipartFile fileInfo) throws Exception {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HfSeniority hfSeniority = new HfSeniority();
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.replace("-", "");
        if(fileInfo != null) {
            hfSeniority.setFileId(updateCategoryPicture(fileInfo,uuid,"无"));
        }
        hfSeniority.setSeniorityName(request.getSeniorityName());
        hfSeniority.setCreateTime(LocalDateTime.now());
        hfSeniority.setModifityTime(LocalDateTime.now());
        hfSeniority.setIsDeleted((short) 0);
        return builder.body(ResponseUtils.getResponseBody(hfSeniorityMapper.insert(hfSeniority)));
    }

    @ApiOperation(value = "删除排行相关信息", notes = "删除排行相关信息")
    @RequestMapping(value = "/deleteSeniorityInfo", method = RequestMethod.GET)
    public ResponseEntity<JSONObject> deleteSeniorityInfo(@RequestParam Integer seniorityId) throws Exception {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        FileMangeService fileMangeService = new FileMangeService();
        HfSeniority hfSeniority = hfSeniorityMapper.selectByPrimaryKey(seniorityId);
        if(hfSeniority.getFileId() != null) {
            FileDesc fileDesc = fileDescMapper.selectByPrimaryKey(hfSeniority.getFileId());
            fileMangeService.deleteFile(fileDesc.getGroupName(), fileDesc.getRemoteFilename());
            fileDescMapper.deleteByPrimaryKey(hfSeniority.getFileId());
        }
        return builder.body(ResponseUtils.getResponseBody(hfSeniorityMapper.deleteByPrimaryKey(seniorityId)));
    }

    @ApiOperation(value = "修改排行相关信息", notes = "修改排行相关信息")
    @RequestMapping(value = "/updateSeniorityInfo", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> updateSeniorityInfo(HfSeniorityRequest request,MultipartFile fileInfo) throws Exception {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HfSeniority hfSeniority = hfSeniorityMapper.selectByPrimaryKey(request.getId());
        if(hfSeniority != null) {
            String uuid = UUID.randomUUID().toString();
            uuid = uuid.replace("-", "");
            if(fileInfo != null) {
                FileMangeService fileMangeService = new FileMangeService();
                String arr[];
                arr = fileMangeService.uploadFile(fileInfo.getBytes(),"-1");
                if(hfSeniority.getFileId() == null) {
                    FileDesc fileDesc = new FileDesc();
                    fileDesc.setFileName(uuid);
                    fileDesc.setGroupName(arr[0]);
                    fileDesc.setRemoteFilename(arr[1]);
                    fileDesc.setUserId(-1);
                    fileDesc.setCreateTime(LocalDateTime.now());
                    fileDesc.setModifyTime(LocalDateTime.now());
                    fileDesc.setIsDeleted((short) 0);
                    fileDescMapper.insert(fileDesc);
                    hfSeniority.setFileId(fileDesc.getId());
                }else {
                    FileDesc fileDesc = fileDescMapper.selectByPrimaryKey(hfSeniority.getFileId());
                    fileMangeService.deleteFile(fileDesc.getGroupName(),fileDesc.getRemoteFilename() );
                    fileDesc.setGroupName(arr[0]);
                    fileDesc.setRemoteFilename(arr[1]);
                    fileDesc.setModifyTime(LocalDateTime.now());
                    fileDescMapper.updateByPrimaryKey(fileDesc);
                }
                hfSeniority.setFileId(updateCategoryPicture(fileInfo,uuid,"无"));
            }
            if(!StringUtils.isEmpty(request.getSeniorityName())) {
                hfSeniority.setSeniorityName(request.getSeniorityName());
            }
            hfSeniority.setModifityTime(LocalDateTime.now());
            hfSeniorityMapper.updateByPrimaryKey(hfSeniority);
        }
        return builder.body(ResponseUtils.getResponseBody(null));
    }

    @ApiOperation(value = "查询排行相关信息", notes = "查询排行相关信息")
    @RequestMapping(value = "/findSeniorityInfo", method = RequestMethod.GET)
    public ResponseEntity<JSONObject> findSeniorityInfo() throws Exception {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        List<HfSeniority> list = hfSeniorityMapper.selectByExample(null);
        List<HfSeniorityRequest> result = new ArrayList<HfSeniorityRequest>();
        for (int i = 0; i < list.size(); i++) {
			HfSeniority hfSeniority = list.get(i);
			HfSeniorityRequest request = new HfSeniorityRequest();
			request.setId(hfSeniority.getId());
			request.setSeniorityName(hfSeniority.getSeniorityName());
			request.setFileId(hfSeniority.getFileId());
			request.setCreateTime(DateTimeFormatter.ofPattern("yyyy-MM-dd HH：mm：ss")
                    .format(hfSeniority.getCreateTime().plusHours(8L)));
			request.setModifityTime(DateTimeFormatter.ofPattern("yyyy-MM-dd HH：mm：ss")
                    .format(hfSeniority.getModifityTime().plusHours(8L)));
			result.add(request);
		}
        return builder.body(ResponseUtils.getResponseBody(result));
    }


    @RequestMapping(value = "/updateSeniorityPicture", method = RequestMethod.POST)
    @ApiOperation(value = "更新类目图片", notes = "更新类目图片")
    public Integer updateCategoryPicture(MultipartFile fileInfo, @RequestParam(required = false) String uuid ,@RequestParam String type) throws Exception {
        FileMangeService fileMangeService = new FileMangeService();
        String arr[];
        arr = fileMangeService.uploadFile(fileInfo.getBytes(),"-1");
        if("类目页面图片".equals(type)) {
            FileDesc fileDesc = new FileDesc();
            fileDesc.setFileName("类目页面图片");
            fileDesc.setGroupName(arr[0]);
            fileDesc.setRemoteFilename(arr[1]);
            fileDesc.setUserId(-1);
            fileDesc.setCreateTime(LocalDateTime.now());
            fileDesc.setModifyTime(LocalDateTime.now());
            fileDesc.setIsDeleted((short) 0);
            fileDescMapper.insert(fileDesc);
            return -1;
        }
        Integer fileId = null;
        FileDescExample example = new FileDescExample();
        example.createCriteria().andFileNameEqualTo(uuid);
        List<FileDesc> list = fileDescMapper.selectByExample(example);
        if (list.isEmpty()) {
            FileDesc fileDesc = new FileDesc();
            fileDesc.setFileName(uuid);
            fileDesc.setGroupName(arr[0]);
            fileDesc.setRemoteFilename(arr[1]);
            fileDesc.setUserId(-1);
            fileDesc.setCreateTime(LocalDateTime.now());
            fileDesc.setModifyTime(LocalDateTime.now());
            fileDesc.setIsDeleted((short) 0);
            fileDescMapper.insert(fileDesc);
            fileId = fileDesc.getId();
        } else {
            FileDesc fileDesc = list.get(0);
            fileMangeService.deleteFile(fileDesc.getGroupName(),fileDesc.getRemoteFilename() );
            fileDesc.setGroupName(arr[0]);
            fileDesc.setRemoteFilename(arr[1]);
            fileDesc.setModifyTime(LocalDateTime.now());
            fileDescMapper.updateByPrimaryKey(fileDesc);
            fileId = fileDesc.getId();
        }
        return fileId;
    }

    @ApiOperation(value = "给排行添加内容", notes = "给排行添加内容")
    @RequestMapping(value = "/addSeniorityContent", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> addSeniorityContent(@RequestParam Integer seniorityId,@RequestParam Integer goodsId) throws Exception {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        SeniorityInstanceExample example = new SeniorityInstanceExample();
        example.createCriteria().andSeniorityIdEqualTo(seniorityId).andGoodsIdEqualTo(goodsId);
        List<SeniorityInstance> list = seniorityInstanceMapper.selectByExample(example);
        if(!list.isEmpty()) {
            return builder.body(ResponseUtils.getResponseBody("已经存在"));
        }
        SeniorityInstance instance = new SeniorityInstance();
        instance.setGoodsId(goodsId);
        instance.setSeniorityId(seniorityId);
        instance.setCreateTime(LocalDateTime.now());
        instance.setModifieyTime(LocalDateTime.now());
        instance.setIsDeleted((short) 0);
        return builder.body(ResponseUtils.getResponseBody(seniorityInstanceMapper.insert(instance)));
    }

    @ApiOperation(value = "给排行删除内容", notes = "给排行删除内容")
    @RequestMapping(value = "/deleteSeniorityContent", method = RequestMethod.GET)
    public ResponseEntity<JSONObject> deleteSeniorityContent(@RequestParam Integer seniorityId,@RequestParam Integer goodsId) throws Exception {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        SeniorityInstanceExample example = new SeniorityInstanceExample();
        example.createCriteria().andSeniorityIdEqualTo(seniorityId).andGoodsIdEqualTo(goodsId);
        List<SeniorityInstance> list = seniorityInstanceMapper.selectByExample(example);
        if(!list.isEmpty()) {
            seniorityInstanceMapper.deleteByPrimaryKey(list.get(0).getId());
        }
        return builder.body(ResponseUtils.getResponseBody(null));
    }


    @ApiOperation(value = "查询排行内容", notes = "查询排行内容")
    @RequestMapping(value = "/findSeniorityContent", method = RequestMethod.GET)
    public ResponseEntity<JSONObject> findSeniorityContent(@RequestParam Integer seniorityId) throws Exception {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        List<HfGoodsDisplay> list = new ArrayList<HfGoodsDisplay>();
        SeniorityInstanceExample example = new SeniorityInstanceExample();
        example.createCriteria().andSeniorityIdEqualTo(seniorityId);
        List<SeniorityInstance> list2 = seniorityInstanceMapper.selectByExample(example);
        for (int i = 0; i < list2.size(); i++) {
            SeniorityInstance instance = list2.get(i);
            HfGoodsDisplay goodsDisplay = hfGoodsDao.selectGoodsPartInfo(instance.getGoodsId());
            if(goodsDisplay != null) {
                HfPrice price = hfPriceMapper.selectByPrimaryKey(goodsDisplay.getPriceId());
                if(price != null) {
                    if(price.getSellPrice() != null) {
                        goodsDisplay.setSellPrice(price.getSellPrice());
                    }
                }
                HfGoodsPictrueExample example2 = new HfGoodsPictrueExample();
                example2.createCriteria().andGoodsIdEqualTo(instance.getGoodsId());
                List<HfGoodsPictrue> hfGoodsPictrues = hfGoodsPictrueMapper.selectByExample(example2);
                if(!hfGoodsPictrues.isEmpty()) {
                    goodsDisplay.setFileId(hfGoodsPictrues.get(0).getFileId());
                }
                list.add(goodsDisplay);
            }
        }
        return builder.body(ResponseUtils.getResponseBody(list));
    }

    @ApiOperation(value = "小程序查询商品列表", notes = "小程序查询商品列表")
    @RequestMapping(value = "/findProductList", method = RequestMethod.GET)
    public ResponseEntity<JSONObject> findProductList(@RequestParam(name = "类型，榜单0，类目1") Integer type,@RequestParam Integer id,
                                                      @RequestParam(name = "page", required = false) Integer page,
                                                      @RequestParam(name = "size", required = false) Integer size) throws Exception {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        if(type == 0) {
            List<HfGoodsDisplay> list = new ArrayList<HfGoodsDisplay>();
            SeniorityInstanceExample example = new SeniorityInstanceExample();
            example.createCriteria().andSeniorityIdEqualTo(id);
            if (!StringUtils.isEmpty(page)) {
                if (!StringUtils.isEmpty(size)) {
                    PageHelper.startPage(page, size);
                }
            }
            List<SeniorityInstance> list2 = seniorityInstanceMapper.selectByExample(example);
            for (int i = 0; i < list2.size(); i++) {
                SeniorityInstance instance = list2.get(i);
                HfGoodsDisplay goodsDisplay = hfGoodsDao.selectGoodsPartInfo(instance.getGoodsId());
                if(goodsDisplay != null) {
                    HfPrice price = hfPriceMapper.selectByPrimaryKey(goodsDisplay.getPriceId());
                    if(price != null) {
                        if(price.getSellPrice() != null) {
                            goodsDisplay.setSellPrice(price.getSellPrice());
                        }
                    }
                    HfGoodsPictrueExample example2 = new HfGoodsPictrueExample();
                    example2.createCriteria().andGoodsIdEqualTo(instance.getGoodsId());
                    List<HfGoodsPictrue> hfGoodsPictrues = hfGoodsPictrueMapper.selectByExample(example2);
                    if(!hfGoodsPictrues.isEmpty()) {
                        goodsDisplay.setFileId(hfGoodsPictrues.get(0).getFileId());
                    }
                    list.add(goodsDisplay);
                }
            }
            return builder.body(ResponseUtils.getResponseBody(list));
        }
        if(type == 1) {
            List<HfGoodsDisplay> hfGoodsDisplays = new ArrayList<HfGoodsDisplay>();
            ProductExample example = new ProductExample();
            example.createCriteria().andCategoryIdEqualTo(id);
            if (!StringUtils.isEmpty(page)) {
                if (!StringUtils.isEmpty(size)) {
                    PageHelper.startPage(page, size);
                }
            }
            List<Product> list = productMapper.selectByExample(example);
            if (!list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    Product product = list.get(i);
                    HfGoodsDisplay display = new HfGoodsDisplay();
                    display.setCategoryId(id);
                    display.setProductId(product.getId());
                    List<HfGoodsDisplay> goodsDisplay = hfGoodsDao.selectProductBycategoryIdOrProductName(display);
                    if(!goodsDisplay.isEmpty()) {
                        HfGoodsPictrueExample example2 = new HfGoodsPictrueExample();
                        example2.createCriteria().andGoodsIdEqualTo(goodsDisplay.get(0).getId());
                        List<HfGoodsPictrue> list2 = hfGoodsPictrueMapper.selectByExample(example2);
                        if(!list2.isEmpty()) {
                            HfGoodsPictrue hfGoodsPictrue = list2.get(0);
                            goodsDisplay.get(0).setFileId(hfGoodsPictrue.getFileId());
                        }
                        hfGoodsDisplays.add(goodsDisplay.get(0));
                    }
                }
            }
            return builder.body(ResponseUtils.getResponseBody(hfGoodsDisplays));
        }
        return builder.body(ResponseUtils.getResponseBody(null));
    }

}
