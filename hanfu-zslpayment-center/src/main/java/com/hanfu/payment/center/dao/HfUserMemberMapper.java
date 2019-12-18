package com.hanfu.payment.center.dao;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Mapper
@Component
public interface HfUserMemberMapper {

    void rechargeMember(Integer userId, Integer money);
}