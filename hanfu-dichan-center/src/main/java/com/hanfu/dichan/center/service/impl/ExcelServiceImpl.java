package com.hanfu.dichan.center.service.impl;


import com.hanfu.dichan.center.service.ExcelService;
import com.hanfu.dichan.center.utils.ExcelUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Service
public class ExcelServiceImpl implements ExcelService {

    @Override
    public Map<Integer, Map<Integer, Object>> addExcelInfo(MultipartFile file) {
        Map<Integer, Map<Integer,Object>> map = new HashMap<Integer, Map<Integer,Object>>(5);
        try {
            map = ExcelUtil.readExcelContentz(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //excel数据存在map里，map.get(0).get(0)为excel第1行第1列的值，此处可对数据进行处理
        return map;
    }

}

