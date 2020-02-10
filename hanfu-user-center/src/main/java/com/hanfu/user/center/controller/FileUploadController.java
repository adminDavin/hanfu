//
//package com.hanfu.user.center.controller;
//
//import java.io.IOException;
//import java.time.LocalDateTime;
//import java.util.List;
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.multipart.MultipartHttpServletRequest;
//import org.springframework.web.servlet.ModelAndView;
//import com.hanfu.utils.response.handler.ResponseEntity;
//import com.hanfu.utils.response.handler.ResponseEntity.BodyBuilder;
//import com.hanfu.utils.response.handler.ResponseUtils;
//import com.alibaba.fastjson.JSONObject;
//import com.hanfu.common.service.FileMangeService;
//import com.hanfu.user.center.dao.FileDescMapper;
//import com.hanfu.user.center.dao.HfAuthMapper;
//import com.hanfu.user.center.dao.HfUserMapper;
//import com.hanfu.user.center.manual.dao.UserDao;
//import com.hanfu.user.center.model.FileDesc;
//import com.hanfu.user.center.model.FileDescExample;
//import com.hanfu.user.center.model.HfUser;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//
//@Controller
//@Api
//@RequestMapping("/user/weChat")
//@CrossOrigin
//public class FileUploadController {
//    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
//    @Autowired
//    FileDescMapper fileDescMapper;
//    @Autowired
//    private HfUserMapper hfUserMapper;
//    @Resource
//    private RedisTemplate<String, Object> redisTemplate;
//    @Autowired
//    HfAuthMapper hfAuthMapper;
//    @Autowired
//    UserDao userDao;
//
//    @RequestMapping(value = "/uploadImage", method = {RequestMethod.POST, RequestMethod.GET})
//    public ModelAndView uploadImage(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        System.out.println("进入get方法！");
//
//        MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
//        MultipartFile multipartFile = req.getFile("file");
//
//        logger.info(JSONObject.toJSONString(multipartFile));
//        return null;
//    }
//
//    @RequestMapping(value = "/upload_avatar", method = RequestMethod.POST)
//    @ApiOperation(value = "上传头像", notes = "上传头像")
//    public ResponseEntity<JSONObject> uploadAvatar(@RequestParam(value = "file", required = false) MultipartFile file,
//                                                   @RequestParam(value = "userId", required = false) Integer userId) throws Exception {
//        BodyBuilder builder = ResponseUtils.getBodyBuilder();
//        FileMangeService fileMangeService = new FileMangeService();
//        String arr[];
//        if (file != null) {
//            arr = fileMangeService.uploadFile(file.getBytes(), String.valueOf(userId));
//            FileDescExample example = new FileDescExample();
//            example.createCriteria().andUserIdEqualTo(userId);
//            List<FileDesc> list = fileDescMapper.selectByExample(example);
//            if (list.isEmpty()) {
//                FileDesc fileDesc = new FileDesc();
//                fileDesc.setFileName(file.getName());
//                fileDesc.setGroupName(arr[0]);
//                fileDesc.setRemoteFilename(arr[1]);
//                fileDesc.setUserId(userId);
//                fileDesc.setCreateTime(LocalDateTime.now());
//                fileDesc.setModifyTime(LocalDateTime.now());
//                fileDesc.setIsDeleted((short) 0);
//                fileDescMapper.insert(fileDesc);
//                HfUser hfUser = hfUserMapper.selectByPrimaryKey(userId);
//                hfUser.setFileId(fileDesc.getId());
//                hfUserMapper.updateByPrimaryKey(hfUser);
//            } else {
//                FileDesc fileDesc = list.get(0);
//                fileDesc.setFileName(file.getName());
//                fileDesc.setGroupName(arr[0]);
//                fileDesc.setRemoteFilename(arr[1]);
//                fileDesc.setModifyTime(LocalDateTime.now());
//                fileDescMapper.updateByPrimaryKey(fileDesc);
//            }
//        }
//        return builder.body(ResponseUtils.getResponseBody(null));
//    }
//}
