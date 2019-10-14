package com.ibicd.employee.service;

import com.ibicd.domain.employee.EmployeePositive;
import com.ibicd.employee.dao.PositiveDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @ClassName PositiveService
 * @Description TODO
 * @Author Julie
 * @Date 2019/10/09 7:12
 * @Version 1.0
 */
@Service
public class PositiveService {
    @Autowired
    private PositiveDao positiveDao;

    public EmployeePositive findById(String uid, Integer status) {
        EmployeePositive positive = positiveDao.findByUserId(uid);
        if (status != null && positive != null) {
            if (positive.getEstatus() != status) {
                positive = null;
            }
        }
        return positive;
    }

    public EmployeePositive findById(String uid) {
        return positiveDao.findByUserId(uid);
    }

    public void save(EmployeePositive positive) {
        positive.setCreateTime(new Date());
        positive.setEstatus(1);//未执行
        positiveDao.save(positive);
    }
}
