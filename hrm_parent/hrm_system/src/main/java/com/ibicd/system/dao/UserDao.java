package com.ibicd.system.dao;

import com.ibicd.domain.system.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @ClassName UserDao
 * @Description 用戶數據層
 * @Author Julie
 * @Date 2019/9/22 13:08
 * @Version 1.0
 */
public interface UserDao extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {
    /**
     * 通过手机号码查找用户
     *
     * @param mobile
     * @return
     */
    public User findByMobile(String mobile);
}
