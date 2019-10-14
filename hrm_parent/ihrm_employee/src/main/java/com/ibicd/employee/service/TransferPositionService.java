package com.ibicd.employee.service;

import com.ibicd.domain.employee.EmployeeTransferPosition;
import com.ibicd.employee.dao.TransferPositionDao;
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
public class TransferPositionService {
    @Autowired
    private TransferPositionDao transferPositionDao;

    public EmployeeTransferPosition findById(String uid, Integer status) {
        EmployeeTransferPosition transferPosition = transferPositionDao.findByUserId(uid);
        if (status != null && transferPosition != null) {
            if (transferPosition.getEstatus() != status) {
                transferPosition = null;
            }
        }
        return transferPosition;
    }

    public void save(EmployeeTransferPosition transferPosition) {
        transferPosition.setCreateTime(new Date());
        transferPosition.setEstatus(1); //未执行
        transferPositionDao.save(transferPosition);
    }
}
