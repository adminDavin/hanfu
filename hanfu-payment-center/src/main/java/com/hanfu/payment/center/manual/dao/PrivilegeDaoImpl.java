package com.hanfu.payment.center.manual.dao;

import com.hanfu.payment.center.manual.model.Coupons;
import com.hanfu.payment.center.manual.model.hfUserPrivilege;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class PrivilegeDaoImpl implements PrivilegeDao{
    @Autowired
    SqlSessionTemplate sqlSessionTemplate;
    @Override
    public List<hfUserPrivilege> selectPrivilege(Integer userId, String status) {
        Coupons coupons = new Coupons();
        coupons.setUserId(userId);
        coupons.setStatus(status);
        return sqlSessionTemplate.selectOne("selectPrivilege", coupons);
    }
}
