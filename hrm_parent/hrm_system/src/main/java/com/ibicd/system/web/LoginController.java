package com.ibicd.system.web;

import com.ibicd.common.controller.BaseController;
import com.ibicd.common.entity.Result;
import com.ibicd.common.entity.ResultCode;
import com.ibicd.common.utils.JwtUtils;
import com.ibicd.common.utils.PermissionConstants;
import com.ibicd.common.utils.UserLevelConstants;
import com.ibicd.domain.system.Permission;
import com.ibicd.domain.system.Role;
import com.ibicd.domain.system.User;
import com.ibicd.domain.system.response.ProfileResult;
import com.ibicd.system.service.PermissionService;
import com.ibicd.system.service.UserService;
import io.jsonwebtoken.Claims;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName LoginController
 * @Description 登录控制器
 * @Author Julie
 * @Date 2019/9/24 21:31
 * @Version 1.0
 */
//1.解决跨域
@CrossOrigin
@RestController
public class LoginController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private JwtUtils jwtUtils;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result login(@RequestBody Map<String, String> loginMap) {
        String mobile = loginMap.get("mobile");
        String password = loginMap.get("password");

        try {
            //1.构造登录令牌
            password = new Md5Hash(password, mobile, 3).toString();
            UsernamePasswordToken upToken = new UsernamePasswordToken(mobile, password);
            //2.获取subject
            Subject subject = SecurityUtils.getSubject();
            //3.调用login，进入realm完成认证
            subject.login(upToken);
            //4.获取sessionId
            String sessionId = (String) subject.getSession().getId();
            //5.构造返回结果
            return new Result(ResultCode.SUCCESS, sessionId);

        } catch (Exception e) {
            return new Result(ResultCode.MOBILE_OR_PWD_ERROR);

        }


    }

    /**
     * 用户登录成功后获取用户信息
     * 1.获取用户id
     * 2.根据id查询用户信息
     * 3.构建返回值对象
     * 4.响应数据
     *
     * @return
     */
    @RequestMapping(value = "/profile", method = RequestMethod.POST)
    public Result profile(HttpServletRequest request) throws Exception {
//        Claims claims = (Claims) request.getAttribute("user_claims");
//
//        String userId = claims.getId();
//        User user = userService.findById(userId);

        //1.sassAdmin saas管理员具备所有权限
        //2.coAdmin 企业管理员具备企业所有权限
        //3.user:普通用户（需要分配角色）


//        if (UserLevelConstants.NORMAL_USER.equals(user.getLevel())) {
//            profileResult = new ProfileResult(user);
//        } else {
//            Map map = new HashMap();
//            if (UserLevelConstants.COMP_ADMIN.equals(user.getLevel())) {
//                map.put("enVisible", "1");
//            }
//            List<Permission> all = permissionService.findAll(map);
//
//            profileResult = new ProfileResult(user, all);
//        }

        //整合shiro
        ProfileResult profileResult = null;
        Subject subject = SecurityUtils.getSubject();
        profileResult = (ProfileResult) subject.getPrincipal();

        return new Result(ResultCode.SUCCESS, profileResult);

    }
}
