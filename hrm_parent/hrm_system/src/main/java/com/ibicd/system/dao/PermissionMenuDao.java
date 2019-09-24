package com.ibicd.system.dao;

import com.ibicd.domain.system.PermissionMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @ClassName PermissionMenuDao
 * @Description 权限菜单接口
 * @Author Julie
 * @Date 2019/9/23 7:14
 * @Version 1.0
 */
public interface PermissionMenuDao extends JpaRepository<PermissionMenu, String>, JpaSpecificationExecutor<PermissionMenu> {
}
