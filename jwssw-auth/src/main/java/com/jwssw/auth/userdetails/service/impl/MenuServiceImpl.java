package com.jwssw.auth.userdetails.service.impl;

import com.jwssw.auth.userdetails.entity.Menu;
import com.jwssw.auth.userdetails.mapper.MenuMapper;
import com.jwssw.auth.userdetails.service.MenuService;
import com.jwssw.core.base.service.impl.MyServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统菜单服务接口实现类
 *
 * @author Rewloc
 * @version 1.0
 * @date 2020/1/26 14:02
 * @since JDK 11
 */
@Service
public class MenuServiceImpl extends MyServiceImpl<MenuMapper, Menu> implements MenuService {

    /**
     * 根据角色ID集合查询对应的菜单权限集合
     *
     * @param roleIds 角色ID集合
     * @return 菜单权限集合
     */
    @Override
    public List<String> listPermsByRoleIds(List<String> roleIds) {
//        QueryWrapper qw = new QueryWrapper();
//        qw.in("role_id", roleIds);
        return baseMapper.listPermsByRoleIds(roleIds);
    }
}
