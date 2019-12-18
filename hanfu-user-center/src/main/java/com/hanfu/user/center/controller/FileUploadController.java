
package com.hanfu.user.center.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.spec.InvalidParameterSpecException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Resource;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseEntity.BodyBuilder;
import com.hanfu.utils.response.handler.ResponseUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.domain.Activity;
import com.google.api.client.util.SecurityUtils;
import com.hanfu.common.service.FileMangeService;
import com.hanfu.user.center.dao.FileDescMapper;
import com.hanfu.user.center.dao.HfAuthMapper;
import com.hanfu.user.center.dao.HfUserMapper;
import com.hanfu.user.center.manual.dao.UserDao;
import com.hanfu.user.center.manual.model.ActivityUserInfo;
import com.hanfu.user.center.model.FileDesc;
import com.hanfu.user.center.model.HfAuth;
import com.hanfu.user.center.model.HfAuthExample;
import com.hanfu.user.center.model.HfUser;
import com.hanfu.user.center.model.HfUserExample;
import com.hanfu.user.center.request.UserInfoRequest;
import com.hanfu.user.center.response.handler.AuthKeyIsExistException;
import com.hanfu.user.center.response.handler.ParamInvalidException;
import com.hanfu.user.center.response.handler.UserNotExistException;
import com.hanfu.user.center.service.UserCenterService;
import com.hanfu.user.center.utils.GetMessageCode;
import com.hanfu.user.center.utils.UrlUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Controller
@Api
@RequestMapping("/user/weChat")
@CrossOrigin
public class FileUploadController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	FileDescMapper fileDescMapper;
	@Autowired
	private HfUserMapper hfUserMapper;
	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	@Autowired 
	HfAuthMapper hfAuthMapper;
	@Autowired
	UserDao userDao;
	
	
	@RequestMapping(value = "/uploadImage", method = { RequestMethod.POST,RequestMethod.GET})
    public ModelAndView uploadImage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("进入get方法！");
 
        MultipartHttpServletRequest req =(MultipartHttpServletRequest)request;
        MultipartFile multipartFile =  req.getFile("file");
 
        logger.info(JSONObject.toJSONString(multipartFile));
        return null;
    }
  

	 
	@RequestMapping(path = "/upload_avatar")
	@ApiOperation(value = "上传头像", notes = "上传头像")
	public ResponseEntity<JSONObject> uploadAvatar(@RequestParam(value = "file", required = false) MultipartFile file,
	        @RequestParam(value = "userId", required = false) Integer userId) throws Exception{
		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		FileMangeService fileMangeService = new FileMangeService();
		String arr[];
		arr = fileMangeService.uploadFile(file.getBytes(), String.valueOf(userId));
		FileDesc fileDesc = new FileDesc();
		fileDesc.setFileName(file.getName());
		fileDesc.setGroupName(arr[0]);
		fileDesc.setRemoteFilename(arr[1]);
		fileDesc.setUserId(userId);
		fileDesc.setCreateTime(LocalDateTime.now());
		fileDesc.setModifyTime(LocalDateTime.now());
		fileDesc.setIsDeleted((short) 0);
		fileDescMapper.insert(fileDesc);
		HfUser hfUser = new HfUser();
		hfUser.setFileId(fileDesc.getId());
		hfUserMapper.insert(hfUser);	
		return builder.body(ResponseUtils.getResponseBody(hfUserMapper.updateByPrimaryKeySelective(hfUser)));
	}
}