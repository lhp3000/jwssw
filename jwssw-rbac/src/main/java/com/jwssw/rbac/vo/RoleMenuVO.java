package com.jwssw.rbac.vo;

import com.jwssw.core.base.vo.MyValueObject;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 角色菜单 VO对象
 *
 * @author Rewloc
 * @version 1.0
 * @date 2020-02-18 14:26:12
 * @since JDK 11
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class RoleMenuVO extends MyValueObject {
    /**
     * 表业务字段
     */
    private String roleId;
    private String menuId;
}
