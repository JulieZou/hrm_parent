package com.ibicd.system.client;

import com.ibicd.common.entity.Result;
import com.ibicd.domain.company.Department;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @ClassName DepartmentFeignClient
 * @Description 调用企业微服务根据id查询部门
 * @Author Julie
 * @Date 2019/10/9 8:10
 * @Version 1.0
 */
@FeignClient("ihrm-company")
public interface DepartmentFeignClient {

    @RequestMapping(value = "/company/department/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable(value = "id") String id);

    @RequestMapping(value = "/company/queryByCode", method = RequestMethod.POST)
    public Department queryByCode(@RequestParam("code")String code,@RequestParam("companyId")String companyId);
}
