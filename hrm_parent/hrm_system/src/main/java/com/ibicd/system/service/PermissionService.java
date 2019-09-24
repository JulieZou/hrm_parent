package com.ibicd.system.service;

import com.hrm.common.entity.ResultCode;
import com.hrm.common.exception.CommonException;
import com.hrm.common.utils.BeanMapUtils;
import com.hrm.common.utils.IdWorker;
import com.ibicd.domain.system.*;
import com.ibicd.system.dao.PermissionApiDao;
import com.ibicd.system.dao.PermissionDao;
import com.ibicd.system.dao.PermissionMenuDao;
import com.ibicd.system.dao.PermissionPointDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hrm.common.utils.PermissionConstants;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @ClassName PermissionService
 * @Description 权限接口类
 * @Author Julie
 * @Date 2019/9/23 7:17
 * @Version 1.0
 */
@Service
@Transactional
public class PermissionService {

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private PermissionMenuDao permissionMenuDao;

    @Autowired
    private PermissionDao permissionDao;

    @Autowired
    private PermissionPointDao permissionPointDao;

    @Autowired
    private PermissionApiDao permissionApiDao;


    public void save(Map<String, Object> map) throws Exception {
        String id = idWorker.nextId() + "";
        //1.通过map构造permission对象
        Permission permission = BeanMapUtils.mapToBean(map, Permission.class);
        //2.根据类型构造不同的资源对象（菜单，按钮，api）
        Integer type = permission.getType();
        switch (type) {
            case PermissionConstants.PY_MENU:
                PermissionMenu menu = BeanMapUtils.mapToBean(map, PermissionMenu.class);
                menu.setId(id);
                permissionMenuDao.save(menu);
                break;
            case PermissionConstants.PY_POINT:
                PermissionPoint permissionPoint = BeanMapUtils.mapToBean(map, PermissionPoint.class);
                permissionPoint.setId(id);
                permissionPointDao.save(permissionPoint);
                break;
            case PermissionConstants.PY_API:
                PermissionApi permissionApi = BeanMapUtils.mapToBean(map, PermissionApi.class);
                permissionApi.setId(id);
                permissionApiDao.save(permissionApi);
                break;
            default:
                throw new CommonException(ResultCode.FAIL);

        }
        //3.保存
        permissionDao.save(permission);
    }

    /**
     * 更新权限
     *
     * @param map
     * @throws Exception
     */
    public void update(Map<String, Object> map) throws Exception {

        Permission perm = BeanMapUtils.mapToBean(map, Permission.class);
        String permId = perm.getId();
        Permission permission = permissionDao.findById(permId).get();
        permission.setCode(perm.getCode());
        permission.setName(perm.getName());
        permission.setDescription(perm.getDescription());
        permission.setEnVisible(perm.getEnVisible());

        Integer type = perm.getType();
        switch (type) {

            case PermissionConstants.PY_MENU:
                PermissionMenu menu = BeanMapUtils.mapToBean(map, PermissionMenu.class);
                menu.setId(permId);
                permissionMenuDao.save(menu);
                break;
            case PermissionConstants.PY_POINT:
                PermissionPoint permissionPoint = BeanMapUtils.mapToBean(map, PermissionPoint.class);
                permissionPoint.setId(permId);
                permissionPointDao.save(permissionPoint);
                break;
            case PermissionConstants.PY_API:
                PermissionApi permissionApi = BeanMapUtils.mapToBean(map, PermissionApi.class);
                permissionApi.setId(permId);
                permissionApiDao.save(permissionApi);
                break;
            default:
                throw new CommonException(ResultCode.FAIL);
        }
        permissionDao.save(permission);

    }

    /**
     * 根据id查找权限
     *
     * @param id
     * @return
     * @throws Exception
     */
    public Map<String, Object> findById(String id) throws Exception {
        Optional<Permission> permissionOptional = permissionDao.findById(id);

        if (permissionOptional.isPresent()) {
            Permission permission = permissionOptional.get();
            Integer type = permission.getType();
            Object object = null;

            if (type == PermissionConstants.PY_MENU) {
                object = permissionMenuDao.findById(id).get();
            } else if (type == PermissionConstants.PY_POINT) {
                object = permissionPointDao.findById(id).get();
            } else if (type == PermissionConstants.PY_API) {
                object = permissionApiDao.findById(id).get();
            } else {
                throw new CommonException(ResultCode.FAIL);
            }

            Map<String, Object> map = BeanMapUtils.beanToMap(object);
            map.put("name", permission.getName());
            map.put("type", permission.getType());
            map.put("code", permission.getCode());
            map.put("description", permission.getDescription());
            map.put("pid", permission.getPid());
            map.put("enVisible", permission.getEnVisible());

            return map;
        }

        return null;
    }

    /**
     * 4.查询全部
     * type : 查询全部权限列表type：0：菜单 + 按钮（权限点） 1：菜单2：按钮（权限点）3：API接
     * 口
     * enVisible : 0：查询所有saas平台的最高权限，1：查询企业的权限
     * pid ：父id
     */
    public List<Permission> findAll(Map<String, Object> map) {
        //1.需要查询条件
        Specification<Permission> spec = new Specification<Permission>() {
            /**
             * 动态拼接查询条件
             * @return
             */
            public Predicate toPredicate(Root<Permission> root, CriteriaQuery<?>
                    criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();

                //根据父id查询
                if (!StringUtils.isEmpty(map.get("pid"))) {
                    list.add(criteriaBuilder.equal(root.get("pid").as(String.class),
                            (String) map.get("pid")));
                }

                //根据enVisible查询
                if (!StringUtils.isEmpty(map.get("enVisible"))) {
                    list.add(criteriaBuilder.equal(root.get("enVisible").as(String.class),
                            (String) map.get("enVisible")));
                }

                //根据类型 type
                if (!StringUtils.isEmpty(map.get("type"))) {
                    String ty = (String) map.get("type");
                    CriteriaBuilder.In<Object> in =
                            criteriaBuilder.in(root.get("type"));
                    if ("0".equals(ty)) {
                        in.value(1).value(2);
                    } else {
                        in.value(Integer.parseInt(ty));
                    }
                    list.add(in);
                }
                return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
            }
        };
        return permissionDao.findAll(spec);
    }


    /**
     * 5.根据id删除
     * //1.删除权限
     * //2.删除权限对应的资源
     */
    public void deleteById(String id) throws Exception {

        //1.通过传递的权限id查询权限
        Permission permission = permissionDao.findById(id).get();
        permissionDao.delete(permission);

        //2.根据类型构造不同的资源
        int type = permission.getType();
        switch (type) {
            case PermissionConstants.PY_MENU:
                permissionMenuDao.deleteById(id);
                break;
            case PermissionConstants.PY_POINT:
                permissionPointDao.deleteById(id);
                break;
            case PermissionConstants.PY_API:
                permissionApiDao.deleteById(id);
                break;
            default:
                throw new CommonException(ResultCode.FAIL);
        }
    }

}


