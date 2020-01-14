package com.hanfu.user.center.manual.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AddressDaoImpl implements AddressDao {
    @Autowired
    SqlSessionTemplate sqlSessionTemplate;

    @Override
    public Integer updateAddress(Integer id) {
        int result = sqlSessionTemplate.update("updateAddress", id);
        return result;
    }
}
