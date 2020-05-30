package com.jwssw.rbac.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jwssw.core.base.service.MyService;
import com.jwssw.core.http.PageRequest;
import com.jwssw.rbac.entity.Role;
import com.jwssw.rbac.vo.MenuVO;
import com.jwssw.rbac.vo.RoleMenuVO;
import com.jwssw.rbac.vo.RoleVO;
import com.jwssw.rbac.vo.UserVO;

import java.util.List;
import java.util.Map;

/**
 * 系统角色服务接口
 *
 * @author Rewloc
 * @version 1.0
 * @date 2020/2/2 12:25
 * @since JDK 11
 */
public interface RoleService extends MyService<Role> {
    /**
     * 分页查询
     *
     * @param req 分页请求对象
     * @return 查询结果
     */
    IPage<Role> selectPage(PageRequest req);

    /**
     * 保存数据源信息
     *
     * @param vo Role信息
     * @return 是否成功
     */
    public boolean save(RoleVO vo);

    /**
     * 根据用户Id集合获取对应的权限
     *
     * @param userIds 用户Id集合
     * @return 角色集合
     */
    List<Map<String, String>> listByUserIds(List<String> userIds);

    /**
     * 查询角色菜单集合
     *
     * @return 角色对应的菜单集合
     */
    List<MenuVO> findRoleMenus(String roleId);

    /**
     * 保存角色菜单
     *
     * @param records
     * @return
     */
    boolean saveRoleMenus(List<RoleMenuVO> records);
}
