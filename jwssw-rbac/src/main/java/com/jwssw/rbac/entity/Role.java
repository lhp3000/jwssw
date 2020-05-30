package com.jwssw.rbac.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jwssw.core.base.entity.MyModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 系统角色实体类
 *
 * @author Rewloc
 * @version 1.0
 * @date 2020/2/2 12:15
 * @since JDK 11
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@TableName("sys_role")
public class Role extends MyModel<Role> {
    /**
     * 表字段静态值
     */
    public static final String NAME = "name";
    public static final String REMARK = "remark";

    /**
     * 表业务字段
     */
    private String name;
    private String remark;
}
