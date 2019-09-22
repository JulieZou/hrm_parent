package com.hrm.common.controller;

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
public class BaseController  {

    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected String companyId;
    protected String companyName;

    @ModelAttribute
    public void setResAndReq(HttpServletRequest resquest,HttpServletResponse response){
        this.request = resquest;
        this.response = response;
        this.companyId = "1";
        this.companyName= "測試";
    }

    public String parseCompanyId(){

        return "1";
    }

    public String parseCompanyName(){
        return "测试公司名称";
    }


}
