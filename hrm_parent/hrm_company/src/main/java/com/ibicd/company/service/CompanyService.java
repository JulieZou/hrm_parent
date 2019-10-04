package com.ibicd.company.service;

import com.ibicd.common.service.BaseService;
import com.ibicd.common.utils.IdWorker;
import com.ibicd.company.dao.CompanyDao;
import com.ibicd.domain.company.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @ClassName CompanyService
 * @Description 企业服务
 * @Author Julie
 * @Date 2019/9/16 8:06
 * @Version 1.0
 */

@Service
public class CompanyService extends BaseService {

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 新建
     *
     * @param company
     * @return
     */
    public Company add(Company company) {
        company.setId(idWorker.nextId() + "");
        company.setCreateTime(new Date());
        company.setState(1);//启用
        company.setAuditState("0");//待审核
        company.setBalance(0d);
        return companyDao.save(company);
    }

    /**
     * 更新
     *
     * @param company
     * @return
     */
    public Company update(Company company) {
        return companyDao.save(company);
    }
    /**
     * 通过Id查找
     *
     * @param id
     * @return
     */
    public Company findById(String id) {

        return companyDao.findById(id).get();
    }


    public void deletebyById(String id) {

        companyDao.deleteById(id);
    }

    public List<Company> findAll(String companyId) {
        return companyDao.findAll(getSpec(companyId));
    }



}
