package com.hanfu.user.center.manual.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.alibaba.fastjson.JSON;
import com.hanfu.user.center.Application;
import com.hanfu.user.center.manual.dao.ManualDao;

@SpringBootTest(classes = Application.class)
public class UserServiceTest {

    @Autowired
    private ManualDao dbDao;

//	@Test
//	public void getSimpleUserInfo() {
//		System.out.println(JSON.toJSON(dbDao.getSimpleUserInfo()));
//	}
}
