package com.ibicd.company.web;

import com.ibicd.common.controller.BaseController;
import com.ibicd.common.entity.Result;
import com.ibicd.company.service.CompanyService;
import com.ibicd.domain.company.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName CompanyController
 * @Description TODO
 * @Author Julie
 * @Date 2019/9/16 8:13
 * @Version 1.0
 */

@RestController
@RequestMapping("/company")
public class CompanyController extends BaseController {


    @Autowired
    private CompanyService companyService;


    @RequestMapping(value = "", method = RequestMethod.POST)
    public Result add(@RequestBody Company company) {
        companyService.add(company);
        return Result.SUCCESS();
    }




}
