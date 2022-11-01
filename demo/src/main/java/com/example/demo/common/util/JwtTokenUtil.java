package com.example.demo.common.util;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@Component
public class JwtTokenUtil {
    private final static long EXPIRATION = 1800;

    @Resource
    private RedisTemplate<String ,Object> redisTemplate;
    /**
     * 生成token
     * @param username
     * @return
     */
    public static String createToken(String username) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("123456##$!！@%|");
            Map<String, Object> map = new HashMap<>();
            map.put("username", username);
            String token = JWT.create()
                    .withIssuer("auth0")
                    .withHeader(map) //设置响应头
                    .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION * 1000)) //设置失效时间
                    .withClaim("username",username)
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception) {
            //Invalid Signing configuration / Couldn't convert Claims.
        }
        return null;
    }

    /**
     * 检验token
     * @param token
     * @return
     */
    public static Boolean checkToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("123456##$!！@%|"); //use more secure key
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("auth0")
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (JWTVerificationException exception) {
            //Invalid signature/claims
        }
        return false;
    }


    /**
     * 检验是否过期
     * @param token
     * @return
     */
    public  Boolean isJwtExpired(String token) {
        Long res = redisTemplate.getExpire(token);
        if (res>0){
            //未过期

            //重置时间
            redisTemplate.expire(token,30L, TimeUnit.MINUTES); //重置为30分钟
            return true;
        }
        return false;
    }
}
