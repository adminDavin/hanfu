package com.hanfu.dichan.center.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;

import java.util.Date;

/**
 * 加密
 * @author shihao
 *
 */
public final class Encrypt {

    /**
     * 生成加密后的token
     * @param isVip 是不是VIP,true表示是VIP，false表示不是VIP。
     * @param userId 用户id
     * @param Type  类型
     * @return 加密后的token
     */
    public String getToken(final boolean isVip, final Integer userId,
                           final String Type,Integer merId) {
        String token = null;
        try {
            Date expiresAt = new Date(System.currentTimeMillis() + 24L * 60L * 3600L * 1000L);
            token = JWT.create()
                    .withIssuer("auth0")
                    .withClaim("isVip", isVip)
                    .withClaim("merId",merId)
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
        return token;
    }
}
