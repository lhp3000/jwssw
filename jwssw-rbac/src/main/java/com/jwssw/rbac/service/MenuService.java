package com.jwssw.rbac.service;

import com.jwssw.core.base.service.MyService;
import com.jwssw.rbac.entity.Menu;
import com.jwssw.rbac.vo.MenuVO;
import com.jwssw.rbac.vo.RouterVO;

import java.util.List;

/**
 * 系统菜单服务接口
 *
 * @author Rewloc
 * @version 1.0
 * @date 2020/1/31 13:10
 * @since JDK 11
 */
public interface MenuService extends MyService<Menu> {

    /**
     * 查询菜单树
     *
     * @return 菜单集合
     */
    List<MenuVO> findMenuTree();

    /**
     * 获取用户角色菜单
     *
     * @param userName 用户名
     * @param menuType 菜单类型
     * @return 用户菜单列表
     */
    public List<RouterVO> findNavTree(String userName, int menuType);

    /**
     * 根据用户名称获取用户权限李彪
     *
     * @param userName 用户名
     * @return 权限集合
     */
    List<String> findPermissions(String userName);

    /**
     * 根据角色id查询对应的菜单信息
     *
     * @param roleId 角色id
     * @return 菜单集合
     */
    List<MenuVO> findRoleMenus(String roleId);

    /**
     * 保存、修改菜单信息
     *
     * @param vo 菜单实体类
     * @return 是否成功
     */
    public boolean save(MenuVO vo);
}
