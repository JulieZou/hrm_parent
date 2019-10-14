package com.ibicd.employee.dao;

import com.ibicd.domain.employee.EmployeePositive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @ClassName PositiveDao
 * @Description 员工转正申请访问接口
 * @Author Julie
 * @Date 2019/10/09 7:03
 * @Version 1.0
 */
public interface PositiveDao extends JpaRepository<EmployeePositive, String>, JpaSpecificationExecutor<EmployeePositive> {
    EmployeePositive findByUserId(String uid);
}