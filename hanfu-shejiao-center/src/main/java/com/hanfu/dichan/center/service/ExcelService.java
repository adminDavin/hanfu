package com.hanfu.dichan.center.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface ExcelService {

    /**
     * 提取excel中数据
     * @param file
     * @return
     */
    public Map<Integer, Map<Integer, Object>> addExcelInfo(MultipartFile file);

}
