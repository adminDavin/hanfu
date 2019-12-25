package com.hanfu.cancel.tool;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hanfu.cancel.model.HfUser;
import com.hanfu.cancel.model.Paging;

public class PageTool{
    public static void num(Paging paging){
        PageHelper.startPage(paging.getPageNum(), paging.getPageSize());
    }


}
