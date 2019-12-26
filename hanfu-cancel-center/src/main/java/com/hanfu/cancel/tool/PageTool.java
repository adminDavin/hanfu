package com.hanfu.cancel.tool;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hanfu.cancel.model.HfUser;
import com.hanfu.cancel.model.Paging;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseUtils;
import org.springframework.boot.configurationprocessor.json.JSONException;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PageTool {
    public static void num(Paging paging){
        if (paging.getPageNum()==null||paging.getPageSize()==null){
            paging.setPageNum(1);
            paging.setPageSize(10000000);
        }
        PageHelper.startPage(paging.getPageNum(), paging.getPageSize());
    }
}
