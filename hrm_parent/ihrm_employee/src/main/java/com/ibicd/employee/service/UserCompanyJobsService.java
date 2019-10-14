package com.ibicd.employee.service;

import com.ibicd.domain.employee.UserCompanyJobs;
import com.ibicd.employee.dao.UserCompanyJobsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName ResignationService
 * @Description TODO
 * @Author Julie
 * @Date 2019/10/09 7:12
 * @Version 1.0
 */
@Service
public class UserCompanyJobsService {
    @Autowired
    private UserCompanyJobsDao userCompanyJobsDao;

    public void save(UserCompanyJobs jobsInfo) {
        userCompanyJobsDao.save(jobsInfo);
    }

    public UserCompanyJobs findById(String userId) {
        return userCompanyJobsDao.findByUserId(userId);
    }
}
