package com.hanfu.user.center.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseEntity.BodyBuilder;
import com.hanfu.utils.response.handler.ResponseUtils;
import com.alibaba.fastjson.JSONObject;
import com.google.inject.internal.util.Lists;
import com.hanfu.common.service.FileMangeService;
import com.hanfu.user.center.dao.FileDescMapper;
import com.hanfu.user.center.model.FileDesc;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@Api
@RequestMapping("/file")
@CrossOrigin
public class FileMangerController {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    FileDescMapper fileDescMapper;

    @RequestMapping(value = "/image", method = RequestMethod.GET)
    @ApiOperation(value = "上传文件", notes = "上传文件")
    public void getFile(@RequestParam(value = "userId", required = false) Integer userId,
            @RequestParam(value = "fileId", required = false) Integer fileId, HttpServletResponse response)
            throws Exception {
        FileDesc fileDesc = fileDescMapper.selectByPrimaryKey(fileId);
        byte[] file = FileMangeService.downloadFile(fileDesc.getGroupName(), fileDesc.getRemoteFilename());

        if (file != null) {
            BufferedImage readImg = ImageIO.read(new ByteArrayInputStream(file));
            ImageIO.write(readImg, "png", response.getOutputStream());
        }
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ApiOperation(value = "上传文件", notes = "上传文件")
    public ResponseEntity<JSONObject> upload(@RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam(value = "userId", required = false) Integer userId) throws Exception {
        BodyBuilder builder = ResponseUtils.getBodyBuilder();
        String[] arr = FileMangeService.uploadFile(file.getBytes(), String.valueOf(userId));

        FileDesc fileDesc = getFileDesc(file, userId, arr);
        fileDescMapper.insert(fileDesc);
        return builder.body(ResponseUtils.getResponseBody(fileDesc));
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ApiOperation(value = "上传文件", notes = "上传文件")
    public ResponseEntity<JSONObject> getList(@RequestParam(value = "userId", required = false) Integer userId) throws Exception {
        BodyBuilder builder = ResponseUtils.getBodyBuilder();

        List<FileDesc> list = fileDescMapper.selectByExample(null);
        return builder.body(ResponseUtils.getResponseBody(list));
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ApiOperation(value = "删除文件", notes = "删除文件")
    public ResponseEntity<JSONObject> delete(@RequestParam(value = "userId", required = false) Integer userId,
            @RequestParam(value = "fileId", required = false) Integer fileId) throws Exception {
        BodyBuilder builder = ResponseUtils.getBodyBuilder();
        FileDesc fileDesc = fileDescMapper.selectByPrimaryKey(fileId);
        try {
            FileMangeService.deleteFile(fileDesc.getGroupName(), fileDesc.getRemoteFilename());
        } catch(Exception e) {
            logger.error("delete remote file Failed", e);
        }
        return builder.body(ResponseUtils.getResponseBody(fileDescMapper.deleteByPrimaryKey(fileId)));
    }

    @RequestMapping(value = "/upload/batch", method = RequestMethod.POST)
    @ApiOperation(value = "上传文件", notes = "上传文件")
    public ResponseEntity<JSONObject> uploadBatch(
            @RequestParam(value = "files", required = false) MultipartFile[] files,
            @RequestParam(value = "userId", required = false) Integer userId) throws Exception {
        BodyBuilder builder = ResponseUtils.getBodyBuilder();

        List<MultipartFile> fileList = Lists.newArrayList(files);
        List<FileDesc> fileDescs = fileList.stream().map(file -> {
            try {
                String[] fileInfo;
                fileInfo = FileMangeService.uploadFile(file.getBytes(), String.valueOf(userId));
                FileDesc fileDesc = getFileDesc(file, userId, fileInfo);
                fileDescMapper.insert(fileDesc);
                return fileDesc;
            } catch (IOException e) {
                logger.error("uploadBatch failed", e);
                e.printStackTrace();
                return null;
            }

        }).collect(Collectors.toList());

        return builder.body(ResponseUtils.getResponseBody(fileDescs));
    }

    private FileDesc getFileDesc(MultipartFile file, Integer userId, String[] arr) {
        FileDesc fileDesc = new FileDesc();
        fileDesc.setFileName(file.getName());
        fileDesc.setGroupName(arr[0]);
        fileDesc.setRemoteFilename(arr[1]);
        fileDesc.setUserId(userId);
        fileDesc.setCreateTime(LocalDateTime.now());
        fileDesc.setModifyTime(LocalDateTime.now());
        fileDesc.setIsDeleted((short) 0);
        return fileDesc;
    }
}
