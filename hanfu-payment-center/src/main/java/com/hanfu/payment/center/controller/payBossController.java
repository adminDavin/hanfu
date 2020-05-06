package com.hanfu.payment.center.controller;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.internal.util.file.IOUtils;
import com.hanfu.common.service.FileMangeService;
import com.hanfu.payment.center.dao.FileDescMapper;
import com.hanfu.payment.center.dao.PayBossMapper;
import com.hanfu.payment.center.dao.PayOrderMapper;
import com.hanfu.payment.center.model.FileDesc;
import com.hanfu.payment.center.model.PayBoss;
import com.hanfu.payment.center.model.PayBossExample;
import com.hanfu.payment.center.model.PayOrder;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.time.LocalDateTime;
import java.util.List;

import static java.awt.desktop.UserSessionEvent.Reason.LOCK;

@RestController
@Api
@RequestMapping("/PayBoss")
@CrossOrigin
public class payBossController {
    @Autowired
    private FileDescMapper fileDescMapper;
    @Autowired
    private PayBossMapper payBossMapper;

    @ApiOperation(value = "证书", notes = "证书")
    @RequestMapping(value = "/appAPI", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> selectCoupons(Integer bossId, MultipartFile file)
            throws JSONException, org.springframework.boot.configurationprocessor.json.JSONException, IOException {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        String arr[];
        FileMangeService fileMangeService = new FileMangeService();
        arr = fileMangeService.uploadFile(file.getBytes(), String.valueOf("-1"));
        FileDesc fileDesc = new FileDesc();
        fileDesc.setFileName(file.getName());
        fileDesc.setGroupName(arr[0]);
        fileDesc.setRemoteFilename(arr[1]);
        fileDesc.setUserId(-1);
        fileDesc.setCreateTime(LocalDateTime.now());
        fileDesc.setModifyTime(LocalDateTime.now());
        fileDesc.setIsDeleted((short) 0);
        fileDescMapper.insert(fileDesc);
        PayBoss payBoss = new PayBoss();
        payBoss.setBossId(bossId);
        payBoss.setApiclientCert(String.valueOf(fileDesc.getId()));
        payBossMapper.insertSelective(payBoss);
        payBossMapper.selectByPrimaryKey(1);
        return builder.body(ResponseUtils.getResponseBody(0));
    }

    @ApiOperation(value = "获取证书", notes = "获取证书")
    @RequestMapping(value = "/getVedio", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "fileId", value = "文件id", required = true, type = "Integer")})
    public void getVedio(@RequestParam(name = "fileId") Integer fileId, HttpServletResponse response) throws Exception {
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Content-Type", "audio/mp4;charset=UTF-8");
        FileDesc fileDesc = fileDescMapper.selectByPrimaryKey(fileId);
        if (fileDesc == null) {
            throw new Exception("file not exists");
        }
        FileMangeService fileManageService = new FileMangeService();
            byte[] file = fileManageService.downloadFile(fileDesc.getGroupName(), fileDesc.getRemoteFilename());
//            ByteArrayInputStream stream = new ByteArrayInputStream(file);
//            BufferedImage readImg = ImageIO.read(stream);
//            stream.reset();
        PayBossExample payBossExample = new PayBossExample();
        payBossExample.createCriteria().andBossIdEqualTo(1);
        System.out.println(payBossMapper.selectByExample(payBossExample).get(0).getAppid());
            OutputStream outputStream = response.getOutputStream();
//            ImageIO.write(readImg, "png", outputStream);
            IOUtils.write(file, outputStream);
            outputStream.close();
    }
}

