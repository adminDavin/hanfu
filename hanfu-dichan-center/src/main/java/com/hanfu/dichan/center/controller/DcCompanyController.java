package com.hanfu.dichan.center.controller;

import com.alibaba.fastjson.JSONObject;
import com.hanfu.common.service.FileMangeService;
import com.hanfu.dichan.center.dao.DcFileDescMapper;
import com.hanfu.dichan.center.dao.DcGeneralFileMapper;
import com.hanfu.dichan.center.dao.DcRichTextMapper;
import com.hanfu.dichan.center.dao.DcUserMapper;
import com.hanfu.dichan.center.manual.model.UserExcel;
import com.hanfu.dichan.center.model.*;
import com.hanfu.dichan.center.service.ExcelService;
import com.hanfu.dichan.center.utils.ExcelExport2;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@RestController
@Api
@RequestMapping("/company")
@CrossOrigin
public class DcCompanyController {
    @Autowired
    private DcFileDescMapper dcFileDescMapper;
@Autowired
private DcRichTextMapper dcRichTextMapper;
@Autowired
private DcGeneralFileMapper dcGeneralFileMapper;
    @Autowired
    private ExcelService excelService;
    @Autowired
    private DcUserMapper dcUserMapper;

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
    @ApiOperation(value = "删除富文本", notes = "删除富文本")
    @RequestMapping(value = "/deletedText", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> deletedText(Integer productId,String type) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        DcRichTextExample dcRichTextExample = new DcRichTextExample();
        dcRichTextExample.createCriteria().andProjectIdEqualTo(productId).andTextTypeEqualTo(type);
        DcRichText dcRichText = new DcRichText();
        dcRichText.setIsDeleted((byte) 0);
        dcRichTextMapper.updateByExampleSelective(dcRichText,dcRichTextExample);
        return builder.body(ResponseUtils.getResponseBody(0));
    }
    @ApiOperation(value = "修改富文本", notes = "修改富文本")
    @RequestMapping(value = "/updateText", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> updateText(DcRichText dcRichText) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        System.out.println(dcRichText.getRichText().replace('\"','\''));
        dcRichText.setRichText(dcRichText.getRichText().replace('\"','\''));

        dcRichTextMapper.updateByPrimaryKeySelective(dcRichText);
        return builder.body(ResponseUtils.getResponseBody(0));
    }
    @ApiOperation(value = "添加项目概况图", notes = "添加项目概况图")
    @RequestMapping(value = "/addProject", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> addProject(MultipartFile file,Integer projectId) throws Exception {
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
        DcGeneralFile dcGeneralFile = new DcGeneralFile();
        dcGeneralFile.setFileId(fileDesc.getId());
        dcGeneralFile.setGeneralId(projectId);
        dcGeneralFile.setFileType(General.GeneralTypeEnum.GENERAL_TYPE_ENUM.getGeneralType());
        dcGeneralFileMapper.insertSelective(dcGeneralFile);
        return builder.body(ResponseUtils.getResponseBody(0));
    }
    @ApiOperation(value = "查询项目概况图", notes = "查询项目概况图")
    @RequestMapping(value = "/selectProject", method = RequestMethod.GET)
    public ResponseEntity<JSONObject> selectProject(Integer projectId) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        DcGeneralFileExample dcGeneralFileExample = new DcGeneralFileExample();
        dcGeneralFileExample.createCriteria().andFileTypeEqualTo(General.GeneralTypeEnum.GENERAL_TYPE_ENUM.getGeneralType()).andIsDeletedEqualTo((byte) 0).andGeneralIdEqualTo(projectId);
        List<DcGeneralFile> generalFiles = dcGeneralFileMapper.selectByExample(dcGeneralFileExample);
        List<String> fileList = new ArrayList<>();
        generalFiles.forEach(dcGeneralFile -> {
            fileList.add("https://www.tjsichuang.cn:1443/api/dichan/category/getPicture?id="+dcGeneralFile.getFileId());
        });
        return builder.body(ResponseUtils.getResponseBody(fileList));
    }
    @ApiOperation(value = "删除项目概况图", notes = "删除项目概况图")
    @RequestMapping(value = "/deletedProject", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> deletedProject(Integer fileId) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        DcFileDesc fileDesc = dcFileDescMapper.selectByPrimaryKey(fileId);
        FileMangeService fileMangeService = new FileMangeService();
        fileMangeService.deleteFile(fileDesc.getGroupName(), fileDesc.getRemoteFilename());
        dcFileDescMapper.deleteByPrimaryKey(fileId);
        DcGeneralFileExample dcGeneralFileExample = new DcGeneralFileExample();
        dcGeneralFileExample.createCriteria().andFileIdEqualTo(fileId);
        return builder.body(ResponseUtils.getResponseBody(dcGeneralFileMapper.deleteByExample(dcGeneralFileExample)));
    }
    @PostMapping()
    public Object excelOperation(@RequestParam("file") MultipartFile file) {
        Map<Integer, Map<Integer, Object>> excelMap = excelService.addExcelInfo(file);
        System.out.println(excelMap);
        Iterator<Map.Entry<Integer, Map<Integer, Object>>> it = excelMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Integer, Map<Integer, Object>> entry = it.next();
//            System.out.println("key:" + entry.getKey() + "  key:" + entry.getValue());
            Iterator<Map.Entry<Integer, Object>> its = entry.getValue().entrySet().iterator();
            Map.Entry<Integer, Object> entry1=null;
            List list = new ArrayList();
            while (its.hasNext()){
                entry1 = its.next();
                list.add(entry1.getValue());
            }
//            DecimalFormat df = new DecimalFormat("#");
            BigDecimal bd = new BigDecimal((String) list.get(0));
            DcUser dcUser = new DcUser();
//            System.out.println(list.get(0));
            dcUser.setPhone(bd.toPlainString());
            dcUser.setRealName(String.valueOf(list.get(1)));
            DcUserExample dcUserExample = new DcUserExample();
            dcUserExample.createCriteria().andPhoneEqualTo(bd.toPlainString());
            List<DcUser> dcUsers = dcUserMapper.selectByExample(dcUserExample);
            int ab= Integer.parseInt(list.get(2).toString());
            byte ac= (byte) ab;
            if ((list.get(2)).equals("0.0")){
                ac = 0;
            } else if ((list.get(2)).equals("1.0")) {
                ac = 1;
            }
            if (dcUsers.size()==0){
                dcUserMapper.insertSelective(dcUser);
            } else {
                System.out.println(dcUsers.get(0).getId()+"---"+ac);
                dcUser.setIdDeleted(ac);
                dcUser.setId(dcUsers.get(0).getId());
                dcUser.setRealName(String.valueOf(list.get(1)));
                dcUserMapper.updateByPrimaryKeySelective(dcUser);
            }
        }
        //	自行添加处理，单纯测试则无须添加
        return excelMap;
    }
    @ApiOperation(value = "导出", notes = "导出")
    @RequestMapping(value = "/selectTests", method = RequestMethod.GET)
    public void selectTests(HttpServletResponse response) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
//创建一个数组用于设置表头
        String[] arr = new String[]{"phone","name","state","remark"};
        DcUserExample dcUserExample = new DcUserExample();
        List<DcUser> dcUsers = dcUserMapper.selectByExample(dcUserExample);
        List<UserExcel> userExcels = new ArrayList<>();
        dcUsers.forEach(dcUser -> {
            UserExcel userExcel = new UserExcel();
            userExcel.setName(dcUser.getRealName());
            userExcel.setPhone(dcUser.getPhone());
            userExcel.setState(Integer.valueOf(dcUser.getIdDeleted()));
            if (dcUser.getIdDeleted().equals(0)){
                userExcel.setRemark("在职");
            } else {
                userExcel.setRemark("离职");
            }
            userExcels.add(userExcel);
        });
        //调用Excel导出工具类
        ExcelExport2.export(response,userExcels,arr);

    }
}
