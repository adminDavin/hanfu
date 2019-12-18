package com.hanfu.cancel.service.impl;

import com.hanfu.cancel.dao.CancelMapper;
import com.hanfu.cancel.model.record;
import com.hanfu.cancel.service.CancelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CancelServiceImpl implements CancelService {
    @Autowired
    private CancelMapper cancelMapper;
    @Override
    public List<record> select() {
        return cancelMapper.select();
    }
}
