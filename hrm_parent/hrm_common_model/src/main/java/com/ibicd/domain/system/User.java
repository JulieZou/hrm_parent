package com.ibicd.domain.system;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ibicd.domain.poi.ExcelAttribute;
import lombok.Getter;
import lombok.Setter;
import org.apache.poi.ss.usermodel.DateUtil;

import javax.persistence.*;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName User
 * @Description 用户实体类
 * @Author Julie
 * @Date 2019/9/22 13:03
 * @Version 1.0
 */
@Entity
@Table(name = "bs_user")
@Getter
@Setter
public class User implements Serializable {

    public User() {

    }


    public User(Object[] values) {
        this.username = values[1].toString();
        this.mobile = values[2].toString();
        this.workNumber = new DecimalFormat("#").format(values[3]).toString();
        this.formOfEmployment = ((Double) values[4]).intValue();
        this.timeOfEntry = DateUtil.parseYYYYMMDDDate(values[5].toString());
        this.departmentId = values[6].toString();

    }

    /**
     * ID
     */
    @Id
    private String id;
    /**
     * 手机号码
     */
    private String mobile;
    /**
     * 用户名称
     */
    @ExcelAttribute(sort = 1)
    private String username;
    /**
     * 密码
     */
    private String password;

    /**
     * 启用状态 0为禁用 1为启用
     */
    private Integer enableState;
    /**
     * 创建时间
     */
    private Date createTime;

    private String companyId;

    private String companyName;

    /**
     * 部门ID
     */
    private String departmentId;

    /**
     * 入职时间
     */
    private Date timeOfEntry;

    /**
     * 聘用形式
     */
    private Integer formOfEmployment;

    /**
     * 工号
     */
    private String workNumber;

    /**
     * 管理形式
     */
    private String formOfManagement;

    /**
     * 工作城市
     */
    private String workingCity;

    /**
     * 转正时间
     */
    private Date correctionTime;

    /**
     * sassAdmin saas管理员具备所有权限
     * coAdmin 企业管理员具备企业所有权限
     * user:普通用户（需要分配角色）
     */
    private String level;

    private String staffPhoto;//用户头像

    /**
     * 在职状态 1.在职  2.离职
     */
    private Integer inServiceStatus;

    private String departmentName;

    /**
     * JsonIgnore
     * : 忽略json转化
     */
    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "pe_user_role", joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")}
    )
    private Set<Role> roles = new HashSet<Role>();//用户与角色   多对多
}
