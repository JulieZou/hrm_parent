package com.ibicd.system.dao;

import com.ibicd.domain.system.PermissionPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @ClassName PermissionPointDao
 * @Description 权限按钮接口
 * @Author Julie
 * @Date 2019/9/23 7:15
 * @Version 1.0
 */
public interface PermissionPointDao extends JpaRepository<PermissionPoint, String>, JpaSpecificationExecutor<PermissionPoint> {
}
