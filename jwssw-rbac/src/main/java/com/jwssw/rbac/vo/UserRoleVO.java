package com.jwssw.rbac.vo;

import com.jwssw.core.base.vo.MyValueObject;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户角色VO对象
 *
 * @author Rewloc
 * @version 1.0
 * @date 2020/2/4 14:30
 * @since JDK 11
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class UserRoleVO extends MyValueObject {
    /**
     * 表业务字段
     */
    private String userId;
    private String roleId;
}
