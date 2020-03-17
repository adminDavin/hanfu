//package com.hanfu.user.center.controller;
//
//import com.alibaba.fastjson.JSONObject;
//import com.hanfu.common.service.FileMangeService;
//import com.hanfu.user.center.config.PermissionConstants;
//import com.hanfu.user.center.dao.AuthorizationMapper;
//import com.hanfu.user.center.dao.FileMapper;
//import com.hanfu.user.center.model.Authorization;
//import com.hanfu.user.center.model.FileDesc;
//import com.hanfu.user.center.service.RequiredPermission;
//import com.hanfu.utils.response.handler.ResponseEntity;
//import com.hanfu.utils.response.handler.ResponseUtils;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//import tk.mybatis.mapper.entity.Example;
//
//import javax.imageio.ImageIO;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.awt.image.BufferedImage;
//import java.io.ByteArrayInputStream;
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.UUID;
//
//@RestController
//@Api
//@RequestMapping("/accredit")
//@CrossOrigin
//public class accreditController {
//    @Autowired
//    private AuthorizationMapper authorizationMapper;
//    @Autowired
//    private FileMapper fileMapper;
//    @RequiredPermission(PermissionConstants.ADMIN_PRODUCT_LIST)
//    @RequestMapping(path = "/insertTest", method = RequestMethod.GET)
//    @ApiOperation(value = "insertTest", notes = "insertTest")
//    public ResponseEntity<JSONObject> insertTest(Authorization authorization, HttpServletResponse response, HttpServletRequest request) throws Exception {
//        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
//        authorizationMapper.insert(authorization);
//        return builder.body(ResponseUtils.getResponseBody("成功"));
//    }
//
////    @RequestMapping(path = "/selectAccredit", method = RequestMethod.GET)
////    @ApiOperation(value = "员工查询", notes = "员工查询")
////    public ResponseEntity<JSONObject> selectAccredit() throws Exception {
////        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
////        return builder.body(ResponseUtils.getResponseBody(authorizationMapper.selectAll()));
////    }
//
//    @RequestMapping(path = "/deleteAccredit", method = RequestMethod.GET)
//    @ApiOperation(value = "员工删除", notes = "员工删除")
//    public ResponseEntity<JSONObject> deleteAccredit(int id) throws Exception {
//        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
//        authorizationMapper.deleteByPrimaryKey(id);
//        return builder.body(ResponseUtils.getResponseBody("成功"));
//    }
//
//    @RequestMapping(value = "/deleteBatchCancel", method = RequestMethod.GET)
//    @ApiOperation(value = "批量删除", notes = "批量删除")
//    public ResponseEntity<JSONObject> deleteBatchCancel(@SuppressWarnings("rawtypes") @RequestParam("id") List id) throws Exception {
//        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
//        System.out.println(id);
//        if (id == null) {
//            builder.body(ResponseUtils.getResponseBody("请选择"));
//        }
//        for (int i = 0; i < id.size(); i++) {
//            int cancelID = Integer.parseInt(id.get(i).toString());
//            System.out.println(cancelID);
//            Authorization authorization = authorizationMapper.selectByPrimaryKey(cancelID);
//            System.out.println(authorization);
//            authorizationMapper.deleteByPrimaryKey(cancelID);
//        }
//        return builder.body(ResponseUtils.getResponseBody("成功"));
//    }
//
//
//    @RequestMapping(path = "/insertAccredit", method = RequestMethod.POST)
//    @ApiOperation(value = "新增人员", notes = "新增人员")
//    public ResponseEntity<JSONObject> insertAccredit(Authorization authorization, MultipartFile fileInfo) throws Exception {
////        System.out.println(file.getBytes());
//        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
//        String uuid = UUID.randomUUID().toString(); //转化为String对象
//            System.out.println(uuid); //打印UUID
//            uuid = uuid.replace("-", "");//因为UUID本身为32位只是生成时多了“-”，所以将它们去点就可
//                System.out.println(uuid);
//        Integer fileId = null;
//        FileMangeService fileMangeService = new FileMangeService();
//        String arr[];
//        arr = fileMangeService.uploadFile(fileInfo.getBytes(),"-1");
//        Example example = new Example(FileDesc.class);
//        Example.Criteria criteria = example.createCriteria();
//        criteria.andEqualTo("fileName",authorization.getEmployeeCode());
//        List<FileDesc> list = fileMapper.selectByExample(example);
//        System.out.println(list+"1231111111");
//            FileDesc fileDesc = new FileDesc();
//            fileDesc.setFileName(authorization.getEmployeeCode());
//            fileDesc.setGroupName(arr[0]);
//            fileDesc.setRemoteFilename(arr[1]);
//            fileDesc.setUserId(-1);
//            fileDesc.setCreateTime(LocalDateTime.now());
//            fileDesc.setModifyTime(LocalDateTime.now());
//            fileDesc.setIsDeleted((short) 0);
//            fileMapper.insert(fileDesc);
//            fileId = fileDesc.getId();
//            System.out.println(fileDesc.getId()+"-------"+"1234567890");
//
////        else {
////            FileDesc fileDesc = list.get(0);
////            fileMangeService.deleteFile(fileDesc.getGroupName(),fileDesc.getRemoteFilename() );
////            fileDesc.setGroupName(arr[0]);
////            fileDesc.setRemoteFilename(arr[1]);
////            fileDesc.setModifyTime(LocalDateTime.now());
////            fileMapper.updateByPrimaryKey(fileDesc);
////            fileId = fileDesc.getId();
////        }
//            authorization.setFileId(fileId);
//        authorization.setModifyDate(LocalDateTime.now());
//        authorization.setCreateDate(LocalDateTime.now());
//            authorizationMapper.insert(authorization);
//
//        return builder.body(ResponseUtils.getResponseBody("成功"));
//    }
//
//    @RequestMapping(path = "/updateAccredit", method = RequestMethod.POST)
//    @ApiOperation(value = "修改人员", notes = "修改人员")
//    public ResponseEntity<JSONObject> updateAccredit(Authorization authorization, MultipartFile file) throws Exception {
//        System.out.println(file.getBytes());
//        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
//        String uuid = UUID.randomUUID().toString(); //转化为String对象
//        System.out.println(uuid); //打印UUID
//        uuid = uuid.replace("-", "");//因为UUID本身为32位只是生成时多了“-”，所以将它们去点就可
//        System.out.println(uuid);
//        FileMangeService fileMangeService = new FileMangeService();
//        String arr[];
//        arr = fileMangeService.uploadFile(file.getBytes(), uuid);
//        FileDesc fileDesc = new FileDesc();
//        fileDesc.setFileName(file.getName());
//        fileDesc.setGroupName(arr[0]);
//        fileDesc.setRemoteFilename(arr[1]);
//        fileDesc.setCreateTime(LocalDateTime.now());
//        fileDesc.setModifyTime(LocalDateTime.now());
//        fileDesc.setIsDeleted((short) 0);
//        fileMapper.insert(fileDesc);
//        int fileId=fileDesc.getId();
//        authorization.setId(authorization.getId());
//        authorization.setFileId(fileId);
//        authorization.setModifyDate(LocalDateTime.now());
//        authorizationMapper.updateByPrimaryKeySelective(authorization);
//        return builder.body(ResponseUtils.getResponseBody("成功"));
//    }
//    @RequestMapping(path = "/select", method = RequestMethod.GET)
//    @ApiOperation(value = "图片查询", notes = "图片查询")
//    public void select(int id, HttpServletResponse response) throws Exception {
//        Authorization authorization =authorizationMapper.selectByPrimaryKey(id);
//        System.out.println(authorization.getFileId());
//        FileDesc fileDesc = fileMapper.selectByPrimaryKey(authorization.getFileId());
//        System.out.println(fileDesc);
//        FileMangeService fileManageService = new FileMangeService();
//        byte[] file = fileManageService.downloadFile(fileDesc.getGroupName(), fileDesc.getRemoteFilename());
//        if (file != null) {
//            BufferedImage readImg = ImageIO.read(new ByteArrayInputStream(file));
//            ImageIO.write(readImg, "png", response.getOutputStream());
//        }
//    }
//}
