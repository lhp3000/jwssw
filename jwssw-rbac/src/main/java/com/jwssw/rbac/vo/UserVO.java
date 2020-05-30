package com.jwssw.rbac.vo;

import com.jwssw.core.base.vo.MyValueObject;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 系统用户VO对象
 *
 * @author Rewloc
 * @version 1.0
 * @date 2020/1/26 14:04
 * @since JDK 11
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class UserVO extends MyValueObject {

    /**
     * 表业务字段
     */
    private String name;
    private String nickName;
    private String avatar;
    private String password;
    private String salt;
    private String email;
    private String mobile;
    private String status;
    private String deptId;
    /**
     * 权限集合，主要是存放sysmenu表中的perms字段值
     */
    List<String> listPerms;
    /**
     * 用户角色集合
     */
    private List<UserRoleVO> listUserRole;
    /**
     * 用户列表中显示的角色名称
     */
    private String roleNames;

    /**
     * 用户部门名称
     */
    private String deptName;
}
