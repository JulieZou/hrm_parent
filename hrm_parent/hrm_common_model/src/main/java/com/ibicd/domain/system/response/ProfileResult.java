package com.ibicd.domain.system.response;

import com.ibicd.domain.system.Permission;
import com.ibicd.domain.system.Role;
import com.ibicd.domain.system.User;
import lombok.Getter;
import lombok.Setter;
import org.crazycake.shiro.AuthCachePrincipal;

import java.io.Serializable;
import java.util.*;

/**
 * @ClassName ProfileResult
 * @Description 用户登录成功后响应结果
 * @Author Julie
 * @Date 2019/9/24 22:44
 * @Version 1.0
 */
@Setter
@Getter
public class ProfileResult implements Serializable,AuthCachePrincipal {


    private String mobile;

    private String userName;

    private String companyName;

    private String companyId;

    private Map<String, Object> roles;


    public ProfileResult(User user, List<Permission> permissions){

        this.mobile = user.getMobile();
        this.userName = user.getUsername();
        this.companyId = user.getCompanyId();
        this.companyName = user.getCompanyName();
        Set<String> menus = new HashSet<>();
        Set<String> points = new HashSet<>();
        Set<String> apis = new HashSet<>();

        for (Permission permission : permissions) {
            String code = permission.getCode();
            Integer type = permission.getType();
            switch (type) {
                case 1:
                    menus.add(code);
                    break;
                case 2:
                    points.add(code);
                    break;
                case 3:
                    apis.add(code);
            }
        }

        Map<String, Object> roleMap = new HashMap<>();
        roleMap.put("menus", menus);
        roleMap.put("points", points);
        roleMap.put("apis", apis);
        this.roles = roleMap;
    }

    public ProfileResult(User user) {

        this.mobile = user.getMobile();
        this.userName = user.getUsername();
        this.companyId = user.getCompanyId();
        this.companyName = user.getCompanyName();
        Set<Role> roles = user.getRoles();
        Set<String> menus = new HashSet<>();
        Set<String> points = new HashSet<>();
        Set<String> apis = new HashSet<>();

        //iter
        for (Role role : roles) {
            Set<Permission> permissions = role.getPermissions();
            for (Permission permission : permissions) {
                String code = permission.getCode();
                Integer type = permission.getType();
                switch (type) {
                    case 1:
                        menus.add(code);
                        break;
                    case 2:
                        points.add(code);
                        break;
                    case 3:
                        apis.add(code);
                }
            }

        }
        Map<String, Object> roleMap = new HashMap<>();
        roleMap.put("menus", menus);
        roleMap.put("points", points);
        roleMap.put("apis", apis);
        this.roles = roleMap;
    }

    @Override
    public String getAuthCacheKey() {
        return null;
    }
}
