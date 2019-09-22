package com.ibicd.company.dao;

import com.ibicd.domain.company.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @ClassName CompanyDao
 * @Description JpaRepository提供了基本的增删改查 JpaSpecificationExecutor用于做复杂的条件查询
 * @Author Julie
 * @Date 2019/9/16 7:50
 * @Version 1.0
 */
public interface CompanyDao extends JpaRepository<Company,String>,JpaSpecificationExecutor<Company> {
}
