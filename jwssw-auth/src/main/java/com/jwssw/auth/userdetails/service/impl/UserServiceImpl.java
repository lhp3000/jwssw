package com.jwssw.auth.userdetails.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jwssw.auth.userdetails.entity.User;
import com.jwssw.auth.userdetails.mapper.UserMapper;
import com.jwssw.auth.userdetails.service.MenuService;
import com.jwssw.auth.userdetails.service.UserService;
import com.jwssw.auth.userdetails.vo.UserVO;
import com.jwssw.core.base.service.impl.MyServiceImpl;
import com.jwssw.core.util.Utils.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统用户的服务接口实现类
 *
 * @author Rewloc
 * @version 1.0
 * @date 2020/1/26 13:20
 * @since JDK 11
 */
@Service
public class UserServiceImpl extends MyServiceImpl<UserMapper, User> implements UserService {

    /**
     * 加载菜单服务
     */
    @Autowired
    private MenuService menuService;

    /**
     * 根据用户名查询用户信息
     *
     * @param name 用户名
     * @return 用户信息（包括：用户权限等）
     */
    @Override
    public UserVO loadUserByUsername(String name) {
        // 根据用户名查询用户信息
        QueryWrapper<User> qwUser = new QueryWrapper<>();
        qwUser.eq(User.NAME, name);
        User sysUser = baseMapper.selectOne(qwUser);
        if (ObjectUtil.isNull(sysUser)) {
            return null;
        }

        UserVO userVo = BeanUtil.toBean(sysUser, UserVO.class);
        // 根据用户信息获取用户角色
        List<String> listRoleId = baseMapper.listRoleIdByUserId(userVo.getId());
        if (CollUtil.isNotEmpty(listRoleId)) {
            // 根据用户角色获取用户权限
            List<String> listPerms = menuService.listPermsByRoleIds(listRoleId);
            if (CollUtil.isNotEmpty(listPerms)) {
                userVo.setListPerms(listPerms);
            }
        }

        // 返回用户信息
        return userVo;
    }
}
