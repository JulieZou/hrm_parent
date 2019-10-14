package com.ibicd.company.service;

import com.ibicd.common.service.BaseService;
import com.ibicd.common.utils.IdWorker;
import com.ibicd.company.dao.DepartmentDao;
import com.ibicd.domain.company.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @ClassName DepartmentService
 * @Description TODO
 * @Author Julie
 * @Date 2019/9/18 8:03
 * @Version 1.0
 */
@Service
public class DepartmentService extends BaseService {


    /**
     * 新增
     */
    public void save(Department department) {
        String id = idWorker.nextId() + "";
        department.setId(id);
        departmentDao.save(department);

    }


    /**
     * 修改
     */
    public void update(Department department) {
        //查找更新部门是否存在
        Department dept = findById(department.getId());
        //拷贝值
        if (dept == null) {
            return;
        }

        dept.setCode(department.getCode());
        dept.setName(department.getName());
        dept.setIntroduce(department.getIntroduce());

        //更新
        departmentDao.save(dept);
    }

    /**
     * 根据id查询部门
     */
    public Department findById(String id) {
        Optional<Department> optional = departmentDao.findById(id);
        if (optional.isPresent()) {
            Department dept = optional.get();
            return dept;
        }

        return null;
    }

    /**
     * 查询全部列表
     */
    public List<Department> findAll(String companyId) {

        return departmentDao.findAll(getSpec(companyId));
    }


    /**
     * 通过编码查找部门
     *
     * @param code
     * @param companyId
     * @return
     */
    public Department findByCode(String code, String companyId) {

        return departmentDao.findByCodeAndCompanyId(code,companyId);
    }

    /**
     * 删除
     */
    public void deleteById(String id) {
        departmentDao.deleteById(id);
    }


    @Autowired
    private DepartmentDao departmentDao;

    @Autowired
    private IdWorker idWorker;


}
