package com.hanfu.order.center.controller;


import java.time.LocalDateTime;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.hanfu.common.service.FileMangeService;
import com.hanfu.order.center.dao.FileDescMapper;
import com.hanfu.order.center.dao.hfEvaluateMapper;
import com.hanfu.order.center.model.FileDesc;
import com.hanfu.order.center.model.hfEvaluate;
import com.hanfu.order.center.model.hfEvaluateExample;
import com.hanfu.order.center.request.Evaluate;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseUtils;
import com.hanfu.utils.response.handler.ResponseEntity.BodyBuilder;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
@RequestMapping("/message")
@Api
public class MessageController {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    FileDescMapper fileDescMapper;
    @Autowired
    hfEvaluateMapper hfEvaluateMapper1;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    
    @SuppressWarnings("static-access")
	@ApiOperation(value = "添加评价", notes = "添加评价")
    @RequestMapping(value = "/insertReply", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "evaluate", value = "评价", required = false, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "orderId", value = "订单Id", required = false, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户Id", required = false, type = "Integer"),
    })
    public ResponseEntity<JSONObject> insertReply(MultipartFile fileInfo, String evaluate, Integer orderId, Integer userId)
            throws JSONException, Exception {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        if (orderId == null) {
            return builder.body(ResponseUtils.getResponseBody("没有任何评价"));
        }
        hfEvaluate hfEvaluate1 = new hfEvaluate();
        hfEvaluate1.setUserId(userId);
        hfEvaluate1.setOrderId(orderId);
        hfEvaluate1.setCreateTime(LocalDateTime.now());
        hfEvaluate1.setModifyTime(LocalDateTime.now());
        hfEvaluate1.setIsDeleted((short)0);
		FileMangeService fileMangeService = new FileMangeService();
		String arr[];
			arr = fileMangeService.uploadFile(fileInfo.getBytes(), String.valueOf(userId));
			FileDesc fileDesc = new FileDesc();
			fileDesc.setFileName(fileInfo.getName());
			fileDesc.setGroupName(arr[0]);
			fileDesc.setRemoteFilename(arr[1]);
			fileDesc.setUserId(userId);
			fileDesc.setCreateTime(LocalDateTime.now());
			fileDesc.setModifyTime(LocalDateTime.now());
			fileDesc.setIsDeleted((short) 0);
			fileDescMapper.insert(fileDesc);
			hfEvaluate1.setFileId(fileDesc.getId());
			hfEvaluate1.setEvaluate(evaluate);
        return builder.body(ResponseUtils.getResponseBody(hfEvaluateMapper1.insertSelective(hfEvaluate1)));
    }

    @ApiOperation(value = "查看评价", notes = "查看评价")
    @RequestMapping(value = "/SeekReply", method = RequestMethod.GET)
    public ResponseEntity<JSONObject> SeekReply(Evaluate evaluate)
            throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        hfEvaluateExample example = new hfEvaluateExample();
        if(evaluate.getUserId() != null) {
        	   example.createCriteria().andUserIdEqualTo(evaluate.getUserId());
               return builder.body(ResponseUtils.getResponseBody(hfEvaluateMapper1.selectByExample(example)));
        }else {
             if(evaluate.getOrderId() != null) {
            	 example.createCriteria().andOrderIdEqualTo(evaluate.getOrderId());
            	 return builder.body(ResponseUtils.getResponseBody(hfEvaluateMapper1.selectByExample(example)));
             }
        }
        return builder.body(ResponseUtils.getResponseBody("没有任何评价"));
    }

    @ApiOperation(value = "评价回复", notes = "评价回复")
    @RequestMapping(value = "/queryReply", method = RequestMethod.GET)
    public ResponseEntity<JSONObject> queryReply(Evaluate evaluate)
            throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        hfEvaluate hfevaluate2 = new hfEvaluate();
        hfEvaluate hfevaluate1 = hfEvaluateMapper1.selectByPrimaryKey(evaluate.getId());
        hfevaluate2.setEvaluate(evaluate.getEvaluate());
        hfevaluate2.setOrderId(hfevaluate1.getOrderId());
        hfevaluate2.setCreateTime(LocalDateTime.now());
        hfevaluate2.setModifyTime(LocalDateTime.now());
        hfevaluate2.setIsDeleted((short) 0);
        hfEvaluateMapper1.insertSelective(hfevaluate2);
        return builder.body(ResponseUtils.getResponseBody(hfEvaluateMapper1.insertSelective(hfevaluate2)));
    }
}
