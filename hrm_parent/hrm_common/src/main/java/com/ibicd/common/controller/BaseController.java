package com.ibicd.common.controller;

import com.ibicd.domain.system.response.ProfileResult;
import io.jsonwebtoken.Claims;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName BaseController
 * @Description 公共的Controller
 * @Author Julie
 * @Date 2019/9/19 8:12
 * @Version 1.0
 */
public class BaseController {

    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected Claims claims;
    protected String companyId;
    protected String companyName;

// 使用jwt 方式
// @ModelAttribute
//    public void setResAndReq(HttpServletRequest resquest, HttpServletResponse response) {
//        this.request = resquest;
//        this.response = response;
//        Object claims = resquest.getAttribute("user_claims");
//        if (claims != null) {
//            this.claims = (Claims) claims;
//            this.companyId = (String) this.claims.get("companyId");
//            this.companyName = (String) this.claims.get("companyName");
//        }
//
//    }

    public String parseCompanyId() {
        this.companyId = "1";
        return this.companyId ;
    }

    public String parseCompanyName() {
        return "测试";
    }

    /**
     * 使用shiro 获取
     *
     * @param resquest
     * @param response
     */
    public void setResAndReq(HttpServletRequest resquest, HttpServletResponse response) {
        this.request = resquest;
        this.response = response;
        Subject subject = SecurityUtils.getSubject();
        PrincipalCollection principalCollection = subject.getPrincipals();
        if (principalCollection != null && !principalCollection.isEmpty()) {
            ProfileResult result = (ProfileResult) principalCollection.getPrimaryPrincipal();
            this.companyId = result.getCompanyId();
            this.companyName = result.getCompanyName();
        }
    }


}
