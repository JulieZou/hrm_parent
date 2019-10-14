package com.ibicd.employee.service;

import com.ibicd.domain.employee.EmployeeResignation;
import com.ibicd.employee.dao.EmployeeResignationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @ClassName ResignationService
 * @Description TODO
 * @Author Julie
 * @Date 2019/10/09 7:12
 * @Version 1.0
 */
@Service
public class ResignationService {
    @Autowired
    EmployeeResignationDao resignationDao;

    public void save(EmployeeResignation resignation) {
        resignation.setCreateTime(new Date());
        resignationDao.save(resignation);
    }

    public EmployeeResignation findById(String userId) {
        return resignationDao.findByUserId(userId);
    }
}
