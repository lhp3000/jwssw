package com.jwssw.auth.userdetails.service;

import com.jwssw.auth.userdetails.entity.Menu;
import com.jwssw.core.base.service.MyService;

import java.util.List;

/**
 * 系统菜单服务接口
 *
 * @author Rewloc
 * @version 1.0
 * @date 2020/1/26 14:01
 * @since JDK 11
 */
public interface MenuService extends MyService<Menu> {

    /**
     * 根据角色ID集合查询对应的菜单权限集合
     *
     * @param roleIds 角色ID集合
     * @return 菜单权限集合
     */
    List<String> listPermsByRoleIds(List<String> roleIds);
}
