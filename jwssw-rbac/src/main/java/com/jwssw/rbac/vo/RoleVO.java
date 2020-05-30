package com.jwssw.rbac.vo;

import com.jwssw.core.base.vo.MyValueObject;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 系统角色Value Object对象
 *
 * @author Rewloc
 * @version 1.0
 * @date 2020/2/2 12:23
 * @since JDK 11
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class RoleVO extends MyValueObject {
    /**
     * 表业务字段
     */
    private String name;
    private String remark;
}
