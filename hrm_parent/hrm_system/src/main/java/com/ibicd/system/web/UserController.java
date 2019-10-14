
package com.ibicd.system.web;

import com.ibicd.common.controller.BaseController;
import com.ibicd.common.entity.PageResult;
import com.ibicd.common.entity.Result;
import com.ibicd.common.entity.ResultCode;
import com.ibicd.common.poi.ExcelImportUtils;
import com.ibicd.domain.system.User;
import com.ibicd.system.service.UserService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName UserController
 * @Description 用户
 * @Author Julie
 * @Date 2019/9/22 22:42
 * @Version 1.0
 */
@RestController
@RequestMapping("/sys")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    /**
     * 用户上传头像
     *
     * @param id
     * @param file
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/user/upload/{id}")
    public Result uploadPhoto(@PathVariable("id") String id, @RequestParam(name = "file") MultipartFile file) throws IOException {
        String imageUrl = userService.uploadImage(id, file);
        return new Result(ResultCode.SUCCESS, imageUrl);
    }


    @RequestMapping(value = "/user/import", method = RequestMethod.POST)
    public Result importUser(@RequestParam(value = "file") MultipartFile file) throws Exception {
        InputStream fis = null;
        try {
            fis = file.getInputStream();
            //获取用户数据列表
            List<User> users = new ArrayList<>();
            ExcelImportUtils<User> importUtils = new ExcelImportUtils<>(User.class);
            users = importUtils.parseExcel(fis, 1, 1);

            //批量保存数据
            userService.saveAll(users, companyId, companyName);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return new Result(ResultCode.SUCCESS);

    }


    /**
     * 获取单元格内容
     *
     * @param cell
     * @return
     */
    private Object getCellValue(Cell cell) {

        CellType cellType = cell.getCellType();
        Object value = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //根据单元格类型获取数据
        switch (cellType) {
            case STRING:
                value = cell.getStringCellValue();
                break;
            case BOOLEAN:
                value = cell.getBooleanCellValue();
                break;
            case NUMERIC://日期也是数字格式
                if (DateUtil.isCellDateFormatted(cell))
                    value = sdf.format(cell.getDateCellValue());
                else
                    value = cell.getNumericCellValue();
                break;
            case FORMULA:
                value = cell.getCellFormula();
                break;
        }

        return value;
    }

    //保存用户
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public Result add(@RequestBody User user) throws Exception {
        user.setCompanyId(parseCompanyId());
        user.setCompanyName(parseCompanyName());
        userService.save(user);
        return Result.SUCCESS();
    }

    //更新用户
    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT, name = "API-USER-UPDATE")
    public Result update(@PathVariable(name = "id") String id, @RequestBody User user)
            throws Exception {
        userService.update(user);
        return Result.SUCCESS();
    }

    //删除用户
    @RequiresPermissions(value = "API-USER-DELETE")
    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE, name = "API-USER-DELETE")
    public Result delete(@PathVariable(name = "id") String id) throws Exception {
        userService.deleteById(id);
        return Result.SUCCESS();
    }

    /**
     * 根据ID查询用户
     */
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable(name = "id") String id) throws Exception {
        User user = userService.findById(id);
        return new Result(ResultCode.SUCCESS, user);
    }

    /**
     * 分页查询用户
     */
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public Result findByPage(int page, int pagesize, @RequestParam Map<String, Object>
            map) throws Exception {
        map.put("companyId", parseCompanyId());
        Page<User> searchPage = userService.findAll(map, page, pagesize);
        PageResult<User> pr = new PageResult(searchPage.getTotalElements(), searchPage.getContent());
        return new Result(ResultCode.SUCCESS, pr);
    }

    /**
     * 给指定用户分配角色
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "/user/assignRoles", method = RequestMethod.PUT)
    public Result assignRoles(@RequestBody Map<String, Object> map) {
        String userId = (String) map.get("id");
        List<String> roleIds = (List<String>) map.get("roleIds");
        userService.assignRoles(userId, roleIds);
        return new Result(ResultCode.SUCCESS);
    }
}
