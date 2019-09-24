package com.ibicd.system.web;

import com.hrm.common.entity.PageResult;
import com.hrm.common.entity.Result;
import com.hrm.common.entity.ResultCode;
import com.ibicd.domain.system.Role;
import com.ibicd.system.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @ClassName RoleController
 * @Description 角色控制器
 * @Author Julie
 * @Date 2019/9/22 22:50
 * @Version 1.0
 */
@RestController
@RequestMapping("/sys")
public class RoleController {

    @Autowired
    private RoleService roleService;

    //添加角色
    @RequestMapping(value = "/role", method = RequestMethod.POST)
    public Result add(@RequestBody Role role) throws Exception {
        String companyId = "1";
        role.setCompanyId(companyId);
        roleService.save(role);
        return Result.SUCCESS();
    }

    //更新角色
    @RequestMapping(value = "/role/{id}", method = RequestMethod.PUT)
    public Result update(@PathVariable(name = "id") String id, @RequestBody Role role)
            throws Exception {
        roleService.update(role);
        return Result.SUCCESS();
    }

    //删除角色
    @RequestMapping(value = "/role/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable(name = "id") String id) throws Exception {
        roleService.delete(id);
        return Result.SUCCESS();
    }

    /**
     * 根据ID获取角色信息
     */
    @RequestMapping(value = "/role/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable(name = "id") String id) throws Exception {
        Role role = roleService.findById(id);
        return new Result(ResultCode.SUCCESS, role);
    }

    /**
     * 分页查询角色
     */
    @RequestMapping(value = "/role", method = RequestMethod.GET)
    public Result findByPage(int page, int pagesize, Role role) throws Exception {
        String companyId = "1";
        Page<Role> searchPage = roleService.findSearch(companyId, page, pagesize);
        PageResult<Role> pr = new
                PageResult(searchPage.getTotalElements(), searchPage.getContent());
        return new Result(ResultCode.SUCCESS, pr);
    }

    /**
     * 分配权限
     *
     * @param map
     * @return
     */
    public Result assignPermission(@RequestBody Map<String, Object> map) {

        String roleId = (String) map.get("id");
        List<String> permIds = (List<String>) map.get("permIds");
        roleService.assignPerms(roleId, permIds);
        return new Result(ResultCode.SUCCESS);

    }

}
