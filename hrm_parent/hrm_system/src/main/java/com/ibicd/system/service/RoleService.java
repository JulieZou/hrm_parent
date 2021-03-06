package com.ibicd.system.service;

import com.ibicd.common.utils.IdWorker;
import com.ibicd.common.utils.PermissionConstants;
import com.ibicd.domain.system.Permission;
import com.ibicd.system.dao.PermissionDao;
import com.ibicd.system.dao.RoleDao;
import com.ibicd.domain.system.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @ClassName RoleService
 * @Description 角色服务
 * @Author Julie
 * @Date 2019/9/22 22:46
 * @Version 1.0
 */
@Service
public class RoleService {

    @Autowired
    private IdWorker idWorker;
    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PermissionDao permissionDao;

    /**
     * 添加角色
     */
    public void save(Role role) {
        role.setId(idWorker.nextId() + "");
        roleDao.save(role);
    }

    /**
     * 更新角色
     */
    public void update(Role role) {
        Role targer = roleDao.getOne(role.getId());
        targer.setDescription(role.getDescription());
        targer.setName(role.getName());
        roleDao.save(targer);
    }

    /**
     * 根据id查询角色
     *
     * @param id
     * @return
     */
    public Role findById(String id) {
        return roleDao.findById(id).get();
    }


    /**
     * 删除角色
     */
    public void delete(String id) {
        roleDao.deleteById(id);
    }

    /**
     * 分页查询
     *
     * @param companyId
     * @param page
     * @param size
     * @return
     */
    public Page<Role> findSearch(String companyId, int page, int size) {
        Specification<Role> specification = new Specification<Role>() {
            @Override
            public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> query,
                                         CriteriaBuilder cb) {
                return cb.equal(root.get("companyId").as(String.class), companyId);
            }
        };
        return roleDao.findAll(specification, PageRequest.of(page - 1, size));
    }

    /**
     * 分配权限
     *
     * @param roleId
     * @param permIds
     */
    public void assignPerms(String roleId, List<String> permIds) {

        //1.获取分配的角色对象
        Role role = roleDao.findById(roleId).get();
        //2.构造角色的权限集合
        Set<Permission> perms = new HashSet<>();
        for (String permId : permIds) {
            Permission permission = permissionDao.findById(permId).get();
            //需要根据父id和类型查询API权限列表
            List<Permission> apiList =
                    permissionDao.findByTypeAndPid(PermissionConstants.PY_API, permission.getId());
            perms.addAll(apiList);//自定赋予API权限
            perms.add(permission);//当前菜单或按钮的权限
        }
        //3.设置角色和权限的关系
        role.setPermissions(perms);
        //4.更新角色
        roleDao.save(role);
    }
}


