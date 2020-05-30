package com.jwssw.rbac.service;

import com.jwssw.core.base.service.MyService;
import com.jwssw.rbac.entity.UserRole;

import java.util.List;

/**
 * 系统用户服务接口
 *
 * @author Rewloc
 * @version 1.0
 * @date 2020/1/31 14:10
 * @since JDK 11
 */
public interface UserRoleService extends MyService<UserRole> {

    /**
     * 通过用户名称获取其对应的所有角色Id集合
     *
     * @param userName 用户名
     * @return 角色Id集合
     */
    List<String> listRoleIdByUsername(String userName);


    /**
     * 根据用户ID删除已有的角色集合
     *
     * @param userId 用户ID
     * @return 操作条数
     */
    int deleteByUserId(String userId);
}
