package com.jwssw.rbac.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jwssw.core.base.service.impl.MyServiceImpl;
import com.jwssw.core.constant.MyConst;
import com.jwssw.core.http.PageRequest;
import com.jwssw.core.util.Utils;
import com.jwssw.core.util.Utils.BeanUtil;
import com.jwssw.rbac.entity.Menu;
import com.jwssw.rbac.entity.Role;
import com.jwssw.rbac.entity.RoleMenu;
import com.jwssw.rbac.entity.User;
import com.jwssw.rbac.mapper.RoleMapper;
import com.jwssw.rbac.service.MenuService;
import com.jwssw.rbac.service.RoleMenuService;
import com.jwssw.rbac.service.RoleService;
import com.jwssw.rbac.vo.MenuVO;
import com.jwssw.rbac.vo.RoleMenuVO;
import com.jwssw.rbac.vo.RoleVO;
import com.jwssw.rbac.vo.UserVO;
import org.apache.commons.lang.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 系统角色服务接口实现类
 *
 * @author Rewloc
 * @version 1.0
 * @date 2020/2/2 12:27
 * @since JDK 11
 */
@Service
public class RoleServiceImpl extends MyServiceImpl<RoleMapper, Role> implements RoleService {
    @Autowired
    private MenuService menuService;
    @Autowired
    private RoleMenuService roleMenuService;

    /**
     * 分页查询
     *
     * @param req 分页请求对象
     * @return 查询结果
     */
    @Override
    public IPage<Role> selectPage(PageRequest req) {
        IPage<Role> page = new Page<>(req.getCurrent(), req.getSize());
        QueryWrapper<Role> qw = new QueryWrapper<>();
        Role entity = req.toBean(Role.class);
        if (StrUtil.isNotEmpty(entity.getName())) {
            qw.like(Role.NAME, entity.getName());
            qw.or();
            qw.like(Role.REMARK, entity.getName());
        }
        qw.orderByAsc(Role.NAME);
        return baseMapper.selectPage(page, qw);
    }

    /**
     * 保存Role信息
     *
     * @param vo Role信息
     * @return 是否成功
     */
    @Override
    public boolean save(RoleVO vo) {
        // 如果id为空表示新增
        if (StrUtil.isEmpty(vo.getId())) {
            return baseMapper.insert(vo.toPO(Role.class)) > 0;
        }

        // 使用id进行修改
        return baseMapper.updateById(vo.toPO(Role.class)) > 0;
    }

    /**
     * 根据用户Id集合获取对应的权限
     *
     * @param userIds 用户Id集合
     * @return 角色集合
     */
    @Override
    public List<Map<String, String>> listByUserIds(List<String> userIds) {
        return baseMapper.listByUserIds(userIds);
    }

    /**
     * 查询角色菜单集合
     *
     * @return 角色对应的菜单集合
     */
    @Override
    public List<MenuVO> findRoleMenus(String roleId) {
        // 查询角色信息
        Role sysRole = baseMapper.selectById(roleId);

        // 判断是否为admin角色
        if (MyConst.ADMIN.equalsIgnoreCase(sysRole.getName())) {
            // 如果是超级管理员，返回全部
            return BeanUtil.listPOToVO(menuService.list(), MenuVO.class);
        }

        // 除管理员之外的角色
        return menuService.findRoleMenus(roleId);
    }

    /**
     * 保存角色对应的菜单信息
     *
     * @param records 角色菜单集合
     * @return 操作数据条数
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveRoleMenus(List<RoleMenuVO> records) {
        // 获取角色ID
        String roleId = records.get(0).getRoleId();
        // 移除已经有的角色菜单关联信息
        QueryWrapper<RoleMenu> qu = new QueryWrapper<>();
        qu.eq(RoleMenu.ROLE_ID, roleId);
        roleMenuService.remove(qu);

        // 批量保存角色菜单权限信息
        return roleMenuService.saveBatch(BeanUtil.listVOToPO(records, RoleMenu.class));
    }
}
