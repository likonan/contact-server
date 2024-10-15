package com.swpu.contactserver.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@Component
public class JwtComponent {
    /**
     *  生成JWT token
     * @param username
     * @param password
     * @return
     */
    public String sign(String username, String password) {
        //生成签名算法
        Algorithm alg = Algorithm.HMAC384(username + password);
        //生成过期时间
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR_OF_DAY,3);
        Date date = cal.getTime();

        //.......
        return JWT.create().withClaim("username", username).withExpiresAt(date).sign(alg);
    }

    /**
     * 获取token过期时间
     * @param token
     * @return
     */
    public Date getExpires(String token){
        return JWT.decode(token).getExpiresAt();
    }
    /**
     * 从JWTToken中获取用户名
     *
     * @param token
     * @return
     */
    public String getUserName(String token) {
        return JWT.decode(token).getClaim("username").asString();
    }
    /**
     * 验证token
     * @param token
     * @param username
     * @param password
     * @return
     */
    public boolean verify(String token,String username,String password){
        Algorithm alg = Algorithm.HMAC384(username + password);
        JWTVerifier verifier = JWT.require(alg).withClaim("username", username).build();
        try{
            verifier.verify(token);
            return true;
        }catch (JWTVerificationException e){
            e.printStackTrace();
            return false;
        }
    }
}
