package com.hanfu.cancel.service.impl;

import com.hanfu.cancel.dao.CancelMapper;
import com.hanfu.cancel.model.record;
import com.hanfu.cancel.service.CancelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class CancelServiceImpl implements CancelService {
    @Autowired
    private CancelMapper cancelMapper;
    @Override
    public List<record> select() {
        return cancelMapper.select();
    }

    @Override
    public List<record> selectDate(LocalDateTime createData, LocalDateTime create1) {
        return cancelMapper.selectDate(createData,create1);
    }

    @Override
    public List<record> selectRegion(String site) {
        return cancelMapper.selectRegion(site);
    }

    @Override
    public List<record> selectCancelId(int cancelId) {
        return cancelMapper.selectCancelId(cancelId);
    }
}
