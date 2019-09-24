package com.ibicd.system.dao;

import com.ibicd.domain.system.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @ClassName PermissionDao
 * @Description 权限接口
 * @Author Julie
 * @Date 2019/9/23 7:12
 * @Version 1.0
 */
public interface PermissionDao extends JpaRepository<Permission, String>, JpaSpecificationExecutor<Permission> {

    List<Permission> findByTypeAndPid(int type, String pid);
}
