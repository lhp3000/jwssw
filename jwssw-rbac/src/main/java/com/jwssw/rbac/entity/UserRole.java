package com.jwssw.rbac.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jwssw.core.base.entity.MyModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户角色关联实体类
 *
 * @author Rewloc
 * @version 1.0
 * @date 2020/1/31 14:00
 * @since JDK 11
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@TableName("sys_user_role")
public class UserRole extends MyModel<UserRole> {
    /**
     * 表字段静态值
     */
    public static final String USER_ID = "user_id";
    public static final String ROLE_ID = "role_id";

    /**
     * 表业务字段
     */
    private String userId;
    private String roleId;
}
