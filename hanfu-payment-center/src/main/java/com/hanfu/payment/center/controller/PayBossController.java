package com.hanfu.payment.center.controller;

import com.alibaba.fastjson.JSONObject;
import com.hanfu.payment.center.dao.PayBossMapper;
import com.hanfu.payment.center.model.PayBoss;
import com.hanfu.payment.center.model.PayBossExample;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@CrossOrigin
@RestController
@RequestMapping("/PayBoss")
@Api
public class PayBossController {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
@Autowired
private PayBossMapper payBossMapper;
    // 项目根路径下的目录  -- SpringBoot static 目录相当于是根路径下（SpringBoot 默认）
    public final static String UPLOAD_PATH_PREFIX = "payment/car";

    @ApiOperation(value = "证书上传", notes = "证书上传")
    @RequestMapping(value = "/payment", method = RequestMethod.POST)
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public String payment(MultipartFile uploadFile, HttpServletRequest request, PayBoss payBoss)
            throws JSONException, IOException {
        if(uploadFile.isEmpty()){
            //返回选择文件提示
            return "请选择上传文件";
        }
        PayBossExample payBossExample = new PayBossExample();
        payBossExample.createCriteria().andBossIdEqualTo(payBoss.getBossId()).andIsDeletedEqualTo((byte) 0);
        List<PayBoss> payBossList = payBossMapper.selectByExample(payBossExample);
        payBoss.setAppId(payBoss.getAppid());
        if (payBossList.size()==0){
            payBossMapper.insertSelective(payBoss);
        } else {
            payBoss.setId(payBossList.get(0).getId());
            payBossMapper.updateByPrimaryKeySelective(payBoss);
        }


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");
        //构建文件上传所要保存的"文件夹路径"--这里是相对路径，保存到项目根路径的文件夹下
        String realPath = new String("src/main/resources/" + UPLOAD_PATH_PREFIX);
        logger.info("-----------上传文件保存的路径【"+ realPath +"】-----------");
        String format = sdf.format(new Date());
        //存放上传文件的文件夹
        File file = new File(realPath + payBoss.getBossId());
        logger.info("-----------存放上传文件的文件夹【"+ file +"】-----------");
        logger.info("-----------输出文件夹绝对路径 -- 这里的绝对路径是相当于当前项目的路径而不是“容器”路径【"+ file.getAbsolutePath() +"】-----------");
        if(!file.isDirectory()){
            //递归生成文件夹
            file.mkdirs();
        }
        //获取原始的名字  original:最初的，起始的  方法是得到原来的文件名在客户机的文件系统名称
        String oldName = uploadFile.getOriginalFilename();
        logger.info("-----------文件原始的名字【"+ oldName +"】-----------");
        String newName = UUID.randomUUID().toString() + oldName.substring(oldName.lastIndexOf("."),oldName.length());
        logger.info("-----------文件要保存后的新名字【"+ newName +"】-----------");
        try {
            //构建真实的文件路径
            File newFile = new File(file.getAbsolutePath() + File.separator + newName);
            //转存文件到指定路径，如果文件名重复的话，将会覆盖掉之前的文件,这里是把文件上传到 “绝对路径”
            uploadFile.transferTo(newFile);
            String filePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/payment/" + format + newName;
            logger.info("-----------【"+ filePath +"】-----------");
            payBoss.setApiclientCert(UPLOAD_PATH_PREFIX+payBoss.getBossId()+"/"+newName);
            payBossMapper.updateByPrimaryKeySelective(payBoss);
            return filePath;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "上传失败!";
    }
}
