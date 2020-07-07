package com.hanfu.dichan.center.controller;

import com.alibaba.fastjson.JSONObject;
import com.hanfu.common.service.FileMangeService;
import com.hanfu.dichan.center.dao.DcFileDescMapper;
import com.hanfu.dichan.center.dao.DcRichTextMapper;
import com.hanfu.dichan.center.model.DcFileDesc;
import com.hanfu.dichan.center.model.DcRichText;
import com.hanfu.dichan.center.model.DcRichTextExample;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@Api
@RequestMapping("/company")
@CrossOrigin
public class DcCompanyController {
    @Autowired
    private DcFileDescMapper dcFileDescMapper;
@Autowired
private DcRichTextMapper dcRichTextMapper;
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
    @ApiOperation(value = "添加富文本", notes = "添加富文本")
    @RequestMapping(value = "/addText", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> addText(DcRichText dcRichText) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        System.out.println(dcRichText.getRichText().replace('\"','\''));
        dcRichText.setRichText(dcRichText.getRichText().replace('\"','\''));

//        dcRichText.setRichText(StringEscapeUtils.escapeHtml(dcRichText.getRichText()));
//        System.out.println(dcRichText.getRichText());
        dcRichTextMapper.insertSelective(dcRichText);
        return builder.body(ResponseUtils.getResponseBody(0));
    }
    @ApiOperation(value = "查询富文本", notes = "查询富文本")
    @RequestMapping(value = "/selectText", method = RequestMethod.GET)
    public ResponseEntity<JSONObject> selectText(Integer projectId, String type) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        DcRichTextExample dcRichTextExample = new DcRichTextExample();
        dcRichTextExample.createCriteria().andProjectIdEqualTo(projectId).andTextTypeEqualTo(type).andIsDeletedEqualTo((byte) 0);
        String a = dcRichTextMapper.selectByExample(dcRichTextExample).get(0).getRichText();
        System.out.println(a);
//        String jieguo = a.substring(a.indexOf("")+1,a.indexOf("\"));
//        JSONObject jsonObject1 =JSONObject.parseObject(dcRichTextMapper.selectByExample(dcRichTextExample).toString());
//        Object myclass = JSONObject.parseObject(a , Object.class);// jsonStr 是String类型。
//        String newJson = StringEscapeUtils.unescapeHtml(a);
//        System.out.println(newJson);
        return builder.body(ResponseUtils.getResponseBody(a));
    }
}
