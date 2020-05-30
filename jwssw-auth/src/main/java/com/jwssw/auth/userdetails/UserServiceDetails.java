package com.jwssw.auth.userdetails;

import cn.hutool.core.util.ObjectUtil;
import com.jwssw.auth.userdetails.service.UserService;
import com.jwssw.auth.userdetails.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义用户信息类
 *
 * @author Rewloc
 * @version 1.0
 * @date 2020/1/16 14:10
 * @since JDK 11
 */
@Slf4j
@Component
public class UserServiceDetails implements UserDetailsService {
    /**
     * 系统用户服务接口
     */
    @Autowired
    private UserService userService;

    /**
     * 根据用户名查询用户信息
     *
     * @param username 用户名
     * @return 用户信息
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 根据用户名从数据库中获取用户信息
        UserVO userVo = userService.loadUserByUsername(username);

        // 判断用户信息是否为空，如果为空提示不存在用户信息
        if (ObjectUtil.isNull(userVo)) {
            throw new UsernameNotFoundException("the user is not found");
        } else {
            // 加载用户权限信息
            List<GrantedAuthority> authorities = new ArrayList<>();
            userVo.getListPerms().forEach(perms -> {
                authorities.add(new SimpleGrantedAuthority(perms));
            });

            // 默认登录权限
            authorities.add(new SimpleGrantedAuthority("rbac:login:check"));

            // todo 采用spring security已经实现的UserDetail对象来返回
            // todo 密码比对方式（用户名+密码+盐）
            return new User(username, userVo.getPassword(), authorities);
        }
    }
}
