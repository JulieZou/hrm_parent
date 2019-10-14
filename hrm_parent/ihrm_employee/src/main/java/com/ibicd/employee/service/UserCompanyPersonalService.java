package com.ibicd.employee.service;

import com.ibicd.domain.employee.UserCompanyPersonal;
import com.ibicd.domain.employee.response.EmployeeReportResult;
import com.ibicd.employee.dao.UserCompanyPersonalDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName UserCompanyPersonalService
 * @Description TODO
 * @Author Julie
 * @Date 2019/10/09 7:12
 * @Version 1.0
 */
@Service
public class UserCompanyPersonalService {

    @Autowired
    private UserCompanyPersonalDao userCompanyPersonalDao;

    public void save(UserCompanyPersonal personalInfo) {
        userCompanyPersonalDao.save(personalInfo);
    }

    public UserCompanyPersonal findById(String userId) {
        return userCompanyPersonalDao.findByUserId(userId);
    }

    public List<EmployeeReportResult> export(String companyId, String month) {

        return userCompanyPersonalDao.export(companyId, month + "%");

    }
}
