package com.hanfu.user.center.utils;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

/**
 * 加密
 * @author shihao
 *
 */
public final class Encrypt {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    /**
     * 生成加密后的token
     * @param isVip 是不是VIP,true表示是VIP，false表示不是VIP。
     * @param userId 用户id
     * @param Type  类型
     * @return 加密后的token
     */
    public String getToken(final boolean isVip, final Integer userId,
                           final String Type) {
        String token = null;
        try {
            Date expiresAt = new Date(System.currentTimeMillis() + 24L * 60L * 3600L * 1000L);
            token = JWT.create()
                    .withIssuer("auth0")
                    .withClaim("isVip", isVip)
                    .withClaim("userId", userId)
                    .withClaim("Type", Type)
                    .withExpiresAt(expiresAt)
                    // 使用了HMAC256加密算法。
                    // mysecret是用来加密数字签名的密钥。
                    .sign(Algorithm.HMAC256("mysecret"));
        } catch (JWTCreationException exception){
            //Invalid Signing configuration / Couldn't convert Claims.
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (token!=null){
            redisTemplate.opsForValue().set(userId+Type+"token",token);
            redisTemplate.expire(userId+Type+"token",300 , TimeUnit.SECONDS);
        }
        return token;
    }
}
