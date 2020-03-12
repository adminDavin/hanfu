package com.hanfu.user.center.service.impl;

import com.hanfu.user.center.service.UserCenterService;
import com.hanfu.user.center.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class UserCenterServiceImpl implements UserCenterService {
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    public boolean checkToken(String token) {
        //解析出userId和uuid
        if (token == null || "".equals(token)) {
            return false;
        }
        String[] arr1 = token.split("_");
        if (arr1.length != 2) {
            return false;
        }
        //根据redis进行检查
        String key = arr1[0] + "_token";
        String r_token = (String) redisTemplate.opsForValue().get(key);
        if (r_token == null) {
            return false;
        }
        if (!token.equals(r_token)) {
            return false;
        }
        //返回检测结果,更新token时间
        redisTemplate.opsForValue().set(key, token,
                Constants.STATE_MANAGER, TimeUnit.HOURS);
        return true;
    }
}