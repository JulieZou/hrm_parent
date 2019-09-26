package com.ibicd.test;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * @ClassName TestCreateJwt
 * @Description TODO
 * @Author Julie
 * @Date 2019/9/24 8:04
 * @Version 1.0
 */
public class TestCreateJwt {

    public static void main(String[] args) {

        JwtBuilder builder = Jwts.builder().setId("123").setSubject("Subject").setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, "keykeykey")
                .claim("companyId","123").claim("companyName","I Believe I Can Do");
        String token = builder.compact();
        System.out.println(token);

    }
}
