package com.ibicd.common.shiro.session;

import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;

/**
 * @ClassName CustomSessionManager
 * @Description 自定义会话管理器
 * @Author Julie
 * @Date 2019/10/8 7:27
 * @Version 1.0
 */
public class CustomSessionManager extends DefaultWebSessionManager {

    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        String sessionId = WebUtils.toHttp(request).getHeader("Authorization");
        if (StringUtils.isEmpty(sessionId)) {

            return super.getSessionId(request, response);
        } else {
            sessionId = sessionId.replaceAll("Bearer ","");
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, "header");
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, sessionId);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
            return sessionId;
        }
    }
}
