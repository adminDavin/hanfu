package com.hanfu.order.center.controller;

import com.alibaba.fastjson.JSONObject;
import com.hanfu.common.service.FileMangeService;
import com.hanfu.order.center.cancel.dao.ProductMapper;
import com.hanfu.order.center.cancel.model.Product;
import com.hanfu.order.center.dao.*;
import com.hanfu.order.center.manual.dao.DiscoverDao;
import com.hanfu.order.center.manual.model.DiscoverDisplay;
import com.hanfu.order.center.manual.model.DiscoverUser;
import com.hanfu.order.center.manual.model.DiscoverDisplay.DiscoverTypeEnum;
import com.hanfu.order.center.model.*;
import com.hanfu.order.center.request.CreateHfOrderRequest;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

@RestController
@Api
@RequestMapping("/Discover")
@CrossOrigin
public class DiscoverController {

	private static final String REST_URL_PREFIX = "https://swcloud.tjsichuang.cn:1445/api/user/";
	private static final String LOCK = "lock";
	@Autowired
    RestTemplate restTemplate;
	@Autowired
	private DiscoverMapper discoverMapper;
	@Autowired
	private DiscoverProductMapper discoverProductMapper;
	@Autowired
	private DiscoverPictrueMapper discoverPictrueMapper;
	@Autowired
	private HfOrderMapper hfOrderMapper;
	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private HfGoodMapper hfGoodMapper;
	@Autowired
	private HfOrderDetailMapper hfOrderDetailMapper;
	@Autowired
	private DiscoverDao discoverDao;
	@Autowired
	private FileDescMapper fileDescMapper;

	@RequestMapping(value = "/addDiscover", method = RequestMethod.POST)
	@ApiOperation(value = "添加发现", notes = "添加发现")
	@Transactional(rollbackFor = {RuntimeException.class, Error.class})
	public ResponseEntity<JSONObject> addDiscover(DiscoverDisplay discoverDisplay) throws Exception {
		ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
		Discover discover = new Discover();
		discover.setCreateTime(LocalDateTime.now());
		discover.setModifyTime(LocalDateTime.now());
		discover.setIsDeleted((short) 0);
		discover.setDiscoverType(DiscoverTypeEnum.getDiscoverTypeEnum(discoverDisplay.getDiscoverType()).getDiscoverType());
		discover.setDiscoverHeadline(discoverDisplay.getDiscoverHeadline());
		discover.setDiscoverContent(discoverDisplay.getDiscoverContent());
		discover.setDiscoverDesc(discoverDisplay.getDiscoverDesc());
		discover.setUserId(discoverDisplay.getUserId());
		discoverMapper.insert(discover);
		if(discoverDisplay.getProductId() != null) {
			if (discoverDisplay.getProductId().size() != 0) {
				addproduct(discoverDisplay.getProductId(), discover.getId());
			}
		}
		List<Integer> fileId = discoverDisplay.getFileId();
		if (fileId != null) {
			for (int i = 0; i < fileId.size(); i++) {
				DiscoverPictrue pictrue = new DiscoverPictrue();
				pictrue.setDiscoverId(discover.getId());
				pictrue.setFileId(fileId.get(i));
				pictrue.setCreateTime(LocalDateTime.now());
				pictrue.setModifyTime(LocalDateTime.now());
				pictrue.setIsDeleted((short) 0);
				discoverPictrueMapper.insert(pictrue);
			}
		}

		return builder.body(ResponseUtils.getResponseBody(0));
	}

	public void addproduct(List<Integer> productId, Integer id) {
		for (Integer product : productId) {
			DiscoverProduct discoverProduct = new DiscoverProduct();
			discoverProduct.setCreateTime(LocalDateTime.now());
			discoverProduct.setModifyTime(LocalDateTime.now());
			discoverProduct.setIsDeleted((short) 0);
			discoverProduct.setProductId(product);
			discoverProduct.setDiscoverId(id);
			discoverProductMapper.insert(discoverProduct);
		}

	}

	@RequestMapping(value = "/selectproductId", method = RequestMethod.GET)
	@ApiOperation(value = "查询用户订单商品", notes = "查询用户订单商品")
	public ResponseEntity<JSONObject> selectproductId(Integer userId) throws JSONException {
		ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
		List<String> status = new ArrayList<String>();
		status.add("complete");
		status.add("evaluate");
		List<Map<String, String>> products = new ArrayList<>();
		HfOrderExample hforderexample = new HfOrderExample();
		hforderexample.createCriteria().andUserIdEqualTo(userId).andOrderTypeEqualTo("nomalOrder")
				.andOrderStatusIn(status);
		List<HfOrder> hfOrders = hfOrderMapper.selectByExample(hforderexample);
		System.out.println(hfOrders);
		if (hfOrders.size() != 0) {
			hfOrders.forEach(hfOrder -> {
				HfOrderDetailExample hfOrderDetailexample = new HfOrderDetailExample();
				hfOrderDetailexample.createCriteria().andOrderIdEqualTo(hfOrder.getId());
				List<HfOrderDetail> hfOrderDetail = hfOrderDetailMapper.selectByExample(hfOrderDetailexample);
				HfGoods hfGoods = hfGoodMapper.selectByPrimaryKey(hfOrderDetail.get(0).getGoodsId());
				Product product = productMapper.selectByPrimaryKey(hfGoods.getProductId());
				Map<String, String> params = new HashMap<>();
				params.put("productId", String.valueOf(product.getId()));
				params.put("productName", product.getHfName());
				params.put("fileId", String.valueOf(product.getFileId()));
				int i = 0;
				products.add(i, params);
				i++;
			});
		}
		List<Map<String, String>> productMap = products.stream().distinct().collect(Collectors.toList());
		return builder.body(ResponseUtils.getResponseBody(productMap));
	}

//    @RequestMapping(value = "/selectDiscover", method = RequestMethod.GET)
//    @ApiOperation(value = "查询发现", notes = "查询发现")
//    public ResponseEntity<JSONObject> selectDiscover(Integer userId) throws JSONException {
//        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
//        List<DiscoverUser> discoverUserList = discoverDao.selectDiscoverAll(userId);
//        discoverUserList.forEach(discoverUser -> {
//            DiscoverPictrueExample discoverPictrueExample = new DiscoverPictrueExample();
//            discoverPictrueExample.createCriteria().andDiscoverIdEqualTo(discoverUser.getDiscoverId());
//            List<Integer> fileIds = new ArrayList<>();
//            List<DiscoverPictrue> discoverPictrueList= discoverPictrueMapper.selectByExample(discoverPictrueExample);
//            System.out.println(discoverPictrueList);
//            discoverPictrueList.forEach(discoverPictrue -> {
//                fileIds.add(discoverPictrue.getFileId());
//                System.out.println(fileIds);
//            });
//            discoverUser.setDiscoverFiles(fileIds);
//        });
//        return builder.body(ResponseUtils.getResponseBody(discoverUserList));
//    }

	@RequestMapping(value = "/addPictrue", method = RequestMethod.POST)
	@ApiOperation(value = "添加发现图片", notes = "添加发现图片")
	@Transactional(rollbackFor = {RuntimeException.class, Error.class})
	public ResponseEntity<JSONObject> addPictrue(Integer discoverId, MultipartFile multipartFile, String fileDescs)
			throws JSONException, IOException {
		ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
		FileMangeService fileMangeService = new FileMangeService();
		String arr[];
//		for (MultipartFile multipartFile : files) {
		arr = fileMangeService.uploadFile(multipartFile.getBytes(), String.valueOf(discoverId));
//        }
		FileDesc fileDesc = new FileDesc();
		fileDesc.setFileName(multipartFile.getName());
		fileDesc.setGroupName(arr[0]);
		fileDesc.setRemoteFilename(arr[1]);
		fileDesc.setUserId(discoverId);
		fileDesc.setCreateTime(LocalDateTime.now());
		fileDesc.setModifyTime(LocalDateTime.now());
		fileDesc.setIsDeleted((short) 0);
		fileDescMapper.insert(fileDesc);
		DiscoverPictrue discoverPictrue = new DiscoverPictrue();
		discoverPictrue.setFileName(multipartFile.getName());
		discoverPictrue.setFileId(fileDesc.getId());
		discoverPictrue.setFileDesc(fileDescs);
		discoverPictrue.setDiscoverId(discoverId);
		discoverPictrue.setIsDeleted((short) 0);
		discoverPictrue.setCreateTime(LocalDateTime.now());
		discoverPictrue.setModifyTime(LocalDateTime.now());
		discoverPictrueMapper.insert(discoverPictrue);
		return builder.body(ResponseUtils.getResponseBody(fileDesc.getId()));
	}

	@RequestMapping(value = "/delleteDiscover", method = RequestMethod.POST)
	@ApiOperation(value = "删除发现", notes = "删除发现")
	@Transactional(rollbackFor = {RuntimeException.class, Error.class})
	public ResponseEntity<JSONObject> delleteDiscover(Integer discoverId) throws JSONException {
		ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
		Discover discover = new Discover();
		discover.setId(discoverId);
		discover.setIsDeleted((short) 1);
		discoverMapper.updateByPrimaryKeySelective(discover);
		return builder.body(ResponseUtils.getResponseBody(0));
	}

}
