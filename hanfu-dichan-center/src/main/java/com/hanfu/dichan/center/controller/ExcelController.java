package com.hanfu.dichan.center.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.hanfu.dichan.center.dao.DcUserMapper;
import com.hanfu.dichan.center.model.DcUser;
import com.hanfu.dichan.center.model.DcUserExample;
import com.hanfu.dichan.center.service.ExcelService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.*;

@Api
@CrossOrigin
@RestController
@RequestMapping(value = "/excel")
public class ExcelController {

    @Autowired
    private ExcelService excelService;
    @Autowired
    private DcUserMapper dcUserMapper;

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
            byte ac= 0;
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
                int a= dcUserMapper.updateByPrimaryKeySelective(dcUser);
            }
        }
        //	自行添加处理，单纯测试则无须添加
        return excelMap;
    }

}

