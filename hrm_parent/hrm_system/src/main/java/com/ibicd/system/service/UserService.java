package com.ibicd.system.service;

import com.hrm.common.utils.IdWorker;
import com.ibicd.domain.system.User;
import com.ibicd.system.dao.RoleDao;
import com.ibicd.system.dao.UserDao;
import com.ibicd.domain.system.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

/**
 * @ClassName UserService
 * @Description 用户接口
 * @Author Julie
 * @Date 2019/9/22 13:09
 * @Version 1.0
 */
@Service
public class UserService {

    @Autowired
    private IdWorker idWorker;
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;


    /**
     * 保存用户
     *
     * @param user
     */
    public void save(User user) {
        String id = idWorker.nextId() + "";
        user.setPassword("123456");
        user.setEnableState(1);
        user.setId(id);
        userDao.save(user);
    }

    /**
     * 更新
     *
     * @param user
     */
    public void update(User user) {

        Optional<User> userDaoById = userDao.findById(user.getId());
        if (!userDaoById.isPresent())
            return;
        User target = userDaoById.get();
        target.setUsername(user.getUsername());
        target.setPassword(user.getPassword());
        target.setDepartmentId(user.getDepartmentId());
        target.setDepartmentName(user.getDepartmentName());

    }

    /**
     * 动态构建查询条件
     *
     * @param searchMap
     * @return
     */
    private Specification<User> createSpecification(Map searchMap) {


        return new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {


                List<Predicate> predicateList = new ArrayList<>();
                // ID
                if (searchMap.get("id") != null && !"".equals(searchMap.get("id"))) {
                    predicateList.add(cb.equal(root.get("id").as(String.class),
                            (String) searchMap.get("id")));
                }

                // 手机号码
                if (searchMap.get("mobile") != null &&
                        !"".equals(searchMap.get("mobile"))) {
                    predicateList.add(cb.equal(root.get("mobile").as(String.class),
                            (String) searchMap.get("mobile")));
                }

                // 用户ID
                if (searchMap.get("departmentId") != null &&
                        !"".equals(searchMap.get("departmentId"))) {
                    predicateList.add(cb.like(root.get("departmentId").as(String.class),
                            (String) searchMap.get("departmentId")));
                }
                if (searchMap.get("companyId") != null &&
                        !"".equals(searchMap.get("companyId"))) {
                    predicateList.add(cb.like(root.get("companyId").as(String.class),
                            (String) searchMap.get("companyId")));
                }
                if (searchMap.get("hasDept") != null &&
                        !"".equals(searchMap.get("hasDept"))) {
                    if ("0".equals((String) searchMap.get("hasDept"))) {
                        predicateList.add(cb.isNull(root.get("departmentId")));
                    } else {
                        predicateList.add(cb.isNotNull(root.get("departmentId")));
                    }
                }
                return cb.and(predicateList.toArray(new
                        Predicate[predicateList.size()]));

            }
        };
    }


    /**
     * 调整部门
     *
     * @param deptId
     * @param deptName
     * @param ids
     */
    public void changeDept(String deptId, String deptName, List<String> ids) {

        for (String userId : ids) {

            User user = userDao.findById(userId).get();
            user.setDepartmentId(deptId);
            user.setDepartmentName(deptName);
            userDao.save(user);
        }
    }

    /**
     * 分配角色
     *
     * @param userId
     * @param roleId
     */
    public void assignRoles(String userId, List<String> roleId) {
        Optional<User> optional = userDao.findById(userId);
        if (optional.isPresent()) {
            Set<Role> roles = new HashSet<>();
            User user = optional.get();
            for (String s : roleId) {
                Role role = roleDao.findById(s).get();
                roles.add(role);
            }
            user.setRoles(roles);
            userDao.save(user);
        }

    }

    /**
     * 4.查询全部用户列表
     * 参数：map集合的形式
     * hasDept
     * departmentId
     * companyId
     */
    public Page findAll(Map<String, Object> map, int page, int size) {

        Page<User> userPage = userDao.findAll(createSpecification(map), PageRequest.of(page - 1, size));

        return userPage;
    }


    /**
     * 根据id查找用户
     *
     * @param id
     * @return
     */
    public User findById(String id) {
        return userDao.findById(id).get();
    }

    public User findByMobile(String mobile){
        return userDao.findByMobile(mobile);
    }

    /**
     * 根据id删除用户
     *
     * @param id
     */
    public void deleteById(String id) {
        userDao.deleteById(id);
    }


}
