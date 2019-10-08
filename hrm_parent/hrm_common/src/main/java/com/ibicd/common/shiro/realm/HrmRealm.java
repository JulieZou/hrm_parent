package com.ibicd.common.shiro.realm;

import com.ibicd.domain.system.response.ProfileResult;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.Set;

/**
 * @ClassName HrmRealm
 * @Description 公共的relam
 * @Author Julie
 * @Date 2019/10/8 7:32
 * @Version 1.0
 */
public class HrmRealm extends AuthorizingRealm {
    @Override
    public void setName(String name) {
        super.setName("hrmRealm");
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        ProfileResult principal = (ProfileResult) principalCollection.getPrimaryPrincipal();
        Set<String> apis = (Set<String>) principal.getRoles().get("apis");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(apis);
        return info;

    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        return null;
    }
}
