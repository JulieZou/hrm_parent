package com.ibicd.employee.dao;

import com.ibicd.domain.employee.UserCompanyJobs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @ClassName UserCompanyJobsDao
 * @Description 员工岗位信息访问接口
 * @Author Julie
 * @Date 2019/10/09 7:04
 * @Version 1.0
 */
public interface UserCompanyJobsDao extends JpaRepository<UserCompanyJobs, String>, JpaSpecificationExecutor<UserCompanyJobs> {
    UserCompanyJobs findByUserId(String userId);
}