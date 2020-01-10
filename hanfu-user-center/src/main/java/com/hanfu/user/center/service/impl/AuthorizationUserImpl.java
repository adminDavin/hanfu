package com.hanfu.user.center.service.impl;

import com.hanfu.user.center.dao.AuthorizationUserMapper;
import com.hanfu.user.center.service.AuthorizationUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 皓月千里
 *
 * @param handler
 * @return
 */
import java.util.List;
@Service
public class AuthorizationUserImpl implements AuthorizationUserService {
    @Autowired
    private AuthorizationUserMapper authorizationUserMapper;
    @Override
    public List<AuthorizationUserMapper> selectAuthorizationUser() {
        return authorizationUserMapper.selectAuthorizationUser();
    }
}
