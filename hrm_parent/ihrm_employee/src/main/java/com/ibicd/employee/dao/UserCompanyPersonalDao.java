package com.ibicd.employee.dao;

import com.ibicd.domain.employee.UserCompanyPersonal;
import com.ibicd.domain.employee.response.EmployeeReportResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @ClassName UserCompanyPersonalDao
 * @Description 员工企业详情访问接口
 * @Author Julie
 * @Date 2019/10/09 7:05
 * @Version 1.0
 */
public interface UserCompanyPersonalDao extends JpaRepository<UserCompanyPersonal, String>, JpaSpecificationExecutor<UserCompanyPersonal> {

    UserCompanyPersonal findByUserId(String userId);

    /**
     * SELECT * FROM em_user_company_personal p
     * LEFT JOIN em_resignation e ON p.user_id = e.user_id WHERE p.time_of_entry LIKE '2018-02%'
     * OR e.resignation_time LIKE '2018-02%'
     *
     * @param companyId
     * @param month
     * @return
     */
    @Query(value="select new com.ibicd.domain.employee.response.EmployeeReportResult(a,b) " +
            "from UserCompanyPersonal a left join EmployeeResignation b on a.userId = b.userId " +
            "where a.companyId=?1 and (a.timeOfEntry like ?2 or b.resignationTime like ?2)")
    List<EmployeeReportResult> export(String companyId, String month);
}