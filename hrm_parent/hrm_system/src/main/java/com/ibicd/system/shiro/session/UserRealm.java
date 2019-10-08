package com.ibicd.system.shiro.session;

import com.ibicd.common.shiro.realm.HrmRealm;
import com.ibicd.common.utils.UserLevelConstants;
import com.ibicd.domain.system.Permission;
import com.ibicd.domain.system.User;
import com.ibicd.domain.system.response.ProfileResult;
import com.ibicd.system.service.PermissionService;
import com.ibicd.system.service.UserService;
import org.apache.shiro.authc.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName UserRealm
 * @Description 用户认证方法
 * @Author Julie
 * @Date 2019/10/8 7:36
 * @Version 1.0
 */
public class UserRealm extends HrmRealm {

    @Autowired
    private UserService userService;

    @Autowired
    private PermissionService permissionService;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        //1.获取用户的手机号和密码
        UsernamePasswordToken upToken = (UsernamePasswordToken) authenticationToken;
        String mobile = upToken.getUsername();
        String password = new String(upToken.getPassword());

        //2.根据手机号查询用户
        User user = userService.findByMobile(mobile);

        //3.判断用户是否存在，用户密码是否正确
        if (user != null && user.getPassword().equals(password)) {
            //4. 构造安全数据并返回（用户基本信息，权限信息，profileResult）
            ProfileResult result = null;
            if (user.getLevel().equals("user")) {
                result = new ProfileResult(user);
            } else {
                Map map = new HashMap();
                if (UserLevelConstants.COMP_ADMIN.equals(user.getLevel())) {
                    map.put("enVisible", 1);
                }
                List<Permission> permissionServiceAll = permissionService.findAll(map);
                result = new ProfileResult(user, permissionServiceAll);
            }
            return new SimpleAuthenticationInfo(result, user.getPassword(), this.getName());
        }

        //返回null，抛出异常
        return null;

    }

}
