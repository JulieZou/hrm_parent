package com.ibicd.company.dao;

import com.ibicd.domain.company.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @ClassName DepartmentDao
 * @Description 部门接口
 * @Author Julie
 * @Date 2019/9/18 8:01
 * @Version 1.0
 */
public interface DepartmentDao extends JpaRepository<Department,String>,JpaSpecificationExecutor<Department> {


}
