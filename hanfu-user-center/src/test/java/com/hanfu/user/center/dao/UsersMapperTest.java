package com.hanfu.user.center.dao;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hanfu.user.center.Application;
import com.hanfu.user.center.dao.UsersMapper;
import com.hanfu.user.center.model.UsersExample;

@SpringBootTest(classes = Application.class)
public class UsersMapperTest {

    @Autowired
    private UsersMapper usersMapper;

//	@Test
//    void runTest() {
//		long count = usersMapper.countByExample(new UsersExample());
//		System.out.println(count);
//    }
}
