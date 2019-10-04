package com.ibicd.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Date;
import java.util.Map;

/**
 * @ClassName JwtUtils
 * @Description 登录JWT 工具类
 * @Author Julie
 * @Date 2019/9/24 20:55
 * @Version 1.0
 */
@Getter
@Setter
@ConfigurationProperties("jwt.config")//读取配置文件中的值
public class JwtUtils {

    /**
     * 密钥
     */
    private String key;

    /**
     * 签名的失效时间
     */
    private Long ttl;

    /**
     * 设置认证token
     *
     * @param id   用户登录id
     * @param name 用户登录名称
     * @param map
     * @return
     */
    public String createJwt(String id, String name, Map<String, Object> map) {
        //1.设置失效时间
        Date expTime = new Date(System.currentTimeMillis() + ttl);

        //2.创建jwtBuilder
        JwtBuilder jwtBuilder = Jwts.builder().setId(id).setSubject(name).setIssuedAt(new Date()).signWith(SignatureAlgorithm.HS256, key);

        //3.根据map设置claims
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            jwtBuilder.claim(key, value);
        }

        jwtBuilder.setExpiration(expTime);//设置失效时间

        //4.创建token
        return jwtBuilder.compact();

    }


    /**
     * 解析token
     *
     * @param token
     * @return
     */
    public Claims parseJwt(String token) {

        Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
        return claims;
    }

}
