package com.ibicd.employee.dao;

import com.ibicd.domain.employee.EmployeeArchive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


/**
 * @ClassName ArchiveDao
 * @Description 员工归档访问接口
 * @Author Julie
 * @Date 2019/10/09 7:01
 * @Version 1.0
 */
public interface ArchiveDao extends JpaRepository<EmployeeArchive, String>, JpaSpecificationExecutor<EmployeeArchive> {

    @Query(value = "SELECT * FROM em_archive WHERE company_id = ?1 AND month = ?2 ORDER BY create_time DESC LIMIT 1;", nativeQuery = true)
    EmployeeArchive findByLast(String companyId, String month);

    @Query(value = "SELECT * FROM em_archive WHERE company_id = ?1 AND month LIKE ?2 GROUP BY month HAVING MAX(create_time) limit ?3,?4", nativeQuery = true)
    List<EmployeeArchive> findAllData(String companyId, String year, Integer index, Integer pagesize);

    @Query(value = "SELECT count(DISTINCT month) FROM em_archive WHERE company_id = ?1 AND month LIKE ?2", nativeQuery = true)
    long countAllData(String companyId, String year);
}