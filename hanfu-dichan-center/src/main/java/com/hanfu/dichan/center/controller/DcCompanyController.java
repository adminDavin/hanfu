package com.hanfu.dichan.center.controller;

import com.alibaba.fastjson.JSONObject;
import com.hanfu.common.service.FileMangeService;
import com.hanfu.dichan.center.dao.DcFileDescMapper;
import com.hanfu.dichan.center.model.DcFileDesc;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@RestController
@Api
@RequestMapping("/company")
@CrossOrigin
public class DcCompanyController {
    @Autowired
    private DcFileDescMapper dcFileDescMapper;

    @ApiOperation(value = "上传图片", notes = "上传图片")
    @RequestMapping(value = "/fileUpLoad", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> fileUpLoad(MultipartFile file) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        String arr[];
        FileMangeService fileMangeService = new FileMangeService();
        arr = fileMangeService.uploadFile(file.getBytes(), String.valueOf("-1"));
        DcFileDesc fileDesc = new DcFileDesc();
        fileDesc.setFileName(file.getName());
        fileDesc.setGroupName(arr[0]);
        fileDesc.setRemoteFilename(arr[1]);
        fileDesc.setUserId(-1);
        fileDesc.setCreateTime(LocalDateTime.now());
        fileDesc.setModifyTime(LocalDateTime.now());
        fileDesc.setIsDeleted((short) 0);
        dcFileDescMapper.insert(fileDesc);
        String a = "https://www.tjsichuang.cn:1443/api/dichan/category/getPicture?id=";
        return builder.body(ResponseUtils.getResponseBody(a+fileDesc.getId()));
    }
}
