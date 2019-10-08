package com.ibicd.system;

import com.ibicd.common.shiro.realm.HrmRealm;
import com.ibicd.common.shiro.session.CustomSessionManager;
import com.ibicd.system.shiro.session.UserRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @ClassName ShiroConfiguration
 * @Description TODO
 * @Author Julie
 * @Date 2019/10/4 8:45
 * @Version 1.0
 */
@Configuration
public class ShiroConfiguration {

    //1.创建Realm
    @Bean
    public HrmRealm customRealm() {

        return new UserRealm();
    }

    //2.创建安全管理器
    @Bean
    public SecurityManager securityManager(HrmRealm realm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager(realm);
        //将自定义的会话管理器注册到安全管理器中
        securityManager.setSessionManager(sessionManager());
        //将自定义的缓存管理器注册到安全管理器中
        securityManager.setCacheManager(cacheManager());

        return securityManager;
    }

    //3.配置Shiro过滤器工厂(在web程序中，shiro进行权限控制全部都是通过一组过滤器集合控制)
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager secManager) {
        ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();
        filterFactoryBean.setSecurityManager(secManager);

        filterFactoryBean.setLoginUrl("/authError?code=1");//跳转登录页面
        filterFactoryBean.setUnauthorizedUrl("/authError?code=2");//未授权的页面

        //设置过滤器集合
        Map<String, String> filterMap = new LinkedHashMap<>();
        filterMap.put("/login", "anon");//当前请求地址可以匿名访问(登录和error)
        filterMap.put("/autherror","anon");
        filterMap.put("/**", "authc"); //当前请求地址必须认证后才可以访问

        filterFactoryBean.setFilterChainDefinitionMap(filterMap);
        return filterFactoryBean;
    }


    //4.开启对shiro的支持
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;

    }


    /**
     * 1.redis 控制器，操作Redis
     * 2.SessionDao
     * 3.会话管理
     * 4.缓存管理
     */

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    /**
     * redis 管理器
     *
     * @return
     */
    public RedisManager redisManager() {

        RedisManager redisManager = new RedisManager();
        redisManager.setHost(host);
        redisManager.setPort(port);
        return redisManager;
    }

    /**
     * SessionDao
     *
     * @return
     */
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO sessionDAO = new RedisSessionDAO();
        sessionDAO.setRedisManager(redisManager());
        return sessionDAO;
    }

    /**
     * 设置自定义会话管理器
     *
     * @return
     */
    public DefaultWebSessionManager sessionManager() {

        CustomSessionManager customSessionManager = new CustomSessionManager();
        customSessionManager.setSessionDAO(redisSessionDAO());
        return customSessionManager;
    }

    /**
     * 设置缓存管理器
     *
     * @return
     */
    public RedisCacheManager cacheManager() {

        RedisCacheManager cacheManager = new RedisCacheManager();
        cacheManager.setRedisManager(redisManager());
        return cacheManager;
    }


}
