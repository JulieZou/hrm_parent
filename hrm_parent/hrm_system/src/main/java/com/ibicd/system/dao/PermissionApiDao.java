package com.ibicd.system.dao;

import com.ibicd.domain.system.PermissionApi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @ClassName PermissionApiDao
 * @Description 权限API接口类
 * @Author Julie
 * @Date 2019/9/23 7:16
 * @Version 1.0
 */
public interface PermissionApiDao extends JpaRepository<PermissionApi, String>, JpaSpecificationExecutor<PermissionApi> {
}
