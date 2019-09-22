package com.ibicd.system.dao;

import com.ibicd.domain.system.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.ibicd.domain.system.Role;
/**
 * @ClassName RoleDao
 * @Description TODO
 * @Author Julie
 * @Date 2019/9/22 13:10
 * @Version 1.0
 */
public interface RoleDao extends JpaRepository<Role,String>,JpaSpecificationExecutor<Role> {
}
