package com.hanfu.group.center.service.impl;

import com.hanfu.group.center.manual.dao.HfGoodsMapper;
import com.hanfu.group.center.manual.dao.HfUserMapper;
import com.hanfu.group.center.manual.model.HfUser;
import com.hanfu.group.center.service.HfUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author:gyj
 * @date: 2019/12/26
 * @time: 14:05
 */
@Service
@Transactional
public class HfUserServiceImpl implements HfUserService {
    @Autowired
    HfUserMapper Mapper;
    @Override
    public HfUser selectByPrimaryKey(Integer id) {
        return Mapper.selectByPrimaryKey(id);
    }
}
