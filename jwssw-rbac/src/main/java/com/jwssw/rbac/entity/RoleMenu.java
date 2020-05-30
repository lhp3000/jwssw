package com.jwssw.rbac.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jwssw.core.base.entity.MyModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 角色菜单 实体
 *
 * @author Rewloc
 * @version 1.0
 * @date 2020-02-18 14:26:12
 * @since JDK 11
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@TableName("sys_role_menu")
public class RoleMenu extends MyModel<RoleMenu> {
    /**
     * 表字段静态值
     */
    public static final String ROLE_ID = "role_id";
    public static final String MENU_ID = "menu_id";
    /**
     * 表业务字段
     */
    private String roleId;
    private String menuId;
}
