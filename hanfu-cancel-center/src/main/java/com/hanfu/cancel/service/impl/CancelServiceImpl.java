package com.hanfu.cancel.service.impl;

import com.hanfu.cancel.dao.CancelMapper;
import com.hanfu.cancel.dao.HfOrdersDetailMapper;
import com.hanfu.cancel.model.record;
import com.hanfu.cancel.service.CancelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CancelServiceImpl implements CancelService {
    @Autowired
    private CancelMapper cancelMapper;
    @Autowired
    private HfOrdersDetailMapper hfOrdersDetailMapper;
//    @Autowired
//    private PermissionService permissionService;

    @Override
    public List<record> select() {
//        permissionService.test();//7889789798798798798789
//        permissionService.test();
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
    public List<record> Test(String site, Date createData, Date createDate1) {
        List<record> recordList = cancelMapper.Test(site, createData, createDate1);
        return recordList;
    }
}
