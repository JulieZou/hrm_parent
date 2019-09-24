package com.ibicd.test;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

/**
 * @ClassName TestParseJwt
 * @Description TODO
 * @Author Julie
 * @Date 2019/9/24 8:13
 * @Version 1.0
 */
public class TestParseJwt {
    public static void main(String[] args) {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMjMiLCJzdWIiOiJTdWJqZWN0IiwiaWF0IjoxNTY5MjgzODcyfQ.W2jPIDpubwtRXztzU_gKyHWxy2UziVl95HH5oo53RI4";
        Claims body = Jwts.parser().setSigningKey("keykeykey").parseClaimsJws(token).getBody();

       //不正确的key 导致报错：A signing key must be specified if the specified JWT is digitally signed.
        System.out.println(body.getId());
        System.out.println(body.getSubject());
        System.out.println(body.getIssuedAt());
    }
}
