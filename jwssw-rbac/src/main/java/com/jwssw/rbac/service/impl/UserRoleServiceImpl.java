package com.jwssw.rbac.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jwssw.core.base.service.impl.MyServiceImpl;
import com.jwssw.rbac.entity.User;
import com.jwssw.rbac.entity.UserRole;
import com.jwssw.rbac.mapper.UserRoleMapper;
import com.jwssw.rbac.service.UserRoleService;
import com.jwssw.rbac.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 系统用户角色的服务接口实现类
 *
 * @author Rewloc
 * @version 1.0
 * @date 2020/1/31 14:11
 * @since JDK 11
 */
@Service
public class UserRoleServiceImpl extends MyServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

    @Autowired
    private UserService userService;

    /**
     * 通过用户名称获取其对应的所有角色Id集合
     *
     * @param userName 用户名
     * @return 角色Id集合
     */
    @Override
    public List<String> listRoleIdByUsername(String userName) {
        // 根据用户名获取用户信息
        QueryWrapper<User> qwUser = new QueryWrapper<>();
        qwUser.eq(User.NAME, userName);
        User user = userService.getOne(qwUser);
        if (ObjectUtil.isNull(user)) {
            return null;
        }

        // 根据用户ID获取用户所有权限
        QueryWrapper<UserRole> qwUserRole = new QueryWrapper<>();
        qwUserRole.eq(UserRole.USER_ID, user.getId());
        List<UserRole> list = baseMapper.selectList(qwUserRole);
        if (CollUtil.isEmpty(list)) {
            return null;
        }

        // 组装角色ID集合
        return list.stream().map(UserRole::getRoleId).collect(Collectors.toList());
    }

    /**
     * 根据用户ID删除已有的角色集合
     *
     * @param userId 用户ID
     * @return 删除条数
     */
    @Override
    public int deleteByUserId(String userId) {
        // 执行删除操作并返回操作条数
        return baseMapper.deleteByUserId(userId);
    }
}
