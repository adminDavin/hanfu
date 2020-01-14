package com.hanfu.user.center.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanfu.user.center.dao.UsersMapper;
import com.hanfu.user.center.model.Users;
import com.hanfu.user.center.model.UsersExample;
import com.hanfu.user.center.request.LoginReuqest;
import com.hanfu.user.center.service.CommonService;


@Service("commonService")
public class CommonServiceImpl implements CommonService {

    @Autowired
    private UsersMapper usersMapper;


    @Override
    public List<Users> getUserList() {
        UsersExample example = new UsersExample();
        return usersMapper.selectByExample(example);
    }

}
