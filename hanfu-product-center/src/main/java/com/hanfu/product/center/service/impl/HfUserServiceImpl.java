package com.hanfu.product.center.service.impl;

import com.hanfu.product.center.dao.HfUserGroupMapper;
import com.hanfu.product.center.model.HfUser;
import com.hanfu.product.center.service.HfUserService;
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
    HfUserGroupMapper Mapper;
    @Override
    public HfUser selectByPrimaryKey(Integer id) {
        return Mapper.selectByPrimaryKey(id);
    }
}
