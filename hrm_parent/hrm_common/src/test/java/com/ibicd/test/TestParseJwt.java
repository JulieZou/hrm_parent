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
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMjMiLCJzdWIiOiJTdWJqZWN0IiwiaWF0IjoxNTY5MzI5MDY0LCJjb21wYW55SWQiOiIxMjMiLCJjb21wYW55TmFtZSI6IkkgQmVsaWV2ZSBJIENhbiBEbyJ9.2FbAokKLcrBmr8e-7MmXRQ1ymk55JTVW6AKJjiyd8U0";
        Claims claims = Jwts.parser().setSigningKey("keykeykey").parseClaimsJws(token).getBody();

       //不正确的key 导致报错：A signing key must be specified if the specified JWT is digitally signed.
        System.out.println(claims.getId());
        System.out.println(claims.getSubject());
        System.out.println(claims.getIssuedAt());

        System.out.println(claims.get("companyId"));//123
        System.out.println(claims.get("companyName"));//I Believe I Can Do

    }
}
