package com.jwssw.rbac.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.jwssw.core.base.service.impl.MyServiceImpl;
import com.jwssw.core.constant.MyConst;
import com.jwssw.core.util.Utils.BeanUtil;
import com.jwssw.rbac.entity.Menu;
import com.jwssw.rbac.mapper.MenuMapper;
import com.jwssw.rbac.service.MenuService;
import com.jwssw.rbac.service.UserRoleService;
import com.jwssw.rbac.vo.MenuVO;
import com.jwssw.rbac.vo.RouterVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 系统菜单的服务接口实现类
 *
 * @author Rewloc
 * @version 1.0
 * @date 2020/1/31 13:11
 * @since JDK 11
 */
@Service
public class MenuServiceImpl extends MyServiceImpl<MenuMapper, Menu> implements MenuService {

    /**
     * 加载用户角色服务接口
     */
    @Autowired
    private UserRoleService userRoleServer;


    /**
     * 查询菜单树
     *
     * @return 菜单集合
     */
    @Override
    public List<MenuVO> findMenuTree() {
        // 获取系统中所有菜单
        List<Menu> menus = list();
        // 过滤顶级菜单
        List<MenuVO> superMenus = menus.stream().filter(menu -> StrUtil.isEmpty(menu.getParentId())
                || "-1".equals(menu.getParentId())).map(menu -> menu.toVO(MenuVO.class)).collect(Collectors.toList());

        // 根据orderNum字段排序
        superMenus.sort((o1, o2) -> o1.getOrderNum().compareTo(o2.getOrderNum()));

        // 过滤子菜单
        findMenuChildren(superMenus, menus);

        // 返回用户的权限菜单
        return superMenus;
    }

    /**
     * 获取用户角色菜单
     *
     * @param userName 用户名
     * @param menuType 菜单类型
     * @return 用户菜单列表
     */
    @Override
    public List<RouterVO> findNavTree(String userName, int menuType) {
        // 根据用户名获取其角色集合
        List<String> listRoleIds = userRoleServer.listRoleIdByUsername(userName);
        if (CollUtil.isEmpty(listRoleIds)) {
            return null;
        }

        // 根据角色获取权限菜单集合并排序
        List<Menu> listMenus = baseMapper.listByRoleIds(listRoleIds);
        List<RouterVO> listSupper = listMenus.stream().filter(menu -> (StrUtil.isEmpty(menu.getParentId())
                || MyConst.NEGATIVE_ONE_STR.equals(menu.getParentId())))
                .map(menu -> BeanUtil.toBean(menu, RouterVO.class)).collect(Collectors.toList());
        // 根据orderNum字段排序
        listSupper.sort((o1, o2) -> o1.getOrderNum().compareTo(o2.getOrderNum()));

        // 过滤根菜单的子菜单
        findChildren(listSupper, listMenus, menuType);

        // 将sysMenuVO转化成RouterVO后返回
        return listSupper;
    }

    /**
     * 根据用户名称获取用户权限李彪
     *
     * @param userName 用户名
     * @return 权限集合
     */
    @Override
    public List<String> findPermissions(String userName) {
        // 获取用户角色信息
        List<String> listRoleIds = userRoleServer.listRoleIdByUsername(userName);
        if (CollUtil.isEmpty(listRoleIds)) {
            return null;
        }

        // 根据角色id集合获取对应的菜单信息
        List<Menu> listMenus = baseMapper.listByRoleIds(listRoleIds);

        // 返回角色对应的权限信息
        return listMenus.stream().filter(menu -> StrUtil.isNotEmpty(menu.getPerms())).map(Menu::getPerms)
                .collect(Collectors.toList());
    }

    /**
     * 根据角色id查询对应的菜单信息
     *
     * @param roleId 角色id
     * @return 菜单集合
     */
    @Override
    public List<MenuVO> findRoleMenus(String roleId) {
        // 获取角色对应的菜单
        List<Menu> list = baseMapper.listByRoleIds(Arrays.asList(roleId));

        // 将entity转换成vo返回
        return BeanUtil.listPOToVO(list, MenuVO.class);
    }

    /**
     * 保存、修改菜单信息
     *
     * @param vo 菜单实体类
     * @return 是否成功
     */
    @Override
    public boolean save(MenuVO vo) {
        // 如果菜单ID为空就新增，否则就修改
        if (StrUtil.isEmpty(vo.getId())) {
            return baseMapper.insert(vo.toPO(Menu.class)) > 0;
        }
        // 如果父ID为空，就默认为0
        if (StrUtil.isEmpty(vo.getParentId())) {
            vo.setParentId("-1");
        }
        // 更新菜单信息
        return baseMapper.updateById(vo.toPO(Menu.class)) > 0;
    }

    /**
     * 过滤子菜单树
     *
     * @param superMenus 父级菜单
     * @param menus      菜单集合
     */
    private void findMenuChildren(List<MenuVO> superMenus, List<Menu> menus) {
        // 循环父级菜单加载子菜单
        for (MenuVO menuVo : superMenus) {
            // 定义子菜单列表
            List<MenuVO> childrenList = new ArrayList<>();
            // 循环菜单并判断是否为当前父菜单的子菜单
            for (Menu menu : menus) {
                // 当前菜单父菜单Id是否和父菜单的id相同
                if (menuVo.getId().equals(menu.getParentId())) {
                    MenuVO childrenVo = menu.toVO(MenuVO.class);
                    childrenVo.setParentName(menuVo.getName());
                    childrenList.add(childrenVo);
                }
            }
            // 子菜单排序
            childrenList.sort((o1, o2) -> o1.getOrderNum().compareTo(o2.getOrderNum()));
            // 把子菜单添加到父级里
            menuVo.setChildren(childrenList);
            // 继续下一级菜单循环
            findMenuChildren(childrenList, menus);
        }
    }

    /**
     * 过滤路由子菜单
     *
     * @param superMenus 顶级菜单
     * @param menus      菜单集合
     * @param menuType   菜单类型
     */
    private void findChildren(List<RouterVO> superMenus, List<Menu> menus, int menuType) {
        // 循环跟菜单集合
        for (RouterVO superMenuVO : superMenus) {
            // 过滤加载子菜单集合
            List<RouterVO> children = new ArrayList<>();
            for (Menu menu : menus) {
                // 如果是获取类型不需要按钮，且菜单类型是按钮的，直接过滤掉
                if (menuType == 1 && "2".equals(menu.getType())) {
                    continue;
                }
                // 判断当前菜单是否为子菜单
                if (StrUtil.isNotEmpty(superMenuVO.getId()) && superMenuVO.getId().equals(menu.getParentId())) {
                    // 是否存在子菜单,不在的时候加入子菜单中
                    if (!children.stream().anyMatch(child -> child.getId().equals(menu.getId()))) {
                        children.add(BeanUtil.toBean(menu, RouterVO.class));
                    }
                }
            }
            // 添加子菜单集合
            superMenuVO.setChildren(children);
            // 根据orderNum字段过滤
            children.sort((o1, o2) -> o1.getOrderNum().compareTo(o2.getOrderNum()));
            // 继续循环下一级菜单集合
            findChildren(children, menus, menuType);
        }
    }
}
