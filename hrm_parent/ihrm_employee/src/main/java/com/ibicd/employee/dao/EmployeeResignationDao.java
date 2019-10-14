package com.ibicd.employee.dao;

import com.ibicd.domain.employee.EmployeeResignation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @ClassName EmployeeResignationDao
 * @Description 员工离职申请访问接口
 * @Author Julie
 * @Date 2019/10/09 7:02
 * @Version 1.0
 */
public interface EmployeeResignationDao extends JpaRepository<EmployeeResignation, String>, JpaSpecificationExecutor<EmployeeResignation> {
    EmployeeResignation findByUserId(String uid);
}