package com.ibicd.company.web;

import com.hrm.common.entity.Result;
import com.hrm.common.entity.ResultCode;
import com.ibicd.company.service.CompanyService;
import com.ibicd.company.service.DepartmentService;
import com.ibicd.domain.company.Company;
import com.ibicd.domain.company.Department;
import com.ibicd.domain.company.response.DeptListResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName DepartmentController
 * @Description 部门Controller
 * @Author Julie
 * @Date 2019/9/19 6:53
 * @Version 1.0
 */

//解决跨域
@CrossOrigin
@RestController
@RequestMapping(value = "/company")
public class DepartmentController {


    @RequestMapping(value = "/department", method = RequestMethod.POST)
    public Result save(@RequestBody Department department) {

        department.setCompanyId("1");
        departmentService.save(department);
        return Result.SUCCESS();
    }

    @RequestMapping(value = "/department", method = RequestMethod.GET)
    public Result findAll() {
        String companyId = "1";
        List<Department> all = departmentService.findAll(companyId);

        Company company = companyService.findById(companyId);
        DeptListResult deptListResult = new DeptListResult(company, all);
        return new Result(ResultCode.SUCCESS, deptListResult);
    }


    @RequestMapping(value = "/department/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable(value = "id") String id) {
        Department department = departmentService.findById(id);
        return new Result(ResultCode.SUCCESS, department);

    }

    @RequestMapping(value = "/department/{id}", method = RequestMethod.PUT)
    public Result update(@PathVariable(value = "id") String id, @RequestBody Department department) {

        department.setId(id);
        departmentService.update(department);
        return new Result(ResultCode.SUCCESS);
    }

    @RequestMapping(value = "/department/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable(value = "id") String id) {
        departmentService.deleteById(id);
        return new Result(ResultCode.SUCCESS);
    }

    @Autowired
    private CompanyService companyService;

    @Autowired
    private DepartmentService departmentService;
}
