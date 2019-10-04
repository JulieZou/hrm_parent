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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

        //1.根据mobilec查询用户
        User user = userService.findByMobile(mobile);
        //登录失败
        if (user == null || !user.getPassword().equals(password))
            return new Result(ResultCode.MOBILE_OR_PWD_ERROR);
        else {
            //登录成功,将有权限的api放入到token中
            StringBuilder builder = new StringBuilder();
            Set<Role> roles = user.getRoles();
            for (Role role : roles) {
                Set<Permission> permissions = role.getPermissions();
                for (Permission permission : permissions) {
                    if (permission.getType() == PermissionConstants.PY_API)
                        builder.append(",").append(permission.getCode());
                }
            }
            //3.生成jwt信息
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("companyId", user.getCompanyId());
            map.put("companyName", user.getUsername());
            map.put("apis", builder.toString().substring(1));
            String token = jwtUtils.createJwt(user.getId(), user.getUsername(), map);
            return new Result(ResultCode.SUCCESS, token);

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
        Claims claims = (Claims) request.getAttribute("user_claims");

        String userId = claims.getId();
        User user = userService.findById(userId);

        //1.sassAdmin saas管理员具备所有权限
        //2.coAdmin 企业管理员具备企业所有权限
        //3.user:普通用户（需要分配角色）
        ProfileResult profileResult = null;
        if (UserLevelConstants.NORMAL_USER.equals(user.getLevel())) {
            profileResult = new ProfileResult(user);
        } else {
            Map map = new HashMap();
            if (UserLevelConstants.COMP_ADMIN.equals(user.getLevel())) {
                map.put("enVisible", "1");
            }
            List<Permission> all = permissionService.findAll(map);

            profileResult = new ProfileResult(user, all);
        }

        return new Result(ResultCode.SUCCESS, profileResult);

    }
}
