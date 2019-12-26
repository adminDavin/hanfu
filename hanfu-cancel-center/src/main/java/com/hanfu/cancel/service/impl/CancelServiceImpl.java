package com.hanfu.cancel.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hanfu.cancel.dao.CancelMapper;
import com.hanfu.cancel.dao.HfOrdersDetailMapper;
import com.hanfu.cancel.model.HfOrdersDetail;
import com.hanfu.cancel.model.record;
import com.hanfu.cancel.service.CancelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class CancelServiceImpl implements CancelService {
    @Autowired
    private CancelMapper cancelMapper;
    @Autowired
    private HfOrdersDetailMapper hfOrdersDetailMapper;

    @Override
    public List<record> select() {
        return cancelMapper.select();
    }

    @Override
    public List<record> selectDate(Date createData, Date createDate1) {
        return cancelMapper.selectDate(createData, createDate1);
    }

    @Override
    public List<record> selectRegion(String site) {
        return cancelMapper.selectRegion(site);
    }

    @Override
    public List<record> selectCancelId(int cancelId) {
        return cancelMapper.selectCancelId(cancelId);
    }

    @Override
    public PageInfo<record> Test(String site, Date createData, Date createDate1) {
        PageHelper.startPage(2, 1);
        List<record> recordList=cancelMapper.Test(site, createData, createDate1);
        PageInfo<record> pageInfo = new PageInfo<>(recordList);
        return pageInfo;
    }


}