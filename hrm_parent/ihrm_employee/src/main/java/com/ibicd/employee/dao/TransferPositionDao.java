package com.ibicd.employee.dao;

import com.ibicd.domain.employee.EmployeeTransferPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @ClassName TransferPositionDao
 * @Description 员工调岗申请访问接口
 * @Author Julie
 * @Date 2019/10/09 7:05
 * @Version 1.0
 */
public interface TransferPositionDao extends JpaRepository<EmployeeTransferPosition, String>, JpaSpecificationExecutor<EmployeeTransferPosition> {
    EmployeeTransferPosition findByUserId(String uid);
}