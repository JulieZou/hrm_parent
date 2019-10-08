package com.ibicd.common.interceptor;

import com.ibicd.common.entity.ResultCode;
import com.ibicd.common.exception.CommonException;
import com.ibicd.common.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 1.简化获取token数据的代码
 * 统一的用户权限校验（用户是否登录）
 * 2.判断用户是否具有当前访问接口的权限
 *
 * @ClassName JwtInterceptor
 * @Description 自定义拦截器
 * @Author Julie
 * @Date 2019/9/26 8:25
 * @Version 1.0
 */
//@Component
public class JwtInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private JwtUtils jwtUtils;

    //ALT+INSERT 快捷键实现快速重写父类方法

    /**
     * 进入控制器之前执行
     * 判断用户是否登录：
     * 1.通过request获取token信息
     * 2.从token中解析获取claims
     * 3.将claims绑定到request域中
     *
     * @param request
     * @param response
     * @param handler
     * @return true-表示继续往下执行
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //通过拦截器获取token
        String authorization = request.getHeader("Authorization");
        if (!StringUtils.isEmpty(authorization) && authorization.startsWith("Bearer")) {
            String token = authorization.replace("Bearer ", "");
            Claims claims = jwtUtils.parseJwt(token);
            if (claims != null) {
                String apis = (String) claims.get("apis");//api-user-delete
                HandlerMethod handlerMethod = (HandlerMethod) handler;
                RequestMapping requestMapping = handlerMethod.getMethodAnnotation(RequestMapping.class);
                String name = requestMapping.name();

                if (apis.contains(name)) {
                    request.setAttribute("user_claims", claims);
                    return true;
                } else
                    throw new CommonException(ResultCode.UNAUTHORISE);
            }
        }
        throw new CommonException(ResultCode.UNAUTHENTICATED);
    }

    /**
     * 执行控制器之后执行
     *
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    /**
     * 响应结束之前执行
     *
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }
}
