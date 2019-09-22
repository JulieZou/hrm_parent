package com.ibicd.domain.company.response;

import com.ibicd.domain.company.Company;
import com.ibicd.domain.company.Department;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName DeptListResult
 * @Description TODO
 * @Author Julie
 * @Date 2019/9/19 7:07
 * @Version 1.0
 */
@Data
public class DeptListResult {

    private String companyId;

    private String companyName;

    private String companyManage;

    private List<Department> depts;

    public DeptListResult(){

    }

    public DeptListResult(Company company,List<Department> depts){
        this.companyId  = company.getId();
        this.companyName = company.getName();
        this.companyManage = company.getLegalRepresentative();
        this.depts = depts;
    }
}
