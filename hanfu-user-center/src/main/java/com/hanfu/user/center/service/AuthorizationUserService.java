package com.hanfu.user.center.service;

import com.hanfu.user.center.dao.AuthorizationUserMapper;

import java.util.List;
/**
 * 皓月千里
 *
 * @param
 * @return
 */
public interface AuthorizationUserService {
    List<AuthorizationUserMapper> selectAuthorizationUser();
}
